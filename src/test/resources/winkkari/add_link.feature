Feature: The user can add a new link tip

  Scenario: User can add a new link tip with valid information
    Given User is on the new link page
    When  Title "TestLink", URL "www.testurl.com" and comment "TestComment" are entered to create a new link
    Then  New tip is created

  Scenario: User can not add a new link tip with empty url
    Given User is on the new link page
    When  Title "TestLink", URL "" and comment "Some comment" are entered to create a new link
    Then  New tip is not created and the user is taken to the list page

  Scenario: User can not add a new link tip with empty title
    Given User is on the new link page
    When  Empty title, URL "www.testurl.com" and comment "Some comment" are entered to create a new link
    Then  New tip is not created and the user is taken to the list page

  Scenario: User can not add a new link tip with empty comment
    Given User is on the new link page
    When  Title "TestLink", URL "www.testurl.com" and comment "" are entered to create a new link
    Then  New tip is not created and the user is taken to the list page
