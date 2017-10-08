package pageObject.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ArticlePage {
    BaseFunctions baseFunctions;

    private static final Logger LOGGER = LogManager.getLogger(ArticlePage.class);

    private static final By ARTICLE = By.xpath("//h3[@class='top2012-title']");
    private static final By TITLE = By.xpath("//a[@class='top2012-title']");
    private static final By COMMENT_COUNT = By.xpath("//h3/a[@class='comment-count']");

    public ArticlePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }
}
