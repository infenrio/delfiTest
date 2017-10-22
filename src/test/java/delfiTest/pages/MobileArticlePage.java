package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MobileArticlePage {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(MobileArticlePage.class);

    private static final By ARTICLE = By.id("article");
    private static final By TITLE = By.xpath("//h1");
    private static final By COMMENT_COUNT = By.xpath("//div[@class='article-title']/a[@class='commentCount']");
    private static final By COMMENT_BUTTON = By.xpath("//a[contains(@href, 'com=1')]");

    public MobileArticlePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;

        LOGGER.info("Checking that article is present.");
        Assert.assertTrue("Article is not present.", baseFunctions.isPresentElement(ARTICLE));
    }

    public String getTitle() {
        LOGGER.info("Getting title from the article page.");
        WebElement element = baseFunctions.getElement(TITLE);
        return element.getText();
    }

    public int getCommentCount() {
        LOGGER.info("Getting comment count for article.");
        if (baseFunctions.findElements(COMMENT_COUNT).size() != 0) {
            String elementText = baseFunctions.getElement(COMMENT_COUNT).getText();
            return commentHelper.stringToInt(elementText);
        } else {
            return 0;
        }
    }

    public void goToArticleComments() {
        LOGGER.info("Clicking on button to read article comments.");
        baseFunctions.clickElement(COMMENT_BUTTON);
    }
}
