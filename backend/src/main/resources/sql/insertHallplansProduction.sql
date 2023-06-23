INSERT INTO hallplan (id, name, description, is_template)
VALUES (-1, 'Hall A', 'Large concert hall', true),
       (-2, 'Hall B', 'Intimate theater', true),
       (-3, 'Hall C', 'Open-air amphitheater', true),
       (-4, 'Hall D', 'Conference center', true),
       (-5, 'Hall E', 'Sports arena', true),
       (-6, 'Hall F', 'Small music venue', false),
       (-7, 'Hall G', 'Cinema complex', false),
       (-8, 'Hall H', 'Museum exhibition space', true);

INSERT INTO seatrow (id, row_nr, hallplan_id)
VALUES (-1, 1, -1);

INSERT INTO section (id, name, color, price, hallplan_id)
VALUES (-1, 'VIP', 'gold', 100, -1),
       (-2, 'Front Row', 'red', 80, -1),
       (-3, 'Middle Section', 'green', 60, -1),
       (-4, 'Back Section', 'blue', 40, -1),
       (-5, 'Standing Area', 'gray', 20, -1),
       (-6, 'Balcony', 'purple', 70, -1),
       (-7, 'Family Section', 'orange', 50, -1),
       (-8, 'Disabled Section', 'pink', 30, -1);

INSERT INTO seat (id, status, type, capacity, order_nr, seat_nr, seatrow_id, section_id, bought_nr, reserved_nr)
VALUES
    (-1, 'FREE', 'SEAT', 1, 1, 1, -1, -1, 0, 0);




