package petclinic.glue;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;

import cucumber.driver.SeleniumDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Owners extends SeleniumDriver{
	
	By btnFindOwner = By.xpath("//button[@class='btn btn-primary']");
	By firstOwner = By.xpath("//table[@id='owners']/tbody/tr[1]/td[1]/a");
	By addVisit = By.xpath("//table[@class='table-condensed']/tbody/tr/td[2]/a");
	By description = By.id("description");
	By btnAddVisit = By.xpath("//button[@class='btn btn-primary']");
	By visitDescription = By.xpath("//table[@class='table-condensed']/tbody/tr[1]/td[2]");
	By btnAddOwner = By.xpath("//a[@class='btn btn-primary']");
	By btnAddOwner2 = By.xpath("//button[@class='btn btn-primary']");
	By ownerTelephone = By.xpath("//tbody/tr[4]/td");
	By firstNameField = By.id("firstName");
	By lastNameField = By.id("lastName");
	By addressField = By.id("address");
	By cityField = By.id("city");
	By telephoneField = By.id("telephone");
	
	String exDesc = "example description";
	String ownerTel = "1234567890";

	@When("User clicks Find Owner")
	public void user_clicks_find_owner() {
	    currentDriver.findElement(btnFindOwner).click();
	}
	@Then("User clicks first owner")
	public void user_clicks_first_owner() {
	    currentDriver.findElement(firstOwner).click();
	}
	@Then("User clicks Add Visit in first pet")
	public void user_clicks_add_visit_in_first_pet() {
	    currentDriver.findElement(addVisit).click();
	}
	@Then("User types a description")
	public void user_types_a_description() {
	    currentDriver.findElement(description).sendKeys(exDesc);
	}
	@Then("User clicks Add Visit")
	public void user_clicks_add_visit() {
	    currentDriver.findElement(btnAddVisit).click();
	}
	@Then("New Visit appears in visit list")
	public void new_visit_appears_in_visit_list() {
	    String visitDesc = currentDriver.findElement(visitDescription).getText();
	    assertEquals(exDesc, visitDesc);
	}

	@When("User clicks Add Owner Button")
	public void user_clicks_add_owner_button() {
	    currentDriver.findElement(btnAddOwner).click();
	}
	
	@Then("User types first name {string}")
	public void user_types_first_name(String fname) {
	    currentDriver.findElement(firstNameField).sendKeys(fname);
	}
	
	@Then("User types last name {string}")
	public void user_types_last_name(String lname) {
	    currentDriver.findElement(lastNameField).sendKeys(lname);
	}
	
	@Then("User types address {string}")
	public void user_types_address(String address) {
	    currentDriver.findElement(addressField).sendKeys(address);
	}
	
	@Then("User types city {string}")
	public void user_types_city(String city) {
	    currentDriver.findElement(cityField).sendKeys(city);
	}
	
	@Then("User types telephone {int}")
	public void user_types_telephone(Integer tel) {
	    currentDriver.findElement(telephoneField).sendKeys(tel.toString());
	}
	
	@When("User clicks Add Owner")
	public void user_clicks_add_owner() {
	    currentDriver.findElement(btnAddOwner2).click();
	}
	@Then("New Owner appears in owner list")
	public void new_owner_appears_in_owner_list() {
	    String tel = currentDriver.findElement(ownerTelephone).getText();
	    assertEquals(ownerTel, tel);
	}





}
