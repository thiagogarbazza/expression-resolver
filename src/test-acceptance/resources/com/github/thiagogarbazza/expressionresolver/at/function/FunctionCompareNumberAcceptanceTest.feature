Feature: Function compare number
  It is necessary that expression-resolver perform compare number values.

  Background:
    Given the variable named $N is in the expression's execution context with the value: 45
    And the variable named $OTHER_VALUE_N is in the expression's execution context with the value: 90

  Scenario Outline: 01. Perform "compare numbers".
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed                 | result expected |
      | return compareNumber(1, 9);               | -1              |
      | return compareNumber(3.3, 9.9);           | -1              |
      | return compareNumber(5, 5);               | 0               |
      | return compareNumber(5.123, 5.123);       | 0               |
      | return compareNumber(9, 1);               | 1               |
      | return compareNumber(9.12, 1.98);         | 1               |
      | return compareNumber(9.12, $N);           | -1              |
      | return compareNumber($OTHER_VALUE_N, $N); | 1               |
