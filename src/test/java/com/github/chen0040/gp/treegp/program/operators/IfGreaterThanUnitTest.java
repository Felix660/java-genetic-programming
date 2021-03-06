package com.github.chen0040.gp.treegp.program.operators;


import com.github.chen0040.gp.exceptions.SizeMismatchedException;
import com.github.chen0040.gp.treegp.program.Primitive;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 11/5/2017.
 */
public class IfGreaterThanUnitTest {

   @Test
   public void test_makeCopy(){
      IfGreaterThan op1 = new IfGreaterThan();
      AssertionsForClassTypes.assertThat(op1.arity()).isEqualTo(4);
      Primitive op2 = op1.makeCopy();
      assertThat(op1).isEqualTo(op2);
   }

   @Test
   public void test_ifGreaterThan(){
      IfGreaterThan op1 = new IfGreaterThan();
      op1.beforeExecute(Arrays.asList(0.1, 0.05, 1.0, 2.0), null);
      op1.execute(null);
      assertThat(op1.getValue()).isEqualTo(1.0);
   }

   @Test
   public void test_ifNotGreaterThan(){
      IfGreaterThan op1 = new IfGreaterThan();
      op1.beforeExecute(Arrays.asList(0.1, 0.5, 1.0, 2.0), null);
      op1.execute(null);
      assertThat(op1.getValue()).isEqualTo(2.0);
   }

   @Test(expectedExceptions = SizeMismatchedException.class)
   public void test_readException(){

      IfGreaterThan op1 = new IfGreaterThan();
      op1.beforeExecute(Arrays.asList(0.1, 0.5, 1.0), null);
   }

}
