package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.SequencePlayer;

public class ABCParser {
    private Map<String, String> header;
    private HashMap<Character, Integer> keySignature;
    private List<String> voices;
    private int beatsPM;
    private final int ticksPerQuarterNote = 192;
    private HashMap<String, Integer> tempKeySig;
    private double duration;

    public ABCParser(Map<String, String> header, List<String> voices)
            throws InvalidFileException {
        this.voices = voices;
        this.header = header;
        makeDefaultFields();
        this.keySignature = makeKeySignature(header.get("K"));
        // Chosen because a big multiple of 2 & 3
        this.duration = setDuration(this.header.get("L"));
        beatsPM = Integer.valueOf(header.get("Q").trim());
        tempKeySig = new HashMap<String, Integer>();
    }

    // Parses the string stored as the length to return a double for duration
    private double setDuration(String d) throws InvalidFileException {
        if (!d.matches(Regex.note_length_strict)) {
            throw new InvalidFileException("Invalid note length given");
        } else {
            String parts[] = d.split("/");
            return Double.valueOf(parts[0]) / Double.valueOf(parts[1]);
        }
    }

    // Sets the 4 default fields values if they didn't exist in the header
    private void makeDefaultFields() {
        // Default note length
        if (!header.containsKey("L")) {
            header.put("L", "1/8");
        }
        // Default key
        if (!header.containsKey("K")) {
            header.put("K", "C");
        }
        // Default composer
        if (!header.containsKey("C")) {
            header.put("C", "Unknown");
        }
        // Default tempo
        if (!header.containsKey("Q")) {
            header.put("Q", "120");
        }
    }

    // Sets the default semitones for the 7 notes based on the key signature
    private HashMap<Character, Integer> makeKeySignature(String key) {
        HashMap<Character, Integer> keySig = new HashMap<Character, Integer>();

        // Fill the keySignature map with default 0 values
        Character[] keys = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        for (int i = 0; i < keys.length; i++) {
            keySig.put(keys[i], 0);
        }

        if (key.equals("C") || key.equals("Am")) {
            // Do nothing, default values already set
        }
        // F major or d minor
        else if (key.equals("F") || key.equals("Dm")) {
            keySig.put('B', -1);
        }
        // B flat major or g minor
        else if (key.equals("_B") || key.equals("Gm")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
        }
        // E flat major or C minor
        else if (key.equals("_E") || key.equals("Cm")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
            keySig.put('A', -1);
        }
        // A flat major or f minor
        else if (key.equals("_A") || key.equals("Fm")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
            keySig.put('A', -1);
            keySig.put('D', -1);
        }
        // D flat major or b flat minor
        else if (key.equals("_D") || key.equals("_Bm")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
            keySig.put('A', -1);
            keySig.put('D', -1);
            keySig.put('G', -1);
        }
        // G flat major or e flat minor
        else if (key.equals("_G") || key.equals("_Em")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
            keySig.put('A', -1);
            keySig.put('D', -1);
            keySig.put('G', -1);
            keySig.put('C', -1);
        }
        // C flat major or a flat minor
        else if (key.equals("_C") || key.equals("_Am")) {
            keySig.put('B', -1);
            keySig.put('E', -1);
            keySig.put('A', -1);
            keySig.put('D', -1);
            keySig.put('G', -1);
            keySig.put('C', -1);
            keySig.put('F', -1);
        }
        // And now the sharps!
        // G major or e minor
        else if (key.equals("G") || key.equals("Em")) {
            keySig.put('F', 1);
        }
        // D major or b minor
        else if (key.equals("D") || key.equals("Bm")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
        }
        // A major or f sharp minor
        else if (key.equals("A") || key.equals("^Fm")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
            keySig.put('G', 1);
        }
        // E major or c sharp minor
        else if (key.equals("E") || key.equals("^Cm")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
            keySig.put('G', 1);
            keySig.put('D', 1);
        }
        // B major or g sharp minor
        else if (key.equals("B") || key.equals("^Gm")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
            keySig.put('G', 1);
            keySig.put('D', 1);
            keySig.put('A', 1);
        }
        // F sharp major or d sharp minor
        else if (key.equals("^F") || key.equals("^Dm")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
            keySig.put('G', 1);
            keySig.put('D', 1);
            keySig.put('A', 1);
            keySig.put('E', 1);
        }
        // C sharp major or a sharp minor
        else if (key.equals("^C") || key.equals("^Am")) {
            keySig.put('F', 1);
            keySig.put('C', 1);
            keySig.put('G', 1);
            keySig.put('D', 1);
            keySig.put('A', 1);
            keySig.put('E', 1);
            keySig.put('B', 1);
        } else
            throw new RuntimeException("Not a valid key signature.");

        return keySig;
    }

