package com.vex.baritone.api.utils;

import com.vex.baritone.api.utils.accessor.IItemStack;
import com.vex.baritone.api.utils.accessor.ILootTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap.Builder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.class_173;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_181;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2688;
import net.minecraft.class_2769;
import net.minecraft.class_310;
import net.minecraft.class_3218;
import net.minecraft.class_3264;
import net.minecraft.class_3268;
import net.minecraft.class_3286;
import net.minecraft.class_3503;
import net.minecraft.class_5268;
import net.minecraft.class_5304;
import net.minecraft.class_5321;
import net.minecraft.class_5363;
import net.minecraft.class_5455;
import net.minecraft.class_6861;
import net.minecraft.class_7655;
import net.minecraft.class_7659;
import net.minecraft.class_7699;
import net.minecraft.class_7780;
import net.minecraft.class_8565;
import net.minecraft.class_8567;
import net.minecraft.class_9383;
import net.minecraft.class_32.class_5143;
import net.minecraft.class_47.class_48;
import net.minecraft.class_5455.class_6890;
import net.minecraft.class_8567.class_8568;
import net.minecraft.class_9383.class_9385;
import net.minecraft.server.MinecraftServer;
import sun.misc.Unsafe;

public final class BlockOptionalMeta {
   private static final Pattern PATTERN = Pattern.compile("^(?<id>.+?)(?:\\[(?<properties>.+?)?\\])?$");
   private final class_2248 block;
   private final String propertiesDescription;
   private final Set<class_2680> blockstates;
   private final ImmutableSet<Integer> stateHashes;
   private final ImmutableSet<Integer> stackHashes;
   private static Map<class_2248, List<class_1792>> drops = new HashMap<>();
   private static Method getVanillaServerPack;

   public BlockOptionalMeta(@Nonnull class_2248 var1) {
      this.block = var1;
      this.propertiesDescription = "{}";
      this.blockstates = getStates(var1, Collections.emptyMap());
      this.stateHashes = getStateHashes(this.blockstates);
      this.stackHashes = getStackHashes(this.blockstates);
   }

   public BlockOptionalMeta(@Nonnull String var1) {
      Matcher var3;
      if (!(var3 = PATTERN.matcher(var1)).find()) {
         throw new IllegalArgumentException("invalid block selector");
      }

      this.block = BlockUtils.stringToBlockRequired(var3.group("id"));
      Map var2 = (var1 = var3.group("properties")) != null && !var1.equals("") ? parseProperties(this.block, var1) : Collections.emptyMap();
      this.propertiesDescription = var1 == null ? "{}" : "{" + var1.replace("=", ":") + "}";
      this.blockstates = getStates(this.block, var2);
      this.stateHashes = getStateHashes(this.blockstates);
      this.stackHashes = getStackHashes(this.blockstates);
   }

   private static <C extends Comparable<C>, P extends class_2769<C>> P castToIProperty(Object var0) {
      return (P)var0;
   }

   private static Map<class_2769<?>, ?> parseProperties(class_2248 var0, String var1) {
      Builder var2 = ImmutableMap.builder();
      String[] var7;
      int var3 = (var7 = var1.split(",")).length;

      for (int var4 = 0; var4 < var3; var4++) {
         String var5;
         String[] var6;
         if ((var6 = (var5 = var7[var4]).split("=")).length != 2) {
            throw new IllegalArgumentException(String.format("\"%s\" is not a valid property-value pair", var5));
         }

         var5 = var6[0];
         String var10 = var6[1];
         class_2769 var9;
         Comparable var11 = (Comparable)castToIProperty(var9 = var0.method_9595().method_11663(var5))
            .method_11900(var10)
            .orElseThrow(() -> new IllegalArgumentException(String.format("\"%s\" is not a valid value for %s on %s", var10, var9, var0)));
         var2.put(var9, var11);
      }

      return var2.build();
   }

   private static Set<class_2680> getStates(@Nonnull class_2248 var0, @Nonnull Map<class_2769<?>, ?> var1) {
      return var0.method_9595()
         .method_11662()
         .stream()
         .filter(var1x -> var1.entrySet().stream().allMatch(var1xx -> var1x.method_11654((class_2769)var1xx.getKey()) == var1xx.getValue()))
         .collect(Collectors.toSet());
   }

   private static ImmutableSet<Integer> getStateHashes(Set<class_2680> var0) {
      return ImmutableSet.copyOf(var0.stream().map(class_2688::hashCode).toArray(Integer[]::new));
   }

   private static ImmutableSet<Integer> getStackHashes(Set<class_2680> var0) {
      return ImmutableSet.copyOf(
         var0.stream()
            .flatMap(var0x -> drops(var0x.method_26204()).stream().map(var0xx -> new class_1799(var0xx, 1)))
            .map(var0x -> ((IItemStack)var0x).getBaritoneHash())
            .toArray(Integer[]::new)
      );
   }

   public final class_2248 getBlock() {
      return this.block;
   }

   public final boolean matches(@Nonnull class_2248 var1) {
      return var1 == this.block;
   }

