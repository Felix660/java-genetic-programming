package com.github.chen0040.gp.treegp.program;


import com.github.chen0040.data.utils.StringUtils;
import com.github.chen0040.gp.commons.Observation;

import java.io.Serializable;


/**
 * Created by xschen on 10/5/2017.
 */
public class Terminal extends Primitive implements Serializable {
   private static final long serialVersionUID = 8438360593097636018L;

   public Terminal(){
      super();
   }


   @Override public Terminal makeCopy() {
      Terminal clone = new Terminal(getSymbol(), getValue(), getTextValue(), isReadOnly());
      clone.copy(this);
      return clone;
   }


   @Override public void execute(Observation observation){

   }


   public Terminal(String symbol, double value, String textValue, boolean readonly) {
      super(0, symbol, value, textValue, readonly);
   }

   public Terminal(String symbol) {
      super(0, symbol, 0, "", false);
   }

   @Override
   public String toString(){
      if(isReadOnly()){
         if(StringUtils.isEmpty(getTextValue())) {
            return "" + getValue();
         } else if(getValue() == 0) {
            return getTextValue();
         } else {
            return getTextValue() + "(" + getValue() + ")";
         }
      } else {
         return getName();
      }
   }
}
