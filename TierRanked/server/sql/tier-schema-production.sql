drop database if exists tier;
create database tier;
use tier;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table category (
    category_id int primary key auto_increment,
    `name` varchar(100) not null
);

create table tier_list (
    tier_list_id int primary key auto_increment,
    `name` varchar(100) not null,
    `description` varchar(500) null,
    `timestamp` timestamp not null,
    s_tier json null,
    a_tier json null,
    b_tier json null,
    c_tier json null,
    d_tier json null,
    e_tier json null,
    f_tier json null,
    upvotes int not null,
    downvotes int not null,
    app_user_id int not null,
    category_id int not null,
    constraint fk_tier_list_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_tier_list_category_id
        foreign key (category_id)
        references category(category_id)    
);

create table display_profile (
    display_profile_id int primary key auto_increment,
    picture varchar(1000) null,
    bio varchar(500) null,
    twitter varchar(100) null,
    instagram varchar(100) null,
    tiktok varchar(100) null,
    username varchar(50) not null unique,
    app_user_id int not null,
    constraint fk_display_profile_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id)
);

create table photo (
    photo_id int primary key auto_increment,
    filepath varchar(500) not null,
    `description` varchar(1000) not null,
    tier_list_id int not null,
    constraint fk_photo_tier_list_id
        foreign key (tier_list_id)
        references tier_list(tier_list_id)
);

create table `comment` (
    comment_id int primary key auto_increment,
    `comment` varchar(1000) not null,
    `timestamp` timestamp not null,
    app_user_id int not null,
    tier_list_id int not null,
    constraint fk_comment_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_comment_tier_list_id
        foreign key (tier_list_id)
        references tier_list(tier_list_id)
);

