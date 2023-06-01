INSERT INTO hallplan (id, name, description)
VALUES (-1, 'Hall A', 'Large concert hall'),
       (-2, 'Hall B', 'Intimate theater'),
       (-3, 'Hall C', 'Open-air amphitheater'),
       (-4, 'Hall D', 'Conference center'),
       (-5, 'Hall E', 'Sports arena'),
       (-6, 'Hall F', 'Small music venue'),
       (-7, 'Hall G', 'Cinema complex'),
       (-8, 'Hall H', 'Museum exhibition space');

INSERT INTO seatrow (id, row_nr, hallplan_id)
VALUES (-1, 1, -1),
       (-2, 2, -1),
       (-3, 3, -1),
       (-4, 4, -1),
       (-5, 1, -2),
       (-6, 2, -2),
       (-7, 3, -3),
       (-8, 1, -4);

INSERT INTO section (id, name, color, price, hallplan_id)
VALUES (-1, 'VIP', 'gold', 100, -1),
       (-2, 'Front Row', 'red', 80, -1),
       (-3, 'Middle Section', 'green', 60, -1),
       (-4, 'Back Section', 'blue', 40, -1),
       (-5, 'Standing Area', 'gray', 20, -1),
       (-6, 'Balcony', 'purple', 70, -1),
       (-7, 'Family Section', 'orange', 50, -1),
       (-8, 'Disabled Section', 'pink', 30, -1);

INSERT INTO seat (id, status, type, capacity, order_nr, seat_nr, seatrow_id, section_id)
VALUES (-1, 'FREE', 'SEAT', 1, 1, 1, -1, -1),
       (-2, 'FREE', 'SEAT', 1, 2, 2, -1, -1),
       (-3, 'FREE', 'SEAT', 1, 3, 3, -1, -2),
       (-4, 'OCCUPIED', 'SEAT', 1, 4, 4, -1, -2),
       (-5, 'FREE', 'VACANT_SEAT', 1, 1, 1, -2, -3),
       (-6, 'FREE', 'STANDING_SEAT', 1, 1, 1, -3, -4),
       (-7, 'RESERVED', 'SEAT', 2, 2, 2, -1, -1),
       (-8, 'FREE', 'SEAT', 1, 3, 3, -2, -3);
