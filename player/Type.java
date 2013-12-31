package player;

/**
 * Token type for abc language.
 */
public enum Type {
    // TODO: check on whether these types should be terminals only
    EOF,    // end of file
    TEXT,   // text that doesn't contain any markup codes
    //NOTE,
    MULTI_NOTE, // TODO: check how specific these types should be (compare with design.pdf)
    TUPLET_ELEMENT,
    BARLINE,
    NTH_REPEAT,
    //REST,
    NOTE_LENGTH,
    ACCIDENTAL,
    BASENOTE,
    TUPLET_SPEC,
    MID_TUNE_FIELD,
    NOTE_ELEMENT,
    PITCH,
    OCTAVE,
    
        
    //TOKENS THAT WE DON'T CARE ABOUT, BUT NEED TO IDENTIFY ANYWAYS:
    COMMENT,
    SPACE,
    END_OF_LINE, //?//
    //
    

    REPEAT1,
    REPEAT2,
    //
    BAR,
    DOUBLE_BAR,
    START_REPEAT,
    END_REPEAT,
    REST,
    VOICE,
    NOTE
}
