DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  uid VARCHAR(10) NOT NULL UNIQUE,
  firstname VARCHAR(100) NOT NULL,
  lastname VARCHAR(100) NOT NULL,
  age INT DEFAULT NULL
);

INSERT INTO person(uid, firstname, lastname, age) VALUES('AWJPR2992K', 'Jitendra', 'Rawat', 30);