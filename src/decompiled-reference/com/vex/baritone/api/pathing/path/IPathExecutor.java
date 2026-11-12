package com.vex.baritone.api.pathing.path;

import com.vex.baritone.api.pathing.calc.IPath;

public interface IPathExecutor {
   IPath getPath();

   int getPosition();
}
