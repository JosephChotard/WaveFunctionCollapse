package com.josephchotard;

import java.util.Arrays;
import java.util.Set;

import static java.lang.Math.log;

public record WaveElement(Set<String> options) {

    public Set<String> getOptions() {
        return options;
    }

    public double shannonEntropy() {
        double[] weights = this.options
                .stream()
                .map(opt -> ElementRulesCollection.getElementRules(opt).frequency())
                .mapToDouble(x -> x)
                .toArray();
        double sum = Arrays.stream(weights).sum();
        return log(sum) - (Arrays.stream(weights).map(w -> w * log(w)).sum() / sum);
    }

    /**
     * @param availableOptions keep only the options that are also in availableOptions
     * @return whether the options changed
     */
    public boolean keepPossible(Set<String> availableOptions) {
        int originalSize = this.options.size();
        this.options.retainAll(availableOptions);
        return originalSize != this.options.size();
    }

    /**
     * Collapse the element to a random option weighted by the target frequency of each option
     */
    public void collapse() {
//        Get the total weight of all options combined
        double totalWeight = this.options
                .stream()
                .mapToDouble(option -> ElementRulesCollection.getElementRules(option).frequency())
                .sum();
//      Convert set to array to keep track of index
        String[] optionsArray = this.options.toArray(new String[0]);
        int idx = 0;
//      Randomly choose an element (weighted by frequency)
        for (double r = Math.random() * totalWeight; idx < optionsArray.length - 1; ++idx) {
            r -= ElementRulesCollection.getElementRules(optionsArray[idx]).frequency();
            if (r <= 0.0) break;
        }
        String collapseTo = optionsArray[idx];
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