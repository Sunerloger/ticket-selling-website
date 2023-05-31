DELETE FROM news_read WHERE 1=1;
DELETE FROM news_image WHERE 1=1;
DELETE FROM news WHERE 1=1;
DELETE FROM event WHERE 1=1;

INSERT INTO event (id, title, artist, category, description, duration, image)
VALUES  (-1, 'Exciting Music Festival', 'Various Artists', 'Music', 'Join us for a weekend filled with music, food, and fun. The festival will feature top artists from around the world. Get your tickets now!', NULL, 'festival_cover_image.jpg'),
        (-2, 'Book Launch Event', 'G R R Martin', 'Literature', 'Join us for the launch of a highly anticipated book by a renowned author. Enjoy an evening of readings, discussions, and book signings. Limited seats available!', NULL, 'book_launch_cover_image.jpg'),
        (-3, 'Fashion Show Extravaganza', 'Karl Lagerfeld', 'Fashion', 'Step into the world of haute couture as renowned designers showcase their latest collections. Prepare to be amazed by the creativity and glamour on display!', NULL, 'fashion_show_cover_image.jpg'),
        (-4, 'Tech Conference 2023', 'Linus Torvalds', 'Technology', 'Discover groundbreaking technologies and insights at our annual tech conference. Engage with industry experts, attend informative sessions, and network with like-minded professionals.', NULL, 'tech_conference_cover_image.jpg'),
        (-5, 'Dance Workshop Series', 'Martha Graham Dance Company', 'Dance', 'Calling all dance enthusiasts! Join our workshop series and learn various dance styles from professional instructors. No experience required. Lets dance!', NULL, 'dance_workshop_cover_image.jpg'),
        (-6, 'Comedy Night', 'Trevor Noah', 'Comedy', 'Get ready to laugh out loud as talented comedians take the stage. Sit back, relax, and enjoy a night of hilarious stand-up comedy. Dont miss this comedy extravaganza!', NULL, 'comedy_night_cover_image.jpg'),
        (-7, 'Fitness Expo', 'Joe Wicks', 'Fitness', 'Join us at the fitness expo to discover the latest trends in fitness, attend workout sessions, and gain valuable insights from industry professionals. Your journey to a healthier lifestyle starts here!', NULL, 'fitness_expo_cover_image.jpg'),
        (-8, 'Theater Performance: Romeo and Juliet', 'Globe Theater Group', 'Theater', 'Witness a captivating rendition of Shakespeares Romeo and Juliet performed by a talented cast. Experience the timeless magic of this iconic play!', NULL, 'romeo_and_juliet_cover_image.jpg'),
        (-9, 'Technology Exhibition', 'Oculus', 'Technology', 'Experience the latest technological advancements at our exhibition. From AI to robotics, explore innovations that are shaping our future. Get ready to be amazed!', NULL, 'technology_exhibition_cover_image.jpg'),
        (-10, 'Film Festival', 'Martin Scorsese', 'Film', 'Experience the magic of cinema at our annual film festival. From independent films to international hits, this is an event that cinephiles wont want to miss!', NULL, 'film_festival_cover_image.jpg'),
        (-11, 'Concert: Symphony Orchestra', 'Franz Welser-Moest', 'Music', 'Be mesmerized by the enchanting melodies performed by a world-class symphony orchestra. Prepare to be transported to a realm of beauty and emotion!', NULL, 'symphony_orchestra_cover_image.jpg'),
        (-12, 'Photography Exhibition', 'Annie Leibovitz', 'Art', 'Explore the art of photography at our exhibition featuring stunning images by talented photographers. Discover the stories behind each photograph!', NULL, 'photography_exhibition_cover_image.jpg'),
        (-13, 'Health and Wellness Expo', 'Dr Sanjay Gupta', 'Health', 'Discover the latest trends in health and wellness at our expo. From fitness workshops to mindfulness sessions, explore ways to lead a balanced and healthy lifestyle.', NULL, 'health_wellness_expo_cover_image.jpg'),
        (-14, 'Theater Performance: Hamlet', 'Benedict Cumberbatch', 'Theater', 'Experience the gripping tale of Hamlet performed by a talented cast. Witness the complexities of human nature in this Shakespearean masterpiece!', NULL, 'hamlet_cover_image.jpg'),
        (-15, 'Science Fiction Convention', 'Neil Gaiman', 'Science Fiction', 'Immerse yourself in the world of science fiction at our convention. From panel discussions to cosplay contests, embrace your inner geek!', NULL, 'science_fiction_convention_cover_image.jpg'),
        (-16, 'Live Music Night: Jazz & Blues', 'Yoko Kanno', 'Music', 'Experience an evening of soulful jazz and blues music performed by talented musicians. Let the music transport you to a world of emotions!', NULL, 'jazz_blues_cover_image.jpg'),
        (-17, 'Technology Conference', 'Sundar Pichai', 'Technology', 'Join us at our technology conference to explore emerging trends and innovations. Engage with industry experts, attend insightful sessions, and get inspired!', NULL, 'technology_conference_cover_image.jpg'),
        (-18, 'Outdoor Adventure Expo', 'Bear Grylls', 'Outdoor', 'Discover the thrill of outdoor adventures at our expo. From rock climbing to kayaking, get inspired to embark on your next adrenaline-pumping escapade!', NULL, 'outdoor_adventure_expo_cover_image.jpg');

