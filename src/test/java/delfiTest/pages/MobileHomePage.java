package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MobileHomePage {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(MobileHomePage.class);

    private static final By FOLLOWING_SIBLING = By.xpath("following-sibling::*");

    public MobileHomePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;


    }

    public String getArticleTitle(int articleNumber) {
        LOGGER.info("Getting Title for article number: " + articleNumber);
        return baseFunctions.getElementWithWait(By.xpath("(//div[@class='md-mosaic-title']/a[@class='md-scrollpos'])[" + articleNumber + "]")).getText();
    }

    public int getCommentCount(String title) {
        LOGGER.info("Getting comment count for article: " + title);
        WebElement element = baseFunctions.getElementWithWait(By.linkText(title));
        if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
            element = element.findElement(FOLLOWING_SIBLING);
            String elementText = element.getText();
            return commentHelper.stringToInt(elementText);
        } else {
            return 0;
        }
    }

    public WebElement getElementByTitle(String title) {
        LOGGER.info("Getting WebElement for article: " + title);
        return baseFunctions.getElementWithWait(By.linkText(title));
    }

    public void clickArticleByTitle(String title) {
        LOGGER.info("Clicking on article to get its view page.");
        baseFunctions.clickElement(getElementByTitle(title));
    }
}
