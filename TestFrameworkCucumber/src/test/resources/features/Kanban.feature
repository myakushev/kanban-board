Feature: Example Feature

  Scenario: Basic Cucumber Test
    Given this is a basic test Scenario.
    And send create-kanban-board request
      """
        {
          "title": "Hello World"
        }
      """