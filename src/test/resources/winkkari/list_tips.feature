Feature: The user can list tips

    Scenario: User can look up all the available tips
        Given   User is on the main page
        When    User wants to check tips and clicks "List all tips"
        Then    All the available tips will be displayed

    Scenario: User can see all types of tips on a single view
        Given   There are tips of multiple types available
        And     User is on the main page
        When    User wants to check tips and clicks "List all tips"
        Then    Tips of all types will be displayed

