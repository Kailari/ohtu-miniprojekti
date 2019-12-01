Feature: The user can add a new video tip

  Scenario: User can add a new tip link with valid information
    Given   User is on the new video page
    When    Title "TestVideo", URL "www.testurl.com" and comment "TestComment" are entered to create a new video
    Then    New tip is created