DROP DATABASE IF EXISTS Proyecto;
CREATE DATABASE Proyecto;
Use Proyecto;


CREATE TABLE Usuarios(
Id int unsigned auto_increment primary key,
nombre varchar(30),
contrasenia varchar(20)

 );
 INSERT INTO Usuarios  VALUES
 (1,"admin","admin"),
 (2,"root","toor");

CREATE TABLE Cubos(
Id int unsigned auto_increment primary key,
tipo varchar(30),
modelo varchar(20),
precio double

 );
 INSERT INTO Usuarios  VALUES
 (1,"2x2","normal", 6.81),
 (2,"2x2","mirror", 8.99),
 (3,"3x3","normal", 12.84),
 (4,"3x3","mirror", 9.98),
 (5,"3x3","gear", 15),
 (6,"3x3","windmill", 10.99),
 (7,"2x2x3","normal", 5.25),
 (8,"2x2x3","banana", 7.90),
 (9,"piraminx","normal", 9.99),
 (10,"megaminx","normal", 12.99);
