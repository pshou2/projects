drop database if exists tier_test;
create database tier_test;
use tier_test;

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
    picture varchar(100) null,
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

delimiter //
create procedure set_known_good_state()
begin

	delete from app_user_role;
    delete from app_role;
    alter table app_role auto_increment = 1;
    delete from display_profile;
    alter table display_profile auto_increment = 1;
	delete from `comment`;
	alter table `comment` auto_increment = 1;
    delete from tier_list;
    alter table tier_list auto_increment = 1;
    delete from category;
    alter table category auto_increment = 1;
    delete from app_user;
    alter table app_user auto_increment = 1;
    
    -- data
	insert into app_role (`name`) values
		('USER'),
		('ADMIN');

	-- passwords are set to "P@ssw0rd!"
	insert into app_user (username, password_hash, enabled)
		values
		('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
		('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
        ('phil@shou.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

	insert into app_user_role
		values
		(1, 2),
		(2, 1);
		
	insert into category (`name`)
		values
		('Food & Drink'),
		('Music'),
		('Movies'),
		('Sports'),
		('Video Games');

	insert into tier_list (`name`, `description`, `timestamp`, s_tier, b_tier, upvotes, downvotes, app_user_id, category_id) 
		values
		('Cereal Tear List', 'i like capn crunch', current_timestamp(), '["https://ipcdn.freshop.com/resize?url=https://images.freshop.com/00030000573211/7214e1293b0348b6608b4276af98e732_large.png&width=256&type=webp&quality=80", "https://i5.walmartimages.com/asr/0bedea68-c19b-4783-b224-51e0c03e91fa.18d08896ee4e818f9fa56419d93ea062.jpeg"]', '["https://ipcdn.freshop.com/resize?url=https://images.freshop.com/00016000275515/eb833dbc1d2fe94779157ccb2efd30a4_large.png&width=256&type=webp&quality=80"]', 0, 1, 1, 1);

	insert into tier_list (`name`, `description`, `timestamp`, s_tier, f_tier, upvotes, downvotes, app_user_id, category_id) 
		values
		('BEST PIZZA CHAINS', 'little caesars is TRASH', current_timestamp(), '["https://cdn-1.webcatalog.io/catalog/pizza-hut/pizza-hut-icon-filled-256.webp?v=1675613942058"]', '["https://logowik.com/content/uploads/images/little-caesars.jpg"]', 52, 0, 2, 1);

	insert into display_profile (picture, bio, username, app_user_id)
		values
		('https://t4.ftcdn.net/jpg/01/18/03/35/360_F_118033506_uMrhnrjBWBxVE9sYGTgBht8S5liVnIeY.jpg', 'cereal enthusiast', 'john@smith.com', 1),
		('https://i.pinimg.com/550x/57/70/f0/5770f01a32c3c53e90ecda61483ccb08.jpg', 'not accepting criticism', 'sally@jones.com', 2);

	insert into `comment` (`comment`, `timestamp`, tier_list_id, app_user_id)
		values
		('cheerios higher than cinnamon toast crunch? cringe', current_timestamp(), 1, 2),
		('BASED', current_timestamp(), 2, 1);

end //
-- 4. Change the statement terminator back to the original.
delimiter ;