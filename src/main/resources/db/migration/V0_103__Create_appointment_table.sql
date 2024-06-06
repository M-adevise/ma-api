create table if not exists "appointment" (
    id varchar primary key not null,
    summary varchar,
    beginning timestamp with time zone,
    ending timestamp with time zone,
    creation_datetime timestamp with time zone,
    organizer varchar references "doctor"(id),
    participant varchar references "patient"(id)
);