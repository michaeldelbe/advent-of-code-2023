package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day15part1 {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day15input.txt"));

    int result = 0;
    String[] steps = lines.get(0).split(",");

    for (String step : steps) {
      result += hash(step);
    }
    System.out.println(result);
  }

  private static int hash(String step) {
    int currentValue = 0;
    for (char c : step.toCharArray()) {
      currentValue += c;
      currentValue *= 17;
      currentValue %= 256;
    }
    return currentValue;
  }
}
