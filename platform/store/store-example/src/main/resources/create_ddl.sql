drop table if exists example_events_timeline cascade;

create table example_events_timeline (
  event_id    varchar(255) not null,
  state       varchar(50)  not null,
  inserted_at bigint       not null,
  timestamp   bigint       not null,
  sensor_bid  varchar(255) not null,
  primary key (event_id)
);

create index idx_example_events_timeline_sensor_bid
  on example_events_timeline (sensor_bid);
create index idx_example_events_timeline_timestamp
  on example_events_timeline (timestamp);
alter table example_events_timeline
  add constraint idx_example_events_timeline_uniq unique (sensor_bid, timestamp);
