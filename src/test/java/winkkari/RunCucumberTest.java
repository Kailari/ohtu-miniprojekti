package winkkari;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/resources/winkkari",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunCucumberTest {
    @ClassRule
    public static ServerRule server = new ServerRule(4567);
}
