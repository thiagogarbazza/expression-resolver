package com.github.thiagogarbazza.expressionresolver.resolver.month;

import com.github.thiagogarbazza.expressionresolver.util.NumberUtil;
import com.github.thiagogarbazza.expressionresolver.util.dependencyinjection.Inject;
import com.github.thiagogarbazza.expressionresolver.util.dependencyinjection.Service;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Service
@NoArgsConstructor(access = PRIVATE, onConstructor = @__(@Inject))
public class FunctionMonthResolver {

  public BigDecimal resolver(final LocalDate value) {
    final int day = value.getMonthValue();

    return NumberUtil.toBigDecimal(day);
  }
}
