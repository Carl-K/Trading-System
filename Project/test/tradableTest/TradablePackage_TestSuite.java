package tradableTest;

import junit.framework.*;

public class TradablePackage_TestSuite {
   public static void main(String[] a) {
      // add the test's in the suite
      TestSuite suite = new TestSuite(Order_UnitTest.class, QuoteSide_UnitTest.class, Quote_UnitTest.class);
      TestResult result = new TestResult();
      suite.run(result);
      System.out.println("Number of test cases = " + result.runCount());
      System.out.println("Number of errors = " + result.errorCount());
      System.out.println("Number of fails = " + result.failureCount());
   }
}
