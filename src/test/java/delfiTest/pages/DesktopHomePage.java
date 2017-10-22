package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DesktopHomePage {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(DesktopHomePage.class);

    private static final By TOP_ARTICLES = By.id("column1-top");
    private static final By FOLLOWING_SIBLING = By.xpath("following-sibling::*");

    public DesktopHomePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;

        LOGGER.info("Checking that top articles are present.");
        Assert.assertTrue("Top articles are not present.", baseFunctions.isPresentElement(TOP_ARTICLES));
    }

    public String getArticleTitle(int articleNumber) {
        LOGGER.info("Getting Title for article number: " + articleNumber);
        return baseFunctions.getElementWithWait(By.xpath("(//h3/a[@class='top2012-title'])[" + articleNumber + "]")).getText();
    }

    public WebElement getElementByTitle(String title) {
        LOGGER.info("Getting WebElement for article: " + title);
        return baseFunctions.getElementWithWait(By.linkText(title));
    }

    public void clickArticleByTitle(String title) {
        LOGGER.info("Clicking on article to get its view page.");
        baseFunctions.clickElement(getElementByTitle(title));
    }

    public int getCommentCount(String title) {
        LOGGER.info("Getting comment count for article: " + title);
        WebElement element = getElementByTitle(title);
        if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
            element = element.findElement(FOLLOWING_SIBLING);
            String elementText = element.getText();
            return commentHelper.stringToInt(elementText);
        } else {
            return 0;
        }
    }
}
