package com.josephchotard;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Wave {
    final private WaveElement[][] waveElements;
    final private int width;
    final private int height;

    public Wave(int width, int height, String[] options) {
        this.width = width;
        this.height = height;
        this.waveElements = new WaveElement[height][width];
        for (WaveElement[] row: this.waveElements) {
            for (int i = 0; i<width; i++) {
                row[i] = new WaveElement(new HashSet<>(Arrays.asList(options)));
            }
        }
    }

    public boolean isWaveFunctionCollapsed() {
        return Stream.of(this.waveElements).allMatch(
                row -> Stream.of(row).allMatch(element -> element.getOptions().size() == 1)
        );
    }

    /**
     * Propagates the change on the graph using BFS
     * @param x X position of element to start propagation from
     * @param y Y position of element to start propagation from
     */
    public void propagate(int x, int y) {
        LinkedList<WaveElementWithPosition> queue = new LinkedList<>();

        queue.add(new WaveElementWithPosition(
                this.waveElements[y][x],
                x,
                y
        ));

        while (queue.size() != 0) {
            WaveElementWithPosition currentWaveElement = queue.poll();
//            Iterates over the possible neighbours
            for (NeighbourOptions neighbourOption : this.getNeighbours(currentWaveElement.x(), currentWaveElement.y())) {
//                Get the actual neighbour element
                WaveElement neighbourElement = this.waveElements[neighbourOption.y()][neighbourOption.x()];
                Set<String> possibleNeighbourOptions = currentWaveElement.waveElement().getOptions()
                        .stream()
//                        For each option of the current wave element figure out which options are valid for the neighbour
                        .map(option -> neighbourOption.getPossibleNeighbours().apply(option))
//                        Reduce all the above options into on set
                        .reduce(
                                new HashSet<>(),
                                (set, possible_neighbours) -> {
                                    set.addAll(possible_neighbours);
                                    return set;
                                }
                        );
//                Keep only the neighbour options that are also possible based on the current element
                boolean changed = neighbourElement.keepPossible(possibleNeighbourOptions);
//                If the options were changed, we need to propagate the change further
                if (changed) {
                    queue.add(
                            new WaveElementWithPosition(
                                    neighbourElement,
                                    neighbourOption.x(),
                                    neighbourOption.y()
                            )
                    );
                }
            }
        }
    }

    private NeighbourOptions[] getNeighbours(int x, int y) {
        return Stream.of(new NeighbourOptions[]{
                new NeighbourOptions(x-1,y, option -> ElementRulesCollection.getElementRules(option).leftNeighbours()),
                new NeighbourOptions(x+1,y, option -> ElementRulesCollection.getElementRules(option).rightNeighbours()),
                new NeighbourOptions(x,y-1, option -> ElementRulesCollection.getElementRules(option).topNeighbours()),
                new NeighbourOptions(x,y+1, option -> ElementRulesCollection.getElementRules(option).bottomNeighbours()),
        })
            .filter(no -> (no.x() >= 0 && no.x() < this.width && no.y() >= 0 && no.y() < this.height))
                .toArray(NeighbourOptions[]::new);
    }

    public Optional<WaveElementWithPosition> getLowestEntropyElement() {
        ArrayList<WaveElementWithPosition> lowestEntropyElements = new ArrayList<>();
        double minEntropy = Integer.MAX_VALUE;
        for (int y = 0; y < this.waveElements.length; y++) {
            WaveElement[] row = this.waveElements[y];
            for (int x = 0; x < row.length; x++) {
                WaveElement waveElement = row[x];
                double entropy = waveElement.shannonEntropy();
                if (waveElement.getOptions().size() == 0) {
                    return Optional.empty();
                }
                if (entropy != 0 && entropy < minEntropy) {
                    lowestEntropyElements.clear();
                    lowestEntropyElements.add(new WaveElementWithPosition(waveElement, x, y));
                    minEntropy = entropy;
                } else if (entropy == minEntropy) {
                    lowestEntropyElements.add(new WaveElementWithPosition(waveElement, x, y));
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
        String rowBarrier = "═".repeat(width+1);
        stringRepresentation.append("╔").append(rowBarrier).append("╗\n");
        for (WaveElement[] row: this.waveElements) {
            stringRepresentation.append("║");
            for (WaveElement element: row) {
                stringRepresentation.append(element);
            }
            stringRepresentation.append(" ║\n");
        }
        stringRepresentation.append("╚").append(rowBarrier).append("╝\n");
        return stringRepresentation.toString();
    }
}

record NeighbourOptions(int x, int y, Function<String, Set<String>> getPossibleNeighbours) {}
