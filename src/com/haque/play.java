package com.haque;

public class play {
    public static void main(String[] args) {
        int sps = 44100;
        System.out.println("Enter number of notes above A4 and duration --");
        while (!StdIn.isEmpty()) {
            int pitch = StdIn.readInt();//distance from A
            //int pitch = 7;
            double duration = StdIn.readDouble();//note com.haque.play time
            //double duration = 1.0;
            double hz = 440 * Math.pow(2, pitch / 12.0);//frequency
            int N = (int) (sps * duration);//number of samples
            double[] a = new double[N + 1];//sampled sine wave
            for (int i = 0; i <= N; i++) {
                a[i] = Math.sin(2 * Math.PI * i * hz / sps);
                System.out.print(a[i] + " ");
            }
            StdAudio.play(a);
        }
    }
}