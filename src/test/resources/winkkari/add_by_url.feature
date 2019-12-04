Feature: The user can add a new link tip by searching its info by the URL

  Scenario: User can search link info by its URL
    Given User is on the new link page
    When  An URL is given
    Then  Create new link fields are automatically filled
  