DROP TABLE IF EXISTS good;
CREATE TABLE good (
	id serial primary key,
	user_id varchar(8),
	date date,
	good1 varchar(400),
	good2 varchar(400),
	good3 varchar(400),
	registered_datetime timestamp,
	update_datetime timestamp
);
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id varchar(8) primary key,
	mail_address varchar(60) NOT NULL,
	password varchar(60) NOT NULL,
	delete_flg boolean NOT NULL,
	registered_datetime timestamp,
	update_datetime timestamp,
	delete_datetime timestamp
);
DROP SEQUENCE IF EXISTS users_id_seq CASCADE;
CREATE SEQUENCE users_id_seq
	INCREMENT BY 1
	MAXVALUE 99999999
	START WITH 1
	NO CYCLE
;