Feature: The user can add a new book tip by searching its info with ISBN

  Scenario: User can search book info by its ISBN
    Given User is on the new book page
    When  An ISBN is given
    Then  Create new book fields are automatically filled
  