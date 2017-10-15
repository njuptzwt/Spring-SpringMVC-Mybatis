create database seckill;
use seckill;
#建立秒杀商品表格
create table seckill(
seckill_id bigint not null auto_increment comment '秒杀商品的id',
name varchar(120) not null comment '秒杀商品名称',
number int not null comment '库存的数量',
start_time timestamp not null comment '开始时间',
end_time timestamp not null comment '结束时间',
create_time timestamp not null default current_timestamp comment '秒杀商品的创建时间',
primary key(seckill_id),
#建立索引的操作函数
key index_start_time(start_time),
key index_end_time(end_time),
key index_create_time(create_time)
)Engine=innodb auto_increment 1000 default charset=utf8 comment='秒杀库存表';

insert into seckill(name,number,start_time,end_time)values('2000元秒杀iphone8','100','2017-10-28 00:00:00','2017-10-29 00:00:00')
,('1000元秒杀iphone4','100','2017-10-28 00:00:00','2017-10-29 00:00:00')
,('500元秒杀小米6','100','2017-10-28 00:00:00','2017-10-29 00:00:00')
,('200元秒杀小米node','100','2017-10-28 00:00:00','2017-10-29 00:00:00');

#秒杀成功明细表alter
#用户的相关信息
create table success_killed(
seckill_id bigint not null comment '秒杀商品的id',
userphone bigint not null comment '用户的信息',
state tinyint not null default -1 comment '状态表示：-1表示无效，0表示成功，1表示已付款，2表示已发货',
create_time timestamp  not null default current_timestamp comment '订单的创建时间',
primary key(seckill_id,userphone),
key index_create_time(create_time)
)engine=innodb charset utf8 comment '秒杀成功明细表';
#手写ddl数据库的操作有助于记录产品每次上线的更改；