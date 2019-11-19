package winkkari;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    private final WebDriver driver = new HtmlUnitDriver();
    private final String baseUrl = "http://localhost:4567";

    @Given("User is on the new tip page")
    public void userIsOnTheNewTipPage() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("Author {string} and title {string} are entered")
    public void authorAndTitleAreEntered(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("New tip is created")
    public void newTipIsCreated() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
