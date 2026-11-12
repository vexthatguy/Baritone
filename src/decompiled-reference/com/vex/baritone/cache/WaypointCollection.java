package com.vex.baritone.cache;

import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.cache.Waypoint;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WaypointCollection implements IWaypointCollection {
   private final Path a;
   private final Map<IWaypoint.Tag, Set<IWaypoint>> a;

   WaypointCollection(Path var1) {
      this.a = var1;
      if (!Files.exists(var1)) {
         try {
            Files.createDirectories(var1);
         } catch (IOException var6) {
         }
      }

      System.out.println("Would save waypoints to " + var1);
      this.a = new HashMap<>();
      WaypointCollection var7 = this;
      IWaypoint.Tag[] var2;
      int var3 = (var2 = IWaypoint.Tag.values()).length;

      for (int var4 = 0; var4 < var3; var4++) {
         IWaypoint.Tag var5 = var2[var4];
         var7.a(var5);
      }
   }

   private synchronized void a(IWaypoint.Tag var1) {
      this.a.put(var1, new HashSet<>());
      Path var2;
      if (Files.exists(var2 = this.a.resolve(var1.name().toLowerCase() + ".mp4"))) {
         try (
            FileInputStream var20 = new FileInputStream(var2.toFile());
            BufferedInputStream var3 = new BufferedInputStream(var20);
            DataInputStream var4 = new DataInputStream(var3);
         ) {
            long var6;
            if ((var6 = var4.readLong()) != 121977993584L) {
               throw new IOException("Bad magic value " + var6);
            }

            long var8 = var4.readLong();

            while (var8-- > 0L) {
               String var5 = var4.readUTF();
               long var11 = var4.readLong();
               int var21 = var4.readInt();
               int var7 = var4.readInt();
               int var10 = var4.readInt();
               this.a.get(var1).add(new Waypoint(var5, var1, new BetterBlockPos(var21, var7, var10), var11));
            }
         } catch (IOException var19) {
         }
      }
   }

   private synchronized void b(IWaypoint.Tag var1) {
      Path var2 = this.a.resolve(var1.name().toLowerCase() + ".mp4");

      try (
         FileOutputStream var14 = new FileOutputStream(var2.toFile());
         BufferedOutputStream var3 = new BufferedOutputStream(var14);
         DataOutputStream var4 = new DataOutputStream(var3);
      ) {
         var4.writeLong(121977993584L);
         var4.writeLong(this.a.get(var1).size());

         for (IWaypoint var5 : this.a.get(var1)) {
            var4.writeUTF(var5.getName());
            var4.writeLong(var5.getCreationTimestamp());
            var4.writeInt(var5.getLocation().method_10263());
            var4.writeInt(var5.getLocation().method_10264());
            var4.writeInt(var5.getLocation().method_10260());
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      }
   }

   @Override
   public void addWaypoint(IWaypoint var1) {
      if (this.a.get(var1.getTag()).add(var1)) {
         this.b(var1.getTag());
      }
   }

   @Override
   public void removeWaypoint(IWaypoint var1) {
      if (this.a.get(var1.getTag()).remove(var1)) {
         this.b(var1.getTag());
      }
   }

   @Override
   public IWaypoint getMostRecentByTag(IWaypoint.Tag var1) {
      return this.a.get(var1).stream().min(Comparator.comparingLong(var0 -> -var0.getCreationTimestamp())).orElse(null);
   }

   @Override
   public Set<IWaypoint> getByTag(IWaypoint.Tag var1) {
      return Collections.unmodifiableSet(this.a.get(var1));
   }

   @Override
   public Set<IWaypoint> getAllWaypoints() {
      return this.a.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
   }
}
