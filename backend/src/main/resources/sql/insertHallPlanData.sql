INSERT INTO hallplan (name, description)
VALUES ('Hall A', 'Large concert hall'),
       ('Hall B', 'Intimate theater'),
       ('Hall C', 'Open-air amphitheater'),
       ('Hall D', 'Conference center'),
       ('Hall E', 'Sports arena'),
       ('Hall F', 'Small music venue'),
       ('Hall G', 'Cinema complex'),
       ('Hall H', 'Museum exhibition space');

INSERT INTO seatrow (row_nr, hallplan_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (1, 4);

INSERT INTO section (name, color, price)
VALUES ('VIP', 'gold', 100),
       ('Front Row', 'red', 80),
       ('Middle Section', 'green', 60),
       ('Back Section', 'blue', 40),
       ('Standing Area', 'gray', 20),
       ('Balcony', 'purple', 70),
       ('Family Section', 'orange', 50),
       ('Disabled Section', 'pink', 30);

INSERT INTO seat (status, type, capacity, seat_nr, seatrow_id, section_id)
VALUES ('FREE', 'SEAT', 1, 1, 1, 1),
       ('FREE', 'SEAT', 1, 2, 1, 1),
       ('FREE', 'SEAT', 1, 3, 1, 2),
       ('OCCUPIED', 'SEAT', 1, 4, 1, 2),
       ('FREE', 'VACANT_SEAT', 1, 1, 2, 3),
       ('FREE', 'STANDING_SEAT', 1, 1, 3, 4),
       ('RESERVED', 'SEAT', 2, 2, 1, 1),
       ('FREE', 'SEAT', 1, 3, 2, 3);


