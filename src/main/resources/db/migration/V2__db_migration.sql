create table account_table_migrate
(
    account_id   bigserial not null
        constraint account_table_pk_migrate
            primary key,
    client_id    bigint    not null
        constraint fkb453v9qc79nsmniqggyvwrj5i
            references user_table,
    account_num  bigint    not null,
    account_type text      not null,
    balance      numeric   not null
);

alter table account_table_migrate
    owner to tfpxkvffnwfdmw;

create unique index account_table_account_id_uindex_migrate
    on account_table_migrate (account_id);

create unique index account_table_account_num_uindex_migrate
    on account_table_migrate (account_num);

create table payment_table_migrate
(
    payment_id    bigserial not null
        constraint payment_table_pk_migrate
            primary key,
    amount        numeric   not null,
    reason        text,
    timestamp     timestamp not null,
    source_acc_id bigint    not null,
    dest_acc_id   bigint    not null,
    status        varchar(20) default 'NEW'::character varying
);

alter table payment_table_migrate
    owner to tfpxkvffnwfdmw;

create unique index payment_table_payment_id_uindex_migrate
    on payment_table_migrate (payment_id);

create table user_table_migrate
(
    client_id bigserial not null
        constraint user_table_pk_migrate
            primary key,
    firstname text      not null,
    lastname  text      not null
);

alter table user_table_migrate
    owner to tfpxkvffnwfdmw;

create unique index user_table_client_id_uindex_migrate
    on user_table_migrate (client_id);

