Feature: The user can delete tips

  Scenario: User can delete a tip
    Given   User has added a book tip with Author "TestAuthor" and title "TestBook"
    And     User is on the list page
    When    User wants to delete the tip with title "TestBook" and clicks delete
    Then    The tip with title "TestBook" is deleted