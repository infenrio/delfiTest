package pageObject.pages;

import com.google.common.base.CharMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommentHelper {

    private static final Logger LOGGER = LogManager.getLogger(CommentHelper.class);

    public Integer stringToInt(String textInput) {
        return Integer.valueOf(textInput.substring(1, textInput.length()-1));
//        String digits = CharMatcher.digit().removeFrom(textInput);
//        return Integer.valueOf(digits);
    }
}
