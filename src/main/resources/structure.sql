DROP TABLE IF EXISTS application_user;

CREATE TABLE IF NOT EXISTS application_user (
  id         SERIAL PRIMARY KEY,
  email      VARCHAR(100) NOT NULL,
  password   VARCHAR(100) NOT NULL,
  role       VARCHAR(100) NOT NULL
);