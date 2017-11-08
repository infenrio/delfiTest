package delfiStepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.util.List;

public class HomePageStepDefs {
    @Given("Print test annotation (.*)")
    public void print_test_annotation(String annotation) {
        System.out.println(annotation);
    }

    @When("we are sending data to the server")
    public void sending_data_to_server() {

    }

    @Given("the weather is (.*) with a temperature ([0-9*])")
    public void weather_data_is(String weather, BigDecimal temperature) {

    }

    @Then("Temperatures are:")
    public void check_temperatures(List<BigDecimal> temperatures) {

    }
}
