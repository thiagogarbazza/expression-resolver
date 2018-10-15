package com.github.thiagogarbazza.expressionresolver.it.core;

import com.github.thiagogarbazza.expressionresolver.domain.Expression;
import com.github.thiagogarbazza.expressionresolver.domain.Result;
import com.github.thiagogarbazza.expressionresolver.it.AbstractIT;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class SingleValueJsonIT extends AbstractIT {

  @Test
  public void verifySingleValueJson() {
    final Map<String, Object> resultExpected = new TreeMap<>();
    resultExpected.put("any-boolean", true);
    resultExpected.put("any-date", LocalDate.of(2018, 2, 28));
    resultExpected.put("any-number", new BigDecimal("13"));
    resultExpected.put("any-string", "any-string-value");
    resultExpected.put("any-json", new TreeMap<String, Object>());

    assertExpression(
      new Expression("return { 'any-boolean': true, 'any-date': 2018/02/28, 'any-number': 13, 'any-string': 'any-string-value', 'any-json': { } };"),
      new Result(resultExpected));
  }

  @Test
  public void verifySingleValueJsonEmpty() {
    assertExpression(new Expression("return { };"), new Result(new TreeMap<String, Object>()));
  }
}
