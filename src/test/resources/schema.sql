DROP TABLE IF EXISTS good;
CREATE TABLE good (
	id serial primary key,
	user_id integer NOT NULL,
	date date NOT NULL,
	good1 varchar(400),
	good2 varchar(400),
	good3 varchar(400),
	registered_datetime timestamp,
	update_datetime timestamp
);
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id serial primary key,
	mail_address varchar(60) UNIQUE NOT NULL,
	password varchar(60) NOT NULL,
	delete_flg boolean NOT NULL,
	registered_datetime timestamp,
	update_datetime timestamp,
	delete_datetime timestamp
);
DROP TABLE IF EXISTS tmp_user;
CREATE TABLE tmp_user (
	id serial PRIMARY KEY,
	mail_address varchar(60) NOT NULL,
	password varchar(60) NOT NULL,
	token varchar(60) NOT NULL,
	expiry_date timestamp NOT NULL
);