package sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

public class SequencePlayerTest {
    
    @Test
    // Row row row your boat
    public void testPiece1() {
        
        try {
            SequencePlayer player = new SequencePlayer(140, 48);
            player.addNote(new Pitch('C').toMidiNote(), 0, 48);
            player.addNote(new Pitch('C').toMidiNote(), 48, 48);
            player.addNote(new Pitch('C').toMidiNote(), 96, 36);
            player.addNote(new Pitch('D').toMidiNote(), 132, 12);
            player.addNote(new Pitch('E').toMidiNote(), 144, 48);

            player.addNote(new Pitch('E').toMidiNote(), 192, 36);
            player.addNote(new Pitch('D').toMidiNote(), 228, 12);
            player.addNote(new Pitch('E').toMidiNote(), 240, 36);
            player.addNote(new Pitch('F').toMidiNote(), 276, 12);
            player.addNote(new Pitch('G').toMidiNote(), 288, 96);

            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    384, 16);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    400, 16);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    416, 16);

            player.addNote(new Pitch('G').toMidiNote(), 432, 16);
            player.addNote(new Pitch('G').toMidiNote(), 448, 16);
            player.addNote(new Pitch('G').toMidiNote(), 464, 16);

            player.addNote(new Pitch('E').toMidiNote(), 480, 16);
            player.addNote(new Pitch('E').toMidiNote(), 496, 16);
            player.addNote(new Pitch('E').toMidiNote(), 512, 16);

            player.addNote(new Pitch('C').toMidiNote(), 528, 16);
            player.addNote(new Pitch('C').toMidiNote(), 544, 16);
            player.addNote(new Pitch('C').toMidiNote(), 560, 16);

            player.addNote(new Pitch('G').toMidiNote(), 576, 36);
            player.addNote(new Pitch('F').toMidiNote(), 612, 12);
            player.addNote(new Pitch('E').toMidiNote(), 624, 36);
            player.addNote(new Pitch('D').toMidiNote(), 660, 12);
            player.addNote(new Pitch('C').toMidiNote(), 672, 48);
            player.play();
            
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }
    
    @Test 
    // Mario song
    public void testPiece2() {
        
        try {
            SequencePlayer p = new SequencePlayer(200,24);
            
            // [e1/2F1/2^] 
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 0, 12);
            p.addNote(new Pitch('F').transpose(1).toMidiNote(), 0, 12);
            
            // [e1/2F1/2^] 
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 12, 24);
            p.addNote(new Pitch('F').transpose(1).toMidiNote(), 12, 24);
            
            // z1/2. Rest next start = 24 + 12 = 36
            
            // [e1/2F1/2^] 
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(),36, 12);
            p.addNote(new Pitch('F').transpose(1).toMidiNote(),36, 12);
            
            // z1/2. Rest next start = 48 + 12 = 60
            
            // [c1/2F1/2^] 
            p.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 60, 12);
            p.addNote(new Pitch('F').transpose(1).toMidiNote(), 60, 12);
            
            // [eF] 
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(),72,24);
            p.addNote(new Pitch('F').transpose(1).toMidiNote(),72,24);
            
            // Measure 2
            p.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 96, 24); 
            p.addNote(new Pitch('B').toMidiNote(), 96, 24);
            p.addNote(new Pitch('G').toMidiNote(), 96, 24);
            
            // Full rest. Next start = 120 + 24 = 144
            
            p.addNote(new Pitch('G').toMidiNote(), 144, 24);
            
            // Full rest next start = 168 + 24 = 192
            
            // Measure 3 //c3/4 
            p.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 192, 36);
            p.addNote(new Pitch('G').toMidiNote(), 228, 12); 
            // full rest. Next start = 240 + 24 = 264
            // E 
            p.addNote(new Pitch('E').toMidiNote(), 264, 24);
            
            //Measure 4 
            // E1/4 
            // It would sound better to have the notes tied 
            // The E in the last measure 36 and go straight to the B
            p.addNote(new Pitch('E').toMidiNote(), 288, 12); 
            // A
            p.addNote(new Pitch('A').toMidiNote(), 300, 24); 
            // B
            p.addNote(new Pitch('B').toMidiNote(), 324, 24); 
            // B flat
            p.addNote(new Pitch('B').transpose(-1).toMidiNote(), 348, 12); 
            // A 
            p.addNote(new Pitch('A').toMidiNote(), 360, 24);
            
            // Measure 5 // Geg triplet 
            p.addNote(new Pitch('G').toMidiNote(), 384, 16); 
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 400, 16);
            p.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 416, 16); 
            // a 
            p.addNote(new Pitch('A').transpose(Pitch.OCTAVE).toMidiNote(), 432, 24); 
            // f
            p.addNote(new Pitch('F').transpose(Pitch.OCTAVE).toMidiNote(), 456, 12); 
            // g 
            p.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 468, 12);
            
            // Measure 6 
            // eighth note rest; next start = 480+12 = 492 
            // e
            p.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 492, 24); 
            // c 
            p.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 516, 12); 
            // d
            p.addNote(new Pitch('D').transpose(Pitch.OCTAVE).toMidiNote(), 528, 12); 
            // B 
            p.addNote(new Pitch('B').toMidiNote(), 540, 16);
            p.play();
            
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
