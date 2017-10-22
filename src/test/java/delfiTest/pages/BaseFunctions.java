package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseFunctions {
    private static final Logger LOGGER = LogManager.getLogger(BaseFunctions.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String FIREFOX_DRIVER_PATH = "C:/geckodriver.exe";

    public BaseFunctions() {
        LOGGER.info("Setting system property for driver.");
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);

        LOGGER.info("Initializing new driver (opening).");
        this.driver = new FirefoxDriver();

        LOGGER.info("Setting driver wait functionality for 10 seconds.");
        wait = new WebDriverWait(driver, 10);

        LOGGER.info("Maximizing window.");
        driver.manage().window().maximize();
    }

    public void openUrl(String url) {
        LOGGER.info("Open URL: " + url);
        driver.get(url);
    }

    public WebElement getElement(By locator) {
        LOGGER.info("Getting element.");
        return driver.findElement(locator);
    }

    public WebElement getElementWithWait(By locator) {
        LOGGER.info("Getting element with wait.");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> findElements(By locator) {
        LOGGER.info("Finding elements.");
        return driver.findElements(locator);
    }

    public void clickElement(By locator) {
        LOGGER.info("Clicking element.");
        driver.findElement(locator).click();
    }

    public void clickElement(WebElement webElement) {
        LOGGER.info("Clicking element.");
        webElement.click();
    }

    public String getCurrentUrl() {
        LOGGER.info("Getting current URL.");
        return driver.getCurrentUrl();
    }

    public void scrollToBottom() {
        LOGGER.info("Scrolling to the bottom of page.");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
    }
}
