/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : order

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2020-07-12 23:04:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `categories`
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES ('1', '北京菜');
INSERT INTO `categories` VALUES ('2', '新疆菜');
INSERT INTO `categories` VALUES ('3', '朝鲜族菜');
INSERT INTO `categories` VALUES ('4', '四川风味菜');
INSERT INTO `categories` VALUES ('10', '上海菜');
INSERT INTO `categories` VALUES ('11', '河南菜');

-- ----------------------------
-- Table structure for `menus`
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `mname` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('6', '3', '辣白菜', '11.30');
INSERT INTO `menus` VALUES ('9', '1', '卤煮火烧', '23.00');
INSERT INTO `menus` VALUES ('10', '2', '红柳大串', '15.00');
INSERT INTO `menus` VALUES ('11', '4', '宫保鸡丁', '20.00');
INSERT INTO `menus` VALUES ('13', '10', '红豆冰山', '23.50');
INSERT INTO `menus` VALUES ('14', '10', '软炸里脊', '31.00');
INSERT INTO `menus` VALUES ('16', '1', '北京烤鸭', '98.00');
INSERT INTO `menus` VALUES ('18', '4', '麻婆豆腐', '36.01');
INSERT INTO `menus` VALUES ('19', '1', '红烧猪手', '26.29');
INSERT INTO `menus` VALUES ('20', '1', '豆汁儿', '1.50');

-- ----------------------------
-- Table structure for `tb_resume`
-- ----------------------------
DROP TABLE IF EXISTS `tb_resume`;
CREATE TABLE `tb_resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_resume
-- ----------------------------
INSERT INTO `tb_resume` VALUES ('6', '北京', '李四', '1234');
INSERT INTO `tb_resume` VALUES ('7', '上海', '赵云', '456');
INSERT INTO `tb_resume` VALUES ('8', '重庆', '关悦', '789');
INSERT INTO `tb_resume` VALUES ('10', '深zhen', '345', '134');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
