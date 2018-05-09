DROP TABLE IF EXISTS application_user;
DROP TABLE IF EXISTS user_data;

CREATE TABLE IF NOT EXISTS application_user (
  id         SERIAL PRIMARY KEY,
  email      VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  role       VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_data (
  id         SERIAL PRIMARY KEY,
  email      VARCHAR(100) NOT NULL UNIQUE,

);