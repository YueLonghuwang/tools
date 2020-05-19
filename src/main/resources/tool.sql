/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.27-log : Database - tool
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tool` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tool`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`password`) values 
(123,'xzq123','xzq123');

/*Table structure for table `file_pool` */

DROP TABLE IF EXISTS `file_pool`;

CREATE TABLE `file_pool` (
  `id` varchar(60) NOT NULL,
  `md5` varchar(60) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `size` varchar(40) DEFAULT NULL,
  `localPath` varchar(60) DEFAULT NULL,
  `createTime` varchar(40) DEFAULT NULL,
  `fileName` varchar(200) DEFAULT NULL,
  `filePath` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `file_pool` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` varchar(11) NOT NULL,
  `pattern` varchar(255) DEFAULT NULL COMMENT '权限列表接口',
  `patternZh` varchar(255) DEFAULT NULL COMMENT '权限列表中文描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`id`,`pattern`,`patternZh`) values 
('10','/api/v1/pool/addPool','添加工具'),
('11','/api/v1/pool/arrayFile','自定义工具列表'),
('12','/api/v1/pool/updatePool','根据id修改工具'),
('13','/api/v1/pool/selectOne','根据文件名称查询'),
('14','/api/v1/pool/selectTwo','根据名称和使用范围查询'),
('15','/api/v1/pool/delete','根据id删除工具'),
('4','/api/v1/user/db/addUser','添加用户'),
('5','/api/v1/user/db/updateUser','修改用户'),
('6','/api/v1/role/db/list','角色列表'),
('7','/api/v1/menu/db/findMenuByRole','角色权限'),
('8','/api/v1/role/db/edit','添加/修改角色'),
('9','/api/v1/pool/findByIdPool','根据id查询工具');

/*Table structure for table `menu_role` */

DROP TABLE IF EXISTS `menu_role`;

CREATE TABLE `menu_role` (
  `mid` varchar(50) DEFAULT NULL COMMENT 'menu表外键',
  `rid` varchar(50) DEFAULT NULL COMMENT 'role表外键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `menu_role` */

insert  into `menu_role`(`mid`,`rid`) values 
('4','2'),
('5','2'),
('7','2'),
('6','3'),
('8','2'),
('6','de8bba1a-d97f-4904-8b66-74df7db32791');

/*Table structure for table `pe_information` */

DROP TABLE IF EXISTS `pe_information`;

CREATE TABLE `pe_information` (
  `id` varchar(40) NOT NULL,
  `scope` varchar(30) DEFAULT NULL COMMENT '使用范围',
  `copyright` varchar(30) DEFAULT NULL COMMENT '版权信息',
  `summarize` varchar(30) DEFAULT NULL COMMENT '功能概述',
  `date` varchar(30) DEFAULT NULL COMMENT '日期',
  `provided` varchar(30) DEFAULT NULL COMMENT '提供人',
  `developers` varchar(30) DEFAULT NULL COMMENT '开发商',
  `pool_described` varchar(30) DEFAULT NULL COMMENT '工具描述',
  `status` int(11) DEFAULT NULL COMMENT '工具的发布和撤销',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pe_information` */

/*Table structure for table `pe_role` */

DROP TABLE IF EXISTS `pe_role`;

CREATE TABLE `pe_role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `nameZh` varchar(32) DEFAULT NULL,
  `companyId` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `pe_role` */

insert  into `pe_role`(`id`,`name`,`nameZh`,`companyId`) values 
('1','provide','工具提供者','1'),
('2','admin','系统管理员','1'),
('3','user','工具使用者','1'),
('de8bba1a-d97f-4904-8b66-74df7db32791','xxx','营销员','1');

/*Table structure for table `pe_role_user` */

DROP TABLE IF EXISTS `pe_role_user`;

CREATE TABLE `pe_role_user` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pe_role_user` */

insert  into `pe_role_user`(`user_id`,`role_id`) values 
('45bfcd93-4f38-4f10-a465-a6efc58f279f','0c3b1bf0-04e3-4a4a-ab50-e1d4975081d6'),
('45bfcd93-4f38-4f10-a465-a6efc58f279f','123');

/*Table structure for table `pe_user` */

DROP TABLE IF EXISTS `pe_user`;

CREATE TABLE `pe_user` (
  `id` varchar(60) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `pe_user` */

insert  into `pe_user`(`id`,`username`,`password`,`enabled`,`locked`,`company_id`) values 
('1','provider','$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq',1,0,'1'),
('2','admin','$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq',1,0,'1'),
('3','user','$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq',1,0,'1');

/*Table structure for table `tb_classifiedmanagement` */

DROP TABLE IF EXISTS `tb_classifiedmanagement`;

CREATE TABLE `tb_classifiedmanagement` (
  `id` varchar(40) NOT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_classifiedmanagement` */

insert  into `tb_classifiedmanagement`(`id`,`type`) values 
('1','武器类'),
('2','商品类'),
('3','服饰类');

/*Table structure for table `tb_file` */

DROP TABLE IF EXISTS `tb_file`;

CREATE TABLE `tb_file` (
  `id` varchar(40) NOT NULL,
  `md5` varchar(40) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `size` varchar(40) DEFAULT NULL,
  `localPath` varchar(200) DEFAULT NULL,
  `createTime` varchar(30) DEFAULT NULL,
  `fileName` varchar(200) DEFAULT NULL,
  `filePath` varchar(300) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `toolsbaseinfo_id` varchar(40) DEFAULT NULL COMMENT '工具的基本信息表id',
  `toolsstorage_id` varchar(40) DEFAULT NULL COMMENT '工具名称和路径id',
  `versionmanagement_id` varchar(40) DEFAULT NULL COMMENT '版本管理id',
  `classifiedmanagement_id` varchar(40) DEFAULT NULL COMMENT '类型id',
  `updownloadmanagement_id` varchar(40) DEFAULT NULL COMMENT '附件id',
  `averageScore` float DEFAULT NULL COMMENT '平均分',
  `downloadStatistics` int(11) DEFAULT NULL COMMENT '下载统计',
  `toolsevaluate_Id` varchar(40) DEFAULT NULL COMMENT '评价表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_file` */

insert  into `tb_file`(`id`,`md5`,`type`,`size`,`localPath`,`createTime`,`fileName`,`filePath`,`state`,`toolsbaseinfo_id`,`toolsstorage_id`,`versionmanagement_id`,`classifiedmanagement_id`,`updownloadmanagement_id`,`averageScore`,`downloadStatistics`,`toolsevaluate_Id`) values 
('401e0c52-718c-45ca-8b75-441fba2ed565','911fa5389cacd121838c5b7507ad1c87','jpg','2671410','C:\\Users\\xzq52\\Desktop\\json\\bbb\\125.jpg','2020-05-05 10:45:55.604','30.jpg','C:\\Users\\xzq52\\Desktop\\json\\bbb\\125.jpg',0,'73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','c3e3945e-5bfb-4d5b-baab-88581a2920f2','6884c990-07dc-4b09-a8bf-04ab4af41052',NULL,'c6a03b9b-bf7a-4ba4-a9fa-7400a48138da',1,4,NULL),
('48ff76a9-7a2a-4689-94ba-a7495fd25294','4e61233d1238814dd1da716d65c9433a','jpg','2327727','C:\\Users\\xzq52\\Desktop\\json\\bbb\\124.jpg','2020-05-05 07:09:52.426','39.jpg','C:\\Users\\xzq52\\Desktop\\json\\bbb\\124.jpg',0,'73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','454ddd36-fe01-4282-b067-6635a6824d83','d03fb94a-0b0e-4557-bd9c-b4daa6d152fd',NULL,'31a6d2a4-78e0-4653-9f6d-bf1a697dc1cb',2,3,NULL),
('f3725fbe-8e62-4662-a3f0-a7fff59f34c8','de4e63f35e8501238331458f8dfe30d4','jpg','8654340','C:\\Users\\xzq52\\Desktop\\json\\bbb\\127.jpg','2020-05-05 10:46:59.605','43.jpg','C:\\Users\\xzq52\\Desktop\\json\\bbb\\127.jpg',0,'73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','73aef7bf-b2e2-4e03-89b1-a4b63457c971','c84de369-6648-4c38-b725-971fae47ea70',NULL,'03704d92-a75c-4c5b-9b0c-b0c5d42f1e3a',3,2,NULL),
('f5513c4a-fc1a-4bf7-b061-cf73f6e3ac9b','a261bb8501e6a667100269198a95a935','jpg','8615011','C:\\Users\\xzq52\\Desktop\\json\\bbb\\126.jpg','2020-05-05 10:46:21.86','34.jpg','C:\\Users\\xzq52\\Desktop\\json\\bbb\\126.jpg',0,'73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','829e1b75-f0ed-4bd5-b1f3-6b347fe44330','0762f00c-0b7c-449b-89c4-17a737d2357c',NULL,'d443efe2-2fa1-4c8e-8b85-006db99eb934',4,1,NULL);

/*Table structure for table `tb_toolsbaseinfo` */

DROP TABLE IF EXISTS `tb_toolsbaseinfo`;

CREATE TABLE `tb_toolsbaseinfo` (
  `id` varchar(40) NOT NULL COMMENT '工具的基本信息',
  `environment` varchar(100) DEFAULT NULL COMMENT '运行环境',
  `scope` varchar(40) DEFAULT NULL COMMENT '使用范围',
  `name` varchar(40) DEFAULT NULL COMMENT '名称',
  `size` varchar(40) DEFAULT NULL COMMENT '安装包大小',
  `summarize` varchar(100) DEFAULT NULL COMMENT '功能概述',
  `updateTime` varchar(40) DEFAULT NULL COMMENT '更新日期',
  `provider` varchar(40) DEFAULT NULL COMMENT '提供人',
  `developers` varchar(40) DEFAULT NULL COMMENT '开发商',
  `copyright` varchar(40) DEFAULT NULL COMMENT '版权信息',
  `describess` varchar(40) DEFAULT NULL COMMENT '工具描述',
  `score` varchar(40) DEFAULT NULL COMMENT '分数',
  `typeId` varchar(40) DEFAULT NULL COMMENT '类型id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_toolsbaseinfo` */

insert  into `tb_toolsbaseinfo`(`id`,`environment`,`scope`,`name`,`size`,`summarize`,`updateTime`,`provider`,`developers`,`copyright`,`describess`,`score`,`typeId`) values 
('73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','电脑上面运行','使用范围','名称','安装包大小','功能概述','更新日期','提供人','开发商','版权信息','工具描述','分数','1');

/*Table structure for table `tb_toolsdatastorage` */

DROP TABLE IF EXISTS `tb_toolsdatastorage`;

CREATE TABLE `tb_toolsdatastorage` (
  `id` varchar(40) NOT NULL COMMENT '工具成果',
  `user_id` varchar(40) DEFAULT NULL COMMENT '用户id',
  `file_id` varchar(40) DEFAULT NULL COMMENT '文件id',
  `file_path` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `state` int(11) DEFAULT NULL COMMENT '状态码',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `md5` varchar(50) DEFAULT NULL COMMENT 'md5',
  `name` varchar(40) DEFAULT NULL COMMENT '名称',
  `type` varchar(20) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_toolsdatastorage` */

insert  into `tb_toolsdatastorage`(`id`,`user_id`,`file_id`,`file_path`,`state`,`size`,`md5`,`name`,`type`) values 
('04273090-db19-4dba-8769-841a7b46eae1','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\124.jpg',1,9999,'124',NULL,NULL),
('1f377561-a72b-4925-907e-de21aafdaf4d','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\138.jpg',2,9,'138','dressing','jpg'),
('30488073-d5dc-45ed-ad2b-49cd134dc983','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\129.jpg',1,9999,'129',NULL,NULL),
('4e80d424-fb99-4807-a6e1-48cd58b77c05','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\128.JPG',1,9999,'128',NULL,NULL),
('71bbfe5b-4537-4637-bb96-a82b8252c18a','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\127.jpg',2,9999,'127',NULL,NULL),
('76b7189f-cbb4-4a8c-835f-66f3ecd061a2','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\126.jpg',1,9999,'126',NULL,NULL),
('80d9d60f-e6ac-4d69-87ae-266ebc8a5ceb','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\130.jpg',1,0,'130',NULL,NULL),
('90b79930-3829-45b8-a14e-5a7cfb282802','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\132.jpg',2,9999,'132',NULL,NULL),
('ab795569-5c94-48c9-ac8a-0a99565b9342','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\134.jpg',1,9999,'134',NULL,NULL),
('ba9b73cb-98ce-40f7-a29a-7be77eab4049','4e994db0-4398-4d03-86a4-e79c6d7bb6b9','48ff76a9-7a2a-4689-94ba-a7495fd25294','C:\\Users\\xzq52\\Desktop\\json\\fruit\\123123123123.jpg',2,9999,'123123123123',NULL,NULL);

/*Table structure for table `tb_toolsevaluate` */

DROP TABLE IF EXISTS `tb_toolsevaluate`;

CREATE TABLE `tb_toolsevaluate` (
  `pool_id` varchar(40) DEFAULT NULL COMMENT '评价管理',
  `evaluateStar` varchar(10) DEFAULT NULL COMMENT '评价星星',
  `evaluateContent` varchar(400) DEFAULT NULL COMMENT '评价内容',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_toolsevaluate` */

insert  into `tb_toolsevaluate`(`pool_id`,`evaluateStar`,`evaluateContent`,`user_id`) values 
('6444f756-6369-4df3-bdf5-96b75ccfbd6c','1','好',NULL),
('6444f756-6369-4df3-bdf5-96b75ccfbd6c','1','好',NULL),
('73fec0d8-c3fc-46b7-965f-6d4dcb82cb57','3','一般','4e994db0-4398-4d03-86a4-e79c6d7bb6b9');

/*Table structure for table `tb_toolsstorage` */

DROP TABLE IF EXISTS `tb_toolsstorage`;

CREATE TABLE `tb_toolsstorage` (
  `id` varchar(40) NOT NULL COMMENT '系统管理员执行工具存储功能。',
  `path` varchar(40) DEFAULT NULL COMMENT '系统管理员执行工具存储功能。',
  `name` varchar(40) DEFAULT NULL COMMENT '自定义工具名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_toolsstorage` */

/*Table structure for table `tb_updownloadmanagement` */

DROP TABLE IF EXISTS `tb_updownloadmanagement`;

CREATE TABLE `tb_updownloadmanagement` (
  `id` varchar(30) NOT NULL,
  `program` varchar(40) DEFAULT NULL COMMENT '安装程序',
  `environment` varchar(40) DEFAULT NULL COMMENT '运行环境',
  `patch` varchar(40) DEFAULT NULL COMMENT '补丁',
  `instructions` varchar(1000) DEFAULT NULL COMMENT '使用手册',
  `goodCase` varchar(50) DEFAULT NULL COMMENT '优秀案例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_updownloadmanagement` */

/*Table structure for table `tb_versionmanagement` */

DROP TABLE IF EXISTS `tb_versionmanagement`;

CREATE TABLE `tb_versionmanagement` (
  `Version_id` varchar(50) DEFAULT NULL,
  `version_number` varchar(30) DEFAULT NULL COMMENT '版本号',
  `Release_notes` varchar(1000) DEFAULT NULL COMMENT '版本说明',
  KEY `version_number` (`version_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_versionmanagement` */

insert  into `tb_versionmanagement`(`Version_id`,`version_number`,`Release_notes`) values 
('123','这个不好用','vc-2'),
('70f3ac73-d417-4cae-b3a0-513f6f76a702','版本说明','vc-2'),
('c91edbf9-5290-4c00-98ed-1e3f982d8421','版本说明','vc-2'),
('fa6afeff-35a5-4aa9-917f-9794f355314a','版本说明','vc-2'),
('be1abaa7-e84d-4ee1-9b01-c0d7865ec6ef','版本说明','vc-2'),
('d03fb94a-0b0e-4557-bd9c-b4daa6d152fd','还行','1.2'),
('6884c990-07dc-4b09-a8bf-04ab4af41052','还行','1.3'),
('0762f00c-0b7c-449b-89c4-17a737d2357c','还行','1.4'),
('c84de369-6648-4c38-b725-971fae47ea70','还行','1.5');

/*Table structure for table `tools_information` */

DROP TABLE IF EXISTS `tools_information`;

CREATE TABLE `tools_information` (
  `iid` varchar(10) NOT NULL,
  `scopeofuser` varchar(30) DEFAULT NULL,
  `copyright` varchar(30) DEFAULT NULL,
  `overview` varchar(30) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `provide` varchar(30) DEFAULT NULL,
  `developers` varchar(30) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `average` float(12,0) DEFAULT NULL,
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tools_information` */

insert  into `tools_information`(`iid`,`scopeofuser`,`copyright`,`overview`,`updatetime`,`provide`,`developers`,`description`,`state`,`average`) values 
('123','aaa','aaa','aaa','2020-04-15 04:01:10','aaa','aaa','aaa','aaa',12);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `uid` varchar(50) DEFAULT NULL,
  `rid` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `user_role` */

insert  into `user_role`(`uid`,`rid`) values 
('2','2'),
('3','3'),
('1','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
