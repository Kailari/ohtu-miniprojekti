Feature: The user can order tips


  Scenario: User can order tips Descending
    Given   User is on the main page
    And     User has added a book tip with Author "AAA" and title "AAA"
    And     User has added a book tip with Author "XXX" and title "XXX"
    When    User chooses to order by Title
    Then    The tip with title "XXX" is shown first


  Scenario: User can order tips Ascending
    Given   User is on the main page
    And     User has added a book tip with Author "AAA" and title "AAA"
    And     User has added a book tip with Author "XXX" and title "XXX"
    When    User chooses to order by Title
    When    User chooses to order by Title
    Then    The tip with title "AAA" is shown first