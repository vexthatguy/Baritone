package com.vex.baritone.api.cache;

import com.vex.baritone.api.utils.BlockOptionalMetaLookup;
import com.vex.baritone.api.utils.IPlayerContext;
import java.util.List;
import net.minecraft.class_1923;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

public interface IWorldScanner {
   List<class_2338> scanChunkRadius(IPlayerContext var1, BlockOptionalMetaLookup var2, int var3, int var4, int var5);

   default List<class_2338> scanChunkRadius(IPlayerContext var1, List<class_2248> var2, int var3, int var4, int var5) {
      return this.scanChunkRadius(var1, new BlockOptionalMetaLookup(var2.toArray(new class_2248[0])), var3, var4, var5);
   }

   List<class_2338> scanChunk(IPlayerContext var1, BlockOptionalMetaLookup var2, class_1923 var3, int var4, int var5);

   default List<class_2338> scanChunk(IPlayerContext var1, List<class_2248> var2, class_1923 var3, int var4, int var5) {
      return this.scanChunk(var1, new BlockOptionalMetaLookup(var2), var3, var4, var5);
   }

   int repack(IPlayerContext var1);

   int repack(IPlayerContext var1, int var2);
}
