DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS post;

CREATE TABLE post (
    post_id bigserial PRIMARY KEY,
    user_id varchar(50) NOT NULL,
    title varchar(100) NOT NULL,
    content text NOT NULL,
    dt_created timestamp NOT NULL,
    dt_updated timestamp
);

CREATE TABLE tag (
    tag_id bigserial PRIMARY KEY,
    name varchar(50) NOT NULL
);


CREATE TABLE post_tag (
    post_id bigint REFERENCES post(post_id) ON DELETE CASCADE NOT NULL,
    tag_id bigint REFERENCES tag(tag_id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (post_id, tag_id)
);

CREATE TABLE comment (
    comment_id bigserial PRIMARY KEY,
    user_id varchar(50) NOT NULL,
    post_id bigint REFERENCES post(post_id) ON DELETE CASCADE NOT NULL,
    content text,
    dt_created timestamp NOT NULL,
    dt_updated timestamp
);




--Data
insert into post (user_id, title, content, dt_created, dt_updated)
	values ('97668e44-07ad-4c60-953e-963a837ae233', 'Day 1', 'It''s all good!', to_timestamp('2022-05-14 18:05:50', 'yyyy-MM-dd HH24:mm:ss'), null);
insert into post (user_id, title, content, dt_created, dt_updated)
	values ('97668e44-07ad-4c60-953e-963a837ae233', 'Day 2', 'It''s all ok!', to_timestamp('2022-05-15 18:05:50', 'yyyy-MM-dd HH24:mm:ss'), null);
insert into post (user_id, title, content, dt_created, dt_updated)
	values ('a8353053-5704-406b-b5a2-b01cb7b0940b', 'Day 3', 'It''s all bad!', to_timestamp('2022-05-16 18:05:50', 'yyyy-MM-dd HH24:mm:ss'), null);

insert into tag (name) values ('news');
insert into tag (name) values ('life');
insert into tag (name) values ('day');
insert into tag (name) values ('mood');
insert into tag (name) values ('ideas');
insert into tag (name) values ('thoughts');

insert into post_tag(post_id, tag_id) values (1, 1);
insert into post_tag(post_id, tag_id) values (1, 2);
insert into post_tag(post_id, tag_id) values (2, 3);
insert into post_tag(post_id, tag_id) values (2, 2);
insert into post_tag(post_id, tag_id) values (2, 1);
insert into post_tag(post_id, tag_id) values (2, 5);
insert into post_tag(post_id, tag_id) values (3, 3);
insert into post_tag(post_id, tag_id) values (3, 2);
insert into post_tag(post_id, tag_id) values (3, 6);

insert into comment(user_id, post_id, content, dt_created)
    values('97668e44-07ad-4c60-953e-963a837ae233', 2, 'Nice!', to_timestamp('2022-05-14 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));
insert into comment(user_id, post_id, content, dt_created)
    values('a8353053-5704-406b-b5a2-b01cb7b0940b', 1, 'Awesome!', to_timestamp('2022-05-15 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));
insert into comment(user_id, post_id, content, dt_created)
    values('a8353053-5704-406b-b5a2-b01cb7b0940b', 1, 'Excellent!', to_timestamp('2022-05-16 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));
insert into comment(user_id, post_id, content, dt_created)
    values('97668e44-07ad-4c60-953e-963a837ae233', 1, 'Wonderful!', to_timestamp('2022-05-17 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));
insert into comment(user_id, post_id, content, dt_created)
    values('a8353053-5704-406b-b5a2-b01cb7b0940b', 3, 'Disgusting!', to_timestamp('2022-05-18 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));
insert into comment(user_id, post_id, content, dt_created)
    values('a8353053-5704-406b-b5a2-b01cb7b0940b', 3, 'Atrocious!', to_timestamp('2022-05-19 18:05:50', 'yyyy-MM-dd HH24:mm:ss'));