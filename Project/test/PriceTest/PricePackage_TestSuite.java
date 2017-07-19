package PriceTest;

import junit.framework.*;

public class PricePackage_TestSuite {
   public static void main(String[] a) {
      // add the test's in the suite
      TestSuite suite = new TestSuite(Price_UnitTest.class, MarketPrice_UnitTest.class, PriceFactory_UnitTest.class );
      TestResult result = new TestResult();
      suite.run(result);
      System.out.println("Number of test cases = " + result.runCount());
      System.out.println("Number of errors = " + result.errorCount());
      System.out.println("Number of fails = " + result.failureCount());
   }
}
