package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Day01 {
  public static final String NUM_REGEX = "(one|two|three|four|five|six|seven|eight|nine)";
  public static final Map<String, Integer> NUM_MAP =
      Map.of(
          "one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8,
          "nine", 9);

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day01input.txt"));

    int sum = 0;

    for (String line : lines) {
      String value;
      value = String.valueOf(findFirstDigit(line));
      value = value.concat(String.valueOf(findLastDigit(line)));
      sum += Integer.parseInt(value);
    }
    System.out.println("Result: " + sum);
  }

  static int findFirstDigit(String line) {
    Pair<Integer, Integer> firstDigitNumeric = findFirstDigitNumeric(line);
    Pair<Integer, Integer> firstDigitString = findFirstDigitString(line);

    return firstDigitString == null
        ? firstDigitNumeric.getRight()
        : (firstDigitString.getLeft() < firstDigitNumeric.getLeft()
            ? firstDigitString.getRight()
            : firstDigitNumeric.getRight());
  }

  static int findLastDigit(String line) {
    Pair<Integer, Integer> lastDigitNumeric = findLastDigitNumeric(line);
    Pair<Integer, Integer> lastDigitString = findLastDigitString(line);

    return lastDigitString == null
        ? lastDigitNumeric.getRight()
        : (lastDigitString.getLeft() > lastDigitNumeric.getLeft()
            ? lastDigitString.getRight()
            : lastDigitNumeric.getRight());
  }

  static Pair<Integer, Integer> findLastDigitNumeric(String line) {
    String reversed = StringUtils.reverse(line);
    Pair<Integer, Integer> pair = findFirstDigitNumeric(reversed);
    return Pair.of(line.length() - pair.getLeft() - 1, pair.getRight());
  }

  static Pair<Integer, Integer> findFirstDigitNumeric(String line) {
    char[] lineChars = line.toCharArray();
    Integer digit = null;
    int idx = 0;
    while (digit == null && idx < lineChars.length) {
      char c = lineChars[idx];
      if ("0123456789".indexOf(c) != -1) {
        digit = Character.getNumericValue(c);
      }
      idx++;
    }

    return Pair.of(idx - 1, digit);
  }

  static Pair<Integer, Integer> findFirstDigitString(String line) {
    Pattern pattern = Pattern.compile(NUM_REGEX);
    Matcher matcher = pattern.matcher(line);
    String matched = null;
    Integer index = null;
    if (matcher.find()) {
      matched = matcher.group();
      index = matcher.start();
    }
    return matched != null ? Pair.of(index, NUM_MAP.get(matched)) : null;
  }

  static Pair<Integer, Integer> findLastDigitString(String line) {
    Pattern pattern = Pattern.compile(NUM_REGEX);
    Matcher matcher = pattern.matcher(line);
    String matched = null;
    Integer index = null;
    int matcherStartIdx = 0;
    while (matcherStartIdx < line.length() && matcher.find(matcherStartIdx)) {
      matched = matcher.group();
      index = matcher.start();
      matcherStartIdx = index + 1;
    }
    return matched != null ? Pair.of(index, NUM_MAP.get(matched)) : null;
  }
}
