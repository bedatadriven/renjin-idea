package org.renjin.idea.rt;

import com.google.common.base.Strings;
import org.renjin.eval.EvalException;
import org.renjin.eval.Session;
import org.renjin.eval.SessionBuilder;
import org.renjin.parser.RParser;
import org.renjin.sexp.Closure;
import org.renjin.sexp.FunctionCall;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Symbol;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    List<String> packagesToLoad;
    if(Strings.isNullOrEmpty(defaultPackages)) {
      packagesToLoad = Session.DEFAULT_PACKAGES;
    } else {
      packagesToLoad = Arrays.asList(defaultPackages.split("\\s*,\\s*"));
    }
    Session session = builder.build();
    
    if(!defaultPackages.isEmpty()) {
      for (String packageName : packagesToLoad) {
        if(!packageName.equals("base")) {
          try {
            session.getTopLevelContext().evaluate(FunctionCall.newCall(Symbol.get("library"), Symbol.get(packageName)));
          } catch (EvalException e) {
            System.err.println("WARNING: default package \"" + packageName + "\" could not be loaded: " + e.getMessage());
          }
        }
      }
    }

    try {
      session.getTopLevelContext().evaluate(testScript);
    } catch(Exception e) {
      if(e instanceof EvalException) {
        ((EvalException) e).printRStackTrace(System.out);
      }
      e.printStackTrace();
      System.exit(-1);
    }

    if(testFunction != null) {
      runTest(session, testFunction);
    } else {
      runAllTests(session);
    }
  }

  private static void runAllTests(Session session) {
    for (Symbol symbol : session.getGlobalEnvironment().getSymbolNames()) {
      if(symbol.getPrintName().startsWith("test.")) {
        SEXP value = session.getGlobalEnvironment().getVariable(symbol);
        if(value instanceof Closure) {
          Closure testFunction = (Closure) value;
          if(testFunction.getFormals().length() == 0) {
            runTest(session, symbol.getPrintName());
          }
        }
      }
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
      System.out.print(result.getStackTrace());
    }
  }

  private static TestResult executeTest(Session session, String testFunction) {
    try {
      session.getTopLevelContext().evaluate(FunctionCall.newCall(Symbol.get(testFunction)));
      return new TestResult(true, null);
      
    } catch (EvalException e) {
      StringWriter stringWriter = new StringWriter();
      stringWriter.append("ERROR: ").append(e.getMessage());
      e.printRStackTrace(new PrintWriter(stringWriter));
      return new TestResult(false, null, stringWriter.toString());
    }
  }
}
