package com.github.thiagogarbazza.expressionresolver.resolver.comparedate;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.thiagogarbazza.expressionresolver.resolver.NormalizeResult.toBigDecimal;
import static com.github.thiagogarbazza.expressionresolver.resolver.comparedate.ResolverFunctionCompareDate.getResolverFunctionCompareDate;
import static org.junit.Assert.assertEquals;

public class ResolverFunctionCompareDateTest {

  private ResolverFunctionCompareDate resolverFunctionCompareDate;

  @Before
  public void before() {
    resolverFunctionCompareDate = getResolverFunctionCompareDate();
  }

  @Test
  public void verifyResolverEquals() {
    LocalDate left = LocalDate.of(2000, 1, 1);
    LocalDate right = LocalDate.of(2000, 1, 1);

    assertEquals(toBigDecimal("0"), resolverFunctionCompareDate.resolver(left, right));
  }

  @Test
  public void verifyResolverGreater() {
    LocalDate left = LocalDate.of(2020, 1, 1);
    LocalDate right = LocalDate.of(2000, 1, 1);

    assertEquals(toBigDecimal("1"), resolverFunctionCompareDate.resolver(left, right));
  }

  @Test
  public void verifyResolverLess() {
    LocalDate left = LocalDate.of(2000, 1, 1);
    LocalDate right = LocalDate.of(2020, 1, 1);

    assertEquals(toBigDecimal("-1"), resolverFunctionCompareDate.resolver(left, right));
  }
}
