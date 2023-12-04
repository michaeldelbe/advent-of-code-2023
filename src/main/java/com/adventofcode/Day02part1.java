package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class Day02part1 {

    public static final Map<String, Integer> MAX_CUBES = Map.of("red", 12, "green", 13, "blue", 14);

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day02input.txt"));

        int sum = 0;
        for (String line : lines) {
            String[] split = line.split(":");
            String header = split[0];
            String game = split[1].trim();
            String[] rounds = game.split(";");
            boolean possible = true;

            int gameId = Integer.parseInt(header.split(" ")[1]);

            for (String round : rounds) {
                for (String group : round.trim().split(",")) {
                    String[] groupSplit = group.trim().split(" ");
                    int number = Integer.parseInt(groupSplit[0]);
                    String color = groupSplit[1];
                    if (number > MAX_CUBES.get(color)) {
                        possible = false;
                        break;
                    }
                }
                if (!possible) {
                    break;
                }
            }

            if (possible) {
                sum += gameId;
            }
        }

        System.out.println("Result: " + sum);
    }

}
