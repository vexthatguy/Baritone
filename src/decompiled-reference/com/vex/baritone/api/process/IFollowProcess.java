package com.vex.baritone.api.process;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1799;

public interface IFollowProcess extends IBaritoneProcess {
   void follow(Predicate<class_1297> var1);

   void pickup(Predicate<class_1799> var1);

   List<class_1297> following();

   Predicate<class_1297> currentFilter();

   default void cancel() {
      this.onLostControl();
   }
}