   public final boolean matches(@Nonnull class_2680 var1) {
      return var1.method_26204() == this.block && this.stateHashes.contains(var1.hashCode());
   }

   public final boolean matches(class_1799 var1) {
      int var2 = ((IItemStack)var1).getBaritoneHash() - var1.method_7919();
      return this.stackHashes.contains(var2);
   }

   @Override
   public final String toString() {
      return String.format("BlockOptionalMeta{block=%s,properties=%s}", this.block, this.propertiesDescription);
   }

   public final class_2680 getAnyBlockState() {
      return this.blockstates.size() > 0 ? this.blockstates.iterator().next() : null;
   }

   public final Set<class_2680> getAllBlockStates() {
      return this.blockstates;
   }

   public final Set<Integer> stackHashes() {
      return this.stackHashes;
   }

   private static class_3268 getVanillaServerPack() {
      if (getVanillaServerPack == null) {
         (getVanillaServerPack = Arrays.stream(class_3286.class.getDeclaredMethods())
               .filter(var0x -> var0x.getReturnType() == class_3268.class)
               .findFirst()
               .orElseThrow())
            .setAccessible(true);
      }

      try {
         return (class_3268)getVanillaServerPack.invoke(null);
      } catch (Exception var0) {
         var0.printStackTrace();
         return null;
      }
   }

   private static synchronized List<class_1792> drops(class_2248 var0) {
      return drops.computeIfAbsent(
         var0,
         var1 -> {
            if (var1.method_26162().isEmpty()) {
               return Collections.emptyList();
            }

            ArrayList var2 = new ArrayList();

            try {
               BlockOptionalMeta.ServerLevelStub var3 = BlockOptionalMeta.ServerLevelStub.fastCreate();
               class_8568 var5 = new class_8568(var3)
                  .method_51874(class_181.field_24424, class_243.field_1353)
                  .method_51874(class_181.field_1224, var0.method_9564())
                  .method_51874(class_181.field_1229, new class_1799(class_1802.field_22024, 1));
               getDrops(var1, var5).stream().map(class_1799::method_7909).forEach(var2::add);
            } catch (Exception var4) {
               var4.printStackTrace();
            }

            return var2;
         }
      );
   }

   private static List<class_1799> getDrops(class_2248 var0, class_8568 var1) {
      Optional var2;
      class_8567 var3;
      return (List<class_1799>)((var2 = var0.method_26162()).isEmpty()
         ? Collections.emptyList()
         : ((ILootTable)((BlockOptionalMeta.ServerLevelStub)(var3 = var1.method_51874(class_181.field_1224, var0.method_9564())
                     .method_51875(class_173.field_1172))
                  .method_51863())
               .holder()
               .method_58295((class_5321)var2.get()))
            .invokeGetRandomItems(new class_48(var3).method_304(1L).method_309(null)));
   }

   public static class ServerLevelStub extends class_3218 {
      private static class_310 client = class_310.method_1551();
      private static Unsafe unsafe = getUnsafe();
      private static CompletableFuture<class_5455> registryAccess = load();

      public ServerLevelStub(
         MinecraftServer var1,
         Executor var2,
         class_5143 var3,
         class_5268 var4,
         class_5321<class_1937> var5,
         class_5363 var6,
         boolean var7,
         long var8,
         List<class_5304> var10,
         boolean var11,
         @Nullable class_8565 var12
      ) {
         super(var1, var2, var3, var4, var5, var6, var7, var8, var10, var11, var12);
      }

      public class_7699 method_45162() {
         assert client.field_1687 != null;
         return client.field_1687.method_45162();
      }

      public static BlockOptionalMeta.ServerLevelStub fastCreate() {
         try {
            return (BlockOptionalMeta.ServerLevelStub)unsafe.allocateInstance(BlockOptionalMeta.ServerLevelStub.class);
         } catch (InstantiationException var1) {
            throw new RuntimeException(var1);
         }
      }

      public class_5455 method_30349() {
         return registryAccess.join();
      }

      public class_9385 holder() {
         return new class_9385(this.method_30349().method_40316());
      }

      public static Unsafe getUnsafe() {
         try {
            Field var0;
            (var0 = Unsafe.class.getDeclaredField("theUnsafe")).setAccessible(true);
            return (Unsafe)var0.get(null);
         } catch (Exception var1) {
            throw new RuntimeException(var1);
         }
      }

      public static CompletableFuture<class_5455> load() {
         class_6861 var0 = new class_6861(class_3264.field_14190, List.of(class_3286.method_45287()));
         class_7780 var1 = class_7659.method_45139();
         List var2 = class_3503.method_61307(var0, var1.method_45928(class_7659.field_39971));
         List var3 = class_3503.method_61313(var1.method_45935(class_7659.field_39972), var2);
         return class_9383.method_58284(
               var1.method_45930(class_7659.field_39972, new class_6890[]{class_7655.method_56515(var0, var3, class_7655.field_39968)}),
               var2,
               var0,
               ForkJoinPool.commonPool()
            )
            .thenApply(var0x -> var0x.comp_2898().method_45926());
      }
   }
}
