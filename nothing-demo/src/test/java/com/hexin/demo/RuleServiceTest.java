package com.hexin.demo;


import com.hexin.demo.rule.Rule;
import com.hexin.demo.rule.service.RuleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RuleServiceTest {

    @Resource
    private RuleService ruleService;

    @Test
    public void testExecuteRule() {
        Rule rule = new Rule();
        rule.setBusinessType("complexBusinessType");
        rule.setExpression("if (param1 > 10) { return 'param1 is greater than 10'; } else { return 'param1 is not greater than 10'; }");
        rule.setPriority(1);

        Map<String, Object> params = new HashMap<>();
        params.put("param1", 16);

        String result = ruleService.executeRule("complexBusinessType", params);
        System.out.println(result);
        //assertEquals("param1 is greater than 10", result);
    }
}