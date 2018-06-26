
DROP TABLE IF EXISTS t_attach;
CREATE TABLE t_attach (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	fname VARCHAR (100) NOT NULL,
	ftype VARCHAR (50),
	fkey VARCHAR (100) NOT NULL,
	author_id INTEGER (10) NOT NULL,
	created INTEGER (10) NOT NULL);

-- 表：t_comments
DROP TABLE IF EXISTS t_comments;
CREATE TABLE t_comments (
	coid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	cid INTEGER DEFAULT (0) NOT NULL,
	created INTEGER (10) NOT NULL,
	author VARCHAR (200) NOT NULL,
	author_id INTEGER (10) DEFAULT (0),
	owner_id INTEGER (10) DEFAULT (0),
	mail VARCHAR (200) NOT NULL,
	url VARCHAR (200),
	ip VARCHAR (64),
	agent VARCHAR (200),
	content TEXT NOT NULL,
	type VARCHAR (16),
	status VARCHAR (16),
	parent INTEGER (10) DEFAULT (0));


INSERT INTO `t_comments` VALUES ('1', '2', '1529466671', '订单', '0', '1', '23@qq.com', '', '0:0:0:0:0:0:0:1', null, '的点点滴滴', 'comment', 'approved', '0');


-- 表：t_contents
DROP TABLE IF EXISTS t_contents;

CREATE TABLE t_contents (
	cid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
	title VARCHAR (255) NOT NULL,
	slug VARCHAR (255) CONSTRAINT idx_u_slug UNIQUE,
	--thumb_img VARCHAR(255),
	created INTEGER (10) NOT NULL,
	modified INTEGER (10),
	content TEXT,
	author_id INTEGER (10) NOT NULL,
	type VARCHAR (16) NOT NULL,
	status VARCHAR (16) NOT NULL,
	--fmt_type VARCHAR (16) DEFAULT ('markdown'),
	tags VARCHAR (200),
	categories VARCHAR (200),
	hits INTEGER (10) DEFAULT (0),
	comments_num INTEGER (1) DEFAULT (0),
	allow_comment INTEGER (1) DEFAULT (1),
	allow_ping INTEGER (1),
	allow_feed INTEGER (1)
	);


INSERT INTO `t_contents` VALUES ('1', 'about my blog', 'about', '1487853610', '1487872488', '### Hello World\r\n\r\nabout me\r\n\r\n### ...\r\n\r\n...', '1', 'page', 'publish', null, null, '0', '0', '1', '1', '1'), ('2', 'Hello My Blog', null, '1487861184', '1487872798', '## Hello  World.\r\n\r\n> ...\r\n\r\n----------\r\n\r\n\r\n<!--more-->\r\n\r\n```java\r\npublic static void main(String[] args){\r\n    System.out.println(\"Hello 13 Blog.\");\r\n}\r\n```', '1', 'post', 'publish', '', 'default', '10', '1', '1', '1', '1');


-- 表：t_logs
DROP TABLE IF EXISTS t_logs;
CREATE TABLE t_logs (
	id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	"action" VARCHAR (100) NOT NULL,
	data VARCHAR (2000),
	author_id INTEGER (10) NOT NULL,
	ip VARCHAR (20),
	created INTEGER (10) NOT NULL);


INSERT INTO `t_logs` VALUES ('1', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1529458167'), ('2', '登录后台', null, '1', '0:0:0:0:0:0:0:1', '1529465999');


-- 表：t_metas
DROP TABLE IF EXISTS t_metas;
CREATE TABLE t_metas (
	mid INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	name VARCHAR (200) NOT NULL,
	slug VARCHAR (200),
	type VARCHAR (32) NOT NULL,
	description VARCHAR (255),
	sort INTEGER (4) DEFAULT (0),
	parent INTEGER (10) DEFAULT (0));


INSERT INTO `t_metas` VALUES ('1', 'default', null, 'category', null, '0', '0'), ('6', 'my github', 'https://github.com/ZHENFENG13', 'link', null, '0', '0');

-- 表：t_options
DROP TABLE IF EXISTS t_options;
CREATE TABLE t_options (
	name VARCHAR (100) PRIMARY KEY UNIQUE NOT NULL,
	value TEXT,
	description VARCHAR (255));


INSERT INTO `t_options` VALUES ('site_description', '13 Blog', null), ('site_keywords', '13 Blog', null), ('site_record', '', '备案号'), ('site_theme', 'default', null), ('site_title', 'My Blog', ''), ('social_github', '', null), ('social_twitter', '', null), ('social_weibo', '', null), ('social_zhihu', '', null);

-- 表：t_relationships
DROP TABLE IF EXISTS t_relationships;
CREATE TABLE t_relationships (
	cid INTEGER (10) NOT NULL,
	mid INTEGER (10) NOT NULL);



INSERT INTO `t_relationships` VALUES ('2', '1');



-- 表：t_users
DROP TABLE IF EXISTS t_users;
CREATE TABLE t_users (
	uid INTEGER PRIMARY KEY UNIQUE NOT NULL,
	username VARCHAR (64) UNIQUE NOT NULL,
	password VARCHAR (64) NOT NULL,
	email VARCHAR (100),
	home_url VARCHAR (255),
	screen_name VARCHAR (100),
	created INTEGER (10) NOT NULL,
	activated INTEGER (10),
	logged INTEGER (10),
	group_name VARCHAR (16));


INSERT INTO `t_users` VALUES ('1', 'admin', 'a66abb5684c45962d887564f08346e8d', '1034683568@qq.com', null, 'admin', '1490756162', '0', '0', 'visitor');


