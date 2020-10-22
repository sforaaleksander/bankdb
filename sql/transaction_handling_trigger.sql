CREATE OR REPLACE FUNCTION handle_transaction()
    RETURNS TRIGGER AS $$
    BEGIN
        if NEW.amount + (select available_balance from accounts where id=NEW.account_id) < 0
            THEN raise notice 'not sufficient funds';return NULL;
        END IF;
        call update_account_balance(new.account_id, new.amount);
        return NEW;
    end;
    $$
LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE update_account_balance(account_id integer, transaction_amount bigint)
AS $$
    BEGIN
        update public.accounts set available_balance = available_balance + transaction_amount WHERE id=account_id;
        update public.accounts set booking_balance = booking_balance + transaction_amount WHERE id=account_id;
    end;
    $$
LANGUAGE plpgsql;


CREATE TRIGGER check_funds_for_transaction
    BEFORE INSERT on transactions
    FOR EACH ROW
    EXECUTE PROCEDURE handle_transaction();
