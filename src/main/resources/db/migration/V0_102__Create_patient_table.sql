create table if not exists "patient" (
    id varchar primary key not null,
    first_name varchar,
    last_name varchar,
    email varchar,
    birthdate timestamp with time zone,
    authentication_id varchar not null,
    photo_id varchar,
    doctor_id varchar references "doctor"(id),
    nic varchar,
    role role
);