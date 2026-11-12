package com.vex.baritone.api.utils;

import com.vex.baritone.api.behavior.IBehavior;
import com.vex.baritone.api.utils.input.Input;

public interface IInputOverrideHandler extends IBehavior {
   boolean isInputForcedDown(Input var1);

   void setInputForceState(Input var1, boolean var2);

   void clearAllKeys();
}
