drop sequence if exists public.transaction_id_seq cascade ;

CREATE SEQUENCE public.transaction_id_seq;
ALTER TABLE public.transactions
    alter column id set default nextval('public.transaction_id_seq');
SELECT setval('public.transaction_id_seq', COALESCE((SELECT MAX(id)+1 FROM public.transactions), 1), false);

ALTER TABLE public.transactions
    ALTER COLUMN id SET NOT NULL;
