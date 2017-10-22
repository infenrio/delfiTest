package pageObject.wrappers;

import pageObject.pages.ArticlePage;
import pageObject.pages.BaseFunctions;
import pageObject.pages.HomePage;

public class Wrappers {
    BaseFunctions baseFunctions = new BaseFunctions();
    private static final String HOME_PAGE_URL = "https://www.delfi.lv";
    private static final String ARTICLE_NAME = "Riga vs Riga";

    void wrapperExampleTest() {
        baseFunctions.openUrl(HOME_PAGE_URL);
        HomePage homePage = new HomePage(baseFunctions);

        ArticlePage articlePage = homePage.openArticleByTitle(ARTICLE_NAME);
    }
}