-- data
insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('emma@wilson.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('alex@thomas.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('lucas@harris.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('olivia@martinez.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('noah@rodriguez.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('mia@lopez.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('liam@lee.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ava@miller.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('logan@taylor.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('isabella@clark.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ethan@mitchell.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('emma@brown.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('william@jackson.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('mia@davis.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('alex@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ed@harris.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('oliver@martin.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ava@harris.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ethan@thompson.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sophia@rodriguez.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('liam@clark.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('isabella@lewis.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('jacob@walker.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('olivia@wright.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('michael@hall.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('emily@young.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('benjamin@morgan.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('harper@cooper.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ken.lott.film@gmail.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('philshou@gmail.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('ipanganiban99@gmail.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 2),
    (2, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 1),
    (18, 1),
    (19, 1),
    (20, 1),
    (21, 1),
    (22, 1),
    (23, 1),
    (24, 1),
    (25, 1),
    (26, 1),
    (27, 1),
    (28, 1),
    (29, 1),
    (30, 1),
    (31, 2),
    (32, 2),
    (33, 2);
    
insert into category (`name`)
    values
    ('Food & Drink'),
    ('Music'),
    ('TV/Movies'),
    ('Sports'),
    ('Video Games');

-- SPORTS
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Lakers Players', 'ITS SHOWTIME', '2023-06-05T15:24:27',
        '["https://cdn.nba.com/manage/2021/08/magic-johnson-iso-archive-1568x882.jpg", "https://media.tenor.com/Alah2Goh5NcAAAAC/kobe-bryant-kobe.gif"]',
        '["https://media.vanityfair.com/photos/625ed168ad9d3f02ae020471/1:1/w_1680,h_1680,c_limit/515125316", "https://s22928.pcdn.co/wp-content/uploads/2018/01/Jerry-West-1.jpg"]',
        '["https://s22928.pcdn.co/wp-content/uploads/2016/01/5608b3a444dc82bd329f2fa89466-e1455556676954.jpeg"]',
        '["https://cdn.nba.com/manage/2021/08/james-worthy-iso.jpg"]',
        '["https://149346005.v2.pressablecdn.com/wp-content/uploads/2021/03/Elgin-Baylor.jpg"]',
        '["https://s22928.pcdn.co/wp-content/uploads/2020/05/pau-gasol-2010-nba-finals-640x430.jpg"]',
        73, 12, 17, 4);

        

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Knicks Players', 'I be Spiked out, I could trip the referee', '2023-07-01T15:24:27',
        '["https://cdn.nba.com/manage/2021/08/ewing-legends-prof-1568x882.jpg", "https://cdn.britannica.com/73/199773-050-F4CA81A6/Willis-Reed-game-finals-Los-Angeles-Lakers-1970.jpg"]',
        '["https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Walt_Frazier_1977.jpeg/220px-Walt_Frazier_1977.jpeg"]',
        '["https://www.basketballnetwork.net/.image/ar_4:3%2Cc_fill%2Ccs_srgb%2Cfl_progressive%2Cq_auto:good%2Cw_1200/MTkwODUwODYwNzQ4NjQ2MDcw/charles-oakley-copy.jpg"]',
        '["https://mn2s-content.s3.eu-west-2.amazonaws.com/wp-content/uploads/2021/08/09182341/Renaldo-Balkman.png"]',
        '["https://theknickswall.com/wp-content/uploads/2019/03/GettyImages-1050887948-1024x682.jpeg"]',
        '["https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_JFIPI8LaDCei4I8peRdZuYgXtBltTm1ri42xN0URKAADFQtNawhF9U95TJJwjY4EWlE&usqp=CAU"]',
        52, 10, 19, 4);


INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Dallas Cowboys Players', 'WE DEM BOYS', '2023-05-27T15:24:27',
        '["https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Staubach_cowboys_qb.jpg/220px-Staubach_cowboys_qb.jpg", "https://cdn.britannica.com/86/197086-050-7FEFA7A6/Emmitt-Smith.jpg", "https://static.clubs.nfl.com/image/private/t_editorial_squared_6_desktop/f_png/nfl100/rqwuft4tfvmcyq96yvxw.png"]',
        '["https://static.clubs.nfl.com/image/private/t_editorial_landscape_mobile/cowboys/i3x1esudfowpalmpoi48.jpg", "https://cdn.vox-cdn.com/thumbor/pLUxRUPNXiWMYJuHlE_zdPa7QW0=/0x0:1280x1114/1400x1400/filters:focal(623x266:827x470):format(jpeg)/cdn.vox-cdn.com/uploads/chorus_image/image/53661977/bob_lilly_dallas_cowboys_file_photos_pg_600.0.jpg"]',
        '["https://www.si.com/.image/t_share/MTc5MzY2ODE4Mjc4ODExNTg4/ware-.jpg"]',
        '["https://www.profootballhof.com/pfhof/media/Default/Items/White_Randy_Action_180x220.jpg?ext=.jpg"]',
        '["https://img.bleacherreport.net/img/images/photos/002/427/208/hi-res-55726299_crop_north.jpg?1375389114&w=3072&h=2048"]',
        '["https://s3media.247sports.com/Uploads/Assets/416/957/5957416.JPG"]',
        68, 15, 7, 4);


INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Greatest Female College NCAA Players of All Time', 'CHAMS is the GOAT', '2023-07-05T15:24:27',
        '["https://www.ncaa.com/_flysystem/public-s3/styles/original/public-s3/images/2020/09/09/CHAMIQUE-HOLDSCLAW_0.png?itok=PYSiSP98", "https://vault.si.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cfl_progressive%2Cq_auto:good%2Cw_1200/MTcyNzE3ODM0OTc0MDEzMjMx/taurasi-uconn-pointing.jpg"]',
        '["https://cdn.vox-cdn.com/thumbor/qg3xVwtVmQ1yovu-J1JI8NMclzA=/0x22:600x422/1200x800/filters:focal(0x22:600x422)/cdn.vox-cdn.com/photo_images/540470/GYI0060082339.jpg", "https://d2779tscntxxsw.cloudfront.net/56967f1beb074.jpeg?width=650&quality=80"]',
        '["https://cdn.vox-cdn.com/thumbor/Au9xalpnoVbAKHn62VyxIRvWziI=/0x449:1648x1548/1200x800/filters:focal(0x449:1648x1548)/cdn.vox-cdn.com/uploads/chorus_image/image/45961346/GettyImages-507753.0.jpg"]',
        '["https://www.sheknows.com/wp-content/uploads/2018/08/91143d555a5a8e0c3e82.jpeg?w=300"]',
        '["https://static1.squarespace.com/static/52ce0ee4e4b0874a2418363b/t/54458f53e4b091dd3f5e5201/1413844819783/"]',
        '["https://bloximages.newyork1.vip.townnews.com/theadvocate.com/content/tncms/assets/v3/editorial/a/a2/aa2ba2a5-a450-532d-92e6-29bea633e766/5c7efbfc610db.image.jpg?resize=348%2C500"]',
        98, 23, 15, 4);


INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Soccer Clubs', 'Dont @ ME!', '2023-07-05T15:24:27',
        '["https://upload.wikimedia.org/wikipedia/en/thumb/5/56/Real_Madrid_CF.svg/1200px-Real_Madrid_CF.svg.png", "https://upload.wikimedia.org/wikipedia/en/thumb/4/47/FC_Barcelona_%28crest%29.svg/1200px-FC_Barcelona_%28crest%29.svg.png", "https://upload.wikimedia.org/wikipedia/en/thumb/7/7a/Manchester_United_FC_crest.svg/640px-Manchester_United_FC_crest.svg.png"]',
        '["https://pbs.twimg.com/profile_images/1675865870705336321/svbI6Npg_400x400.jpg", "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Liverpool_FC.svg/1200px-Liverpool_FC.svg.png"]',
        '["https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Logo_of_AC_Milan.svg/1200px-Logo_of_AC_Milan.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/FC_Internazionale_Milano_2021.svg/800px-FC_Internazionale_Milano_2021.svg.png"]',
        '["https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png"]',
        142, 87, 6, 4);



-- TV/MOVIES
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Controversial Spider-Man Movie Rankings', 'Andrew <3', '2023-06-09T15:24:27',
        '["https://m.media-amazon.com/images/M/MV5BMjMyOTM4MDMxNV5BMl5BanBnXkFtZTcwNjIyNzExOA@@._V1_FMjpg_UX1000_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BOGQ5YTM3YzctOTVmMC00OGIyLWFkZTYtMWYwOWZhMjA2MWMwXkEyXkFqcGdeQXVyMjUyMTE5MA@@._V1_FMjpg_UX1000_.jpg", "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BOTA5NDYxNTg0OV5BMl5BanBnXkFtZTgwODE5NzU1MTE@._V1_.jpg"]',
        '["https://upload.wikimedia.org/wikipedia/en/b/bd/Spider-Man_Far_From_Home_poster.jpg"]',
        '["https://cdn.marvel.com/content/1x/spider-mannowayhome_lob_crd_03.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BNTFkZjdjN2QtOGE5MS00ZTgzLTgxZjAtYzkyZWQ5MjEzYmZjXkEyXkFqcGdeQXVyMTM0NTUzNDIy._V1_.jpg", "https://www.sonypictures.com/sites/default/files/styles/max_560x840/public/title-key-art/morbius_onesheet_1400x2100_he.jpg?itok=-jQVkWIe"]',
        25, 45, 19, 3);



INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier,  f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Ranking Indiana Jones Movies', 'KALI MAAAAAA', '2023-07-05T15:24:27',
        '["https://upload.wikimedia.org/wikipedia/en/a/a6/Raiders_of_the_Lost_Ark_Theatrical_Poster.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BY2Q0ODg4ZmItNDZiYi00ZWY5LTg2NzctNmYwZjA5OThmNzE1XkEyXkFqcGdeQXVyMjM4MzQ4OTQ@._V1_FMjpg_UX1000_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BYzgzMTIzNzctNmNiZC00ZDYyLWJjNzktMmQ2MDM2ZDkwZGVhXkEyXkFqcGdeQXVyMjM4MzQ4OTQ@._V1_FMjpg_UX1000_.jpg"]',
        '["https://upload.wikimedia.org/wikipedia/en/d/d5/Kingdomofthecrystalskull.jpg", "data:image/jpeg;base64,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"]',
        62, 38, 9, 3);


        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best HBO Shows', 'Its not TV, its HBO.', '2023-07-05T15:24:27',
        '["https://m.media-amazon.com/images/M/MV5BN2IzYzBiOTQtNGZmMi00NDI5LTgxMzMtN2EzZjA1NjhlOGMxXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_FMjpg_UX1000_.jpg", "https://m.media-amazon.com/images/M/MV5BZGJjYzhjYTYtMDBjYy00OWU1LTg5OTYtNmYwOTZmZjE3ZDdhXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg", "https://ntvb.tmsimg.com/assets/p7892928_b_h10_ab.jpg?w=960&h=540"]',
        '["https://upload.wikimedia.org/wikipedia/en/a/a7/Westworld_Season_3.jpg", "https://upload.wikimedia.org/wikipedia/en/thumb/a/a7/Chernobyl_2019_Miniseries.jpg/220px-Chernobyl_2019_Miniseries.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BMDVjZDQ2N2MtMzMxYy00ZjliLTg5YjAtNjk1OTUwYjVjNWQ0XkEyXkFqcGdeQXVyNzA5NjUyNjM@._V1_FMjpg_UX1000_.jpg", "https://m.media-amazon.com/images/M/MV5BMTU1Mzk2ODEzN15BMl5BanBnXkFtZTgwNDQwMjAxMTI@._V1_.jpg"]',
        '["https://m.media-amazon.com/images/I/51pv4nDTbFL._AC_UF894,1000_QL80_.jpg"]',
        '["https://resizing.flixster.com/XPqyIzlNE-gIIBK8gHpViO8W7OM=/ems.cHJkLWVtcy1hc3NldHMvdHZzZXJpZXMvUlRUVjE1NTA4OS53ZWJw"]',
        '["https://m.media-amazon.com/images/M/MV5BOWU3ODM4ZTEtNjI5Ni00ODc0LTg3MTctOTNmZDVkNzU5YzA3XkEyXkFqcGdeQXVyNTI2MzI4NTU@._V1_FMjpg_UX1000_.jpg"]',
        110, 21, 13, 3);

        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best 90s Sitcoms', 'Seinfeld > Friends', '2023-07-05T15:24:27',
        '["https://flxt.tmsimg.com/assets/p183875_b_v9_ab.jpg", "https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg"]',
        '["https://static.wikia.nocookie.net/freshprince/images/d/d0/4.jpg/revision/latest?cb=20180624131453", "https://m.media-amazon.com/images/M/MV5BN2VhZjA4ZGMtMTM0ZC00MTIyLWFjMzMtOWI4Y2JjN2IyNmYyXkEyXkFqcGdeQXVyNjc4NTExMTk@._V1_.jpg"]',
        '["https://flxt.tmsimg.com/assets/p7892593_b_v13_aa.jpg", "https://m.media-amazon.com/images/M/MV5BZmM1OTU0ZDgtYzExNC00ODJkLWI2NGMtODgyOGJjMzg3NTBmXkEyXkFqcGdeQXVyNjE5MjUyOTM@._V1_FMjpg_UX1000_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BNzEzMzM2ODc1Ml5BMl5BanBnXkFtZTcwMTg2MjAzMQ@@._V1_FMjpg_UX1000_.jpg", "https://m.media-amazon.com/images/M/MV5BMjExYWU5MmItNjM5Yi00MTRkLTg4MDMtZTNmNmE3OGQ4NjFlXkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg"]',
        95, 17, 8, 3);

        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Nickelodeon Cartoons of All Time', 'I miss nick!', '2023-07-05T15:24:27',
        '["https://m.media-amazon.com/images/M/MV5BNDUwNjBkMmUtZjM2My00NmM4LTlmOWQtNWE5YTdmN2Y2MTgxXkEyXkFqcGdeQXRyYW5zY29kZS13b3JrZmxvdw@@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BNTExYTFhYzEtYmQ4YS00NDZkLWE5YmUtMjdhZjZiYThlODQ1XkEyXkFqcGdeQXVyMTM0NTUzNDIy._V1_FMjpg_UX1000_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BMGU1Zjg4MDctMmVjZi00OWFlLThmMzQtOWNkOGMzZDdiN2EzXkEyXkFqcGdeQXVyODA4OTIyMzY@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BODc5YTBhMTItMjhkNi00ZTIxLWI0YjAtNTZmOTY0YjRlZGQ0XkEyXkFqcGdeQXVyODUwNjEzMzg@._V1_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BNzUyOGEyMDQtOWVkMS00NGFkLTgxNWQtOWJiZTE0OTY3NjE2XkEyXkFqcGdeQXVyODA4OTIyMzY@._V1_QL75_UX190_CR0,2,190,281_.jpg"]',
        '["https://m.media-amazon.com/images/M/MV5BNjZjYjY2MmUtMDBiMC00NzgyLTlkNDYtMWE5NGQ1ODZmNmZhXkEyXkFqcGdeQXVyODA4OTIyMzY@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BYmQzMDExNTItZDQyNy00ZGIwLWE5ZjAtMzQzODA0NWM3MmMyXkEyXkFqcGdeQXVyODA4OTIyMzY@._V1_FMjpg_UX1000_.jpg", "https://m.media-amazon.com/images/I/71WZ2kqPG3L._AC_UF894,1000_QL80_.jpg"]',
        120, 25, 11, 3);

        

-- FOOD
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Pizza Places in NYC', 'WHen the moon hits your eye like a big pizza pie....', '2023-07-05T15:24:27',
        '["https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQDpsSCVmf0UdYGhrE3juLD2s56LDJRq289UgaYcNR2IpJ1I7rYOyscvDWke55gNS-7bM&usqp=CAU"]',
        '["https://media-cdn.tripadvisor.com/media/photo-s/01/c6/4e/5f/di-fara-special.jpg", "https://media-cdn.tripadvisor.com/media/photo-s/17/cb/75/5f/juliana-s-pizza.jpg"]',
        '["https://media-cdn.tripadvisor.com/media/photo-s/04/01/f1/63/patsy-s-pizzeria-2nd.jpg", "https://media-cdn.tripadvisor.com/media/photo-s/08/93/5c/1c/grimaldi-s.jpg"]',
        '["https://media.timeout.com/images/103321889/750/422/image.jpg", "https://media-cdn.tripadvisor.com/media/photo-s/18/9a/1f/5c/pizza.jpg"]',
        '["https://s3.amazonaws.com/secretsaucefiles/photos/images/000/215/986/large/IMG_3173.jpg?1540188991"]',
        '["https://popmenucloud.com/cdn-cgi/image/width%3D1200%2Cheight%3D1200%2Cfit%3Dscale-down%2Cformat%3Dauto%2Cquality%3D60/ykqmcwoe/941aa2cc-7990-431e-9dcc-ef4532fa1625.jpg"]',
        98, 23, 15, 1);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Fast Food Burgers in America', 'West coast burgers rule', '2023-07-05T15:24:27',
        '["https://www.in-n-out.com/ResourcePackages/INNOUT/content/images/menu/double-double-meal.png?package=INNOUT&v=2023", "https://www.tastingtable.com/img/gallery/shake-shack-cheese-burger-recipe/intro-1640803629.jpg"]',
        '["https://assets3.thrillist.com/v1/image/2835023/828x610/flatten;crop;webp=auto;jpeg_quality=60.jpg"]',
        '["https://whatnowaus.com/wp-content/uploads/sites/20/2023/01/whata1.jpeg"]',
        '["https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/McD_Big_Mac.jpg/1200px-McD_Big_Mac.jpg"]',
        '["https://media.cnn.com/api/v1/images/stellar/prod/220912104435-01-burger-king-whopper-040522-file.jpg?c=original"]',
        '["https://www.freestufffinder.com/wp-content/uploads/2023/03/Wendys-Daves-Single-Cheeseburger.jpg"]',
        115, 52, 11, 1);



INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier,a_tier,c_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES ('Best Ben & Jerry\'s Ice Cream Flavors', 'CFB for the WIN', '2023-07-05T15:24:27',
        '["https://i5.walmartimages.com/asr/11c05145-a206-41b9-86f5-dfc34ccbe8e0.29661f4eb50b816e971e92f381df7c65.jpeg", "https://cdn.shopify.com/s/files/1/0537/6855/2622/products/ETFZ-0000-0020_2__26312.png?v=1614712553", "https://www.foodservicedirect.com/media/catalog/product/0/7/076840100354_wnt92lhcnnocrimy.jpg?width=1200&height=1200&quality=85&fit=bounds"]',
        '["https://www.benandjerrys.ca/files/live/sites/systemsite/files/flavors/products/ca/pints/open-closed-pints/can-eng-cherry-garcia-category-open.png"]',
        '["https://www.benjerry.com/files/live/sites/systemsite/files/US%20and%20Global%20Assets/Flavors/Product%20Assets/US/New%20York%20Super%20Fudge%20Chunk%20Ice%20Cream/new-york-super-fudge-2022-detail.png", "Strawberry", "https://assets.wakefern.com/is/image/wakefern/7684010004-001?$Mi9Product_detail$"]',
        '["https://www.benjerry.com/files/live/sites/systemsite/files/US%20and%20Global%20Assets/Flavors/Product%20Assets/US/Vanilla%20Caramel%20Fudge%20Ice%20Cream/vanilla-caramel-fudge-2022-detail.png", "https://www.benjerry.com/files/live/sites/us/files/migrated/whats-new/6588-flavor-flops/5-Pina-Colada-400x400.jpg?imwidth=1200"]',
        84, 19, 7, 1);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Best Sodas', 
    'Dont know what the hype is about sprite honestly', 
    '2023-07-05T15:24:27',
    '["https://images.heb.com/is/image/HEBGrocery/000862949-1"]',   -- S tier
    '["https://target.scene7.com/is/image/Target/GUEST_1361a461-e2b2-4de2-98aa-4007e6d8b5c2?wid=488&hei=488&fmt=pjpeg"]',       -- A tier
    '["https://images.heb.com/is/image/HEBGrocery/000539118-1?jpegSize=150&hei=1400&fit=constrain&qlt=75"]',   -- B tier
    '["https://target.scene7.com/is/image/Target/GUEST_35e908e8-83f0-49d4-9040-d68b3019ce24?wid=488&hei=488&fmt=pjpeg"]',      -- C tier
    '["https://www.jiomart.com/images/product/original/491349790/mountain-dew-750-ml-product-images-o491349790-p491349790-0-202203150326.jpg?im=Resize=(420,420)"]',-- D tier
    '["https://mobileimages.lowes.com/productimages/8f97d404-92b2-4790-91d9-2c0bf06320c1/12234681.tiff"]',         -- E tier
    '["https://target.scene7.com/is/image/Target/GUEST_bdd6ab3c-67f1-434e-ac78-1b4baa6f7298?wid=488&hei=488&fmt=pjpeg", "https://i5.walmartimages.com/asr/6cc02809-1db9-4531-ab00-8be1fc0812d6.9a9959d24aa5da47dc0d79ff3bfad67e.jpeg"]', -- F tier
    92, 
    14, 
    20, 
    1
);

        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Best Cereals of All Time', 
    'the best cereals of all time.', 
    '2023-07-05T15:24:27',
    '["https://m.media-amazon.com/images/I/81Jv2VHEfTL._AC_UF894,1000_QL80_.jpg", "https://m.media-amazon.com/images/I/81MH6vFTs9L._AC_UF894,1000_QL80_.jpg"]',   -- S tier
    '["https://m.media-amazon.com/images/I/813T8LFLNJL._AC_UF894,1000_QL80_.jpg"]',       -- A tier
    '["https://m.media-amazon.com/images/I/81pNaLhC5oL._AC_UF894,1000_QL80_.jpg", "https://m.media-amazon.com/images/I/91b6OfHNdaL.jpg", "https://www.kroger.com/product/images/large/front/0003800019986"]',   -- B tier
    '["https://m.media-amazon.com/images/I/81dwrBrkqlL._AC_UF894,1000_QL80_.jpg"]',      -- C tier
    '["https://m.media-amazon.com/images/I/81FWaYxBbuL.jpg"]',-- D tier
    '["https://upload.wikimedia.org/wikipedia/en/c/cf/CocoaPuffs.jpg"]',         -- E tier
    '["https://m.media-amazon.com/images/I/81z+bYy3umL.jpg", "https://starfishmarket.com/wp-content/uploads/2013/10/KelloggsCornPopscereal_4508489.jpg"]', -- F tier
    148, 
    32, 
    6, 
    1
);




