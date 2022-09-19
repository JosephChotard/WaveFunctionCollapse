package com.josephchotard;

import java.util.stream.Stream;

public class WaveElement {
    private String[] options;

    public WaveElement(String[] options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public int shannonEntropy() {
        return this.options.length;
    }

    public void removeOption(String option) {
        this.options = Stream.of(this.options).filter(o -> !o.equals(option)).toArray(String[]::new);
    }

    @Override
    public String toString() {
        if (this.options.length == 1) {
            return options[0];
        } else if (this.options.length == 0) {
            return "╳";
        } else {
            return "░";
        }
    }
}
