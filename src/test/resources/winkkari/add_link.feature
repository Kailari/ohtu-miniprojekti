Feature: The user can add a new link tip

  Scenario: User can add a new tip link with valid information
    Given   User is on the new link page
    When    Title "TestLink", URL "www.testurl.com" and comment "TestComment" are entered to create a new link
    Then    New tip is created