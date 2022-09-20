package com.josephchotard;

import java.util.Random;
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
        if (this.options.length == 1) {
            return 0;
        }
        return this.options.length;
    }

    public void removeOption(String option) {
        this.options = Stream.of(this.options).filter(o -> !o.equals(option)).toArray(String[]::new);
    }

    /**
     * Collapse the element to a random option
     */
    public void collapse() {
        Random rand = new Random();
        String collapseTo = this.options[rand.nextInt(this.options.length)];
        this.options = new String[]{collapseTo};
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