-- MUSIC
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id) 
VALUES ('Ranking Drake Albums', '6God', '2023-07-05T15:24:27', 
        '["https://upload.wikimedia.org/wikipedia/en/thumb/a/ae/Drake_-_Take_Care_cover.jpg/220px-Drake_-_Take_Care_cover.jpg", "https://upload.wikimedia.org/wikipedia/en/4/42/Drake_-_Nothing_Was_the_Same_cover.png"]', 
        '["https://upload.wikimedia.org/wikipedia/en/a/af/Drake_-_Views_cover.jpg", "https://upload.wikimedia.org/wikipedia/en/9/90/Scorpion_by_Drake.jpg"]', 
        '["https://static.wikia.nocookie.net/drake/images/a/ac/F9c203911cdfcbea91a4abaf9fbc1c8a17-17-drake-review.rsquare.w700.jpg/revision/latest?cb=20201229212906", "https://upload.wikimedia.org/wikipedia/en/9/9c/Drake_-_Thank_Me_Later_cover.jpg"]', 
        '["https://upload.wikimedia.org/wikipedia/en/7/70/Drake_-_More_Life_cover.jpg"]', '["https://upload.wikimedia.org/wikipedia/en/7/79/Drake_-_Certified_Lover_Boy.png"]', '["https://upload.wikimedia.org/wikipedia/en/1/1a/Drake_-_Scary_Hours_2.png"]', '["https://upload.wikimedia.org/wikipedia/en/thumb/6/6b/Drake_-_Dark_Lane_Demo_Tapes.png/220px-Drake_-_Dark_Lane_Demo_Tapes.png"]', 
        76, 29, 14, 2);

        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id) 
VALUES (
    'Best Rappers of the Past 5 Years', 'Kung Fu KENNY!', '2023-07-05T15:24:27', 
    '["https://upload.wikimedia.org/wikipedia/commons/3/32/Pulitzer2018-portraits-kendrick-lamar.jpg", "https://hips.hearstapps.com/hmg-prod/images/j_cole_photo_by_isaac_brekken_wireimage_getty_503069628.jpg"]', 
    '["https://hips.hearstapps.com/hmg-prod/images/drake_photo_by_prince_williams_wireimage_getty_479503454.jpg"]', 
    '["https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Travis_Scott_-_Openair_Frauenfeld_2019_08.jpg/1200px-Travis_Scott_-_Openair_Frauenfeld_2019_08.jpg", "https://hips.hearstapps.com/hmg-prod/images/gettyimages-1189841420.jpg?resize=1200:*"]', 
    '["https://www.rollingstone.com/wp-content/uploads/2022/10/ice-spice-ayntk.jpg"]', 
    '["https://wallpapers.com/images/featured/playboi-carti-pictures-o1duxv134a98qd2w.jpg"]', 
    '["https://m.media-amazon.com/images/M/MV5BZTNkMGVjYTYtMTJlYi00N2IxLWFjNzktNWUxYzIxN2I1Yzg1XkEyXkFqcGdeQXVyNjg3MDMxNzU@._V1_.jpg"]', 
    '["https://www.billboard.com/wp-content/uploads/2022/04/Jack-Harlow-cr-Urban-Wyatt-press-2022-billboard-pro-1260.jpg?w=942&h=623&crop=1"]', 
    105, 42, 7, 2
);

        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id) 
VALUES (
    'Ranking Taylor Swift Albums', 'For my Swifties!', '2023-07-05T15:24:27', 
    '["https://upload.wikimedia.org/wikipedia/en/f/f6/Taylor_Swift_-_1989.png", "https://upload.wikimedia.org/wikipedia/en/e/e8/Taylor_Swift_-_Red.png"]', 
    '["https://upload.wikimedia.org/wikipedia/en/8/86/Taylor_Swift_-_Fearless.png"]', 
    '["https://upload.wikimedia.org/wikipedia/en/8/8f/Taylor_Swift_-_Speak_Now_cover.png", "https://upload.wikimedia.org/wikipedia/en/c/cd/Taylor_Swift_-_Lover.png"]', 
    '["https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png"]', 
    '["https://media.vogue.co.uk/photos/5d547db95da545000800e817/master/w_1600%2Cc_limit/original"]', 
    '["https://upload.wikimedia.org/wikipedia/en/0/0a/Taylor_Swift_-_Evermore.png"]', 
    '["https://upload.wikimedia.org/wikipedia/en/f/f8/Taylor_Swift_-_Folklore.png", "https://i.scdn.co/image/ab67616d0000b273d969ad6fcaca272d9dbc44ee"]', 
    92, 17, 12, 2
);


INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id) 
VALUES (
    'Best Movie Soundtracks of All Time', 'A tier list ranking the top movie soundtracks of all time.', '2023-07-05T15:24:27', 
    '["https://m.media-amazon.com/images/I/51+EG-ve-mL._UF1000,1000_QL80_.jpg", "https://upload.wikimedia.org/wikipedia/en/0/0c/TheBeeGeesSaturdayNightFeveralbumcover.jpg"]', 
    '["https://upload.wikimedia.org/wikipedia/en/0/06/Pulp_Fiction_%28Soundtrack%29.png"]', 
    '["https://s2982.pcdn.co/wp-content/uploads/2020/11/guardians-of-the-galaxy-soundtrack-featurre.png"]', 
    '["https://upload.wikimedia.org/wikipedia/en/b/ba/VA_-_Titanic_-_OST.JPG"]', 
    '["https://m.media-amazon.com/images/I/71L9sAehjmL._UF1000,1000_QL80_.jpg"]', 
    '["https://m.media-amazon.com/images/I/61HCbHPjXLL._UF1000,1000_QL80_.jpg"]', 
    '["https://m.media-amazon.com/images/I/910+uIVUBPL._UF1000,1000_QL80_.jpg", "https://m.media-amazon.com/images/I/51X072K7YML._UF1000,1000_QL80_.jpg"]', 
    118, 63, 5, 2
);
        
INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Greatest One-Hit Wonders of All Time', 'Heeeeeeeey Macarena!', '2023-07-05T15:24:27',
    '["https://s.yimg.com/ny/api/res/1.2/Eos6UbHBbpqG6jurlIq1pA--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MA--/https://media.zenfs.com/en-US/best_life_342/509b8d720f866ed0ad42c295a954f690", "https://m.media-amazon.com/images/I/81n8RnJ0bhL._UF1000,1000_QL80_.jpg"]',
    '["https://static.stereogum.com/uploads/2020/07/Who-Let-The-Dogs-Out-1595521303-870x870.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/d/d5/A-ha_take_on_me-1stcover.jpg", "https://upload.wikimedia.org/wikipedia/en/7/78/DexysMidnightRunnerComeOnEileen7InchSingleCover.jpg"]',
    '["https://foreveryoung80s.files.wordpress.com/2017/04/gloria-jones-tainted.jpg?w=499&h=504"]',
    '["https://upload.wikimedia.org/wikipedia/en/a/a8/Video_Killed_the_Radio_Star_Bruce_Woolley.jpg"]',
    '["https://images.squarespace-cdn.com/content/v1/6139634dacc59146c0eae970/1632703730073-ZWWCRAP0W2ILQMGZJ5JK/2021iceicebaby_album_500.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/1/12/Achy_Breaky_Heart.jpg"]',
    134, 52, 9, 2
);

        
        
-- VIDEO GAMES 

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Most Influential Video Game Consoles of All Time', 'The kids dont know about the first one!', '2023-07-05T15:24:27',
    '["https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Atari-2600-Wood-4Sw-Set.png/640px-Atari-2600-Wood-4Sw-Set.png", "https://m.media-amazon.com/images/I/81s7B+Als-L.jpg"]',
    '["https://m.media-amazon.com/images/I/81QWa2SdU-L.jpg"]',
    '["https://m.media-amazon.com/images/I/61oFqsoj9nL.jpg", "https://m.media-amazon.com/images/I/71r3BuKrYcL.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Virtual-Boy-Set.jpg/1200px-Virtual-Boy-Set.jpg"]',
    '["https://m.media-amazon.com/images/I/511mrPZIrCL.jpg"]',
    '["https://m.media-amazon.com/images/I/51PCiqTcClL._AC_UF1000,1000_QL80_.jpg"]',
    '["https://www.voomwa.com/cdn/shop/collections/wii-console-white_600x600.jpg?v=1627352970"]',
    95, 28, 18, 5
);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Best Open-World Games', 'best open-world games that offer vast exploration and immersive experiences.', '2023-07-05T15:24:27',
    '["https://upload.wikimedia.org/wikipedia/en/1/15/The_Elder_Scrolls_V_Skyrim_cover.png", "https://upload.wikimedia.org/wikipedia/en/thumb/c/c4/GTASABOX.jpg/220px-GTASABOX.jpg"]',
    '["https://assets.nintendo.com/image/upload/c_fill,w_1200/q_auto:best/f_auto/dpr_2.0/ncom/software/switch/70010000000025/7137262b5a64d921e193653f8aa0b722925abc5680380ca0e18a5cfd91697f58"]',
    '["https://image.api.playstation.com/cdn/UP1004/CUSA03041_00/Hpl5MtwQgOVF9vJqlfui6SDB5Jl4oBSq.png", "https://upload.wikimedia.org/wikipedia/en/thumb/9/99/ACOdysseyCoverArt.png/220px-ACOdysseyCoverArt.png"]',
    '["https://cdn1.epicgames.com/ecebf45065bc4993abfe0e84c40ff18e/offer/WDOG_STD_Store_Landscape_2580x1450-2580x1450-3fd07dcc02e65aca288d794c177c7512.jpg"]',
    '["https://image.api.playstation.com/vulcan/ap/rnd/202009/2310/HIjTn6HknjoOgTCBFVXKWzz2.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"]',
    '["https://fanatical.imgix.net/product/original/01de5e19-aa75-443a-8f96-0e49d26a1ace.jpeg?auto=compress,format&w=400&fit=crop&h=", "https://cdn.akamai.steamstatic.com/steam/apps/552520/capsule_616x353.jpg?t=1681229356"]',
    127, 41, 22, 5
);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Most Iconic Video Game Characters', 'most iconic and memorable video game characters of all time.', '2023-07-05T15:24:27',
    '["https://upload.wikimedia.org/wikipedia/en/a/a9/MarioNSMBUDeluxe.png", "https://static.wikia.nocookie.net/zelda_gamepedia_en/images/2/29/OoT_Link_Artwork.png/revision/latest/scale-to-width-down/320?cb=20120304192937"]',
    '["https://upload.wikimedia.org/wikipedia/en/4/42/Master_chief_halo_infinite.png"]',
    '["https://static.wikia.nocookie.net/p__/images/0/02/Snake_SSBU.png/revision/latest/scale-to-width-down/1200?cb=20180613145316&path-prefix=protagonist", "https://upload.wikimedia.org/wikipedia/en/a/a8/LaraCroftInfobox.png"]',
    '["https://static.wikia.nocookie.net/p__/images/0/0d/Pac-ManRe-Pac.png/revision/latest?cb=20220716205255&path-prefix=protagonist"]',
    '["https://upload.wikimedia.org/wikipedia/en/d/d4/Donkey_Kong_character.png"]',
    '["https://mario.wiki.gallery/images/thumb/b/ba/Sonic_SSBU.png/1200px-Sonic_SSBU.png"]',
    '["https://upload.wikimedia.org/wikipedia/en/d/d6/Samus_Aran.png", "https://static.wikia.nocookie.net/godofwar/images/e/e8/Kratos_gow_2018.png/revision/latest?cb=20230104133535"]',
    152, 48, 9, 5
);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'Best MOBA Games', 'best multiplayer online battle arena games', '2023-07-05T15:24:27',
    '["https://cdn1.epicgames.com/offer/24b9b5e323bc40eea252a10cdd3b2f10/LoL_1200x1600-15ad6c981af8d98f50e833eac7843986", "https://cdn.akamai.steamstatic.com/steam/apps/570/capsule_616x353.jpg?t=1682639497"]',
    '["https://static.heroesofthestorm.com/images/global/fb-share-1fcc54becc.jpg"]',
    '["https://i.ytimg.com/vi/xAPsmI_zDZs/maxresdefault.jpg", "https://mmoculture.com/wp-content/uploads/2019/11/Vainglory-image.png"]',
    '["https://upload.wikimedia.org/wikipedia/en/a/ae/Paragon_cover_art.jpg"]',
    '["https://assets.nintendo.com/image/upload/c_fill,w_1200/q_auto:best/f_auto/dpr_2.0/ncom/software/switch/70010000002022/015492cb88ef370d58b834f7b658f347866dfe5f1e158ddded923fed8f187048"]',
    '["https://editors.charlieintel.com/wp-content/uploads/2023/02/mobile-legends-bang-bang-redeemable-codes.jpg"]',
    '["https://cdn.stunlock.com/blog/2019/07/03154019/battlerite_announcement1.jpg", "https://upload.wikimedia.org/wikipedia/en/6/6a/MystCover.png"]',
    118, 33, 19, 5
);

