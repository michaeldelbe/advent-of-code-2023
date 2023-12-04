package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day04part1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day04input.txt"));

        int sum = 0;

        for (String line : lines) {
            String game = line.split(":")[1].trim();
            String[] split = game.split("\\|");
            List<String> winningNumbers = Arrays.stream(split[0].trim().split("\\s+")).toList();
            String[] myNumbers = split[1].trim().split("\\s+");

            long matchCount = Arrays.stream(myNumbers).filter(winningNumbers::contains).count();
            int points = matchCount > 0 ? 1 : 0;

            for (int i = 1; i < matchCount; i++) {
                points = points * 2;
            }
            sum += points;
        }
        System.out.println("Result: " + sum);
    }

}
