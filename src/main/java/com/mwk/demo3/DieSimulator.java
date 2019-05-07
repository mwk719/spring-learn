package com.mwk.demo3;

public class DieSimulator {

    public static void main(String[] args) {
        Die d = new Die(6);
        final int TRIES = 10;

        for (int i = 0; i < TRIES; i++) {
            int n = d.cast();
            System.out.print(n + " ");
        }
    }

}
