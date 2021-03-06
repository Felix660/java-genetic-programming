package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.exceptions.InvalidCostException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Created by xschen on 14/5/2017.
 */
@Getter
@Setter
public class Solution implements Comparable<Solution>, Serializable {
   private static final long serialVersionUID = 5589700036065720457L;
   private double cost;
   private boolean costValid;
   private final List<Program> trees = new ArrayList<>();

   public Solution() {

   }

   public Solution(int treeCount, Function<Integer, Program> generator){
      for (int i = 0; i < treeCount; ++i)
      {
         trees.add(generator.apply(i));
      }
   }

   public Solution makeCopy()
   {
      Solution clone = new Solution();
      clone.copy(this);
      return clone;
   }

   public void copy(Solution that){
      trees.clear();
      for(int i=0; i < that.trees.size(); ++i){
         trees.add(that.trees.get(i).makeCopy());
      }
      cost = that.cost;
      costValid = that.costValid;
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < trees.size(); ++i)
      {
         if (i != 0)
         {
            sb.append("\n");
         }
         sb.append("Trees[").append(i).append("]: ").append(trees.get(i));
      }
      return sb.toString();
   }

   public String mathExpression()
   {
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < trees.size(); ++i)
         {
            if (i != 0)
            {
               sb.append("\n");
            }
            sb.append("Trees[").append(i).append("]: ").append(trees.get(i).mathExpression());
         }
         return sb.toString();
   }

   public void invalidateCost(){
      costValid = false;
   }

   public void execute(Observation observation){

      for(int i=0; i < trees.size(); ++i){
         Program tree = trees.get(i);
         tree.read(observation);
         double output = tree.execute(observation);
         observation.setPredictedOutput(i % observation.outputCount(), output);
      }
   }

   public void executeWithText(Observation observation){

      for(int i=0; i < trees.size(); ++i){
         Program tree = trees.get(i);
         tree.readText(observation);
         String output = tree.executeWithText(observation);
         observation.setPredictedOutput(i % observation.outputCount(), output);
      }
   }

   public int averageTreeDepth()
   {
      if (trees.isEmpty()) return 0;
      int sum=0;
      for (int i = 0; i < trees.size(); ++i)
      {
         sum += (trees.get(i)).getDepth();
      }
      return sum / trees.size();

   }

   public int averageTreeLength()
   {
      if (trees.isEmpty()) return 0;
      int sum=0;
      for (int i = 0; i < trees.size(); ++i)
      {
         sum += (trees.get(i)).getLength();
      }
      return sum / trees.size();

   }


   @Override public int compareTo(Solution that) {
      if(!costValid || !that.costValid) {
         throw new InvalidCostException("cost of the solutions involved in the comparison is not valid for comparison");
      }

      int cmp = Double.compare(cost, that.cost);

      if(cmp == 0) {
         int this_better_count = 0;
         for (int i = 0; i < trees.size(); ++i)
         {
            if (trees.get(i).compareTo(that.trees.get(i)) < 0)
            {
               this_better_count++;
            }
         }

         return Integer.compare(trees.size(), this_better_count * 2);
      } else {
         return cmp;
      }
   }


   public boolean isBetterThan(Solution that) {
      return compareTo(that) < 0;
   }
}
