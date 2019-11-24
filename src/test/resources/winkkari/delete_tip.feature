Feature: The user can delete tips

  Scenario: User can delete a tip
    Given   User has added a tip
    And     User is on the list page
    When    User wants to delete the tip and clicks delete
    Then    The tip is deleted