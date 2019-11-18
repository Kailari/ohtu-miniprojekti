Feature: The user can add a new reading tip

    Scenario: User can add a new tip with valid information
        Given   User is on the new tip page
        When    Author "Robert Martin" and title "Clean Code: A Handbook of Agile Software Craftmanship" are entered
        Then    New tip is created
