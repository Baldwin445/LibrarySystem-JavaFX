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

 Date: 28/10/2019 23:01:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acct_illegal_table
-- ----------------------------
DROP TABLE IF EXISTS `acct_illegal_table`;
CREATE TABLE `acct_illegal_table`  (
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `illegal_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `beg_time` datetime(6) NOT NULL,
  `han_date` datetime(6) NOT NULL,
  `rmb` float(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`acct_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for acct_info_table
-- ----------------------------
DROP TABLE IF EXISTS `acct_info_table`;
CREATE TABLE `acct_info_table`  (
  `acct_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_pwd` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_state` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_own` int(3) NOT NULL,
  `reg_date` date NOT NULL,
  `cancel_date` date NOT NULL,
  `role` int(1) NOT NULL,
  `acct_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_age` int(2) NOT NULL,
  `acct_faculty` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_major` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `acct_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_email` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`acct_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_info_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_info_table`;
CREATE TABLE `admin_info_table`  (
  `admin_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_pwd` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_limits` int(1) NOT NULL,
  `admin_sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_age` int(2) NOT NULL,
  `admin_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_email` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for am_record_table
-- ----------------------------
DROP TABLE IF EXISTS `am_record_table`;
CREATE TABLE `am_record_table`  (
  `op_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`op_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bm_record_table
-- ----------------------------
DROP TABLE IF EXISTS `bm_record_table`;
CREATE TABLE `bm_record_table`  (
  `op_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_id` int(6) NOT NULL,
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `notes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`op_id`, `book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book_borrow_table
-- ----------------------------
DROP TABLE IF EXISTS `book_borrow_table`;
CREATE TABLE `book_borrow_table`  (
  `book_id` int(6) NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `b_date` datetime(6) NOT NULL,
  `r_date` datetime(6) NOT NULL,
  `admin_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `br_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `renew` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`, `br_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book_info_table
-- ----------------------------
DROP TABLE IF EXISTS `book_info_table`;
CREATE TABLE `book_info_table`  (
  `book_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_id` int(6) NOT NULL,
  `cat_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publisher` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_year` int(4) NOT NULL,
  `long_time` int(11) NOT NULL,
  `book_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remain_day` int(2) NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`book_id`, `isbn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for br_record_table
-- ----------------------------
DROP TABLE IF EXISTS `br_record_table`;
CREATE TABLE `br_record_table`  (
  `book_id` int(6) NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `br_date` datetime(6) NOT NULL,
  `admin_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `br_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `br_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`book_id`, `br_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recommend_record_table
-- ----------------------------
DROP TABLE IF EXISTS `recommend_record_table`;
CREATE TABLE `recommend_record_table`  (
  `book_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acct_id` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publisher` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `publish_year` int(4) NOT NULL,
  `lang` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reco_info` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\r\n ',
  `admin_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `op_date` datetime(6) NOT NULL,
  PRIMARY KEY (`isbn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
