package org.renjin.idea.rt;

public class TestResult {
  private boolean passed;
  private String output;
  private String stackTrace;

  public TestResult(boolean passed, String output) {
    this.passed = passed;
    this.output = output;
  }

  public TestResult(boolean passed, String output, String stackTrace) {
    this.passed = passed;
    this.output = output;
    this.stackTrace = stackTrace;
  }

  public boolean isPassed() {
    return passed;
  }

  public String getOutput() {
    return output;
  }

  public String getStackTrace() {
    return stackTrace;
  }
}
