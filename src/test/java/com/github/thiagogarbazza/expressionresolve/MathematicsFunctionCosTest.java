package com.github.thiagogarbazza.expressionresolve;

import com.github.thiagogarbazza.expressionresolve.domain.Expression;
import org.junit.Test;

import java.math.BigDecimal;

public class MathematicsFunctionCosTest extends AbstractFunctionsTest {

  @Test
  public void testMathematicsCosBy5() {
    final Expression expression = new Expression("cos(90)");
    assertExpression(expression, RESULT_COS_90);
  }

  @Test
  public void testMathematicsCosByIdentifier() {
    EXPRESSION_CONTEXT.set("X", BigDecimal.valueOf(90));
    final Expression expression = new Expression("cos(X)");
    assertExpression(expression, RESULT_COS_90);
  }

  @Test
  public void testMathematicsCosByIdentifierNotInstanceValid() {
    EXPRESSION_CONTEXT.set("Z", "InstanceInvalid");
    final Expression expression = new Expression("cos(Z)");
    assertException(expression, "Variable 'Z' not instance class java.math.BigDecimal.");
  }

  @Test
  public void testMathematicsCosByIdentifierNotPresentInContext() {
    final Expression expression = new Expression("cos(a)");
    assertException(expression, "Variable 'a' not present in context.");
  }

  @Test
  public void testMathematicsCosNoArguments() {
    final Expression expression = new Expression("cos()");
    assertException(expression, "In position 1:4 no viable alternative at input 'cos()'.");
  }

  @Test
  public void testMathematicsCosNoParentheses() {
    final Expression expression = new Expression("cos");
    assertException(expression, "In position 1:3 no viable alternative at input 'cos'.");
  }
}
