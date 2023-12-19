package com.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

public class Day19part1 {
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day19input.txt"));

    int separatorIdx = lines.indexOf("");
    List<String> rulesInput = lines.subList(0, separatorIdx);
    List<String> partsInput = lines.subList(separatorIdx + 1, lines.size());

    List<Workflow> workflows = parseWorkflows(rulesInput);
    List<Part> parts = parseParts(partsInput);

    int result = processParts(parts, workflows);
    System.out.println(result);
  }

  private static int processParts(List<Part> parts, List<Workflow> workflows) {
    int result = 0;
    for (Part part : parts) {
      result += processPart(part, workflows);
    }
    return result;
  }

  private static int processPart(Part part, List<Workflow> workflows) {
    Pair<Action, String> result = processWorkflow(part, "in", workflows);
    while (result.getLeft().equals(Action.F)) {
      result = processWorkflow(part, result.getRight(), workflows);
    }
    System.out.println(result + " | " + part);
    return result.getLeft().equals(Action.A)
        ? part.getX() + part.getM() + part.getA() + part.getS()
        : 0;
  }

  private static Pair<Action, String> processWorkflow(
      Part part, String workflowName, List<Workflow> workflows) {
    Workflow workflow =
        workflows.stream()
            .filter(wf -> wf.getName().equals(workflowName))
            .findFirst()
            .orElseThrow();
    return workflow.run(part);
  }

  private static List<Workflow> parseWorkflows(List<String> workflows) {
    List<Workflow> result = new ArrayList<>();
    Pattern pattern = Pattern.compile("(?<name>[a-z]+)\\{(?<rules>.+)}");

    for (String workflow : workflows) {
      Matcher matcher = pattern.matcher(workflow);
      matcher.find();
      String name = matcher.group("name");
      String[] rules = matcher.group("rules").split(",");

      result.add(new Workflow(name, parseRules(rules)));
    }
    return result;
  }

  private static List<Rule> parseRules(String[] rules) {
    List<Rule> result = new ArrayList<>();
    for (String rule : rules) {
      if (rule.contains(":")) {
        String[] ruleParts = rule.split(":");
        result.add(
            isForward(ruleParts[1])
                ? new Rule(ruleParts[0], Action.F, ruleParts[1])
                : new Rule(ruleParts[0], Action.from(ruleParts[1]), null));
      } else {
        result.add(
            isForward(rule)
                ? new Rule(null, Action.F, rule)
                : new Rule(null, Action.from(rule), null));
      }
    }
    return result;
  }

  private static boolean isForward(String action) {
    return action.length() > 1;
  }

  private static List<Part> parseParts(List<String> parts) {
    List<Part> result = new ArrayList<>();

    Pattern pattern = Pattern.compile("\\{x=(?<x>.+),m=(?<m>.+),a=(?<a>.+),s=(?<s>.+)}");
    for (String part : parts) {
      Matcher matcher = pattern.matcher(part);
      matcher.find();
      int x = Integer.parseInt(matcher.group("x"));
      int m = Integer.parseInt(matcher.group("m"));
      int a = Integer.parseInt(matcher.group("a"));
      int s = Integer.parseInt(matcher.group("s"));
      result.add(new Part(x, m, a, s));
    }
    return result;
  }

  @Data
  @AllArgsConstructor
  static class Part {
    private int x;
    private int m;
    private int a;
    private int s;
  }

  enum Action {
    A, // Accept
    R, // Reject
    F; // Forward

    public static Action from(String action) {
      return Arrays.stream(Action.values())
          .filter(a -> a.name().equals(action))
          .findFirst()
          .orElseThrow();
    }
  }

  @Data
  @AllArgsConstructor
  static class Rule {
    private String condition;
    private Action action;
    private String targetWorkflow;

    @SneakyThrows
    public boolean isTrue(Part part) {
      if (condition == null) {
        return true;
      }
      String subject = condition.substring(0, 1);
      String operation = condition.substring(1, 2);
      int conditionValue = Integer.parseInt(condition.substring(2));
      int value = (Integer) Part.class.getMethod("get" + subject.toUpperCase()).invoke(part);

      return switch (operation) {
        case ">" -> value > conditionValue;
        case "<" -> value < conditionValue;
        default -> throw new IllegalStateException("Unexpected operation: " + operation);
      };
    }
  }

  @Data
  @AllArgsConstructor
  static class Workflow {
    private String name;
    private List<Rule> rules;

    public Pair<Action, String> run(Part part) {
      int ruleIdx = 0;
      while (ruleIdx < rules.size()) {
        Rule rule = rules.get(ruleIdx);
        if (rule.isTrue(part)) {
          return Pair.of(rule.getAction(), rule.getTargetWorkflow());
        }
        ruleIdx++;
      }
      return null;
    }
  }
}