INSERT INTO tier_list (`name`, `description`, `timestamp`, s_tier, a_tier, b_tier, c_tier, d_tier, e_tier, f_tier, upvotes, downvotes, app_user_id, category_id)
VALUES (
    'GTA Games Ranked', 'Since we will never see the new GTA', '2023-07-05T15:24:27',
    '["https://upload.wikimedia.org/wikipedia/en/c/c4/GTASABOX.jpg", "https://upload.wikimedia.org/wikipedia/en/a/a5/Grand_Theft_Auto_V.png"]',
    '["https://upload.wikimedia.org/wikipedia/en/c/ce/Vice-city-cover.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/b/b7/Grand_Theft_Auto_IV_cover.jpg", "https://upload.wikimedia.org/wikipedia/en/thumb/b/be/GTA3boxcover.jpg/220px-GTA3boxcover.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/e/ea/Grand_Theft_Auto_Liberty_City_Stories_box.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/7/75/ChinatownWars.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/4/4d/Grand_Theft_Auto_IV_coverart.jpg"]',
    '["https://upload.wikimedia.org/wikipedia/en/0/0e/The_Ballad_of_Gay_Tony_cover.jpg", "https://m.media-amazon.com/images/I/619ot20VdGL.jpg"]',
    134, 42, 9, 5
);


insert into display_profile (picture, bio, twitter, instagram, tiktok, username, app_user_id)
    values
    ('https://images.squarespace-cdn.com/content/v1/59c3feb149fc2b152179e47e/e2391625-d93a-443a-b8eb-e9f5c2c0861e/Drink+Masters+smoking%21+photo+courtesy+of+Netflix.jpg', 'Master of mixology and Food & Drink enthusiast', 'https://twitter.com/johnsmith', 'https://instagram.com/johnsmith', null, 'john@smith.com', 1),
    ('https://www.udiscovermusic.com/wp-content/uploads/2020/11/Xmas2020_Indie_1000x600.jpg', 'Indie music aficionado and Music lover', 'https://twitter.com/sallyjones', null, 'https://tiktok.com/@sallyjones', 'sally@jones.com', 2),
    ('https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F6%2F2013%2F01%2Fbeloved-geeks-big-bang-theory-touts.jpg&q=60', 'TV/Movies geek, always on the look for the next big show', null, 'https://instagram.com/emmawilson', 'https://tiktok.com/@emmawilson', 'emma@wilson.com', 3),
    ('https://macsources.com/wp-content/uploads/2022/03/contributor-sports-March-2022.jpg', 'Sports fanatic, keeping track of all scores', 'https://twitter.com/alexthomas', null, null, 'alex@thomas.com', 4),
    ('https://images.ctfassets.net/3viuren4us1n/4Wdv0gXSWy77nNuNM2MaoF/b3513ffcbfddbc4146a4fa4b28cb81d9/Gamers-workspace.jpg?fm=webp&w=1920', 'Pro gamer and Video Games lover', null, 'https://instagram.com/lucasharris', 'https://tiktok.com/@lucasharris', 'lucas@harris.com', 5),
    ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9Os0qVEl0TqI_Wq-VYg3qHUfBym8QQgnfdrh_83F0LedQCzLFqHHd4aT4lky1KkPRmdA&usqp=CAU', 'Foodie who loves to share new recipes', 'https://twitter.com/oliviamartinez', null, null, 'olivia@martinez.com', 6),
    ('https://hungryhappenings.com/wp-content/uploads/2019/01/super-bowl-food-football-recipes-tailgating-party.jpg', 'Football fan and occasional food blogger', null, 'https://instagram.com/noahrodriguez', null, 'noah@rodriguez.com', 7),
    ('https://ichef.bbci.co.uk/news/624/mcs/media/images/74182000/jpg/_74182998_0q0a0905.jpg', 'Music enthusiast and vinyl collector', 'https://twitter.com/mialopez', null, null, 'mia@lopez.com', 8),
    ('https://media.wired.com/photos/5f5fdba8af1c7b1f76a6a86b/master/w_2560%2Cc_limit/Culture_Pokemane_vtuber.jpg', 'Anime lover and video game streamer', null, null, 'https://tiktok.com/@liamlee', 'liam@lee.com', 9),
    ('https://www.finedininglovers.com/sites/g/files/xknfdk626/files/styles/im_landscape_100/public/2020-01/Chef%20or%20a%20cook.png.webp?itok=F-1Oj8Ek', 'Chef in training and Food & Drink lover', 'https://twitter.com/avamiller', null, null, 'ava@miller.com', 10),
    ('https://res.cloudinary.com/sprdcyoblog/image/upload/f_auto,q_70/w_1030/v1624355413/blog/article/soccer/mockup-featuring-a-teenager-with-a-soccer-ball-pointing-at-his-t-shirt', 'Soccer enthusiast and Sports blogger', 'https://twitter.com/logantaylor', 'https://instagram.com/logantaylor', null, 'logan@taylor.com', 11),
    ('https://www.indiewire.com/wp-content/uploads/2013/06/ratatouille-04162012-1.jpg', 'Movie buff and TV/Movies critic', null, 'https://instagram.com/isabellaclark', 'https://tiktok.com/@isabellaclark', 'isabella@clark.com', 12),
    ('https://i.guim.co.uk/img/media/1398f335c19b28ff52ea08e5fac2339a4d31d58a/0_167_5032_3020/master/5032.jpg?width=1200&quality=85&auto=format&fit=max&s=70cdc98037290e2083c1987e61604d16', 'Video Games streamer and eSports fan', 'https://twitter.com/ethanmitchell', null, null, 'ethan@mitchell.com', 13),
    ('https://static01.nyt.com/images/2023/01/24/multimedia/17ASKWELL-COFFEE-STOMACH2-mkqt/17ASKWELL-COFFEE-STOMACH2-mkqt-mediumSquareAt3X-v3.jpg', 'Coffee lover and Food & Drink critic', null, 'https://instagram.com/emmabrown', 'https://tiktok.com/@emmabrown', 'emma@brown.com', 14),
    ('https://edtimes.in/wp-content/uploads/2023/02/282066291_381733467226718_4592825925121900801_n-1-640x800.jpeg', 'Music producer and aspiring DJ', 'https://twitter.com/williamjackson', null, null, 'william@jackson.com', 15),
    ('https://i.guim.co.uk/img/static/sys-images/Arts/Arts_/Pictures/2013/2/1/1359722429044/The-Graduate-poster-011.jpg?width=465&quality=85&dpr=1&s=none', 'Moviegoer and amateur film critic', 'https://twitter.com/miadavis', 'https://instagram.com/miadavis', null, 'mia@davis.com', 16),
    ('https://media.gq.com/photos/5582971be52bc4b477a9ad2f/master/w_1600%2Cc_limit/blogs-the-q-lebron5.jpg', 'Basketball fan and Sports blogger', 'https://twitter.com/alexthomas2', null, 'https://tiktok.com/@alexthomas2', 'alex@smith.com', 17),
    ('https://townsquare.media/site/113/files/2023/05/attachment-Dovetail-Joint-Restaurant.jpg?w=980&q=75', 'Foodie with a knack for finding hidden gems', null, 'https://instagram.com/lucasharris2', null, 'ed@harris.com', 18),
    ('https://media-cldnry.s-nbcnews.com/image/upload/t_fit-760w,f_auto,q_auto:best/newscms/2018_09/2348626/180302-jack-white-2015-ac-548p.jpg', 'Musician and avid concert-goer', 'https://twitter.com/olivermartin', null, null, 'oliver@martin.com', 19),
    ('https://static1.colliderimages.com/wordpress/wp-content/uploads/2022/01/The-Best-Movie-Franchises-To-Binge-Watch.jpg', 'TV/Movies enthusiast, binging one series at a time', null, 'https://instagram.com/avaharris', 'https://tiktok.com/@avaharris', 'ava@harris.com', 20),
    ('https://cdn.vox-cdn.com/thumbor/U9lgeMlwUVWPUdhowho0HQWQNZc=/1400x1400/filters:format(png)/cdn.vox-cdn.com/uploads/chorus_asset/file/23620378/HOL_KeyArt__3.png', 'Video Games developer and sci-fi fan', 'https://twitter.com/ethanthompson', null, null, 'ethan@thompson.com', 21),
    ('https://www.sidechef.com/article/fc42883d-c4f8-47a2-8948-8d2e3cdeda68.jpg?d=1408x1120', 'Food & Drink connoisseur and home chef', null, 'https://instagram.com/sophiarodriguez', 'https://tiktok.com/@sophiarodriguez', 'sophia@rodriguez.com', 22),
    ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSETF7HDwz2qgxD9tyyCo3JZKs4Go5T9mdDM6kLLa_32Aw3OFGo8Vku_wOwN8U14rDiexM&usqp=CAU', 'Music journalist and guitarist', 'https://twitter.com/liamclark', null, null, 'liam@clark.com', 23),
    ('https://athlonsports.com/.image/t_share/MTgyMDE4NzA2OTE0NjgyMTg0/image-placeholder-title.jpg', 'Sports commentator and football player', null, 'https://instagram.com/isabellalewis', null, 'isabella@lewis.com', 24),
    ('https://cdn.vox-cdn.com/thumbor/xJj6mAra4_9Sd1SX9cr239D1zNo=/0x0:2560x1440/1400x1050/filters:focal(1270x610:1678x1018):no_upscale()/cdn.vox-cdn.com/uploads/chorus_image/image/70471053/doc_cover.0.jpg', 'TV/Movies lover with a penchant for documentaries', 'https://twitter.com/jacobwalker', null, 'https://tiktok.com/@jacobwalker', 'jacob@walker.com', 25),
    ('https://static01.nyt.com/images/2020/12/20/fashion/14WOMEN-IN-GAMING1/merlin_179871435_a718139f-de7b-4148-a018-09350bd1eeec-superJumbo.jpg', 'Video Games champion and game designer', null, 'https://instagram.com/oliviawright', null, 'olivia@wright.com', 26),
    ('https://www.mercurynews.com/wp-content/uploads/2018/05/sjm-l-whole30-0502-01.jpg?w=428', 'Food & Drink blogger and cookbook author', 'https://twitter.com/michaelhall', null, null, 'michael@hall.com', 27),
    ('https://www.betterteam.com/images/Music-Teacher-Job-Description.jpeg?crop=40:21,smart&width=1200&dpr=2', 'Music teacher and choir singer', null, 'https://instagram.com/emilyyoung', 'https://tiktok.com/@emilyyoung', 'emily@young.com', 28),
    ('https://images.saymedia-content.com/.image/t_share/MTg1MTQ4MzExNjA1OTQ1NjI1/top-10-nba-players-who-became-nba-analysts.jpg', 'Sports analyst and basketball player', 'https://twitter.com/benjaminmorgan', null, null, 'benjamin@morgan.com', 29),
    ('https://www.nyfa.edu/wp-content/uploads/2015/01/moviescriptswheretodownload.png', 'TV/Movies reviewer and screenplay writer', null, 'https://instagram.com/harpercooper', null, 'harper@cooper.com', 30),
    ('https://static01.nyt.com/images/2020/01/05/arts/05martin-scorsese3/05martin-scorsese3-videoSixteenByNineJumbo1600.jpg', 'NYC/Filmbuff', null, null, null, 'ken.lott.film@gmail.com', 31),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Windows_10_Default_Profile_Picture.svg/2048px-Windows_10_Default_Profile_Picture.svg.png', 'Dev10 Cohort 56', null, null, null, 'philshou@gmail.com', 32),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Windows_10_Default_Profile_Picture.svg/2048px-Windows_10_Default_Profile_Picture.svg.png', 'Dev10 Cohort 56', null, null, null, 'ipanganiban99@gmail.com', 33);


   
 
   

