/* 管理员角色 */
CREATE TABLE admin_role
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(16), /* 角色名称 */
    permissions VARCHAR(1024) /* 角色权限 */
);

/* 管理员信息表 */
CREATE TABLE admin
(
    id         INT PRIMARY KEY AUTO_INCREMENT, /* 主键 */
    mobile     VARCHAR(16) NOT NULL, /* mobile */
    email      VARCHAR (20) NOT NULL , /*邮箱*/
    name       VARCHAR(16), /* 用户名称/备注 */
    password   VARCHAR(64) NOT NULL, /* 密码 */
    role_id    INT, /* 角色 */
    img        VARCHAR(1024),/* 图片 */
    status     TINYINT(4), /* 1.正常 2.停用 */
    created_at BIGINT, /* 创建时间 */
    signin_at  BIGINT, /* 最后登录时间 */
    UNIQUE KEY `mobile` (`mobile`)
);

/* 管理员登录日志 & token 持久化 */
CREATE TABLE admin_session
(
    id        INT PRIMARY KEY AUTO_INCREMENT, /* 主键 */
    admin_id  INT, /* 关联admin */
    token     VARCHAR(64), /* token */
    signin_at BIGINT, /* 登录时间 */
    expire_at BIGINT, /* 过期时间 */
    UNIQUE KEY `token` (`token`)
);
-- 验证码
CREATE TABLE `code` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(30) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `code` varchar(10) NOT NULL,
  `expired_at` bigint(30) NOT NULL,
  `extra` varchar(300) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
