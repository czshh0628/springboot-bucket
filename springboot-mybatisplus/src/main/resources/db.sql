/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : mybatisplus

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 08/05/2021 17:54:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                        `age` int(11) DEFAULT NULL COMMENT '年龄',
                        `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `version` int(255) DEFAULT NULL,
                        `deleted` int(255) DEFAULT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'czs', 18, '7606@qq.com', '2021-05-08 16:56:09', '2021-05-08 17:12:58', 0, 1);
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', '2021-05-08 16:56:12', '2021-05-08 16:56:25', NULL, NULL);
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', '2021-05-08 16:56:14', '2021-05-08 16:56:27', NULL, NULL);
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', '2021-05-08 16:56:16', '2021-05-08 16:56:29', NULL, NULL);
INSERT INTO `user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', '2021-05-08 16:56:20', '2021-05-08 16:56:32', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
