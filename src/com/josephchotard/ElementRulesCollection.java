package com.josephchotard;

import java.util.Map;
import java.util.Set;

public class ElementRulesCollection {
    private static Map<String, ElementRules> elements;

    public static void setElements(Map<String, ElementRules> elements) {
        ElementRulesCollection.elements = elements;
    }

    public static ElementRules getElementRules(String elementId) {
        return ElementRulesCollection.elements.get(elementId);
    }

    public static Set<String> getElementIds() {
        return ElementRulesCollection.elements.keySet();
    }
}

record ElementRules(
        String representation,
        Set<String> leftNeighbours,
        Set<String> topNeighbours,
        Set<String> rightNeighbours,
        Set<String> bottomNeighbours
) {}

