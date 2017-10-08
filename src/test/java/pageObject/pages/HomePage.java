package pageObject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage {
    BaseFunctions baseFunctions;

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    private static final By ARTICLE = By.xpath("//h3[@class='top2012-title']");
    private static final By TITLE = By.xpath("//a[@class='top2012-title']");
    private static final By COMMENT_COUNT = By.xpath("//h3/a[@class='comment-count']");

    public HomePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public WebElement getFirstArticle() {
        LOGGER.info("Getting first article from homepage.");
        return baseFunctions.getElement(ARTICLE);
    }

    public String getTitle(WebElement article) {
        LOGGER.info("Getting Title.");
        return article.findElement(TITLE).getText();
    }

    public int getCommentCount(WebElement article) {
        LOGGER.info("Getting comment count.");
        String countText = article.findElement(COMMENT_COUNT).getText();
        return Integer.valueOf(countText.substring(1,countText.length()-1));
    }

    public ArticlePage openArticle() {
        LOGGER.info("Clicking on title.");
        baseFunctions.clickElement(TITLE);
        return new ArticlePage(baseFunctions);
    }
}
