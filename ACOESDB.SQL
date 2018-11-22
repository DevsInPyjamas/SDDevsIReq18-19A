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
  constraint pk_rol primary key clustered ([nombre] ASC)
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
  id_rol varchar(50) not null,
  id_jovenes int not null,
  constraint pk_RolJovenesRelation primary key clustered (id_rol, id_jovenes)
  ) on [Primary];

-- relations

alter table Usuario add rol_id varchar(50) not null;
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Role_User] FOREIGN KEY([rol_id]) references Rol ON UPDATE CASCADE ON DELETE CASCADE;
alter table Usuario add general_project_coordinator int unique not null;
alter table Usuario with check add constraint [FK_User_ProjectGeneralCoordination] foreign key([general_project_coordinator]) references Proyecto;
alter table Usuario add project_responsable int unique not null;
alter table Usuario with check add constraint [FK_User_ProjectGeneralResponsable] foreign key([project_responsable]) references Proyecto;
alter table Proyecto add project_coordinator varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_Coordinator] foreign key([project_coordinator]) references Usuario;
alter table Proyecto add project_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_Responsable] foreign key([project_responsable]) references Usuario;
alter table Accion with check add constraint [FK_Action_Joven] foreign key([id_joven]) references Jovenes;
alter table Accion with check add constraint [FK_Action_Project] foreign key([id_proyecto]) references Proyecto;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Jovenes] foreign key ([id_jovenes]) references Jovenes;
alter table RolJovenesRelation with check add constraint [FK_RolJovenesRelation_Rol] foreign key ([id_rol]) references Rol;