package delfiTest.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MobileCommentPage {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(MobileCommentPage.class);

    private static final By TITLE = By.xpath("//h1[@class='comments-about-title-h1']");
    private static final By REGISTERED_COMMENT_COUNT = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-reg')]/span");
    private static final By ANONYMOUS_COMMENT_COUNT = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-anon')]/span");

    public MobileCommentPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;

    }

    public String getTitle() {
        LOGGER.info("Getting title from the article page.");
        WebElement element = baseFunctions.getElement(TITLE);
        return element.getText();
    }

    public int getRegisteredComments() {
        LOGGER.info("Getting registered comment count.");
        if (baseFunctions.findElements(REGISTERED_COMMENT_COUNT).size() != 0) {
            WebElement element = baseFunctions.getElement(REGISTERED_COMMENT_COUNT);
            String elementText = element.getText();
            return commentHelper.stringToInt(elementText);
        } else {
            return 0;
        }
    }

    public Integer getAnonymousComments() {
        LOGGER.info("Getting anonymous comment count.");
        if (baseFunctions.findElements(ANONYMOUS_COMMENT_COUNT).size() != 0) {
            WebElement element = baseFunctions.getElement(ANONYMOUS_COMMENT_COUNT);
            String elementText = element.getText();
            return commentHelper.stringToInt(elementText);
        } else {
            return 0;
        }
    }
}
