Feature: Kanban Board Management

  Background:
    Given the database for service kanban-service is clean

  Scenario: Create one board and verify it exists
    When I create a new kanban board with body:
      """
      {
        "title": "Board from an elegant test"
      }
      """
    Then the "kanban" table for kanban boards should contain 1 rows

  Scenario: Create a second board and verify total count
    # Сначала создаем первую доску, т.к. Background очистил базу
    When I create a new kanban board with body:
      """
      {
        "title": "First of two boards"
      }
      """
    # Затем вторую
    When I create a new kanban board with body:
      """
      {
        "title": "Second of two boards"
      }
      """
    # И проверяем итоговое количество
    Then the "kanban" table for kanban boards should contain 2 rows