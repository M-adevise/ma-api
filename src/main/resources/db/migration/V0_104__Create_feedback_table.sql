create table if not exists "feedback" (
    id varchar primary key not null,
    comment varchar,
    sender varchar references "patient"(id),
    receiver varchar references "doctor"(id),
    score integer,
    creation_datetime timestamp with time zone
);