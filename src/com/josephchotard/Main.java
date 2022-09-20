package com.josephchotard;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Wave wave = new Wave(10, 5, new String[]{"┫", "┣", "┳", "┻", " "});
        Optional<WaveElement> waveElement = wave.getLowestEntropyElement();
        while (waveElement.isPresent()) {
            waveElement.get().collapse();
            waveElement = wave.getLowestEntropyElement();
        }


        System.out.println(wave);
    }
}
