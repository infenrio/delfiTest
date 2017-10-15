package pageObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pageObject.pages.ArticlePage;
import pageObject.pages.BaseFunctions;
import pageObject.pages.CommentPage;
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

        LOGGER.info("Getting article title from the article page.");
        String articleTitle = articlePage.getTitle();

        LOGGER.info("Getting comment count of the first article.");
        Integer articleCommentCount = articlePage.getCommentCount();

        LOGGER.info("Comparing title of the first article on article view and main pages.");
        Assert.assertEquals("Titles are not equal.", title, articleTitle);

        LOGGER.info("Comparing comment count of the first article on article view and main pages.");
        Assert.assertEquals("Comment counts are not equal.", commentCount, articleCommentCount.intValue());

        LOGGER.info("Open comment page for the article.");
        CommentPage commentPage = articlePage.openCommentPage();

        LOGGER.info("Getting title of the first article on comment page.");
        String titleComment = commentPage.getTitle();

        LOGGER.info("Getting registered comment count.");
        Integer regComments = commentPage.getRegisteredComments();

        LOGGER.info("Getting anonymous comment count.");
        Integer anonComments = commentPage.getAnonymousComments();

        LOGGER.info("Comparing title of the first article on article view and comment pages.");
        Assert.assertEquals("Titles are not equal.", title, titleComment);

        LOGGER.info("Comparing comment count of the first article on article view and comment pages.");
        Assert.assertEquals("Comment counts are not equal.", commentCount, regComments + anonComments);

        LOGGER.info("Test is successful.");
    }
}
