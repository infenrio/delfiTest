package delfiTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class NewsCommentsTest {
    private String DESKTOP_PAGE = "http://www.delfi.lv";
    private String MOBILE_PAGE = "http://m.delfi.lv";

    private static final By FOLLOWING_SIBLING = By.xpath("following-sibling::*");
    private static final By REGISTERED_COMMENTS = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-reg')]/span");
    private static final By ANONYMOUS_COMMENTS = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-anon')]/span");
    private static final By ARTICLE_TITLE_DESKTOP = By.xpath("//h1[@class='article-title']");

    private static final int ARTICLES_TO_CHECK = 5;

    private static final String ARTICLE_FOR_TEST = "Rudenīgi foto: Dzērvju bari lido prom";

    @Test
    public void newsNamesAndCommentCountTest() {
        System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
//        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element;
        String elementText;
        ArrayList<String> newsTitlesDesktop = new ArrayList<String>();
        ArrayList<String> newsTitlesMobile = new ArrayList<String>();
        boolean titleCheckIsOk = false;
        //Try up to 5 times to check whether first 5 articles match on desktop and mobile.
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            driver.get(DESKTOP_PAGE);
            for (int i = 1; i <= ARTICLES_TO_CHECK + 5; i++) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//h3/a[@class='top2012-title'])[" + i + "]")));
                newsTitlesDesktop.add(element.getText());
            }
            driver.get(MOBILE_PAGE);
            for (int i = 1; i <= ARTICLES_TO_CHECK + 5; i++) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='md-mosaic-title']/a[@class='md-scrollpos'])[" + i + "]")));
                newsTitlesMobile.add(element.getText());
            }
            boolean firstFiveArticlesMatch = true;
            for (int i = 0; i < ARTICLES_TO_CHECK; i++) {
                if (!newsTitlesDesktop.get(i).equals(newsTitlesMobile.get(i))) {
                    firstFiveArticlesMatch = false;
                    break;
                }
            }
            // If articles don't match, check that first desktop article is among 2nd-6th articles of mobile.
            if (!firstFiveArticlesMatch) {
                boolean articleIsPresent = false;
                for (int i = 0; i < 5; i++) {
                    if (newsTitlesDesktop.get(0).equals(newsTitlesMobile.get(i + 1))) {
                        articleIsPresent = true;
                        break;
                    }
                }
                if (!articleIsPresent) {
                    break;
                }
                newsTitlesDesktop = new ArrayList<String>();
                newsTitlesMobile = new ArrayList<String>();
            } else {
                titleCheckIsOk = true;
                break;
            }
        }
        Assert.assertTrue("Title check failed.", titleCheckIsOk);

        int commentCount = 0;
        boolean CommentCheckIsOk;
        //Check that first 5 articles have the same number of comments on desktop and mobile.
        for (int i = 0; i < ARTICLES_TO_CHECK; i++) {
            CommentCheckIsOk = false;
            //Try up to 5 times to compare comment count.
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                driver.get(DESKTOP_PAGE);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i))));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if(elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                        commentCount = 0;
                    } else {
                        commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                } else {
                    commentCount = 0;
                }
                driver.get(MOBILE_PAGE);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i))));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if(elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                        if(commentCount == 0) {
                            CommentCheckIsOk = true;
                            break;
                        }
                    }
                    if(commentCount == Integer.parseInt(elementText.substring(1, elementText.length() - 1))) {
                        CommentCheckIsOk = true;
                        break;
                    }
                } else {
                    if(commentCount == 0) {
                        CommentCheckIsOk = true;
                        break;
                    }
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
        }

        String urlForPreviousPage;

        //Check each article inside article view and comment section. Desktop version.
        for (int i = 0; i < ARTICLES_TO_CHECK; i++) {
            driver.get(DESKTOP_PAGE);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i)))).click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
            elementText = element.getText();
            Assert.assertTrue("Title inside news isn't same.", elementText.equals(newsTitlesDesktop.get(i)));

            CommentCheckIsOk = false;
            //Try up to 5 times to compare comment count on main page and inside article view (desktop).
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                driver.get(DESKTOP_PAGE);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i))));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if (elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                        commentCount = 0;
                    } else {
                        commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                } else {
                    commentCount = 0;
                }
                wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i)))).click();
                element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if(Integer.parseInt(elementText.substring(1, elementText.length() - 1)) == commentCount) {
                        CommentCheckIsOk = true;
                        break;
                    }
                } else {
                    if(commentCount == 0) {
                        CommentCheckIsOk = true;
                        break;
                    }
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

            //If comments are present, check comment section of the article (desktop).
            if(commentCount != 0) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
                element = element.findElement(FOLLOWING_SIBLING);
                urlForPreviousPage = driver.getCurrentUrl();
                element.click();
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
                elementText = element.getText();
                if(newsTitlesDesktop.get(i).lastIndexOf(':') == elementText.lastIndexOf(':')) {
                    Assert.assertTrue("Title inside news comments isn't same:\n" + element.getText() + "\n vs \n" + newsTitlesDesktop.get(i), element.getText().equals(newsTitlesDesktop.get(i)));
                } else {
                    String titleWithoutCommentPart = element.getText().substring(0, elementText.lastIndexOf(':'));
                    Assert.assertTrue("Title inside news comments isn't same:\n" + titleWithoutCommentPart + "\n vs \n" + newsTitlesDesktop.get(i), titleWithoutCommentPart.equals(newsTitlesDesktop.get(i)));
                }
                driver.get(urlForPreviousPage);

                CommentCheckIsOk = false;
                //Try up to 5 times to compare comment count inside article view and comment section (desktop).
                for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                    int commentCountReg = 0;
                    int commentCountAnon = 0;
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    urlForPreviousPage = driver.getCurrentUrl();
                    element.click();
                    if(driver.findElements(REGISTERED_COMMENTS).size() > 0) {
                        element = wait.until(ExpectedConditions.presenceOfElementLocated(REGISTERED_COMMENTS));
                        elementText = element.getText();
                        commentCountReg = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                    if(driver.findElements(ANONYMOUS_COMMENTS).size() > 0) {
                        element = wait.until(ExpectedConditions.presenceOfElementLocated(ANONYMOUS_COMMENTS));
                        elementText = element.getText();
                        commentCountAnon = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                    if(commentCount == commentCountReg + commentCountAnon) {
                        CommentCheckIsOk = true;
                        break;
                    } else {
                        driver.get(urlForPreviousPage);
                    }
                }
                Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
            }
        }

        //Check each article inside article view and comment section. Mobile version.
        for (int i = 0; i < ARTICLES_TO_CHECK; i++) {
            driver.get(MOBILE_PAGE);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i)))).click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
            elementText = element.getText();
            Assert.assertTrue("Title inside news isn't same.", elementText.equals(newsTitlesDesktop.get(i)));

            CommentCheckIsOk = false;
            //Try up to 5 times to compare comment count on main page and inside article view (mobile).
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                driver.get(MOBILE_PAGE);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i))));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if (elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                        commentCount = 0;
                    } else {
                        commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                } else {
                    commentCount = 0;
                }
                wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(newsTitlesDesktop.get(i)))).click();
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
                if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    if(Integer.parseInt(elementText.substring(1, elementText.length() - 1)) == commentCount) {
                        CommentCheckIsOk = true;
                        break;
                    }
                } else {
                    if(commentCount == 0) {
                        CommentCheckIsOk = true;
                        break;
                    }
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

            //If comments are present, check comment section of the article (mobile).
            if(commentCount != 0) {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
                element = element.findElement(FOLLOWING_SIBLING);
                urlForPreviousPage = driver.getCurrentUrl();
                element.click();
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1/a/span[@class='text']")));
                elementText = element.getText();
                Assert.assertTrue("Title inside news comments isn't same:\n" + elementText + "\n vs \n" + newsTitlesDesktop.get(i), element.getText().equals(newsTitlesDesktop.get(i)));
                driver.get(urlForPreviousPage);

                CommentCheckIsOk = false;
                //Try up to 5 times to compare comment count inside article view and comment section (mobile).
                for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                    int commentCountReg = 0;
                    int commentCountAnon = 0;
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='article-title']/h1")));
                    element = element.findElement(FOLLOWING_SIBLING);
                    elementText = element.getText();
                    commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    urlForPreviousPage = driver.getCurrentUrl();
                    element.click();
                    if(driver.findElements(REGISTERED_COMMENTS).size() > 0) {
                        element = wait.until(ExpectedConditions.presenceOfElementLocated(REGISTERED_COMMENTS));
                        elementText = element.getText();
                        commentCountReg = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                    if(driver.findElements(ANONYMOUS_COMMENTS).size() > 0) {
                        element = wait.until(ExpectedConditions.presenceOfElementLocated(ANONYMOUS_COMMENTS));
                        elementText = element.getText();
                        commentCountAnon = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                    }
                    if(commentCount == commentCountReg + commentCountAnon) {
                        CommentCheckIsOk = true;
                        break;
                    } else {
                        driver.get(urlForPreviousPage);
                    }
                }
                Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
            }
        }
        driver.quit();
    }

    @Test
    public void specificArticleTest() {
        System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element;
        String elementText;
        int commentCount = 0;
        boolean CommentCheckIsOk = false;
        JavascriptExecutor jsx = (JavascriptExecutor)driver;

        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            driver.get(DESKTOP_PAGE);
            jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST)));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if(elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                    commentCount = 0;
                } else {
                    commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
            } else {
                commentCount = 0;
            }
            driver.get(MOBILE_PAGE);
            jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST)));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if(elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                    if(commentCount == 0) {
                        CommentCheckIsOk = true;
                        break;
                    }
                }
                if(commentCount == Integer.parseInt(elementText.substring(1, elementText.length() - 1))) {
                    CommentCheckIsOk = true;
                    break;
                }
            } else {
                if(commentCount == 0) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        String urlForPreviousPage;

        //Check article inside article view and comment section. Desktop version.
        driver.get(DESKTOP_PAGE);
        jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST))).click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
        elementText = element.getText();
        Assert.assertTrue("Title inside news isn't same.", elementText.equals(ARTICLE_FOR_TEST));

        CommentCheckIsOk = false;
        //Try up to 5 times to compare comment count on main page and inside article view (desktop).
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            driver.get(DESKTOP_PAGE);
            jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST)));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if (elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                    commentCount = 0;
                } else {
                    commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
            } else {
                commentCount = 0;
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST))).click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if(Integer.parseInt(elementText.substring(1, elementText.length() - 1)) == commentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            } else {
                if(commentCount == 0) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        //If comments are present, check comment section of the article (desktop).
        if(commentCount != 0) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
            element = element.findElement(FOLLOWING_SIBLING);
            urlForPreviousPage = driver.getCurrentUrl();
            element.click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
            elementText = element.getText();
            if(ARTICLE_FOR_TEST.lastIndexOf(':') == elementText.lastIndexOf(':')) {
                Assert.assertTrue("Title inside news comments isn't same:\n" + element.getText() + "\n vs \n" + ARTICLE_FOR_TEST, element.getText().equals(ARTICLE_FOR_TEST));
            } else {
                String titleWithoutCommentPart = element.getText().substring(0, elementText.lastIndexOf(':'));
                Assert.assertTrue("Title inside news comments isn't same:\n" + titleWithoutCommentPart + "\n vs \n" + ARTICLE_FOR_TEST, titleWithoutCommentPart.equals(ARTICLE_FOR_TEST));
            }
            driver.get(urlForPreviousPage);

            CommentCheckIsOk = false;
            //Try up to 5 times to compare comment count inside article view and comment section (desktop).
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                int commentCountReg = 0;
                int commentCountAnon = 0;
                element = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_TITLE_DESKTOP));
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                urlForPreviousPage = driver.getCurrentUrl();
                element.click();
                if(driver.findElements(REGISTERED_COMMENTS).size() > 0) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(REGISTERED_COMMENTS));
                    elementText = element.getText();
                    commentCountReg = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
                if(driver.findElements(ANONYMOUS_COMMENTS).size() > 0) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(ANONYMOUS_COMMENTS));
                    elementText = element.getText();
                    commentCountAnon = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
                if(commentCount == commentCountReg + commentCountAnon) {
                    CommentCheckIsOk = true;
                    break;
                } else {
                    driver.get(urlForPreviousPage);
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
        }

        //Check article inside article view and comment section. Mobile version.
        driver.get(MOBILE_PAGE);
        jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST)));
        jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
        element.click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        elementText = element.getText();
        Assert.assertTrue("Title inside news isn't same.", elementText.equals(ARTICLE_FOR_TEST));

        CommentCheckIsOk = false;
        //Try up to 5 times to compare comment count on main page and inside article view (mobile).
        for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
            driver.get(MOBILE_PAGE);
            jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST)));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if (elementText.lastIndexOf('(') < 0 && elementText.lastIndexOf(')') < 0) {
                    commentCount = 0;
                } else {
                    commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
            } else {
                commentCount = 0;
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(ARTICLE_FOR_TEST))).click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
            if (element.findElements(FOLLOWING_SIBLING).size() != 0) {
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                if(Integer.parseInt(elementText.substring(1, elementText.length() - 1)) == commentCount) {
                    CommentCheckIsOk = true;
                    break;
                }
            } else {
                if(commentCount == 0) {
                    CommentCheckIsOk = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);

        //If comments are present, check comment section of the article (mobile).
        if(commentCount != 0) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
            element = element.findElement(FOLLOWING_SIBLING);
            urlForPreviousPage = driver.getCurrentUrl();
            element.click();
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1/a/span[@class='text']")));
            elementText = element.getText();
            Assert.assertTrue("Title inside news comments isn't same:\n" + elementText + "\n vs \n" + ARTICLE_FOR_TEST, element.getText().equals(ARTICLE_FOR_TEST));
            driver.get(urlForPreviousPage);

            CommentCheckIsOk = false;
            //Try up to 5 times to compare comment count inside article view and comment section (mobile).
            for (int compareTryCount = 0; compareTryCount < 5; compareTryCount++) {
                int commentCountReg = 0;
                int commentCountAnon = 0;
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='article-title']/h1")));
                element = element.findElement(FOLLOWING_SIBLING);
                elementText = element.getText();
                commentCount = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                urlForPreviousPage = driver.getCurrentUrl();
                element.click();
                if(driver.findElements(REGISTERED_COMMENTS).size() > 0) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(REGISTERED_COMMENTS));
                    elementText = element.getText();
                    commentCountReg = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
                if(driver.findElements(ANONYMOUS_COMMENTS).size() > 0) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(ANONYMOUS_COMMENTS));
                    elementText = element.getText();
                    commentCountAnon = Integer.parseInt(elementText.substring(1, elementText.length() - 1));
                }
                if(commentCount == commentCountReg + commentCountAnon) {
                    CommentCheckIsOk = true;
                    break;
                } else {
                    driver.get(urlForPreviousPage);
                }
            }
            Assert.assertTrue("Comment count isn't same.", CommentCheckIsOk);
        }
        driver.quit();
    }

}