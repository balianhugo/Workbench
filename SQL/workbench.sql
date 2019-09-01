/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : workbench

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-09-01 11:02:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `wb_data_type`
-- ----------------------------
DROP TABLE IF EXISTS `wb_data_type`;
CREATE TABLE `wb_data_type` (
  `name` varchar(15) NOT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_data_type
-- ----------------------------
INSERT INTO `wb_data_type` VALUES ('wb_user_status', '0', '正常:0,冻结:1,删除:2', '系统用户状态');

-- ----------------------------
-- Table structure for `wb_menu_resource`
-- ----------------------------
DROP TABLE IF EXISTS `wb_menu_resource`;
CREATE TABLE `wb_menu_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `href` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_menu_resource
-- ----------------------------
INSERT INTO `wb_menu_resource` VALUES ('2', '4', 'page', '/user/page', '访问用户管理页面');
INSERT INTO `wb_menu_resource` VALUES ('3', '4', 'add', '/user/add', '新增用户信息');
INSERT INTO `wb_menu_resource` VALUES ('4', '4', 'update', '/user/update', '修改用户信息');
INSERT INTO `wb_menu_resource` VALUES ('5', '4', 'remove', '/user/delete', '删除用户');
INSERT INTO `wb_menu_resource` VALUES ('6', '4', 'bind', '/user/bind', '用户角色绑定');
INSERT INTO `wb_menu_resource` VALUES ('7', '4', 'search', '/user/list', '查询用户信息');
INSERT INTO `wb_menu_resource` VALUES ('8', '4', 'roles', '/user/bindRoles', '获取用户绑定的角色');
INSERT INTO `wb_menu_resource` VALUES ('9', '5', 'add', '/role/add', '新增角色信息');
INSERT INTO `wb_menu_resource` VALUES ('10', '5', 'update', '/role/update', '更新角色信息');
INSERT INTO `wb_menu_resource` VALUES ('11', '5', 'remove', '/role/delete', '删除角色信息');
INSERT INTO `wb_menu_resource` VALUES ('12', '5', 'page', '/role/page', '访问角色管理页面');
INSERT INTO `wb_menu_resource` VALUES ('13', '5', 'search', '/role/list', '查询角色信息');
INSERT INTO `wb_menu_resource` VALUES ('14', '5', 'menu', '/role/bindMenu', '更新角色菜单关联信息');
INSERT INTO `wb_menu_resource` VALUES ('15', '5', 'resource', '/role/updateResource', '更新角色资源关联信息');
INSERT INTO `wb_menu_resource` VALUES ('16', '5', 'menuList', '/role/menuList', '查看角色菜单关联信息');
INSERT INTO `wb_menu_resource` VALUES ('17', '5', 'resourceList', '/role/resourceList', '查看角色可配置的菜单资源');
INSERT INTO `wb_menu_resource` VALUES ('18', '5', 'menuResource', '/role/menuResource', '查看菜单可配置的资源');
INSERT INTO `wb_menu_resource` VALUES ('19', '6', 'add', '/menu/add', '新增菜单');
INSERT INTO `wb_menu_resource` VALUES ('20', '6', 'update', '/menu/update', '更新菜单');
INSERT INTO `wb_menu_resource` VALUES ('21', '6', 'remove', '/menu/delete', '删除菜单');
INSERT INTO `wb_menu_resource` VALUES ('22', '6', 'page', '/menu/page', '菜单管理访问页面');
INSERT INTO `wb_menu_resource` VALUES ('23', '6', 'search', '/menu/list', '查询菜单列表信息');
INSERT INTO `wb_menu_resource` VALUES ('24', '7', 'add', '/resource/add', '新增资源');
INSERT INTO `wb_menu_resource` VALUES ('25', '7', 'update', '/resource/update', '更新资源');
INSERT INTO `wb_menu_resource` VALUES ('26', '7', 'remove', '/resource/delete', '删除资源');
INSERT INTO `wb_menu_resource` VALUES ('27', '7', 'page', '/resource/page', '访问资源管理页面');
INSERT INTO `wb_menu_resource` VALUES ('28', '7', 'search', '/resource/list', '查询资源信息列表');
INSERT INTO `wb_menu_resource` VALUES ('29', '8', 'page', '/person/personPage', '用户个人信息页面');
INSERT INTO `wb_menu_resource` VALUES ('30', '8', 'fileupload', '/person/fileupload', '文件上传');
INSERT INTO `wb_menu_resource` VALUES ('31', '8', 'progress', '/person/progress', '获取文件上传进度');
INSERT INTO `wb_menu_resource` VALUES ('32', '8', 'update', '/person/updateUser', '更新用户信息');
INSERT INTO `wb_menu_resource` VALUES ('33', '9', 'page', '/person/passwordPage', '修改密码页面');
INSERT INTO `wb_menu_resource` VALUES ('34', '9', 'update', '/person/updatePassword', '密码修改操作');
INSERT INTO `wb_menu_resource` VALUES ('35', '11', 'page', '/typeData/page', '访问数据类型管理页面');
INSERT INTO `wb_menu_resource` VALUES ('36', '11', 'list', '/typeData/list', '查询数据类型');
INSERT INTO `wb_menu_resource` VALUES ('37', '11', 'add', '/typeData/add', '新增数据类型');
INSERT INTO `wb_menu_resource` VALUES ('38', '11', 'update', '/typeData/update', '修改数据类型');
INSERT INTO `wb_menu_resource` VALUES ('39', '11', 'delete', '/typeData/delete', '删除数据类型');
INSERT INTO `wb_menu_resource` VALUES ('40', '12', 'page', '/organization/page', '组织机构管理页面');
INSERT INTO `wb_menu_resource` VALUES ('41', '12', 'orgList', '/organization/orgList', '组织机构查询');
INSERT INTO `wb_menu_resource` VALUES ('42', '12', 'orgAdd', '/organization/orgAdd', '组织机构新增');
INSERT INTO `wb_menu_resource` VALUES ('43', '12', 'orgUpdate', '/organization/orgUpdate', '组织机构更新');
INSERT INTO `wb_menu_resource` VALUES ('44', '12', 'orgDel', '/organization/orgDel', '组织机构删除');
INSERT INTO `wb_menu_resource` VALUES ('45', '6', 'icons', '/menu/icons', '为菜单选择菜单图标');
INSERT INTO `wb_menu_resource` VALUES ('46', '12', 'orgUserList', '/organization/orgUserList', '查询机构下用户信息');
INSERT INTO `wb_menu_resource` VALUES ('47', '12', 'bindUser', '/organization/bindUser', '为组织机构添加用户');

-- ----------------------------
-- Table structure for `wb_organization_inf`
-- ----------------------------
DROP TABLE IF EXISTS `wb_organization_inf`;
CREATE TABLE `wb_organization_inf` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(15) NOT NULL,
  `org_name` varchar(20) NOT NULL,
  `parent_id` int(11) NOT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_organization_inf
-- ----------------------------
INSERT INTO `wb_organization_inf` VALUES ('1', '1', '测试', '0');
INSERT INTO `wb_organization_inf` VALUES ('20', '001', 'IT', '0');

-- ----------------------------
-- Table structure for `wb_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wb_role_menu`;
CREATE TABLE `wb_role_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `iconcls` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `parent_menu_id` int(11) NOT NULL,
  `sequence` varchar(5) NOT NULL,
  `level` tinyint(4) NOT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_role_menu
-- ----------------------------
INSERT INTO `wb_role_menu` VALUES ('1', '系统配置', '/img/topmenu/configuration.png', '', '0', 'a', '1');
INSERT INTO `wb_role_menu` VALUES ('2', '权限管理', '/wb/icons?name=/wall--arrow.png', '', '1', 'aa', '2');
INSERT INTO `wb_role_menu` VALUES ('3', '个人设置', '/wb/icons?name=/clipboard--pencil.png', '', '1', 'ab', '2');
INSERT INTO `wb_role_menu` VALUES ('4', '用户管理', '/wb/icons?name=/user--arrow.png', '/user/page', '2', 'aaa', '3');
INSERT INTO `wb_role_menu` VALUES ('5', '角色管理', '/wb/icons?name=/report--arrow.png', '/role/page', '2', 'aab', '3');
INSERT INTO `wb_role_menu` VALUES ('6', '菜单管理', '/wb/icons?name=/block--arrow.png', '/menu/page', '2', 'aac', '3');
INSERT INTO `wb_role_menu` VALUES ('7', '资源管理', '/wb/icons?name=/safe--arrow.png', '/resource/page', '2', 'aad', '3');
INSERT INTO `wb_role_menu` VALUES ('8', '个人信息', '/wb/icons?name=/user--pencil.png', '/person/personPage', '3', 'aba', '3');
INSERT INTO `wb_role_menu` VALUES ('9', '修改密码', '/wb/icons?name=/keyboard--arrow.png', '/person/passwordPage', '3', 'abb', '3');
INSERT INTO `wb_role_menu` VALUES ('10', '基础数据', '/wb/icons?name=/database--arrow.png', '', '1', 'ac', '2');
INSERT INTO `wb_role_menu` VALUES ('11', '类型数据', '/wb/icons?name=/layer--pencil.png', '/typeData/page', '10', 'aca', '3');
INSERT INTO `wb_role_menu` VALUES ('12', '组织管理', '/wb/icons?name=/gear--arrow.png', '/organization/page', '2', 'aae', '3');

-- ----------------------------
-- Table structure for `wb_role_menu_rel`
-- ----------------------------
DROP TABLE IF EXISTS `wb_role_menu_rel`;
CREATE TABLE `wb_role_menu_rel` (
  `menu_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`menu_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_role_menu_rel
-- ----------------------------
INSERT INTO `wb_role_menu_rel` VALUES ('1', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('1', '2');
INSERT INTO `wb_role_menu_rel` VALUES ('2', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('3', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('3', '2');
INSERT INTO `wb_role_menu_rel` VALUES ('4', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('5', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('6', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('7', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('8', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('8', '2');
INSERT INTO `wb_role_menu_rel` VALUES ('9', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('9', '2');
INSERT INTO `wb_role_menu_rel` VALUES ('10', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('11', '1');
INSERT INTO `wb_role_menu_rel` VALUES ('12', '1');

-- ----------------------------
-- Table structure for `wb_role_resource_rel`
-- ----------------------------
DROP TABLE IF EXISTS `wb_role_resource_rel`;
CREATE TABLE `wb_role_resource_rel` (
  `resource_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_role_resource_rel
-- ----------------------------
INSERT INTO `wb_role_resource_rel` VALUES ('2', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('3', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('4', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('5', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('6', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('7', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('8', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('9', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('10', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('11', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('12', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('13', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('14', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('15', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('16', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('17', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('18', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('19', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('20', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('21', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('22', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('23', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('24', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('25', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('26', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('27', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('28', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('29', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('30', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('31', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('32', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('33', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('34', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('35', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('36', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('37', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('38', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('39', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('40', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('41', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('42', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('43', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('44', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('45', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('46', '1');
INSERT INTO `wb_role_resource_rel` VALUES ('47', '1');

-- ----------------------------
-- Table structure for `wb_user_inf`
-- ----------------------------
DROP TABLE IF EXISTS `wb_user_inf`;
CREATE TABLE `wb_user_inf` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) NOT NULL,
  `login_password` varchar(50) NOT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `org_id` int(11) DEFAULT NULL,
  `org_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_user_inf
-- ----------------------------
INSERT INTO `wb_user_inf` VALUES ('1', 'admin', '15bf1a8581b3e5b2f7a096f0b24d44ba', 'admin', 'M', '13538794756', '0', '20', 'IT', 'test@test.com', '');
INSERT INTO `wb_user_inf` VALUES ('2', 'test', '15bf1a8581b3e5b2f7a096f0b24d44ba', 'nick', 'M', '13538794756', '0', '1', '测试', 'test@test.com', '');

-- ----------------------------
-- Table structure for `wb_user_org_rel`
-- ----------------------------
DROP TABLE IF EXISTS `wb_user_org_rel`;
CREATE TABLE `wb_user_org_rel` (
  `user_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_user_org_rel
-- ----------------------------
INSERT INTO `wb_user_org_rel` VALUES ('1', '20');
INSERT INTO `wb_user_org_rel` VALUES ('2', '1');

-- ----------------------------
-- Table structure for `wb_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `wb_user_role`;
CREATE TABLE `wb_user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_user_role
-- ----------------------------
INSERT INTO `wb_user_role` VALUES ('1', 'admin', '系统管理员，负责系统配置维护');
INSERT INTO `wb_user_role` VALUES ('2', 'guest', '访客，平台使用者');

-- ----------------------------
-- Table structure for `wb_user_role_rel`
-- ----------------------------
DROP TABLE IF EXISTS `wb_user_role_rel`;
CREATE TABLE `wb_user_role_rel` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wb_user_role_rel
-- ----------------------------
INSERT INTO `wb_user_role_rel` VALUES ('1', '1');
INSERT INTO `wb_user_role_rel` VALUES ('2', '2');
