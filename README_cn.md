# Breeze

A light and enjoyable job broker.

## 特性

- 支持即时任务和延时任务
- 可以单例部署或集群部署
- 每个任务都有绑定的执行器，指向真正执行任务的代理方

## 轻量

- 在单例模式下，只依赖mysql/pgsql
- 在集群模式下，增加了对redis的依赖
- 资源消耗低，内存占用通常低于500MB

## 安全性

- 每一个改动都有审计日志
- 使用token进行认证

## 运行

已测试的环境

- Java 8
- MySQL 8.0
- Redis 6.0

### 创建数据表

见 [db/job-mysql.SQL](https://github.com/limen/breeze/blob/master/db/job-mysql.SQL)

### 配置数据源

修改 application.yml

## 性能

## 概念

### 执行器

真正执行任务的代理，例如一个http服务.

### 协调者

负责协调时钟，向JSD分配任期

### Job scanner dispatcher(JSD)

接受协调者分发的任期，分配给JS

### Job scanner(JS)

扫描任务序列，将任务ID投递至任务队列

### Job queue(JQ)

保存将要执行的任务ID，FIFO

### 任务消费

利用线程池提升消费速度

## 延时任务的实现

任务被一个长整形的唯一ID标识，该ID与执行时间是正相关的.

任务ID的计算基于执行时间和一个自增的序列号。假定任务的执行时间戳为1632041675，序列号为1，任务ID计算如下

```
1632041675 << 20 + 1
```

拿到一个时间戳，我们就可以计算出该时间戳对应的最小ID值和最大ID值

```
1632041675 << 20 + 1
1632041675 << 20 + (1 << 20 - 1)
```

真相即将揭晓.

```
任务存储在一张表中，该表的主键是任务ID. 当时钟到达一个时间戳时，JS开始基于计算出的最小ID和最大ID进行范围扫描
```

就是这样!

## 集群的实现 

集群使用Redis进行信息同步。

在集群中，每个节点都可以是协调者或跟随者，并且所有的节点都是平等的。

跟随者负责消费任务队列，并随时准备成为协调者。

协调者同时也是跟随者，但需要做额外的工作。它还负责扫描任务表，将任务ID投递到队列。

协调者的任期很短，通常为1秒钟。


## 接口

### 创建执行器

参数

- id 可选，可以指定ID，需确保唯一性
- name 执行器名称
- type 执行器类型，目前支持http, stdout
- config 执行器详细参数

config参数

- uri 接口路径
- codeName 接口返回的code码字段名
- codeOK code码正常值
- method 请求方法
- headers 请求头
- params 请求参数

返回：执行器ID

```
curl --location --request POST '127.0.0.1:8080/executor/create' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin' \
--header 'Content-Type: application/json' \
--data-raw '{"name":"test","type":"http","config":"{\"uri\":\"http:\\\/\\\/127.0.0.1:8080\\\/test\\\/echo\",\"method\":\"POST\",\"codeName\":\"code\",\"codeOK\":0,\"headers\":{\"AppId\":\"h2admin\",\"AppToken\":\"H2admin\"}}","params":"{\"method\":\"POST\",\"codeName\":\"code\",\"codeOK\":0}"}'
```

### 创建任务

参数

- execAfter 延时时间长度，单位秒，0代表立即执行，超过3600秒需使用execAt参数
- execAt  执行时间，和execAfter二选一，优先级高于execAfter
- jobName 任务名称
- executorId 执行器ID
- params 任务参数

返回：任务ID

即时任务
```
curl --location --request POST '127.0.0.1:8080/job/create' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "execAfter":0,
    "jobName":"test",
    "executorId":1,
    "jobParams":""
}'
```

延时任务
```
curl --location --request POST '127.0.0.1:8080/job/create' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "execAfter":600,
    "jobName":"test",
    "executorId":1,
    "jobParams":""
}'
```
```
curl --location --request POST '127.0.0.1:8080/job/create' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "execAt":"2021-10-01 12:00:00",
    "jobName":"test",
    "executorId":1,
    "jobParams":""
}'
```

### 查询任务执行日志

参数

- jobId 任务ID

```
curl --location --request GET '127.0.0.1:8080/job/logs?jobId=1711254935699457' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin'
```

### 查询审计日志

参数

- fromTime 起始时间 
- toTime 截止时间
- appId 
- uri 接口路径, 如/job/create
- fromId 分界id值 
- limit 返回数量

```
curl --location --request GET '127.0.0.1:8080/auditLog/list?fromTime=2021-09-20 08:00:00&toTime=2021-09-20 09:00:00&appId=&uri&limit=1&fromId=3' \
--header 'AppId: brz_admin' \
--header 'AppToken: Brz_admin'
```

## 权限管理

数据表 brz_app_credential

字段

- app_id - App Id
- token - 令牌
- privilege - 权限
- status - 0启用，1禁用

权限值，多个权限值取或

- 4096 超级权限
- 128 创建
- 256 删除
- 512 更新
