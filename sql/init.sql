/* ======= 项目初始化必须的数据，勿添加个人代码 ======= */

INSERT INTO `admin_role` (`id`, `name`, `permissions`)
VALUES
	(1,'超级管理员','[\"ROLE_EDIT\",\"ROLE_LIST\",\"ADMIN_LIST\",\"USER_LIST\",\"ADMIN_EDIT\",\"USER_EDIT\",\"MERCHANT_EDIT\",\"MERCHANT_LIST\",\"SCENE_LIST\",\"SCENE_EDIT\",\"SCENETYPE_LIST\",\"SCENETYPE_EDIT\",\"CATEGORY_EDIT\",\"CATEGORY_LIST\",\"TEMPLATE_EDIT\",\"TEMPLATE_LIST\",\"BRAND_EDIT\",\"BRAND_LIST\",\"ARTICL_EDIT\",\"ARTICL_LIST\",\"PRODUCT_EDIT\",\"PRODUCT_LIST\"]');

/* password A111111+salt */
INSERT INTO `admin` (`id`, `mobile`, `name`, `password`, `role_id`, `img`, `status`, `created_at`, `signin_at`, `email`)
VALUES
	(2,'86-18303800683','张卫正','cd2818ca25923b7e9b1a7e2e3ec891b46f67193757d6fb140134bec947cf49e9',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg',1,1608029997506,NULL,'2387098750@qq.com');

/*删除了商户的不重复键mobile*/
alter table `merchant` drop  key `uk_mobile`;
/*商铺管理员权限表新增字段商户id*/
alter table `merchant_admin_role` add `merchant_id` bigint(20) not null comment '商户id';
/*替换商铺表不重复键uk——mobile*/
alter table `merchant_admin_role` add `merchant_id` bigint(20) not null comment '商户id';
alter table `merchant_admin` add unique key `uk_mobile`(`mobile`);

//添加商户会员升过期表唯一键
alter table `merchant_duration` add unique key `uk_merchantId`(`merchant_id`);
<<<<<<< HEAD
=======

//修改商户管理员权限表格属性
alter table `merchant_admin_role` modify `name` varchar(20) CHARACTER SET utf8mb4 not null;
>>>>>>> bd900cf118336176e03ea0345e50c8fb1a245427

//订单新增列订单秒杀详情备注
alter table trade add trade_payloads varchar(4096) default null comment "订单是否含有秒杀备注";
