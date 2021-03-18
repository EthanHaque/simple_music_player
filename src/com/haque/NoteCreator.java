package com.haque;

/**
 * Creates notes in the forms of double arrays
 *
 * @author Ethan Haque
 * @version 1-27-2020
 */
public class NoteCreator {

    private final int sps = 44100;

    /**
     * Sums two arrays together
     *
     * @param a   doulbe array representing a wave
     * @param b   double array representing a wave
     * @param awt weight for the first array
     * @param bwt weight for the second array
     * @return sum of the two arrays
     */
    public double[] sum(double[] a, double[] b, double awt, double bwt) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] * awt + b[i] * bwt;
        }
        return c;
    }

    /**
     * Creates a single-toned note
     *
     * @param hz   the frequency of the note
     * @param time the duration of the note
     * @param A    the scale factor for the note
     * @return a double array representing a note
     */
    public double[] tone(double hz, double time, double A) {
        int N = (int) (sps * time);
        double[] a = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            a[i] = Math.sin(A * 2 * Math.PI * i * hz / sps);
        }
        return a;
    }

    public double calculateHz(int pitch) {
        return 261.63 * Math.pow(2, pitch / 12.0); // starting from middle c (C4)
    }

    /**
     * Takes a note and superposes it
     *
     * @param pitch the number of notes above middle C
     * @param t     the duration of the note
     * @return a double array representing a superposed note
     */
    public double[] note(int pitch, double t) {
        double hz = calculateHz(pitch);
        double[] a = tone(hz, t, 1);
        double[] hi = tone(2 * hz, t, 1);
        double[] lo = tone(hz / 2, t, 1);
        double[] h = sum(hi, lo, .5, .5);
        return sum(a, h, .75, .25);
    }

    /**
     * Creates a tone that sounds like a piano note
     *
     * @param hz          the frequency of the note
     * @param time        the duration of the note
     * @param A           the scale factor inside the sine function
     * @param scaleFactor the scale factor outside the sine function
     * @return a double array representing a tone that sounds like a piano tone
     */
    public double[] pianoTone(double hz, double time, double A, double scaleFactor) {
        int sps = 44100;
        int N = (int) (sps * time);
        double[] a = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            a[i] = Math.sin(A * 2 * Math.PI * i * hz / sps) * Math.exp(-0.0004 * 2 * Math.PI * i * hz / sps) / scaleFactor;
        }
        return a;
    }

    /**
     * Raises all the values in an array to a power
     *
     * @param arr   the array to raise the values of
     * @param power the power to raise each value to
     * @return an array with all its values raised to a power
     */
    public double[] raiseArrayToPower(double[] arr, double power) {
        double[] c = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            c[i] = Math.pow(arr[i], power);
        }

        return c;
    }

    /**
     * Composes piano tones together to make a piano note
     *
     * @param pitch        the number of notes about middle C
     * @param t            the duration of the note
     * @param numOvertones the number of overtones for the note
     * @return a double array representing a note that sounds like a piano note
     */
    public double[] pianoNote(int pitch, double t, int numOvertones) {
        double hz = calculateHz(pitch);
        double[] root = pianoTone(hz, t, 1, 1);
        for (int i = 0; i < numOvertones; i++) {
            root = sum(root, pianoTone(hz, t, i + 2, Math.pow(2, i + 1)), .9, .1);
        }
        root = sum(root, raiseArrayToPower(root, 3), .5, .5);

        return root;
    }

    /**
     * Creates a double array that sounds representing a chromatic scale
     *
     * @param startingPitch   the starting note for the scale
     * @param time            the duration of each individual note
     * @param timeShift       how much each note will overlap
     * @param numberOvertones the number of overtones for the note
     * @return a double array with an entire chromatic scale in it
     */
    public double[] createChromatic(int startingPitch, double time, double timeShift, int numberOvertones) {
        int N = (int) (sps * time);
        int shift = (int) (sps * timeShift);
        double[] combined = new double[(N - shift) * 12 + 1];
        for (int i = 0; i < 12; i++) {
            double[] note = pianoNote(startingPitch + i, time, numberOvertones);
            for (int j = 0; j < note.length - shift; j++) {
                combined[j + (N - shift) * i] += note[j];
            }
        }
        return combined;
    }
}
