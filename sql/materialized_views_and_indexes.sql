create materialized view customer_transactions1000 as
select substr(t.date::text, 0, 11)                                                       as transaction_date,
       (t.amount / 100)::text || ',' || lpad(abs(t.amount % 100)::text, 2, '0') || ' zł' as amount,
       tt.name                                                                           as transaction_type
from customers c
         join accounts a on c.id = a.customer_id
         join transactions t on a.id = t.account_id
         join transaction_types tt on tt.id = t.transaction_type_id
where amount = 1000;

-- select * from customer_transactions1000;

create index customer_transactions1000_amount_idx on customer_transactions1000 (amount, transaction_date, transaction_type);
create index accounts_customer_id_idx on accounts (customer_id);
create index transactions_account_id_idx on transactions (account_id);
create index transactions_date_idx on transactions (date);
create index transaction_types_id_idx on transaction_types (id);
