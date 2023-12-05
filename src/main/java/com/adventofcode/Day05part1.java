package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Day05part1 {
  public static final String SEED_TO_SOIL = "seed-to-soil";
  public static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
  public static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
  public static final String WATER_TO_LIGHT = "water-to-light";
  public static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
  public static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
  public static final String HUMIDITY_TO_LOCATION = "humidity-to-location";
  public static final List<String> MAPPING_TYPES =
      List.of(
          SEED_TO_SOIL,
          SOIL_TO_FERTILIZER,
          FERTILIZER_TO_WATER,
          WATER_TO_LIGHT,
          LIGHT_TO_TEMPERATURE,
          TEMPERATURE_TO_HUMIDITY,
          HUMIDITY_TO_LOCATION);

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day05input.txt"));

    Map<String, List<Mapping>> mappings = mapMappings(lines);

    ArrayList<Pair<Long, Long>> seedLocations = new ArrayList<>();
    String[] seeds = lines.get(0).split(":")[1].trim().split(" ");
    for (String seed : seeds) {
      seedLocations.add(Pair.of(Long.parseLong(seed), findCorrespondingLocation(Long.parseLong(seed), mappings)));
    }
    seedLocations.sort(Comparator.comparingLong(Pair::getRight));

    System.out.println("Result: " + seedLocations.get(0).getRight());
  }

  private static Long findCorrespondingLocation(Long seed, Map<String, List<Mapping>> mappings) {
    long result = seed;
    for (String mappingType : MAPPING_TYPES) {
      result = findCorrespondingMapping(result, mappingType, mappings);
    }
    return result;
  }

  private static long findCorrespondingMapping(
      long value, String mapType, Map<String, List<Mapping>> allMappings) {
    List<Mapping> mappings = allMappings.get(mapType);

    Optional<Mapping> correspondingMapping =
        mappings.stream().filter(mapping -> mapping.isInRange(value)).findFirst();

    return correspondingMapping.isPresent()
        ? correspondingMapping.get().getDestination(value)
        : value;
  }

  private static Map<String, List<Mapping>> mapMappings(List<String> lines) {
    Map<String, List<Mapping>> mappings = createMapOfTypes();

    mapType(SEED_TO_SOIL, lines, mappings);
    mapType(SOIL_TO_FERTILIZER, lines, mappings);
    mapType(FERTILIZER_TO_WATER, lines, mappings);
    mapType(WATER_TO_LIGHT, lines, mappings);
    mapType(LIGHT_TO_TEMPERATURE, lines, mappings);
    mapType(TEMPERATURE_TO_HUMIDITY, lines, mappings);
    mapType(HUMIDITY_TO_LOCATION, lines, mappings);

    return mappings;
  }

  private static Integer findHeaderIndex(List<String> lines, String seedToSoil) {
    return lines.indexOf(seedToSoil + " map:");
  }

  private static void mapType(
      String type, List<String> lines, Map<String, List<Mapping>> mappings) {
    int ptr = findHeaderIndex(lines, type) + 1;
    while (ptr < lines.size() && StringUtils.isNotBlank(lines.get(ptr))) {
      String[] values = lines.get(ptr).split(" ");
      mappings
          .get(type)
          .add(
              Mapping.builder()
                  .destination(Long.parseLong(values[0]))
                  .source(Long.parseLong(values[1]))
                  .range(Long.parseLong(values[2]))
                  .build());
      ptr++;
    }
  }

  private static Map<String, List<Mapping>> createMapOfTypes() {
    Map<String, List<Mapping>> mappings = new HashMap<>();
    for (String mappingType : MAPPING_TYPES) {
      mappings.put(mappingType, new ArrayList<>());
    }
    return mappings;
  }

  @Data
  @Builder
  static class Mapping {
    private long source;
    private long destination;
    private long range;

    public Long getDestination(Long value) {
      return isInRange(value) ? destination + (value - this.source) : null;
    }

    private boolean isInRange(Long value) {
      return value >= this.source && value <= this.source + range;
    }
  }
}
