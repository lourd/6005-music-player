package player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    // TODO: ADD TESTS

    public static final String commentIndicator = "%";
    public static final String space = "\\s+"; // one or more
    public static final String text = "\\w"; // any word or digit character
    public static final String linefeed = "\\n"; // new line
    public static final String comment = "%+(\\s)*+(\\w*+\\s*)*"; // + linefeed
    public static final String fieldVoice = "V:.+";

    // 1 or 2 of | --> | or || (\\Q \\E enclose literal quotes to match -- > \\Q literal \\E )
    public static final String barType1 = "((\\Q|\\E){1,2})";
    public static final String singleBar = "((\\Q|\\E){1})";
    public static final String doubleBar = "((\\Q|\\E){2})";
    public static final String barType2 = "(\\[\\|)|(\\|\\])"; // [| or |] 
    public static final String startRepeat = "\\Q|:\\E";  // |:
    public static final String endRepeat = "\\Q:|\\E";  // :|
    public static final String barline = "(" + startRepeat + ")|(" + endRepeat + ")|("
            + barType2 + ")|(" + barType1 + ")";  // barType1 OR barType2 OR barType3


    // [1 followed by word boundary or end of line
    public static final String repeat1 = "\\[1(\\b|$)"; 
    // [2 followed by word boundary or end of line
    public static final String repeat2 = "\\[2(\\b|$)"; 
    // repeat1 OR repeat2
    public static final String nth_repeat = repeat1 + "|" + repeat2; 
    
    // Exists to follow the given grammar
    public static final String bar = barline + "|" + nth_repeat;
    
    // public static final String mid_tune_field = ""; // don't need to do?

    // note-element ::= (note | multi-note)
    // multi-note ::= [note+]
    // note ::= note-or-rest [note-length]
    // note-or-rest ::= pitch | rest
    // pitch ::= [accidental] basenote [octave]
    
    // identifies any number of _ OR ^ (specify one to two?)
    public static final String accidental = "(\\^)+|(\\_)+"; 
    // any basic note in either octave
    public static final String basenote = "(C|D|E|F|G|A|B|c|d|e|f|g|a|b)"; 
    // 1 or more high-octave modifiers OR 1 or more low-octave modifiers
    public static final String octave = "(\\')+|(\\,)+"; 
    public static final String rest = "z";
    // digit [0-9] / digit [0=9]
    public static final String note_length_strict = "(\\d)+(\\/)(\\d)+";
    
    public static final String note_length = "(\\d)*((\\/)?(\\d)*)"; //

    public static final String pitch = "(" + accidental + ")?(" + basenote
            + ")(" + octave + ")?";
    public static final String note_or_rest = "(" + pitch + ")|(" + rest + ")"; 
    // pitch OR rest
    public static final String note = "(" + note_or_rest + ")("
            + note_length + ")?"; // + "" + note_length;
    public static final String multi_note = "\\[(" + note + ")+\\]";
    public static final String note_element = "(" + note + ")|(" + multi_note
            + ")";

    // tuplet-element ::= tuplet-spec note-element+
    // ( followed by a digit [0-9]
    public static final String tuplet_spec = "\\(\\d"; 

    // tuplet_spec followed by 1 or more note_elements
    public static final String tuplet_element = "(" + tuplet_spec + ")" + "("
            + note_element + ")+"; 

    // HIGHEST LEVEL EXPRESSIONS
    // abc-music ::= abc_line+
    // abc_line ::= (element+ linefeed) | mid-tune-field | comment
    // element ::= note-element | tuplet-element | barline | nth-repeat | space

    public static final String midTuneField = fieldVoice;
    public static final String element = "(" + note_element + ")|("
            + tuplet_element + ")|(" + barline + ")|(" + nth_repeat + ")|("
            + space + ")";
    public static final String abc_line = "((" + element + ")+" + "("
            + linefeed + "))|" + "(" + midTuneField + ")|"+ "(" + comment + ")";
    public static final String abc_music = "(" + abc_line + ")+";
    
    // extra regex outside of given abc grammar
    public static final String rest_with_length = "(" + rest + ")(" + note_length + ")?";
    
    // Final regex to pass to Lexer
    public static final String abcRegex = "(" + Regex.element + ")|(" + Regex.midTuneField + ")";

    public static Type determineType(String arg) throws Exception {
    	if (arg.matches(Regex.singleBar)){
    	    return Type.BAR;
    	} else if (arg.matches(Regex.fieldVoice)) {
    	    return Type.VOICE;
    	} else if (arg.matches(Regex.doubleBar) || (arg.matches(Regex.barType2))){
    	    return Type.DOUBLE_BAR;
    	} else if (arg.matches(Regex.startRepeat)){
    	    return Type.START_REPEAT;
    	} else if (arg.matches(Regex.endRepeat)){
    	    return Type.END_REPEAT;
    	} else if (arg.matches(Regex.note)){
    	    if(arg.contains("z"))
    	    return Type.REST;
    	    else{
    	        return Type.PITCH;
    	    }
    	} else if (arg.matches(Regex.multi_note)){
    	    return Type.MULTI_NOTE;
    	} else if (arg.matches(Regex.tuplet_element)){
    	    return Type.TUPLET_ELEMENT;
    	} else if (arg.matches(Regex.space)){
    		return Type.SPACE;
    	} else if (arg.matches(Regex.repeat1)) {
    		return Type.REPEAT1;
    	} else if (arg.matches(Regex.repeat2)) {
    		return Type.REPEAT2;
    	} 
    	
    	throw new Exception("Unhandled type: " + arg);
    }
    
    public static int matchCount(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		int count = 0;
		while(matcher.find()) {
			count++;
		}
		return count;
    }
    
    
    //basic tests for all Types
    public static void main(String[] args) throws Exception {
    	System.out.println(determineType("|"));
    	System.out.println(determineType("||"));
    	System.out.println(determineType("|:"));
    	System.out.println(determineType(":|"));
    	System.out.println(determineType("z"));
    	System.out.println(determineType("V:2"));
    	System.out.println(determineType("C"));
    	System.out.println(determineType("^C,,3/4"));
    	System.out.println(determineType("[^C,,3/4^C,,3/4^C,,3/4dd/2]"));
    	System.out.println(determineType("(3^g'f,"));
    	
    }

}
