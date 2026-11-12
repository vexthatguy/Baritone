package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.ForAxis;
import com.vex.baritone.api.command.datatypes.ForBlockOptionalMeta;
import com.vex.baritone.api.command.datatypes.ForDirection;
import com.vex.baritone.api.command.datatypes.RelativeBlockPos;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.command.exception.CommandInvalidTypeException;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.schematic.CompositeSchematic;
import com.vex.baritone.api.schematic.FillSchematic;
import com.vex.baritone.api.schematic.ISchematic;
import com.vex.baritone.api.schematic.MaskSchematic;
import com.vex.baritone.api.schematic.ReplaceSchematic;
import com.vex.baritone.api.schematic.ShellSchematic;
import com.vex.baritone.api.schematic.WallsSchematic;
import com.vex.baritone.api.schematic.mask.shape.CylinderMask;
import com.vex.baritone.api.schematic.mask.shape.SphereMask;
import com.vex.baritone.api.selection.ISelection;
import com.vex.baritone.api.selection.ISelectionManager;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.BlockOptionalMeta;
import com.vex.baritone.api.utils.BlockOptionalMetaLookup;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.schematic.StaticSchematic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.class_2246;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2680;
import net.minecraft.class_2350.class_2351;

public class SelCommand extends Command {
   private ISelectionManager a = this.com.vex.baritone.getSelectionManager();
   BetterBlockPos a = null;
   private CompositeSchematic a = null;
   private class_2382 a = null;

