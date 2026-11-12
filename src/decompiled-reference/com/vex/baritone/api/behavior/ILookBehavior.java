package com.vex.baritone.api.behavior;

import com.vex.baritone.api.behavior.look.IAimProcessor;
import com.vex.baritone.api.utils.Rotation;

public interface ILookBehavior extends IBehavior {
   void updateTarget(Rotation var1, boolean var2);

   IAimProcessor getAimProcessor();
}
