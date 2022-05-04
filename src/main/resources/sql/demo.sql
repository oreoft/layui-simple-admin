-- auto-generated definition
create table t_material
(
    id                int auto_increment comment 'ok'
        primary key,
    name              varchar(50)  default ''                not null comment '物品名称',
    num               int          default 0                 not null comment '数量',
    unit              varchar(10)  default ''                not null comment '单位',
    service_id        int          default 0                 not null comment '租户id',
    light_warning_num int          default 0                 not null comment '黄色预警值',
    heavy_warning_num int          default 0                 not null comment '红色预警值',
    ctime             datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    mtime             datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint uk
        unique (name, unit, service_id)
);

create index idx_service
    on t_material (service_id)
    comment '服务商索引';

