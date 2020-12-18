package bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	glue = "bdd",
	tags = "not @ignore",
	monochrome = true,
	plugin = {"pretty", "summary" ,"html:target/cucumber/cucumber.html", "json:target/cucumber.json"},
	features = {"classpath:features/"})
public class BDDEntryPointAcceptanceTest {

}
