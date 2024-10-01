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
