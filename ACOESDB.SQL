use master;
if exists (select * from sys.databases where name = 'ACOES') begin
  drop database acoes;
end
go
create database acoes;
go
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
  fechaEntrada date not null,
  fechaBaja date,
  beca varchar(50),
  fechaNacimiento date not null,
  isDeleted bit not null default 0,
  notaMedia real not null,
  constraint pk_jovenes primary key clustered (id ASC)
) on [Primary];


create table [dbo].[Proyecto](
  id int not null identity(1, 1),
  nombre varchar(50) not null,
  ubicacion varchar(50) not null,
  tipoProyecto varchar(50) not null,
  isDeleted bit not null default 0,
  constraint pk_Proyecto primary key clustered (id ASC)
) on [Primary];

create table [dbo].[Accion](
  id_proyecto int not null,
  id_joven int not null,
  fecha_entrada date not null,
  fecha_salida date,
  constraint pk_Accion primary key clustered (id_proyecto, id_joven)
) on [Primary];

create table [dbo].[RolJovenesRelation](
  id_rol int not null,
  id_jovenes int not null,
  constraint pk_RolJovenesRelation primary key clustered (id_rol, id_jovenes)
) on [Primary];

create table TipoProyecto(
  nombre varchar(50) not null,
  constraint Pk_TipoProyecto primary key clustered (nombre)
);

create table Asociacion(
  id int not null identity(20000,1),
  nombre varchar(50) not null,
  localizacion varchar(50),
  isDeleted bit not null default 0,
  constraint Pk_Asociacion primary key clustered (id)
);

create table Socio(
  id int not null identity(15000,1),
  nombre varchar(50) not null,
  dni varchar(9) not null unique,
  telefono int,
  direccion varchar(50),
  codigo_postal int,
  provincia varchar(50),
  poblacion varchar(50),
  mensualidad money,
  isDeleted bit not null default 0,
  apellidos varchar(50) not null,
  fecha_nacimiento date not null,
  email varchar(50),
  constraint Pk_Socio primary key clustered (id)
);

create table Transaccion(
  id int not null identity(10000,1),
  emisor varchar(50) not null,
  concepto varchar(50),
  cantidad money not null,
  isDeleted bit not null default 0,
  tipoGasto int not null,
  isValidated bit not null default 0,
  beneficiario int not null,
  tablaBeneficiario varchar(50) not null,
  fecha date not null,
  whatKindOfShitIsThis bit default null,
  constraint Pk_Transaccion primary key clustered (id)
);

create table ApadrinarJoven(
  id int not null identity(30000, 1),
  apadrinador_id int not null,
  joven_id int not null,
  cuota money not null,
  fecha_inicio date not null,
  fecha_fin date,
  constraint pk_ApadrinarJoven primary key clustered (id)
);

create table Empresa(
  nombre varchar(50) not null,
  id int not null identity(25000, 1),
  direccion varchar(50) not null,
  nif varchar(9) not null,
  constraint Pk_Empresa primary key clustered (id)
);

create table TipoGasto(
  id int not null identity(35000, 1),
  nombre varchar(50) not null,
  constraint Pk_TipoGasto primary key clustered (id)
);

create table Colaborador(
  id int not null identity(40000, 1),
  nombre varchar(50),
  pertenece_proyecto int not null,
  constraint pk_colaborador PRIMARY KEY clustered (id),
  constraint [FK_Colaborador_asociacion] foreign key([pertenece_proyecto]) references Proyecto
);
-- relations

alter table Usuario add rol_id int not null;
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Role_User] FOREIGN KEY([rol_id]) references Rol ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add project_coordinator varchar(50) not null unique;
alter table Proyecto with check add constraint [FK_Project_Coordinator] foreign key([project_coordinator]) references Usuario;
alter table Proyecto add project_responsable varchar(50) not null unique;
alter table Proyecto with check add constraint [FK_Project_Responsable] foreign key([project_responsable]) references Usuario;
alter table Proyecto add general_project_coordinator varchar(50) not null;
alter table Proyecto with check add constraint [FK_GeneralProject_Coordinator] foreign key([general_project_coordinator]) references Usuario;
alter table Proyecto add general_project_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_GeneralProject_Responsable] foreign key([general_project_responsable]) references Usuario;
alter table Accion with check add constraint [FK_Action_Joven] foreign key([id_joven]) references Jovenes;
alter table Accion with check add constraint [FK_Action_Project] foreign key([id_proyecto]) references Proyecto;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Jovenes] foreign key ([id_jovenes]) references Jovenes;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Rol] foreign key ([id_rol]) references Rol;
alter table Usuario add pertenece_proyecto int;
alter table usuario with check add constraint [FK_pertenece_proyecto] foreign key([pertenece_proyecto]) references Proyecto on delete cascade;
alter table Usuario add pertenece_asociacion int;
alter table Usuario with check add constraint [FK_Pertenece_asociacion] foreign key([pertenece_asociacion]) references Asociacion;
alter table dbo.Proyecto add constraint [FK_TipoProyecto] foreign key([tipoProyecto]) references TipoProyecto on delete cascade on update cascade;
alter table Transaccion add proyecto int not null;
alter table Transaccion with check add constraint [FK_Proyecto] foreign key([proyecto]) references Proyecto on delete cascade;
alter table Socio add asociacion int not null;
alter table Socio with check add constraint [FK_Asociacion_Socio] foreign key([asociacion]) references Asociacion;
alter table ApadrinarJoven with check add constraint [FK_Apadrinador_Apadrinar] foreign key ([apadrinador_id]) references Socio;
alter table ApadrinarJoven with check add constraint [FK_Joven_Apadrinar] foreign key ([joven_id]) references Jovenes;
alter table Transaccion with check add constraint [FK_Transaccion_TipoGasto] foreign key ([tipoGasto]) references TipoGasto;