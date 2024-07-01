package petclinic.glue;

import org.openqa.selenium.By;

import cucumber.driver.SeleniumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class Home extends SeleniumDriver{
	
	By menuFindOwners = By.xpath("//div[@id='main-navbar']/ul[@class='nav navbar-nav me-auto']/li[@class='nav-item'][2]");
	
    @Before()
    public void initialization() {
        SeleniumDriver.initialize();
    }
    
    @Given("^User goes to Find Owners$")
    public void user_goes_to_Find_Owners() throws Throwable {
        currentDriver.findElement(menuFindOwners).click();
    }

    @After(order = 0) // Cucumber After Hook with order 1
    public void tearDown() {
        SeleniumDriver.close();
    }
}
