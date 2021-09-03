CREATE TABLE `t_like_info`
(
    `id`              varchar(32) NOT NULL COMMENT 'id',
    `liked_member_id` varchar(32) NOT NULL COMMENT '被点赞的用户id',
    `liked_post_id`   varchar(32) NOT NULL COMMENT '点赞的用户id',
    `liked_type_id`   varchar(32) NOT NULL COMMENT '点赞类型id',
    `liked_type`      varchar(32) NOT NULL COMMENT '点赞类型',
    `liked_status`    char(2)              DEFAULT NULL COMMENT '点赞状态 -1取消，1点赞',
    `operation_time`  varchar(32) NOT NULL DEFAULT '-1' COMMENT '操作时间',
    `del_flag`        char(1)     NOT NULL DEFAULT '0' COMMENT '删除0 正常 1 删除',
    `create_by`       varchar(32) NOT NULL COMMENT '创建人',
    `create_time`     datetime    NOT NULL COMMENT '创建时间',
    `update_by`       varchar(32) NOT NULL COMMENT '更新人',
    `update_time`     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY               `liked_member_id` (`liked_member_id`) USING BTREE,
    KEY               `liked_post_id` (`liked_post_id`) USING BTREE,
    KEY               `liked_type_id` (`liked_type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';


CREATE TABLE `t_like_count`
(
    `id`              varchar(32) NOT NULL COMMENT 'id',
    `liked_member_id` varchar(32) NOT NULL COMMENT '被点赞的用户id',
    `liked_type_id`   varchar(32) NOT NULL COMMENT '点赞类型id',
    `liked_type`      varchar(32) NOT NULL COMMENT '点赞类型',
    `like_total`      int(11) DEFAULT '0' COMMENT '点赞总数',
    `del_flag`        char(2)     NOT NULL DEFAULT '0' COMMENT '删除0 正常 1 删除',
    `create_by`       varchar(32) NOT NULL COMMENT '创建人',
    `create_time`     datetime    NOT NULL COMMENT '创建时间',
    `update_by`       varchar(32) NOT NULL COMMENT '更新人',
    `update_time`     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_member_id_type_id_type` (`liked_member_id`,`liked_type_id`,`liked_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞统计表';