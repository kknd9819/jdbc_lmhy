/*
MySQL Backup
Source Server Version: 5.6.17
Source Database: yingtong
Date: 2017/5/17 15:43:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `xx_admin`
-- ----------------------------
DROP TABLE IF EXISTS `xx_admin`;
CREATE TABLE `xx_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_failure_count` int(11) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `m_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `xx_admin_role`;
CREATE TABLE `xx_admin_role` (
  `admins` bigint(20) NOT NULL,
  `roles` bigint(20) NOT NULL,
  PRIMARY KEY (`admins`,`roles`),
  KEY `FKD291D6053FF548F7` (`roles`),
  KEY `FKD291D605A022690F` (`admins`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_log`
-- ----------------------------
DROP TABLE IF EXISTS `xx_log`;
CREATE TABLE `xx_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `content` longtext,
  `ip` varchar(255) NOT NULL,
  `operation` varchar(255) NOT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `parameter` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_menu`
-- ----------------------------
DROP TABLE IF EXISTS `xx_menu`;
CREATE TABLE `xx_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `orders` int(11) DEFAULT NULL,
  `full_name` longtext NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `tree_path` varchar(255) NOT NULL,
  `menu_value` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9E1F1F3EFC0C969F` (`menu_value`),
  KEY `FK9E1F1F3EFE2357CD` (`parent`),
  CONSTRAINT `FK9E1F1F3EFC0C969F` FOREIGN KEY (`menu_value`) REFERENCES `xx_menu_value` (`id`),
  CONSTRAINT `FK9E1F1F3EFE2357CD` FOREIGN KEY (`parent`) REFERENCES `xx_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_menu_value`
-- ----------------------------
DROP TABLE IF EXISTS `xx_menu_value`;
CREATE TABLE `xx_menu_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `orders` int(11) DEFAULT NULL,
  `v_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `xx_operate_log`;
CREATE TABLE `xx_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `content` longtext,
  `ip` varchar(255) NOT NULL,
  `operation` varchar(255) NOT NULL,
  `operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_role`
-- ----------------------------
DROP TABLE IF EXISTS `xx_role`;
CREATE TABLE `xx_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xx_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `xx_role_authority`;
CREATE TABLE `xx_role_authority` (
  `role` bigint(20) NOT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  KEY `FKE06165D939B03AB0` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records
-- ----------------------------
INSERT INTO `xx_admin` VALUES ('1','2014-03-04 00:03:36','2015-06-29 21:43:10','技术部','admin@shopxx.net','','\0',NULL,'2017-02-26 09:26:48','0','172.31.30.40','管理员','78649679a1b1c8c3a52bc08be61586eb','shengyuan2014',NULL), ('2','2014-03-04 00:03:36','2015-06-29 21:43:10','技术部','admin@shopxx.net','','\0',NULL,'2017-05-03 14:35:54','0','127.0.0.1','管理员','0192023a7bbd73250516f069df18b500','admin',NULL), ('3','2017-02-24 16:56:36','2017-02-25 14:53:30',NULL,'123456@qq.com','','\0',NULL,'2017-02-25 14:54:11','1','172.31.30.40','管理员','e10adc3949ba59abbe56e057f20f883e','admin1234',NULL);
INSERT INTO `xx_admin_role` VALUES ('1','1'), ('2','1'), ('3','1');
INSERT INTO `xx_menu` VALUES ('1','2017-02-09 15:52:17','2017-02-09 15:52:19','1','会员','0','会员',',',NULL,NULL), ('2','2014-07-17 10:34:55','2014-07-21 19:56:19','2','会员管理','1','会员管理',',1,','1','1'), ('3','2017-02-24 16:41:24','2017-02-24 16:55:31','3','标签管理商家','2','商家',',3,239,',NULL,'239'), ('121','2014-07-16 16:14:17','2014-07-16 16:14:17','4','内容','0','内容',',',NULL,NULL), ('123','2014-07-17 08:59:43','2014-07-17 08:59:43','2','内容文章管理','1','文章管理',',121,','119','121'), ('124','2014-07-17 09:00:09','2014-07-17 09:00:09','3','内容文章分类','1','文章分类',',121,','120','121'), ('127','2014-07-17 09:01:55','2014-07-17 09:01:55','6','内容广告位','1','广告位',',121,','123','121'), ('128','2014-07-17 09:02:20','2014-07-17 09:02:20','7','内容广告管理','1','广告管理',',121,','124','121'), ('132','2014-07-17 09:58:55','2014-07-17 09:58:55','1','内容文章管理添加','2','添加',',121,123,','128','123'), ('133','2014-07-17 09:59:18','2014-07-17 09:59:18','2','内容文章管理编辑','2','编辑',',121,123,','129','123'), ('134','2014-07-17 09:59:35','2014-07-17 09:59:35','3','内容文章管理查看','2','查看',',121,123,','130','123'), ('135','2014-07-17 10:00:06','2014-07-17 10:00:06','4','内容文章管理删除','2','删除',',121,123,','131','123'), ('136','2014-07-17 10:00:33','2014-07-17 10:00:33','5','内容文章管理类型','2','类型',',121,123,','132','123'), ('137','2014-07-17 10:01:24','2014-07-17 10:01:24','1','内容文章分类添加','2','添加',',121,124,','133','124'), ('138','2014-07-17 10:01:43','2014-07-17 10:01:43','2','内容文章分类编辑','2','编辑',',121,124,','134','124'), ('139','2014-07-17 10:01:59','2014-07-17 10:01:59','3','内容文章分类查看','2','查看',',121,124,','135','124'), ('140','2014-07-17 10:02:37','2014-07-17 10:02:37','4','内容文章分类删除','2','删除',',121,124,','136','124'), ('141','2014-07-17 10:02:57','2014-07-17 10:02:57','5','内容文章分类子类','2','子类',',121,124,','137','124'), ('148','2014-07-17 10:08:38','2014-07-17 10:08:38','1','内容广告位添加','2','添加',',121,127,','144','127'), ('149','2014-07-17 10:08:54','2014-07-17 10:08:54','2','内容广告位编辑','2','编辑',',121,127,','145','127'), ('150','2014-07-17 10:09:10','2014-07-17 10:09:10','3','内容广告位删除','2','删除',',121,127,','146','127'), ('151','2014-07-17 10:10:00','2014-07-17 10:10:00','1','内容广告管理添加','2','添加',',121,128,','147','128'), ('152','2014-07-17 10:10:17','2014-07-17 10:10:17','2','内容广告管理编辑','2','编辑',',121,128,','148','128'), ('153','2014-07-17 10:10:33','2014-07-17 10:10:33','3','内容广告管理删除','2','删除',',121,128,','149','128'), ('180','2014-07-17 10:34:55','2014-07-21 19:56:19','1','系统','0','系统',',',NULL,NULL), ('187','2014-07-17 11:58:41','2014-07-17 15:42:25','7','系统管理员','1','管理员',',180,','179','180'), ('188','2014-07-17 11:59:03','2014-07-17 11:59:03','8','系统角色管理','1','角色管理',',180,','180','180'), ('196','2014-07-17 12:03:54','2014-07-21 19:57:44','16','系统权限管理','1','权限管理',',180,','188','180'), ('217','2014-07-17 15:41:24','2014-07-17 15:42:39','1','系统管理员添加','2','添加',',180,187,','209','187'), ('218','2014-07-17 15:42:57','2014-07-17 15:42:57','2','系统管理员编辑','2','编辑',',180,187,','210','187'), ('219','2014-07-17 15:43:13','2014-07-17 15:43:13','3','系统管理员删除','2','删除',',180,187,','211','187'), ('220','2014-07-17 15:43:48','2014-07-17 15:43:48','1','系统角色管理添加','2','添加',',180,188,','212','188'), ('221','2014-07-17 15:45:12','2014-07-17 15:45:12','2','系统角色管理编辑','2','编辑',',180,188,','213','188'), ('222','2014-07-17 15:45:31','2014-07-17 15:45:31','3','系统角色管理删除','2','删除',',180,188,','214','188'), ('229','2014-07-17 17:34:55','2014-07-17 17:34:55','1','系统权限管理添加','2','添加',',180,196,','221','196'), ('230','2014-07-17 17:35:53','2014-07-17 17:35:53','2','系统权限管理编辑','2','编辑',',180,196,','222','196'), ('231','2014-07-17 17:36:14','2014-07-17 17:36:14','3','系统权限管理删除','2','删除',',180,196,','223','196'), ('232','2014-07-17 17:36:40','2014-07-17 17:36:40','4','系统权限管理子类','2','子类',',180,196,','224','196'), ('239','2017-02-24 16:51:14','2017-02-24 16:51:14','5','商家标签管理','1','标签管理',',3,','225','3');
INSERT INTO `xx_menu_value` VALUES ('1','2017-02-09 15:54:11','2017-02-09 15:54:13',NULL,'admin:member'), ('119','2014-07-17 08:59:43','2014-07-17 08:59:43',NULL,'admin:article'), ('120','2014-07-17 09:00:09','2014-07-17 09:00:09',NULL,'admin:articleCategory'), ('123','2014-07-17 09:01:55','2014-07-17 09:01:55',NULL,'admin:adPosition'), ('124','2014-07-17 09:02:20','2014-07-17 09:02:20',NULL,'admin:ad'), ('128','2014-07-17 09:58:55','2014-07-17 09:58:55',NULL,'article:add'), ('129','2014-07-17 09:59:18','2014-07-17 09:59:18',NULL,'article:edit'), ('130','2014-07-17 09:59:35','2014-07-17 09:59:35',NULL,'article:view'), ('131','2014-07-17 10:00:06','2014-07-17 10:00:06',NULL,'article:delete'), ('132','2014-07-17 10:00:33','2014-07-17 10:00:33',NULL,'article:select'), ('133','2014-07-17 10:01:24','2014-07-17 10:01:24',NULL,'articleCategory:add'), ('134','2014-07-17 10:01:43','2014-07-17 10:01:43',NULL,'articleCategory:edit'), ('135','2014-07-17 10:01:59','2014-07-17 10:01:59',NULL,'articleCategory:view'), ('136','2014-07-17 10:02:37','2014-07-17 10:02:37',NULL,'articleCategory:delete'), ('137','2014-07-17 10:02:57','2014-07-17 10:02:57',NULL,'articleCategory:category'), ('144','2014-07-17 10:08:38','2014-07-17 10:08:38',NULL,'adPosition:add'), ('145','2014-07-17 10:08:54','2014-07-17 10:08:54',NULL,'adPosition:edit'), ('146','2014-07-17 10:09:10','2014-07-17 10:09:10',NULL,'adPosition:delete'), ('147','2014-07-17 10:10:00','2014-07-17 10:10:00',NULL,'ad:add'), ('148','2014-07-17 10:10:17','2014-07-17 10:10:17',NULL,'ad:edit'), ('149','2014-07-17 10:10:33','2014-07-17 10:10:33',NULL,'ad:delete'), ('179','2014-07-17 11:58:41','2014-07-17 15:42:25',NULL,'system:admin'), ('180','2014-07-17 11:59:03','2014-07-17 11:59:03',NULL,'system:role'), ('188','2014-07-17 12:03:54','2014-07-17 12:03:54',NULL,'system:authority'), ('209','2014-07-17 15:41:24','2014-07-17 15:42:39',NULL,'administrator:add'), ('210','2014-07-17 15:42:57','2014-07-17 15:42:57',NULL,'administrator:edit'), ('211','2014-07-17 15:43:13','2014-07-17 15:43:13',NULL,'administrator:delete'), ('212','2014-07-17 15:43:48','2014-07-17 15:43:48',NULL,'role:add'), ('213','2014-07-17 15:45:12','2014-07-17 15:45:12',NULL,'role:edit'), ('214','2014-07-17 15:45:31','2014-07-17 15:45:31',NULL,'role:delete'), ('221','2014-07-17 17:34:55','2014-07-17 17:34:55',NULL,'authority:add'), ('222','2014-07-17 17:35:53','2014-07-17 17:35:53',NULL,'authority:edit'), ('223','2014-07-17 17:36:14','2014-07-17 17:36:14',NULL,'authority:delete'), ('224','2014-07-17 17:36:40','2014-07-17 17:36:40',NULL,'authority:category'), ('225','2017-02-24 16:26:53','2017-02-24 16:26:53',NULL,'admin:tag'), ('226','2017-02-24 16:48:58','2017-02-24 16:48:58',NULL,'admin:tag'), ('227','2017-02-24 16:55:31','2017-02-24 16:55:31',NULL,'标签');
INSERT INTO `xx_role` VALUES ('1','2014-03-04 00:02:44','2014-03-04 00:02:44','拥有管理后台最高权限','','超级管理员',NULL);
INSERT INTO `xx_role_authority` VALUES ('1','member:add'), ('1','member:delete'), ('1','member:view'), ('1','member:edit'), ('1','member:setpwd'), ('1','memberMessage:send'), ('1','memberMessage:delete'), ('1','memberMessage:view'), ('1','admin:member'), ('1','admin:merchant'), ('1','merchant:add'), ('1','merchant:delete'), ('1','merchant:filter'), ('1','merchant:edit'), ('1','admin:category'), ('1','category:add'), ('1','category:edit'), ('1','category:delete'), ('1','category:children'), ('1','merchant:order'), ('1','merchant:dynamic'), ('1','memberMessage:view'), ('1','memberMessage:edit'), ('1','admin:brand'), ('1','brand:add'), ('1','brand:delete'), ('1','brand:edit'), ('1','brand:merchantView'), ('1','normalOrder:view'), ('1','dynamicMsg:add'), ('1','dynamicMsg:delete'), ('1','dynamicMsg:edit'), ('1','admin:message'), ('1','system:admin'), ('1','system:role'), ('1','system:authority'), ('1','admin:article'), ('1','admin:articleCategory'), ('1','admin:adPosition'), ('1','articleCategory:add'), ('1','articleCategory:edit'), ('1','articleCategory:view'), ('1','article:add'), ('1','ad:add'), ('1','ad:edit'), ('1','adPosition:add'), ('1','adPosition:edit'), ('1','adPosition:delete'), ('1','admin:ad'), ('1','administrator:add'), ('1','administrator:edit'), ('1','authority:add'), ('1','authority:edit'), ('1','authority:delete'), ('1','authority:category'), ('1','role:add'), ('1','role:edit'), ('1','role:delete'), ('1','articleCategory:delete'), ('1','articleCategory:category'), ('1','article:edit'), ('1','article:view'), ('1','article:delete'), ('1','article:select'), ('1','ad:delete'), ('1','administrator:delete'), ('1','admin:salary'), ('1','admin:recharge'), ('1','admin:drawCash'), ('1','admin:withdrawal'), ('1','recharge:rehandle'), ('1','fund:task'), ('1','fund:balance'), ('1','fund:finance'), ('0',NULL);
