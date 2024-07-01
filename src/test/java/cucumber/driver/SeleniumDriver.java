package cucumber.driver;

import java.time.Duration;
import java.util.HashMap;

import org.assertj.core.groups.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumDriver {

	/**
	 * Flag que determina si la ejecución de los tests es headless o no
	 */
	private static final Boolean headless;

	/**
	 * Flag que determina el navegador a usar
	 */
	private static final String browser;

	/**
	 * Driver para navegador actual
	 */
	public static WebDriver currentDriver;

	private static final String url;

	private static JavascriptExecutor jsExecutor;

	public static String downloadFilepath = "selenium" + System.getProperty("file.separator");

	/**
	 * Tiempo maximo de espera para el driver actual
	 */
	private static WebDriverWait wait;

	// private static FluentWait<WebDriver> wait;

	static {
		headless = false;
		browser = "CHROME";
		url = "http://localhost:8080";
	}

	public static String getBrowser() {
		return browser;
	}

	public static Boolean getHeadless() {
		return headless;
	}

	public static FluentWait<WebDriver> getFluentWait() {
		return wait;
	}

	public static WebDriver getCurrentDriver() {
		if (currentDriver == null) {
			throw new NullPointerException("Variable [currentDriver] is null: Call createDriver method before this");
		}
		return currentDriver;
	}

	public static void createDefaultDriver() {
		if (currentDriver != null) {
			close();
		}

		createDriver(browser);
	}

	public static void createDriver(String browser) {
		if (currentDriver != null) {
			close();
		}

		if (browser == null || "CHROME".equalsIgnoreCase(browser) || "GOOGLECHROME".equalsIgnoreCase(browser)) {
			currentDriver = createChromeDriver();
		}
		else if ("CHROMIUM".equalsIgnoreCase(browser)) {
			currentDriver = createChromiumDriver();

		}
		else if ("FIREFOX".equalsIgnoreCase(browser)) {
			currentDriver = createFirefoxDriver();

		}
		else {
			throw new IllegalArgumentException(String.format("Given browser name [%s] is not supported", browser));

		}
		jsExecutor = (JavascriptExecutor) currentDriver;
		wait = new WebDriverWait(currentDriver, Duration.ofSeconds(30));

	}

	public static void reset() {
		if (currentDriver == null) {
			throw new NullPointerException("Variable [currentDriver] is null: Call createDriver method before this");
		}

		Class<? extends WebDriver> driverClass = currentDriver.getClass();

		close();

		if (driverClass.equals(ChromeDriver.class)) {
			if (browser == null || "CHROME".equalsIgnoreCase(browser) || "GOOGLECHROME".equalsIgnoreCase(browser)) {
				currentDriver = createChromeDriver();
			}
			else if ("CHROMIUM".equalsIgnoreCase(browser)) {
				currentDriver = createChromiumDriver();
			}

		}
		else if (driverClass.equals(FirefoxDriver.class)) {
			currentDriver = createFirefoxDriver();

		}
		else {
			throw new IllegalArgumentException(
					String.format("Given browser class [%s] is not supported", driverClass.getName()));
		}
		jsExecutor = (JavascriptExecutor) currentDriver;
	}

	public static void close() {
		if (currentDriver != null) {
			currentDriver.quit();
			currentDriver = null;
		}
	}

	private static WebDriver createChromeDriver() {

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		if (headless) {
			options.addArguments("--headless=new");
		}
		options.addArguments("--window-size=1920,1080");

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		return driver;
	}

	private static WebDriver createChromiumDriver() {

		WebDriverManager.chromiumdriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		return driver;
	}

	private static WebDriver createFirefoxDriver() {
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();

		return driver;
	}

	public static void scrollDrown(String xPixels, String yPixels) {
		jsExecutor.executeScript("window.scrollBy(" + xPixels + "," + yPixels + ")", "");
		currentDriver.switchTo().activeElement();
	}

	public static void moveToElementBy(By by, boolean clickable) {

		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		WebElement element = currentDriver.findElement(by);
		moveToElement(element, clickable);
	}

	public static void moveToTopElementBy(By by, boolean clickable) {

		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		WebElement element = currentDriver.findElement(by);
		moveToTopElement(element, clickable);
	}

	public static void moveToElement(WebElement element, boolean clickable) {
		jsExecutor.executeScript("arguments[0].scrollIntoView()", element);
		wait.until(ExpectedConditions.visibilityOf(element));
		// Click on the element when is clickable
		if (clickable) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			jsExecutor.executeScript("arguments[0].click()", element);
		}
		currentDriver.switchTo().activeElement();
	}

	public static void moveToElement(By by, boolean clickable) {
		WebElement element = currentDriver.findElement(by);
		wait.until(ExpectedConditions.visibilityOf(element));
		jsExecutor.executeScript("arguments[0].scrollIntoView()", element);
		// Click on the element when is clickable
		if (clickable) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			jsExecutor.executeScript("arguments[0].click()", element);
		}
		currentDriver.switchTo().activeElement();
	}

	public static void moveToTopElement(WebElement element, boolean clickable) {

		// Move to top of the element
		jsExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
		// Click on the element when is clickable
		if (clickable) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			jsExecutor.executeScript("arguments[0].click()", element);
		}
		currentDriver.switchTo().activeElement();
	}

	public void esperarHastaQueDesaparezca(WebElement elemento) {
		wait.until(ExpectedConditions.invisibilityOf(elemento));
	}

	public void esperarHastaQueAparezca(WebElement elemento) {
		wait.until(ExpectedConditions.visibilityOf(elemento));
	}

	public static void initialize() {
		createDefaultDriver();
		currentDriver.get(url);
	}

}
