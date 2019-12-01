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

    Scenario: User can search for book tips
        Given   User is on the main page
        And     User has added a book tip with Author "TestAuthor" and title "TestBook"
        And     User has added a link tip with title "TestLinkTitle", URL "TestLinkUrl" and comment "TestLinkComment"
        And     User has added a video tip with title "TestVideoTitle", URL "TestVideoUrl" and comment "TestVideoComment"
        When    User chooses to search for books
        Then    Only the tip with title "TestBook" is shown and not titles "TestLinkTitle" and "TestVideoTitle"

    Scenario: User can search for link tips
        Given   User is on the main page
        And     User has added a book tip with Author "TestAuthor" and title "TestBook"
        And     User has added a link tip with title "TestLinkTitle", URL "TestLinkUrl" and comment "TestLinkComment"
        And     User has added a video tip with title "TestVideoTitle", URL "TestVideoUrl" and comment "TestVideoComment"
        When    User chooses to search for links
        Then    Only the tip with title "TestLinkTitle" is shown and not titles "TestBook" and "TestVideoTitle"

    Scenario: User can search for Video tips
        Given   User is on the main page
        And     User has added a book tip with Author "TestAuthor" and title "TestBook"
        And     User has added a link tip with title "TestLinkTitle", URL "TestLinkUrl" and comment "TestLinkComment"
        And     User has added a video tip with title "TestVideoTitle", URL "TestVideoUrl" and comment "TestVideoComment"
        When    User chooses to search for videos
        Then    Only the tip with title "TestVideoTitle" is shown and not titles "TestLinkTitle" and "TestBook"

    Scenario: User can visit url of tip by clicking it
        Given   User has created a link- or video-tip
        And     User is on the list all page
        When    User clicks url of newly added tip
        Then    User is taken to url