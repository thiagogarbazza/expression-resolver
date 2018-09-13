package com.github.thiagogarbazza.expressionresolve;

import java.math.BigDecimal;

import org.junit.Test;

import com.github.thiagogarbazza.expressionresolve.domain.Expression;

public class ComparisonLessThanTest extends AbstractFunctionsTest {

    @Test
    public void testLessThanValue() {
        final Expression expression = new Expression("0<1");
        assertExpression(expression, RESULT_TRUE);
    }

    @Test
    public void testLessThanIdentifiers() {
        EXPRESSION_CONTEXT.set("A", BigDecimal.ONE);
        EXPRESSION_CONTEXT.set("B", BigDecimal.TEN);
        final Expression expression = new Expression("A<B");
        assertExpression(expression, RESULT_TRUE);
    }

    @Test
    public void testLessThanUseFunctions() {
        final Expression expression = new Expression("12<day(2015/03/20)");
        assertExpression(expression, RESULT_TRUE);
    }

    @Test
    public void testNonLessThan() {
        final Expression expression = new Expression("1<0");
        assertExpression(expression, RESULT_FALSE);
    }

    @Test
    public void testNonLessThanIdentifiers() {
        EXPRESSION_CONTEXT.set("A", BigDecimal.TEN);
        EXPRESSION_CONTEXT.set("B", BigDecimal.TEN);
        final Expression expression = new Expression("A<B");
        assertExpression(expression, RESULT_FALSE);
    }

    @Test
    public void testNonLessThanUseFunctions() {
        final Expression expression = new Expression("20<month(2015/03/20)");
        assertExpression(expression, RESULT_FALSE);
    }
}