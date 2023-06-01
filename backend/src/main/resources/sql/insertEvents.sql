INSERT INTO Event (id, title, duration, category, description, image, artist)
VALUES (-1, 'Example Event', '02:00', 'Example Category', 'This is an example event description.', 'base64-encoded-image', 'John');

INSERT INTO Event (id, title, duration, category, description, image, artist)
VALUES (-2, 'Rock Night', '03:30', 'Rock', 'Join us for a night of electrifying rock music!', 'base64-encoded-image', 'The Rockers');

INSERT INTO Event_Date (id, date, starting_time, area_code, address, city, hallplan_id, event_id)
VALUES (-1, '2023-05-21', '02:15', 12345, '123 Example Street', 'Example City', -2, -1);

INSERT INTO Event_Date (id, date, starting_time, area_code, address, city, hallplan_id, event_id)
VALUES (-2, '2023-05-22', '02:15', 54321, '404 Example Street', 'Sampleville', -3, -1);

INSERT INTO Event_Date (id, date, starting_time, area_code, address, city, hallplan_id, event_id)
VALUES (-3, '2023-05-21', '02:15', 12345, '123 Example Street', 'Example City', -1, -1);




INSERT INTO event (id, title, artist, category, description, duration, image)
VALUES  (-101, 'Exciting Music Festival', 'Various Artists', 'Music', 'Join us for a weekend filled with music, food, and fun. The festival will feature top artists from around the world. Get your tickets now!', '02:30:00', NULL),
        (-102, 'Book Launch Event', 'G R R Martin', 'Literature', 'Join us for the launch of a highly anticipated book by a renowned author. Enjoy an evening of readings, discussions, and book signings. Limited seats available!', '01:30:00', NULL),
        (-103, 'Fashion Show Extravaganza', 'Karl Lagerfeld', 'Fashion', 'Step into the world of haute couture as renowned designers showcase their latest collections. Prepare to be amazed by the creativity and glamour on display!', '03:30:00', NULL),
        (-104, 'Tech Conference 2023', 'Linus Torvalds', 'Technology', 'Discover groundbreaking technologies and insights at our annual tech conference. Engage with industry experts, attend informative sessions, and network with like-minded professionals.', '03:00:00', NULL),
        (-105, 'Dance Workshop Series', 'Martha Graham Dance Company', 'Dance', 'Calling all dance enthusiasts! Join our workshop series and learn various dance styles from professional instructors. No experience required. Lets dance!', '00:30:00', NULL),
        (-106, 'Comedy Night', 'Trevor Noah', 'Comedy', 'Get ready to laugh out loud as talented comedians take the stage. Sit back, relax, and enjoy a night of hilarious stand-up comedy. Dont miss this comedy extravaganza!', '01:30:00', NULL),
        (-107, 'Fitness Expo', 'Joe Wicks', 'Fitness', 'Join us at the fitness expo to discover the latest trends in fitness, attend workout sessions, and gain valuable insights from industry professionals. Your journey to a healthier lifestyle starts here!', '01:30:00', NULL),
        (-108, 'Theater Performance: Romeo and Juliet', 'Globe Theater Group', 'Theater', 'Witness a captivating rendition of Shakespeares Romeo and Juliet performed by a talented cast. Experience the timeless magic of this iconic play!', '03:30:00', NULL),
        (-109, 'Technology Exhibition', 'Oculus', 'Technology', 'Experience the latest technological advancements at our exhibition. From AI to robotics, explore innovations that are shaping our future. Get ready to be amazed!', '02:30:00', NULL),
        (-110, 'Film Festival', 'Martin Scorsese', 'Film', 'Experience the magic of cinema at our annual film festival. From independent films to international hits, this is an event that cinephiles wont want to miss!', '01:00:00', NULL),
        (-111, 'Concert: Symphony Orchestra', 'Franz Welser-Moest', 'Music', 'Be mesmerized by the enchanting melodies performed by a world-class symphony orchestra. Prepare to be transported to a realm of beauty and emotion!', '03:30:00', NULL),
        (-112, 'Photography Exhibition', 'Annie Leibovitz', 'Art', 'Explore the art of photography at our exhibition featuring stunning images by talented photographers. Discover the stories behind each photograph!', '02:30:00', NULL),
        (-113, 'Health and Wellness Expo', 'Dr Sanjay Gupta', 'Health', 'Discover the latest trends in health and wellness at our expo. From fitness workshops to mindfulness sessions, explore ways to lead a balanced and healthy lifestyle.', '01:30:00', NULL),
        (-114, 'Theater Performance: Hamlet', 'Benedict Cumberbatch', 'Theater', 'Experience the gripping tale of Hamlet performed by a talented cast. Witness the complexities of human nature in this Shakespearean masterpiece!', '01:00:00', NULL),
        (-115, 'Science Fiction Convention', 'Neil Gaiman', 'Science Fiction', 'Immerse yourself in the world of science fiction at our convention. From panel discussions to cosplay contests, embrace your inner geek!', '01:00:00', NULL),
        (-116, 'Live Music Night: Jazz & Blues', 'Yoko Kanno', 'Music', 'Experience an evening of soulful jazz and blues music performed by talented musicians. Let the music transport you to a world of emotions!', '03:30:00', NULL),
        (-117, 'Technology Conference', 'Sundar Pichai', 'Technology', 'Join us at our technology conference to explore emerging trends and innovations. Engage with industry experts, attend insightful sessions, and get inspired!', '02:30:00', NULL),
        (-118, 'Outdoor Adventure Expo', 'Bear Grylls', 'Outdoor', 'Discover the thrill of outdoor adventures at our expo. From rock climbing to kayaking, get inspired to embark on your next adrenaline-pumping escapade!', '01:30:00', NULL);