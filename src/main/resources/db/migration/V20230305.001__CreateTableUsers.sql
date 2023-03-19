CREATE TABLE users (
  password varchar(255) DEFAULT NULL,
  first_Name varchar(100) DEFAULT NULL,
  last_Name varchar(100) DEFAULT NULL,
  email varchar(100) NOT NULL,
  id bigint auto_increment NOT NULL,
  CF varchar(16),
  filename varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);