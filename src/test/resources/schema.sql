DROP TABLE IF EXISTS good;
CREATE TABLE good (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	date DATE NOT NULL,
	good1 VARCHAR(400),
	good2 VARCHAR(400),
	good3 VARCHAR(400)
);
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	mail_address VARCHAR(60) UNIQUE NOT NULL,
	password VARCHAR(60) NOT NULL
);
DROP TABLE IF EXISTS tmp_user;
CREATE TABLE tmp_user (
	id SERIAL PRIMARY KEY,
	mail_address VARCHAR(60) NOT NULL,
	password VARCHAR(60) NOT NULL,
	token VARCHAR(60) NOT NULL,
	expiry_date TIMESTAMP NOT NULL
);
DROP TABLE IF EXISTS password_reissue_info;
CREATE TABLE password_reissue_info (
	mail_address VARCHAR(60) NOT NULL,
	token VARCHAR(60) NOT NULL
);