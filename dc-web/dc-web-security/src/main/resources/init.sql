DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
`role_id` int(2) NOT NULL,
`role_name` varchar(255) default '' not null COMMENT '角色名称',
`auth` varchar(255) default '' not null COMMENT '角色拥有的权限集合',
PRIMARY KEY (`role_id`)
) ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8 comment '角色表';
-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_USER', 'user');
INSERT INTO `role` VALUES ('2', 'ROLE_ADMIN', 'user,admin');
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` int not null auto_increment,
`username` varchar(255) default '' not null COMMENT '用户名',
`password` varchar(255) default '' not null COMMENT '密码',
`role_id` int(2) default '0' not null COMMENT '用户所属角色编号',
`last_password_change` bigint(13) default '0' not null COMMENT '最后一次密码修改时间',
`enable` tinyint(1) default '0' not null COMMENT '是否启用该账号，可以用来做黑名单',
PRIMARY KEY (`id`)
) ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8 comment '用户表';
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456', '2', '0', '1');
INSERT INTO `user` VALUES (2, 'guest', '123456', '1', '0', '1');
