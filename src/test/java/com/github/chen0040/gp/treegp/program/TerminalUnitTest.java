package com.github.chen0040.gp.treegp.program;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 10/5/2017.
 */
public class TerminalUnitTest {

   @Test(expectedExceptions = RuntimeException.class)
   public void testReadonlyTerminal(){
      Terminal t = new Terminal("read-only-value", 0.1, "", true);
      t.setValue(0.2);
      assertThat(t.getSymbol()).isEqualTo("read-only-value");
      assertThat(t.arity()).isEqualTo(0);
      assertThat(t.isReadOnly()).isTrue();
      assertThat(t.isTerminal()).isTrue();
      assertThat(t.getIndex()).isEqualTo(0);
   }

   @Test
   public void testMutableTerminal() {
      Terminal t = new Terminal("mutable");
      t.setValue(0.3);
      t.setIndex(3);
      assertThat(t.getValue()).isEqualTo(0.3);
      assertThat(t.arity()).isEqualTo(0);
      assertThat(t.isReadOnly()).isFalse();
      assertThat(t.isTerminal()).isTrue();
      assertThat(t.getIndex()).isEqualTo(3);

      Terminal t2 = t.makeCopy();
      assertThat(t).isEqualTo(t2);
   }

   @Test(expectedExceptions = IndexOutOfBoundsException.class)
   public void testGetInputException(){
      Terminal t = new Terminal("mutable");
      t.setValue(0.3);
      t.getInput(0);
   }

   @Test(expectedExceptions = IndexOutOfBoundsException.class)
   public void testSetInputException(){
      Terminal t = new Terminal("mutable");
      t.setValue(0.3);
      t.setInput(2, 0.1);
   }

   @Test(expectedExceptions = RuntimeException.class)
   public void testReadException(){
      Terminal t = new Terminal("mutable");
      t.setValue(0.3);
      t.beforeExecute(Arrays.asList(0.1, 0.2), null);
      t.beforeExecuteWithText(Arrays.asList("Hello", "World"), null);
   }

   @Test
   public void testTerminalWithDefaultConstructor(){
      Terminal t = new Terminal();
      assertThat(t.getSymbol()).isEqualTo("");
      assertThat(t.isReadOnly()).isFalse();
   }
}
