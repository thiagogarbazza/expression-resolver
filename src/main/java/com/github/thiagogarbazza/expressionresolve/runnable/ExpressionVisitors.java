package com.github.thiagogarbazza.expressionresolve.runnable;

import com.github.thiagogarbazza.expressionresolve.domain.ExpressionContext;
import com.github.thiagogarbazza.expressionresolve.parser.ExpressionParser;
import com.github.thiagogarbazza.expressionresolve.parser.ExpressionParserBaseVisitor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.thiagogarbazza.expressionresolve.functionresolver.acos.FunctionResolverAcos.getFunctionResolverAcos;
import static com.github.thiagogarbazza.expressionresolve.functionresolver.asin.FunctionResolverAsin.getFunctionResolverAsin;
import static com.github.thiagogarbazza.expressionresolve.functionresolver.atan.FunctionResolverAtan.getFunctionResolverAtan;
import static com.github.thiagogarbazza.expressionresolve.functionresolver.sin.FunctionResolverSin.getFunctionResolverSin;
import static com.github.thiagogarbazza.expressionresolve.functionresolver.tan.FunctionResolverTan.getFunctionResolverTan;
import static com.github.thiagogarbazza.expressionresolve.util.LocalDateUtil.toLocalDate;
import static java.math.MathContext.DECIMAL128;
import static org.apache.commons.lang3.BooleanUtils.negate;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.StringUtils.EMPTY;

final class ExpressionVisitors extends ExpressionParserBaseVisitor<Object> {

  private final ExpressionContext executionContext;

  @Getter
  private Object valueToBeReturned;

  public ExpressionVisitors(final ExpressionContext expressionContext) {
    this.executionContext = expressionContext;
  }

  @Override
  public final Object visitParse(final ExpressionParser.ParseContext ctx) {
    return visit(ctx.block());
  }

  @Override
  public final Object visitAssignment(final ExpressionParser.AssignmentContext ctx) {
    final String variable = ctx.IDENTIFIER().getText();
    final Object value = visit(ctx.expression());
    executionContext.set(variable, value);

    return value;
  }

  @Override
  public Object visitReturnExpression(final ExpressionParser.ReturnExpressionContext ctx) {
    if (this.valueToBeReturned == null) {
      this.valueToBeReturned = visit(ctx.expression());
    }

    return this.valueToBeReturned;
  }

  @Override
  public Object visitIfConditional(final ExpressionParser.IfConditionalContext ctx) {
    int i = -1;
    ExpressionParser.BooleanExpressionContext booleanExpressionContext;

    do {
      i++;
      booleanExpressionContext = ctx.booleanExpression(i);
    } while (booleanExpressionContext != null && !(Boolean) visit(booleanExpressionContext));

    final ExpressionParser.StatementBlockContext statementBlock = ctx.statementBlock(i);
    if (statementBlock != null) {
      return visit(statementBlock);
    }

    return null;
  }

  @Override
  public final Object visitBooleanGroupedBy(final ExpressionParser.BooleanGroupedByContext ctx) {
    return visit(ctx.booleanExpression());
  }

  @Override
  public Object visitBooleanNumberComparison(final ExpressionParser.BooleanNumberComparisonContext ctx) {
    final BigDecimal left = (BigDecimal) visit(ctx.numberExpresion(0));
    final BigDecimal right = (BigDecimal) visit(ctx.numberExpresion(1));
    Comparison comparison = Comparison.findByOperator(ctx.op.getText());

    return comparison.compare(left, right);
  }

  @Override
  public final Object visitIdentifierBoolean(final ExpressionParser.IdentifierBooleanContext ctx) {
    final String identifier = ctx.IDENTIFIER().getText();

    return executionContext.get(identifier);
  }

  @Override
  public final Object visitBooleanOR(final ExpressionParser.BooleanORContext ctx) {
    Boolean left = (Boolean) visit(ctx.booleanExpression(0));
    Boolean right = (Boolean) visit(ctx.booleanExpression(1));

    return left || right;
  }

  @Override
  public final Object visitPrimitiveBoolean(final ExpressionParser.PrimitiveBooleanContext ctx) {
    final String bool = ctx.getText();

    return toBoolean(bool);
  }

  @Override
  public final Object visitBooleanNegation(final ExpressionParser.BooleanNegationContext ctx) {
    Boolean value = (Boolean) visit(ctx.booleanExpression());

    return negate(value);
  }

  @Override
  public final Object visitBooleanAND(final ExpressionParser.BooleanANDContext ctx) {
    Boolean left = (Boolean) visit(ctx.booleanExpression(0));
    Boolean right = (Boolean) visit(ctx.booleanExpression(1));

    return left && right;
  }

