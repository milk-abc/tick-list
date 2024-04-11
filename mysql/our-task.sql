/*
 Navicat Premium Data Transfer

 Source Server         : study
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : our-task

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 11/04/2024 21:03:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '清单id',
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清单名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `run` tinyint(0) UNSIGNED NULL DEFAULT 1 COMMENT '是否被删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 153 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '所属用户id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `run` tinyint(0) UNSIGNED NULL DEFAULT 1 COMMENT '是否还在使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `category_id` bigint(0) UNSIGNED NULL DEFAULT 1 COMMENT '所属清单id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `run` tinyint(0) UNSIGNED NULL DEFAULT 1 COMMENT '是否完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 183 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_label
-- ----------------------------
DROP TABLE IF EXISTS `task_label`;
CREATE TABLE `task_label`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_id` bigint(0) UNSIGNED NOT NULL COMMENT '清单id',
  `label_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '标签id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 224 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tomato_set
-- ----------------------------
DROP TABLE IF EXISTS `tomato_set`;
CREATE TABLE `tomato_set`  (
  `id` bigint(0) NOT NULL COMMENT '设置id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `aim_number` int(0) NULL DEFAULT NULL COMMENT '每日目标数',
  `start_aim` tinyint(0) NULL DEFAULT NULL COMMENT '是否开始目标',
  `duration` int(0) NULL DEFAULT NULL COMMENT '番茄时长',
  `short_rest_duration` int(0) NULL DEFAULT NULL COMMENT '短休息时长',
  `long_rest_duration` int(0) NULL DEFAULT NULL COMMENT '长休息时长',
  `long_rest_interval_number` int(0) NULL DEFAULT NULL COMMENT '长休息间隔数',
  `auto_start_next` tinyint(0) NULL DEFAULT NULL COMMENT '是否自动开始下一个番茄',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `roles` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '权限',
  `status` tinyint(0) UNSIGNED NULL DEFAULT 1 COMMENT '是否被冻结',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
