package delfiTest.pages;

public class CommentHelper {

    public int stringToInt(String textInput) {
        if(textInput.lastIndexOf('(') < 0 && textInput.lastIndexOf(')') < 0) {
            return 0;
        } else {
            return Integer.parseInt(textInput.substring(1, textInput.length() - 1));
        }
    }
}
