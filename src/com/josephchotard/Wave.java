package com.josephchotard;

import java.util.Arrays;
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
            Arrays.fill(row, new WaveElement(options));
        }
    }

    public boolean isWaveFunctionCollapsed() {
        return Stream.of(this.waveElements).allMatch(
                row -> Stream.of(row).allMatch(element -> element.getOptions().length == 1)
        );
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
            stringRepresentation.append(" ║\n╠").append(rowBarrier).append("╣\n");
        }
        int length = stringRepresentation.length();
        stringRepresentation.delete(length-width*2-4, length);
        stringRepresentation.append("╚").append(rowBarrier).append("╝\n");
        return stringRepresentation.toString();
    }
}
