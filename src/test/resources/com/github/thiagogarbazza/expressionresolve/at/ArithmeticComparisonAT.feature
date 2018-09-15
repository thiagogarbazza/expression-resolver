Feature: Arithmetic comparison
  It is necessary that expression-resolver perform arithmetic comparison expression.

  Scenario Outline: 01. perform "equals" expression.
    Given Send the expression "<expression>".
    When I ask what the result is?
    Then I should have resulted the boolean: "<expression-result>".
    Examples:
      | expression       | expression-result |
      | 1 == 1           | true              |
      | 33.333 == 33.333 | true              |
      | 7 == 8           | false             |
      | 3.3 == 3         | false             |

  Scenario Outline: 02. perform "not equals" expression.
    Given Send the expression "<expression>".
    When I ask what the result is?
    Then I should have resulted the boolean: "<expression-result>".
    Examples:
      | expression  | expression-result |
      | 7 != 8      | true              |
      | 3.3 != 77.8 | true              |
      | 3.3 != 3.33 | true              |
      | 7 != 7      | false             |
      | 3.3 != 3.3  | false             |
