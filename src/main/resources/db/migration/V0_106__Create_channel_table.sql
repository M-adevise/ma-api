create table if not exists "channel" (
    id varchar primary key not null,
    creator varchar not null,
    invited varchar not null
);