    public Playable parse(List<Token> tokens) throws Exception {
        if (tokens.size() == 0) {
            return new Rest(0);
        }

        // BASE CASE: tokens[] is length 1
        if (tokens.size() == 1) {

            Token token = tokens.get(0);
            String value = token.getValue();
            Playable ret = new Rest(0);
            Pattern pattern;
            Matcher matcher;
            // Set the tokenDuration to the default duration
            double tokenDuration = this.duration;
            List<Token> restTokens;

            // Be sure the token is of a proper type, otherwise throw an error
            switch (token.getType()) {

            case MULTI_NOTE:
                // parse the cord
                pattern = Pattern.compile(Regex.note);
                matcher = pattern.matcher(value);

                while (matcher.find()) {
                    String noteStr = matcher.group();

                    restTokens = new ArrayList<Token>();
                    restTokens.add(new Token(Type.PITCH, noteStr));

                    ret = new Together(ret, parse(restTokens));
                }
                return ret;

            case TUPLET_ELEMENT:
                // parse the Tuplet
                pattern = Pattern.compile(Regex.note_element);
                matcher = pattern.matcher(value);

                ArrayList<Playable> items = new ArrayList<Playable>();
                while (matcher.find()) {
                    String matchStr = matcher.group();

                    restTokens = new ArrayList<Token>();
                    restTokens.add(new Token(Regex.determineType(matchStr),
                            matchStr));

                    items.add(parse(restTokens));
                }

                return new Tuplet(items);

            case PITCH:

                // parse the note

                // Extract the letter
                pattern = Pattern.compile(Regex.basenote);
                matcher = pattern.matcher(value);
                Character letter;
                int additionalSemitonesUp = 0;

                if (matcher.find()) {
                    letter = matcher.group().charAt(0);
                    if (Character.isLowerCase(letter)) {
                        letter = Character.toUpperCase(letter);
                        additionalSemitonesUp += 12;
                    }
                } else {
                    throw new Exception("Couldnt find " + Regex.basenote);
                }

                // Figure out the semitonesUp situation
                int semitonesUp = 0;

                Pattern p = Pattern.compile("(" + Regex.basenote + ")("
                        + Regex.octave + ")?");
                Matcher m = p.matcher(value);
                String baseAndOctave = "NONE";

                // Leave like this
                while (m.find()) {
                    baseAndOctave = m.group().replaceAll("[0-9]*/?[0-9]*", "");
                }

                if (value.contains("=") || value.contains("^")
                        || value.contains("_")) {

                    if (value.contains("=")) {
                        semitonesUp = 0;
                    }
                    int numSharps = Regex.matchCount("\\^", value);
                    semitonesUp += numSharps;
                    int numFlats = Regex.matchCount("_", value);
                    semitonesUp -= numFlats;
                    tempKeySig.put(baseAndOctave, numSharps - numFlats);

                } else {
                    if (tempKeySig.containsKey(baseAndOctave)) {
                        semitonesUp += tempKeySig.get(baseAndOctave);
                    } else {
                        semitonesUp += keySignature.get(Character
                                .toUpperCase(letter));
                    }
                }

                semitonesUp += 12 * Regex.matchCount("'", value);
                semitonesUp -= 12 * Regex.matchCount(",", value);

                semitonesUp += additionalSemitonesUp;

                // Calculate the duration

                pattern = Pattern.compile("[0-9]*/?[0-9]*");
                matcher = pattern.matcher(value);
                while (matcher.find()) {
                    String str = matcher.group();
                    if (str.equals("")) {
                        continue;
                    }
                    // Change the duration based on the numbers after the pitch
                    tokenDuration = tokenDuration * calcDuration(str);
                }
                return new Note(letter, semitonesUp, tokenDuration);

            case REST:
                // Calculate the duration

                pattern = Pattern.compile("[0-9]*/?[0-9]*");
                matcher = pattern.matcher(value);

                while (matcher.find()) {
                    String str = matcher.group();
                    if (str.equals("")) {
                        continue;
                    }
                    // Change the duration bsed on the numbers after the rest
                    tokenDuration = tokenDuration * calcDuration(str);
                }

                return new Rest(tokenDuration);
            default:
                return new Rest(0);
                /*
                 * throw new ParserErrorException("Error: " + token.getValue() +
                 * " is an invalid token.");
                 */
            }
        } else if (hasVoices(tokens)) {

            Map<String, Playable> voiceTracks = new HashMap<String, Playable>();

            // Get the Playable for each voice
            for (String voice : voices) {
                List<Token> toPlay = new ArrayList<Token>();
                boolean gathering = false;
                for (int i = 0; i < tokens.size(); i++) {
                    Token curToken = tokens.get(i);

                    if (curToken.getType() == Type.VOICE) {
                        String vId = getVoiceId(curToken);
                        gathering = voice.equals(vId);
                    } else {
                        if (gathering) {
                            toPlay.add(curToken);
                        }
                    }
                }

                voiceTracks.put(voice, parse(toPlay));
            }

            Playable all = new Rest(0);

            for (String voice : voices) {
                all = new Together(all, voiceTracks.get(voice));
            }

            return all;
        } else if (hasRepeats(tokens)) {

            Integer mainStart = 0;
            Integer mainEnd = 0;
            Integer middleStart = 0;
            Integer middleEnd = 0;
            Integer endStart = 0;
            Integer endEnd = 0;

            // Find main, start, end
            for (int i = 0; i < tokens.size(); i++) {
                Type type = tokens.get(i).getType();

                // main
                if (mainEnd == 0) {
                    if (type == Type.START_REPEAT || type == Type.DOUBLE_BAR) {
                        mainStart = i + 1;
                    }

                    if (type == Type.REPEAT1 || type == Type.END_REPEAT) {
                        mainEnd = i;
                        if (type == Type.REPEAT1) {
                            middleStart = middleEnd = i + 1;
                        }

                    }
                } else if (middleStart > 0 && middleStart == middleEnd) {
                    if (type == Type.END_REPEAT) {
                        middleEnd = i;
                    }
                } else if (middleEnd > middleStart) {
                    if (type == Type.REPEAT2) {
                        endStart = i + 1;
                    } else if (endStart != 0
                            && (type == Type.DOUBLE_BAR || type == Type.BAR)) {
                        endEnd = i;
                        break;
                    }
                }
            }

            Playable beginning = parse(tokens.subList(0, mainStart));
            Playable main = parse(tokens.subList(mainStart, mainEnd));
            Playable middle = parse(tokens.subList(middleStart, middleEnd));
            Playable end = parse(tokens.subList(endStart, endEnd));
            Playable remain = parse(tokens.subList(Math.max(mainEnd, endEnd),
                    tokens.size()));

            return new Join(beginning, new Join(main, new Join(middle,
                    new Join(main, new Join(end, remain)))));

        } else if (hasMeasures(tokens)) {
            Playable music = new Rest(0);
            List<Token> tokensToPlay = new ArrayList<Token>();
            tempKeySig.clear();

            for (Token t : tokens) {
                if (t.getType() == Type.BAR || t.getType() == Type.DOUBLE_BAR) {
                    music = new Join(music, parse(tokensToPlay));
                    tokensToPlay.clear();
                    tempKeySig.clear();
                } else {
                    tokensToPlay.add(t);
                }
            }

            music = new Join(music, parse(tokensToPlay));

            return music;
        } else { // just a plain list of tokens in a measure
            List<Token> first = new ArrayList<Token>();
            first.add(tokens.get(0));

            List<Token> rest = tokens.subList(1, tokens.size());
            return new Join(parse(first), parse(rest));
        }
    }

