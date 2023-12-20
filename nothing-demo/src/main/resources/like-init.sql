CREATE TABLE `t_like_info`
(
    `id`               bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `like_user_id`     bigint                                                       NOT NULL COMMENT '点赞的用户id',
    `liked_user_id`    bigint                                                       NOT NULL COMMENT '被点赞的用户id',
    `liked_subject_id` bigint                                                       NOT NULL COMMENT '点赞类型id',
    `liked_type`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点赞类型',
    `operation_time`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '-1' COMMENT '操作时间',
    `create_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_time`      datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
    `update_time`      datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_index` (`like_user_id`, `liked_user_id`, `liked_subject_id`, `liked_type`,
                             `operation_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 91
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='点赞表';

CREATE TABLE `t_like_count`
(
    `id`               bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `liked_user_id`    bigint                                                       NOT NULL COMMENT '被点赞的用户id',
    `liked_subject_id` bigint                                                       NOT NULL COMMENT '点赞类型id',
    `liked_type`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点赞类型',
    `like_total`       bigint                                                                DEFAULT '0' COMMENT '点赞总数',
    `del_flag`         char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL DEFAULT '0' COMMENT '删除0 正常 1 删除',
    `create_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_time`      datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
    `update_time`      datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_index` (`liked_user_id`, `liked_subject_id`, `liked_type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='点赞统计表';