  @Override
  public final Object visitIdentifierDate(final ExpressionParser.IdentifierDateContext ctx) {
    final String identifier = ctx.IDENTIFIER().getText();

    return executionContext.get(identifier);
  }

  @Override
  public final Object visitPrimitiveDate(final ExpressionParser.PrimitiveDateContext ctx) {
    final String date = ctx.getText();

    return toLocalDate(date);
  }

  @Override
  public final Object visitNumberOperationSign(final ExpressionParser.NumberOperationSignContext ctx) {
    final BigDecimal result = (BigDecimal) visit(ctx.numberExpresion());
    if (ctx.MINUS() != null) {
      return result.multiply(BigDecimal.valueOf(-1));
    }

    return result;
  }

  @Override
  public final Object visitIdentifierNumber(final ExpressionParser.IdentifierNumberContext ctx) {
    final String identifier = ctx.IDENTIFIER().getText();

    return executionContext.get(identifier);
  }

  @Override
  public final Object visitNumberOperationSubtract(final ExpressionParser.NumberOperationSubtractContext ctx) {
    BigDecimal result = (BigDecimal) visit(ctx.numberExpresion(0));
    for (int i = 1; i < ctx.numberExpresion().size(); i++) {
      final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion(i));
      result = result.subtract(child);
    }

