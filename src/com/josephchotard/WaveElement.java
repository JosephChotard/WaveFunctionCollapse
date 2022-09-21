package com.josephchotard;

import java.util.Random;
import java.util.Set;

public class WaveElement {
    private Set<String> options;

    public WaveElement(Set<String> options) {
        this.options = options;
    }

    public Set<String> getOptions() {
        return options;
    }

    public int shannonEntropy() {
        if (this.options.size() == 1) {
            return 0;
        }
        return this.options.size();
    }

    public void removeOption(String option) {
        this.options.remove(option);
    }

    /**
     * @param availableOptions keep only the options that are also in availableOptions
     * @return whether or not the options changed
     */
    public boolean keepPossible(Set availableOptions) {
        int originalSize = this.options.size();
        this.options.retainAll(availableOptions);
        return originalSize != this.options.size();
    }

    /**
     * Collapse the element to a random option
     */
    public void collapse() {
        Random rand = new Random();
//        Get random element from array, could also use iterator
        String[] optionsArray = this.options.toArray(new String[this.options.size()]);
        String collapseTo = optionsArray[rand.nextInt(optionsArray.length)];
        this.options.clear();
        this.options.add(collapseTo);
    }

    @Override
    public String toString() {
        if (this.options.size() == 1) {
            return ElementRulesCollection
                    .getElementRules(this.options.iterator().next())
                    .representation();
        } else if (this.options.size() == 0) {
            return "╳";
        } else {
            return "░";
        }
    }
}

record WaveElementWithPosition(WaveElement waveElement, int x, int y) {
}