   public SelCommand(Baritone var1) {
      super(var1, "sel", "selection", "s");
      var1.getGameEventHandler().registerEventListener(new SelCommand$1(this));
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      SelCommand.Action var14;
      if ((var14 = SelCommand.Action.a(var2.getString())) == null) {
         throw new CommandInvalidTypeException(var2.consumed(), "an action");
      }

      if (var14 != SelCommand.Action.a && var14 != SelCommand.Action.b) {
         if (var14 == SelCommand.Action.c) {
            var2.requireMax(0);
            this.a = null;
            this.logDirect(String.format("Removed %d selections", this.a.removeAllSelections().length));
         } else if (var14 == SelCommand.Action.d) {
            var2.requireMax(0);
            if (this.a != null) {
               this.a = null;
               this.logDirect("Undid pos1");
            } else {
               ISelection[] var25;
               if ((var25 = this.a.getSelections()).length <= 0) {
                  throw new CommandInvalidStateException("Nothing to undo!");
               }

               this.a = this.a.removeSelection(var25[var25.length - 1]).pos1();
               this.logDirect("Undid pos2");
            }
         } else if (var14.a()) {
            BlockOptionalMeta var24 = var14 == SelCommand.Action.g
               ? new BlockOptionalMeta(class_2246.field_10124)
               : var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
            class_2351 var19;
            BlockOptionalMetaLookup var29;
            if (var14 == SelCommand.Action.h) {
               var2.requireMin(1);
               ArrayList var32;
               (var32 = new ArrayList()).add(var24);

               while (var2.has(2)) {
                  var32.add(var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
               }

               var24 = var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
               var29 = new BlockOptionalMetaLookup(var32.toArray(new BlockOptionalMeta[0]));
               var19 = null;
            } else if (var14 != SelCommand.Action.e && var14 != SelCommand.Action.f) {
               var2.requireMax(0);
               var29 = null;
               var19 = null;
            } else {
               var2.requireMax(1);
               var19 = var2.hasAny() ? var2.getDatatypeFor(ForAxis.INSTANCE) : class_2351.field_11052;
               var29 = null;
            }

            ISelection[] var33;
            if ((var33 = this.a.getSelections()).length == 0) {
               throw new CommandInvalidStateException("No selections");
            }

            BetterBlockPos var35 = var33[0].min();
            CompositeSchematic var37 = new CompositeSchematic(0, 0, 0);
            ISelection[] var40 = var33;
            int var44 = var33.length;

            for (int var47 = 0; var47 < var44; var47++) {
               BetterBlockPos var53 = var40[var47].min();
               var35 = new BetterBlockPos(Math.min(var35.x, var53.x), Math.min(var35.y, var53.y), Math.min(var35.z, var53.z));
            }

            var40 = var33;
            var44 = var33.length;

            for (int var48 = 0; var48 < var44; var48++) {
               ISelection var50;
               class_2382 var54 = (var50 = var40[var48]).size();
               BetterBlockPos var51 = var50.min();
               ISchematic var56 = (var3x -> {
                  int var4x = var3x.widthX();
                  int var5x = var3x.heightY();
                  int var6x = var3x.lengthZ();
                  switch (var14) {
                     case o:
                        return new WallsSchematic(var3x);
                     case p:
                        return new ShellSchematic(var3x);
                     case q:
                        return MaskSchematic.create(var3x, new SphereMask(var4x, var5x, var6x, true).compute());
                     case r:
                        return MaskSchematic.create(var3x, new SphereMask(var4x, var5x, var6x, false).compute());
                     case e:
                        return MaskSchematic.create(var3x, new CylinderMask(var4x, var5x, var6x, true, var19).compute());
                     case f:
                        return MaskSchematic.create(var3x, new CylinderMask(var4x, var5x, var6x, false, var19).compute());
                     case g:
                     default:
                        return var3x;
                     case h:
                        return new ReplaceSchematic(var3x, var29);
                  }
               }).apply(new FillSchematic(var54.method_10263(), var54.method_10264(), var54.method_10260(), var24));
               var37.put(var56, var51.x - var35.x, var51.y - var35.y, var51.z - var35.z);
            }

            super.com.vex.baritone.getBuilderProcess().build("Fill", var37, var35);
            this.logDirect("Filling now");
         } else if (var14 == SelCommand.Action.j) {
            BetterBlockPos var22 = super.ctx.viewerPos();
            BetterBlockPos var28 = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, var22) : var22;
            var2.requireMax(0);
            ISelection[] var17;
            if ((var17 = this.a.getSelections()).length <= 0) {
               throw new CommandInvalidStateException("No selections");
            }

            BlockStateInterface var31 = new BlockStateInterface(super.ctx);
            BetterBlockPos var34 = var17[0].min();
            CompositeSchematic var36 = new CompositeSchematic(0, 0, 0);
            ISelection[] var38 = var17;
            int var42 = var17.length;

            for (int var10 = 0; var10 < var42; var10++) {
               BetterBlockPos var12 = var38[var10].min();
               var34 = new BetterBlockPos(Math.min(var34.x, var12.x), Math.min(var34.y, var12.y), Math.min(var34.z, var12.z));
            }

            var38 = var17;
            var42 = var17.length;

            for (int var46 = 0; var46 < var42; var46++) {
               ISelection var11;
               class_2382 var52 = (var11 = var38[var46]).size();
               BetterBlockPos var49 = var11.min();
               class_2680[][][] var23 = new class_2680[var52.method_10263()][var52.method_10260()][var52.method_10264()];

               for (int var13 = 0; var13 < var52.method_10263(); var13++) {
                  for (int var15 = 0; var15 < var52.method_10264(); var15++) {
                     for (int var18 = 0; var18 < var52.method_10260(); var18++) {
                        var23[var13][var18][var15] = var31.a(var49.x + var13, var49.y + var15, var49.z + var18);
                     }
                  }
               }

               StaticSchematic var55 = new StaticSchematic(var23);
               var36.put(var55, var49.x - var34.x, var49.y - var34.y, var49.z - var34.z);
            }

            this.a = var36;
            this.a = var34.method_10059(var28);
            this.logDirect("Selection copied");
         } else if (var14 == SelCommand.Action.k) {
            BetterBlockPos var21 = super.ctx.viewerPos();
            BetterBlockPos var27 = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, var21) : var21;
            var2.requireMax(0);
            if (this.a == null) {
               throw new CommandInvalidStateException("You need to copy a selection first");
            }

            super.com.vex.baritone.getBuilderProcess().build("Fill", this.a, var27.method_10081(this.a));
            this.logDirect("Building now");
         } else {
            if (var14 == SelCommand.Action.i || var14 == SelCommand.Action.l || var14 == SelCommand.Action.m) {
               var2.requireExactly(3);
               SelCommand.TransformTarget var20;
               if ((var20 = SelCommand.TransformTarget.a(var2.getString())) == null) {
                  throw new CommandInvalidStateException("Invalid transform type");
               }

               class_2350 var26 = var2.getDatatypeFor(ForDirection.INSTANCE);
               int var16 = var2.getAs(Integer.class);
               ISelection[] var5;
               if ((var5 = this.a.getSelections()).length <= 0) {
                  throw new CommandInvalidStateException("No selections found");
               }

               ISelection[] var6;
               ISelection[] var30;
               int var7 = (var6 = var30 = var20.a.apply(var5)).length;

               for (int var8 = 0; var8 < var7; var8++) {
                  ISelection var9 = var6[var8];
                  if (var14 == SelCommand.Action.i) {
                     this.a.expand(var9, var26, var16);
                  } else if (var14 == SelCommand.Action.l) {
                     this.a.contract(var9, var26, var16);
                  } else {
                     this.a.shift(var9, var26, var16);
                  }
               }

               this.logDirect(String.format("Transformed %d selections", var30.length));
            }
         }
      } else {
         if (var14 == SelCommand.Action.b && this.a == null) {
            throw new CommandInvalidStateException("Set pos1 first before using pos2");
         }

         BetterBlockPos var3 = super.ctx.viewerPos();
         BetterBlockPos var4 = var2.hasAny() ? var2.getDatatypePost(RelativeBlockPos.INSTANCE, var3) : var3;
         var2.requireMax(0);
         if (var14 == SelCommand.Action.a) {
            this.a = var4;
            this.logDirect("Position 1 has been set");
         } else {
            this.a.addSelection(this.a, var4);
            this.a = null;
            this.logDirect("Selection added");
         }
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasExactlyOne()) {
         return new TabCompleteHelper().append(SelCommand.Action.a()).filterPrefix(var2.getString()).sortAlphabetically().stream();
      }