INSERT INTO news (id, title, abbreviated_text, full_text, created_on_timestamp, event_id, cover_image)
VALUES  (-1,'Exciting Music Festival', 'Dont miss this amazing festival!', 'Join us for a weekend filled with music, food, and fun. The festival will feature top artists from around the world. Get your tickets now!', '2023-05-01 09:00:00', -1, 'festival_cover_image.jpg'),
        (-2, 'Art Exhibition Opening', 'Discover captivating artworks', 'We are pleased to announce the opening of our new art exhibition. Come and explore the works of talented artists in various mediums. Admission is free!', '2023-05-02 14:30:00', NULL, 'art_exhibition_cover_image.jpg'),
        (-3, 'Book Launch Event', 'Meet the author and get your signed copy', 'Join us for the launch of a highly anticipated book by a renowned author. Enjoy an evening of readings, discussions, and book signings. Limited seats available!', '2023-05-03 18:15:00', -2, 'book_launch_cover_image.jpg'),
        (-4, 'Movie Premiere Night', 'Be the first to see the latest blockbuster', 'Experience the thrill of being among the first to watch the highly anticipated movie. Get ready for a night of excitement, entertainment, and surprises!', '2023-05-04 20:00:00', NULL, 'movie_premiere_cover_image.jpg'),
        (-5, 'Fashion Show Extravaganza', 'Witness stunning designs on the runway', 'Step into the world of haute couture as renowned designers showcase their latest collections. Prepare to be amazed by the creativity and glamour on display!', '2023-05-05 19:30:00', -3, 'fashion_show_cover_image.jpg'),
        (-6, 'Tech Conference 2023', 'Stay ahead with the latest innovations', 'Discover groundbreaking technologies and insights at our annual tech conference. Engage with industry experts, attend informative sessions, and network with like-minded professionals.', '2023-05-06 10:00:00', -4, 'tech_conference_cover_image.jpg'),
        (-7, 'Charity Gala Dinner', 'Support a worthy cause', 'Join us for an elegant evening of fine dining and entertainment. All proceeds from the event will go towards supporting a charitable organization. Your presence can make a difference!', '2023-05-07 19:00:00', NULL, 'charity_gala_cover_image.jpg'),
        (-8, 'Dance Workshop Series', 'Learn new dance moves', 'Calling all dance enthusiasts! Join our workshop series and learn various dance styles from professional instructors. No experience required. Lets dance!', '2023-05-08 15:30:00', -5, 'dance_workshop_cover_image.jpg'),
        (-9, 'Science Exhibition', 'Explore the wonders of science', 'Embark on a journey of scientific discovery at our interactive exhibition. Engage in hands-on experiments, demonstrations, and learn about the fascinating world of science!', '2023-05-09 11:00:00', NULL, 'science_exhibition_cover_image.jpg'),
        (-10, 'Comedy Night', 'Prepare for laughter-filled evening', 'Get ready to laugh out loud as talented comedians take the stage. Sit back, relax, and enjoy a night of hilarious stand-up comedy. Dont miss this comedy extravaganza!', '2023-05-10 20:30:00', -6, 'comedy_night_cover_image.jpg'),
        (-11, 'Food Festival', 'Savor the flavors', 'Indulge in a culinary adventure at our food festival. Explore a variety of cuisines, sample delectable dishes, and experience a gastronomic delight!', '2023-05-11 12:00:00', NULL, 'food_festival_cover_image.jpg'),
        (-12, 'Fitness Expo', 'Get fit and stay healthy', 'Join us at the fitness expo to discover the latest trends in fitness, attend workout sessions, and gain valuable insights from industry professionals. Your journey to a healthier lifestyle starts here!', '2023-05-12 09:00:00', -7, 'fitness_expo_cover_image.jpg'),
        (-13, 'Theater Performance: Romeo and Juliet', 'A timeless tale of love and tragedy', 'Witness a captivating rendition of Shakespeares Romeo and Juliet performed by a talented cast. Experience the timeless magic of this iconic play!', '2023-05-13 19:30:00', -8, 'romeo_and_juliet_cover_image.jpg'),
        (-14, 'Art Workshop: Painting Masterclass', 'Unleash your inner artist', 'Join our painting masterclass and learn essential techniques from experienced artists. Discover the joy of creating your own masterpiece!', '2023-05-14 14:00:00', NULL, 'painting_masterclass_cover_image.jpg'),
        (-15, 'Technology Exhibition', 'Discover the future', 'Experience the latest technological advancements at our exhibition. From AI to robotics, explore innovations that are shaping our future. Get ready to be amazed!', '2023-05-15 10:00:00', -9, 'technology_exhibition_cover_image.jpg'),
        (-16, 'Fashion Week', 'Celebrate style and creativity', 'Immerse yourself in the world of fashion as designers showcase their cutting-edge collections. From runway shows to exclusive events, this is a week you wont want to miss!', '2023-05-16 18:30:00', NULL, 'fashion_week_cover_image.jpg'),
        (-17, 'Film Festival', 'Celebrate the art of cinema', 'Experience the magic of cinema at our annual film festival. From independent films to international hits, this is an event that cinephiles wont want to miss!', '2023-05-17 11:00:00', -10, 'film_festival_cover_image.jpg'),
        (-18, 'Concert: Symphony Orchestra', 'An enchanting musical experience', 'Be mesmerized by the enchanting melodies performed by a world-class symphony orchestra. Prepare to be transported to a realm of beauty and emotion!', '2023-05-18 20:00:00', -11, 'symphony_orchestra_cover_image.jpg'),
        (-19, 'Business Summit', 'Unlock your business potential', 'Join industry leaders and entrepreneurs at our business summit. Gain valuable insights, network with like-minded professionals, and take your business to new heights!', '2023-05-19 09:30:00', NULL, 'business_summit_cover_image.jpg'),
        (-20, 'Photography Exhibition', 'Capture moments frozen in time', 'Explore the art of photography at our exhibition featuring stunning images by talented photographers. Discover the stories behind each photograph!', '2023-05-20 12:00:00', -12, 'photography_exhibition_cover_image.jpg'),
        (-21, 'Gaming Tournament', 'Test your gaming skills', 'Compete against fellow gamers in our exciting gaming tournament. Show off your skills, win prizes, and experience the thrill of multiplayer gaming!', '2023-05-21 16:00:00', NULL, 'gaming_tournament_cover_image.jpg'),
        (-22, 'Health and Wellness Expo', 'Nurture your body and mind', 'Discover the latest trends in health and wellness at our expo. From fitness workshops to mindfulness sessions, explore ways to lead a balanced and healthy lifestyle.', '2023-05-22 11:30:00', -13, 'health_wellness_expo_cover_image.jpg'),
        (-23, 'Theater Performance: Hamlet', 'A tale of revenge and tragedy', 'Experience the gripping tale of Hamlet performed by a talented cast. Witness the complexities of human nature in this Shakespearean masterpiece!', '2023-05-23 19:00:00', -14, 'hamlet_cover_image.jpg'),
        (-24, 'Food and Wine Pairing Masterclass', 'Elevate your dining experience', 'Join our food and wine pairing masterclass and learn the art of creating harmonious flavor combinations. Indulge in a sensory journey like no other!', '2023-05-24 17:30:00', NULL, 'food_wine_pairing_cover_image.jpg'),
        (-25, 'Science Fiction Convention', 'Explore the realms of imagination', 'Immerse yourself in the world of science fiction at our convention. From panel discussions to cosplay contests, embrace your inner geek!', '2023-05-25 10:00:00', -15, 'science_fiction_convention_cover_image.jpg'),
        (-26, 'Live Music Night: Jazz & Blues', 'Soulful melodies and infectious rhythms', 'Experience an evening of soulful jazz and blues music performed by talented musicians. Let the music transport you to a world of emotions!', '2023-05-26 21:00:00', -16, 'jazz_blues_cover_image.jpg'),
        (-27, 'Art Workshop: Sculpting', 'Shape your imagination', 'Unleash your creativity in our sculpting workshop. Learn techniques to create beautiful sculptures and bring your ideas to life!', '2023-05-27 15:00:00', NULL, 'sculpting_workshop_cover_image.jpg'),
        (-28, 'Technology Conference', 'Embrace the future', 'Join us at our technology conference to explore emerging trends and innovations. Engage with industry experts, attend insightful sessions, and get inspired!', '2023-05-28 09:30:00', -17, 'technology_conference_cover_image.jpg'),
        (-29, 'Literary Festival', 'Celebrate the written word', 'Immerse yourself in the world of literature at our festival. From author readings to book signings, this is an event for book lovers and aspiring writers!', '2023-05-29 12:00:00', NULL, 'literary_festival_cover_image.jpg'),
        (-30, 'Outdoor Adventure Expo', 'Explore the great outdoors', 'Discover the thrill of outdoor adventures at our expo. From rock climbing to kayaking, get inspired to embark on your next adrenaline-pumping escapade!', '2023-05-30 10:00:00', -18, 'outdoor_adventure_expo_cover_image.jpg'),
        (-31, 'Exciting Movie Premiere', 'Don''t miss the highly anticipated movie premiere!', 'Join us for the thrilling premiere of the latest blockbuster!', '2023-05-01 10:00:00', NULL, NULL),
        (-32, 'Live Concert: Rock the Night', 'Experience an electrifying rock concert like never before!', 'Get ready to rock the night away with your favorite bands!', '2023-05-05 19:30:00', NULL, NULL),
        (-33, 'Classic Movie Marathon', 'Relive the golden era of cinema with our classic movie marathon!', 'Enjoy a day filled with timeless movies and nostalgic memories.', '2023-05-10 14:00:00', NULL, NULL),
        (-34, 'Jazz Night: Smooth Sounds and Soulful Melodies', 'Indulge in a mesmerizing evening of jazz music', 'Let the enchanting melodies and smooth sounds transport you to a world of jazz.', '2023-05-12 20:00:00', NULL, NULL),
        (-35, 'Comedy Show: Laugh Your Heart Out!', 'Get ready for a night of laughter and fun!', 'Join us for a hilarious comedy show guaranteed to make you laugh till your sides hurt.', '2023-05-15 19:00:00', NULL, NULL),
        (-36, 'Outdoor Movie Night', 'Enjoy a movie under the stars in our outdoor cinema!', 'Bring your blankets and snacks for a cozy movie night under the open sky.', '2023-05-18 21:00:00', NULL, NULL),
        (-37, 'Pop Music Extravaganza', 'Dance to the latest hits at our pop music extravaganza!', 'Get ready for a high-energy night filled with the hottest pop songs and electrifying performances.', '2023-05-20 18:30:00', NULL, NULL),
        (-38, 'Horror Movie Marathon: Thrills and Chills', 'Experience a spine-chilling horror movie marathon!', 'Prepare yourself for a night of suspense, scares, and thrilling cinematic experiences.', '2023-05-22 20:30:00', NULL, NULL),
        (-39, 'Symphony Orchestra Performance', 'Immerse yourself in the enchanting melodies of a symphony orchestra.', 'Witness the power and beauty of classical music performed by talented musicians.', '2023-05-25 19:00:00', NULL, NULL),
        (-40, 'Sci-Fi Movie Spectacular', 'Embark on an extraordinary journey through the realm of science fiction!', 'Experience the magic of futuristic worlds and mind-bending adventures.', '2023-05-28 16:15:00', NULL, NULL),
        (-41, 'Country Music Festival', 'Get your cowboy boots ready for a weekend of country music!', 'Join us for a lively celebration of country music with renowned artists and toe-tapping beats.', '2023-06-01 17:30:00', NULL, NULL),
        (-42, 'Dance Performance: Rhythm and Grace', 'Witness a mesmerizing dance performance showcasing rhythm and grace.', 'Be captivated by the beauty and precision of talented dancers as they bring stories to life through movement.', '2023-06-05 19:00:00', NULL, NULL),
        (-43, 'Romantic Movie Night: Love in the Air', 'Celebrate love with a special romantic movie night!', 'Indulge in heartwarming stories and memorable moments that will make you believe in love.', '2023-06-08 20:45:00', NULL, NULL),
        (-44, 'Acoustic Music Showcase', 'Experience the raw beauty of acoustic music in an intimate setting.', 'Let the soulful melodies and heartfelt lyrics of acoustic performers touch your heart.', '2023-06-12 18:30:00', NULL, NULL),
        (-45, 'Action Movie Marathon: Explosive Thrills', 'Buckle up for an adrenaline-pumping action movie marathon!', 'Witness epic stunts, intense chase sequences, and explosive thrills on the big screen.', '2023-06-15 21:00:00', NULL, NULL),
        (-46, 'Opera Performance: A Night of Elegance', 'Immerse yourself in the world of opera with a captivating performance.', 'Experience the grandeur and passion of operatic voices as they bring timeless stories to life.', '2023-06-19 19:30:00', NULL, NULL),
        (-47, 'Indie Film Festival', 'Discover unique and thought-provoking films at our indie film festival.', 'Explore the creativity and artistic vision of independent filmmakers from around the world.', '2023-06-22 14:00:00', NULL, NULL),
        (-48, 'Reggae Night: Groove to the Rhythm', 'Feel the laid-back vibes of reggae music at our special event.', 'Let the infectious beats and positive energy of reggae transport you to a tropical paradise.', '2023-06-25 20:00:00', NULL, NULL),
        (-49, 'Movie Sing-Along: Sing Your Heart Out!', 'Join us for a fun-filled movie sing-along experience!', 'Unleash your inner superstar as you sing along to your favorite movie musicals.', '2023-06-29 18:00:00', NULL, NULL),
        (-50, 'Orchestral Movie Soundtracks: A Musical Journey', 'Experience the magic of iconic movie soundtracks performed by a live orchestra.', 'Let the music take you on a cinematic journey filled with emotion and nostalgia.', '2023-07-03 19:30:00', NULL, NULL);

// INSERT INTO news_image (news_id, image_data)
// VALUES ;