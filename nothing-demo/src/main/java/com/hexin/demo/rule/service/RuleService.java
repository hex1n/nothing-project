package com.hexin.demo.rule.service;

import cn.hutool.core.collection.CollUtil;
import com.hexin.demo.mapper.TRuleMapper;
import com.hexin.demo.rule.QLExpressUtil;
import com.hexin.demo.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author hex1n
 * @Date 2024/5/26/00:23
 * @Description
 **/
@Service
public class RuleService {
    @Autowired
    private QLExpressUtil qlExpressUtil;

    @Autowired
    private TRuleMapper tRuleMapper;

    private final Map<String, List<Rule>> ruleCache = new ConcurrentHashMap<>();

    @Async
    public CompletableFuture<String> executeRuleAsync(String businessType, Map<String, Object> params) {
        return CompletableFuture.supplyAsync(() -> executeRule(businessType, params));
    }


    public String executeRule(String businessType, Map<String, Object> params) {
        List<Rule> rules = getRulesFromCache(businessType);
        return rules.stream()
                .map(rule -> executeSingleRule(rule, params))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("No applicable rule found for business type: " + businessType);
    }

    public List<String> executeAllRules(String businessType, Map<String, Object> params) {
        List<Rule> rules = getRulesFromCache(businessType);
        return rules.stream()
                .map(rule -> executeSingleRule(rule, params))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Rule> getRulesFromCache(String businessType) {
        List<Rule> rules = ruleCache.computeIfAbsent(businessType, key -> tRuleMapper.findByBusinessType(businessType));
        return CollUtil.isEmpty(rules) ? Collections.emptyList() : rules;
    }

    private String executeSingleRule(Rule rule, Map<String, Object> params) {
        try {
            return qlExpressUtil.execute(rule.getExpression(), params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
