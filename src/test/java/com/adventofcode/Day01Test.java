package com.adventofcode;

import static org.assertj.core.api.Assertions.assertThat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang3.tuple.Pair;

/** Unit test for simple Day01. */
public class Day01Test extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public Day01Test(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(Day01Test.class);
  }

  public void testFirstDigitString() {
    Pair<Integer, Integer> result = Day01.findFirstDigitString("abcone2threexyz");
    assertThat(result.getLeft()).isEqualTo(3);
    assertThat(result.getRight()).isEqualTo(1);
  }

  public void testLastDigitString() {
    Pair<Integer, Integer> result = Day01.findLastDigitString("abcone2threexyz");
    assertThat(result.getLeft()).isEqualTo(7);
    assertThat(result.getRight()).isEqualTo(3);
  }

  public void testFirstDigitNumeric() {
    Pair<Integer, Integer> result = Day01.findFirstDigitNumeric("abcone2threexyz");
    assertThat(result.getLeft()).isEqualTo(6);
    assertThat(result.getRight()).isEqualTo(2);
  }

  public void testLastDigitNumeric() {
    Pair<Integer, Integer> result = Day01.findLastDigitNumeric("abcone2threexyz");
    assertThat(result.getLeft()).isEqualTo(6);
    assertThat(result.getRight()).isEqualTo(2);
  }

  public void testFirstDigit() {
    int digit = Day01.findFirstDigit("twotj9l8onetwoned");
    assertThat(digit).isEqualTo(2);
  }

  public void testLastDigit() {
    int digit = Day01.findLastDigit("15twone");
    assertThat(digit).isEqualTo(1);
  }
}
