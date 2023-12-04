package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day04input.txt"));

        int totalCards = 0;

        List<Integer> copies = new ArrayList<>();

        for (String line : lines) {
            String game = line.split(":")[1].trim();
            String[] split = game.split("\\|");
            List<String> winningNumbers = Arrays.stream(split[0].trim().split("\\s+")).toList();
            String[] myNumbers = split[1].trim().split("\\s+");

            long matchCount = Arrays.stream(myNumbers).filter(winningNumbers::contains).count();

            int currentCardCopies = 0;
            if (copies.size() > 0) {
                currentCardCopies = copies.remove(0);
            }

            for (int i = 0; i < 1 + currentCardCopies; i++) {
                totalCards++;
                for (int j = 0; j < matchCount; j++) {
                    if (j < copies.size()) {
                        copies.set(j, copies.get(j) + 1);
                    } else {
                        copies.add(1);
                    }
                }
            }
        }
        System.out.println("Result: " + totalCards);
    }

}
