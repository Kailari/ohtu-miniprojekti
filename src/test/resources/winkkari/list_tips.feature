Feature: The user can list tips

    Scenario: User can look up all the available tips
        Given   User is on the main page
        When    User wants to check tips and clicks "List all tips"
        Then    All the available tips will be displayed

