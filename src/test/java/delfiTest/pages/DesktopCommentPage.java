package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DesktopCommentPage {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(DesktopCommentPage.class);

    private static final By COMMENT_TITLE_BLOCK = By.xpath("//div[@class='comments-about-title']");
    private static final By TITLE = By.xpath("//h1");
    private static final By REGISTERED_COMMENT_COUNT = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-reg')]/span");
    private static final By ANONYMOUS_COMMENT_COUNT = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-anon')]/span");

    public DesktopCommentPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;

        LOGGER.info("Checking that comment title block is present.");
        Assert.assertTrue("Comment title block is not present.", baseFunctions.isPresentElement(COMMENT_TITLE_BLOCK));
    }

    public String getTitle() {
        LOGGER.info("Getting title from the article page.");
        WebElement element = baseFunctions.getElement(TITLE);
        return element.getText();
    }

    public Integer getRegisteredComments() {
        LOGGER.info("Getting registered comment count.");
        WebElement element = baseFunctions.getElement(REGISTERED_COMMENT_COUNT);
        return commentHelper.stringToInt(element.getText());
    }

    public Integer getAnonymousComments() {
        LOGGER.info("Getting anonymous comment count.");
        WebElement element = baseFunctions.getElement(ANONYMOUS_COMMENT_COUNT);
        return commentHelper.stringToInt(element.getText());
    }
}
