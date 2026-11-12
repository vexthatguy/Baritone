package io.github.itzispyder.clickcrystals.util.minecraft;

import com.mojang.authlib.GameProfile;
import io.github.itzispyder.clickcrystals.Global;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_636;
import net.minecraft.class_638;
import net.minecraft.class_640;
import net.minecraft.class_746;
import net.minecraft.class_9064;

public final class PlayerUtils implements Global {
   public static boolean invalid() {
      return mc == null || mc.field_1724 == null || mc.field_1687 == null || mc.field_1761 == null || mc.field_1690 == null;
   }

   public static boolean valid() {
      return !invalid();
   }

   public static boolean playerValid(class_1657 player) {
      if (!invalid() && player != null) {
         class_746 p = mc.field_1724;
         GameProfile profile = player.method_7334();
         class_640 entry = p.field_3944.method_2871(profile.id());
         return entry != null;
      } else {
         return false;
      }
   }

   public static class_746 player() {
      return mc.field_1724;
   }

   public static class_636 getInteractions() {
      return mc.field_1761;
   }

   public static class_1937 getWorld() {
      return player().method_73183();
   }

   public static class_638 getClientWorld() {
      return mc.field_1687;
   }

   public static class_243 getPos() {
      return player().method_73189();
   }

   public static class_243 getEyes() {
      return player().method_33571();
   }

   public static class_243 getDir() {
      return player().method_5720();
   }

   public static class_636 getInteractionManager() {
      return mc.field_1761;
   }

   public static float getEntityNameLabelHeight(class_1297 entity, float tickDelta) {
      float yaw = entity.method_5705(tickDelta);
      class_243 vec = entity.method_56072().method_55675(class_9064.field_47745, 0, yaw);
      return (float)(vec == null ? 0.5 : vec.field_1351 + 0.5);
   }

   public static void sendPacket(class_2596<?> packet) {
      if (!invalid()) {
         player().field_3944.method_52787(packet);
      }
   }

   public static long getPing() {
      if (invalid()) {
         return -1L;
      }

      GameProfile p = player().method_7334();
      class_640 entry = player().field_3944.method_2871(p.id());
      return entry == null ? -1L : entry.method_2959();
   }

   public static int getFps() {
      return mc.method_47599();
   }

   public static boolean isMoving() {
      if (invalid()) {
         return false;
      }

      class_746 p = player();
      return p.field_6212 != 0.0F || p.field_6250 != 0.0F;
   }

   public static boolean isColliding() {
      if (invalid()) {
         return false;
      }

      class_746 p = player();
      return p.field_5976 || p.field_5992;
   }

   public static boolean isCollidingHorizontally() {
      if (invalid()) {
         return false;
      }

      class_746 p = player();
      return p.field_5976;
   }

   public static boolean isCollidingVertically() {
      if (invalid()) {
         return false;
      }

      class_746 p = player();
      return p.field_5992;
   }

   public static void boxIterator(class_1937 world, class_238 box, BiConsumer<class_2338, class_2680> function) {
      for (double x = box.field_1323; x <= box.field_1320; x++) {
         for (double y = box.field_1322; y <= box.field_1325; y++) {
            for (double z = box.field_1321; z <= box.field_1324; z++) {
               class_2338 pos = new class_2338((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
               class_2680 state = world.method_8320(pos);
               if (state != null && !state.method_26215()) {
                  function.accept(pos, state);
               }
            }
         }
      }
   }

   public static class_1297 getNearestEntity(class_1937 world, class_1297 exclude, class_243 at, double range, Predicate<class_1297> filter) {
      List<class_1297> candidates = world.method_8333(exclude, class_238.method_29968(at).method_1014(range), filter)
         .stream()
         .sorted(Comparator.comparing(entity -> entity.method_73189().method_1022(at)))
         .toList();
      return candidates.isEmpty() ? null : candidates.get(0);
   }

   public static boolean hasEffects() {
      return valid() && !player().method_6026().isEmpty();
   }

   public static class_1297 getNearestEntity(double range, Predicate<class_1297> filter) {
      return invalid() ? null : getNearestEntity(getWorld(), player(), player().method_73189(), range, filter);
   }

   public static class_1657 getNearestPlayer(double range, Predicate<class_1297> filter) {
      return invalid()
         ? null
         : (class_1657)getNearestEntity(getWorld(), player(), player().method_73189(), range, entity -> entity instanceof class_1657 && filter.test(entity));
   }

   public static boolean runOnNearestBlock(double range, BiPredicate<class_2338, class_2680> filter, BiConsumer<class_2338, class_2680> function) {
      if (invalid()) {
         return false;
      } else {
         AtomicReference<Double> nearestDist = new AtomicReference<>(64.0);
         AtomicReference<class_2338> nearestPos = new AtomicReference<>();
         AtomicReference<class_2680> nearestState = new AtomicReference<>();
         class_238 box = player().method_5829().method_1014(range);
         class_243 player = player().method_73189();
         class_1937 world = getWorld();
         boxIterator(world, box, (pos, state) -> {
            if (filter.test(pos, state) && pos.method_19769(player, nearestDist.get())) {
               nearestDist.set(Math.sqrt(pos.method_19770(player)));
               nearestPos.set(pos);
               nearestState.set(state);
            }
         });
         if (nearestState.get() != null && nearestPos.get() != null) {
            function.accept(nearestPos.get(), nearestState.get());
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean runOnNearestBlock(double range, Predicate<class_2680> filter, BiConsumer<class_2338, class_2680> function) {
      return runOnNearestBlock(range, (pos, state) -> filter.test(state), function);
   }

   public static boolean runOnNearestEntity(double range, Predicate<class_1297> filter, Consumer<class_1297> function) {
      if (invalid()) {
         return false;
      } else {
         class_1297 ent = getNearestEntity(range, filter);
         if (ent != null) {
            function.accept(ent);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class_2338 getNearestBlock(double range, Predicate<class_2680> filter) {
      if (invalid()) {
         return null;
      }

      AtomicReference<Double> nearestDist = new AtomicReference<>(range);
      AtomicReference<class_2338> nearestPos = new AtomicReference<>();
      class_238 box = player().method_5829().method_1014(range);
      class_243 playerPos = player().method_73189();
      class_1937 world = getWorld();
      boxIterator(world, box, (pos, state) -> {
         if (filter.test(state) && pos.method_19769(playerPos, nearestDist.get())) {
            double distance = Math.sqrt(pos.method_19770(playerPos));
            if (distance < nearestDist.get()) {
               nearestDist.set(distance);
               nearestPos.set(pos);
            }
         }
      });
      return nearestPos.get();
   }
}
