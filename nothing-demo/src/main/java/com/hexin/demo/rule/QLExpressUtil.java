package com.hexin.demo.rule;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.InstructionSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hex1n
 * @Date 2024/5/25/21:54
 * @Description
 **/
@Component
@Setter
@Getter
public class QLExpressUtil {

    @Autowired
    private ExpressRunner expressRunner;
    private final Map<String, InstructionSet> compiledExpressions = new ConcurrentHashMap<>();

    public String execute(String expression, Map<String, Object> params) {
        try {
            InstructionSet compiledExpr = compiledExpressions.computeIfAbsent(expression, key -> {
                try {
                    return expressRunner.parseInstructionSet(key);
                } catch (Exception e) {
                    return null;
                }
            });
            if (compiledExpr != null) {
                ExpressContext context = new ExpressContext(params);
                return expressRunner.execute(compiledExpr, context, null, true, false).toString();
            } else {
                return "Error compiling expression: " + expression;
            }
        } catch (Exception e) {
            return "Error executing rule: " + e.getMessage();
        }
    }
}
