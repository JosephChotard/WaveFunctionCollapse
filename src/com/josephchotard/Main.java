package com.josephchotard;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        ElementRulesCollection.setElements(Map.of(
                "┫", new ElementRules(
                        "┫",
                        Stream.of("┣", "┳", "┻")
                            .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┣", "┳")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┣", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┣","┻")
                                .collect(Collectors.toCollection(HashSet::new))
                ),
                "┣", new ElementRules(
                        "┣",
                        Stream.of("┫", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┣", "┳")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┳", "┻")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┣","┻")
                                .collect(Collectors.toCollection(HashSet::new))
                ),
                " ", new ElementRules(
                        " ",
                        Stream.of("┫", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┻", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┣", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┳", " ")
                                .collect(Collectors.toCollection(HashSet::new))
                ),
                "┳", new ElementRules(
                        "┳",
                        Stream.of("┻", "┳", "┣")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┻", " ")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┻", "┳", "┫")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┫", "┻", "┣")
                                .collect(Collectors.toCollection(HashSet::new))
                ),
                "┻", new ElementRules(
                        "┻",
                        Stream.of("┻", "┳", "┣")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┳", "┣", "┫")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of("┻", "┳", "┫")
                                .collect(Collectors.toCollection(HashSet::new)),
                        Stream.of(" ", "┳")
                                .collect(Collectors.toCollection(HashSet::new))
                )
        ));

        Wave wave = new Wave(
                45,
                25,
                new String[]{"┫", "┣", " ", "┳", "┻"}
        );


        Optional<WaveElementWithPosition> waveElement = wave.getLowestEntropyElement();
        while (waveElement.isPresent()) {
            waveElement.get().waveElement().collapse();
            wave.propagate(waveElement.get().x(), waveElement.get().y());
            waveElement = wave.getLowestEntropyElement();
        }
        System.out.println(wave.isWaveFunctionCollapsed());

        System.out.println(wave);
    }
}

// Assign each element an ID (cell type: ex |-)  and keep an info somewhere of the representation as well as neighbour IDs
