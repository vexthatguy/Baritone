package com.vex.baritone.cache;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.cache.ICachedWorld;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.cache.IWorldData;
import java.nio.file.Path;
import net.minecraft.class_1937;
import net.minecraft.class_2874;
import net.minecraft.class_5321;

public class WorldData implements IWorldData {
   public final CachedWorld a;
   private final WaypointCollection a;
   private Path a;
   private class_2874 a;

   WorldData(Path var1, class_2874 var2, class_5321<class_1937> var3) {
      this.a = var1;
      this.a = new CachedWorld(var1.resolve("cache"), var2, var3);
      this.a = new WaypointCollection(var1.resolve("waypoints"));
      this.a = var2;
   }

   public final void a() {
      Baritone.a().execute(() -> {
         System.out.println("Started saving the world in a new thread");
         this.a.save();
      });
   }

   @Override
   public ICachedWorld getCachedWorld() {
      return this.a;
   }

   @Override
   public IWaypointCollection getWaypoints() {
      return this.a;
   }
}
