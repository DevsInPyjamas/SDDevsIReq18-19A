use acoes;
go
insert into Rol values('Coordinador General Proyectos', 1);
insert into Rol values('Responsable General Proyectos', 2);
insert into Rol values('Coordinador Proyecto', 3);
insert into Rol values('Responsable Economico', 4);
insert into Rol values('Becas', 5);
insert into Rol values('Economico', 6);
insert into Rol values('Usuario Raso', 7);
insert into Rol values('Gestor España', 8),
                      ('Coordinador Asociación España', 9),
                      ('Coordinador ACOES España', 10)

insert into TipoGasto(nombre) values ('Material Escolar'), ('Infraestructura'), ('Beca'), ('Comida')

insert into Asociacion(nombre, localizacion) values ('ACOES', 'HONDURAS');
insert into Asociacion(nombre, localizacion) values ('ACOES ESPAÑA', 'España');
insert into Asociacion(nombre, localizacion) values ('Broasos', 'MALAGA');
insert into Asociacion(nombre, localizacion) values ('Al·lotets', 'ILLES BALEARS');
insert into Asociacion(nombre, localizacion) values ('Nins', 'CATALUNYA');

insert into Empresa(nombre, direccion, nif)
values ('INDRA', 'Aqui Mismo 5 SN', '443322'), ('Ebury', 'Alli Mismo SN', '5533LOL'),
       ('UMA', 'En el otro lao SN', 'YEAHLOL'), ('SEQUEL', 'Im a bit drunk', '01-01-201'),
       ('Phace', 'No funciona mi programa', 'Si lol'), ('MEFJUS', 'Yeah', 'Lol');

insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_asociacion)
values('a@a.com', 'alkesst', '1234', 'Alejandro G.', 1, (select id from Asociacion where nombre like '%honduras')),
      ('b@b.com', 'pito', '1234', 'Manolo', 2, (select id from Asociacion where nombre like '%honduras')),
      ('c@c.com', 'rajoy', '1234', 'Mariano', 3, (select id from Asociacion where nombre like '%honduras')),
      ('d@d.com', 'albert', '1234', 'Albert', 3, (select id from Asociacion where nombre like '%honduras')),
      ('pablo@Casado.com', 'Pablo', '1234', 'Casado', 3, (select id from Asociacion where nombre like '%honduras')),
      ('hmm@hmm', 'Ala', '1234', 'EsGrande', 3, (select id from Asociacion where nombre like '%honduras')),
      ('bad@bad.com', 'XxxTentation', '1234', 'Muerto', 3, (select id from Asociacion where nombre like '%honduras')),
      ('bbunny@bbunny.com', 'Amorfoda', '1234', 'Dinero', 3, (select id from Asociacion where nombre like '%honduras')),
      ('wan@uma.es', 'wan', '1234', 'Juan', 4, (select id from Asociacion where nombre like '%honduras')),
      ('hola@hola.com', 'Bienvenido', '1234', 'Bienvenido', 4, (select id from Asociacion where nombre like '%honduras')),
      ('lol@lol.es', 'LolSer', '1234', 'Sergio', 4, (select id from Asociacion where nombre like '%honduras')),
      ('jajaja@jajaja.com', 'Gracioso', '1234', 'Gracioso', 4, (select id from Asociacion where nombre like '%honduras')),
      ('usuario@facilito.com', 'Xiaomi', '1234', 'NotFound', 4, (select id from Asociacion where nombre like '%honduras')),
      ('cani@cani.es', 'PinPan', '1234', 'TomaLacasitos', 4, (select id from Asociacion where nombre like '%honduras')),
      ('balears@acoes.com', 'kekes', '1234', 'Juan G.', 8, (select id from Asociacion where localizacion like 'illes balears')),
      ('ppcc@acoes.com', 'jejes', '1234', 'Carlos G.', 8, (select id from Asociacion where localizacion like 'catalunya')),
      ('malaga@acoes.com', 'malaga', '1234', 'Malaga User', 8, (select id from Asociacion where localizacion like 'malaga')),
      ('españa@acoes.com', 'españa', '1234', 'España user', 8, (select id from Asociacion where localizacion like 'españa')),
      ('cbaleares@a.com', 'mallorca', '1234', 'Joan Dot', 9, (select id from Asociacion where localizacion like 'illes balears')),
      ('cppcc@acoes.com', 'catalunya', '1234', 'CatCoord', 9, (select id from Asociacion where localizacion like 'catalunya')),
      ('cmalaga@acoes.com', 'malaga', '1234', 'MalagaCord', 9, (select id from Asociacion where localizacion like 'malaga')),
      ('cespaña@acoes.com', 'jj2019', '1234', 'Juan Jimenez', 10, (select id from Asociacion where nombre like 'acoes españa'))

insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia, isDeleted) values
('alejandro', 'Paco', 'Paca', 'Menendez', '17-09-09', '17-09-00', 0.0, 0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Pepe', 'Paco', 'Paca', 'Menendez', '17-09-09', '1-02-98', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Pedro', 'Juana', 'Juan', 'Gonzales', '18-02-07', '20-01-98', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Juan', 'Andrea','Paquito', 'Rivera Casado', 'Fue encontrado como tarzan','Pertenece a la comunidad de Honduras', 'Hombre', 'De vez en cuando le pica la cabeza', '26/11/2017', '12/12/2018' , 'Beca Normal','16/05/2005', '7.46' );
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Pablo', 'Francisca','Felix', 'Stalin Muha', 'Venia de un cartel de drogas de la serie de narcos','Pertenece a la comunidad de Colombia', 'Hombre', 'Tiene aires de gallego', '10/1/2015', '1/12/2018' , 'Beca San Miguel','18/06/2007', '5.4' );
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Kefla', 'Selena','Lonnely', 'Bush Obama', 'Encontrado en una universidad despues de un tiroteo','Pertenece a la comunidad de EEUU', 'Mujer', 'Naturaleza asustadiza', '14/09/2014', '1/7/2020', 'Beca Normal','1/01/2005', '9.6' );
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Avelino', 'Maria', 'AvelinoP', 'Gomez', '21-02-2000', '3-01-98', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Manolo', 'Carmen', 'Manolo', 'Ruiz', '1/03/2005', '25/05/2001', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Pedro', 'Rosa', 'Miguel', 'Amador', '10/07/1998', '20-01-1996', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Alejandro', 'Angela', 'Juan', 'Arenas', '21/12/2010', '17/08/2006', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Maria', 'Inma', 'Pablo', 'Borrego', '04/05/2015', '25/03/2010', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Inma', 'Inma', 'Pinito', 'Lapiz', '01/01/2012', '25/06/2009', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Claudia', 'Lucrecia', 'Rodolfo', 'Gomez', '12/12/2005', '14/02/1999', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Juan', 'Carmen', 'Manuel', 'Herrera', '12/05/2008', '01/07/2003', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Ignacio', 'Rocio', 'Borja', 'Estepa', '07/07/2007', '05/05/2005', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Samuel', 'Ursula', 'Ortega', 'Cano', '03/03/2004', '11/07/2000', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Macarena', 'Pepa', 'Ramon', 'Perez', '25/06/2001', '11/11/1996', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Rocio', 'Misa', 'Jose', 'Valiente', '16/03/2010', '11/08/2006', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Rosa', 'Rosa', 'Romario', 'Idel', '03/04/2008', '03/04/2005', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Edgar', 'Mari', 'Roberto', 'Cansino', '04/06/2014', '06/08/2010', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Pheteven', 'Samsa', 'Mariano', 'Stark', '06/05/2011', '15/10/2010', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Dario', 'Artya', 'Gonzalo', 'Treck', '01/05/2005', '28/10/2000', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Naruto', 'Sakura', 'Inoyi', 'Uzumaki', '26/10/2012', '06/10/2006', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Luffy', 'Nami', 'Ace', 'Python', '25/04/2012', '01/07/2009', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('Midoriya', 'Julia', 'Al-Migth', 'Otaku', '06/06/2006', '20/05/2000', 0.0);
insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, fechaEntrada, fechaNacimiento, notaMedia) values
('ElChavo', 'Juana', 'Mariano', 'Delocho', '08/08/2008', '07/12/2002', 0.0);

insert into TipoProyecto values ('Casa Populorum');
insert into TipoProyecto values ('CCJ');
insert into TipoProyecto values ('Colegio');


insert into Proyecto (nombre, ubicacion, tipoProyecto, project_coordinator, project_responsable, general_project_coordinator,
                      general_project_responsable) values
('Hombre', 'Tegulcipaga', 'CCJ', 'pablo@Casado.com', 'wan@uma.es', 'a@a.com', 'b@b.com');
insert into Proyecto (nombre, ubicacion, tipoProyecto, project_coordinator, project_responsable, general_project_coordinator,
                      general_project_responsable) values
