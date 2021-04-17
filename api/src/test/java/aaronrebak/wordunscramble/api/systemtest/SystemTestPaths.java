package aaronrebak.wordunscramble.api.systemtest;

public abstract class SystemTestPaths {

  private SystemTestPaths() {
  }

  public static final String HEALTH_CHECK_PATH = "/actuator/health";

  public static final String CALCULATE_WORDSQUARE_URL = "calculate";

}
