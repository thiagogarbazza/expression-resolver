package com.github.thiagogarbazza.expressionresolver.at;

import com.github.thiagogarbazza.expressionresolver.ExpressionInterpreter;
import com.github.thiagogarbazza.expressionresolver.domain.Expression;
import com.github.thiagogarbazza.expressionresolver.domain.ExpressionContext;
import com.github.thiagogarbazza.expressionresolver.domain.Result;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

import java.math.BigDecimal;

import static com.github.thiagogarbazza.expressionresolver.at.UtilATBigDecimal.stringToBigDecimal;
import static com.github.thiagogarbazza.expressionresolver.at.UtilATBigDecimal.stringToBigDecimals;
import static com.github.thiagogarbazza.expressionresolver.at.UtilATDate.stringToDate;
import static com.github.thiagogarbazza.expressionresolver.at.UtilATDate.stringToDates;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.junit.Assert.assertEquals;

public class ExpressionInterpreterStep {

  private ExpressionContext context;
  private Expression expression;
  private Result expressionResult;
  private ExpressionInterpreter interpreter;

  @Before
  public void before() {
    interpreter = new ExpressionInterpreter();
    context = new ExpressionContext();
  }

  @And("^the following booleans in the context$")
  public void contextBackgroundBoolensInContext(DataTable booleans) throws Throwable {
    booleans.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), toBoolean(cell.get(1))));
  }

  @And("^the following dates collections in the context$")
  public void contextBackgroundDatesCollectionsInContext(DataTable dates) throws Throwable {
    dates.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), stringToDates(cell.get(1))));
  }

  @And("^the following dates in the context$")
  public void contextBackgroundDatesInContext(DataTable dates) throws Throwable {
    dates.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), stringToDate(cell.get(1))));
  }

  @And("^the following numbers collections in the context$")
  public void contextBackgroundNumbersCollectionsInContext(DataTable numbers) throws Throwable {
    numbers.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), stringToBigDecimals(cell.get(1))));
  }

  @And("^the following numbers in the context$")
  public void contextBackgroundNumbersInContext(DataTable numbers) throws Throwable {
    numbers.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), stringToBigDecimal(cell.get(1))));
  }

  @And("^the following strings in the context$")
  public void contextBackgroundStringsInContext(DataTable strings) throws Throwable {
    strings.cells().stream().skip(1).forEach(cell -> context.set(cell.get(0), cell.get(1)));
  }

  @Given("^Send the expression \"(.*)\".$")
  public void givenSendTheExpression(String expression) throws Throwable {
    this.expression = new Expression(expression);
  }

  @Then("^I should have resulted the boolean: \"(.*)\".$")
  public void thenIShouldHaveResultedTheBoolean(String expressionResult) throws Throwable {
    Result expected = new Result(toBoolean(expressionResult));
    assertEquals(expected, this.expressionResult);
  }

  @Then("^I should have resulted the date: \"(.*)\".$")
  public void thenIShouldHaveResultedTheDate(String expressionResult) throws Throwable {
    Result expected = new Result(stringToDate(expressionResult));

    assertEquals(expected, this.expressionResult);
  }

  @Then("^I should have resulted the dates: \"(.*)\".$")
  public void thenIShouldHaveResultedTheDates(String expressionResult) throws Throwable {
    Result expected = new Result(stringToDates(expressionResult));

    assertEquals(expected, this.expressionResult);
  }

  @Then("^I should have resulted the number: \"([^\"]*)\".$")
  public void thenIShouldHaveResultedTheNumber(String expressionResult) throws Throwable {
    Result expected = new Result(new BigDecimal(expressionResult));
    assertEquals(expected, this.expressionResult);
  }

  @Then("^I should have resulted the string: \"([^\"]*)\".$")
  public void thenIShouldHaveResultedTheString(String expressionResult) throws Throwable {
    Result expected = new Result(expressionResult);
    assertEquals(expected, this.expressionResult);
  }

  @When("^I ask what the result is\\?$")
  public void whenIAskWhatTheResultIs() throws Throwable {
    this.expressionResult = interpreter.toInterpret(this.expression, this.context);
  }
}