    return result;
  }

  @Override
  public final Object visitNumberOperationPow(final ExpressionParser.NumberOperationPowContext ctx) {
    final BigDecimal left = (BigDecimal) visit(ctx.numberExpresion(0));
    final BigDecimal right = (BigDecimal) visit(ctx.numberExpresion(1));

    return left.pow(right.intValue());
  }

  @Override
  public final Object visitNumberOperationMultiply(final ExpressionParser.NumberOperationMultiplyContext ctx) {
    BigDecimal result = (BigDecimal) visit(ctx.numberExpresion(0));
    for (int i = 1; i < ctx.numberExpresion().size(); i++) {
      final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion(i));
      result = result.multiply(child);
    }

    return result;
  }

  @Override
  public final Object visitNumberOperationDivide(final ExpressionParser.NumberOperationDivideContext ctx) {
    BigDecimal result = (BigDecimal) visit(ctx.numberExpresion(0));
    for (int i = 1; i < ctx.numberExpresion().size(); i++) {
      final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion(i));
      result = result.divide(child, DECIMAL128);
    }

    return result;
  }

  @Override
  public final Object visitPrimitiveNumber(final ExpressionParser.PrimitiveNumberContext ctx) {
    return new BigDecimal(ctx.getText());
  }

  @Override
  public final Object visitNumberOperationAddition(final ExpressionParser.NumberOperationAdditionContext ctx) {
    BigDecimal result = BigDecimal.ZERO;
    for (ExpressionParser.NumberExpresionContext numberExpresionContext : ctx.numberExpresion()) {
      BigDecimal child = (BigDecimal) visit(numberExpresionContext);
      result = result.add(child);
    }

    return result;
  }

  @Override
  public final Object visitNumberOperationModulo(final ExpressionParser.NumberOperationModuloContext ctx) {
    final BigDecimal left = (BigDecimal) visit(ctx.numberExpresion(0));
    final BigDecimal right = (BigDecimal) visit(ctx.numberExpresion(1));

    return left.remainder(right);
  }

  @Override
  public final Object visitNumberGroupedBy(final ExpressionParser.NumberGroupedByContext ctx) {
    return visit(ctx.numberExpresion());
  }

  @Override
  public final Object visitIdentifierString(final ExpressionParser.IdentifierStringContext ctx) {
    final String identifier = ctx.IDENTIFIER().getText();

    return executionContext.get(identifier);
  }

  @Override
  public final Object visitPrimitiveString(final ExpressionParser.PrimitiveStringContext ctx) {
    return ctx.getText().replaceAll("^['\"]", EMPTY).replaceAll("['\"]$", EMPTY);
  }

  @Override
  public Object visitFunctionDate(final ExpressionParser.FunctionDateContext ctx) {
    BigDecimal year = (BigDecimal) visit(ctx.numberExpresion(0));
    BigDecimal month = (BigDecimal) visit(ctx.numberExpresion(1));
    BigDecimal day = (BigDecimal) visit(ctx.numberExpresion(2));

    return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
  }

  @Override
  public Object visitFunctionToday(final ExpressionParser.FunctionTodayContext ctx) {
    return executionContext.get("today", LocalDate.class);
  }

  @Override
  public Object visitFunctionCos(final ExpressionParser.FunctionCosContext ctx) {
    final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion());
    final double degrees = child.doubleValue();
    final double cos = Math.cos(degrees);

    return normalizeResult(cos);
  }

  @Override
  public Object visitFunctionAcos(final ExpressionParser.FunctionAcosContext ctx) {
    final BigDecimal value = (BigDecimal) visit(ctx.numberExpresion());

    BigDecimal acos = getFunctionResolverAcos().resolver(value);

    return normalizeResult(acos);
  }

  @Override
  public Object visitFunctionSin(final ExpressionParser.FunctionSinContext ctx) {
    final BigDecimal value = (BigDecimal) visit(ctx.numberExpresion());

    final BigDecimal sin = getFunctionResolverSin().resolver(value);

    return normalizeResult(sin);
  }

  @Override
  public Object visitFunctionAsin(final ExpressionParser.FunctionAsinContext ctx) {
    final BigDecimal value = (BigDecimal) visit(ctx.numberExpresion());

    final BigDecimal asin = getFunctionResolverAsin().resolver(value);

    return normalizeResult(asin);
  }

  @Override
  public Object visitFunctionTan(final ExpressionParser.FunctionTanContext ctx) {
    final BigDecimal value = (BigDecimal) visit(ctx.numberExpresion());

    final BigDecimal tan = getFunctionResolverTan().resolver(value);

    return normalizeResult(tan);
  }

  @Override
  public Object visitFunctionAtan(final ExpressionParser.FunctionAtanContext ctx) {
    final BigDecimal value = (BigDecimal) visit(ctx.numberExpresion());

    final BigDecimal atan = getFunctionResolverAtan().resolver(value);

    return normalizeResult(atan);
  }

  @Override
  public Object visitFunctionLn(final ExpressionParser.FunctionLnContext ctx) {
    final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion());
    final double value = child.doubleValue();
    final double ln = Math.log(value);

    return normalizeResult(ln);
  }

  @Override
  public Object visitFunctionLog(final ExpressionParser.FunctionLogContext ctx) {
    final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion());
    final double value = child.doubleValue();
    final double log = Math.log10(value);

    return normalizeResult(log);
  }

  @Override
  public Object visitFunctionSqrt(final ExpressionParser.FunctionSqrtContext ctx) {
    final BigDecimal child = (BigDecimal) visit(ctx.numberExpresion());
    final double value = child.doubleValue();
    final double sqrt = Math.sqrt(value);

    return normalizeResult(sqrt);
  }

  @Override
  public Object visitFunctionCompareNumbers(final ExpressionParser.FunctionCompareNumbersContext ctx) {
    final BigDecimal left = (BigDecimal) visit(ctx.numberExpresion(0));
    final BigDecimal right = (BigDecimal) visit(ctx.numberExpresion(1));
    final Integer result = left.compareTo(right);

    return normalizeResultCompare(result);
  }

  @Override
  public Object visitFunctionCompareStrings(final ExpressionParser.FunctionCompareStringsContext ctx) {
    final String left = (String) visit(ctx.stringExpression(0));
    final String right = (String) visit(ctx.stringExpression(1));
    final Integer result = left.compareTo(right);

    return normalizeResultCompare(result);
  }

  @Override
  public Object visitFunctionCompareDates(final ExpressionParser.FunctionCompareDatesContext ctx) {
    final LocalDate left = (LocalDate) visit(ctx.dateExpresion(0));
    final LocalDate right = (LocalDate) visit(ctx.dateExpresion(1));
    final Integer result = left.compareTo(right);

    return normalizeResultCompare(result);
  }

  @Override
  public Object visitFunctionDay(final ExpressionParser.FunctionDayContext ctx) {
    LocalDate date = (LocalDate) visit(ctx.dateExpresion());

    return this.normalizeResult(date.getDayOfMonth());
  }

  @Override
  public Object visitFunctionMonth(final ExpressionParser.FunctionMonthContext ctx) {
    LocalDate date = (LocalDate) visit(ctx.dateExpresion());

    return this.normalizeResult(date.getMonthValue());
  }

  @Override
  public Object visitFunctionYear(final ExpressionParser.FunctionYearContext ctx) {
    LocalDate date = (LocalDate) visit(ctx.dateExpresion());

    return this.normalizeResult(date.getYear());
  }

  private Object normalizeResult(final double value) {
    final BigDecimal result = BigDecimal.valueOf(value);

    return normalizeResult(result);
  }

  private Object normalizeResult(final Integer result) {
    return normalizeResult(BigDecimal.valueOf(result));
  }

  private Object normalizeResult(final BigDecimal result) {
    return result;
  }

  private Object normalizeResultCompare(Integer result) {
    if (result == 0) {
      return this.normalizeResult(0);
    }

    return result < 0
      ? this.normalizeResult(-1)
      : this.normalizeResult(1);
  }
}
