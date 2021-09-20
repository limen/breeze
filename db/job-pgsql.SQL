create table brz_job (
job_id bigint unique,
job_name varchar(64) not null default '',
schedule_at timestamp with time zone,
executor_id int not null,
status smallint not null default 0,
job_params varchar(1024),
created_at timestamp with time zone default current_timestamp,
primary key (job_id)
);

create table brz_job_exec_log (
id bigserial unique,
job_id bigint not null,
status smallint not null,
context varchar(2048),
elapse int not null,
exec_at timestamp with time zone,
created_at timestamp with time zone default current_timestamp,
primary key (id)
);
create index idx_job_id on brz_job_exec_log using btree (job_id);

create table brz_executor (
id serial unique,
name varchar(64),
type varchar(16),
status smallint not null default 0,
config varchar(1024),
params varchar(1024),
created_at timestamp with time zone default current_timestamp,
updated_at timestamp with time zone default current_timestamp,
primary key (id)
);

create table brz_app_credential (
id serial unique,
app_id varchar(256) not null unique,
app_name varchar(64),
token varchar(256),
privilege int not null,
type varchar(16),
status smallint not null default 0,
created_at timestamp with time zone default current_timestamp,
updated_at timestamp with time zone default current_timestamp,
primary key (id)
);

create table brz_audit_log (
id serial unique,
app_id varchar(256) not null,
ip varchar(16),
uri varchar(64),
req varchar(1024),
resp varchar(1024),
created_at timestamp with time zone default current_timestamp,
primary key (id)
);
create index idx_created_at on brz_audit_log using btree (created_at);

-- 测试用, 注意修改token
insert into brz_app_credential (app_id, app_name, token, privilege) values ('brz_admin', 'admin', 'Brz_admin', 4096);
