package com.github.thiagogarbazza.expressionresolve;

import com.github.thiagogarbazza.expressionresolve.domain.Expression;
import org.junit.Test;

public class MathematicsOperationDivideTest extends AbstractFunctionsTest {

  @Test
  public void testOperationDivideSingle() {
    final Expression expression = new Expression("25 / 5");
    assertExpression(expression, RESULT_5);
  }
}
