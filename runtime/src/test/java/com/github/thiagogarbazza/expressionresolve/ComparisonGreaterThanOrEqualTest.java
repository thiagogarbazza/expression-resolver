package com.github.thiagogarbazza.expressionresolve;

import java.math.BigDecimal;

import org.junit.Test;

import com.github.thiagogarbazza.expressionresolve.domain.Expression;

public class ComparisonGreaterThanOrEqualTest extends AbstractFunctionsTest {

    @Test
    public void testGreaterThanValue() {
        final Expression expression = new Expression("1>=0");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testEqualsValue() {
        final Expression expression = new Expression("1>=1");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testGreaterThanIdentifiers() {
        EXPRESSION_CONTEXT.set("A", BigDecimal.TEN);
        EXPRESSION_CONTEXT.set("B", BigDecimal.ONE);
        final Expression expression = new Expression("A>=B");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testEqualIdentifiers() {
        EXPRESSION_CONTEXT.set("A", BigDecimal.TEN);
        EXPRESSION_CONTEXT.set("B", BigDecimal.TEN);
        final Expression expression = new Expression("A>=B");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testGreaterThanUseFunctions() {
        final Expression expression = new Expression("12>=month(2015/03/20)");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testEqualUseFunctions() {
        final Expression expression = new Expression("20>=day(2015/03/20)");
        assertExpression(expression, TRUE);
    }

    @Test
    public void testNonGreaterThanOrEqual() {
        final Expression expression = new Expression("0>=1");
        assertExpression(expression, FALSE);
    }

    @Test
    public void testNonGreaterThanOrEqualIdentifiers() {
        EXPRESSION_CONTEXT.set("A", BigDecimal.ONE);
        EXPRESSION_CONTEXT.set("B", BigDecimal.TEN);
        final Expression expression = new Expression("A>=B");
        assertExpression(expression, FALSE);
    }

    @Test
    public void testNonGreaterThanOrEqualUseFunctions() {
        final Expression expression = new Expression("12>=day(2015/03/20)");
        assertExpression(expression, FALSE);
    }
}