package com.vex.baritone.api.cache;

import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public interface IWaypoint {
   String getName();

   IWaypoint.Tag getTag();

   long getCreationTimestamp();

   BetterBlockPos getLocation();

   enum Tag {
      HOME("home", "base"),
      DEATH("death"),
      BED("bed", "spawn"),
      USER("user");

      private static final List<IWaypoint.Tag> TAG_LIST = Collections.unmodifiableList(Arrays.asList(values()));
      public final String[] names;

      Tag(String... var3) {
         this.names = var3;
      }

      public final String getName() {
         return this.names[0];
      }

      public static IWaypoint.Tag getByName(String var0) {
         IWaypoint.Tag[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            IWaypoint.Tag var4;
            String[] var5;
            int var6 = (var5 = (var4 = var1[var3]).names).length;

            for (int var7 = 0; var7 < var6; var7++) {
               if (var5[var7].equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] getAllNames() {
         HashSet var0 = new HashSet();
         IWaypoint.Tag[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            IWaypoint.Tag var4 = var1[var3];
            var0.addAll(Arrays.asList(var4.names));
         }

         return var0.toArray(new String[0]);
      }
   }
}
