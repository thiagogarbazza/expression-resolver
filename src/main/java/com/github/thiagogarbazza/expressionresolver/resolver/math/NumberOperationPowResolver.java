package com.github.thiagogarbazza.expressionresolver.resolver.math;

import com.github.thiagogarbazza.expressionresolver.util.NumberUtil;
import com.github.thiagogarbazza.expressionresolver.util.dependencyinjection.Inject;
import com.github.thiagogarbazza.expressionresolver.util.dependencyinjection.Service;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Service
@NoArgsConstructor(access = PRIVATE, onConstructor = @__(@Inject))
public class NumberOperationPowResolver {

  public BigDecimal resolver(final BigDecimal left, final BigDecimal right) {
    final BigDecimal result = left.pow(right.intValue());

    return NumberUtil.toBigDecimal(result);
  }
}
