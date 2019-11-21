Feature: The user can add a new reading tip

    Scenario: User can add a new tip with valid information
        Given   User is on the new tip page
        When    Author "TestAuthor" and title "TestBook" are entered
        Then    New tip is created

    Scenario: User can not add a new tip with empty author and title
        Given   User is on the new tip page
        When    Empty author and empty title are entered
        Then    New tip is not created and the user is taken to the list page

    Scenario: User can not add a new tip with empty author
        Given   User is on the new tip page
        When    Empty author and title "TestBook" are entered
        Then    New tip is not created and the user is taken to the list page

    Scenario: User can not add a new tip with empty title
        Given   User is on the new tip page
        When    Author "TestAuthor" and empty title are entered
        Then    New tip is not created and the user is taken to the list page
