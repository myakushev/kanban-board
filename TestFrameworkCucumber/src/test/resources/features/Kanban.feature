Feature: Kanban Board Management

  Scenario: 1 - Create a new kanban board and verify it in the database
    Given this is a basic test Scenario.
    When I send a request to create a new kanban board
      """
      {
        "title": "Board created from test 1 Spring"
      }
      """
    # Предполагая, что ваше API создает запись в таблице 'kanban_boards'
    Then the kanban table should contain 1 row

  Scenario: 2 - Create a new kanban board and verify it in the database
    Given this is a basic test Scenario.
    When I send a request to create a new kanban board
      """
      {
        "title": "Board created from test 2 Spring"
      }
      """
    # Предполагая, что ваше API создает запись в таблице 'kanban_boards'
    Then the kanban table should contain 2 row