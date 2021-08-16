# ************************************************************
# Sequel Pro SQL dump
# Version 5126
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.31)
# Database: smart
# Generation Time: 2021-03-01 12:19:47 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(10) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `img` varchar(1024) DEFAULT NULL,
  `bank_name` varchar(50) DEFAULT NULL,
  `account_no` varchar(20) DEFAULT NULL,
  `account_name` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `merchant_id`, `type`, `status`, `img`, `bank_name`, `account_no`, `account_name`)
VALUES
	(5,1,1,1,NULL,'中国农业银行','6230520710062139771','张卫正'),
	(6,1,2,1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/3/1/e/603ca07407e48dcd2d753ba52g9p97em.png',NULL,NULL,NULL),
	(7,1,3,1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/3/1/m/603ca13307e48dcd2d753ba6zj4sl7pn.png',NULL,NULL,NULL);

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `code` varchar(10) NOT NULL,
  `detail` varchar(500) NOT NULL,
  `is_default` tinyint(1) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `name` varchar(300) DEFAULT '',
  `created_at` bigint(30) NOT NULL,
  `update_at` bigint(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;

INSERT INTO `address` (`id`, `user_id`, `code`, `detail`, `is_default`, `mobile`, `name`, `created_at`, `update_at`)
VALUES
	(18,10015,'410581','茶店乡',1,'18303800683','张卫正',1611295279677,1611295279677);

/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(16) NOT NULL,
  `name` varchar(16) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `img` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `signin_at` bigint(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;

INSERT INTO `admin` (`id`, `mobile`, `name`, `password`, `role_id`, `img`, `status`, `created_at`, `signin_at`, `email`)
VALUES
	(2,'86-18303800683','张卫正','cd2818ca25923b7e9b1a7e2e3ec891b46f67193757d6fb140134bec947cf49e9',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg',1,1608029997506,NULL,'2387098750@qq.com');

/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table admin_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_role`;

CREATE TABLE `admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `permissions` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `admin_role` WRITE;
/*!40000 ALTER TABLE `admin_role` DISABLE KEYS */;

INSERT INTO `admin_role` (`id`, `name`, `permissions`)
VALUES
	(1,'超级管理员','[\"ROLE_EDIT\",\"ROLE_LIST\",\"ADMIN_LIST\",\"USER_LIST\",\"ADMIN_EDIT\",\"USER_EDIT\",\"MERCHANT_EDIT\",\"MERCHANT_LIST\",\"SCENE_LIST\",\"SCENE_EDIT\",\"SCENETYPE_LIST\",\"SCENETYPE_EDIT\",\"CATEGORY_EDIT\",\"CATEGORY_LIST\",\"TEMPLATE_EDIT\",\"TEMPLATE_LIST\",\"BRAND_EDIT\",\"BRAND_LIST\",\"ARTICL_EDIT\",\"ARTICL_LIST\",\"PRODUCT_EDIT\",\"PRODUCT_LIST\"]');

/*!40000 ALTER TABLE `admin_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table admin_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_session`;

CREATE TABLE `admin_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL,
  `signin_at` bigint(20) DEFAULT NULL,
  `expire_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `admin_session` WRITE;
/*!40000 ALTER TABLE `admin_session` DISABLE KEYS */;

INSERT INTO `admin_session` (`id`, `admin_id`, `token`, `signin_at`, `expire_at`)
VALUES
	(1,2,'6dTBLmbyaHugNXsw2dMxUpNRbusskI3XcO5D5Ivsklvl8TqKyxVqQE0xI31iEfaT',1608030436723,1608116836723),
	(2,2,'OAhFcUrPyyAoL4pLeW10tdomI2dcAK17A4owFi9ViWYFNyWipJlM04fOY4w0UTSX',1608096731567,1608183131567),
	(3,2,'sIDItQFg0LKXbSmXKLBC0oU3wVcB4dpoXBBaMFuEiqQ3U1FLY6SU7OBDWH5mienI',1608104307983,1608190707983),
	(4,2,'GSH2IuVx9euPYNlQviABtw2rbD3n52p2wmXfi3wuKk2LSiI9CvF5CNOUMOL8q28R',1608203238395,1608289638395),
	(5,2,'JDTCfOaaH9zJ0UElBqJPPoJyGxHj0IrhdQgWwPNFT43HuIQbW22u1yIIMjYc28Ct',1608289716737,1608376116737),
	(6,2,'YZUTqoDgXqiuwJulYC3GXS6qZN5jNGSPnvJuIRA8yKHDqnRld27REliLkcMmtHRf',1608465771254,1608552171254),
	(7,2,'H8uU9FNqyQ6FAKz034PyloktOEDItdvSAGvZqeY9nOAtdjDdLwsdCuy8nWw003qh',1608523881660,1608610281660),
	(8,2,'XgYfjrYL5zlVmdmKFYaJA5nJpM0D2HRNsYXlHAESGX88TTW5N6MhJDepS9d7gALa',1608554391384,1608640791384),
	(9,2,'BhfOHJV1lBH1SXIXjIujwVrFvZC4aNaRYR77Z9HgZL9XVndlzb1Z24EIPWibn5Km',1608640911917,1608727311917),
	(10,2,'eROHP9XGTS0JOlnLFzy0cMwMojwxP4C8ZkiVgfKsBNQWaejj6dymyReYh4omK4Mc',1608727966400,1608814366400),
	(11,2,'H9v7ZOKc4nCDBYAoAntLtkESs8Z9Sbg6jwUBwed048y3QL6K2fVMBeneEMHCdQZ7',1608814501140,1608900901140),
	(12,2,'Nv2ZfRRO2zlRn3yamgrrsoRAgvGFRyaBCQw030Ysf5VUg9lO2ZHhkNe97WY15h9F',1608895645274,1608982045274),
	(13,2,'1ZyxkDOyoc3jE1RVE6BfYVWPx5onssDmH0z7afcx7OGAAJRJyrEOXlQNtxoI9RMk',1608951109417,1609037509417),
	(14,2,'OV8WRdiHzW124RGPpOREdCEgEnm7EuTgUjWEyzqEqRLqN0roT4qapH9bMe1iC4fD',1608997616792,1609084016792),
	(15,2,'8bO6L3wdKHVrZb8FPGdTJIl63xsU4t90IuR1TCNxnSfHVaeYrAjbdP8FInEau9ps',1609045698585,1609132098585),
	(16,2,'wc7mvttjnc9YGWOPpSs5K1JDdTwO1GozvGRFMOLB0CVDTZWokcrY7R4TZRArgBJ9',1609122345819,1609208745819),
	(17,2,'lfjL1abfHGVVqRsHatb9U9yMXjz4RHWdAmLo1fjLYTRx9ahS6yQkpioMqBnSb1VN',1609201684793,1609288084793),
	(18,2,'WaFryZSJxupJQIE5VVp14hupfSiJTXal0MpFs83MiBorUARLCeVnnhx4WOVDpsbQ',1609203269739,1609289669739),
	(19,2,'o66wQrDd6oxGieSlRzKTHeKXDM6b25CUYEkCgbFF2FQP2NsQ5nxAUyHuR3ieKlCk',1609291424309,1609377824309),
	(20,2,'ultc0HOn4eTXam4kdVaQBFTjaILaAFXHowq3ABufeG9v8PZ64IMalMamyFp0H6pD',1609390351138,1609476751138),
	(21,2,'0ra33PEFzdLPcUab9av5nJAGe9qG9ha0jnYNyPpnRRfbsSwBcnCFsxjKy4XjvyfS',1609719893345,1609806293345),
	(22,2,'MxY5Lad85GYWvoWSsTjrahQGKIib8caAFav0GOXEITaDf1ydcsmLxovMmFIuVtpO',1609726841396,1609813241396),
	(23,2,'Px96Sn4ewXNFaLHwrqkhpezwA7avzlX46UDN3ZpcY9sv0UsoGXD6EYnAtYrOwBaX',1609813255317,1609899655317),
	(24,2,'In7WMLoLjD8eEEcrG16512KnTdFShA3DyB3bVJ7tjSWPeYMbriN5WnFnU0CinCWh',1609899760644,1609986160644),
	(25,2,'A2Oawp1Ja6RAcRtG1rXKQNbYBqZ6YbjLpSyfn98ElcInI4Shl9HitJNiHZbYcbeF',1609986207834,1610072607834),
	(26,2,'IyVVHwmFgNKOnKwy5wxcBUtIUrCictTVIUI0eoDPKwEtRHNQZoyPmYXIX3Z47IMi',1610154166900,1610240566900),
	(27,2,'eDYW4JjZTjKHZy0rkpNtroxCGDxVSpQrzdhsWyAU7NNNbLhkpvOnqw0tAZfDQvzu',1610249962431,1610336362431),
	(28,2,'9Ii6aUGRTqurAqDK8nkDQMsISrgkJ1oYbArSKtLSG83eK6buyueoEQccBi3KhUD1',1610348866819,1610435266819),
	(29,2,'UKKWUoH9YSuAjTFe6y0vxbh4GXFIaKJOuvw96LXYg5vz0SE3kv9SOg5UslpSGcc2',1610451440107,1610537840107),
	(30,2,'7kzCsi6uD0I0Jm4Aed8MB0F2a2giTgk3S9fheq79zqRyr0DxbVwTfcwTUjwfiZMs',1610540040697,1610626440697),
	(31,2,'1Y2d5unfG9lHU0yM0WtvXIV2zg02509zRCTsXO5keSNYSy49u52lsLbf6R5OvKYO',1610673146721,1610759546721),
	(32,2,'M2WNMyS2wi3EZf5uzcZ4OA5Z3mCzARp9n7WYylAhaN3VzPJiEXYCsCYadtLbpKQA',1610776504637,1610862904637),
	(33,2,'3VEcP1SUR6cTnGiy5U4BkdIEMHKfBxjMcLrLtDiq1VKb9TBuOLLwVDMTvo4xgJPk',1611031281268,1611117681268),
	(34,2,'DSZZRmU0FxOA32KI4L4fVX9LQ7n1eoole618XYesMsHZXW4jJXesaRBoNCMB1hOe',1611112157326,1611198557326),
	(35,2,'nUEmiIPuBBuwxTz87LU56v8JCdBdsuvudZHr5xZra7soXRR3Go9W7XazXy1lttXr',1611307798907,1611394198907),
	(36,2,'sLvY7uU6ziKOaiN0VfGVo8uqm8BWoREXrXcHV9Kbc3DYdFWTjSXPXttCyIGQNSW3',1614078428588,1614164828588),
	(37,2,'NNAWHOCQeisC5gX6nqjZwlzuuk3s4i831dSGX0Zm2Fw2uUoW09ftRT8In8sMTI2y',1614168473099,1614254873099),
	(38,2,'zFdyFkfV8LKRmQ3etVm4dibBwfrmK6ibXNxlH7nAaQ5fA3IBG0IimvaEVIp8z0pt',1614489592628,1614575992628),
	(39,2,'5NAqc8OiZYPrKvmV6lWBv57W6EeVwbzG8rFLQHv8WI358ho8HxOUpUqadjSdjwzr',1614583646874,1614670046874);

/*!40000 ALTER TABLE `admin_session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table article
# ------------------------------------------------------------

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `created_at` bigint(20) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `page_view` int(10) unsigned NOT NULL DEFAULT '0',
  `picture` varchar(500) NOT NULL,
  `intro` varchar(500) NOT NULL,
  `collect_num` bigint(11) DEFAULT NULL,
  `support_num` bigint(20) DEFAULT NULL,
  `author_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_title` (`id`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;

INSERT INTO `article` (`id`, `title`, `created_at`, `status`, `page_view`, `picture`, `intro`, `collect_num`, `support_num`, `author_id`)
VALUES
	(10065,'大连：有病例做11次核酸检测才呈阳性',1609759914674,1,23333,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/4/d/5ff2fc5e1aa3b9c79abf8af5baqsud2t.png','大连：有病例做11次核酸检测才呈阳性',122,2221,2),
	(10066,'河北20例确诊！',1609919861785,2,444,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/6/9/5ff56d619a4959e3ed8b89171opc7och.png','河北20例确诊！国家卫健委：昨日新增确诊病例32例，其中本土病例23例',1,2,2),
	(10067,'现代化科技的厨房应该是什么样子？',1611033706447,2,11123,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/x/60066bce737bb92cdec20f9a8ns0wgio.png','厨房应该是什么样子？——此开卷第一回也。作者自云：曾历过一番梦幻之后，故将真事隐去',111,23,2),
	(10068,'家具选材十个非常有用的小技巧，家具选',1611033849681,1,44,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/s/60066cbe737bb92cdec20f9bxj1zli0z.png','家具选材十个非常有用的小技巧，家具选家具选材十个非常有用的小技巧，',232,67,2),
	(10069,'家具选材十个非常有用的小技巧，家具选材',1611034078211,1,221,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/j/60066d39737bb92cdec20f9cen6lufd5.png','小伙子你这什么车啊?AE86,家具选材十个非常有用的小技巧，家具选材',46,44,2),
	(10070,'水电装修质量验收的标准是什么',1611034159611,1,1267,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/4/60066e10737bb92cdec20f9dbdkjha4r.png','水电装修质量验收的标准是什么水电装修质量验收的标准是什么啊啊啊啊啊啊',563,11,2);

/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table article_content
# ------------------------------------------------------------

DROP TABLE IF EXISTS `article_content`;

CREATE TABLE `article_content` (
  `article_id` bigint(20) NOT NULL,
  `content` text CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `article_content` WRITE;
/*!40000 ALTER TABLE `article_content` DISABLE KEYS */;

INSERT INTO `article_content` (`article_id`, `content`)
VALUES
	(10065,'中新网大连1月4日电(记者 杨毅)大连市民政局局长汤易4日在当地政府举行的疫情防控新闻发布会上表示，由于本次疫情具有潜伏期长、传播速度快、情况复杂等特点，有的病例做了11次核酸检测才显出阳性，有的地区检测6轮还有阳性病例出现。\n\n汤易表示，经过反复分析研判，如果机械地执行原来的14天居家隔离标准，势必存在较大不确定性，给疫情防控带来隐患。\n\n汤易说，在对去过金座商厦、医大二院的重点人群进行隔离管控中，我们发现，每个隔离对象都有上述区域的接触史，但由于接触当时上述区域尚未出现确诊病例，没有将他们发现并进行居家隔离，直到后来发现确诊病例后，才对他们进行居家隔离，在这段时间差里，他们是在全市有疫情出现的情况下自由活动的，都有接触病毒的可能性，因此居家隔离起始时间应从进入居家隔离状态，即从落实“人不出户”管控之日起算，实施“14+7”管控措施，期间做4次核酸检测(第1、7、13、20天)，均呈现阴性才能解除居家隔离。\n\n汤易表示，本次疫情传播力强、速度快，居家隔离人员一定要严格执行居家隔离工作细则等有关规定，科学规范落实居家隔离各项要求，做到科学居家、人不出户。如在居家隔离期内人员擅自外出的，一经发现，该户所有居家隔离人员将被送至隔离酒店实施集中隔离，并重新计算“14+7”隔离时间，集中隔离产生的费用自理；同时，该户所在单元的全体住户都将因为可能感染病毒而全部居家隔离“14+7”天。(完)'),
	(10066,'<div data-islow-browser=\"0\"><p>1月6日，国家卫健委网站通报，1月5日0&mdash;24时，31个省（自治区、直辖市）和新疆生产建设兵团报告新增确诊病例32例，其中境外输入病例9例（广东3例，陕西3例，上海2例，天津1例），本土病例23例（河北20例，北京1例，辽宁1例，黑龙江1例）；无新增死亡病例；新增疑似病例2例，其中境外输入病例1例（在上海），本土病例1例（在辽宁）。</p><p>当日新增治愈出院病例21例，解除医学观察的密切接触者784人，重症病例较前一日增加1例。</p><p>境外输入现有确诊病例285例（其中重症病例4例），现有疑似病例2例。累计确诊病例4348例，累计治愈出院病例4063例，无死亡病例。</p><p>截至1月5日24时，据31个省（自治区、直辖市）和新疆生产建设兵团报告，现有确诊病例443例（其中重症病例14例），累计治愈出院病例82138例，累计死亡病例4634例，累计报告确诊病例87215例，现有疑似病例3例。累计追踪到密切接触者912596人，尚在医学观察的密切接触者17736人。</p><p>31个省（自治区、直辖市）和新疆生产建设兵团报告新增无症状感染者64例（境外输入19例）；当日转为确诊病例8例（境外输入3例）；当日解除医学观察14例（境外输入10例）；尚在医学观察无症状感染者360例（境外输入246例）。</p><p>累计收到港澳台地区通报确诊病例9912例。其中，香港特别行政区9049例（出院8127例，死亡153例），澳门特别行政区46例（出院46例），台湾地区817例（出院697例，死亡7例）。</p></div><p><br></p>'),
	(10067,'<pre>&mdash;&mdash;此开卷第一回也。作者自云：曾历过一番梦幻之后，故将真事隐去，而借\n通灵说此《石头记》一书也，故曰&ldquo;甄士隐&rdquo;云云。但书中所记何事何人?自己又\n云：&ldquo;今风尘碌碌，一事无成，忽念及当日所有之女子：一一细考较去，觉其行止\n见识皆出我之上。我堂堂须眉诚不若彼裙钗，我实愧则有馀，悔又无益，大无可如\n何之日也。当此日，欲将已往所赖天恩祖德，锦衣纨之时，饫甘餍肥之日，背父\n兄教育之恩，负师友规训之德，以致今日一技无成、半生潦倒之罪，编述一集，以\n告天下；知我之负罪固多，然闺阁中历历有人，万不可因我之不肖，自护己短，一\n并使其泯灭也。所以蓬牖茅椽，绳床瓦灶，并不足妨我襟怀；况那晨风夕月，阶柳\n庭花，更觉得润人笔墨。我虽不学无文，又何妨用假语村言敷演出来?亦可使闺阁\n昭传。复可破一时之闷，醒同人之目，不亦宜乎？&rdquo;故曰&ldquo;贾雨村&rdquo;云云。更于篇\n中间用&ldquo;梦&rdquo;&ldquo;幻&rdquo;等字，却是此书本旨，兼寓提醒阅者之意。</pre><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>'),
	(10068,'<pre>看官你道此书从何而起?说来虽近荒唐，细玩颇有趣味。却说那女娲氏炼石补天之时，于大荒山无稽崖炼成高十二丈、见方二十四丈大的顽石三万六千五百零一块。那娲皇只用了三万六千五百块，单单剩下一块未用，弃在青埂峰下。谁知此石自经锻炼之后，灵性已通，自去自来，可大可小。因见众石俱得补天，独自己无才不得入选，遂自怨自愧，日夜悲哀。一日正当嗟悼之际，俄见一僧一道远远而来，生得骨格不凡，丰神迥异，来到这青埂峰下，席地坐谈。见着这块鲜莹明洁的石头，\n且又缩成扇坠一般，甚属可爱。那僧托于掌上，笑道：&ldquo;形体倒也是个灵物了，只是没有实在的好处。须得再镌上几个字，使人人见了便知你是件奇物，然后携你到那昌明隆盛之邦、诗礼簪缨之族、花柳繁华地、温柔富贵乡那里去走一遭。&rdquo;石头听了大喜，因问：&ldquo;不知可镌何字?携到何方?望乞明示。&rdquo;那僧笑道：&ldquo;你且莫问，日后自然明白。&rdquo;说毕，便袖了，同那道人飘然而去，竟不知投向何方。</pre><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>'),
	(10069,'<p>一、 水改造 1、 水路工程安装必须符合设计要求和行业规范。 2、 管道的铺设必须整齐，水表安装水平、方向正确。 3、 管道及管道支座铺设必须牢固。 4、 接头紧密、平直、无砂眼，接头严密，无漏水及滴漏现象。 5、 水龙头预留位置正确合理（根据设计和甲方洁具型号定水的出水口和下水口）。 6、 外露排水管的铺设也应牢固、平直、美观、合理、无漏水现象。 7、 水出口与排水管承口的连接必须严不漏。 8、</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>'),
	(10070,'<p>电改造 1、 所用电线必须符合工程用电要求。 2、 导线连接紧固，导线接头要受力、并有足够的强度。 3、 剥离电线绝缘层时不得损伤电线线芯。 4、 接地线应保证导线的连续性，不允许设置保险、开关、接地电阻应小于10欧姆。 5、 电线与电气元件连接后，无绝缘距离不能大于3mm。紧压连接的导线应顺压紧方向绕一圈或使用接线端子，支紧连接时孔内电线截面积应超过0.5倍孔面积。 6、 采用绞接法时绞接长度应不小于5圈，采用邦扎长度为芯线直径的10倍，并且连接后搪上焊锡，用黑电工胶布包扎时内应包扎塑料防潮绝缘胶布。</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>');

/*!40000 ALTER TABLE `article_content` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bill
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) NOT NULL,
  `merchant_id` int(10) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `remark` varchar(1024) DEFAULT 'null',
  `created_at` bigint(20) NOT NULL,
  `status` tinyint(1) DEFAULT '0',
  `trade_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `imgs` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;

INSERT INTO `bill` (`id`, `order_number`, `merchant_id`, `amount`, `remark`, `created_at`, `status`, `trade_id`, `user_id`, `imgs`)
VALUES
	(1,'20210225203050457',1,1390000,NULL,1614256253451,1,17,0,''),
	(3,'20210226152912254',3,5900,NULL,1614324564604,3,19,10015,'[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/f/2021/3/1/j/603c5a3634b12dc9dd8919eclq1xh5kl.png\"]'),
	(4,'20210226153203688',1,1390000,NULL,1614324741447,1,20,10015,'[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/4/60066e10737bb92cdec20f9dbdkjha4r.png\"]');

/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cart
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `num` int(10) NOT NULL,
  `product_sno` varchar(20) NOT NULL,
  `merchant_id` tinyint(1) NOT NULL,
  `created_at` varchar(15) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_usr_pro_sno` (`user_id`,`product_id`,`product_sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `code`;

CREATE TABLE `code` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(30) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `code` varchar(10) NOT NULL,
  `expired_at` bigint(30) NOT NULL,
  `extra` varchar(300) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;

INSERT INTO `code` (`id`, `created_at`, `subject`, `username`, `code`, `expired_at`, `extra`)
VALUES
	(18,1611064641221,'register','86-18303800683','957258',1611065241221,NULL),
	(19,1611064860448,'register','86-18303800683','933978',1611065460448,NULL),
	(20,1611064975840,'register','86-18303800683','555915',1611065575840,NULL),
	(21,1611102644204,'register','86-18303800683','284859',1611103244204,NULL),
	(22,1611103080505,'register','86-18303800683','772777',1611103680505,NULL),
	(23,1611103739152,'register','86-18303800683','965851',1611104339152,NULL),
	(24,1611205937765,'register','86-13271377120','822882',1611206537765,NULL),
	(25,1611205936363,'register','86-13271377120','982648',1611206536363,NULL),
	(26,1611205939480,'register','86-13271377120','172370',1611206539480,NULL),
	(27,1611205939893,'register','86-13271377120','873089',1611206539893,NULL),
	(28,1611205940098,'register','86-13271377120','733159',1611206540098,NULL),
	(29,1611205940449,'register','86-13271377120','559929',1611206540449,NULL),
	(30,1611205940815,'register','86-13271377120','365079',1611206540815,NULL),
	(31,1611205941047,'register','86-13271377120','174010',1611206541047,NULL),
	(32,1611205963855,'register','86-13271377120','693398',1611206563855,NULL),
	(33,1611206177423,'register','86-13271377120','238051',1611206777423,NULL),
	(34,1611206216377,'register','86-18303800683','388328',1611206816377,NULL),
	(35,1611206766364,'register','86-13271377120','087527',1611207366364,NULL),
	(36,1611207316552,'register','86-13271377120','956743',1611207916552,NULL),
	(37,1614045551241,'register','86-18303800683','933081',1614046151241,NULL),
	(38,1614047783680,'register','86-18303800683','083604',1614048383680,NULL),
	(39,1614047853920,'register','86-18303800683','551496',1614048453920,NULL),
	(40,1614047942234,'register','86-18303800683','652687',1614048542234,NULL),
	(41,1614048270481,'register','86-13271377120','248312',1614048870481,NULL),
	(42,1614048329119,'register','86-13271377120','009970',1614048929119,NULL),
	(43,1614048471416,'register','86-13271377120','325325',1614049071416,NULL),
	(44,1614048639276,'register','86-13271377120','123565',1614049239276,NULL),
	(45,1614048656327,'register','86-13271377120','665155',1614049256327,NULL),
	(46,1614048703415,'register','86-13271377120','950960',1614049303415,NULL),
	(47,1614048754685,'register','86-13271377120','324695',1614049354685,NULL),
	(48,1614048802140,'register','86-13271377120','621626',1614049402140,NULL),
	(49,1614048840635,'register','86-13271377120','579830',1614049440635,NULL),
	(50,1614048891506,'register','86-13271377120','132189',1614049491506,NULL),
	(51,1614048985704,'register','86-13271377120','436521',1614049585704,NULL),
	(52,1614049225550,'register','86-13271377120','422009',1614049825550,NULL),
	(53,1614049330414,'register','86-13271377120','346994',1614049930414,NULL);

/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `from_id` int(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `collect` WRITE;
/*!40000 ALTER TABLE `collect` DISABLE KEYS */;

INSERT INTO `collect` (`id`, `user_id`, `type`, `from_id`, `status`)
VALUES
	(1,10015,4,3,1),
	(2,10015,1,4,1),
	(3,10015,3,3,1),
	(4,10015,3,2,1),
	(5,10015,2,10069,2),
	(7,10015,2,10067,2),
	(8,10015,2,10068,1),
	(9,10015,1,3,2),
	(10,10015,1,2,1);

/*!40000 ALTER TABLE `collect` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant`;

CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(16) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `imgs` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `signin_at` bigint(20) DEFAULT NULL,
  `logo` varchar(1024) NOT NULL DEFAULT '',
  `last_modify` bigint(20) DEFAULT NULL,
  `location` varchar(500) NOT NULL DEFAULT '',
  `sequences` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;

INSERT INTO `merchant` (`id`, `mobile`, `name`, `imgs`, `status`, `created_at`, `signin_at`, `logo`, `last_modify`, `location`, `sequences`)
VALUES
	(1,'86-18303800683','微星官方旗舰店微星官方旗舰店微星官方旗舰店微星官方旗','[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/5/5fffb0e39712b980c9b1f1bcqri7xv4n.png\"]',1,1610592501311,NULL,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/j/5fffb0729712b980c9b1f1bb3pi5wp8d.png',1611192888279,'{\"code\":\"410184\",\"detail\":\"龙湖镇\",\"lat\":34.395692,\"lng\":113.740772,\"poiaddress\":\"河南省郑州市新郑市人民路西段186号\",\"poiname\":\"新郑市人民政府\"}','[\"030000\"]'),
	(2,'86-18437211582','intelintelintelintelintelintelintelintel','[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/k/5fffb1ef9712b980c9b1f1beb36ynvrl.png\"]',1,1610592780119,NULL,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/r/5fffb1839712b980c9b1f1bdwiqou3di.png',1611193033156,'{\"code\":\"410184\",\"detail\":\"龙湖镇\",\"lat\":34.395906,\"lng\":113.742371,\"poiaddress\":\"河南省郑州市新郑市中华路与人民路交叉口东北角\",\"poiname\":\"中信银行(新郑支行)\"}','[\"030000\",\"010000\",\"040000\",\"050000\",\"020000\"]'),
	(3,'86-18803031122','浮雕家饰旗舰店','[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/j/60010a9f3cb779015090adbbzr9vkhy2.png\"]',1,1610680998445,NULL,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/8/600109eb3cb779015090adba4drz9cf6.png',1611125241597,'{\"code\":\"410184\",\"detail\":\"龙湖镇\",\"lat\":34.395692,\"lng\":113.740772,\"poiaddress\":\"河南省郑州市新郑市人民路西段186号\",\"poiname\":\"新郑市人民政府\"}','[\"040000\",\"030000\"]');

/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant_admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant_admin`;

CREATE TABLE `merchant_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(16) NOT NULL,
  `name` varchar(16) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `img` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `signin_at` bigint(20) DEFAULT NULL,
  `role_id` varchar(1024) DEFAULT NULL,
  `merchant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile-mchadm` (`mobile`,`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `merchant_admin` WRITE;
/*!40000 ALTER TABLE `merchant_admin` DISABLE KEYS */;

INSERT INTO `merchant_admin` (`id`, `mobile`, `name`, `password`, `img`, `status`, `created_at`, `signin_at`, `role_id`, `merchant_id`)
VALUES
	(1,'86-18303800683','张卫正','cd2818ca25923b7e9b1a7e2e3ec891b46f67193757d6fb140134bec947cf49e9',NULL,1,NULL,NULL,'1',1),
	(2,'86-18437211582','刘豪','cd2818ca25923b7e9b1a7e2e3ec891b46f67193757d6fb140134bec947cf49e9',NULL,1,NULL,NULL,'1',2),
	(3,'86-18303031122','岛市老八','cd2818ca25923b7e9b1a7e2e3ec891b46f67193757d6fb140134bec947cf49e9',NULL,1,1610680998506,NULL,'1',3);

/*!40000 ALTER TABLE `merchant_admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant_admin_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant_admin_role`;

CREATE TABLE `merchant_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `permissions` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `merchant_admin_role` WRITE;
/*!40000 ALTER TABLE `merchant_admin_role` DISABLE KEYS */;

INSERT INTO `merchant_admin_role` (`id`, `name`, `permissions`)
VALUES
	(1,'超级管理员','[\"ROLE_EDIT\",\"ROLE_LIST\",\"PRODUCT_EDIT\",\"PRODUCT_LIST\"]');

/*!40000 ALTER TABLE `merchant_admin_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant_admin_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant_admin_session`;

CREATE TABLE `merchant_admin_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_admin_id` int(11) DEFAULT NULL,
  `token` varchar(64) DEFAULT NULL,
  `signin_at` bigint(20) DEFAULT NULL,
  `expired_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `merchant_admin_session` WRITE;
/*!40000 ALTER TABLE `merchant_admin_session` DISABLE KEYS */;

INSERT INTO `merchant_admin_session` (`id`, `merchant_admin_id`, `token`, `signin_at`, `expired_at`)
VALUES
	(1,1,'auBcUyF3eMesecDVDirAzxUYxi0SXl1f1bMC6SzhPFnSvCwfTGLIbwyykokUlv2U',1609204806001,1609291206001),
	(2,1,'1DRxcqjiNnYptGQRXh5D0ItVFg15XQb96uk7cfUnYZ5R1Uas4VetwRgp29fvyAlY',1609204817510,1609291217510),
	(3,1,'hnOSC35PHBdXS0FODOAbIwVQ3Uo7shP8gLRd0fYYUuP9x3ETrRfDc0o218sd2CLG',1609205169370,1609291569370),
	(4,1,'XP13ib7kwmMBU9a4uwN9nMVrjqCJGLql9PKW7tI6F4NRKW4iP1HNBitMIh7O8v22',1609205307407,1609291707407),
	(5,1,'fEHW0xJvhW1BW3hCFsVcmIImNZ8UprYYWd4vRwFSP97daBu1VQ1PvlYO47S0nDfP',1609205389958,1609291789958),
	(6,1,'RfBfh7lwPIIl2gDRvbU5pdmG7Yl9wPDBl7ASPcDq573Zof9SE4KV6lmo3WouMpeK',1609205787911,1609292187911),
	(7,1,'fSPPQEixXKG7WsIB0E9h1rH7Dl3TVeWPN7bOqd6tejPvwasDLZ7veIbYzOg3tB7c',1609205792157,1609292192157),
	(8,1,'mNN0gKjVOQbcGOdR21puONSaVOUpBIeyQ0CtBpk23WurAYCRB5lajSKDtK71CGMP',1609205810486,1609292210486),
	(9,1,'G3q4X3hmPgLS9d9DJh8fID3M15aUdV27ad3WnK64n7DZlcGhFw2O5o2jT8g4uuzs',1609205823947,1609292223947),
	(10,1,'SSVCOel4CLK1w480RYVxzBEqqGo6f64TDjYrdaSBtBqPyORwqpdvwfVrd3zZdtw8',1609205862900,1609292262900),
	(11,1,'uLg80IGs8FqruHg2d6pTbFkX8W17GaXVKfbbUvFBWMOohTp1ElKVEK04G1YqKiuk',1609205952298,1609292352298),
	(12,1,'NWmebDzOIj9L4VVLWxAQzbvv8MvbEp4mahp8jhGCjDrwJCqvBt8bJTILPbRXm5fR',1609206045805,1609292445805),
	(13,1,'UYmqazGZ8OrTxx5oQTYORjIS8SFpdXSbWPgQkUFlpixLPe07EkA2T9IG9QmpMpyG',1609206319119,1609292719119),
	(14,1,'Rqq3c5sRXTmk2nR8dWbbrg3I0hN846L71eFzvkpdtRrIKvQVCoBSYOAOjzd0ZW2t',1609206426777,1609292826777),
	(15,1,'OXqE5sJ5kRVPo1AJwqoM8edtK315mSl8H0YV5BhEmnG8PBrGtbRzGg40cyBNqReS',1609206434104,1609292834104),
	(16,1,'hjp2QZeQnDlc7zTIxmoGbhprJQCBnLDnDfSQB5UWVesQjNeNYj8texeET7seRkPK',1609206447329,1609292847329),
	(17,1,'5WNK6BVcgf18TNW9uzi3g0fPC4qRxTymIyl9J8zvb5EXi8oYerIHn3lgfRZlm6s2',1609206610405,1609293010405),
	(18,1,'o6aqDDUeXeBiy6xjQzZwbyuaSh3WqdWXUVuieICa2N2vrOI3NL4sHDax0YkJIQFd',1609206866410,1609293266410),
	(19,1,'ihuYCZLxLxrzzsnIovtau02X8TWGMBaqQkvtGPfuRg08MgWtJARhrPgDEmXBVZKI',1609206907299,1609293307299),
	(20,1,'HNSVQyugVRouFMDVrGFScv4hQw57Zm5aNxvV1qLv30HhwdWdRo1Wkf2EMuxtn0Ep',1609206981160,1609293381160),
	(21,1,'RO2zc2RP62Ofan7AL5RA5YU6TK95f8ACEnpfS7Zr0vZH302mTFvLZEEcYlWIoVX1',1609206997142,1609293397142),
	(22,1,'v9XznKCmhhlkkgFLR0ZtG5mS0JqEH0scLSZhirFs7elBwXJPZwcG0TK7719dppm7',1609207674723,1609294074723),
	(23,1,'IeL9PpxlijdndwEcxmDMQ9cryzpUd95dUQ5nWGGBdEeSDjLW9P0xrZifpffSdFeN',1609207781878,1609294181878),
	(24,1,'YvuYa8Cjl2vakjJjJUlUfPdmwnwpGxjedJriWADGImAEbsXGo7JTt2fpL8TYkxQu',1609207809090,1609294209090),
	(25,1,'BGW5RXetY8IlsDriGCG6EYJcf6sAMuqt12uLAM8gFX20k2m5Pjn5UZ1DlzgKjCPd',1609207965300,1609294365300),
	(26,1,'dllZWnd9wAeXFrrBjypHShiLEuNsTYL1TuHZ33C7QlkgAVgRIV62nK2Mn8z4qHLZ',1609207975037,1609294375037),
	(27,1,'QBPOha35RwuxcaeLTiefOcX4oWVp2KggBtcaHJ8sOVkFCACjhAtX0GpAoPsvBacw',1609208168194,1609294568194),
	(28,1,'jckWRHqtB0iJ2Jt1UdLTvsD4m3U1dYMFhILhQuVUhOIiJl9gRkLtDTtjcPvS8lbG',1609208322175,1609294722175),
	(29,1,'U35jTTvpDPdePbGOE3KFGsn6Dfa4iIyLhxcgGnGXdk7GoYixefrc01M734OdKxIi',1609208601550,1609295001550),
	(30,1,'qKwho0t7qJvbmzUd9tb51N55d1MJyuTChQoQarBNQum2hV0uen5VeZ9WuuoouoI9',1609208601550,1609295001550),
	(31,1,'DJ0QFBvbk4Bs1JtQXtxZt9klMGApvAvO4aUo853DkVJcb186PbkKMwk4UpDbnc4P',1609208601550,1609295001550),
	(32,1,'Wdou8nbN87W4Fr5msPh0eLjM8CyGIu7JsWsdyX6amkKdJKqHzcoyV6LJx3Wtle0z',1609208601553,1609295001553),
	(33,1,'EpwrTo5d8m4t6oo0bt3sZO2XCqbRfGkqKk5XqgVkkoIRPdC8yiNMoVYFv2r1Ummp',1609208615745,1609295015745),
	(34,1,'ZkBM5rQsggwViAkfWjys40kWq1ddbqaZRJdom4Ac8533ilzZP4Hq81LgvsEtDHeK',1609208631316,1609295031316),
	(35,1,'RZEMdjk8DUJ8LzNfdeiif62vb8WS2m814eg4rbjHrvT0yYEtBewl4P71aiVgOtW2',1609208855894,1609295255894),
	(36,1,'WchQiIDlKaBE1EDJsqPENjAO5EHR5R0EhwccofyYIBydD4cwaZ948Wnh57US7AJH',1609209009598,1609295409598),
	(37,1,'qKQ0N03aw6Q8cQWDGn6r5sqePzLAUCUV2ZE4l8YDtWD9lN55KUScf2Ol8yqXkNQZ',1609209066024,1609295466024),
	(38,1,'NBOhjBdFLKd2W84IlMABXa4lY8Hrly7t7W0DQVEprQBl4buAka7mYTFTcKMGcnDj',1609209154631,1609295554631),
	(39,1,'nv0rnnueyWktyvOW6yECQOTSrdGRFwzhuj9WvOMaRZgQGkrlacH41YUwZJcbHODo',1609209177911,1609295577911),
	(40,1,'wh97NFR8GVVHg14zXzYXXHOvdv0xo89tE2ixN2a7ByroEN8POhZA0hNnyztpvS2C',1609209348846,1609295748846),
	(41,1,'96qGoQua1xDTTm2xMcmPuiewtMYOo0atiJTxoHLBaMVGfArsLhKSEjwhGWH5JvLK',1609209944791,1609296344791),
	(42,1,'grELakepZCXqdMOK9afwL3k9j1DNF8QEUIKHoVcGlszSft4iM0prUtwpqeBaAcNp',1609210145430,1609296545430),
	(43,1,'EyJhu8faaeA1lyuYV521zEfHxsuTbi0pAb3iHV7aLFLeRLNDmoRErbnONGznDkds',1609210155257,1609296555257),
	(44,1,'HxlB7F8UKgN1qYEzGeKeyjz07KUMNndwAtPdJUnwYjHW6FrQAkI46Z5vNDj1SmLj',1609210169201,1609296569201),
	(45,1,'hgfQZ7B69E7WSouFQwodSJMcocQ0qGFkPXb4iKemy3o7Ff4Ja9a7PBJmDPsEyjoT',1609212399261,1609298799261),
	(46,1,'j8sMbaD483j7ydFse3t8usZG5wmWmWMpGf3oNdYnUQH0S76MrN3LUjP2mLi1ZYZm',1609212427907,1609298827907),
	(47,1,'r18kVGU2UJ0C7Ea4pLGNa3YRLCgOzhcSuOgXRc92Uaxndr4mK80cVjo5MSRjG5tN',1609222776734,1609309176734),
	(48,1,'HDKvdswu4JGJGo5NIrd7LatV8Sdm8T3noBYhsBWB4XpSAWxSYLd8345cHAJI0YWV',1609222787188,1609309187188),
	(49,1,'EKjJFRMxJ9z9WWMIYMQqWK54SkFfKTtsJQed79VEnxn2oinjpbEw2G0RRtE1uSzQ',1609222881457,1609309281457),
	(50,1,'FGuAJ4P2pDlFvNXzG7JHbaX7BK8Lk1Cwxsgn1hOXTE8SUNoPpLb5NPs1IB8Xq9bh',1609222899933,1609309299933),
	(51,1,'5JGiQPaoMzaPA7iCjPtU8AgW5fkImd496IThH0NraHohl60In7W1flu9q6b12532',1609309375810,1609395775810),
	(52,1,'bNP9A5NGfIIqa6xMTbLLqjrCP8nb1l2eP3TkxXVHDjgQJebHI2CRpuxv6hqzn4xz',1609319898603,1609406298603),
	(53,1,'syjKjBxjAbrnC791vrgCnnbR1dVNRMB7a0vkwjV8MAFk5PQ5H9oXpc9rjLvxex7a',1609319984611,1609406384611),
	(54,1,'UQBR1xQoNwqjBatD7RHLxudhaqQ65OSECfGAiYCC3YWRRNbHN6cXLFyQztIdPpbt',1609329418704,1609415818704),
	(55,1,'n4G3Jfu2WMLvQyWADUGExxjjoFB1zltQTJVZCIVvPtF5Y7DvwCfpOVtZ1eAV3HdV',1609329418719,1609415818719),
	(56,1,'SVAjTRRTFGfFpr8qxuZQIzrFJxdFzoolUjPeRkm4TG0mM62Ky4IOcM9IdI2APZ3Z',1609329418704,1609415818704),
	(57,1,'O3nlLINz83SNalkp8m5iCulhOEoosRpiqV6SFM8DqLCpLRhjDBcgkRe0LlHS6250',1609380495050,1609466895050),
	(58,1,'XAzBC4Yti2KouRNA7OhZ6pd0bC1NvoYrsjWLkhTclaLZMHF2CUFs8VGt5qo4Shzm',1609380553125,1609466953125),
	(59,1,'I6KC83nuFN7wSZETv17oHphevp1bqjvz3Av4fadS1kBL3eTe6NRbd8BSn9KTS1qW',1609380601579,1609467001579),
	(60,1,'P9m2c695mZQRJaBLHeV4V9CwKj4Nd1eaHrDGPMdYJFN81EWZSQlFsLJ9VPkPaSfW',1609380612252,1609467012252),
	(61,1,'zxF3SdBtCIRGXEZS7qB8iTdxBK4mvlBMIDSfxj9Ze5xuBge2FuJNwpTlnHPoJGrk',1609380645573,1609467045573),
	(62,1,'MIVXVID5tXQvcTLk6Pn3wtoPjODbXIIgqv7N7bgFMLaN6bazURX68xZsQhiZVMH4',1609382999800,1609469399800),
	(63,1,'fFCn8l3iIB7pMRuK2ippQPOzAKFdd2mMhHRT3jSlIwTBa4jyeCablb4NQ7qpRUtq',1609393970498,1609480370498),
	(64,1,'PA0S1dgy7wWv01JX5dID8SdduoAoj14uilbBhvcYjOBMkgBGAeDpSC6ElfhMgorp',1609394016054,1609480416054),
	(65,1,'cMGsOo18orq3a00MMRpglO7kT7jIDd4QOwr6ZxxY5qHti3rmHhNldm8TsiBCPaZk',1609720008606,1609806408606),
	(66,1,'U9XjNpwjgQfxe9fcXscAy4U6psmKTzKoePddD1N4pLnCH1GIAiXMZnxk61LOhsHs',1609720033451,1609806433451),
	(67,1,'cTw1rnNSSgl9EAvEGyoXdbOvEFvaOujBY6oPsIWUqqpTaFoKfpj5OmByiFy4wquc',1609720077684,1609806477684),
	(68,1,'QxMMEaBXW7cUSMjOJGoVsxiEoHXawkY8U2J7OH1qdytSE7lLIlv2DAqSZUb7HJBb',1609720231503,1609806631503),
	(69,1,'cZpe6El7MAiJ2MC2TTSJP1GfyPNjyObGOV4tTOowUGl7Pms0HpJoBACcrjEPdd1n',1609720277720,1609806677720),
	(70,1,'e3J3FXfZPTbUn1YnyoZHCFUs1EMj1kTfv1EvodH8HAjaeQJQLZoFSD0UvDFoJPIR',1609720432273,1609806832273),
	(71,1,'mTaJGQuG16pkKVUleujcHRTR3ilqDz1suF62GYSBE2g0SRMUXnpJHag0BrFwwwjH',1609720451717,1609806851717),
	(72,1,'Ll1TN5aAqBAqOKdJ2loBw7LI4ar5c2jC4IYfqB2k6Tm1WEtCloewO1B3AAnyArDt',1609720672168,1609807072168),
	(73,1,'iUdWi4pxyJu36w9TrtiFTe35BG7gX4sy5eSTCaBCJeIug1UPjGilzxZ6DUvNTw7O',1609720748447,1609807148447),
	(74,1,'t63khbDwwpQHn7jyIGSN9AZnE0iknSQycCWHGZ0oOHwO2gm65HZ3FofyV7P2qFVc',1609720889407,1609807289407),
	(75,1,'WRMc1inHoEvdAZTxllaYjOLvBUP6r0AbXD9y2DCAfclpomSZNgKiCmn3QtgRXShf',1609720957574,1609807357574),
	(76,1,'U2N9JIQ8jmUMbiSqSYOxILLTsVTMKPeUkUmtnnJqc6IrMURAsCP7zHPpzmuQZFyp',1609721022306,1609807422306),
	(77,1,'vRIKhu6EMMEAisGiXAiSLgPt8X01e3CdB027INmUOmA6bHU28g4xeFxmnHLuAQi0',1609721022306,1609807422306),
	(78,1,'PEoQ3yE6VzzfsproMjD1pFHMuZvjOvnAW7pBfBJnRX9n2NHQkdYGEytS0j5rTq0A',1609721054334,1609807454334),
	(79,1,'gvMS0pdEAP5qSbcupZXEsBfg1yAur1Pxc5uzUQyrcG69jqa0zhbVJ9sahp8uArF5',1609764953865,1609851353865),
	(80,1,'M5x8sVo3BvIjNidw6U89NX6wfL0ZduPwV5qYSnsWJxrGMBVOVVG9f1T8fm0fSEuT',1609997246817,1610083646817),
	(81,1,'VxYnPnMvmM0bELCHFfp3dVAvOMqROPIFiwOgD5h4o0y7ia0QXmQvTpNNwF8pyS57',1610177453845,1610263853845),
	(82,1,'GrZAqZi4MStlfDHSC1UfFXz1BaG7ShMmaAmUwVpWTnxORdiTGbHxWFCRGXcSGU5K',1610179293626,1610265693626),
	(83,1,'WONaw2Igq1fB5A5X6fgUqRvswij6pNehSC5iXczu4MH8plk4b4nunyDwQqQw1rkD',1610348953881,1610435353881),
	(84,12,'Fn2vPxHuML1ozN8fw8yJguPj79Zj9TE2404G182W96zghDP1NHc7vl7ZmYmE6UM3',1610429581504,1610515981504),
	(85,1,'r77FUD4hPfqCDtXjn9n4rAjOHIf16AQ6B1R0GaizNlSCDPeL3luCrWO9JCiloqLy',1610429644960,1610516044960),
	(86,12,'9wWPhSDqpldvnIOFvLXqnA1cb62dlS8JxrE6DlCEx0jJYl2LqZcggLoJiwSYcprS',1610450244630,1610536644630),
	(87,1,'Tzc8DL4pr9WnkUvPcpDXxlgpGe1KSMunGcHgOfSHq7q5KspwGz5wkz8Vmux8Y2sb',1610583302630,1610669702630),
	(88,1,'Ne73sqSwN9jjEv2ndPD0VGSZB81Vebys6lQPxXEEsWWu0GzFRUEB3HUg634uMNOl',1610593252732,1610679652732),
	(89,1,'vLnKNqyivVr9zbWcDNXFKgo5RNa5yGPYoOpGoGf2mb6pS1sObQOWE1gCgPW50zMz',1610593345636,1610679745636),
	(90,3,'uB7oyzymwU35iLTxVh64ArsQCRZznY72ckm2MkVFC0Vk1ibFLeDBPcKmTzSZjZpK',1610681181432,1610767581432),
	(91,1,'Fvz632TyMM42utf4cLyl0Qbpcr6mC8OIjp9VR871wuWU6ONvOxtWYPi5ulblMqKB',1610769693004,1610856093004),
	(92,1,'hWiqDKgXNjMpUBpHCFyppTI5UZzKZXYks4v5nv16Qby07ElPsqfdGA1BJ6V7mC5B',1610930640781,1611017040781),
	(93,3,'t9hNZCoxFUAQYGXYAtfr8fRv7SyahUg3P0fmV4ZNCGkfBgvxsaS9qSk89NfcziQa',1610930655758,1611017055758),
	(94,1,'hKHHThmeAuolJGkjDx8liIt5JwFGakzL0uw2dsJDysVcndxoDbKXNstcygbg7F4T',1610930898058,1611017298058),
	(95,1,'Eqauwc3tgcTNO650yNpiX8oWFYMzQaEFJ4eTiExa2ZJm18OenifgieEAyW8jKJuD',1611112144607,1611198544607),
	(96,3,'XSX5h7eiaPvrrNL0st6l1pRlKdptcjXi91uvZqVXYeN1meZVGwWy9R6sPBPm5WfU',1611120105732,1611206505732),
	(97,1,'WPcFykItqKFZaEOnHroOlt1ackDjkoImKuOU4LqoNFqqVWRoJsPIU1QoctKgcoxz',1611315101017,1611401501017),
	(98,3,'26lOISt6THnnuLCIcKYtGLHZXVs8acncgs3bf0iLkTGiy6DbHuqnfJUgRu3mJ0Tq',1611315114042,1611401514042),
	(99,3,'qbH8i0YsMNqiuzjiylsS2qDtuAo4elrl5SsvNurl4T6ANpSDPZHwZ7vYMkJRkUX1',1611625192499,1611711592499),
	(100,1,'V54drvrkE4q0qy2pnYA6pmluEtBnFV8HUpVff1TdjIpm3JmG0jpa7LJNkpRNDG3P',1611625377401,1611711777401),
	(101,1,'qmTrRpqEp6FcQ0bMq8TBtXeKya4BSJBS4kmBZqBH9qAslfr0S3y88sXHFHkJ37iC',1611892150295,1611978550295),
	(102,1,'BotNBF4XUjwFXpzc6VXhlzRAKhwqnJDym6xSfmZ62O2w2cWSeKduCEfRiUEeuCvu',1611892150306,1611978550306),
	(103,1,'YUjS1e5G1jHGdbw8U380j2Q2qRNGqkBrsHunzZMv7LgaYi4VsgY3G2XWBsFwrKbH',1611892150306,1611978550306),
	(104,1,'sblKJ1TcwzcgZWJp1El0jbqUD2NUP9Is08BFhKUXF3ng0icfIrw8xDjOgLPgASiW',1611892150306,1611978550306),
	(105,1,'j2Wl6Utg3WttFahT6ymqYTxfSK04IoZuN4OXzyB68jwLPzB9i6m9r2Oxsf1PIYJ5',1611892150306,1611978550306),
	(106,1,'HcMPytBNDhumWxA67yj9zRaGJE9bXlixIh3SXmlvbSE5JpRJNhIUtsWRcfql5mcA',1611892150307,1611978550307),
	(107,1,'ITlGYSHdXiuDpdWRZa5wAq2MwQksHxCegWF3x2B702yBcF7wI7MpvrnONpiFXXvY',1611892150334,1611978550334),
	(108,1,'BBCpu7CJCDGNkwZoHd2uOnFrHJCTg3u0hKxFXR70gdiRkk7LZEyfEdBWqBgP0kUv',1611892150334,1611978550334),
	(109,3,'mhdyGJapFqdnwgWJY8YFOjz1vIME9WDUBbKtWZzXHlcxSd8CV43R7iuQGVFcumHs',1611892518731,1611978918731),
	(110,3,'kGBBGHPwW77LJZKXvaAXyyDnnJFZ08Wd4ublvjE7O5cAREKMIQX6OyoQvqKFtBX1',1612149200759,1612235600759),
	(111,1,'W5EvGvlgG9KuB3n0p0oRbVoblSpGihV9TYltWiGHgQ1LrqdOKLKFKlHNF1y373oK',1613630670355,1613717070355),
	(112,1,'qJk4wMDzXovu29IUaooUfZAQZKB8nXo1c3lMAz6bEqc07jdQQ0keMrP49UK2AAh8',1613957082837,1614043482837),
	(113,3,'nu7xJAeg9x85moZao5dn8AXCDIQ2B5YzE8whPex5i1UN1mJWWMVzxW0mmI0DG7vq',1613957487126,1614043887126),
	(114,1,'BXzAbOiHZr9YnImkrwmacqjQBcqxtHY3BOXUzJHPlEygakBxeuP2Av04njVDaLzM',1613992799995,1614079199995),
	(115,1,'tYT2I6qE9xGFBnX7PoL1NPgV1KjQUyYsA7l0XA8WOJNqm3AkxsX6sDxRE6i0KZBl',1613992812472,1614079212472),
	(116,1,'ZgiSojOQgFWt1T6Y2iaXPa8n3u0o5V0z4LbDeTUvs7ab4Hbw6yTIDpKKCrqAh9GT',1613992840474,1614079240474),
	(117,1,'KErTEUESk6V5DRmp2IcQVa2Tf8HPIp7NvkpUU7ENjWhPHAIvEFreGPz2j1MFD0eF',1613992913578,1614079313578),
	(118,1,'80aNmvxFlYgk86ecKpXWWshfI1gxFNn4AW0FQtdc9h8bCmq54M5Bv5nTczBqZ4hV',1613992924949,1614079324949),
	(119,1,'1YLN6i2tq4cutKtCbVSsJVoLvgKpEfUW8ZSFxlUvsmGtNzQKqDXHJXtKihaJxac2',1613992970810,1614079370810),
	(120,1,'dR4Q6XfePAtadUQPr94WnaKjVfJauaHf9KpfPRxudWwxicHkFvFAPmKxpi1ioEvr',1613993021396,1614079421396),
	(121,1,'3tsnBn5kY0hsBR1joTYFDr1mRSVELXFva8JTR5VDOOHSoRBteqqTahsj8o466CKG',1613993091140,1614079491140),
	(122,1,'bTBzVVTLVqGc5X3w8md82uswhgZDsrlMR2dsMYin2kPpBOx8ZuwqytxeF5F9F6YV',1613993130018,1614079530018),
	(123,1,'ra18MI1ZyGYRGeHxUbLr8vyyf1i2PgqsHLLRKfskpV9Y9WwtiWCCQJYAJdccGoTx',1613993196677,1614079596677),
	(124,1,'gJyFdTl3V99aNfVmSF2eEWsgqO6ksNpFfQmQw8SoEYKzcBPm0nz9yh7ynhKMfu6B',1613993273809,1614079673809),
	(125,1,'EzrougXHOoAVbVR8SzEqsygRCsvIViPF47pT64BGG15ZJ2PB7HDrpxj74FcyIYah',1613993404634,1614079804634),
	(126,1,'Qx89ffXOtkfLBV5IZByxsswIN6EClE1Bvv3goc3QIsC9ENtvVYAuDqiSjPLRGxJH',1613993480765,1614079880765),
	(127,1,'TM7pNTMJMjgZb3R9ccsu8U8wveDK9Qt95NOZsWnmrRZpUToMPU9ujBSPHw6CGKim',1613993512137,1614079912137),
	(128,1,'0yjVeYPVLLxKIDdwGS6gTQpEKOg4gg5IwK0HZTzjpGeaQB23BFHlPnxyfauTJpZh',1613993541857,1614079941857),
	(129,1,'F10huDojyvf235vgmYtTJfV7cKV7rAyWOr67ZAv7AJuUelGnpv2rdC3tMv7AxFzX',1613993651001,1614080051001),
	(130,1,'49K6E46DS0EJ02Doo7IznL94DjvDhV5bMFYOHIBWQeBiOyKkuEBu9KVRhrbMy39K',1613993691366,1614080091366),
	(131,1,'c6zwIv1kfqa7vOiLYxny2rRMMoTwKL4bLM4mcja6hNZEqFWNGHU7fgPte0PHp2Ed',1613993787181,1614080187181),
	(132,1,'LglaLrWwp3864ZmIQeCXoIz4Vk1UH605qgB54Q30yRe35I1ov4Tl5LHRY4ROEZp9',1613994072436,1614080472436),
	(133,1,'SHHGOJvfJ3zE6FBTTNp75AlNTddQuQVshvWYX69JUL4ROT3PDZj4LYq7pyXsgkgp',1613994121483,1614080521483),
	(134,1,'fgXh0Ucph1AmS1cGxdttEbg6grFNVPGbZJ7npK9CzWH9zPOlNsBtqWob3wF2vy6b',1613994153371,1614080553371),
	(135,1,'zF352GxyELoXSBdPrafdkY8pAjsJzWgNwgb4eTD0J5jDI9cBgijNmATrYj3FPgQb',1613994158912,1614080558912),
	(136,1,'mqt0ilB7Xu19qKPR19t0hq0E2mLXFzAhoRuUYkP0S9eAl4SSM1tuJLQpX3i3kLsr',1613994175712,1614080575712),
	(137,1,'r4daPgPuEd2MIB6WI85DwXyU75UzPm9L5xrveXkhg9HIIvokpN4GOzQW3SgNkMIa',1613994233869,1614080633869),
	(138,1,'LKGQX48bsQcMQs4nWV1wLdYt6Mam2ioomjzRgTOaRRsEZUEQVwalCzM7U4y1Qian',1613994364898,1614080764898),
	(139,1,'w9aoMgpEyFeeBqddBesFaf16TerSmpspqrkTgSMljhBs2aCZZvDwTRqt3IPaWpVH',1613994679444,1614081079444),
	(140,3,'8UuZIk2tEVdAqEDEOtJ6QqEMslbEMdXv0XLCQnOWUJBNtgExWmYQKt1GrvSkrSDk',1613998561143,1614084961143),
	(141,3,'ylji2wfSBMR7hmu39FQHguzv8WBq2gxpUgwqAgZbbxrgwTZdr8MxO1agwcDP70iY',1614044021496,1614130421496),
	(142,3,'MxzAjwyk0G6HegS9fzzEa7IwZxNTk7ZcJUsWSDrmpj9YJua9V9gNIYzKHzAsuLT4',1614149201260,1614235601260),
	(143,3,'BT8QMDfpJWPNVRUrCdvEW38QwPA09VLxXwInW3kR3BNO22uJTUscf8mokhebkqre',1614243941403,1614330341403),
	(144,3,'KqxyRWrmJCFnWhDR7hJXnWnL0lfouw3xSmFZ4aZAMzmlmiXooDKoCkOCBb4sVklm',1614489588987,1614575988987),
	(145,3,'5BbVvhHDQriSK4gArN6fDa8aFqd4fQ1GVgQJZ6cshaygNxr2rNJZkU5ZVhbBswHV',1614580530491,1614666930491),
	(146,1,'Wv0wRVDBlmZ9vANUmzwTb6qA3Z7M17zOsdQ8lWvWKjyTdpFTJ2aWWCvh8UhZeyZi',1614580537153,1614666937153),
	(147,1,'9LnVqcQD7q0BA4PNeT2i46pT2vPskpRF8bmLAUJKzVErtzEsIc3UZxXlJ07KxJpK',1614580692725,1614667092725),
	(148,1,'GDd7Df0XnG3yslmW1m249EeEp85nLBgqS4GOOWhvA4cBx8fql933aO4MN6thAxhY',1614585846913,1614672246913);

/*!40000 ALTER TABLE `merchant_admin_session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table merchant_duration
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant_duration`;

CREATE TABLE `merchant_duration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` bigint(20) NOT NULL,
  `created_at` bigint(20) NOT NULL,
  `expired_at` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `merchant_duration` WRITE;
/*!40000 ALTER TABLE `merchant_duration` DISABLE KEYS */;

INSERT INTO `merchant_duration` (`id`, `merchant_id`, `created_at`, `expired_at`)
VALUES
	(1,10,1608204232360,15552000000),
	(2,13,1608204598801,1615980598801),
	(3,14,1608208106113,1639312106113),
	(4,5,1608208106113,1639312106113),
	(5,6,1608208106113,1639312106000),
	(6,7,1608208106113,1639312101211),
	(7,10,1608204232360,1715980598801),
	(8,16,1610181891322,1641285891322),
	(9,17,1610181998094,1641285998094),
	(10,1,1610592501311,1641696501311),
	(11,2,1610592780119,1641696780119),
	(12,3,1610680998445,1641784998445);

/*!40000 ALTER TABLE `merchant_duration` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `sequence` varchar(10) NOT NULL DEFAULT '',
  `merchant_id` int(10) NOT NULL,
  `brand_id` int(10) DEFAULT NULL,
  `created_at` bigint(20) NOT NULL,
  `specs` text NOT NULL,
  `params` varchar(1024) DEFAULT NULL,
  `priority` int(10) NOT NULL,
  `status` tinyint(1) DEFAULT '2',
  `content` varchar(1024) NOT NULL,
  `pub_at` bigint(20) DEFAULT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `name`, `sequence`, `merchant_id`, `brand_id`, `created_at`, `specs`, `params`, `priority`, `status`, `content`, `pub_at`, `price`)
VALUES
	(1,'大自然床垫','010101',5,4,1610591970255,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/7/5fffae969712b980c9b1f1ba3qsjay89.png\"],\"params\":[{\"label\":\"大\",\"value\":\"22\"}],\"price\":19800,\"sno\":\"2\"}]','[{\"label\":\"白色\",\"value\":\"#fff\"}]',2,2,'<p>这是一本红楼梦</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,17800),
	(2,'i9-10900k','030301',1,6,1610594282743,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/o/5fffb7929712b981895e9512lwrpdox2.png\"],\"params\":[{\"label\":\"cpu\",\"value\":\"nb\"}],\"price\":379900,\"sno\":\"100002\",\"stock\":12}]','[{\"label\":\"i9\",\"value\":\"十代\"}]',2,1,'<p>十代i9很牛逼</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,379900),
	(3,'英伟达RTX-3070','030401',1,7,1610609545406,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}]','[{\"label\":\"3070\",\"value\":\"\"}]',2,1,'<p>显卡显卡</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,1390000),
	(4,'led吸顶灯','040201',3,4,1610685611959,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}]','[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}]',1,1,'<p>灯灯灯</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,6900),
	(5,'兔子智能小闹钟','030205',3,NULL,1611125761762,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}]','[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}]',2,1,'<p>好看好看好看好好看好看</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,8500),
	(6,'兔子小闹钟','030205',3,NULL,1611126088317,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/l/6007d51a5845f942c4861204bidmew4d.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"2\",\"stock\":999},{\"imgs\":[],\"params\":[{\"label\":\"\"}]}]','[{\"label\":\"品牌\",\"value\":\"库火\"}]',2,2,'<p>好看好看哈哈</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',NULL,8900);

/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_brand`;

CREATE TABLE `product_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `logo` varchar(1024) NOT NULL,
  `priority` int(5) DEFAULT '1',
  `status` tinyint(1) NOT NULL DEFAULT '2',
  `created_at` bigint(20) NOT NULL,
  `sequences` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `product_brand` WRITE;
/*!40000 ALTER TABLE `product_brand` DISABLE KEYS */;

INSERT INTO `product_brand` (`id`, `name`, `logo`, `priority`, `status`, `created_at`, `sequences`)
VALUES
	(1,'顾家家居','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/m/5fff8f139712b97bcac7c1503bhczezl.png',1,1,1610583843346,'[\"010000\"]'),
	(2,'阿玛尼','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/k/5fff8f2d9712b97bcac7c1512q8vaqne.png',2,1,1610583865194,'[\"040000\"]'),
	(3,'月星家居','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/g/5fff8f429712b97bcac7c152b9r9zo4s.png',2,1,1610583890575,'[\"010000\",\"050000\"]'),
	(4,'宜家家居','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/q/5fff8f749712b97bcac7c154hi1qrgw4.png',4,1,1610583941108,'[\"010000\",\"020000\",\"040000\"]'),
	(5,'曲美简居','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/1/5fff8f8f9712b97bcac7c15590vqk0yz.png',6,1,1610583969221,'[\"020000\"]'),
	(6,'海尔家电','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/a/5fff902a9712b97bcac7c157m0srs8jb.png',2,1,1610584130410,'[\"030000\"]'),
	(7,'Intel','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/6/5fffb7249712b981895e9511jvx924nq.png',2,1,1610594112319,'[\"030300\"]');

/*!40000 ALTER TABLE `product_brand` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `priority` int(5) DEFAULT '1',
  `sequence` varchar(6) DEFAULT '000000',
  `status` tinyint(1) NOT NULL DEFAULT '2',
  `icon` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sequence` (`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;

INSERT INTO `product_category` (`id`, `name`, `parent_id`, `priority`, `sequence`, `status`, `icon`)
VALUES
	(1,'家居',0,1,'010000',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/q/5ffeea849712b979a5f14f572u8p2tn7.png'),
	(2,'空间',0,1,'020000',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/8/5ffeea9c9712b979a5f14f58lbly6aw9.png'),
	(3,'家电',0,1,'030000',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/u/5ffeeab29712b979a5f14f59zuiz8ic8.png'),
	(4,'家饰',0,1,'040000',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/y/5ffeeacb9712b979a5f14f5aniea6r5e.png'),
	(5,'家具',0,1,'050000',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/g/5ffeebc99712b979a5f14f5bb5km6g9f.png'),
	(6,'大家电',3,1,'030100',1,''),
	(7,'小家电',3,1,'030200',1,''),
	(8,'冰箱',6,1,'030101',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/l/5ffeec049712b979a5f14f5cqnovcnns.png'),
	(9,'电视',6,1,'030102',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/b/5ffeec219712b979a5f14f5dxearsvts.png'),
	(10,'空调',6,1,'030103',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/4/5ffeec2f9712b979a5f14f5ey11no6o5.png'),
	(11,'洗衣机',6,1,'030104',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/8/5ffeec4c9712b979a5f14f5f0b4mj6s8.png'),
	(12,'咖啡机',7,1,'030201',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/o/5ffeec5b9712b979a5f14f60y2khcgok.png'),
	(13,'烤箱',7,1,'030202',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/l/5ffeec6f9712b979a5f14f61vgb7xj51.png'),
	(14,'面包机',7,1,'030203',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/v/5ffeec889712b979a5f14f62q93rilek.png'),
	(15,'饮水机',7,1,'030204',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/v/5ffeec9f9712b979a5f14f63gl9atca1.png'),
	(16,'家纺',1,1,'010100',1,''),
	(17,'家居',1,1,'010200',1,''),
	(18,'床上用品',16,1,'010101',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/o/5ffeecee9712b979a5f14f64dfjd0yve.png'),
	(19,'加湿器',17,1,'010201',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/13/r/5ffeed109712b979a5f14f65clfhcyb4.png'),
	(20,'床垫',5,1,'050100',1,''),
	(21,'CPU',3,1,'030300',2,''),
	(22,'Intel',21,1,'030301',2,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/h/5fffb5fb9712b981895e950dizlobs0d.png'),
	(23,'AMD',21,1,'030302',2,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/k/5fffb6539712b981895e950f6kxrsxx0.png'),
	(24,'NVIDIA显卡',3,1,'030400',2,''),
	(25,'AMD显卡',3,1,'030500',1,''),
	(26,'GTX-1660ti',24,1,'030401',2,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/y/5fffb6e89712b981895e95100535rfvf.png'),
	(27,'大家饰',4,1,'040100',1,''),
	(28,'照明',4,1,'040200',1,''),
	(29,'餐吊灯',28,1,'040201',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60010c70092ef9115eae139e1y0wch86.png'),
	(30,'吸顶灯',28,1,'040202',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010cc1092ef9115eae139feda8t1fq.png'),
	(31,'闹钟',7,1,'030205',1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/g/6007d25d5845f942c48611ffoonafbqs.png');

/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product_template
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_template`;

CREATE TABLE `product_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(128) DEFAULT NULL COMMENT '名称',
  `sequence` varchar(1024) DEFAULT NULL COMMENT '分类ID',
  `params` text COMMENT '参数：内容属性部分',
  `content` longtext COMMENT '内容图文部分',
  `created_at` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：1.正常2.停用',
  `specs` varchar(10000) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `product_template` WRITE;
/*!40000 ALTER TABLE `product_template` DISABLE KEYS */;

INSERT INTO `product_template` (`id`, `title`, `sequence`, `params`, `content`, `created_at`, `status`, `specs`)
VALUES
	(1,'i910900k',NULL,'[{\"label\":\"十代\",\"value\":\"cpu\"}]','<p>9.8cpu</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>',1610596175047,1,'[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/x/5fffbf259712b9828e733c1c3x90bz6p.png\"],\"params\":[{\"label\":\"i9\",\"value\":\"cpu\"}],\"price\":379900,\"sno\":\"1\"}]');

/*!40000 ALTER TABLE `product_template` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table scene
# ------------------------------------------------------------

DROP TABLE IF EXISTS `scene`;

CREATE TABLE `scene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(16) NOT NULL DEFAULT '',
  `status` tinyint(4) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `products` text NOT NULL,
  `content` text,
  `img` varchar(1024) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `sub_heading` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `scene` WRITE;
/*!40000 ALTER TABLE `scene` DISABLE KEYS */;

INSERT INTO `scene` (`id`, `title`, `status`, `created_at`, `products`, `content`, `img`, `author_id`, `type`, `sub_heading`)
VALUES
	(1,'新色彩，海洋硬糖系列打造专属',2,1610584822078,'[{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}],\"status\":1}]','<p>你强任你强，大哥背行囊</p>','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/9/5fff929a9712b97bcac7c15d2x48aycb.png',2,4,'现代感：黑、白、灰是永恒经典的色调。'),
	(2,'活力感：蓝色系与橙色系搭配',2,1610584916421,'[{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}],\"status\":1}]','<p>活力感：蓝色系与橙色系搭配，将这两种对比色组合在一起，碰撞出兼具当代与复古风味的视觉感受，给予空间一种新的生命。</p>','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/c/5fff93279712b97bcac7c15e4lfxhffe.png',2,3,'碰撞出兼具当代与复古风味的视觉感受'),
	(3,'新色彩，海洋硬糖系列打造',2,1610585027527,'[{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1}]','<p>《红楼梦》，中国古代<a data-lemmaid=\"11018000\" href=\"https://baike.baidu.com/item/%E7%AB%A0%E5%9B%9E%E4%BD%93/11018000\" target=\"_blank\">章回体</a><a data-lemmaid=\"7708668\" href=\"https://baike.baidu.com/item/%E9%95%BF%E7%AF%87%E5%B0%8F%E8%AF%B4/7708668\" target=\"_blank\">长篇小说</a>，中国古典<a data-lemmaid=\"8376\" href=\"https://baike.baidu.com/item/%E5%9B%9B%E5%A4%A7%E5%90%8D%E8%91%97/8376\" target=\"_blank\">四大名著</a>之一，一般认为是清代作家<a data-lemmaid=\"14919\" href=\"https://baike.baidu.com/item/%E6%9B%B9%E9%9B%AA%E8%8A%B9/14919\" target=\"_blank\">曹雪芹</a>所著。小说以贾、史、王、薛<a data-lemmaid=\"2314013\" href=\"https://baike.baidu.com/item/%E5%9B%9B%E5%A4%A7%E5%AE%B6%E6%97%8F/2314013\" target=\"_blank\">四大家族</a>的兴衰为背景，以富贵公子<a data-lemmaid=\"59563\" href=\"https://baike.baidu.com/item/%E8%B4%BE%E5%AE%9D%E7%8E%89/59563\" target=\"_blank\">贾宝玉</a>为视角，以贾宝玉与<a data-lemmaid=\"260081\" href=\"https://baike.baidu.com/item/%E6%9E%97%E9%BB%9B%E7%8E%89/260081\" target=\"_blank\">林黛玉</a>、<a data-lemmaid=\"396702\" href=\"https://baike.baidu.com/item/%E8%96%9B%E5%AE%9D%E9%92%97/396702\" target=\"_blank\">薛宝钗</a>的爱情婚姻悲剧为主线，描绘了一批举止见识出于须眉之上的闺阁佳人的人生百态，展现了真正的人性美和悲剧美，可以说是一部从各个角度展现女性美以及中国古代社会世态百相的史诗性著作。</p>','https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/5/5fff938c9712b97bcac7c15fvri12mwy.png',2,1,'新色彩，海洋硬糖系列打造');

/*!40000 ALTER TABLE `scene` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table scene_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `scene_type`;

CREATE TABLE `scene_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(1024) NOT NULL DEFAULT '',
  `name` varchar(16) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '2',
  `sub_heading` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `scene_type` WRITE;
/*!40000 ALTER TABLE `scene_type` DISABLE KEYS */;

INSERT INTO `scene_type` (`id`, `icon`, `name`, `status`, `sub_heading`)
VALUES
	(1,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/a/5fff91d09712b97bcac7c159syr24ib2.png','厨房',2,'好玩有趣，应有尽有'),
	(2,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/c/5fff92099712b97bcac7c15aubbljkkh.png','阳台',1,'好玩有趣，因有尽有'),
	(3,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/3/5fff92209712b97bcac7c15baxmzgd0e.png','卧室',2,'好玩有趣'),
	(4,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/u/5fff92309712b97bcac7c15cp8glj57t.png','卫生间',1,'好家伙，我直呼内行');

/*!40000 ALTER TABLE `scene_type` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table trade
# ------------------------------------------------------------

DROP TABLE IF EXISTS `trade`;

CREATE TABLE `trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL,
  `user_id` int(10) NOT NULL,
  `merchant_id` int(10) NOT NULL,
  `order_number` varchar(50) NOT NULL,
  `total_amount` int(20) NOT NULL,
  `total_price` int(10) NOT NULL,
  `coupon_amount` int(10) DEFAULT NULL,
  `mark` varchar(1024) DEFAULT NULL,
  `user_coupon_id` int(10) NOT NULL,
  `created_at` bigint(20) NOT NULL,
  `trade_items` text NOT NULL,
  `address` varchar(1024) NOT NULL,
  `payment` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_number` (`order_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;

INSERT INTO `trade` (`id`, `type`, `user_id`, `merchant_id`, `order_number`, `total_amount`, `total_price`, `coupon_amount`, `mark`, `user_coupon_id`, `created_at`, `trade_items`, `address`, `payment`)
VALUES
	(1,5,10015,1,'20210201105839470',1390000,1390000,0,NULL,0,1612148319034,'[{\"id\":58,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":543}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(2,5,10015,3,'20210201110253297',8900,8900,0,NULL,0,1612148573588,'[{\"id\":57,\"num\":1,\"product\":{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":765},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}],\"status\":1},\"productSno\":\"000001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(3,6,10015,3,'20210131121900018',7900,7900,0,NULL,0,1612066740354,'[{\"id\":56,\"num\":1,\"product\":{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":997}],\"status\":1},\"productSno\":\"000002\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(4,3,10015,3,'20210201123457135',13800,13800,0,NULL,0,1612154097786,'[{\"id\":60,\"num\":1,\"product\":{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":997}],\"status\":1},\"productSno\":\"000002\"},{\"id\":61,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":90},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000003\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(5,5,10015,1,'20210222104342971',1139700,1139700,0,NULL,0,1613961822508,'[{\"id\":62,\"num\":3,\"product\":{\"brandId\":6,\"content\":\"<p>十代i9很牛逼</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610594282743,\"id\":2,\"merchantId\":1,\"name\":\"i9-10900k\",\"params\":[{\"label\":\"i9\",\"value\":\"十代\"}],\"price\":379900,\"priority\":2,\"sequence\":\"030301\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/o/5fffb7929712b981895e9512lwrpdox2.png\"],\"params\":[{\"label\":\"cpu\",\"value\":\"nb\"}],\"price\":379900,\"sno\":\"100002\",\"stock\":9}],\"status\":1},\"productSno\":\"100002\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(6,5,10015,3,'20210222105124831',6900,6900,0,NULL,0,1613962284476,'[{\"id\":63,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":122},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000004\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(7,5,10015,1,'20210223165228005',1390000,1390000,0,NULL,0,1614070348117,'[{\"id\":64,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":543}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(8,5,10015,3,'20210223173543321',41400,41400,0,NULL,0,1614072943523,'[{\"id\":66,\"num\":6,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":117},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000004\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(9,5,10015,3,'20210223173556488',6900,6900,0,NULL,0,1614072956855,'[{\"id\":68,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":122},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000004\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(10,2,10015,3,'20210223185113920',5900,5900,0,NULL,0,1614077473099,'[{\"id\":67,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":90},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000003\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(11,5,10015,1,'20210224172658393',1390000,1390000,0,NULL,0,1614158818302,'[{\"id\":65,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(12,5,10015,1,'20210224192920828',0,0,0,NULL,0,1614166160152,'[{\"id\":78,\"num\":2,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"1\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(13,5,10015,3,'20210225160050986',14800,14800,0,NULL,0,1614240050178,'[{\"id\":91,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000003\"},{\"id\":92,\"num\":1,\"product\":{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}],\"status\":1},\"productSno\":\"000001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(14,5,10015,3,'20210225190621586',23700,23700,0,NULL,0,1614251181579,'[{\"id\":93,\"num\":2,\"product\":{\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1611125761762,\"id\":5,\"merchantId\":3,\"name\":\"兔子智能小闹钟\",\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"price\":8500,\"priority\":2,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"price\":8900,\"sno\":\"000001\",\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"price\":7900,\"sno\":\"000002\",\"stock\":998}],\"status\":1},\"productSno\":\"000001\"},{\"id\":94,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000003\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(15,5,10015,1,'20210225190851471',1390000,1390000,0,NULL,0,1614251331815,'[{\"id\":107,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(16,2,10015,1,'20210225193350741',379900,379900,0,NULL,0,1614252830676,'[{\"id\":108,\"num\":1,\"product\":{\"brandId\":6,\"content\":\"<p>十代i9很牛逼</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610594282743,\"id\":2,\"merchantId\":1,\"name\":\"i9-10900k\",\"params\":[{\"label\":\"i9\",\"value\":\"十代\"}],\"price\":379900,\"priority\":2,\"sequence\":\"030301\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/o/5fffb7929712b981895e9512lwrpdox2.png\"],\"params\":[{\"label\":\"cpu\",\"value\":\"nb\"}],\"price\":379900,\"sno\":\"100002\",\"stock\":11}],\"status\":1},\"productSno\":\"100002\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(17,5,10015,1,'20210225203050457',1390000,1390000,0,NULL,0,1614256250872,'[{\"id\":109,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(18,5,10015,1,'20210226102530967',1390000,1390000,0,NULL,0,1614306330279,'[{\"id\":110,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(19,2,10015,3,'20210226152912254',5900,5900,0,NULL,0,1614324552341,'[{\"id\":111,\"num\":1,\"product\":{\"brandId\":4,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610685611959,\"id\":4,\"merchantId\":3,\"name\":\"led吸顶灯\",\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"price\":6900,\"priority\":1,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":5900,\"sno\":\"000003\",\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":6900,\"sno\":\"000004\",\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":7900,\"sno\":\"000005\",\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"price\":8900,\"sno\":\"000006\",\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"price\":5500,\"sno\":\"000007\",\"stock\":81}],\"status\":1},\"productSno\":\"000003\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(20,2,10015,1,'20210226153203688',1390000,1390000,0,NULL,0,1614324723040,'[{\"id\":112,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":543}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1),
	(21,2,10015,1,'20210301095847600',1390000,1390000,0,NULL,0,1614563927370,'[{\"id\":113,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":543}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',2),
	(22,5,10015,1,'20210301100909835',1390000,1390000,0,NULL,0,1614564549209,'[{\"id\":114,\"num\":1,\"product\":{\"brandId\":7,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"createdAt\":1610609545406,\"id\":3,\"merchantId\":1,\"name\":\"英伟达RTX-3070\",\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"price\":1390000,\"priority\":2,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}],\"price\":1390000,\"sno\":\"100001\",\"stock\":544}],\"status\":1},\"productSno\":\"100001\"}]','{\"code\":\"410581\",\"createdAt\":1611295279677,\"detail\":\"茶店乡\",\"id\":18,\"isDefault\":1,\"mobile\":\"18303800683\",\"name\":\"张卫正\",\"updateAt\":1611295279677,\"userId\":10015}',1);

/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ui
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ui`;

CREATE TABLE `ui` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `components` text,
  `created_at` bigint(20) NOT NULL,
  `is_default` tinyint(1) DEFAULT NULL,
  `title` varchar(20) NOT NULL DEFAULT '',
  `type` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `ui` WRITE;
/*!40000 ALTER TABLE `ui` DISABLE KEYS */;

INSERT INTO `ui` (`id`, `components`, `created_at`, `is_default`, `title`, `type`)
VALUES
	(67,'[{\"key\":\"CATEGORY\",\"list\":[{\"sequence\":\"010000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2020/12/22/p/5fe15568ced17918252d9307pnxi4dbz.png\",\"name\":\"一级分类\",\"id\":1,\"priority\":4,\"parentId\":0,\"status\":1},{\"sequence\":\"020000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg\",\"name\":\"11\",\"id\":25,\"priority\":12,\"parentId\":0,\"status\":1}],\"listStyle\":1,\"title\":\"一级分类\"}]',1609727948179,2,'你好',1),
	(68,'[{\"key\":\"AD\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/4/l/5ff304051aa3b9c845caaa06lls3rv2d.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"AD\"}],\"listStyle\":1,\"title\":\"广告位\"}]',1609761827295,2,'我不好',1),
	(69,'[{\"key\":\"ARTICLE\",\"list\":[{\"createdAt\":1609759914674,\"intro\":\"大连：有病例做11次核酸检测才呈阳性\",\"collectNum\":122,\"id\":10065,\"authorId\":2,\"title\":\"大连：有病例做11次核酸检测才呈阳性\",\"content\":\"\",\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/4/d/5ff2fc5e1aa3b9c79abf8af5baqsud2t.png\",\"pageView\":23333,\"status\":1,\"supportNum\":2221}],\"listStyle\":1,\"title\":\"推荐文章\"},{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/s/5ff3b6811aa3b9cba8a38b5ffy4j87b3.png\",\"act\":\"LINK\",\"cindex\":1,\"payload\":{\"url\":\"https://www.baidu.com/\"},\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/i/5ff3b6a71aa3b9cba8a38b60ndr5559m.png\",\"act\":\"MERCHANT\",\"cindex\":1,\"payload\":{\"createdAt\":1608204598801,\"expiredAt\":1615980598801,\"lastModify\":1608296848767,\"mobile\":\"86-13711112222\",\"name\":\"蜀山球王的小店\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2020/12/17/i/5fdb406ea7eb5907a8d2d8592vvqnrh8.png\",\"location\":{\"code\":\"410122\",\"lng\":113.884965674,\"poiaddress\":\"河南省郑州市中牟县张庄镇富士康\",\"detail\":\"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\",\"poiname\":\"张庄西街幼儿园\",\"lat\":34.561138349},\"id\":13,\"sequences\":[\"010000\"],\"title\":\"蜀山球王的小店\",\"status\":2},\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/6/5ff3b6c11aa3b9cba8a38b61ja0hqybe.png\",\"act\":\"PRODUCT\",\"cindex\":1,\"payload\":{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2},\"index\":-1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"},{\"key\":\"CATEGORY\",\"list\":[{\"sequence\":\"020000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg\",\"name\":\"11\",\"id\":25,\"priority\":12,\"parentId\":0,\"status\":1},{\"sequence\":\"010000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2020/12/22/p/5fe15568ced17918252d9307pnxi4dbz.png\",\"name\":\"一级分类\",\"id\":1,\"priority\":4,\"parentId\":0,\"status\":1}],\"listStyle\":1,\"title\":\"一级分类\"}]',1609807885033,2,'我测试',1),
	(70,'[{\"key\":\"ARTICLE\",\"list\":[{\"createdAt\":1609759914674,\"intro\":\"大连：有病例做11次核酸检测才呈阳性\",\"collectNum\":122,\"id\":10065,\"authorId\":2,\"title\":\"大连：有病例做11次核酸检测才呈阳性\",\"content\":\"\",\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/4/d/5ff2fc5e1aa3b9c79abf8af5baqsud2t.png\",\"pageView\":23333,\"status\":1,\"supportNum\":2221}],\"listStyle\":1,\"title\":\"推荐文章\"},{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/s/5ff3b6811aa3b9cba8a38b5ffy4j87b3.png\",\"act\":\"LINK\",\"cindex\":1,\"payload\":{\"url\":\"https://www.baidu.com/\"},\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/i/5ff3b6a71aa3b9cba8a38b60ndr5559m.png\",\"act\":\"MERCHANT\",\"cindex\":1,\"payload\":{\"createdAt\":1608204598801,\"expiredAt\":1615980598801,\"lastModify\":1608296848767,\"mobile\":\"86-13711112222\",\"name\":\"蜀山球王的小店\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2020/12/17/i/5fdb406ea7eb5907a8d2d8592vvqnrh8.png\",\"location\":{\"code\":\"410122\",\"lng\":113.884965674,\"poiaddress\":\"河南省郑州市中牟县张庄镇富士康\",\"detail\":\"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\",\"poiname\":\"张庄西街幼儿园\",\"lat\":34.561138349},\"id\":13,\"sequences\":[\"010000\"],\"title\":\"蜀山球王的小店\",\"status\":2},\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/6/5ff3b6c11aa3b9cba8a38b61ja0hqybe.png\",\"act\":\"PRODUCT\",\"cindex\":1,\"payload\":{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2},\"index\":-1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"},{\"key\":\"CATEGORY\",\"list\":[{\"sequence\":\"020000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg\",\"name\":\"11\",\"id\":25,\"priority\":12,\"parentId\":0,\"status\":1},{\"sequence\":\"010000\",\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2020/12/22/p/5fe15568ced17918252d9307pnxi4dbz.png\",\"name\":\"一级分类\",\"id\":1,\"priority\":4,\"parentId\":0,\"status\":1}],\"listStyle\":1,\"title\":\"一级分类\"}]',1609807887199,2,'我测试',1),
	(71,'[{\"key\":\"SCENE\",\"list\":[{\"createdAt\":1609836273878,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/4/5ff426e6dc56b9d4328d21cdejv1t1yt.png\",\"id\":2,\"authorId\":2,\"title\":\"测试\",\"type\":2,\"content\":\"<p>www adadad</p>\",\"products\":[{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":2}],\"listStyle\":1,\"title\":\"情景购\"}]',1609852054269,2,'测试情景购',1),
	(72,'[{\"key\":\"SCENE\",\"list\":[{\"createdAt\":1609894782271,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/6/v/5ff50b3fdc56b9db79c8b105auiyg7es.png\",\"id\":3,\"authorId\":2,\"title\":\"秋季，空间风格\",\"type\":1,\"content\":\"<p>小编，水上一篇。</p>\",\"products\":[{\"createdAt\":1609330539166,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"先来半杯小鸟伏特加\",\"id\":10,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":1},{\"createdAt\":1609836273878,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/4/5ff426e6dc56b9d4328d21cdejv1t1yt.png\",\"id\":2,\"authorId\":2,\"title\":\"测试\",\"type\":2,\"content\":\"<p>www adadad</p>\",\"products\":[{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":2}],\"listStyle\":1,\"title\":\"情景购\"}]',1609896203666,2,'啛啛喳喳错',1),
	(73,'[{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/l/5ff957464927d9281e8a3bb6dukz2zr0.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/x/5ff957604927d9281e8a3bb7bmdvvgk1.png\",\"act\":\"LINK\",\"cindex\":0,\"payload\":{\"url\":\"https://www.baidu.com/s?ie=UTF-8&wd=baidu\"},\"index\":1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"}]',1610176411718,2,'测试banner',1),
	(75,'[{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/9/5ffab00c6151793e7c76c896gcldoxq1.png\",\"act\":\"LINK\",\"cindex\":0,\"payload\":{\"url\":\"http://sandbox-wx.moco.maidaotech.cn/#/article/4028b2447684765801768485f63a0007\"},\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/w/5ffab02c6151793e7c76c897tdhhrccf.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/o/5ffab0366151793e7c76c8989qgl1xuk.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"},{\"key\":\"NAV\",\"list\":[{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/d/5ffab0626151793e7c76c899baxfoag7.png\",\"name\":\"居家用品\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/5/5ffab0766151793e7c76c89ab1judv3x.png\",\"name\":\"家具系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/m/5ffab0876151793e7c76c89bk285idi1.png\",\"name\":\"家电系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/5/5ffab0966151793e7c76c89cdkwpuhsy.png\",\"name\":\"家饰系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/10/5/5ffab0a86151793e7c76c89d64ye0rwu.png\",\"name\":\"空间系列\",\"index\":-1,\"type\":\"case\"}],\"listStyle\":1,\"title\":\"二级导航\"},{\"key\":\"BESTBUY\",\"list\":[{\"createdAt\":1610179692566,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/0/5ff963e34927d93a234be02f3ici00k8.png\"],\"sno\":\"2\",\"price\":998,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"茄子状小台灯\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"茄子状小台灯\",\"id\":13,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"恒好用\"}],\"priority\":2,\"content\":\"<p>茄子状小台灯恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2},{\"createdAt\":1610179402377,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/7/5ff963014927d93a234be02eqx629889.png\"],\"sno\":\"1\",\"price\":143,\"params\":[{\"label\":\"暂无\",\"value\":\"测试\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"艺术水泥灰花盆\",\"id\":12,\"params\":[{\"label\":\"艺术水泥灰花盆\",\"value\":\"艺术水泥灰花盆艺术水泥灰花盆艺术水泥灰花盆\"}],\"priority\":1,\"content\":\"<p>艺术水泥灰花盆很漂亮，艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2}],\"listStyle\":1,\"subHeading\":\"好玩有趣，应有尽有\",\"title\":\"发现好物\"},{\"key\":\"MERCHANT\",\"list\":[{\"createdAt\":1610181891322,\"expiredAt\":1641285891322,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/5/5ff96c914927d93a234be036pr620048.png\"],\"mobile\":\"86-18303800683\",\"name\":\"极有家生活好品质\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/b/5ff96c6d4927d93a234be0358kvplvvk.png\",\"location\":{\"code\":\"410184\",\"lng\":113.742371,\"poiaddress\":\"河南省郑州市新郑市中华路与人民路交叉口东北角\",\"detail\":\"ahdhjajhj\",\"poiname\":\"中信银行(新郑支行)\",\"lat\":34.395906},\"id\":16,\"sequences\":[\"100000\",\"110000\"],\"title\":\"极有家生活好品质\",\"status\":2},{\"createdAt\":1610181998094,\"expiredAt\":1641285998094,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/2/5ff96d4f4927d93a234be038nydudkn4.png\"],\"mobile\":\"86-18303800683\",\"name\":\"宜家一个占位符\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/f/5ff96d2b4927d93a234be0376lsbgtfl.png\",\"location\":{\"code\":\"410184\",\"lng\":113.740772,\"poiaddress\":\"河南省郑州市新郑市人民路西段186号\",\"detail\":\"啊啊啊啊\",\"poiname\":\"新郑市人民政府\",\"lat\":34.395692},\"id\":17,\"sequences\":[\"100000\",\"110000\"],\"title\":\"宜家一个占位符\",\"status\":2}],\"listStyle\":1,\"subHeading\":\"好生活从周边开始\",\"title\":\"发现好店\"},{\"key\":\"SCENE\",\"list\":[{\"createdAt\":1609894782271,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/6/v/5ff50b3fdc56b9db79c8b105auiyg7es.png\",\"id\":3,\"authorId\":2,\"title\":\"秋季，空间风格\",\"type\":1,\"content\":\"<p>小编，水上一篇。</p>\",\"products\":[{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2},{\"createdAt\":1609330539166,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"先来半杯小鸟伏特加\",\"id\":10,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":1},{\"createdAt\":1609836273878,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/5/4/5ff426e6dc56b9d4328d21cdejv1t1yt.png\",\"id\":2,\"authorId\":2,\"title\":\"测试\",\"type\":2,\"content\":\"<p>www adadad</p>\",\"products\":[{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/ysmc.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":2}],\"listStyle\":1,\"subHeading\":\"场景购买，懂你所懂\",\"title\":\"发现好用\"},{\"key\":\"ARTICLE\",\"list\":[{\"id\":10066,\"title\":\"河北20例确诊！\",\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/6/9/5ff56d619a4959e3ed8b89171opc7och.png\"},{\"id\":10065,\"title\":\"大连：有病例做11次核酸检测才呈阳性\",\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/4/d/5ff2fc5e1aa3b9c79abf8af5baqsud2t.png\"}],\"listStyle\":1,\"subHeading\":\"探索从房间开始\",\"title\":\"发现好玩\"}]',1610264944956,2,'3c家居',1),
	(76,'[{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/l/5ffd3940d3363960b3211a2573lonus8.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/v/5ffd394dd3363960b3211a26p4itdd64.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/d/5ffd395fd3363960b3211a279xcdqky7.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/c/5ffd3968d3363960b3211a28ee1s8lm6.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"},{\"key\":\"NAV\",\"list\":[{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/5/5ffd398ed3363960b3211a298822l9t1.png\",\"name\":\"居家用品\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/1/5ffd39a5d3363960b3211a2aa1bpzf9a.png\",\"name\":\"家具系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/u/5ffd39b4d3363960b3211a2bov324z86.png\",\"name\":\"家电系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/3/5ffd39c3d3363960b3211a2cy7g72q1m.png\",\"name\":\"家饰系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/d/5ffd39d7d3363960b3211a2d9biz6rys.png\",\"name\":\"空间系列\",\"index\":-1,\"type\":\"case\"}],\"listStyle\":1,\"title\":\"二级导航\"},{\"key\":\"ARTICLE\",\"list\":[{\"id\":10070,\"title\":\"水电装修质量验收的标准是什么\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/4/60066e10737bb92cdec20f9dbdkjha4r.png\",\"content\":\"\",\"pageView\":1267},{\"id\":10069,\"title\":\"家具选材十个非常有用的小技巧，家具选材\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/j/60066d39737bb92cdec20f9cen6lufd5.png\",\"content\":\"\",\"pageView\":221}],\"listStyle\":1,\"title\":\"推荐文章\"},{\"key\":\"SCENE\",\"list\":[{\"createdAt\":1610427421192,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/l/5ffd2bffd336395ca1579f76ilpw1c74.png\",\"subHeading\":\"好玩有趣，应有尽有\",\"id\":6,\"authorId\":2,\"title\":\"新色彩，海洋硬糖系列打造专属厨房\",\"type\":1,\"content\":\"<p>好玩有趣，应有尽有</p>\",\"products\":[{\"createdAt\":1610179692566,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/0/5ff963e34927d93a234be02f3ici00k8.png\"],\"sno\":\"2\",\"price\":998,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"茄子状小台灯\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"茄子状小台灯\",\"id\":13,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"恒好用\"}],\"priority\":2,\"content\":\"<p>茄子状小台灯恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2},{\"createdAt\":1609330913440,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/kugou.jpg\"],\"sno\":\"1\",\"price\":998,\"params\":[{\"label\":\"你\",\"value\":\"  问问\"}]}],\"merchantId\":5,\"brandId\":1,\"name\":\"就来这么高\",\"id\":11,\"params\":[{\"label\":\"啊啊啊\",\"value\":\"我问问\"}],\"priority\":3,\"content\":\"<p>吾问无为谓</p>\",\"status\":2}],\"status\":2},{\"createdAt\":1610420619724,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/12/m/5ffd115ad336395ca1579f74iynlzfui.png\",\"subHeading\":\"测试副标题\",\"id\":4,\"authorId\":2,\"title\":\"测试\",\"type\":1,\"content\":\"<p>新京报快讯 据国家卫健委网站消息，1月11日0&mdash;24时，31个省(自治区、直辖市)和新疆生产建设兵团报告新增确诊病例55例，其中境外输入病例13例(上海4例，辽宁3例，天津2例，福建1例，河南1例，广东1例，陕西1例)，本土病例42例(河北40例，北京1例，黑龙江1例);无新增死亡病例;无新增疑似病例。</p><p>当日新增治愈出院病例31例，解除医学观察的密切接触者567人，重症病例较前一日减少2例。</p>\",\"products\":[{\"createdAt\":1610179692566,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/0/5ff963e34927d93a234be02f3ici00k8.png\"],\"sno\":\"2\",\"price\":998,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"茄子状小台灯\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"茄子状小台灯\",\"id\":13,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"恒好用\"}],\"priority\":2,\"content\":\"<p>茄子状小台灯恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2},{\"createdAt\":1610179402377,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/7/5ff963014927d93a234be02eqx629889.png\"],\"sno\":\"1\",\"price\":143,\"params\":[{\"label\":\"暂无\",\"value\":\"测试\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"艺术水泥灰花盆\",\"id\":12,\"params\":[{\"label\":\"艺术水泥灰花盆\",\"value\":\"艺术水泥灰花盆艺术水泥灰花盆艺术水泥灰花盆\"}],\"priority\":1,\"content\":\"<p>艺术水泥灰花盆很漂亮，艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2}],\"status\":2}],\"listStyle\":1,\"subHeading\":\"场景购买，懂你所懂\",\"title\":\"发现好用\"},{\"key\":\"MERCHANT\",\"list\":[{\"createdAt\":1610181998094,\"expiredAt\":1641285998094,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/2/5ff96d4f4927d93a234be038nydudkn4.png\"],\"mobile\":\"86-18303800683\",\"name\":\"宜家一个占位符\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/f/5ff96d2b4927d93a234be0376lsbgtfl.png\",\"location\":{\"code\":\"410184\",\"lng\":113.740772,\"poiaddress\":\"河南省郑州市新郑市人民路西段186号\",\"detail\":\"啊啊啊啊\",\"poiname\":\"新郑市人民政府\",\"lat\":34.395692},\"id\":17,\"sequences\":[\"100000\",\"110000\"],\"title\":\"宜家一个占位符\",\"status\":2},{\"createdAt\":1610181891322,\"expiredAt\":1641285891322,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/5/5ff96c914927d93a234be036pr620048.png\"],\"mobile\":\"86-18303800683\",\"name\":\"极有家生活好品质\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/b/5ff96c6d4927d93a234be0358kvplvvk.png\",\"location\":{\"code\":\"410184\",\"lng\":113.742371,\"poiaddress\":\"河南省郑州市新郑市中华路与人民路交叉口东北角\",\"detail\":\"ahdhjajhj\",\"poiname\":\"中信银行(新郑支行)\",\"lat\":34.395906},\"id\":16,\"sequences\":[\"100000\",\"110000\"],\"title\":\"极有家生活好品质\",\"status\":2}],\"listStyle\":1,\"subHeading\":\"好生活从周边开始\",\"title\":\"发现好店\"},{\"key\":\"BESTBUY\",\"list\":[{\"createdAt\":1610179692566,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/0/5ff963e34927d93a234be02f3ici00k8.png\"],\"sno\":\"2\",\"price\":998,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"茄子状小台灯\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"茄子状小台灯\",\"id\":13,\"params\":[{\"label\":\"茄子状小台灯\",\"value\":\"恒好用\"}],\"priority\":2,\"content\":\"<p>茄子状小台灯恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用恒好用</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2},{\"createdAt\":1610179402377,\"sequence\":\"010101\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/9/7/5ff963014927d93a234be02eqx629889.png\"],\"sno\":\"1\",\"price\":143,\"params\":[{\"label\":\"暂无\",\"value\":\"测试\"}]}],\"merchantId\":5,\"brandId\":5,\"name\":\"艺术水泥灰花盆\",\"id\":12,\"params\":[{\"label\":\"艺术水泥灰花盆\",\"value\":\"艺术水泥灰花盆艺术水泥灰花盆艺术水泥灰花盆\"}],\"priority\":1,\"content\":\"<p>艺术水泥灰花盆很漂亮，艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮艺术水泥灰花盆很漂亮</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":2}],\"listStyle\":1,\"subHeading\":\"好玩有趣，应有尽有\",\"title\":\"发现好物\"}]',1610431099831,2,'3c家居2',1),
	(77,'[{\"key\":\"BANNER\",\"list\":[{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/m/5ffff1983cb779015090adb1cximwj7y.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/h/5ffff1a83cb779015090adb2ukb3qzq6.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"},{\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/9/5ffff1c33cb779015090adb3fcbcwzx6.png\",\"act\":\"NONE\",\"cindex\":0,\"index\":-1,\"type\":\"BANNER\"}],\"listStyle\":1,\"title\":\"轮播图\"},{\"key\":\"NAV\",\"list\":[{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/5/5ffff1f13cb779015090adb46d6jsqhl.png\",\"name\":\"居家用品\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/r/5ffff2023cb779015090adb5vujswgvp.png\",\"name\":\"家具系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/f/5ffff2153cb779015090adb68f2rhdkq.png\",\"name\":\"家电系列\",\"index\":-1,\"type\":\"atlas\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/k/5ffff2243cb779015090adb7irb5bm68.png\",\"name\":\"家饰系列\",\"index\":-1,\"type\":\"case\"},{\"cindex\":1,\"icon\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/7/5ffff2343cb779015090adb8t2odrvlk.png\",\"name\":\"空间系列\",\"index\":-1,\"type\":\"case\"}],\"listStyle\":1,\"title\":\"二级导航\"},{\"key\":\"BESTBUY\",\"list\":[{\"createdAt\":1610609545406,\"sequence\":\"030401\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/v/5ffff3503cb779015090adb9mccfwpp0.png\"],\"sno\":\"1\",\"price\":1390000,\"params\":[{\"label\":\"显卡\",\"value\":\"1\"}]}],\"merchantId\":1,\"price\":1390000,\"brandId\":7,\"name\":\"英伟达RTX-3070\",\"id\":3,\"params\":[{\"label\":\"3070\",\"value\":\"\"}],\"priority\":2,\"content\":\"<p>显卡显卡</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1},{\"createdAt\":1610594282743,\"sequence\":\"030301\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/o/5fffb7929712b981895e9512lwrpdox2.png\"],\"sno\":\"2\",\"price\":379900,\"params\":[{\"label\":\"cpu\",\"value\":\"nb\"}]}],\"merchantId\":1,\"price\":379900,\"brandId\":6,\"name\":\"i9-10900k\",\"id\":2,\"params\":[{\"label\":\"i9\",\"value\":\"十代\"}],\"priority\":2,\"content\":\"<p>十代i9很牛逼</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1}],\"listStyle\":1,\"subHeading\":\"好玩有趣，应有尽有\",\"title\":\"好物发现\"},{\"key\":\"MERCHANT\",\"list\":[{\"createdAt\":1610592780119,\"expiredAt\":1641696780119,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/k/5fffb1ef9712b980c9b1f1beb36ynvrl.png\"],\"mobile\":\"86-18437211582\",\"name\":\"intel官方旗舰店\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/r/5fffb1839712b980c9b1f1bdwiqou3di.png\",\"location\":{\"code\":\"410184\",\"lng\":113.742371,\"poiaddress\":\"河南省郑州市新郑市中华路与人民路交叉口东北角\",\"detail\":\"龙湖镇\",\"poiname\":\"中信银行(新郑支行)\",\"lat\":34.395906},\"id\":2,\"sequences\":[\"030000\"],\"title\":\"intel官方旗舰店\",\"status\":2},{\"createdAt\":1610592501311,\"expiredAt\":1641696501311,\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/5/5fffb0e39712b980c9b1f1bcqri7xv4n.png\"],\"lastModify\":1610603494023,\"mobile\":\"86-18303800683\",\"name\":\"微星官方旗舰店\",\"logo\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/j/5fffb0729712b980c9b1f1bb3pi5wp8d.png\",\"location\":{\"code\":\"410184\",\"lng\":113.740772,\"poiaddress\":\"河南省郑州市新郑市人民路西段186号\",\"detail\":\"龙湖镇\",\"poiname\":\"新郑市人民政府\",\"lat\":34.395692},\"id\":1,\"sequences\":[\"030000\"],\"title\":\"微星官方旗舰店\",\"status\":1}],\"listStyle\":1,\"subHeading\":\"好生活从周边开始\",\"title\":\"发现好店\"},{\"key\":\"SCENE\",\"list\":[{\"createdAt\":1610585027527,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/5/5fff938c9712b97bcac7c15fvri12mwy.png\",\"subHeading\":\"新色彩，海洋硬糖系列打造\",\"id\":3,\"authorId\":2,\"title\":\"新色彩，海洋硬糖系列打造\",\"type\":1,\"content\":\"<p>《红楼梦》，中国古代<a data-lemmaid=\\\"11018000\\\" href=\\\"https://baike.baidu.com/item/%E7%AB%A0%E5%9B%9E%E4%BD%93/11018000\\\" target=\\\"_blank\\\">章回体</a><a data-lemmaid=\\\"7708668\\\" href=\\\"https://baike.baidu.com/item/%E9%95%BF%E7%AF%87%E5%B0%8F%E8%AF%B4/7708668\\\" target=\\\"_blank\\\">长篇小说</a>，中国古典<a data-lemmaid=\\\"8376\\\" href=\\\"https://baike.baidu.com/item/%E5%9B%9B%E5%A4%A7%E5%90%8D%E8%91%97/8376\\\" target=\\\"_blank\\\">四大名著</a>之一，一般认为是清代作家<a data-lemmaid=\\\"14919\\\" href=\\\"https://baike.baidu.com/item/%E6%9B%B9%E9%9B%AA%E8%8A%B9/14919\\\" target=\\\"_blank\\\">曹雪芹</a>所著。小说以贾、史、王、薛<a data-lemmaid=\\\"2314013\\\" href=\\\"https://baike.baidu.com/item/%E5%9B%9B%E5%A4%A7%E5%AE%B6%E6%97%8F/2314013\\\" target=\\\"_blank\\\">四大家族</a>的兴衰为背景，以富贵公子<a data-lemmaid=\\\"59563\\\" href=\\\"https://baike.baidu.com/item/%E8%B4%BE%E5%AE%9D%E7%8E%89/59563\\\" target=\\\"_blank\\\">贾宝玉</a>为视角，以贾宝玉与<a data-lemmaid=\\\"260081\\\" href=\\\"https://baike.baidu.com/item/%E6%9E%97%E9%BB%9B%E7%8E%89/260081\\\" target=\\\"_blank\\\">林黛玉</a>、<a data-lemmaid=\\\"396702\\\" href=\\\"https://baike.baidu.com/item/%E8%96%9B%E5%AE%9D%E9%92%97/396702\\\" target=\\\"_blank\\\">薛宝钗</a>的爱情婚姻悲剧为主线，描绘了一批举止见识出于须眉之上的闺阁佳人的人生百态，展现了真正的人性美和悲剧美，可以说是一部从各个角度展现女性美以及中国古代社会世态百相的史诗性著作。</p>\",\"products\":[{\"createdAt\":1610685611959,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"sno\":\"000003\",\"price\":5900,\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"sno\":\"000004\",\"price\":6900,\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"sno\":\"000005\",\"price\":7900,\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"sno\":\"000006\",\"price\":8900,\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"sno\":\"000007\",\"price\":5500,\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"stock\":81}],\"merchantId\":3,\"price\":6900,\"brandId\":4,\"name\":\"led吸顶灯\",\"id\":4,\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"priority\":1,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1}],\"status\":2},{\"createdAt\":1610584916421,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/c/5fff93279712b97bcac7c15e4lfxhffe.png\",\"subHeading\":\"碰撞出兼具当代与复古风味的视觉感受\",\"id\":2,\"authorId\":2,\"title\":\"活力感：蓝色系与橙色系搭配\",\"type\":3,\"content\":\"<p>活力感：蓝色系与橙色系搭配，将这两种对比色组合在一起，碰撞出兼具当代与复古风味的视觉感受，给予空间一种新的生命。</p>\",\"products\":[{\"createdAt\":1611125761762,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"sno\":\"000001\",\"price\":8900,\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"sno\":\"000002\",\"price\":7900,\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"stock\":998}],\"merchantId\":3,\"price\":8500,\"name\":\"兔子智能小闹钟\",\"id\":5,\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"priority\":2,\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1}],\"status\":2},{\"createdAt\":1610584822078,\"img\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/14/9/5fff929a9712b97bcac7c15d2x48aycb.png\",\"subHeading\":\"现代感：黑、白、灰是永恒经典的色调。\",\"id\":1,\"authorId\":2,\"title\":\"新色彩，海洋硬糖系列打造专属\",\"type\":4,\"content\":\"<p>你强任你强，大哥背行囊</p>\",\"products\":[{\"createdAt\":1610685611959,\"sequence\":\"040201\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/r/60010ef5092ef9115eae13a00qtguibn.png\"],\"sno\":\"000003\",\"price\":5900,\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":91},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/d/60010f46092ef9115eae13a1xadvtxed.png\"],\"sno\":\"000004\",\"price\":6900,\"params\":[{\"label\":\"形状\",\"value\":\"太阳花长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":123},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/m/60010f75092ef9115eae13a2cxhay49b.png\"],\"sno\":\"000005\",\"price\":7900,\"params\":[{\"label\":\"形状\",\"value\":\"交叉文长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":78},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/6/60010faf092ef9115eae13a3rpqdp1mj.png\"],\"sno\":\"000006\",\"price\":8900,\"params\":[{\"label\":\"形状\",\"value\":\"花朵长\"},{\"label\":\"尺寸\",\"value\":\"70*50\"}],\"stock\":84},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/15/v/60011c34092ef9115eae13a41b60a02z.png\"],\"sno\":\"000007\",\"price\":5500,\"params\":[{\"label\":\"形状\",\"value\":\"蝴蝶花长\"},{\"label\":\"尺寸\",\"value\":\"60*50\"}],\"stock\":81}],\"merchantId\":3,\"price\":6900,\"brandId\":4,\"name\":\"led吸顶灯\",\"id\":4,\"params\":[{\"label\":\"品牌\",\"value\":\"卡优美\"},{\"label\":\"型号\",\"value\":\"KYM-1277\"},{\"label\":\"功率\",\"value\":\"41w（含）-50w（含）\"},{\"label\":\"颜色分类\",\"value\":\"蝴蝶花长70*50太阳花长70*50\"},{\"label\":\"电压\",\"value\":\"220v\"},{\"label\":\"生产企业\",\"value\":\"中山市古镇灯天灯饰厂\"},{\"label\":\"上市时间\",\"value\":\"2020-11-07\"},{\"label\":\"保修期\",\"value\":\"36个月\"}],\"priority\":1,\"content\":\"<p>灯灯灯</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1},{\"createdAt\":1611125761762,\"sequence\":\"030205\",\"specs\":[{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/i/6007d3a35845f942c4861202z4vm93gz.png\"],\"sno\":\"000001\",\"price\":8900,\"params\":[{\"label\":\"颜色\",\"value\":\"绿色\"}],\"stock\":766},{\"imgs\":[\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/20/y/6007d3d35845f942c486120360blknah.png\"],\"sno\":\"000002\",\"price\":7900,\"params\":[{\"label\":\"颜色\",\"value\":\"粉色\"}],\"stock\":998}],\"merchantId\":3,\"price\":8500,\"name\":\"兔子智能小闹钟\",\"id\":5,\"params\":[{\"label\":\"品牌\",\"value\":\"库火\"},{\"label\":\"材质\",\"value\":\"其他\"},{\"label\":\"产地\",\"value\":\"中国大陆\"},{\"label\":\"省份\",\"value\":\"广东\"}],\"priority\":2,\"content\":\"<p>好看好看好看好好看好看</p><p data-f-id=\\\"pbf\\\" style=\\\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\\\">Powered by <a href=\\\"https://www.froala.com/wysiwyg-editor?pb=1\\\" title=\\\"Froala Editor\\\">Froala Editor</a></p>\",\"status\":1}],\"status\":2}],\"listStyle\":1,\"subHeading\":\"情景购买，懂你所懂\",\"title\":\"发现好用\"},{\"key\":\"ARTICLE\",\"list\":[{\"intro\":\"水电装修质量验收的标准是什么水电装修质量验收的标准是什么啊啊啊啊啊啊\",\"id\":10070,\"title\":\"水电装修质量验收的标准是什么\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/4/60066e10737bb92cdec20f9dbdkjha4r.png\",\"content\":\"<p>电改造 1、 所用电线必须符合工程用电要求。 2、 导线连接紧固，导线接头要受力、并有足够的强度。 3、 剥离电线绝缘层时不得损伤电线线芯。 4、 接地线应保证导线的连续性，不允许设置保险、开关、接地电阻应小于10欧姆。 5、 电线与电气元件连接后，无绝缘距离不能大于3mm。紧压连接的导线应顺压紧方向绕一圈或使用接线端子，支紧连接时孔内电线截面积应超过0.5倍孔面积。 6、 采用绞接法时绞接长度应不小于5圈，采用邦扎长度为芯线直径的10倍，并且连接后搪上焊锡，用黑电工胶布包扎时内应包扎塑料防潮绝缘胶布。</p>\",\"pageView\":1267},{\"intro\":\"河北20例确诊！国家卫健委：昨日新增确诊病例32例，其中本土病例23例\",\"id\":10066,\"title\":\"河北20例确诊！\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/6/9/5ff56d619a4959e3ed8b89171opc7och.png\",\"content\":\"<div data-islow-browser=\\\"0\\\"><p>1月6日，国家卫健委网站通报，1月5日0&mdash;24时，31个省（自治区、直辖市）和新疆生产建设兵团报告新增确诊病例32例，其中境外输入病例9例（广东3例，陕西3例，上海2例，天津1例），本土病例23例（河北20例，北京1例，辽宁1例，黑龙江1例）；无新增死亡病例；新增疑似病例2例，其中境外输入病例1例（在上海），本土病例1例（在辽宁）。</p><p>当日新增治愈出院病例21例，解除医学观察的密切接触者784人，重症病例较前一日增加1例。</p><p>境外输入现有确诊病例285例（其中重症病例4例），现有疑似病例2例。累计确诊病例4348例，累计治愈出院病例4063例，无死亡病例。</p><p>截至1月5日24时，据31个省（自治区、直辖市）和新疆生产建设兵团报告，现有确诊病例443例（其中重症病例14例），累计治愈出院病例82138例，累计死亡病例4634例，累计报告确诊病例87215例，现有疑似病例3例。累计追踪到密切接触者912596人，尚在医学观察的密切接触者17736人。</p><p>31个省（自治区、直辖市）和新疆生产建设兵团报告新增无症状感染者64例（境外输入19例）；当日转为确诊病例8例（境外输入3例）；当日解除医学观察14例（境外输入10例）；尚在医学观察无症状感染者360例（境外输入246例）。</p><p>累计收到港澳台地区通报确诊病例9912例。其中，香港特别行政区9049例（出院8127例，死亡153例），澳门特别行政区46例（出院46例），台湾地区817例（出院697例，死亡7例）。</p></div><p><br></p>\",\"pageView\":444},{\"intro\":\"厨房应该是什么样子？——此开卷第一回也。作者自云：曾历过一番梦幻之后，故将真事隐去\",\"id\":10067,\"title\":\"现代化科技的厨房应该是什么样子？\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/x/60066bce737bb92cdec20f9a8ns0wgio.png\",\"content\":\"<pre>&mdash;&mdash;此开卷第一回也。作者自云：曾历过一番梦幻之后，故将真事隐去，而借\\n通灵说此《石头记》一书也，故曰&ldquo;甄士隐&rdquo;云云。但书中所记何事何人?自己又\\n云：&ldquo;今风尘碌碌，一事无成，忽念及当日所有之女子：一一细考较去，觉其行止\\n见识皆出我之上。我堂堂须眉诚不若彼裙钗，我实愧则有馀，悔又无益，大无可如\\n何之日也。当此日，欲将已往所赖天恩祖德，锦衣纨之时，饫甘餍肥之日，背父\\n兄教育之恩，负师友规训之德，以致今日一技无成、半生潦倒之罪，编述一集，以\\n告天下；知我之负罪固多，然闺阁中历历有人，万不可因我之不肖，自护己短，一\\n并使其泯灭也。所以蓬牖茅椽，绳床瓦灶，并不足妨我襟怀；况那晨风夕月，阶柳\\n庭花，更觉得润人笔墨。我虽不学无文，又何妨用假语村言敷演出来?亦可使闺阁\\n昭传。复可破一时之闷，醒同人之目，不亦宜乎？&rdquo;故曰&ldquo;贾雨村&rdquo;云云。更于篇\\n中间用&ldquo;梦&rdquo;&ldquo;幻&rdquo;等字，却是此书本旨，兼寓提醒阅者之意。</pre>\",\"pageView\":11123},{\"intro\":\"家具选材十个非常有用的小技巧，家具选家具选材十个非常有用的小技巧，\",\"id\":10068,\"title\":\"家具选材十个非常有用的小技巧，家具选\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/s/60066cbe737bb92cdec20f9bxj1zli0z.png\",\"content\":\"<pre>看官你道此书从何而起?说来虽近荒唐，细玩颇有趣味。却说那女娲氏炼石补天之时，于大荒山无稽崖炼成高十二丈、见方二十四丈大的顽石三万六千五百零一块。那娲皇只用了三万六千五百块，单单剩下一块未用，弃在青埂峰下。谁知此石自经锻炼之后，灵性已通，自去自来，可大可小。因见众石俱得补天，独自己无才不得入选，遂自怨自愧，日夜悲哀。一日正当嗟悼之际，俄见一僧一道远远而来，生得骨格不凡，丰神迥异，来到这青埂峰下，席地坐谈。见着这块鲜莹明洁的石头，\\n且又缩成扇坠一般，甚属可爱。那僧托于掌上，笑道：&ldquo;形体倒也是个灵物了，只是没有实在的好处。须得再镌上几个字，使人人见了便知你是件奇物，然后携你到那昌明隆盛之邦、诗礼簪缨之族、花柳繁华地、温柔富贵乡那里去走一遭。&rdquo;石头听了大喜，因问：&ldquo;不知可镌何字?携到何方?望乞明示。&rdquo;那僧笑道：&ldquo;你且莫问，日后自然明白。&rdquo;说毕，便袖了，同那道人飘然而去，竟不知投向何方。</pre>\",\"pageView\":44},{\"intro\":\"小伙子你这什么车啊?AE86,家具选材十个非常有用的小技巧，家具选材\",\"id\":10069,\"title\":\"家具选材十个非常有用的小技巧，家具选材\",\"authorId\":2,\"picture\":\"https://18303800683.oss-cn-beijing.aliyuncs.com/smart/file/2021/1/19/j/60066d39737bb92cdec20f9cen6lufd5.png\",\"content\":\"<p>一、 水改造 1、 水路工程安装必须符合设计要求和行业规范。 2、 管道的铺设必须整齐，水表安装水平、方向正确。 3、 管道及管道支座铺设必须牢固。 4、 接头紧密、平直、无砂眼，接头严密，无漏水及滴漏现象。 5、 水龙头预留位置正确合理（根据设计和甲方洁具型号定水的出水口和下水口）。 6、 外露排水管的铺设也应牢固、平直、美观、合理、无漏水现象。 7、 水出口与排水管承口的连接必须严不漏。 8、</p>\",\"pageView\":221}],\"listStyle\":1,\"subHeading\":\"探索从房间开始\",\"title\":\"发现好玩\"}]',1610609654029,1,'3c家居测试3',1);

/*!40000 ALTER TABLE `ui` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT '',
  `age` int(3) DEFAULT '18',
  `sex` tinyint(1) DEFAULT NULL COMMENT '??1??2??',
  `mobile` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(200) DEFAULT '',
  `status` tinyint(1) DEFAULT NULL,
  `created_at` bigint(20) NOT NULL,
  `avatar` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `age`, `sex`, `mobile`, `password`, `status`, `created_at`, `avatar`)
VALUES
	(10015,'张卫正',NULL,1,'86-18303800683','75382d4c2e382475872c146d1e0becebd648f753e83efc5b7cb8b9ef9f332754',1,1610084588159,'https://18303800683.oss-cn-beijing.aliyuncs.com/smart/f/2021/1/22/t/600a20cd936a5961dbeb2edd0thhtdt0.png'),
	(10016,NULL,NULL,NULL,'86-13271377120','75382d4c2e382475872c146d1e0becebd648f753e83efc5b7cb8b9ef9f332754',1,1611207327931,NULL);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_session`;

CREATE TABLE `user_session` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `token` varchar(300) DEFAULT NULL,
  `signin_at` bigint(20) NOT NULL,
  `expire_at` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;

INSERT INTO `user_session` (`id`, `user_id`, `token`, `signin_at`, `expire_at`)
VALUES
	(102,10015,'7YqO6c0aCsfWsJvOretYxfGunKWCicfJgR46uifODw4oAHvSIBIJdTUk08C4ZzEU',1610085219728,1610171619728),
	(103,10015,'qGF7JlaGm5hx30TAdo9CoSwkXWatA48iWMiwUuQF6SfwAs6S4Iu7ymDnuHndxFow',1610087350313,1610173750313),
	(104,10015,'XaKRPCd7qFDHwTAnwsyFPZz1V8rnKeZpwr4QXdgrjTgGhTfXa4ychzgBmSBllVDr',1610157756440,1610244156440),
	(105,10015,'yTdEw0tX0pTFElHi3ziEykwT6xqbeXzKlRiynZwBAdhDGIesfRVKnBqEkZ8jldxq',1610628203536,1610714603536),
	(106,10015,'cQb8PqU8Ndzax7EZEwtRhSWjHcB2LIgE3rrTDJHbzlrrWm2WxOzj8Uxspp9TLgGu',1610633266683,1610719666683),
	(107,10015,'YZRYHi0P4AQpIYkgVItYcPNjzj6mOEWwnyuNfvsaLYkTXHfcsyobCbOykQbqplZc',1610671537311,1610757937311),
	(108,10015,'ndmA4D1yfEhi97JxvuonPVhN9srRU9FGNBf1fz6xGmllBsfyMONZAk04l8LSGqIx',1610671828245,1610758228245),
	(109,10015,'bRWO49sphhPxMMHEJF1NDOtXMTmK3Yc7K67QqGllZuueR5B0neAEJHiB4X9Vq8kO',1610698107254,1610784507254),
	(110,10015,'i8EsRTowcQheVEfqAKmhE3QCeqEh4AXyDDFZqXRx0NusWg2Hb58NFR7GxuQAA5Jx',1610771630416,1610858030416),
	(111,10015,'VkwZfF0p3jaZT68QVFtt1v2Ow3HtbJT0js0ZfLM3O1XcDuSwP7GvK7jV2bFBxOwm',1610951418049,1611037818049),
	(112,10015,'mX6V0YFSasbzneHfZHrOhWGDC4kbiOmdWYRIjliGz6uCTGOjvZeMamlU2VSySM0I',1610951435069,1611037835069),
	(113,10015,'EHILoXLOb9SbAKtYzukt4BIqgDTEHkBF4ddzORmaAk93EfXpgYm7y5CfdHGdL4Ap',1611038448811,1611124848811),
	(114,10015,'3tyQ9GUdknZk6aVhti7jILrP75x9yatwelPGH4pJIQDvyRdctZNKfYoymIuc9ubW',1611039243796,1611125643796),
	(115,10015,'CipFkgsSYJQEd3jdBxuQffMYGLhCe4yfX7zXO94DBOwk88OrzfXFQfzuoKKJ4NVB',1611054935920,1611141335920),
	(116,10015,'07ufwQ4aXqdNopgAzNg9E16NFU8bbNSzvxEu35UoyeS5j8LcLrXuWXUe9PCttWNJ',1611054935920,1611141335920),
	(117,10015,'8M4E5zbvgEjeNb4W2HIl5lo6TJnNuM3IOEbyCYI69QpPuXd21nLLgttPFTBfADbf',1611103914162,1611190314162),
	(118,10015,'owPxZgL1waDe6FXMk9rchXwWmpaf4giRYrOeRoxUfqF2ykl7WfyscwA2UUlj0aEs',1611112383902,1611198783902),
	(119,10015,'ty2wsBugfFyRmQseQHAnPLKkeIvkz2ImCMLGBUxmMzkjBYTEKAuUvAfd5gw0rQS6',1611118393770,1611204793770),
	(120,10015,'6bJAC5rPLXiL8UcLLs7VEevXEw5RfFMdY4DtCRk4Hr9LK3NbUOlvlVSju47PXoKT',1611122658567,1611209058567),
	(121,10015,'qAvjLIEi94npcF2OgsdCoARKTrgsHKFZrY8PPtFqofp0SJEPvKJMeATlf5D6JkQv',1611127860955,1611214260955),
	(122,10015,'ylYFwfLtq4Zxb3B5SISp2owqU3rQhUfCCc6pL0l3yed8WgZ4OTDldwuNts0dImMl',1611128163986,1611214563986),
	(123,10015,'GXYcy4fBeXUyddH05sfe01HbxFEEkcmeBmgUPB8YXER2Dq50p90R4pinSnseptdj',1611128522012,1611214922012),
	(124,10015,'09tUORIw5cjY3JP056It7EmKM1erWC9mTNrx6h7qwZFA0sHLfP0i1uN3uJ3F0iV3',1611153357268,1611239757268),
	(125,10015,'rFz1oF4COS9zloIRoETOhYIGdOWq9d1frsjpOvSkC3MDcbpYZiu3w2FoqYb2JsFl',1611153357595,1611239757595),
	(126,10016,'silOtxZ0yU6Gq8dK8GNyfp4a9cVJWj7qHbW0soEhFsGhSv2a5ji1hjtuGptyhluk',1611207332307,1611293732307),
	(127,10016,'7NYXliOara4WsPDBOYJLehYfzveYeaDdJqGro11jPR4t2soHbslW7NgYEpqqGr0c',1611217120638,1611303520638),
	(128,10015,'1WgdZLEcfpb8wIXkVsleEmK8HpnV3j4Ltkf7sCllt6rBl4ujY1hU9z1Hc2elhYjR',1611219746526,1611306146526),
	(129,10016,'2lPeGtMtL0Nw8k9F1U2OwctOVUKnqYq780kRU3wZGzkShh4hYwXG6r3wly557CwV',1611221544894,1611307944894),
	(130,10016,'vSlPbPuQ7kwCb3ibZ6BDedokctO5Lay5CNzFFRRIJmkIxOTPOpZtAmXuelkRDGXx',1611221829555,1611308229555),
	(131,10015,'oXyjT462Cgq9nGeQJ8sO99blPgmuxGw0Oz4FBLvWmEkUfo6GpgtsGnJE5kuq093P',1611227804715,1611314204715),
	(132,10015,'nD3kUOlmJNtIXZTUliNSAww7sZQy6eLgjpmJQiAkBIe4zGNMW5TOSYvVngHbbpLz',1611236709013,1611323109013),
	(133,10015,'Ob5O3mpcGw2bZn2UOzpQv9R6APOjPSjkb8UA4jcYWzU3E1pUYeG3Yv0pdXocjxZm',1611274679285,1611361079285),
	(134,10015,'cVQOsnDuaKT6WDJM6sOEYzUdsJuXRK0IoEt5NUIS0VQivjSdI9JVDs9Jwqc065eA',1611277392179,1611363792179),
	(135,10015,'SzKQTrcTYyw0p2LMQ8a3wlHYtQHPF6McbKev3Hijct4QPQfLn91OkFxx9QxBIOXn',1611323288118,1611409688118),
	(136,10015,'xnFLlSwrAdxjvv0A9Q52uCji548HTA1pjZqqfESCwuZ95iNdp6MEC5MbXV1N3him',1611364313003,1611450713003),
	(137,10015,'N0fbp3WEh0HQlgUZtPg2bPsrZdd8XkfRXsxqzPOvs5hZlAmYf1mvjeQ4ttEh8PHd',1611391379166,1611477779166),
	(138,10015,'L4wVWbUI7My5DIHDoLs4prqknmKVqAnRkfZ9lTGtUgUwCr9SO6EwWsUuFvN5L378',1611488715923,1611575115923),
	(139,10015,'fpcB8zgDVm4ze9tiaT8zO26Hg8aFYnoTBNbdhf8M3isz3COTiv6yc0FyyDN5xuPa',1611540417521,1611626817521),
	(140,10015,'Y8b23H6lFTCxNgr7kCLw9CniARi1DHqqU5dDoHwgU0YWk4lAYNXCuYtqqW8RXOVT',1611540442495,1611626842495),
	(141,10015,'bl8ewNWvaDg6EmoFUjYmBU4DHmKc7anymIZyt6XsBOBof09gQxzdjH4eFzSznPTg',1611560489515,1611646889515),
	(142,10015,'J6W5k5Su6TMSOuX9VNaQpOvzMNUSgFk4QKxzhh1OWK6UA7vFogYBnUFDCXrNQ9ud',1611576259921,1611662659921),
	(143,10015,'WgG2KXodqbvjbirqPT4lEF1HpbnY7d8jMRFoul51cqIhC9FrZZgX0rb3UQcpeR7b',1611626899262,1611713299262),
	(144,10015,'8JeiTwVm77rPWxhDx6hvj5imla5sWCwYWh8lHZ5NFdPsWdkGhAwgycRoI01AojDr',1611635310594,1611721710594),
	(145,10015,'oXGr67IlylQLGVqymDiaZTby9sB3lLkFqxg3X5yKVFcCtO4YoqHPN92QDPF9C91I',1611713328763,1611799728763),
	(146,10015,'WkIKeIadgtPOuF0ejodrAEuGx2YbhwJXArIyQwRDa8Smk4wutRLIVx4xmIK3Ji1I',1611730912737,1611817312737),
	(147,10015,'jFrfLllDt6VqK3dL1HzNWbdCs3cTifvtUhxIHc5GeUsJleqfKbmKhqwKb9pxvknQ',1611730934847,1611817334847),
	(148,10015,'CVSu0d2nxUl34aHwgKYWFdPOcWn7S2sp745QXNZZLDlyuOCUIhqWnJ3rrH3S2opt',1611886271732,1611972671732),
	(149,10015,'kAvuFuTcwVRoYEI9zVaGgXD45q9NDu3XyONZ50rj80DO0WS5ciEfKDxX0W8MDH8R',1611889935582,1611976335582),
	(150,10015,'NscfgorFnHqTNaBvbGDlP6OcdEeKKCYAHm7wSOUfoLv6HkS2Ni9zgbaelWq7UTH4',1611890018551,1611976418551),
	(151,10015,'B8qbI0HOL77MTjPXdq3GaTi9VnPFBgp3cVqKOdMiApE32q984PeqlOQyg2395HRw',1611890397033,1611976797033),
	(152,10015,'PTKQDR4j9bkUdJ07slL9fFcD0vU0MT9MOUeohLfxqVl101lCt0baFm0Ze4yHt6nL',1611890429992,1611976829992),
	(153,10015,'nV49pumsmaWq4SBXzUxJv9xPpP4elhBp0DNwhKDzKMSbmuX7h8FqGDyB4JYQPUok',1611891610431,1611978010431),
	(154,10015,'5eGDMKRvNS6t6N7kA3ejb4zgycj5sv81jjm4kaTigDWsTdgKIf34NuElYwAn4ANK',1611891621388,1611978021388),
	(155,10015,'MJQxcelPLtwvjYJoJm81ST2o25RQs83equ2rcay501QjHrKYghDKiP2uM3KzgQap',1611983938956,1612070338956),
	(156,10015,'RAuYeX6gDTP1h0Cpjmqqt7HoRHxdn03nuPs7EgVBLrVw4rrKsEtoYpum1uZFr90h',1611983957403,1612070357403),
	(157,10015,'ZRlVaQhJ0rfmLsz6c7s6161oBHqeDASQqiKRx86vNQjulh4cec8dfj7t9lK66frX',1611984012664,1612070412664),
	(158,10015,'OTREnly5hFnLRNkNWV0X16KB9Yf0Q0lqL7Vst2L39YOY9Mh9E23eF1HZUULbbDZz',1612145386017,1612231786017),
	(159,10015,'PY71htoqDK0vFQVYVlTgOZXuq3O7FaAgyAtt1zljBDEE4ua7C8GdbuIUeraqgqw7',1613630719930,1613717119930),
	(160,10015,'QPS2OlBi38tzGpxfdiSl4PdPvZt51eGzVihs4DLByHwqdpBgDZUpnbLHt1nK14gD',1613957055457,1614043455457),
	(161,10015,'i6ODtDM7qDkrN5cp6ancoMd3E46JtZ27i8VOqgAwaQpttWtYVvDWjU5WHmcQKDiw',1614045413550,1614131813550),
	(162,10016,'8mFTDWLiSTpOGUajpp6yJx9oBAXd4KO6n8QEIcxQ9IXSqlo7atXAKMkD0RquvHYB',1614049617962,1614136017962),
	(163,10015,'MtszGIF45AZZQNO1wK1fQKw0hIxEAErXEeXi9uCMYSld6UCE3VrWA3rdV4DskKS8',1614049700010,1614136100010),
	(164,10016,'AxCNWzUa5BZiPleIgExO1KDZBh9D0mXRiOuoUBkhKU1HpAVUuHpn0cRdDQKJiKyh',1614050068992,1614136468992),
	(165,10015,'lJoaIU2oyemFxZbvCiJKcZLZC9tOxiFf58Kcr3Ughw6cS5bMIwSjZCHwsaVu3Cyn',1614050246563,1614136646563),
	(166,10015,'ACvJLmhTP2h7eBgLTOmcQgmAJeSaL9WQYOqJRccME7R8tm3s6ARuD9ziTOcsg4Be',1614050976158,1614137376158),
	(167,10015,'d9DCQm0QuiJnY6YE5xv9xQ3p0bDiWCZeMDWMo63aF2PsmzcwwFft9AO2hJaCQ2vq',1614051014956,1614137414956),
	(168,10015,'kbMNKscRkURULN99JUnzE4IxJEYtockLfunGStYs3WSs6viuYzzuWeAg2mrZALoN',1614051043636,1614137443636),
	(169,10015,'TDYvpgzDyPJxoGj4OygNRGwZ1IQTXm0bsi6Jk29uKazKqzsmHHp5IQb3JclyThS8',1614051099029,1614137499029),
	(170,10015,'q0QdpmXqkCBdNCCCjfz7qvxPWyH31Di22f9tEtjUVTT6iaXD0LcCmWUGHgRX4Epl',1614051136933,1614137536933),
	(171,10015,'CUgoEczPfJrtnHnMlV1hy4CBit8kwJEWtdeMfARRTkiRRk4HmqKnCPfgwtKy8akh',1614077571830,1614163971830),
	(172,10015,'2Ij9RCmUoEDidMvd2hoq2MU7H6hsZJAAyzTQl0kjjWJgvJlOkTqCaXmOOifSF6BY',1614147032909,1614233432909),
	(173,10015,'j3AYhQqZr8L8ubgeABw0unjbCX0ZKffllXeXIHr29G64zTTL2aA8ohIPFy5nTI4y',1614147522378,1614233922378),
	(174,10015,'35UKJpSFVrlYiswUIe9IeoNIZ11AIkCq5sPYuEdjuWeGpBcR3i1a334TV8zNRLH3',1614147570418,1614233970418),
	(175,10015,'d7tubKESxmDZcF1773Cj1ndJSZfT9PjtlLrW7f2EPfdwJ8VsJzmPSQdqE6AwKZln',1614234029110,1614320429110),
	(176,10015,'CcFh6TRHpYfGIQKTI08lvTsQuAtNsPuhe5tFPEMCuWqYAtqQDyp7E828c6b2hBhc',1614320666020,1614407066020),
	(177,10015,'HbQozRs5PyulEKawecz3H92LjuLggQ7b6cURUlV272yJ6dAHM2SN6pVKCpg5UNYd',1614403496362,1614489896362),
	(178,10015,'em3E55AM4fote7nOakJXiHGow3m1EocWY9Ip8F6ebwT6PVYVZ9XDod1fsYCy8UVl',1614563879283,1614650279283),
	(179,10015,'Mq45gYTmp24AWNwoilWcwYUoHs65HBhl4BqjT6jbepHlS9Q8fGxKuvpHFow3Ku3I',1614564022845,1614650422845),
	(180,10015,'xLKlb1folpz3GIfgsAHEvffJjTySiIpAlIYlSCCio44b8LunsCiZvexrgA85lgJg',1614564602195,1614651002195);

/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `duration` bigint(20) NOT NULL COMMENT '优惠券持续时间(天)',
  `status` tinyint(2) NOT NULL COMMENT '优惠券状态',
  `created_at` bigint(20) NOT NULL COMMENT '优惠券发行时间',
  `payload` varchar(4096) NOT NULL COMMENT '优惠券类型以及详细分类等情况',
  `rule` varchar(1024) NOT NULL COMMENT '优惠额度以及规则',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `valid_thru` bigint(20) NOT NULL COMMENT '优惠券过期时间(us)',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券id',
  `status` tinyint(2) NOT NULL COMMENT '用户优惠券使用状态/待使用/已使用/过期',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `get_at` bigint(20) NOT NULL COMMENT '用户获得优惠券时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_ccoupon_id` (`user_id`,`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sec_kill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(10) NOT NULL COMMENT '商品id',
  `merchant_id` int(10) NOT NULL COMMENT '商户id',
  `start_at` bigint(20) NOT NULL COMMENT '开始时间',
  `end_at` bigint(20) Not NULL COMMENT '结束时间',
  `product_sno` varchar(20) NOT NULL COMMENT '商品规格',
  `sec_kill_price` int(10) NOT NULL COMMENT '秒杀价',
  `num` int(1) NOT NULL COMMENT '秒杀商品数量',
  `status` tinyint(1) DEFAULT '2' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_id_product_sno` (`product_id`,`product_sno`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

alter table `sec_kill` drop column `product_sno`;
alter table `sec_kill` drop column `sec_kill_price`;
alter table `sec_kill` drop column `num`;
alter table `sec_kill` add column `sec_kill_spec` text not null;
alter table `sec_kill` add column `title` varchar(30) not null;

/*
3月6日 16：28 张卫正修改表结构
 */
alter table `scene` change column `sub_heading` `sub_heading` varchar(140) not null;
alter table `product` change column `content` `content` text not null;
/*
3月8日 13:50 张卫正cart表添加字段
 */
alter table `cart` add column `cart_payload` varchar(4096);
