/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : touceng_finance

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 09/08/2018 22:31:24 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tc_agent_wallet`
-- ----------------------------
DROP TABLE IF EXISTS `tc_agent_wallet`;
CREATE TABLE `tc_agent_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '余额',
  `agent_id` varchar(64) NOT NULL COMMENT '代理商主键id',
  `agent_code` varchar(64) NOT NULL COMMENT '代理商商户号',
  `account_no` varchar(50) NOT NULL COMMENT '账户编号',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-禁用；1-正常；2-封存]',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_agent_currency` (`agent_id`,`currency`) COMMENT '代理货币类型唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商钱包账户';

-- ----------------------------
--  Table structure for `tc_agent_wallet_log`
-- ----------------------------
DROP TABLE IF EXISTS `tc_agent_wallet_log`;
CREATE TABLE `tc_agent_wallet_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(50) NOT NULL COMMENT '多宝订单号',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '余额',
  `currency` varchar(10) DEFAULT NULL COMMENT '货币类型[RMB-人民币]',
  `user_id` varchar(64) NOT NULL COMMENT '用户主键id',
  `agent_id` varchar(64) NOT NULL COMMENT '代理商主键id',
  `product_code` varchar(64) NOT NULL COMMENT '业务类型',
  `product_name` varchar(64) NOT NULL COMMENT '业务类型名称',
  `agent_wallet_account_no` varchar(50) NOT NULL COMMENT '代理账户编号',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-待处理；1-成功；2-失败；3-异常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `order_amount` decimal(20,3) DEFAULT NULL,
  `order_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商钱包账户记录日志';

-- ----------------------------
--  Table structure for `tc_channel_wallet`
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_wallet`;
CREATE TABLE `tc_channel_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '余额',
  `channel_name` varchar(64) NOT NULL COMMENT '渠道名称',
  `channel_account_no` varchar(64) NOT NULL COMMENT '渠道账户号',
  `channel_account_name` varchar(64) NOT NULL COMMENT '渠道账户名称',
  `email` varchar(64) DEFAULT NULL COMMENT '渠道注册邮箱',
  `account_no` varchar(50) NOT NULL COMMENT '账户编号',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-禁用；1-正常；2-封存]',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_channel_currency` (`channel_account_no`,`currency`) COMMENT '商户货币类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='内部渠道钱包账户';

-- ----------------------------
--  Table structure for `tc_channel_wallet_log`
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_wallet_log`;
CREATE TABLE `tc_channel_wallet_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(50) NOT NULL COMMENT '多宝订单号',
  `order_amount` decimal(20,3) DEFAULT NULL COMMENT '多宝订单金额',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '多宝订单类型[0-收款；1-付款]',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) NOT NULL COMMENT '余额',
  `channel_account_no` varchar(20) NOT NULL COMMENT '渠道手续费账户',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `product_code` varchar(64) NOT NULL COMMENT '业务类型',
  `product_name` varchar(64) NOT NULL COMMENT '业务类型名称',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-待处理；1-成功；2-失败；3-异常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_product` (`order_no`,`product_code`) COMMENT '唯一索引订单号关联产品'
) ENGINE=InnoDB AUTO_INCREMENT=86734 DEFAULT CHARSET=utf8 COMMENT='内部渠道钱包账户记录日志';

-- ----------------------------
--  Table structure for `tc_charge_wallet`
-- ----------------------------
DROP TABLE IF EXISTS `tc_charge_wallet`;
CREATE TABLE `tc_charge_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '余额',
  `channel_account_no` varchar(20) NOT NULL COMMENT '渠道手续费账户',
  `account_no` varchar(50) NOT NULL COMMENT '账户编号',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-禁用；1-正常；2-封存]',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_channel_currency` (`channel_account_no`,`currency`) COMMENT '商户货币类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='手续费钱包账户';

-- ----------------------------
--  Table structure for `tc_charge_wallet_log`
-- ----------------------------
DROP TABLE IF EXISTS `tc_charge_wallet_log`;
CREATE TABLE `tc_charge_wallet_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(50) NOT NULL COMMENT '多宝订单号',
  `order_amount` decimal(20,3) DEFAULT NULL COMMENT '多宝订单金额',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '多宝订单类型[0-收款；1-付款]',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) NOT NULL COMMENT '余额',
  `channel_account_no` varchar(20) NOT NULL COMMENT '渠道手续费账户',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `product_code` varchar(64) NOT NULL COMMENT '业务类型',
  `product_name` varchar(64) NOT NULL COMMENT '业务类型名称',
  `charge_wallet_account_no` varchar(50) NOT NULL COMMENT '手续费账户编号',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态[0-待处理；1-成功；2-失败；3-异常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_product` (`order_no`,`product_code`) COMMENT '唯一索引订单号关联产品'
) ENGINE=InnoDB AUTO_INCREMENT=86733 DEFAULT CHARSET=utf8 COMMENT='手续费钱包账户记录日志';

