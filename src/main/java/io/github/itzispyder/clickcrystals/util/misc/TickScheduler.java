package io.github.itzispyder.clickcrystals.util.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TickScheduler {
   private static final Set<TickScheduler> SCHEDULER_REGISTRY = new HashSet<>();
   private final Map<Long, List<Runnable>> schedule = new HashMap<>();
   private long currentTimeTicks;

   public static void tickAll() {
      List<TickScheduler> schedulers = new ArrayList<>(SCHEDULER_REGISTRY);
      schedulers.forEach(TickScheduler::tick);
   }

   public TickScheduler() {
      SCHEDULER_REGISTRY.add(this);
   }

   public int count() {
      return this.schedule.size();
   }

   public void destroy() {
      SCHEDULER_REGISTRY.remove(this);
   }

   private void tick() {
      this.currentTimeTicks++;
      List<Runnable> tasks = this.schedule.get(this.currentTimeTicks);
      if (tasks != null) {
         for (Runnable task : tasks) {
            task.run();
         }

         this.schedule.remove(this.currentTimeTicks);
      }
   }

   public long currentTimeTicks() {
      return this.currentTimeTicks;
   }

   public void schedule(int ticks, Runnable task) {
      if (ticks <= 0) {
         task.run();
      } else {
         List<Runnable> currentScheduled = this.schedule.getOrDefault(this.currentTimeTicks + ticks, new ArrayList<>());
         currentScheduled.add(task);
         this.schedule.put(this.currentTimeTicks + ticks, currentScheduled);
      }
   }

   public void schedule(double seconds, Runnable task) {
      if (seconds <= 0.0) {
         task.run();
      } else {
         int ticks = (int)(seconds * 20.0 + 0.5);
         this.schedule(ticks, task);
      }
   }
}
