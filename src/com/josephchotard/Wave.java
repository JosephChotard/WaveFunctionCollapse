package com.josephchotard;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Wave {
    private WaveElement[][] waveElements;
    private int width;
    private int height;

    public Wave(int width, int height, String[] options) {
        this.width = width;
        this.height = height;
        this.waveElements = new WaveElement[height][width];
        for (WaveElement[] row: this.waveElements) {
            for (int i = 0; i<width; i++) {
                row[i] = new WaveElement(options);
            }
        }
    }

    public boolean isWaveFunctionCollapsed() {
        return Stream.of(this.waveElements).allMatch(
                row -> Stream.of(row).allMatch(element -> element.getOptions().length == 1)
        );
    }

    public Optional<WaveElement> getLowestEntropyElement() {
        ArrayList<WaveElement> lowestEntropyElements = new ArrayList();
        int minEntropy = Integer.MAX_VALUE;
        for (WaveElement[] row: this.waveElements) {
            for (WaveElement waveElement: row) {
                int entropy = waveElement.shannonEntropy();
                if (entropy != 0 && entropy < minEntropy) {
                    lowestEntropyElements.clear();
                    lowestEntropyElements.add(waveElement);
                    minEntropy = entropy;
                } else if (entropy == minEntropy) {
                    lowestEntropyElements.add(waveElement);
                }
            }
        }
        if (lowestEntropyElements.size() == 0) {
            return Optional.empty();
        }
        Random rand = new Random();
        return Optional.of(lowestEntropyElements.get(rand.nextInt(lowestEntropyElements.size())));
    }

    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Wave of size ").append(this.width).append("x").append(this.height).append(":\n");
        String rowBarrier = "═".repeat(width*2+1);
        stringRepresentation.append("╔").append(rowBarrier).append("╗\n");
        for (WaveElement[] row: this.waveElements) {
            stringRepresentation.append("║");
            for (WaveElement element: row) {
                stringRepresentation.append(" ").append(element);
            }
            stringRepresentation.append(" ║\n");
        }
        int length = stringRepresentation.length();
        stringRepresentation.append("╚").append(rowBarrier).append("╝\n");
        return stringRepresentation.toString();
    }
}
