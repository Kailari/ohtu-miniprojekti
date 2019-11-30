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
        /*
        System.setProperty(
            "webdriver.chrome.driver",
            "F:\\Downloads\\chromedriver_win32\\chromedriver.exe"
        );
        driver = new ChromeDriver();
        */
        //driver = new FirefoxDriver();
        driver = new HtmlUnitDriver();

        Random random = new Random();
        rand = random.nextInt() * 10;
    }

    @Given("User is on the main page")
    public void userIsOnTheMainPage() {
        driver.get(baseUrl);
    }

    @Given("User is on the new tip page")
    public void userIsOnTheNewTipPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Add a new tip"));
        element.click();
    }

    @Given("User is on the list page")
    public void userIsOnTheListPage() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("List all tips"));
        element.click();
    }

    @Given("User has added a tip")
    public void userHasAddedTip() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Add a new tip"));
        element.click();
        createNewTip("Testaaja", "Testi2");
    }

    @When("Author {string} and title {string} are entered")
    public void authorAndTitleAreEntered(String author, String title) {
        createNewTip(author + rand, title + rand);
    }

    @Then("New tip is created")
    public void newTipIsCreated() {
        sleep(1);
        pageHasContent(Integer.toString(rand));
    }

    @When("Empty author and empty title are entered")
    public void emptyAuthorAndEmptyTitleAreEntered() {
        createNewTip("", "");
    }

    @Then("New tip is not created and the user is taken to the list page")
    public void newTipIsNotCreatedAndTheUserIsTakenToTheListPage() {
        sleep(1);
        pageHasContent("All tips");
    }

    @When("Empty author and title {string} are entered")
    public void emptyAuthorAndTitleAreEntered(String title) {
        createNewTip("", title);
    }

    @When("Author {string} and empty title are entered")
    public void emptyAuthorAndEmptyTitleAreEntered(String author) {
        createNewTip(author, "");
    }

    @When("User wants to check tips and clicks {string}")
    public void userGoesToListAllTips(String string) {
        WebElement element = driver.findElement(By.linkText(string));
        element.click();
    }

    @Then("All the available tips will be displayed")
    public void allTheAvailableTipsWillBeDisplayed() {
        sleep(1);
        pageHasContent("All tips");
    }

    @When("User wants to delete the tip and clicks delete")
    public void userDeletesTip() {
        sleep(10);
        WebElement element = driver.findElement(By.xpath(
                "//table/tbody/tr[td[span[text()='Testi2']]]/td[4]/form/input"
        ));
        System.out.println("TAG IS:   " + element.getTagName());
        element.submit();
    }

    @Then("The tip is deleted")
    public void tipIsDeleted() {
        sleep(1);
        pageHasNotContent("Testi2");
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

    private void createNewTip(String author, String title) {
        assertTrue(driver.getPageSource().contains("Add new tip"));
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(author);
        element = driver.findElement(By.name("title"));
        element.sendKeys(title);
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
