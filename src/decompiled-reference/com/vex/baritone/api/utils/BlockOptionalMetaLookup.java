package com.vex.baritone.api.utils;

import com.vex.baritone.api.utils.accessor.IItemStack;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_1799;
import net.minecraft.class_2248;
import net.minecraft.class_2680;

public class BlockOptionalMetaLookup {
   private final ImmutableSet<class_2248> blockSet;
   private final ImmutableSet<class_2680> blockStateSet;
   private final ImmutableSet<Integer> stackHashes;
   private final BlockOptionalMeta[] boms;

   public BlockOptionalMetaLookup(BlockOptionalMeta... var1) {
      this.boms = var1;
      HashSet var2 = new HashSet();
      HashSet var3 = new HashSet();
      HashSet var4 = new HashSet();

      for (BlockOptionalMeta var7 : var1) {
         var2.add(var7.getBlock());
         var3.addAll(var7.getAllBlockStates());
         var4.addAll(var7.stackHashes());
      }

      this.blockSet = ImmutableSet.copyOf(var2);
      this.blockStateSet = ImmutableSet.copyOf(var3);
      this.stackHashes = ImmutableSet.copyOf(var4);
   }

   public BlockOptionalMetaLookup(class_2248... var1) {
      this(Stream.of(var1).map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
   }

   public BlockOptionalMetaLookup(List<class_2248> var1) {
      this(var1.stream().map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
   }

   public BlockOptionalMetaLookup(String... var1) {
      this(Stream.of(var1).map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
   }

   public boolean has(class_2248 var1) {
      return this.blockSet.contains(var1);
   }

   public boolean has(class_2680 var1) {
      return this.blockStateSet.contains(var1);
   }

   public boolean has(class_1799 var1) {
      int var2 = ((IItemStack)var1).getBaritoneHash() - var1.method_7919();
      return this.stackHashes.contains(var2);
   }

   public List<BlockOptionalMeta> blocks() {
      return Arrays.asList(this.boms);
   }

   @Override
   public String toString() {
      return String.format("BlockOptionalMetaLookup{%s}", Arrays.toString(this.boms));
   }
}
