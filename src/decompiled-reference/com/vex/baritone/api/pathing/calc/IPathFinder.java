package com.vex.baritone.api.pathing.calc;

import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.utils.PathCalculationResult;
import java.util.Optional;

public interface IPathFinder {
   Goal getGoal();

   PathCalculationResult calculate(long var1, long var3);

   boolean isFinished();

   Optional<IPath> pathToMostRecentNodeConsidered();

   Optional<IPath> bestPathSoFar();
}
