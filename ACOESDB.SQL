create database [ACOES]
GO
use [ACOES]
go
-- tables
create table [dbo].[Usuario](
  email varchar(50) not null,
  usuario varchar(50) not null,
  password varchar(50) not null,
  nombre varchar(50) not null,
  CONSTRAINT PK_tLibro PRIMARY KEY CLUSTERED (email ASC)
) ON [Primary];


create table [dbo].[Rol](
  nombre varchar(50) not null,
  id int not null
  constraint pk_rol primary key clustered ([id] ASC)
) on [Primary]


create table [dbo].[Jovenes](
                              id int not null identity(5000,1),
  nombre varchar(50) not null,
  nombreMadre varchar(50),
  nombrePadre varchar(50),
  apellidos varchar(50),
  historial varchar(500),
  datosComunidad varchar(100),
  genero varchar(10),
  observaciones varchar(1000),
  fechaEntrada datetime not null,
  fechaBaja datetime,
  beca varchar(50),
  fechaNacimiento datetime not null,
  notaMedia real not null,
  constraint pk_jovenes primary key clustered (id ASC)
) on [Primary];


create table [dbo].[Proyecto](
  id int not null identity(1, 1),
  nombre varchar(50) not null,
  ubicacion varchar(50) not null,
  tipoProyecto varchar(50) not null,
  constraint pk_Proyecto primary key clustered (id ASC)
) on [Primary];

create table [dbo].[Accion](
  id_proyecto int not null,
  id_joven int not null,
  fecha_entrada datetime not null,
  fecha_salida datetime,
  constraint pk_Accion primary key clustered (id_proyecto, id_joven)
) on [Primary];

create table [dbo].[RolJovenesRelation](
  id_rol int not null,
  id_jovenes int not null,
  constraint pk_RolJovenesRelation primary key clustered (id_rol, id_jovenes)
) on [Primary];

-- relations

alter table Usuario add rol_id int not null;
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Role_User] FOREIGN KEY([rol_id]) references Rol ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add project_coordinator varchar(50) not null unique;
alter table Proyecto with check add constraint [FK_Project_Coordinator] foreign key([project_coordinator]) references Usuario;
alter table Proyecto add project_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_Responsable] foreign key([project_responsable]) references Usuario;
alter table Proyecto add general_project_coordinator varchar(50) not null unique;
alter table Proyecto with check add constraint [FK_GeneralProject_Coordinator] foreign key([general_project_coordinator]) references Usuario;
alter table Proyecto add general_project_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_GeneralProject_Responsable] foreign key([general_project_responsable]) references Usuario;
alter table Accion with check add constraint [FK_Action_Joven] foreign key([id_joven]) references Jovenes;
alter table Accion with check add constraint [FK_Action_Project] foreign key([id_proyecto]) references Proyecto;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Jovenes] foreign key ([id_jovenes]) references Jovenes;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Rol] foreign key ([id_rol]) references Rol;


-- data set
insert into Rol values('Coordinador General Proyectos', 1);
insert into Rol values('Responsable General Proyectos', 2);
insert into Rol values('Coordinador Proyecto',3);
insert into Rol values('Responsable Economico',4);
insert into Rol values('Becas',5);
insert into Rol values('Economico',6);
insert into Rol values('Usuario Raso',7);


insert into Usuario (email, usuario, password, nombre, rol_id)
values('a@a.com', 'alkesst', '1234', 'Alejandro G.', '1');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('pito@gmail.com', 'pito', '1234', 'Manolo', '2');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('m@rajoy.com', 'rajoy', '1234', 'Mariano', '3');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('albert@rivera.com', 'albert', '1234', 'Albert', '3');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('pablo@Casado.com', 'Pablo', '1234', 'Casado', '3');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('wan@uma.es', 'wan', '1234', 'Juan', '4');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('pedro@uma.es', 'gatobetis', '1234', 'Pedro', '5');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('parejo@uma.com', 'parejo', '1234', 'Jesus P', '6');
insert into Usuario (email, usuario, password, nombre, rol_id)
values ('av@a.es', 'av', '1234', 'Antonio', '7');


insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Juan', 'Andrea','Paquito', 'Rivera Casado', 'Fue encontrado como tarzan','Pertenece a la comunidad de Honduras', 'Hombre', 'De vez en cuando le pica el culo', '26/11/2017', '12/12/2018' , 'Beca Normal','16/05/2005', '7.46' );

insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Pablo', 'Francisca','Felix', 'Stalin Muha', 'Venia de un cartel de drogas de la serie de narcos','Pertenece a la comunidad de Colombia', 'Hombre', 'Tiene aires de gallego', '10/1/2015', '1/12/2018' , 'Beca San Miguel','18/06/2007', '5.4' );

insert into Jovenes (nombre, nombreMadre, nombrePadre, apellidos, historial, datosComunidad, genero, observaciones, fechaEntrada, fechaBaja, beca,fechaNacimiento ,notaMedia)
values('Kefla', 'Selena','Lonnely', 'Bush Obama', 'Encontrado en una universidad despues de un tiroteo','Pertenece a la comunidad de EEUU', 'Mujer', 'Naturaleza asustadiza', '14/09/2014', '1/7/2020', 'Beca Normal','1/01/2005', '9.6' );

insert into Proyecto (nombre, ubicacion, tipoProyecto,project_coordinator,project_responsable,general_project_coordinator,general_project_responsable)
values ('RosaliaCentro','Estepa','CasaPopulorum','m@rajoy.com','wan@uma.es','a@a.com','pito@gmail.com');
insert into Proyecto (nombre, ubicacion, tipoProyecto,project_coordinator,project_responsable,general_project_coordinator,general_project_responsable)
values ('Colegio Juan Corrales','Gilena','Colegio','albert@rivera.com','wan@uma.es','a@a.com','pito@gmail.com');
insert into Proyecto (nombre, ubicacion, tipoProyecto,project_coordinator,project_responsable,general_project_coordinator,general_project_responsable)
values ('CCJ Betis','Sevilla','CCJ','pablo@Casado.com','wan@uma.es','a@a.com','pito@gmail.com');


