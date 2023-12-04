package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class Day02part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day02input.txt"));

        int sum = 0;

        for (String line : lines) {
            String[] split = line.split(":");
            String game = split[1].trim();
            String[] rounds = game.split(";");
            Map<String, Integer> minCubes = new HashMap<>(Map.of("red", 0, "green", 0, "blue", 0));

            for (String round : rounds) {
                for (String group : round.trim().split(",")) {
                    String[] groupSplit = group.trim().split(" ");
                    Integer number = Integer.valueOf(groupSplit[0]);
                    String color = groupSplit[1];
                    if (minCubes.get(color) < number) {
                        minCubes.replace(color, number);
                    }
                }
            }
            Integer power = minCubes.values().stream().reduce(1, (a, b) -> a * b);
            sum += power;
        }
        System.out.println("Result: " + sum);
    }

}
