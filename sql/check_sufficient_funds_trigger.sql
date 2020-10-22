CREATE OR REPLACE FUNCTION check_if_sufficient_funds_for_transaction()
    RETURNS TRIGGER AS $$
    BEGIN
        if NEW.amount + (select available_balance from accounts where id=NEW.account_id) < 0
            THEN raise notice 'not sufficient funds';return NULL;
        END IF;
        return NEW;
    end;
    $$
LANGUAGE plpgsql;


CREATE TRIGGER check_funds_for_transaction
    BEFORE INSERT on transactions
    FOR EACH ROW
    EXECUTE PROCEDURE check_if_sufficient_funds_for_transaction();