    private boolean hasMeasures(List<Token> tokens) {
        for (Token t : tokens) {
            if (t.getType() == Type.BAR || t.getType() == Type.DOUBLE_BAR) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRepeats(List<Token> tokens) {
        for (Token t : tokens) {
            if (t.getType() == Type.START_REPEAT
                    || t.getType() == Type.END_REPEAT) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVoices(List<Token> tokens) {
        for (Token t : tokens) {
            if (t.getType() == Type.VOICE) {
                return true;
            }
        }
        return false;
    }

    private double calcDuration(String fraction) {

        // Default value is 1/2 when num and den are absent
        if (fraction.equals("/")) {
            return .50;
        }

        // No multiplier when nothing listed
        if (fraction.equals("")) {
            return 1.0;
        }

        if (fraction.contains("/")) {
            if (fraction.charAt(0) == '/') {
                String parts[] = fraction.split("/");
                return 1.0 / Double.valueOf(parts[1]);
            } else {
                String parts[] = fraction.split("/");
                if (parts.length == 2) {
                    return Double.valueOf(parts[0]) / Double.valueOf(parts[1]);
                } else {
                    return Double.valueOf(parts[0]);
                }
            }
        } else {
            return Double.valueOf(fraction);
        }
    }

    private String getVoiceId(Token voice) {
        String id = voice.getValue().trim();
        id = id.substring(2);

        return id.trim();
    }

    public int bpm() {
        return beatsPM;
    }

    public int getTicksPerQuarterNote() {
        return ticksPerQuarterNote;
    }

    public PlayableVisitor makePlayableVisitor()
            throws MidiUnavailableException, InvalidMidiDataException {
        return new ABCPlayableVisitor(new SequencePlayer(bpm(), getTicksPerQuarterNote()),
                getTicksPerQuarterNote());
    }

}
