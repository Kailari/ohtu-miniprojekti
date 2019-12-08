package winkkari;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;



import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Stepdefs {
    private WebDriver driver;
    private final String baseUrl = "http://localhost:4567";
    int rand;

    @Before
    public void setUp() {
        
        /*System.setProperty(
            "webdriver.chrome.driver",
            "F:\\Downloads\\chromedriver_win32\\chromedriver.exe"
        );
        driver = new ChromeDriver();
        */
        //driver = new ChromeDriver();
        // driver = new FirefoxDriver();
        driver = new HtmlUnitDriver();

        Random random = new Random();
        rand = random.nextInt() * 10;
    }

    @Given("User is on the main page")
    public void userIsOnTheMainPage() {
        driver.get(baseUrl);
    }

    @Given("User is on the new book page")
    public void userIsOnTheNewBookTipPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("book tip"));
        element.click();
    }

    @Given("User is on the new link page")
    public void userIsOnTheNewLinkTipPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("link tip"));
        element.click();
    }

    @Given("User is on the new video page")
    public void userIsOnTheNewVideoTipPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("video tip"));
        element.click();
    }

    @Given("User is on the list page")
    public void userIsOnTheListPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("List all tips"));
        element.click();
    }

    @Given("User has added a book tip with Author {string} and title {string}")
    public void userHasAddedBookTip(String author, String title) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("book tip"));
        element.click();
        createNewBookTip(author + rand, title + rand);
    }

    @Given("User has added a link tip with title {string}, URL {string} and comment {string}")
    public void userHasAddedLinkTip(String title, String url, String comment) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("link tip"));
        element.click();
        createNewLinkTip(title + rand, url, comment);
    }

    @Given("User has added a video tip with title {string}, URL {string} and comment {string}")
    public void userHasAddedVideoTip(String title, String url, String comment) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("video tip"));
        element.click();
        createNewVideoTip(title + rand, url, comment);
    }

    @Given("User has clicked edit button next to tip {string}")
    public void userHasClickedLinkEditButton(String title) {
        WebElement element = driver.findElement(By.xpath(
                "//table/tbody/tr[td[span[text()='" + title + rand + "']]]/td[7]/a"
        ));
        element.click();
    }

    @When("User edits author to {string} and title to {string} and clicks submit")
    public void userEditsBookTip(String author, String title) {
        WebElement element = driver.findElement(By.name("author"));
        element.clear();
        element.sendKeys(author);
        element = driver.findElement(By.name("title"));
        element.clear();
        element.sendKeys(title);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }

    @When("User edits link title to {string}, url to {string} and comment to {string} and clicks submit")
    public void userEditsLinkTip(String title, String url, String comment) {
        WebElement element = driver.findElement(By.name("title"));
        element.clear();
        element.sendKeys(title);
        element = driver.findElement(By.name("url"));
        element.clear();
        element.sendKeys(url);
        element = driver.findElement(By.name("comment"));
        element.clear();
        element.sendKeys(comment);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }

    @When("User edits video title to {string}, url to {string} and comment to {string} and clicks submit")
    public void userEditsVideoTip(String title, String url, String comment) {
        WebElement element = driver.findElement(By.name("title"));
        element.clear();
        element.sendKeys(title);
        element = driver.findElement(By.name("url"));
        element.clear();
        element.sendKeys(url);
        element = driver.findElement(By.name("comment"));
        element.clear();
        element.sendKeys(comment);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }

    @Then("The displayed tip has updated values {string} and {string}")
    public void displayedTipHasUpdatedValues(String first, String second) {
        pageHasContent(first);
        pageHasContent(second);
    }

    @Then("Link URL {string} and video URL {string} are displayed as links")
    public void LinkTipUrlAndVideoTipUrlAreDisplayedAsLinks(String linkUrl, String videoUrl) {
        assertTrue(driver.findElement(By.linkText(linkUrl)).isDisplayed());
        assertTrue(driver.findElement(By.linkText(videoUrl)).isDisplayed());
    }

    @When("Author {string} and title {string} are entered")
    public void authorAndTitleAreEntered(String author, String title) {
        createNewBookTip(author + rand, title + rand);
    }

    @When("Title {string}, URL {string} and comment {string} are entered to create a new link")
    public void titleUrlAndCommentAreEnteredToCreateLink(String title, String url, String comment) {
        createNewLinkTip(title + rand, url, comment);
    }

    @When("Empty title, URL {string} and comment {string} are entered to create a new link")
    public void emptyTitleUrlAndCommentAreEnteredToCreateLink(String url, String comment) {
        createNewLinkTip("", url, comment);
    }

    @When("Title {string}, URL {string} and comment {string} are entered to create a new video")
    public void titleUrlAndCommentAreEnteredToCreateVideo(
            String title,
            String url,
            String comment
    ) {
        createNewVideoTip(title + rand, url, comment);
    }

    @When("Empty title, URL {string} and comment {string} are entered to create a new video")
    public void emptyTitleUrlAndCommentAreEnteredToCreateVideo(
            String url,
            String comment
    ) {
        createNewVideoTip("", url, comment);
    }

    @Then("New tip is created")
    public void newTipIsCreated() {

        sleep(1);
        pageHasContent(Integer.toString(rand));
    }

    @When("Empty author and empty title are entered")
    public void emptyAuthorAndEmptyTitleAreEntered() {
        createNewBookTip("", "");
    }

    @Then("New tip is not created and the user is taken to the list page")
    public void newTipIsNotCreatedAndTheUserIsTakenToTheListPage() {
        sleep(1);
        pageHasContent("Tips");
    }

    @When("Empty author and title {string} are entered")
    public void emptyAuthorAndTitleAreEntered(String title) {
        createNewBookTip("", title);
    }

    @When("Author {string} and empty title are entered")
    public void emptyAuthorAndEmptyTitleAreEntered(String author) {
        createNewBookTip(author, "");
    }

    @When("User wants to check tips and clicks {string}")
    public void userGoesToListAllTips(String string) {
        WebElement element = driver.findElement(By.linkText(string));
        element.click();
    }

    @Then("All the available tips will be displayed")
    public void allTheAvailableTipsWillBeDisplayed() {
        sleep(1);
        pageHasContent("Tips");
    }

    @When("User wants to delete the tip with title {string} and clicks delete")
    public void userDeletesTip(String title) {
        sleep(1);
        WebElement element = driver.findElement(By.xpath(
                "//table/tbody/tr[td[span[text()='" + title + rand + "']]]/td[6]/form/input"
        ));
        System.out.println("TAG IS:   " + element.getTagName());
        element.submit();
    }

    @Then("The tip with title {string} is deleted")
    public void tipIsDeleted(String title) {
        sleep(1);
        pageHasNotContent(title + rand);
    }

    @Given("There are tips of multiple types available")
    public void thereAreTipsOfMultipleTypesAvailable() {
        // Well but there are! (ServerRule.java)
    }

    @Then("Tips of all types will be displayed")
    public void tipsOfAllTypesWillBeDisplayed() {
        pageHasContent("This is a BookTip");
        pageHasContent("This is a LinkTip");
        pageHasContent("This is a VideoTip");
    }

    @When("User chooses to search for books")
    public void userSearchesForBooks() {
        sleep(1);
        WebElement element = driver.findElement(By.xpath(
                "//body/ul/li[2]/a"
        ));
        element.click();
    }

    @When("User chooses to search for links")
    public void userSearchesForLinks() {
        sleep(1);
        WebElement element = driver.findElement(By.xpath(
                "//body/ul/li[3]/a"
        ));
        element.click();
    }

    @When("User chooses to search for videos")
    public void userSearchesForVideos() {
        sleep(1);
        WebElement element = driver.findElement(By.xpath(
                "//body/ul/li[4]/a"
        ));
        element.click();
    }

    @Then("Only the tip with title {string} is shown and not titles {string} and {string}")
    public void onlySpecifiedTipsAreShown(String title1, String title2, String title3) {
        pageHasContent(title1 + rand);
        pageHasNotContent(title2 + rand);
        pageHasNotContent(title3 + rand);
    }

    @When("An ISBN is given")
    public void anISBNIsGiven() {
        var element = driver.findElement(By.name("isbnSearch"));
        element.sendKeys("1234567890123");
        element = driver.findElement(By.cssSelector(".button.find"));
        element.submit();
    }

    @Then("Create new book fields are automatically filled")
    public void createNewFieldsAreAutomaticallyFilled() {
        var title = driver.findElement(By.name("title"));
        var author = driver.findElement(By.name("author"));
        var isbn = driver.findElement(By.name("isbn"));

        assertFalse(title.getAttribute("value").isEmpty());
        assertFalse(author.getAttribute("value").isEmpty());
        assertFalse(isbn.getAttribute("value").isEmpty());
    }

    @When("An URL is given")
    public void anURLIsGiven() {
        var element = driver.findElement(By.name("urlSearch"));
        element.sendKeys("https://www.google.com");
        element = driver.findElement(By.cssSelector(".button.find"));
        element.submit();
    }

    @Then("Create new link fields are automatically filled")
    public void createNewLinkFieldsAreAutomaticallyFilled() {
        final var url = driver.findElement(By.name("url"));
        final var title = driver.findElement(By.name("title"));
        final var comment = driver.findElement(By.name("comment"));

        assertFalse(url.getAttribute("value").isEmpty());
        assertFalse(title.getAttribute("value").isEmpty());
        assertFalse(comment.getAttribute("value").isEmpty());
    }
    @When("User chooses to order by Title")
    public void orderBy(){
        WebElement element = driver.findElement(By.xpath(
                "//body/table/thead/tr/th[1]/a"
        ));
        element.click();
    }
    @Then("The tip with title {string} is shown first")
    public void orderIsCorrect(String string) {
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.xpath(
                "//body/table/tbody/tr/td[1]/span"
        ));
        System.out.println(element.getTagName());
        System.out.println(element.getText());
        assertTrue(element.getText().contains(string));
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    //Helper methods:

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void pageHasNotContent(String content) {
        assertFalse(driver.getPageSource().contains(content));
    }

    private void createNewBookTip(String author, String title) {
        assertTrue(driver.getPageSource().contains("Add a new book tip"));
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(author);
        element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }

    private void createNewLinkTip(String title, String url, String comment) {
        assertTrue(driver.getPageSource().contains("Add a new link tip"));
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("comment"));
        element.sendKeys(comment);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }

    private void createNewVideoTip(String title, String url, String comment) {
        assertTrue(driver.getPageSource().contains("Add a new video tip"));
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("comment"));
        element.sendKeys(comment);
        element = driver.findElement(By.name("submit"));
        element.submit();
    }


    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
