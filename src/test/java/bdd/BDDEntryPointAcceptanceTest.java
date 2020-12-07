package bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	publish = true,
	glue = "bdd",
	tags = "not @ignore",
	monochrome = true,
	plugin = {"pretty", "summary" ,"html:target/cucumber/cucumber.html", "json:target/cucumber.json"},
	features = {"src/test/resources/features"})
public class BDDEntryPointAcceptanceTest {

}
