Feature: The user can add a new video tip

  Scenario: User can add a new video tip with valid information
    Given User is on the new video page
    When  Title "TestVideo", URL "www.testurl.com" and comment "TestComment" are entered to create a new video
    Then  New tip is created

  Scenario: User can not add a new video tip with empty title
    Given User is on the new video page
    When  Empty title, URL "www.testurl.com" and comment "TestComment" are entered to create a new video
    Then  New tip is not created and the user is taken to the list page

  Scenario: User can not add a new video tip with empty url
    Given User is on the new video page
    When  Title "TestVideo", URL "" and comment "TestComment" are entered to create a new video
    Then  New tip is not created and the user is taken to the list page

  Scenario: User can not add a new video tip with empty comment
    Given User is on the new video page
    When  Title "TestVideo", URL "www.testurl.com" and comment "" are entered to create a new video
    Then  New tip is not created and the user is taken to the list page
