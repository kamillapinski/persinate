package dev.lapinski.persinate.model.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UnderscoreTranslationStrategy implements NameTranslationStrategy {
    @Override
    public String translateName(String name) {
        List<Integer> bigLettersPositions = getBigLettersPositions(name);

        if (bigLettersPositions.isEmpty()) {
            return name.toLowerCase().intern();
        }

        List<String> nameParts = splitIntoLowercaseParts(name, bigLettersPositions);

        return String.join("_", nameParts);
    }

    private static List<Integer> getBigLettersPositions(String name) {
        return IntStream
            .range(0, name.length())
            .skip(1) //do not include the first letter
            .filter(charIdx -> Character.isUpperCase(name.charAt(charIdx)))
            .boxed()
            .toList();
    }

    private static List<String> splitIntoLowercaseParts(String name, List<Integer> bigLettersPositions) {
        String lowercaseName = name.toLowerCase().intern();

        List<String> nameParts = new ArrayList<>(4);

        for (int letterIdx = 0; letterIdx < bigLettersPositions.size(); letterIdx++) {
            int prevIdx = switch (letterIdx) {
                case 0 -> 0;
                default -> bigLettersPositions.get(letterIdx - 1);
            };

            nameParts.add(lowercaseName.substring(prevIdx, bigLettersPositions.get(letterIdx)));
        }

        int lastIdx = bigLettersPositions.getLast();
        nameParts.add(lowercaseName.substring(lastIdx));
        return nameParts;
    }
}
