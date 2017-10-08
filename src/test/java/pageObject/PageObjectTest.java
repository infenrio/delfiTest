package pageObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pageObject.pages.ArticlePage;
import pageObject.pages.BaseFunctions;
import pageObject.pages.HomePage;

public class PageObjectTest {

    BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest.class);

    private static final String HOME_PAGE_URL = "http://rus.delfi.lv";

    @Test
    public void firstArticleTest() {
        LOGGER.info("Open homepage.");
        baseFunctions.openUrl(HOME_PAGE_URL);

        LOGGER.info("Getting title of the first article.");
        HomePage homePage = new HomePage(baseFunctions);
        WebElement article = homePage.getFirstArticle();
        String title = homePage.getTitle(article);

        LOGGER.info("Getting comment count of the first article.");
        int commentCount = homePage.getCommentCount(article);

        LOGGER.info("Open first article.");
        ArticlePage articlePage = homePage.openArticle();

        LOGGER.info("Getting title of the first article.");


        LOGGER.info("Getting comment count of the first article.");
        LOGGER.info("Comparing title of the first article on article view and main pages.");
        LOGGER.info("Comparing comment count of the first article on article view and main pages.");
        LOGGER.info("Getting comment count of the first article.");

        LOGGER.info("Open comment page for the article.");
        LOGGER.info("Getting title of the first article on comment page.");
        LOGGER.info("Getting registered comment count.");
        LOGGER.info("Getting anonymous comment count.");
        LOGGER.info("Comparing title of the first article on article view and comment pages.");
        LOGGER.info("Comparing comment count of the first article on article view and comment pages.");
        LOGGER.info("Test is successful.");
    }
}
