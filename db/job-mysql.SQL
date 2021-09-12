create table brz_job (
  job_id bigint not null,
  job_name varchar(32) not null default '',
  schedule_at datetime not null,
  executor_id int not null,
  status tinyint not null default 0,
  job_params varchar(1024),
  created_at datetime default current_timestamp,
  primary key (job_id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

create table brz_job_exec_log (
  id bigint not null auto_increment,
  job_id bigint not null,
  status tinyint not null,
  context varchar(2048),
  elapse int not null,
  exec_at datetime,
  created_at datetime,
  primary key (id),
  key idx_job_id (job_id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

create table brz_executor (
  id int not null auto_increment,
  name varchar(64),
  type varchar(16),
  status tinyint not null default 0,
  config varchar(1024),
  params varchar(1024),
  created_at datetime default current_timestamp,
  updated_at datetime default current_timestamp on update current_timestamp,
  primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

create table brz_app_credential (
  id int not null auto_increment,
  app_id varchar(256) not null,
  app_name varchar(64),
  token varchar(256),
  privilege int not null,
  type varchar(16),
  status tinyint not null default 0,
  created_at datetime default current_timestamp,
  updated_at datetime default current_timestamp on update current_timestamp,
  primary key (id),
  unique key un_app_id (app_id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

create table brz_audit_log (
  id int not null auto_increment,
  app_id varchar(256) not null,
  ip varchar(16),
  uri varchar(64),
  req varchar(1024),
  resp varchar(1024),
  created_at datetime default current_timestamp,
  primary key (id),
  key idx_created_at (created_at)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;


