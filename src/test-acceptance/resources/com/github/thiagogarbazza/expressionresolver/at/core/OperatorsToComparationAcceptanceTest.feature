Feature: Comparison operators
  It is necessary that expression-resolver perform arithmetic comparison expression.


  StatementsOperatorsToComparison

  Background:
    Given the variable named $A is in the expression's execution context with the value: 4.0
    And the variable named $B is in the expression's execution context with the value: 5
    And the variable named $C is in the expression's execution context with the value: 5.0
    And the variable named $D is in the expression's execution context with the value: 25.5

  Scenario Outline: 01 - perform "equals" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 1 == 1;            | true            |
      | return 1.0 == 1.0;        | true            |
      | return 1 == 1.0;          | true            |
      | return 33.333 == 33.333;  | true            |
      | return 7 == 8;            | false           |
      | return 3.3 == 3;          | false           |
      | return $A == 4;           | true            |
      | return $A == 3;           | false           |
      | return $A == $B;          | false           |
      | return $B == $C;          | true            |

  Scenario Outline: 02 - perform "not equals" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 7 != 8;            | true            |
      | return 7.0 != 8.0;        | true            |
      | return 7 != 8.0;          | true            |
      | return 3.3 != 77.8;       | true            |
      | return 3.3 != 3.33;       | true            |
      | return 7 != 7;            | false           |
      | return 7 != 7.0;          | false           |
      | return 3.3 != 3.3;        | false           |
      | return $A != 3;           | true            |
      | return $A != $B;          | true            |
      | return $B != $C;          | false           |

  Scenario Outline: 03 - perform "greater than" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 9 > 5;             | true            |
      | return 9.0 > 5;           | true            |
      | return 9.9 > 5.5;         | true            |
      | return 9.9 > 9;           | true            |
      | return 7 > 7;             | false           |
      | return 3 > 3.3;           | false           |
      | return 9.9 > 9.9;         | false           |
      | return $A > 3;            | true            |
      | return $A > $B;           | false           |
      | return $B > $C;           | false           |

  Scenario Outline: 04 - perform "greater than or equal" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 9 >= 5;            | true            |
      | return 9.0 >= 5;          | true            |
      | return 9.9 >= 5.5;        | true            |
      | return 9.9 >= 9.9;        | true            |
      | return 9.9 >= 9;          | true            |
      | return 7 >= 7;            | true            |
      | return 1 >= 8;            | false           |
      | return 3 >= 3.3;          | false           |
      | return $A >= 3;           | true            |
      | return $A >= $B;          | false           |
      | return $B >= $C;          | true            |

  Scenario Outline: 05 - perform "less than" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 5 < 9;             | true            |
      | return 5.0 < 9;           | true            |
      | return 5.5 < 9.9;         | true            |
      | return 9 < 9.9;           | true            |
      | return 7 < 7;             | false           |
      | return 3.3 < 3;           | false           |
      | return 5.5 < 5.5;         | false           |
      | return $A < 3;            | false           |
      | return $A < $B;           | true            |
      | return $B < $C;           | false           |

  Scenario Outline: 06 - perform "less than or equal" expression
    Given send the expression: <expression to be executed>
    When to request expression execution
    Then will should to get the result: <result expected>
    Examples:
      | expression to be executed | result expected |
      | return 5 <= 9;            | true            |
      | return 5.0 <= 9;          | true            |
      | return 5.5 <= 9.9;        | true            |
      | return 9.9 <= 9.9;        | true            |
      | return 9 <= 9.9;          | true            |
      | return 7 <= 7;            | true            |
      | return 8 <= 1;            | false           |
      | return 3.3 <= 3;          | false           |
      | return $A <= 3;           | false           |
      | return $A <= $B;          | true            |
      | return $B <= $C;          | true            |
