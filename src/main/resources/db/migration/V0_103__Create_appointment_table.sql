create table if not exists "appointment" (
    id varchar primary key not null,
    summary varchar,
    "from" timestamp with time zone,
    "to" timestamp with time zone,
    creation_datetime timestamp with time zone,
    organizer varchar references "doctor"(id),
    participant varchar references "patient"(id)
);