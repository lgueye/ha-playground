insert into example_events_timeline (event_id, state, inserted_at, timestamp, sensor_bid) values ('94b8547a-6628-46d5-abcc-8bdb6d39488a', 'on', 1562591903000, 1562591903000, 'sens-q7ikjxk1ftik');
insert into example_events_timeline (event_id, state, inserted_at, timestamp, sensor_bid) values ('8348cc5e-82b5-4254-9f51-e30e022efb8a', 'off', 1562591904000, 1562591904000, 'sens-q7ikjxk1ftik');
insert into example_events_timeline (event_id, state, inserted_at, timestamp, sensor_bid) values ('5b68570d-7593-4310-8ad1-3fee88424ff8', 'on', 1562591905000, 1562591905000, 'sens-q7ikjxk1ftik');
insert into example_events_timeline (event_id, state, inserted_at, timestamp, sensor_bid) values ('47abd6c3-bdff-46dc-877d-8f5284659a0a', 'off', 1562591906000, 1562591906000, 'sens-q7ikjxk1ftik');

select * from example_events_timeline where sensor_bid = 'sens-q7ikjxk1ftik' state = 'off';

update example_events_timeline set state = 'on' where state = 'off';

delete from example_events_timeline;
