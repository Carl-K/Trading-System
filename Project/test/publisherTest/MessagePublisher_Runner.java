package publisherTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MessagePublisher_Runner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(MessagePublisher_UnitTest.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
}