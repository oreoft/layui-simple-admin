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
INSERT INTO sys_user (id, login_name, nick_name, icon, password, salt, phone, email, locked, service_id, ctime,mtime, create_by, update_by, del_flag, remarks)VALUES (1, 'root', '超级管理员', '', '776c76360a772d4b2c19bdcd6dedecf2d21d6837', '87b50c42c475619e', '18888888888','root@someget.cn', 0, 1, '2022-05-02 19:39:10', '2022-05-02 19:39:21', 1, 1, 0, '');


-- auto-generated definition
create table sys_menu
(
    id         bigint unsigned auto_increment
        primary key,
    name       varchar(40)   not null default '' comment '菜单名称',
    parent_id  bigint        not null default 0 comment '父菜单',
    level      bigint        not null default 0 comment '菜单层级',
    parent_ids varchar(2000) not null default '' comment '父菜单联集',
    sort       smallint      not null default 0 comment '排序',
    href       varchar(2000) not null default '' comment '链接地址',
    target     varchar(20)   not null default '' comment '打开方式',
    icon       varchar(100)  not null default '' comment '菜单图标',
    bg_color   varchar(255)  not null default '' comment '显示背景色',
    is_show    tinyint       not null default 0 comment '是否显示',
    permission varchar(200)  not null default '' comment '权限标识',
    create_by  bigint        not null default 0,
    update_by  bigint        not null default 0,
    ctime      datetime      not null default CURRENT_TIMESTAMP comment '创建时间',
    mtime      datetime      not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    remarks    varchar(255)  not null default '',
    del_flag   tinyint       not null default 0
) comment '菜单表';
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (1, '系统管理', 0, 1, '1,', 10, '', '', '', '', 1, '', 1, 1, '2022-05-02 19:17:32', '2022-05-02 19:42:03', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (2, '系统用户管理', 1, 2, '1,2,', 200, '/admin/system/user/list', '', '', ' ', 1, 'sys:user:list', 1, 1, '2022-05-02 19:40:14', '2022-05-02 19:42:03', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (3, '系统角色管理', 1, 2, '1,3,', 10, '/admin/system/role/list', '', '', ' ', 1, 'sys:role:list', 1, 1, '2022-05-02 19:40:15', '2022-05-02 19:42:03', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (4, '系统权限管理', 1, 2, '1,4,', 20, '/admin/system/menu/list', '', '', ' ', 1, 'sys:menu:list', 1, 1, '2022-05-02 19:40:15', '2022-05-02 19:42:03', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (5, '新增系统权限', 4, 3, '1,4,20,', 0, '', '', '', '', 0, 'sys:menu:add', 1, 1, '2022-05-02 19:17:32', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (6, '编辑系统权限', 4, 3, '1,4,21,', 10, '', '', '', '', 0, 'sys:menu:edit', 1, 1, '2022-05-02 19:17:32', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (7, '删除系统权限', 4, 3, '1,4,22,', 20, '', '', '', '', 0, 'sys:menu:delete', 1, 1, '2022-05-02 19:17:32', '2022-05-02 19:42:03', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (8, '删除系统资源', 5, 3, '1,5,23,', 0, '', '', '', '', 0, 'sys:rescource:delete', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 1);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (9, '新增系统角色', 3, 3, '1,3,24,', 0, '', '', '', '', 0, 'sys:role:add', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (10, '编辑菜单权限', 3, 3, '1,3,25,', 10, '', '', '', '', 0, 'sys:role:edit', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (11, '删除角色', 3, 3, '1,3,26,', 20, '', '', '', '', 0, 'sys:role:delete', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (12, '新增系统用户', 2, 3, '1,2,35,', 0, '', '', '', '', 0, 'sys:user:add', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (13, '编辑系统用户', 2, 3, '1,2,36,', 10, '', '', '', '', 0, 'sys:user:edit', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (14, '删除系统用户', 2, 3, '1,2,37,', 20, '', '', '', '', 0, 'sys:user:delete', 1, 1, '2022-05-02 19:17:31', '2022-05-02 19:42:02', ' ', 0);
INSERT INTO sys_menu (id, name, parent_id, level, parent_ids, sort, href, target, icon, bg_color, is_show, permission, create_by, update_by, ctime, mtime, remarks, del_flag) VALUES (15, '修改密码', 2, 3, '1,2,53,', 30, '', '', '', '', 0, 'sys:user:changePassword', 1, 1, '2022-05-02 19:17:32', '2022-05-02 19:42:03', ' ', 0);

-- auto-generated definition
create table sys_role
(
    id        bigint unsigned auto_increment
        primary key,
    name      varchar(40)  null comment '角色名称',
    create_by bigint       null,
    ctime     datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    mtime     datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间',
    update_by bigint       null,
    remarks   varchar(255) null,
    del_flag  tinyint      null
) comment '角色表';
INSERT INTO sys_role (id, name, ctime, create_by, mtime, update_by, remarks, del_flag) VALUES (1, '超级管理员', '2017-11-02 14:19:07', 1, '2022-04-16 14:11:04', 1, '最高权限管理员', 0);


-- auto-generated definition
create table sys_role_menu
(
    role_id bigint unsigned not null default 0,
    menu_id bigint unsigned not null default 0,
    primary key (role_id, menu_id)
) comment '角色对应对应关系表';
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 1);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 2);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 3);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 4);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 5);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 6);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 7);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 8);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 9);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 10);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 11);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 12);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 13);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 14);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 15);


-- auto-generated definition
create table sys_user_role
(
    user_id bigint unsigned not null default 0,
    role_id bigint unsigned not null default 0,
    primary key (user_id, role_id)
) comment '用户对应角色表';
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);






