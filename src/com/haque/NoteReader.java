package com.haque;

import java.util.HashMap;

/**
 * Takes in strings of notes to convert them to pitches
 *
 * @author Ethan Haque
 * @version 1-27-2020
 */
public class NoteReader {
    private HashMap<String, Integer> freqMap;

    /**
     * Constructor for the NoteReader class
     */
    public NoteReader() {
        freqMap = new HashMap();
        freqMap.put("C", 0);
        freqMap.put("C#", 1);
        freqMap.put("D", 2);
        freqMap.put("D#", 3);
        freqMap.put("E", 4);
        freqMap.put("F", 5);
        freqMap.put("F#", 6);
        freqMap.put("G", 7);
        freqMap.put("G#", 8);
        freqMap.put("A", 9);
        freqMap.put("A#", 10);
        freqMap.put("B", 11);
    }

    /**
     * Converts a string of a note and octave into a pitch
     *
     * @param note   the string name of a note
     * @param offset the number of octaves away from the 4th octave a note is
     * @return a int for the number of notes above or below middle C
     */
    public int getNextPitch(String note, int offset) {
        int val = freqMap.get(note);
        int pitch = val + (offset - 4) * 12;

        return pitch;
    }
}
