package pageObject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class BaseFunctions {
    private static final Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    WebDriver driver;
    private static final String FIREFOX_DRIVER_PATH = "C:/geckodriver.exe";

    public BaseFunctions() {
        LOGGER.info("Setting system property for driver.");
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);

        LOGGER.info("Initializing new driver (opening).");
        this.driver = new FirefoxDriver();

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

    public void clickElement(By locator) {
        LOGGER.info("Clicking element.");
        driver.findElement(locator).click();
    }

    public List<WebElement> findElements(By locator) {
        LOGGER.info("Finding elements.");
        return driver.findElements(locator);
    }
}
