package com.github.hotire.reactor.utils.cache;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

class ReactiveCacheAspectTest {

    @Disabled
    @Test
    void spel() {
        final String message = "my foo is #name, i bar #age";
        ExpressionParser parser = new SpelExpressionParser();
        Map<String, String> map = new HashMap<>();
        map.put("name", "hotire");
        map.put("age", "1");
        StandardEvaluationContext context = new StandardEvaluationContext(map);
//        context.addPropertyAccessor(new MapAccessor());
        context.setVariable("name", "hotire");
        context.setVariable("age", "1");
//        Expression expression = parser.parseExpression(message, ParserContext.TEMPLATE_EXPRESSION);
//        String value = expression.getValue(context, String.class);
        String result = parser.parseExpression(message).getValue(context, String.class);
        System.out.println(result);
    }

    @Disabled
    @Test
    void parse() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("my foo is #name");

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "hotire");
        String result = exp.getValue(context, String.class);
        System.out.println(result);
    }
}