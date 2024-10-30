DROP DATABASE IF EXISTS taller;
CREATE DATABASE  taller;
USE taller;
CREATE TABLE  coche (
    id integer NOT NULL AUTO_INCREMENT,
    matricula varchar(10) DEFAULT NULL,
    marca varchar(20) DEFAULT NULL,
    modelo   varchar(20) DEFAULT NULL,
    tipo varchar(20) default null,
    PRIMARY KEY (id)
);
