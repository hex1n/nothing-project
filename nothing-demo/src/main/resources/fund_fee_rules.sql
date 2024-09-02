CREATE TABLE fund_fee_rules
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    fee_rules_id         BIGINT         not null COMMENT '费用规规则id',
    fund_code            VARCHAR(10)    NOT NULL COMMENT '万得代码win',
    trade_type           VARCHAR(10)    NOT NULL COMMENT 'purchase,redemption',
    condition_expression VARCHAR(255)   NOT NULL, -- 条件表达式
    fee_type             VARCHAR(10)    not null COMMENT '费用类型 rate:费率 fixed:固定金额',
    fee_value            DECIMAL(10, 2) NOT NULL,
    discount_fee_value   DECIMAL(10, 2) DEFAULT NULL
);