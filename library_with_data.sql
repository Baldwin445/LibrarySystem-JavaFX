/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 05/11/2019 22:36:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acct_illegal_table
-- ----------------------------
DROP TABLE IF EXISTS `acct_illegal_table`;
CREATE TABLE `acct_illegal_table`  (
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `illegal_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `beg_time` datetime(6) NULL DEFAULT NULL,
  `han_date` datetime(6) NULL DEFAULT NULL,
  `rmb` float(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`acct_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for acct_info_table
-- ----------------------------
DROP TABLE IF EXISTS `acct_info_table`;
CREATE TABLE `acct_info_table`  (
  `acct_name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_pwd` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_state` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_own` int(3) NULL DEFAULT NULL,
  `reg_date` date NULL DEFAULT NULL,
  `cancel_date` date NULL DEFAULT NULL,
  `role` int(1) NULL DEFAULT NULL,
  `acct_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_age` int(2) NULL DEFAULT NULL,
  `acct_faculty` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_major` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_email` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`acct_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acct_info_table
-- ----------------------------
INSERT INTO `acct_info_table` VALUES ('陈宇', '170001', '170001', 'N', 0, NULL, NULL, 1, 'M', 21, '信息学院', '软件工程', '13713700000', NULL);

-- ----------------------------
-- Table structure for admin_info_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_info_table`;
CREATE TABLE `admin_info_table`  (
  `admin_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_pwd` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_limits` int(1) NULL DEFAULT NULL,
  `admin_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_age` int(2) NULL DEFAULT NULL,
  `admin_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_email` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_info_table
-- ----------------------------
INSERT INTO `admin_info_table` VALUES ('梅花', 'admin_super', 'admin', 'N', 4, 'M', 21, '13713600000', 'test@163.com');

-- ----------------------------
-- Table structure for am_record_table
-- ----------------------------
DROP TABLE IF EXISTS `am_record_table`;
CREATE TABLE `am_record_table`  (
  `op_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`op_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bm_record_table
-- ----------------------------
DROP TABLE IF EXISTS `bm_record_table`;
CREATE TABLE `bm_record_table`  (
  `op_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_id` int(6) NOT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`op_id`, `book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book_borrow_table
-- ----------------------------
DROP TABLE IF EXISTS `book_borrow_table`;
CREATE TABLE `book_borrow_table`  (
  `book_id` int(6) NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_date` datetime(6) NULL DEFAULT NULL,
  `r_date` datetime(6) NULL DEFAULT NULL,
  `admin_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `br_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `renew` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`, `br_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book_info_table
-- ----------------------------
DROP TABLE IF EXISTS `book_info_table`;
CREATE TABLE `book_info_table`  (
  `book_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_id` int(6) NOT NULL,
  `cat_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publisher` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publish_year` int(4) NULL DEFAULT NULL,
  `long_time` int(11) NULL DEFAULT NULL,
  `book_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_info_table
-- ----------------------------
INSERT INTO `book_info_table` VALUES ('毛概', 123456, 'T123', 'T', '测试出版社', '陈宇', '9789789789780', 2004, 180, 'N');
INSERT INTO `book_info_table` VALUES ('思修', 123457, 'T123', 'T', '测试出版社', '陈宇', '9789789789780', 2004, 180, 'N');

-- ----------------------------
-- Table structure for book_order_table
-- ----------------------------
DROP TABLE IF EXISTS `book_order_table`;
CREATE TABLE `book_order_table`  (
  `book_id` int(6) NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remain_day` int(2) NULL DEFAULT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`book_id`, `isbn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for br_record_table
-- ----------------------------
DROP TABLE IF EXISTS `br_record_table`;
CREATE TABLE `br_record_table`  (
  `book_id` int(6) NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `br_date` datetime(6) NULL DEFAULT NULL,
  `admin_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `br_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `br_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`, `br_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recommend_record_table
-- ----------------------------
DROP TABLE IF EXISTS `recommend_record_table`;
CREATE TABLE `recommend_record_table`  (
  `book_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publisher` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_year` int(4) NULL DEFAULT NULL,
  `lang` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reco_info` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\r\n ',
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `op_date` datetime(6) NOT NULL,
  PRIMARY KEY (`isbn`, `op_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
