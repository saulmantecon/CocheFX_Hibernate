DROP DATABASE IF EXISTS taller;
CREATE DATABASE  taller;
USE taller;
CREATE TABLE  coche (
    id integer NOT NULL AUTO_INCREMENT,
    matricula varchar(10) DEFAULT NULL UNIQUE,
    marca varchar(20) DEFAULT NULL,
    modelo   varchar(20) DEFAULT NULL,
    tipo varchar(20) default null,
    PRIMARY KEY (id)
);

CREATE TABLE multa (
  id_multa integer unsigned NOT NULL AUTO_INCREMENT,
  precio DOUBLE NOT NULL,
  fecha DATE DEFAULT NULL,
  matricula varchar(7) NOT NULL,
  PRIMARY KEY (id_multa)
  ) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

