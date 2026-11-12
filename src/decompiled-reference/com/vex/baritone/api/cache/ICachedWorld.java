package com.vex.baritone.api.cache;

import java.util.ArrayList;
import net.minecraft.class_2338;
import net.minecraft.class_2818;

public interface ICachedWorld {
   ICachedRegion getRegion(int var1, int var2);

   void queueForPacking(class_2818 var1);

   boolean isCached(int var1, int var2);

   ArrayList<class_2338> getLocationsOf(String var1, int var2, int var3, int var4, int var5);

   void reloadAllFromDisk();

   void save();
}
