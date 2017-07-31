exec sp_addrolemember 'db_owner', 'max'

/**
 * 经营管理信息
 */
create table manageInfo(
	pk_id bigint IDENTITY(1,1) primary key,		-- 主键
	manageTitle varchar(200),		-- 活动id
	content  text ,					-- 图片的描述信息TEXT
	createAt datetime not null,		-- 上传时间
	createBy varchar(200),			-- 上传的人
	flage int						-- 设置标志位 预留后面的提示标志
)


alter table ruleFile add flage_new int default 0;
alter table notice add flage_new int default 0;
alter table activity_title add flage_new int default 0

/**
 * 删除staffInfo表中的departmentID
 */
ALTER TABLE staffInfo DROP COLUMN departmentID;
/**
 * 新增departmenCode字段存储部门的编号唯一
 */
ALTER TABLE staffInfo Add departmentCode varchar(200);
/**
 * 新增状态字段 默认初始值为1：在职， 0 ：离职
 */
ALTER TABLE staffInfo add stat int default 1
/**
 * 通讯录的照片路径
 */
ALTER TABLE staffInfo Add headImg varchar(200);

/**
 公告表
**/
create table notice(            
	pk_id bigint primary key,		--主键
	title nvarchar(255) not null,	--标题
	title_img varchar(200),			--图片的路径
	content ntext not null,			--正文
	noticeTime datetime,			--公告的时间
	sortTime dateTime,				--置顶排序时间
	updateTime dateTime				-- 更新公告的时间
);

/**
 # 活动表
**/
create table tour(
	pk_id bigint primary key,		-- 主键
	title varchar(200) not null,	-- 标题
	content ntext not null,			-- 正文
	tourTime datetime,				-- 公告的时间
	sortTime dateTime,				-- 置顶排序时间
	updateTime dateTime				-- 更新公告的时间
)
/**
 * 上传的图片的表
 */
create table tourImg(
pk_id bigint primary key,		--主键
tourid bigint not null,			--事件id
fileName varchar(200) not null,	--文件名
description ntext,				--图片的描述信息
updateAt datetime not null,		--上传时间
updateBy varchar(200)			-- 上传的人
)



/**
 * 上传规章制度
 */
create table ruleFile(
pk_id bigint primary key,		--主键
type int not null,				--事件id
fileName varchar(200) not null,	--文件名
fileAllName varchar(200) not null,	--文件名
createAt datetime not null,		--上传时间
createBy varchar(200)			-- 上传的人
)

-- 下载文件的文件模板
create table uploadFileModel(            
	pk_id bigint primary key,		--主键
	title nvarchar(255) not null,	--名称
	createAt datetime				--创建时间
);

-- 上传文件的所属模板的类型
create table uploadFileType(            
	pk_id bigint primary key,		--主键
	uploadFileModelID bigint,
	title nvarchar(255) not null,	--名称
	createAt datetime				--创建时间
);

-- 上传文件
create table uploadFile(            
	pk_id bigint primary key,		--主键
	modelID bigint not null,
	typeID bigint not null,	--名称
	fileName varchar(200) not null,	--文件名
	path varchar(200) not null,     --路径
	createBy varchar(200) not null,
	createAt datetime				--创建时间
);

-- 公司名称
create table company(            
	pk_id bigint primary key,		--主键
	company varchar(200) not null,	--文件名
	createBy varchar(200) not null,
	createAt datetime				--创建时间
);

-- 部门表
create table department(            
	pk_id bigint primary key,			--主键
	companyId bigint,					--公司id
	department varchar(200) not null,	--部门名称
	Code varchar(200) not null,			--部门编码
	createBy varchar(200) not null,
	createAt datetime					--创建时间
);


-- 员工表
create table staffInfo(            
	pk_id bigint primary key,			--主键
	departmentID bigint,				--公司id
	useName varchar(200) not null,		--部门名称
	gender int not null,				--性别
	telphone varchar(200) ,
	celphone varchar(200) ,
	email varchar(200) ,
	createBy varchar(200) not null,
	createAt datetime					--创建时间
);

--活动展示的标题和时间
create table activity_title(
	pk_id bigint primary key,		--主键
	title varchar(200) not null,	--文件名
	filePath varchar(200) ,				--图片的描述信息
	createAt datetime not null,		--上传时间
	createBy varchar(200)			-- 上传的人
)

-- 文章
create table article(
	pk_id bigint primary key,		--主键
	activityId varchar(200),		--活动id
	content  text ,			--图片的描述信息TEXT
	createAt datetime not null,		--上传时间
	createBy varchar(200)			-- 上传的人
)

-- 会议室
create table meet(
	pk_id bigint primary key,		--主键
	companyId bigint,				--公司id
	name varchar(200) ,				--会议室名称
	createAt datetime not null,		--上传时间
	createBy varchar(200)			-- 上传的人
)


create table bookMeet(
	pk_id bigint primary key,		--主键
	meetId bigint,					--公司id
	bookDate datetime,				-- 日期
	startTime varchar(20),      	--开始时间
	endTime varchar(20),			--结束时间
	assist varchar(20),				--辅助设备
	booker varchar(20),				--预定人
	remark text,					-- 备注
	createAt datetime not null,		--上传时间
	createBy varchar(200)			-- 上传的人
)

insert  into meet (pk_id,name,createBy,createAt) values(1,'火星','admin',getdate());
insert  into meet (pk_id,name,createBy,createAt) values(2,'天王星','admin',getdate());
insert  into meet (pk_id,name,createBy,createAt) values(3,'海王星','admin',getdate());
insert  into meet (pk_id,name,createBy,createAt) values(4,'冥王星','admin',getdate());
insert  into meet (pk_id,name,createBy,createAt) values(5,'火星','admin',getdate());
insert  into meet (pk_id,name,createBy,createAt) values(6,'水星','admin',getdate());


-- 用户表
create table t_use(            
	pk_id bigint primary key,			--主键
	log_name nvarchar(255) not null,	--标题
	user_name varchar(200),				--用户名
	gender varchar(200),				--性别
	tel varchar(200),					--电话号码
	mobliephone varchar(200),			--手机号
	email varchar(200),					--电子邮件
	stat varchar(200),					--状态
	password varchar(200),				--密码
	role varchar(200)					--角色
);




