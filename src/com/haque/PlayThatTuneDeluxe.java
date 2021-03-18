package com.haque;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Plays music in a variety of ways
 *
 * @author Ethan Haque
 * @version 1-27-2020
 */
public class PlayThatTuneDeluxe {
    private NoteCreator mm;
    private NoteReader nr;

    /**
     * Main entry point for the program
     *
     * @param args the arguments to the main method
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        PlayThatTuneDeluxe pttd = new PlayThatTuneDeluxe();
        System.out.println("----------------- Playing single notes with superimposed harmonics -----------------");
        System.out.println("Enter the note you want to play, its octave, and its duration. Ex. 'C 4 2' or 'F# 3 3'");
        for (int i = 0; i < 5; i++) {
            pttd.playSingleNote();
        }

        System.out.println("----------------- Playing notes that attempt to mimic a piano -----------------");
        System.out.println("Enter the note you want to play, its octave, and its duration Ex. 'C 4 2' or 'F# 3 3'");
        for (int i = 0; i < 5; i++) {
            pttd.playPianoNote();
        }

        System.out.println("----------------- Playing a chromatic scale with piano notes -----------------");
        System.out.println("Enter a starting note, its octave, and the duration for each individual note Ex. 'C 4 2' or 'F# 3 3'");
        pttd.playChromatic();

        System.out.println("----------------- Playing music from a file -----------------");
        System.out.println("Enter a file path to a music text file");
        String path = StdIn.readString();
        pttd.readMusicFile(path);
    }

    /**
     * Constructor for the PlayThatTuneDeluxe class
     */
    public PlayThatTuneDeluxe() {
        nr = new NoteReader();
        mm = new NoteCreator();
    }

    /**
     * Plays a single-toned note/ pure note based on user input
     */
    private void playSingleNote() {
        String noteName = StdIn.readString();
        int shift = StdIn.readInt();
        double duration = StdIn.readDouble();

        int pitch = nr.getNextPitch(noteName, shift);

        double[] a = createSingleNote(pitch, duration);
        StdAudio.play(a);
    }

    /**
     * Plays a piano-sounding note based on user input
     */
    private void playPianoNote() {
        String noteName = StdIn.readString();
        int shift = StdIn.readInt();
        double duration = StdIn.readDouble();

        int pitch = nr.getNextPitch(noteName, shift);

        double[] a = createPianoNote(pitch, duration, 4);
        StdAudio.play(a);
    }

    /**
     * Plays a chromatic scale based on user input
     */
    private void playChromatic() {
        String noteName = StdIn.readString();
        int shift = StdIn.readInt();
        double duration = StdIn.readDouble();

        int pitch = nr.getNextPitch(noteName, shift);

        double[] a = createChromatic(pitch, duration, duration / 3, 4);
        StdAudio.play(a);
    }

    /**
     * Creates a note
     *
     * @param pitch the number of notes above or below middle C
     * @param time  the duration of the note
     * @return a double array representing a note
     */
    private double[] createSingleNote(int pitch, double time) {
        return mm.note(pitch, time);
    }

    /**
     * Creates a note that sounds like a piano
     *
     * @param pitch           the number of notes above or below middle C
     * @param time            the duration of the note
     * @param numberOvertones the number of notes to overlay the root tone of the piano note
     * @return a double array representing a note that sounds like a piano
     */
    private double[] createPianoNote(int pitch, double time, int numberOvertones) {
        return mm.pianoNote(pitch, time, numberOvertones);
    }

    /**
     * Creates a chromatic scale
     *
     * @param pitch           the initial note to start the scale on measured by how above or below middle C it is
     * @param time            the duration of each individual note
     * @param timeShift       the time each note should overlap
     * @param numberOvertones the number of tones to superimpose over the root note
     * @return a double array representing an entire chromatic scale
     */
    private double[] createChromatic(int pitch, double time, double timeShift, int numberOvertones) {
        return mm.createChromatic(pitch, time, timeShift, numberOvertones);
    }

    /**
     * Reads in a text file to play a song
     *
     * @param path the path to the text file
     * @throws FileNotFoundException
     */
    private void readMusicFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitString = line.split(" ");

            int pitch = nr.getNextPitch(splitString[0], Integer.parseInt(splitString[1]));

            StdAudio.play(createPianoNote(pitch, Double.parseDouble(splitString[2]), 4));
        }
    }


}
