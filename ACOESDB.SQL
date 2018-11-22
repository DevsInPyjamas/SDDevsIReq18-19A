USE [master]

create database [ACOES]
GO

use [ACOES]
go

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
-- tables
create table [dbo].[Usuario](
  email varchar(50) not null,
  usuario varchar(50) not null,
  password varchar(50) not null,
  nombre varchar(50) not null,
  CONSTRAINT PK_tLibro PRIMARY KEY CLUSTERED (email ASC)
) ON [Primary];
go

create table [dbo].[Rol](
  nombre varchar(50) not null,
  id int not null
    constraint pk_rol primary key clustered ([id] ASC)
) on [Primary]
go

create table [dbo].[Jovenes](
  id varchar(50) not null,
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
go

create table [dbo].[Proyecto](
  id int not null,
  nombre varchar(50) not null,
  ubicacion varchar(50) not null,
  tipoProyecto varchar(50) not null,
  constraint pk_Proyecto primary key clustered (id ASC)
) on [Primary];

-- relations

alter table Usuario add rol_id int not null;
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Role_User] FOREIGN KEY([rol_id]) references Rol ON UPDATE CASCADE ON DELETE CASCADE;
alter table Usuario add general_project_coordinator int;
alter table Usuario with check add constraint [FK_User_ProjectGeneralCoordination] foreign key([general_project_coordinator]) references Proyecto ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add general_coordinator varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_GeneralCoordination] foreign key([general_coordinator]) references Usuario ON UPDATE CASCADE ON DELETE CASCADE;
alter table Usuario add project_responsable int;
alter table Usuario with check add constraint [FK_User_ProjectGeneralResponsable] foreign key([project_responsable]) references Proyecto ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add general_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_GeneralResponsable] foreign key([general_responsable]) references Usuario ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add project_coordinator varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_Coordinator] foreign key([project_coordinator]) references Usuario ON UPDATE CASCADE ON DELETE CASCADE;
alter table Proyecto add project_responsable varchar(50) not null;
alter table Proyecto with check add constraint [FK_Project_Responsable] foreign key([project_responsable]) references Usuario ON UPDATE CASCADE ON DELETE CASCADE;