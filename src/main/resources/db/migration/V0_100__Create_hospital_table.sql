create table if not exists "hospital" (
    id varchar primary key,
    name varchar,
    nif varchar,
    stat varchar,
    contact varchar,
    advisor jsonb
);