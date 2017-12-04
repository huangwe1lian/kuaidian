/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : kuaidian

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2017-12-04 22:58:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gl_acl
-- ----------------------------
DROP TABLE IF EXISTS `gl_acl`;
CREATE TABLE `gl_acl` (
  `aclId` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned DEFAULT NULL,
  `resourceId` varchar(255) DEFAULT NULL,
  `roleId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`aclId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_acl
-- ----------------------------

-- ----------------------------
-- Table structure for gl_contractor_session
-- ----------------------------
DROP TABLE IF EXISTS `gl_contractor_session`;
CREATE TABLE `gl_contractor_session` (
  `id` int(10) NOT NULL,
  `session_id` varchar(255) NOT NULL,
  `contractor_id` int(10) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_contractor_session
-- ----------------------------
INSERT INTO `gl_contractor_session` VALUES ('2', '150b24b3-664b-46d7-af54-2d0f3196a019', '1', '2017-11-30 18:13:59', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('11', '3f00efe3-86f4-4136-b9d6-088b8e2430ce', '1', '2017-11-30 18:19:33', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('21', '4cb60f1d-7a64-4056-ab9b-a2170b61fb02', '1', '2017-11-30 18:23:22', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('22', '3412c3a5-c14f-4cc0-8c1b-bd429aaec2ef', '1', '2017-11-30 18:23:37', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('23', '42c86b64-cea4-4bfc-a99d-9519d68494df', '1', '2017-11-30 18:24:23', '192.168.22.78');
INSERT INTO `gl_contractor_session` VALUES ('24', '4aa62de4-f968-4136-aadc-8f5c042f7110', '1', '2017-11-30 18:25:25', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('25', '4d9309f5-01a2-4c76-9d6c-771a1f23ed66', '1', '2017-12-01 09:10:16', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('26', '37f5a9ce-c4bb-44a1-9a48-237008da3bf0', '1', '2017-12-01 09:26:02', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('27', 'e389a62e-0d9b-4c1e-a1d0-20398b75f068', '1', '2017-12-01 16:10:43', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('28', '3fcb6c23-2987-4be3-84ca-77cd66b53e21', '1', '2017-12-01 17:03:11', '192.168.33.95');
INSERT INTO `gl_contractor_session` VALUES ('31', '501b8c9e-8e69-46d7-982a-fd737d61aa2d', '1', '2017-12-04 22:02:51', '169.254.150.38');
INSERT INTO `gl_contractor_session` VALUES ('32', '03f0413e-0869-4e4a-a5fd-fe70c5c8db67', '1', '2017-12-04 22:54:56', '169.254.150.38');
INSERT INTO `gl_contractor_session` VALUES ('33', '174edbe0-14c0-4ff5-a0ae-b798d4c99a25', '1', '2017-12-04 22:56:24', '169.254.150.38');

-- ----------------------------
-- Table structure for gl_function
-- ----------------------------
DROP TABLE IF EXISTS `gl_function`;
CREATE TABLE `gl_function` (
  `functionId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`functionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_function
-- ----------------------------

-- ----------------------------
-- Table structure for gl_keygen
-- ----------------------------
DROP TABLE IF EXISTS `gl_keygen`;
CREATE TABLE `gl_keygen` (
  `table_name` varchar(100) NOT NULL,
  `last_used_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_keygen
-- ----------------------------
INSERT INTO `gl_keygen` VALUES ('gl_contractor_session', '31');
INSERT INTO `gl_keygen` VALUES ('gl_session', '30');

-- ----------------------------
-- Table structure for gl_log
-- ----------------------------
DROP TABLE IF EXISTS `gl_log`;
CREATE TABLE `gl_log` (
  `logId` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `logAt` datetime DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `query_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_log
-- ----------------------------

-- ----------------------------
-- Table structure for gl_log_detail
-- ----------------------------
DROP TABLE IF EXISTS `gl_log_detail`;
CREATE TABLE `gl_log_detail` (
  `logDetailId` int(10) unsigned NOT NULL,
  `logId` int(10) unsigned DEFAULT NULL,
  `type_` varchar(255) DEFAULT NULL,
  `before_` text,
  `after_` text,
  PRIMARY KEY (`logDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_log_detail
-- ----------------------------

-- ----------------------------
-- Table structure for gl_role
-- ----------------------------
DROP TABLE IF EXISTS `gl_role`;
CREATE TABLE `gl_role` (
  `roleId` int(10) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_role
-- ----------------------------

-- ----------------------------
-- Table structure for gl_role_function
-- ----------------------------
DROP TABLE IF EXISTS `gl_role_function`;
CREATE TABLE `gl_role_function` (
  `id` int(10) unsigned NOT NULL,
  `roleId` int(10) unsigned NOT NULL,
  `functionId` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_role_function
-- ----------------------------

-- ----------------------------
-- Table structure for gl_session
-- ----------------------------
DROP TABLE IF EXISTS `gl_session`;
CREATE TABLE `gl_session` (
  `id` int(10) NOT NULL,
  `session_id` varchar(255) NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_session
-- ----------------------------
INSERT INTO `gl_session` VALUES ('2', '150b24b3-664b-46d7-af54-2d0f3196a019', '1', '2017-11-30 18:13:59', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('11', '3f00efe3-86f4-4136-b9d6-088b8e2430ce', '1', '2017-11-30 18:19:33', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('21', '4cb60f1d-7a64-4056-ab9b-a2170b61fb02', '1', '2017-11-30 18:23:22', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('22', '3412c3a5-c14f-4cc0-8c1b-bd429aaec2ef', '1', '2017-11-30 18:23:37', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('23', '42c86b64-cea4-4bfc-a99d-9519d68494df', '1', '2017-11-30 18:24:23', '192.168.22.78');
INSERT INTO `gl_session` VALUES ('24', '4aa62de4-f968-4136-aadc-8f5c042f7110', '1', '2017-11-30 18:25:25', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('25', '4d9309f5-01a2-4c76-9d6c-771a1f23ed66', '1', '2017-12-01 09:10:16', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('26', '37f5a9ce-c4bb-44a1-9a48-237008da3bf0', '1', '2017-12-01 09:26:02', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('27', 'e389a62e-0d9b-4c1e-a1d0-20398b75f068', '1', '2017-12-01 16:10:43', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('28', '3fcb6c23-2987-4be3-84ca-77cd66b53e21', '1', '2017-12-01 17:03:11', '192.168.33.95');
INSERT INTO `gl_session` VALUES ('31', '501b8c9e-8e69-46d7-982a-fd737d61aa2d', '1', '2017-12-04 22:02:51', '169.254.150.38');

-- ----------------------------
-- Table structure for gl_user
-- ----------------------------
DROP TABLE IF EXISTS `gl_user`;
CREATE TABLE `gl_user` (
  `userId` int(10) unsigned NOT NULL,
  `account` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `createAt` datetime DEFAULT NULL,
  `loginAt` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gl_user
-- ----------------------------

-- ----------------------------
-- Table structure for kd_contractor
-- ----------------------------
DROP TABLE IF EXISTS `kd_contractor`;
CREATE TABLE `kd_contractor` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_contractor
-- ----------------------------
INSERT INTO `kd_contractor` VALUES ('1', 'shangjia', '123456');

-- ----------------------------
-- Table structure for kd_cuisine
-- ----------------------------
DROP TABLE IF EXISTS `kd_cuisine`;
CREATE TABLE `kd_cuisine` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `contractor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_cuisine
-- ----------------------------
INSERT INTO `kd_cuisine` VALUES ('1', '菜式1', '1');
INSERT INTO `kd_cuisine` VALUES ('2', '菜式2', '1');

-- ----------------------------
-- Table structure for kd_order
-- ----------------------------
DROP TABLE IF EXISTS `kd_order`;
CREATE TABLE `kd_order` (
  `id` bigint(20) NOT NULL,
  `number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_order
-- ----------------------------

-- ----------------------------
-- Table structure for kd_staff
-- ----------------------------
DROP TABLE IF EXISTS `kd_staff`;
CREATE TABLE `kd_staff` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_staff
-- ----------------------------
INSERT INTO `kd_staff` VALUES ('1', 'staff', '123456');

-- ----------------------------
-- Table structure for kd_user
-- ----------------------------
DROP TABLE IF EXISTS `kd_user`;
CREATE TABLE `kd_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_user
-- ----------------------------
INSERT INTO `kd_user` VALUES ('1', 'huangweilian', '123456');

-- ----------------------------
-- Table structure for kd_user_order
-- ----------------------------
DROP TABLE IF EXISTS `kd_user_order`;
CREATE TABLE `kd_user_order` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kd_user_order
-- ----------------------------
