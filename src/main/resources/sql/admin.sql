-- auto-generated definition
create table sys_user
(
    id         bigint unsigned auto_increment comment '用户ID'
        primary key,
    login_name varchar(36)  not null default '' comment '登录名',
    nick_name  varchar(40)  not null default '' comment '昵称',
    icon       varchar(500) not null default '' comment '头像',
    password   varchar(40)  not null default '' comment '密码',
    salt       varchar(40)  not null default '' comment 'shiro加密盐',
    phone      varchar(11)  not null default '' comment '手机号码',
    email      varchar(200) not null default '' comment '邮箱地址',
    locked     tinyint      not null default 0 comment '是否锁定',
    service_id int          not null default 0 comment '租户id',
    ctime      datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    mtime      datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    create_by  bigint       not null default 0 comment '被谁创建',
    update_by  bigint       not null default 0 comment '被谁更新',
    del_flag   tinyint      not null default 0 comment '是否删除 0-否, 1-是',
    remarks    varchar(255) not null default '' comment '备注',
    index uk_service_name (service_id, nick_name) comment '昵称搜索',
    unique index uk_phone_service (phone) comment 'uk-手机登陆',
    unique index idx_login_name (login_name) comment 'uk-登陆名称'
) comment '用户表';

-- auto-generated definition
create table sys_menu
(
    id          bigint unsigned auto_increment
        primary key,
    name        varchar(40)   null comment '菜单名称',
    parent_id   bigint        null comment '父菜单',
    level       bigint        null comment '菜单层级',
    parent_ids  varchar(2000) null comment '父菜单联集',
    sort        smallint      null comment '排序',
    href        varchar(2000) null comment '链接地址',
    target      varchar(20)   null comment '打开方式',
    icon        varchar(100)  null comment '菜单图标',
    bg_color    varchar(255)  null comment '显示背景色',
    is_show     tinyint       null comment '是否显示',
    permission  varchar(200)  null comment '权限标识',
    create_by   bigint        null,
    update_by   bigint        null,
    ctime      datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    mtime      datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    remarks     varchar(255)  null,
    del_flag    tinyint       null
) comment '菜单表';

-- auto-generated definition
create table sys_role
(
    id          bigint unsigned auto_increment
        primary key,
    name        varchar(40)  null comment '角色名称',
    create_by   bigint       null,
    ctime      datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    mtime      datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    update_by   bigint       null,
    remarks     varchar(255) null,
    del_flag    tinyint      null
) comment '角色表';

-- auto-generated definition
create table sys_role_menu
(
    role_id bigint unsigned not null default 0,
    menu_id bigint unsigned not null default 0,
    primary key (role_id, menu_id)
) comment '角色对应对应关系表';

-- auto-generated definition
create table sys_user_role
(
    user_id bigint unsigned not null default 0,
    role_id bigint unsigned not null default 0,
    primary key (user_id, role_id)
) comment '用户对应角色表';








