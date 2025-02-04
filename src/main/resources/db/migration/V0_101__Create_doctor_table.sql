do
$$
    begin
        if not exists (select from pg_type where typname = 'role') then
            create type role as ENUM ('ADMIN', 'DOCTOR', 'PATIENT');
        end if;
    end
$$;

do
$$
    begin
        if not exists (select from pg_type where typname = 'sex') then
            create type sex as ENUM ('MALE', 'FEMININE');
        end if;
    end
$$;

create table if not exists "doctor" (
    id varchar primary key not null,
    first_name varchar,
    last_name varchar,
    email varchar,
    birthdate timestamp with time zone,
    authentication_id varchar,
    department_id varchar,
    photo_id varchar,
    registry_number varchar,
    nic varchar,
    sex sex,
    contact varchar,
    country varchar,
    city varchar,
    address varchar,
    branch varchar,
    role role
);