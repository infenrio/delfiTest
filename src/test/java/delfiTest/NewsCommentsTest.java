package delfiTest;

import delfiTest.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class NewsCommentsTest {
    BaseFunctions baseFunctions = new BaseFunctions();

    private static final Logger LOGGER = LogManager.getLogger(NewsCommentsTest.class);

    private String DESKTOP_PAGE_URL = "http://www.delfi.lv";
    private String MOBILE_PAGE_URL = "http://m.delfi.lv";

    private static final int ARTICLES_TO_CHECK = 5;

    private static final String ARTICLE_FOR_TEST = "Latvijas tuvumā manīta Krievijas armijas zemūdene un kuģis";

    @Test
    public void newsNamesAndCommentCountTest() {
        ArrayList<String> newsTitlesDesktop = new ArrayList<String>();
        ArrayList<String> newsTitlesMobile = new ArrayList<String>();
        boolean titleCheckIsOk = false;

        LOGGER.info("Trying up to 5 times to check whether first 5 articles match on desktop and mobile home pages.");
        for (int compareTryCount = 1; compareTryCount <= 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening desktop home page.");
            baseFunctions.openUrl(DESKTOP_PAGE_URL);
            DesktopHomePage desktopHomePage = new DesktopHomePage(baseFunctions);
            for (int i = 1; i <= ARTICLES_TO_CHECK + 1; i++) {
                LOGGER.info("Remembering title of the article Nr: " + i);
                newsTitlesDesktop.add(desktopHomePage.getArticleTitle(i));
            }
            LOGGER.info("Opening mobile home page.");
            baseFunctions.openUrl(MOBILE_PAGE_URL);
            MobileHomePage mobileHomePage = new MobileHomePage(baseFunctions);
            for (int i = 1; i <= ARTICLES_TO_CHECK + 1; i++) {
                LOGGER.info("Remembering title of the article Nr: " + i);
                newsTitlesMobile.add(mobileHomePage.getArticleTitle(i));
            }
            LOGGER.info("Checking that each of the first 5 articles on main page had same titles on desktop and mobile.");
            boolean firstFiveArticlesMatch = true;
            for (int i = 1; i <= ARTICLES_TO_CHECK; i++) {
                LOGGER.info("Comparing titles of the article Nr: " + i);
                if (!newsTitlesDesktop.get(i-1).equals(newsTitlesMobile.get(i-1))) {
                    LOGGER.info("Found unequal article title.");
                    firstFiveArticlesMatch = false;
                    break;
                }
            }
            if (!firstFiveArticlesMatch) {
                LOGGER.info("First 5 articles don't match, checking that first desktop article is among 2nd-6th articles of mobile.");
                boolean articleIsPresent = false;
                for (int i = 1; i <= 5; i++) {
                    int mobileArticleNumber = i+1;
                    LOGGER.info("Comapring title of first desktop article with title of " + mobileArticleNumber + " mobile article.");
                    if (newsTitlesDesktop.get(0).equals(newsTitlesMobile.get(i))) {
                        LOGGER.info("Article titles match.");
                        articleIsPresent = true;
                        break;
                    }
                }
                if (!articleIsPresent) {
                    LOGGER.info("Title of first desktop article wasn't present among 2nd-6th articles of mobile.");
                    break;
                }
                newsTitlesDesktop = new ArrayList<String>();
                newsTitlesMobile = new ArrayList<String>();
            } else {
                LOGGER.info("Title check was OK.");
                titleCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("First 5 title check on main pages failed.", titleCheckIsOk);

        int desktopArticleCommentCount, mobileArticleCommentCount;
        boolean CommentCheckIsOk;

        LOGGER.info("Checking that first 5 articles have the same number of comments on desktop and mobile.");
        for (int i = 1; i <= ARTICLES_TO_CHECK; i++) {
            CommentCheckIsOk = false;
            LOGGER.info("Trying up to 5 times to compare comment count for article number: " + i);
            for (int compareTryCount = 1; compareTryCount <= 5; compareTryCount++) {
                LOGGER.info("Performing try number: " + compareTryCount);
                LOGGER.info("Opening desktop home page.");
                baseFunctions.openUrl(DESKTOP_PAGE_URL);
                DesktopHomePage desktopHomePage = new DesktopHomePage(baseFunctions);
                LOGGER.info("Getting article's comment count.");
                desktopArticleCommentCount = desktopHomePage.getCommentCount(newsTitlesDesktop.get(i-1));
                LOGGER.info("Opening mobile home page.");
                baseFunctions.openUrl(MOBILE_PAGE_URL);
                MobileHomePage mobileHomePage = new MobileHomePage(baseFunctions);
                LOGGER.info("Getting article's comment count.");
                mobileArticleCommentCount = mobileHomePage.getCommentCount(newsTitlesDesktop.get(i-1));
                if(desktopArticleCommentCount == mobileArticleCommentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
            Assert.assertTrue("Comment count isn't same for article Nr: " + i, CommentCheckIsOk);
        }

        String urlForPreviousPage;
        int homePageCommentCount, articlePageCommentCount, commentPageCommentCount;

        LOGGER.info("Checking each article inside article view and comment section. Desktop version.");
        for (int i = 1; i <= ARTICLES_TO_CHECK; i++) {
            LOGGER.info("Checking article number: " + i);
            LOGGER.info("Opening desktop home page.");
            baseFunctions.openUrl(DESKTOP_PAGE_URL);
            DesktopHomePage desktopHomePage = new DesktopHomePage(baseFunctions);
            LOGGER.info("Clicking on article title.");
            desktopHomePage.clickArticleByTitle(newsTitlesDesktop.get(i - 1));
            DesktopArticlePage desktopArticlePage = new DesktopArticlePage(baseFunctions);
            LOGGER.info("Checking article title on article page.");
            String articleText = desktopArticlePage.getTitle();
            Assert.assertEquals("Title inside news isn't same.", newsTitlesDesktop.get(i - 1), articleText);

            CommentCheckIsOk = false;
            LOGGER.info("Trying up to 5 times to compare comment count on main page and inside article view (desktop).");
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                LOGGER.info("Performing try number: " + compareTryCount);
                LOGGER.info("Opening desktop home page.");
                baseFunctions.openUrl(DESKTOP_PAGE_URL);
                desktopHomePage = new DesktopHomePage(baseFunctions);
                LOGGER.info("Getting article's comment count.");
                homePageCommentCount = desktopHomePage.getCommentCount(newsTitlesDesktop.get(i - 1));
                LOGGER.info("Clicking on article title.");
                desktopHomePage.clickArticleByTitle(newsTitlesDesktop.get(i - 1));
                desktopArticlePage = new DesktopArticlePage(baseFunctions);
                articlePageCommentCount = desktopArticlePage.getCommentCount();
                if (homePageCommentCount == articlePageCommentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

            urlForPreviousPage = baseFunctions.getCurrentUrl();
            LOGGER.info("Opening article comment page.");
            desktopArticlePage.goToArticleComments();
            DesktopCommentPage desktopCommentPage = new DesktopCommentPage(baseFunctions);
            LOGGER.info("Checking article title on comment page.");
            articleText = desktopCommentPage.getTitle();
            //TO DO - Extracting text about comments in title.
            if(newsTitlesDesktop.get(i - 1).lastIndexOf(':') != articleText.lastIndexOf(':')) {
                articleText = articleText.substring(0, articleText.lastIndexOf(':'));
            }
            Assert.assertEquals("Title on comment page isn't same.", newsTitlesDesktop.get(i - 1), articleText);
            CommentCheckIsOk = false;
            LOGGER.info("Trying up to 5 times to compare comment count on article view page and on comment page (desktop).");
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                LOGGER.info("Performing try number: " + compareTryCount);
                LOGGER.info("Opening article view page.");
                baseFunctions.openUrl(urlForPreviousPage);
                desktopArticlePage = new DesktopArticlePage(baseFunctions);
                LOGGER.info("Getting article's comment count.");
                articlePageCommentCount = desktopArticlePage.getCommentCount();
                LOGGER.info("Opening article comment page.");
                desktopArticlePage.goToArticleComments();
                desktopCommentPage = new DesktopCommentPage(baseFunctions);
                commentPageCommentCount = desktopCommentPage.getAnonymousComments() + desktopCommentPage.getRegisteredComments();
                if (articlePageCommentCount == commentPageCommentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
        }

        LOGGER.info("Checking each article inside article view and comment section. Mobile version.");
        for (int i = 1; i <= ARTICLES_TO_CHECK; i++) {
            LOGGER.info("Checking article number: " + i);
            LOGGER.info("Opening mobile home page.");
            baseFunctions.openUrl(MOBILE_PAGE_URL);
            MobileHomePage mobileHomePage = new MobileHomePage(baseFunctions);
            LOGGER.info("Clicking on article title.");
            mobileHomePage.clickArticleByTitle(newsTitlesDesktop.get(i - 1));
            MobileArticlePage mobileArticlePage = new MobileArticlePage(baseFunctions);
            LOGGER.info("Checking article title on article page.");
            Assert.assertEquals("Title inside news isn't same.", newsTitlesDesktop.get(i - 1), mobileArticlePage.getTitle());

            CommentCheckIsOk = false;
            LOGGER.info("Trying up to 5 times to compare comment count on main page and inside article view (mobile).");
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                LOGGER.info("Performing try number: " + compareTryCount);
                LOGGER.info("Opening mobile home page.");
                baseFunctions.openUrl(MOBILE_PAGE_URL);
                LOGGER.info("Getting article's comment count.");
                homePageCommentCount = mobileHomePage.getCommentCount(newsTitlesDesktop.get(i - 1));
                LOGGER.info("Clicking on article title.");
                mobileHomePage.clickArticleByTitle(newsTitlesDesktop.get(i - 1));
                mobileArticlePage = new MobileArticlePage(baseFunctions);
                articlePageCommentCount = mobileArticlePage.getCommentCount();
                if (homePageCommentCount == articlePageCommentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

            urlForPreviousPage = baseFunctions.getCurrentUrl();
            LOGGER.info("Opening article comment page.");
            mobileArticlePage.goToArticleComments();
            MobileCommentPage mobileCommentPage = new MobileCommentPage(baseFunctions);
            LOGGER.info("Checking article title on comment page.");
            Assert.assertEquals("Title on comment page isn't same.", newsTitlesDesktop.get(i - 1), mobileCommentPage.getTitle());
            CommentCheckIsOk = false;
            LOGGER.info("Trying up to 5 times to compare comment count on article view page and on comment page (mobile).");
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                LOGGER.info("Performing try number: " + compareTryCount);
                LOGGER.info("Opening article view page.");
                baseFunctions.openUrl(urlForPreviousPage);
                mobileArticlePage = new MobileArticlePage(baseFunctions);
                LOGGER.info("Getting article's comment count.");
                articlePageCommentCount = mobileArticlePage.getCommentCount();
                LOGGER.info("Opening article comment page.");
                mobileArticlePage.goToArticleComments();
                mobileCommentPage = new MobileCommentPage(baseFunctions);
                commentPageCommentCount = mobileCommentPage.getAnonymousComments() + mobileCommentPage.getRegisteredComments();
                if (articlePageCommentCount == commentPageCommentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
        }
    }

    @Test
    public void specificArticleTest() {
        int desktopArticleCommentCount, mobileArticleCommentCount, articlePageCommentCount, commentPageCommentCount;
        boolean CommentCheckIsOk = false;
        LOGGER.info("Checking that the article has the same number of comments on desktop and mobile.");
        LOGGER.info("Trying up to 5 times to compare comment count for article.");
        for (int compareTryCount = 1; compareTryCount <= 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening desktop home page.");
            baseFunctions.openUrl(DESKTOP_PAGE_URL);
            baseFunctions.scrollToBottom();
            DesktopHomePage desktopHomePage = new DesktopHomePage(baseFunctions);
            LOGGER.info("Getting article's comment count.");
            desktopArticleCommentCount = desktopHomePage.getCommentCount(ARTICLE_FOR_TEST);
            LOGGER.info("Opening mobile home page.");
            baseFunctions.openUrl(MOBILE_PAGE_URL);
            baseFunctions.scrollToBottom();
            MobileHomePage mobileHomePage = new MobileHomePage(baseFunctions);
            LOGGER.info("Getting article's comment count.");
            mobileArticleCommentCount = mobileHomePage.getCommentCount(ARTICLE_FOR_TEST);
            if(desktopArticleCommentCount == mobileArticleCommentCount) {
                CommentCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        String urlForPreviousPage;

        LOGGER.info("Checking article inside article view and comment section. Desktop version.");
        LOGGER.info("Opening desktop home page.");
        baseFunctions.openUrl(DESKTOP_PAGE_URL);
        baseFunctions.scrollToBottom();
        DesktopHomePage desktopHomePage = new DesktopHomePage(baseFunctions);
        LOGGER.info("Clicking on article title.");
        desktopHomePage.clickArticleByTitle(ARTICLE_FOR_TEST);
        DesktopArticlePage desktopArticlePage = new DesktopArticlePage(baseFunctions);
        LOGGER.info("Checking article title on article page.");
        String articleText = desktopArticlePage.getTitle();
        Assert.assertEquals("Title inside news isn't same.", ARTICLE_FOR_TEST, articleText);

        CommentCheckIsOk = false;
        LOGGER.info("Trying up to 5 times to compare comment count on main page and inside article view (desktop).");
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening desktop home page.");
            baseFunctions.openUrl(DESKTOP_PAGE_URL);
            baseFunctions.scrollToBottom();
            desktopHomePage = new DesktopHomePage(baseFunctions);
            LOGGER.info("Getting article's comment count.");
            desktopArticleCommentCount = desktopHomePage.getCommentCount(ARTICLE_FOR_TEST);
            LOGGER.info("Clicking on article title.");
            desktopHomePage.clickArticleByTitle(ARTICLE_FOR_TEST);
            desktopArticlePage = new DesktopArticlePage(baseFunctions);
            articlePageCommentCount = desktopArticlePage.getCommentCount();
            if (desktopArticleCommentCount == articlePageCommentCount) {
                CommentCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        urlForPreviousPage = baseFunctions.getCurrentUrl();
        LOGGER.info("Opening article comment page.");
        desktopArticlePage.goToArticleComments();
        DesktopCommentPage desktopCommentPage = new DesktopCommentPage(baseFunctions);
        LOGGER.info("Checking article title on comment page.");
        articleText = desktopCommentPage.getTitle();
        //TO DO - Extracting text about comments in title.
        if(ARTICLE_FOR_TEST.lastIndexOf(':') != articleText.lastIndexOf(':')) {
            articleText = articleText.substring(0, articleText.lastIndexOf(':'));
        }
        Assert.assertEquals("Title on comment page isn't same.", ARTICLE_FOR_TEST, articleText);
        CommentCheckIsOk = false;
        LOGGER.info("Trying up to 5 times to compare comment count on article view page and on comment page (desktop).");
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening article view page.");
            baseFunctions.openUrl(urlForPreviousPage);
            desktopArticlePage = new DesktopArticlePage(baseFunctions);
            LOGGER.info("Getting article's comment count.");
            articlePageCommentCount = desktopArticlePage.getCommentCount();
            LOGGER.info("Opening article comment page.");
            desktopArticlePage.goToArticleComments();
            desktopCommentPage = new DesktopCommentPage(baseFunctions);
            commentPageCommentCount = desktopCommentPage.getAnonymousComments() + desktopCommentPage.getRegisteredComments();
            if (articlePageCommentCount == commentPageCommentCount) {
                CommentCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        LOGGER.info("Checking article inside article view and comment section. Mobile version.");
        LOGGER.info("Opening mobile home page.");
        baseFunctions.openUrl(MOBILE_PAGE_URL);
        baseFunctions.scrollToBottom();
        MobileHomePage mobileHomePage = new MobileHomePage(baseFunctions);
        LOGGER.info("Clicking on article title.");
        mobileHomePage.clickArticleByTitle(ARTICLE_FOR_TEST);
        MobileArticlePage mobileArticlePage = new MobileArticlePage(baseFunctions);
        LOGGER.info("Checking article title on article page.");
        Assert.assertEquals("Title inside news isn't same.", ARTICLE_FOR_TEST, mobileArticlePage.getTitle());

        CommentCheckIsOk = false;
        LOGGER.info("Trying up to 5 times to compare comment count on main page and inside article view (mobile).");
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening mobile home page.");
            baseFunctions.openUrl(MOBILE_PAGE_URL);
            baseFunctions.scrollToBottom();
            LOGGER.info("Getting article's comment count.");
            mobileArticleCommentCount = mobileHomePage.getCommentCount(ARTICLE_FOR_TEST);
            LOGGER.info("Clicking on article title.");
            mobileHomePage.clickArticleByTitle(ARTICLE_FOR_TEST);
            mobileArticlePage = new MobileArticlePage(baseFunctions);
            articlePageCommentCount = mobileArticlePage.getCommentCount();
            if (mobileArticleCommentCount == articlePageCommentCount) {
                CommentCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        urlForPreviousPage = baseFunctions.getCurrentUrl();
        LOGGER.info("Opening article comment page.");
        mobileArticlePage.goToArticleComments();
        MobileCommentPage mobileCommentPage = new MobileCommentPage(baseFunctions);
        LOGGER.info("Checking article title on comment page.");
        Assert.assertEquals("Title on comment page isn't same.", ARTICLE_FOR_TEST, mobileCommentPage.getTitle());
        CommentCheckIsOk = false;
        LOGGER.info("Trying up to 5 times to compare comment count on article view page and on comment page (mobile).");
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            LOGGER.info("Performing try number: " + compareTryCount);
            LOGGER.info("Opening article view page.");
            baseFunctions.openUrl(urlForPreviousPage);
            mobileArticlePage = new MobileArticlePage(baseFunctions);
            LOGGER.info("Getting article's comment count.");
            articlePageCommentCount = mobileArticlePage.getCommentCount();
            LOGGER.info("Opening article comment page.");
            mobileArticlePage.goToArticleComments();
            mobileCommentPage = new MobileCommentPage(baseFunctions);
            commentPageCommentCount = mobileCommentPage.getAnonymousComments() + mobileCommentPage.getRegisteredComments();
            if (articlePageCommentCount == commentPageCommentCount) {
                CommentCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
    }
}