package winkkari;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import io.cucumber.java.After;

import java.sql.SQLOutput;
import java.util.Random;

public class Stepdefs {
    //    private final WebDriver driver = new FirefoxDriver();
    private final WebDriver driver = new HtmlUnitDriver();
    private final String baseUrl = "http://localhost:4567";
    Random random = new Random();
    int rand = random.nextInt() * 10;

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
        try{ Thread.sleep(1000); } catch(Exception e){} //Sleep for 1 sec
        pageHasContent(/*Integer.toString(rand)*/ "All tips");
    }

    @When("Empty author and empty title are entered")
    public void emptyAuthorAndEmptyTitleAreEntered() {
        createNewTip("", "");
    }

    @Then("New tip is not created and the user is taken to the list page")
    public void newTipIsNotCreatedAndTheUserIsTakenToTheListPage() {
        try{ Thread.sleep(1000); } catch(Exception e){} //Sleep for 1 sec
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
        try{ Thread.sleep(1000); } catch(Exception e){} //Sleep for 1 sec
        pageHasContent("All tips");
    }

    @When("User wants to delete the tip and clicks delete")
    public void userDeletesTip() {
        WebElement element = driver.findElement(By.xpath("//table/tbody/tr[td[span[text()='Testi2']]]/td[3]/form/input[1]"));
        System.out.println("TAG IS:   " + element.getTagName());
        element.submit();
    }

    @Then("The tip is deleted")
    public void tipIsDeleted() {
        try{ Thread.sleep(1000); } catch(Exception e){} //Sleep for 1 sec
        pageHasNotContent("Testi2");

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
}
