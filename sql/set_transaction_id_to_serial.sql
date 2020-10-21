CREATE SEQUENCE transaction_id_seq;

ALTER TABLE transactions
    alter column id set default nextval('transaction_id_seq');
ALTER TABLE transactions
    ALTER COLUMN id SET NOT NULL;

ALTER SEQUENCE transaction_id_seq
OWNED BY transactions.id;
