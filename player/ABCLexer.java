package player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ABCLexer {

    public static List<Token> getTokens(String input) throws Exception {

        String REGEX = Regex.abcRegex;
        List<Token> tokens = new ArrayList<Token>();
        List<String> types = TokenExtractor(input, REGEX);
        for (String t : types) {
            Type type = Regex.determineType(t);
            if (type != Type.SPACE) {
                tokens.add(new Token(type, t));
            }
            // System.out.println(t + " === " +Regex.determineType(t));
        }
        return tokens;
    }
    
    private static List<String> TokenExtractor(String input, String reg)
            throws Exception {
        //Pattern.matches(reg, input);
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(input);

        List<String> words = new ArrayList<String>();
        while (m.find()) { // iterator: advances through all possible matches
            String w = m.group();
            words.add(w);
            // System.out.println(w);
        }
        return words;
    }
}
