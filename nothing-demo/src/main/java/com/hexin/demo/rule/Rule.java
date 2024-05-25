package com.hexin.demo.rule;

import lombok.Data;

import java.util.Date;

@Data
public class Rule {

    private Long id;

    private String businessType;

    private String expression;

    private Integer priority;

    private Date startDate;

    private Date endDate;

    private boolean isActive;

}