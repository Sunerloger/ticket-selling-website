INSERT INTO event (id, title, start_time, cityname, area_code, duration, category, address, description, image)
VALUES ('-1', 'Large concert hall'),
       ('-2', 'Intimate theater');

INSERT INTO news (id, title, abbreviated_text, full_text, created_on_timestamp, cover_image, event_id)
VALUES ('-1', 'Hall A', 'Large concert hall'),
       ('-2', 'Hall B', 'Intimate theater');

INSERT INTO newsImages (id, image_data, news_id)
VALUES ('Hall A', 'Large concert hall', '-1'),
       ('Hall B', 'Intimate theater', '-1');

-- TODO: insert data