package com.vex.baritone.api.pathing.calc;

import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.process.PathingCommand;
import java.util.Optional;

public interface IPathingControlManager {
   void registerProcess(IBaritoneProcess var1);

   Optional<IBaritoneProcess> mostRecentInControl();

   Optional<PathingCommand> mostRecentCommand();
}
