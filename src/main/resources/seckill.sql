#秒杀系统的数据库设计
CREATE DATABASE SECONDKill;
use SECONDKILL;
#建立表格的操作
CREATE TABLE seckill(
seckill_id bigint not null auto_inrement comment '商品库存id'
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT="秒杀系统的数据库表格"