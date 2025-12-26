import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestExample {
   
   @Test
   public void testNode()
   {
      Node<Integer> b = new Node<Integer>(7);
      Node<Integer> a = new Node<Integer>(18, b);

      int v = a.getValue();
      assertEquals(v, 18);
     
      assertEquals(a.getNext(), b);          
   }
   
   @Test
   public void testOutput()
   {
      PrintStream originalOut = System.out;

      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      
      Node<Integer> b = new Node<Integer>(7);
      Node<Integer> a = new Node<Integer>(18, b);
      System.out.println(a);
      assertEquals("18 ==> 7 ==> null\n", outContent.toString());
      
      System.setOut(originalOut);
   }
}
