CREATE OR REPLACE FUNCTION check_email()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(select email from customers c WHERE c.email = NEW.email)
    THEN
        raise notice 'email already exists';
        return NULL;
    END IF;
    return NEW;
end;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER check_if_email_exists
    BEFORE UPDATE
    on customers
    FOR EACH ROW
EXECUTE PROCEDURE check_email();


--Drop trigger check_if_email_exists on customers;
