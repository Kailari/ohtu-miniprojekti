Feature: The user can edit tips

    Scenario: User can edit a book tip
        Given   User is on the list page
        And     User has added a book tip with Author "EditTestAuthor" and title "EditTestBook"
        And     User has clicked edit button next to tip "EditTestBook"
        When    User edits author to "EditedAuthor" and title to "EditedTitle" and clicks submit
        Then    The displayed tip has updated values "EditedAuthor" and "EditedTitle"

    Scenario: User can edit a link tip
        Given   User is on the list page
        And     User has added a link tip with title "EditTestLinkTitle", URL "EditTestLinkUrl" and comment "EditTestLinkComment"
        And     User has clicked edit button next to tip "EditTestLinkTitle"
        When    User edits link title to "EditedLinkTitle", url to "EditedLinkUrl" and comment to "EditedLinkComment" and clicks submit
        Then    The displayed tip has updated values "EditedLinkTitle" and "EditedLinkUrl"

    Scenario: User can edit a video tip
        Given   User is on the list page
        And     User has added a video tip with title "EditTestVideoTitle", URL "EditTestVideoUrl" and comment "EditTestVideoComment"
        And     User has clicked edit button next to tip "EditTestVideoTitle"
        When    User edits video title to "EditedVideoTitle", url to "EditedVideoUrl" and comment to "EditedVideoComment" and clicks submit
        Then    The displayed tip has updated values "EditedVideoTitle" and "EditedVideoUrl"

