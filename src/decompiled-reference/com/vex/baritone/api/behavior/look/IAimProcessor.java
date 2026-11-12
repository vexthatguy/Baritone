package com.vex.baritone.api.behavior.look;

import com.vex.baritone.api.utils.Rotation;

public interface IAimProcessor {
   Rotation peekRotation(Rotation var1);

   ITickableAimProcessor fork();
}