-- ----------------------------
--  Table structure for `tc_cost_wallet`
-- ----------------------------
DROP TABLE IF EXISTS `tc_cost_wallet`;
CREATE TABLE `tc_cost_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '余额',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `channel_account_no` varchar(20) NOT NULL COMMENT '渠道手续费账户',
  `account_no` varchar(50) NOT NULL COMMENT '账户编号',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-禁用；1-正常；2-封存]',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_channel_currency` (`channel_account_no`,`currency`) COMMENT '商户货币类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='内部成本钱包账户';

-- ----------------------------
--  Table structure for `tc_cost_wallet_log`
-- ----------------------------
DROP TABLE IF EXISTS `tc_cost_wallet_log`;
CREATE TABLE `tc_cost_wallet_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(50) NOT NULL COMMENT '多宝订单号',
  `order_amount` decimal(20,3) DEFAULT NULL COMMENT '多宝订单金额',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '多宝订单类型[0-收款；1-付款]',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) NOT NULL COMMENT '余额',
  `channel_account_no` varchar(20) NOT NULL COMMENT '渠道手续费账户',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `product_code` varchar(64) NOT NULL COMMENT '业务类型',
  `product_name` varchar(64) NOT NULL COMMENT '业务类型名称',
  `cost_wallet_account_no` varchar(50) NOT NULL COMMENT '成本账户编号',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态[0-待处理；1-成功；2-失败；3-异常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_product` (`order_no`,`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=44061 DEFAULT CHARSET=utf8 COMMENT='内部成本账户记录日志';

-- ----------------------------
--  Table structure for `tc_user_wallet`
-- ----------------------------
DROP TABLE IF EXISTS `tc_user_wallet`;
CREATE TABLE `tc_user_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account_no` varchar(50) NOT NULL COMMENT '账户编号',
  `income` decimal(20,3) DEFAULT NULL COMMENT '总收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '总支出',
  `balance` decimal(20,3) DEFAULT NULL COMMENT '当前可用余额',
  `frozon_balance` decimal(20,3) DEFAULT NULL COMMENT '冻结资金余额',
  `user_id` varchar(64) NOT NULL COMMENT '用户主键id',
  `user_code` varchar(64) DEFAULT NULL COMMENT '用户商户号',
  `agent_id` varchar(64) DEFAULT NULL COMMENT '代理商主键id',
  `user_type` tinyint(1) DEFAULT '0' COMMENT '商户类型：0 普通商户，1 代理商户，2 子商户',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态[0-禁用；1-正常；2-封存；]',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_currency` (`user_id`,`currency`) COMMENT '用户货币类型唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COMMENT='会员钱包账户';

-- ----------------------------
--  Table structure for `tc_user_wallet_log`
-- ----------------------------
DROP TABLE IF EXISTS `tc_user_wallet_log`;
CREATE TABLE `tc_user_wallet_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(50) NOT NULL COMMENT '多宝订单号',
  `order_amount` decimal(20,3) DEFAULT NULL COMMENT '多宝订单金额',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '多宝订单类型[0-收款；1-付款]',
  `income` decimal(20,3) DEFAULT NULL COMMENT '收入',
  `outcome` decimal(20,3) DEFAULT NULL COMMENT '支出',
  `balance` decimal(20,3) NOT NULL COMMENT '余额',
  `currency` varchar(10) NOT NULL COMMENT '货币类型[RMB-人民币]',
  `user_id` varchar(64) NOT NULL COMMENT '用户主键id',
  `user_code` varchar(20) DEFAULT NULL COMMENT '商户号码',
  `user_type` tinyint(1) DEFAULT NULL COMMENT '商户类型：[0 普通商户，1 代理商户，2 子商户]',
  `product_code` varchar(64) NOT NULL COMMENT '业务类型',
  `product_name` varchar(64) NOT NULL COMMENT '业务类型名称',
  `user_wallet_account_no` varchar(50) NOT NULL COMMENT '用户账户编号',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态[0-待处理；1-成功；2-失败；3-异常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_product` (`order_no`,`product_code`,`user_id`) USING BTREE COMMENT '用户订单产品类型组合唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=133874 DEFAULT CHARSET=utf8 COMMENT='会员钱包账户记录日志';

SET FOREIGN_KEY_CHECKS = 1;