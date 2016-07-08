package org.renjin.idea.rt;

import org.renjin.eval.EvalException;
import org.renjin.eval.Session;
import org.renjin.eval.SessionBuilder;
import org.renjin.parser.RParser;
import org.renjin.sexp.FunctionCall;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Symbol;

import java.io.*;
import java.util.Arrays;

/**
 * Runs tests and reports results
 */
public class TestRunner {
  
  public static void main(String[] args) {
    System.out.println("TestRunner invoked: " + Arrays.asList(args));

    File scriptFile = new File(args[0]);
    String defaultPackages = args[1];
    String testFunction = null;
    if(args.length > 2) {
      testFunction = args[2];
    }
    
    SEXP testScript;
    try {
      testScript = RParser.parseAllSource(new FileReader(scriptFile));
    } catch (IOException e) {
      System.err.println(args[0] + " does not exist.");
      System.exit(-1);
      return;
    }

    SessionBuilder builder = new SessionBuilder();
    if(!defaultPackages.equals("base")) {
      builder.withDefaultPackages();
    }
    Session session = builder.build();
    session.getTopLevelContext().evaluate(testScript);
    
    if(testFunction != null) {
      runTest(session, testFunction);
    }
  }

  private static void runTest(Session session, String testFunction) {
    System.out.print("Running test " + testFunction + "... ");
    System.out.flush();

    TestResult result = executeTest(session, testFunction);
    if(result.isPassed()) {
      System.out.println("PASSED");
    } else {
      System.out.println("FAILED");
    }
  }

  private static TestResult executeTest(Session session, String testFunction) {
    try {
      session.getTopLevelContext().evaluate(FunctionCall.newCall(Symbol.get(testFunction)));
      return new TestResult(true, null);
      
    } catch (EvalException e) {
      StringWriter stringWriter = new StringWriter();
      e.printRStackTrace(new PrintWriter(stringWriter));
      return new TestResult(false, null, stringWriter.toString());
    }
  }
}
