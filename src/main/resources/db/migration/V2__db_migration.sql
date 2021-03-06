create table IF NOT EXISTS "account_table"
(
    account_id   bigserial not null
        constraint account_table_pk
            primary key,
    client_id    bigint    not null
        constraint fk_account_table_to_user
            references user_table,
    account_num  bigint    not null,
    account_type text      not null,
    balance      numeric   not null
);

alter table account_table
    owner to tfpxkvffnwfdmw;

create unique index account_table_account_id_uindex
    on account_table (account_id);

create unique index account_table_account_num_uindex
    on account_table (account_num);

create table IF NOT EXISTS "payment_table"
(
    payment_id    bigserial not null
        constraint payment_table_pk
            primary key,
    amount        numeric   not null,
    reason        text,
    timestamp     timestamp not null,
    source_acc_id bigint    not null,
    dest_acc_id   bigint    not null,
    status        varchar(20) default 'NEW'::character varying
);

alter table payment_table
    owner to tfpxkvffnwfdmw;

create unique index payment_table_payment_id_uindex
    on payment_table (payment_id);

create table IF NOT EXISTS "user_table"
(
    client_id bigserial not null
        constraint user_table_pk
            primary key,
    firstname text      not null,
    lastname  text      not null
);

alter table user_table
    owner to tfpxkvffnwfdmw;

create unique index user_table_client_id_uindex
    on user_table (client_id);

