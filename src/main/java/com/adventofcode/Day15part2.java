package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class Day15part2 {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day15input.txt"));
    String[] steps = lines.get(0).split(",");
    Map<Integer, Box> boxes = initializeBoxes();

    for (String step : steps) {
      String[] split = step.split("=|-");
      String lensLabel = split[0];
      int boxId = hash(lensLabel);
      Box box = boxes.get(boxId);
      if (split.length > 1) {
        // = operation
        int focalLength = Integer.parseInt(split[1]);
        box.addOrReplaceLens(new Lens(lensLabel, focalLength));
      } else {
        // - operation
        box.removeLens(lensLabel);
      }
    }
    int totalFocusingPower = getTotalFocusingPower(boxes);
    System.out.println(totalFocusingPower);
  }

  private static int getTotalFocusingPower(Map<Integer, Box> boxes) {
    int totalFocusingPower = 0;
    for (Box box : boxes.values()) {
      totalFocusingPower += box.getBoxFocusingPower();
    }
    return totalFocusingPower;
  }

  private static Map<Integer, Box> initializeBoxes() {
    Map<Integer, Box> boxes = new HashMap<>();
    for (int i = 0; i < 256; i++) {
      boxes.put(i, new Box(i));
    }
    return boxes;
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

  @Data
  @RequiredArgsConstructor
  private static class Box {
    private final int id;
    private List<Lens> lenses = new ArrayList<>();

    public void removeLens(String lensLabel) {
      lenses.removeIf(lens -> lens.getLabel().equals(lensLabel));
    }

    public void addOrReplaceLens(Lens newLens) {
      List<Lens> foundLenses =
          lenses.stream().filter(lens -> lens.getLabel().equals(newLens.getLabel())).toList();
      if (!foundLenses.isEmpty()) {
        foundLenses.forEach(lens -> lens.setFocalLength(newLens.getFocalLength()));
      } else {
        lenses.add(newLens);
      }
    }

    private int getBoxFocusingPower() {
      int result = 0;
      for (int i = 0; i < lenses.size(); i++) {
        result += getFocusingPower(i + 1);
      }
      return result;
    }

    private int getFocusingPower(int lensPosition) {
      return (id + 1) * lensPosition * lenses.get(lensPosition - 1).getFocalLength();
    }
  }

  @Data
  @AllArgsConstructor
  private static class Lens {
    private String label;
    private int focalLength;
  }
}
