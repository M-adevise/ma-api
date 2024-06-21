create table if not exists "message" (
    id varchar primary key not null,
    sender_id varchar not null,
    receiver_id varchar not null,
    channel_id varchar references "channel"(id),
    content varchar,
    attachment text
);