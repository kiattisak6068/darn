# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table amphoe (
  id                            integer not null,
  th                            varchar(255),
  en                            varchar(255),
  province_id                   integer,
  constraint pk_amphoe primary key (id)
);
create sequence amphoe_seq;

create table darn_info (
  id                            integer not null,
  lat                           varchar(255),
  lng                           varchar(255),
  name                          varchar(255),
  constraint pk_darn_info primary key (id)
);
create sequence darn_info_seq;

create table province (
  id                            integer not null,
  th                            varchar(255),
  en                            varchar(255),
  constraint pk_province primary key (id)
);
create sequence province_seq;

alter table amphoe add constraint fk_amphoe_province_id foreign key (province_id) references province (id) on delete restrict on update restrict;
create index ix_amphoe_province_id on amphoe (province_id);


# --- !Downs

alter table amphoe drop constraint if exists fk_amphoe_province_id;
drop index if exists ix_amphoe_province_id;

drop table if exists amphoe;
drop sequence if exists amphoe_seq;

drop table if exists darn_info;
drop sequence if exists darn_info_seq;

drop table if exists province;
drop sequence if exists province_seq;