insert into `comment` (`comment`, `timestamp`, tier_list_id, app_user_id)
    values
    ('Nice list, but I would swap the top 2.', '2023-06-20 10:15:21', 5, 2),
    ('I completely agree with your rankings!', '2023-06-18 14:52:37', 12, 8),
    ('I think your list is a bit off, but respect your opinion.', '2023-06-30 20:23:45', 3, 13),
    ('Interesting choices, made me rethink my own list!', '2023-07-04 18:10:42', 17, 22),
    ('I disagree on a few but overall, good job!', '2023-06-25 11:33:58', 8, 1),
    ('This is a unique list, got some thinking to do.', '2023-07-02 16:45:07', 23, 4),
    ('I would definitely move a few things around.', '2023-06-19 09:12:15', 14, 7),
    ('Interesting picks, got me questioning my own.', '2023-06-28 15:33:09', 1, 30),
    ('I love your list! Really unique perspective.', '2023-06-22 08:22:45', 7, 16),
    ('Great choices, they are spot on!', '2023-06-16 19:40:32', 21, 25),
    ('I’m no expert, but I think your list needs a vacation!', '2023-06-23 14:12:45', 19, 10),
('You had me at your top choice. Everything else is just bonus.', '2023-06-26 21:35:17', 9, 29),
('I have several disagreements, but I respect the audacity!', '2023-06-20 08:22:19', 16, 3),
('Oh wow, this list... interesting to say the least!', '2023-06-30 13:56:08', 24, 6),
('Did you rank these in your sleep? Still love it!', '2023-06-28 18:43:37', 2, 15),
('Your list is like pineapple on pizza - controversial!', '2023-07-03 22:10:09', 4, 27),
('I see what you did there with your picks, nice one.', '2023-06-24 16:31:21', 22, 18),
('Your choices? Out of this world! Literally.', '2023-06-19 09:12:34', 11, 5),
('A moment of silence for the ones that didnt make the cut.', '2023-07-01 17:55:27', 20, 12),
('This list would make an excellent comedy plot.', '2023-06-21 14:33:41', 15, 21),
('I think we need to have a serious talk about this list.', '2023-06-22 16:55:43', 3, 27),
('We clearly have VERY different tastes, and that’s okay…', '2023-06-19 14:42:11', 18, 7),
('This list is...how do I put this gently...not my cup of tea.', '2023-06-26 21:25:56', 12, 22),
('I have a bone to pick with your choice at number 3.', '2023-06-29 17:38:19', 9, 11),
('Your top pick is my last, and I’m not even joking!', '2023-06-24 15:56:04', 20, 5),
('I disagree with your choices, but I’ll defend your right to make them.', '2023-06-30 08:52:33', 7, 28),
('Ive seen a lot of lists, but this one takes the cake.', '2023-06-25 12:15:44', 23, 3),
('My dog would make better choices than this.', '2023-06-28 19:20:12', 15, 17),
('This list makes me question everything I thought I knew about you.', '2023-07-02 20:33:46', 14, 6),
('This list is as cohesive as oil and water.', '2023-06-27 11:21:09', 5, 25),
('Diversity in taste is what makes the world interesting!', '2023-06-30 12:43:55', 22, 8),
('How much coffee did you drink before making this list?', '2023-06-25 10:30:20', 7, 29),
('This list is a wild roller coaster of emotions.', '2023-06-23 17:48:23', 4, 13),
('I love how youre not afraid to put pineapple on pizza. Unpopular opinions, FTW!', '2023-06-27 13:16:40', 12, 9),
('Wait, are these in descending order? 😅', '2023-07-02 08:30:32', 9, 18),
('Your top pick is an underdog, didnt see that coming.', '2023-06-19 20:22:45', 16, 5),
('Your number 5 is number 1 in my heart.', '2023-06-29 14:16:11', 2, 24),
('Where is the love for indie games?', '2023-06-20 09:15:38', 24, 15),
('Its like you read my mind! Except for that number 3 pick...', '2023-07-01 16:30:22', 20, 12),
('You and I could be friends, if not for this list. 😂', '2023-06-28 10:23:38', 11, 14),
('Your rankings are as unique as a snowflake... a very strange snowflake.', '2023-06-26 18:32:00', 15, 16),
('Never thought Id find someone else who appreciates that obscure band!', '2023-06-30 11:05:37', 8, 21),
('Feels like you just threw darts at a board for this list.', '2023-07-02 14:20:44', 23, 1),
('This tier list has more twists than a soap opera.', '2023-06-25 09:30:58', 19, 6),
('This list gave me whiplash, but in a good way!', '2023-06-29 15:15:23', 5, 17),
('Youve inspired me to try some new things... and avoid others.', '2023-06-28 17:45:50', 13, 7),
('I respect your courage to rank THAT in your top 5.', '2023-06-22 13:32:16', 6, 25),
('Your list is a mystery wrapped in an enigma.', '2023-07-01 19:23:41', 3, 28),
('Is there a hidden meaning behind this list? I must know!', '2023-06-20 10:16:23', 21, 4),
('Gonna need a cup of tea to process this one.', '2023-06-27 16:50:33', 17, 11),
("Wow, you really threw me for a loop with that third pick.", "2023-07-04 10:33:21", 1, 5),
("Never in a million years would I have put that game at number one.", "2023-06-30 15:22:18", 9, 17),
("I cant even express how much I disagree with this.", "2023-06-27 13:56:29", 17, 6),
("This list is all over the place, like my last road trip.", "2023-07-01 12:23:45", 13, 4),
("Is this list random or is there some method to the madness?", "2023-06-29 14:15:20", 20, 7),
("Ive seen better tier lists from my grandma.", "2023-07-02 19:45:51", 11, 3),
("Interesting choices... especially that last one.", "2023-06-30 16:31:22", 6, 10),
("This list is a roller coaster of choices.", "2023-07-04 09:10:15", 22, 2),
("Your taste is as unique as a four leaf clover.", "2023-06-28 18:20:30", 5, 13),
("I wouldnt touch half of your top tier with a ten foot pole.", "2023-06-25 11:46:49", 15, 19),
("I can respect the courage to put that so high.", "2023-07-03 17:52:38", 8, 16),
("I see we have a maverick here!", "2023-07-02 20:24:27", 3, 25),
("I feel like this is a secret message or something.", "2023-07-05 08:32:48", 21, 22),
("Your list is as unpredictable as the weather.", "2023-06-26 14:56:55", 19, 8),
("I think youve invented a new genre with this list.", "2023-07-04 21:45:12", 7, 23),
("This feels like a bold statement, and Im not sure I agree.", "2023-06-29 10:19:25", 24, 9),
("Im not sure what to say... this list is something else.", "2023-07-03 16:30:39", 16, 18),
("I may not agree, but I respect your originality.", "2023-06-28 12:24:47", 2, 15),
("This list is the equivalent of pineapple on pizza.", "2023-07-01 11:18:59", 14, 21),
("Theres some picks here that are just... wow.", "2023-07-04 09:23:42", 18, 24),
("Ive never seen such a list in all my years of tier-listing.", "2023-06-27 15:38:52", 10, 27),
("It is clear you are a free thinker.", "2023-07-05 12:25:46", 12, 28),
("Thats a controversial choice, but I can see where you're coming from.", "2023-06-28 10:15:34", 23, 30),
("Definitely a different perspective on things.", "2023-07-03 17:27:18", 4, 29),
("This list... I dont even know where to begin.", "2023-06-30 11:43:56", 9, 26),
("Well, to each their own.", "2023-07-02 14:25:10", 25, 5),
("If I squint, I can almost see the logic.", "2023-06-29 16:30:24", 8, 12),
("We definitely do not share the same taste.", "2023-06-26 19:45:32", 3, 14),
('Everyone has their own taste, I guess...', '2023-06-15 08:30:00', 20, 28),
('This list really shows your unique taste', '2023-06-18 16:15:00', 4, 3),
('Nostalgia hit hard with this list', '2023-06-20 11:20:00', 13, 16),
('Now, thats a comprehensive list', '2023-06-30 09:30:00', 2, 19),
('Interesting, but not my cup of tea', '2023-06-11 16:05:00', 23, 25),
('Hmm, I see what you did there', '2023-06-10 17:00:00', 8, 7),
('Top 3, really? We need a talk...', '2023-06-25 15:15:00', 11, 30),
('I miss some of these games', '2023-06-14 12:05:00', 3, 6),
('Double thumbs up for this list', '2023-06-09 13:45:00', 19, 15),
('I expected some classics here...', '2023-06-17 14:10:00', 21, 13),
('This list makes me want to grab a snack', '2023-06-29 10:00:00', 22, 5),
('This list has opened my eyes', '2023-06-22 18:10:00', 14, 2),
('Who would have thought of this ranking?', '2023-06-23 19:30:00', 9, 10),
('This list is just perfect', '2023-06-12 10:30:00', 18, 9),
('Sports fans would love this', '2023-06-21 14:20:00', 24, 4),
('This list is music to my ears', '2023-06-28 13:35:00', 12, 8),
('Who made this list?', '2023-06-27 12:25:00', 15, 1),
('Wow, never thought about it this way', '2023-06-16 14:50:00', 16, 12),
('Interesting choice in the top tier', '2023-06-24 11:00:00', 5, 21),
('A unique list for sure', '2023-06-26 15:45:00', 17, 14),
('Youve got some niche choices here', '2023-06-15 19:30:00', 7, 23),
('I might not agree, but I respect your choices', '2023-06-30 16:00:00', 10, 27);








    
-- select statements
-- select * from category
-- select * from tier_list
 -- select * from display_profile
-- select * from comment
-- select * from app_user_role;