('Escuela Tegulcipaga', 'Tegulcipaga', 'Colegio', 'd@d.com', 'hola@hola.com', 'a@a.com', 'b@b.com');
insert into Proyecto (nombre, ubicacion, tipoProyecto, project_coordinator, project_responsable, general_project_coordinator,
                      general_project_responsable) values
('Casa Tegulcipaga', 'Tegulcipaga', 'Casa Populorum', 'c@c.com', 'lol@lol.es', 'a@a.com', 'b@b.com');



insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5000', '17-09-09');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5001', '17-09-09');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5002', '18-02-07');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5003', '26/11/2017');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5004', '10/1/2015');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5005', '14/09/2014');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5006', '1/03/2005');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5007', '10/07/1998');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5008', '21/12/2010');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5009', '04/05/2015');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5010', '01/01/2012');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5011', '12/12/2005');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5012', '12/05/2008');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5013', '07/07/2007');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5014', '03/03/2004');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5015', '25/06/2001');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5016', '16/03/2010');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5017', '03/04/2008');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5018', '04/06/2014');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('1','5019', '06/05/2011');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5020', '01/05/2005');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5021', '26/10/2012');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5022', '25/04/2012');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('3','5023', '06/06/2006');
insert into Accion( id_proyecto, id_joven ,fecha_entrada) values ('2','5024', '08/08/2008');




insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('pedro@uma.es', 'gatobetis', '1234', 'Pedro', '5', 1);
insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('hey@hey.com', 'uwu', '1234', 'Hyem', '5', 2);
insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('otaku@uma.es', 'LOlplayer', '1234', 'Reckles', '5', 3);
insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('parejo@uma.com', 'parejo', '1234', 'Jesus P', '6', 1);
insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('pozo@pozo.com', 'pozo', '1234', 'Pozo', '6', 2);
insert into Usuario (email, usuario, password, nombre, rol_id, pertenece_proyecto)
values ('onichan@onichan.com', 'onix', '1234', 'Boruto', '6', 3);
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('av@a.es', 'av', '1234', 'Antonio', 7);

insert into socio(nombre, dni, mensualidad, asociacion, apellidos, fecha_nacimiento)
values('Alejandro', '76767676K', 200, 20002, 'apellido1', '1998-01-20');
insert into socio(nombre, dni, mensualidad, asociacion, apellidos, fecha_nacimiento)
values('WanGarcia', '67676767K', 150, 20001, 'apellido1', '1998-01-20');
insert into socio(nombre, dni, mensualidad, asociacion, apellidos, fecha_nacimiento)
values('Pedro', '123456789', 100, 20003, 'apellido1', '1998-01-20');
insert into socio(nombre, dni, mensualidad, asociacion, apellidos, fecha_nacimiento)
values('Manuel', 'DeFalla:)', 150, 20002, 'apellido1', '1998-01-20'),
      ('Manel',  '43565656K', 0, (select id from Asociacion where localizacion like 'cat%'), 'Garau Fons' ,'1959-01-10');

insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_inicio)
values(15000, 5000, 15, '20-01-2010');
insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_inicio)
values(15001, 5001, 15, '20-01-2010');
insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_inicio)
values(15002, 5002, 15, '20-01-2008');
insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_inicio)
values(15003, 5003, 15, '20-01-2018');
insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_fin, fecha_inicio)
values(15003, 5005, 15, '20-01-2016', '20-01-2015'),
      (15000, 5006, 150, '20-01-2005', '20-01-2001'),
      (15002, 5017, 150, '20-01-2018', '20-01-2011');

insert into Transaccion(emisor, concepto, cantidad, tipoGasto, proyecto, id_empresa, socio_donante)
values ('Juan Castilla', 'Nuevo material escolar', 2500, (select id from dbo.TipoGasto where nombre like 'Material escolar')
         , 2, (select id from empresa where nombre = 'INDRA')
         , null), ('Ks', 'Comida', 150, (select id from dbo.TipoGasto where nombre like 'comida'), 3, null,
                   (select id from socio where nombre = 'Alejandro')),
       ('MEFJUS', 'Infraestructura', 250000, (select id from tipoGasto where nombre like 'infraestructura'),
        1, (select id from empresa where nombre = 'MEFJUS'), null)