      SelCommand.Action var3;
      if ((var3 = SelCommand.Action.a(var2.getString())) != null) {
         if (var3 != SelCommand.Action.a && var3 != SelCommand.Action.b) {
            if (var3.a()) {
               if (var2.hasExactlyOne() || var3 == SelCommand.Action.h) {
                  while (var2.has(2)) {
                     var2.get();
                  }

                  return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
               }

               if (var2.hasExactly(2) && (var3 == SelCommand.Action.e || var3 == SelCommand.Action.f)) {
                  var2.get();
                  return var2.tabCompleteDatatype(ForAxis.INSTANCE);
               }
            } else if (var3 == SelCommand.Action.i || var3 == SelCommand.Action.l || var3 == SelCommand.Action.m) {
               if (var2.hasExactlyOne()) {
                  return new TabCompleteHelper().append(SelCommand.TransformTarget.a()).filterPrefix(var2.getString()).sortAlphabetically().stream();
               }

               if (SelCommand.TransformTarget.a(var2.getString()) != null && var2.hasExactlyOne()) {
                  return var2.tabCompleteDatatype(ForDirection.INSTANCE);
               }
            }
         } else if (var2.hasAtMost(3)) {
            return var2.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
         }
      }

      return Stream.empty();
   }

   @Override
   public String getShortDesc() {
      return "WorldEdit-like commands";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The sel command allows you to manipulate Baritone's selections, similarly to WorldEdit.",
         "",
         "Using these selections, you can clear areas, fill them with blocks, or something else.",
         "",
         "The expand/contract/shift commands use a kind of selector to choose which selections to target. Supported ones are a/all, n/newest, and o/oldest.",
         "",
         "Usage:",
         "> sel pos1/p1/1 - Set position 1 to your current position.",
         "> sel pos1/p1/1 <x> <y> <z> - Set position 1 to a relative position.",
         "> sel pos2/p2/2 - Set position 2 to your current position.",
         "> sel pos2/p2/2 <x> <y> <z> - Set position 2 to a relative position.",
         "",
         "> sel clear/c - Clear the selection.",
         "> sel undo/u - Undo the last action (setting positions, creating selections, etc.)",
         "> sel set/fill/s/f [block] - Completely fill all selections with a block.",
         "> sel walls/w [block] - Fill in the walls of the selection with a specified block.",
         "> sel shell/shl [block] - The same as walls, but fills in a ceiling and floor too.",
         "> sel sphere/sph [block] - Fills the selection with a sphere bounded by the sides.",
         "> sel hsphere/hsph [block] - The same as sphere, but hollow.",
         "> sel cylinder/cyl [block] <axis> - Fills the selection with a cylinder bounded by the sides, oriented about the given axis. (default=y)",
         "> sel hcylinder/hcyl [block] <axis> - The same as cylinder, but hollow.",
         "> sel cleararea/ca - Basically 'set air'.",
         "> sel replace/r <blocks...> <with> - Replaces blocks with another block.",
         "> sel copy/cp <x> <y> <z> - Copy the selected area relative to the specified or your position.",
         "> sel paste/p <x> <y> <z> - Build the copied area relative to the specified or your position.",
         "",
         "> sel expand <target> <direction> <blocks> - Expand the targets.",
         "> sel contract <target> <direction> <blocks> - Contract the targets.",
         "> sel shift <target> <direction> <blocks> - Shift the targets (does not resize)."
      );
   }

   enum Action {
      a("pos1", "p1", "1"),
      b("pos2", "p2", "2"),
      c("clear", "c"),
      d("undo", "u"),
      n("set", "fill", "s", "f"),
      o("walls", "w"),
      p("shell", "shl"),
      q("sphere", "sph"),
      r("hsphere", "hsph"),
      e("cylinder", "cyl"),
      f("hcylinder", "hcyl"),
      g("cleararea", "ca"),
      h("replace", "r"),
      i("expand", "ex"),
      j("copy", "cp"),
      k("paste", "p"),
      l("contract", "ct"),
      m("shift", "sh");

      private final String[] a;

      Action(String... var3) {
         this.a = var3;
      }

      public static SelCommand.Action a(String var0) {
         SelCommand.Action[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            SelCommand.Action var4;
            String[] var5;
            int var6 = (var5 = (var4 = var1[var3]).a).length;

            for (int var7 = 0; var7 < var6; var7++) {
               if (var5[var7].equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] a() {
         HashSet var0 = new HashSet();
         SelCommand.Action[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            SelCommand.Action var4 = var1[var3];
            var0.addAll(Arrays.asList(var4.a));
         }

         return var0.toArray(new String[0]);
      }

      public final boolean a() {
         return this == n || this == o || this == p || this == q || this == r || this == e || this == f || this == g || this == h;
      }
   }

   enum TransformTarget {
      a(var0 -> var0, "all", "a"),
      b(var0 -> new ISelection[]{var0[var0.length - 1]}, "newest", "n"),
      c(var0 -> new ISelection[]{var0[0]}, "oldest", "o");

      final Function<ISelection[], ISelection[]> a;
      private final String[] a;

      TransformTarget(Function<ISelection[], ISelection[]> var3, String... var4) {
         this.a = var3;
         this.a = var4;
      }

      public static SelCommand.TransformTarget a(String var0) {
         SelCommand.TransformTarget[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            SelCommand.TransformTarget var4;
            String[] var5;
            int var6 = (var5 = (var4 = var1[var3]).a).length;

            for (int var7 = 0; var7 < var6; var7++) {
               if (var5[var7].equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] a() {
         HashSet var0 = new HashSet();
         SelCommand.TransformTarget[] var1;
         int var2 = (var1 = values()).length;

         for (int var3 = 0; var3 < var2; var3++) {
            SelCommand.TransformTarget var4 = var1[var3];
            var0.addAll(Arrays.asList(var4.a));
         }

         return var0.toArray(new String[0]);
      }
   }
}
