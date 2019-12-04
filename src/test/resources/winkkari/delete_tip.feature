Feature: The user can delete tips

  Scenario: User can delete a book tip
    Given   User has added a book tip with Author "TestAuthor" and title "TestBook"
    And     User is on the list page
    When    User wants to delete the tip with title "TestBook" and clicks delete
    Then    The tip with title "TestBook" is deleted

  Scenario: User can delete a link tip
    Given   User has added a link tip with title "DeleteTestTitle", URL "www.test.org" and comment "test"
    And     User is on the list page
    When    User wants to delete the tip with title "DeleteTestTitle" and clicks delete
    Then    The tip with title "DeleteTestTitle" is deleted

  Scenario: User can delete a video tip
    Given   User has added a video tip with title "DeleteTestTitle", URL "www.videos.org/somevid" and comment "test"
    And     User is on the list page
    When    User wants to delete the tip with title "DeleteTestTitle" and clicks delete
    Then    The tip with title "DeleteTestTitle" is deleted
