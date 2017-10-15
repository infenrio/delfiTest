package pageObject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePage {
    BaseFunctions baseFunctions;
    CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(ArticlePage.class);

    private static final By ARTICLE = By.xpath("//h3[@class='top2012-title']");
    private static final By TITLE = By.xpath("//h1[@class='article-title']/span");
    private static final By COMMENT_COUNT = By.xpath("//div[@class='article-title']/a[@class='comment-count']");

    public ArticlePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public String getTitle() {
        LOGGER.info("Getting title from the article page.");
        WebElement element = baseFunctions.getElement(TITLE);
        return element.getText();
    }

    public Integer getCommentCount() {
        LOGGER.info("Getting comment count from the article page.");
        WebElement element = baseFunctions.getElement(COMMENT_COUNT);
        return commentHelper.stringToInt(element.getText());
    }

    public CommentPage openCommentPage() {
        LOGGER.info("Openning comment page.");
        baseFunctions.clickElement(COMMENT_COUNT);
        return new CommentPage(baseFunctions);
    }
}
