package com.github.thiagogarbazza.expressionresolver.resolver.day;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.thiagogarbazza.expressionresolver.resolver.NormalizeResult.toBigDecimal;
import static com.github.thiagogarbazza.expressionresolver.resolver.day.ResolverFunctionDay.getResolverFunctionDay;
import static org.junit.Assert.assertEquals;

public class ResolverFunctionDayTest {

  private ResolverFunctionDay resolverFunctionDay;

  @Before
  public void before() {
    resolverFunctionDay = getResolverFunctionDay();
  }

  @Test
  public void verifyResolver() {
    assertEquals(toBigDecimal("31"), resolverFunctionDay.resolver(LocalDate.of(2018, 1, 31)));
  }
}
