CREATE OR REPLACE FUNCTION handle_transaction() RETURNS TRIGGER AS
$$
BEGIN
    if NEW.amount + (select available_balance from accounts where id = NEW.account_id) < 0
    THEN
        raise notice 'not sufficient funds';
        return NULL;
    END IF;
    call update_account_balance(new.account_id, new.amount, false);
    call insert_record_to_particular_transaction_table(new);
    return NEW;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE update_account_balance(account_id integer, transaction_amount bigint, is_incoming bool) AS
$$
DECLARE
    factor integer;
BEGIN
    IF is_incoming THEN factor := 1; ELSE factor := -1; END IF;
    update public.accounts
    set available_balance = available_balance + (transaction_amount * factor)
    WHERE id = account_id;
    update public.accounts set booking_balance = booking_balance + (transaction_amount * factor) WHERE id = account_id;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE insert_record_to_particular_transaction_table(data RECORD) AS
$$
DECLARE
    random_account_id integer;
BEGIN
    IF data.transaction_type_id = 3 THEN
        insert into public.atm_transactions (transaction_id, card_id, atm_id)
        values (data.id, get_card_id_from_account_id(data.account_id), get_random_atm_number());
    ELSIF data.transaction_type_id = 2 THEN
        insert into card_payments (transaction_id, card_id, recipient_name)
        VALUES (data.id, get_card_id_from_account_id(data.account_id), get_random_string());
    ELSE
        random_account_id := get_random_recipient_account_id();
        INSERT INTO transfers (transaction_id, recipient_account_id, title)
        VALUES (data.id, random_account_id, get_random_string());
        CALL update_account_balance(random_account_id, data.amount, true);
    END IF;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_random_recipient_account_id() RETURNS integer AS
$$
DECLARE
    max_id            integer;
    random_account_id integer;
BEGIN
    SELECT MAX(id) FROM accounts INTO max_id;
    random_account_id := floor(random() * (max_id - 1) + 1);
    RETURN random_account_id;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_random_string() RETURNS varchar(20) AS
$$
DECLARE
    string varchar(20);
BEGIN
    SELECT array_to_string(ARRAY(SELECT chr((97 + round(random() * 25)) :: integer)
                                 FROM generate_series(1, 15)), '')
    INTO string;
    RETURN string;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_card_id_from_account_id(this_account_id integer) RETURNS INTEGER AS
$$
DECLARE
    card_id_found integer;
BEGIN
    SELECT cards.id
    FROM cards
             JOIN accounts ON cards.account_id = accounts.id
    WHERE cards.account_id = this_account_id
    INTO card_id_found;
    RETURN card_id_found;
end;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_random_atm_number() RETURNS INTEGER AS
$$
DECLARE
    max        integer;
    random_atm integer;
BEGIN
    SELECT MAX(id) FROM atms INTO max;
    random_atm := floor(random() * (max - 1) + 1);
    RETURN random_atm;
end;
$$ LANGUAGE plpgsql;


CREATE TRIGGER check_funds_for_transaction
    BEFORE INSERT
    on transactions
    FOR EACH ROW
EXECUTE PROCEDURE handle_transaction();
