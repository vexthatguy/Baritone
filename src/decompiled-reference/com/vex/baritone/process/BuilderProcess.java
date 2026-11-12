package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalComposite;
import com.vex.baritone.api.pathing.goals.GoalGetToBlock;
import com.vex.baritone.api.process.IBuilderProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.schematic.FillSchematic;
import com.vex.baritone.api.schematic.ISchematic;
import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.api.schematic.MirroredSchematic;
import com.vex.baritone.api.schematic.RotatedSchematic;
import com.vex.baritone.api.schematic.SubstituteSchematic;
import com.vex.baritone.api.schematic.format.ISchematicFormat;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.RayTraceUtils;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.Movement;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BaritoneProcessHelper;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.PathingCommandContext;
import com.vex.baritone.utils.schematic.MapArtSchematic;
import com.vex.baritone.utils.schematic.SchematicSystem;
import com.vex.baritone.utils.schematic.SelectionSchematic;
import com.vex.baritone.utils.schematic.litematica.LitematicaHelper;
import com.vex.baritone.utils.schematic.schematica.SchematicaHelper;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2383;
import net.minecraft.class_239;
import net.minecraft.class_2404;
import net.minecraft.class_2415;
import net.minecraft.class_2429;
import net.minecraft.class_243;
import net.minecraft.class_2465;
import net.minecraft.class_2470;
import net.minecraft.class_2510;
import net.minecraft.class_2533;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_3545;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

public final class BuilderProcess extends BaritoneProcessHelper implements IBuilderProcess {
   private static final Set<class_2769<?>> a = ImmutableSet.of(
      class_2465.field_11459,
      class_2383.field_11177,
      class_2510.field_11571,
      class_2510.field_11572,
      class_2510.field_11565,
      class_2429.field_11332,
      new class_2769[]{
         class_2429.field_11335, class_2429.field_11331, class_2429.field_11328, class_2429.field_11327, class_2533.field_11631, class_2533.field_11625
      }
   );
   private HashSet<BetterBlockPos> a;
   private LongOpenHashSet a;
   private String a;
   private ISchematic b;
   public ISchematic a;
   public class_2382 a;
   private int a;
   private boolean a;
   private int b;
   private int c;
   public List<class_2680> a;
   private int d = 0;

   public BuilderProcess(Baritone var1) {
      super(var1);
   }

   @Override
   public final void build(String var1, ISchematic var2, class_2382 var3) {
      this.a = var1;
      this.a = var2;
      this.b = null;
      boolean var6 = var2 instanceof SelectionSchematic;
      if (!Baritone.a().buildSubstitutes.value.isEmpty()) {
         this.a = new SubstituteSchematic(this.a, Baritone.a().buildSubstitutes.value);
      }

      if (Baritone.a().buildSchematicMirror.value != class_2415.field_11302) {
         this.a = new MirroredSchematic(this.a, Baritone.a().buildSchematicMirror.value);
      }

      if (Baritone.a().buildSchematicRotation.value != class_2470.field_11467) {
         this.a = new RotatedSchematic(this.a, Baritone.a().buildSchematicRotation.value);
      }

      this.a = new BuilderProcess$1(this.a);
      int var4 = var3.method_10263();
      int var5 = var3.method_10264();
      int var8 = var3.method_10260();
      if (Baritone.a().schematicOrientationX.value) {
         var4 += var2.widthX();
      }

      if (Baritone.a().schematicOrientationY.value) {
         var5 += var2.heightY();
      }

      if (Baritone.a().schematicOrientationZ.value) {
         var8 += var2.lengthZ();
      }

      this.a = new class_2382(var4, var5, var8);
      this.a = false;
      this.b = Baritone.a().startAtLayer.value;
      this.d = var2.heightY();
      if (Baritone.a().buildOnlySelection.value && var6) {
         if (super.a.a.getSelections().length == 0) {
            this.logDirect("Poor little kitten forgot to set a selection while BuildOnlySelection is true");
            this.d = 0;
         } else if (Baritone.a().buildInLayers.value) {
            OptionalInt var7 = Stream.of(super.a.a.getSelections()).mapToInt(var0 -> var0.min().y).min();
            OptionalInt var9 = Stream.of(super.a.a.getSelections()).mapToInt(var0 -> var0.max().y).max();
            if (var7.isPresent() && var9.isPresent()) {
               var4 = Baritone.a().layerOrder.value ? var5 + var2.heightY() - var9.getAsInt() : var7.getAsInt() - var5;
               this.d = (Baritone.a().layerOrder.value ? var5 + var2.heightY() - var7.getAsInt() : var9.getAsInt() - var5) + 1;
               this.b = Math.max(this.b, var4 / Baritone.a().layerHeight.value);
               this.logDebug(String.format("Schematic starts at y=%s with height %s", var5, var2.heightY()));
               this.logDebug(String.format("Selection starts at y=%s and ends at y=%s", var7.getAsInt(), var9.getAsInt()));
               this.logDebug(String.format("Considering relevant height %s - %s", var4, this.d));
            }
         }
      }

      this.c = 0;
      this.a = new LongOpenHashSet();
      this.a = null;
   }

   @Override
   public final void resume() {
      this.a = false;
   }

   @Override
   public final void pause() {
      this.a = true;
   }

   @Override
   public final boolean isPaused() {
      return this.a;
   }

   @Override
   public final boolean build(String var1, File var2, class_2382 var3) {
      Optional var4;
      if (!(var4 = SchematicSystem.a.getByFile(var2)).isPresent()) {
         return false;
      }

      try {
         var6 = ((ISchematicFormat)var4.get()).parse(new FileInputStream(var2));
      } catch (Exception var5) {
         var5.printStackTrace();
         return false;
      }

      ISchematic var7 = this.a(var3, var6);
      this.build(var1, var7, var3);
      return true;
   }

   private ISchematic a(class_2382 var1, IStaticSchematic var2) {
      ISchematic var3 = var2;
      if (Baritone.a().mapArtMode.value) {
         var3 = new MapArtSchematic(var2);
      }

      if (Baritone.a().buildOnlySelection.value) {
         var3 = new SelectionSchematic(var3, var1, super.a.a.getSelections());
      }

      return var3;
   }

   @Override
   public final void buildOpenSchematic() {
      if (SchematicaHelper.a()) {
         Optional var1;
         if ((var1 = SchematicaHelper.a()).isPresent()) {
            IStaticSchematic var2 = (IStaticSchematic)((class_3545)var1.get()).method_15442();
            class_2338 var4 = (class_2338)((class_3545)var1.get()).method_15441();
            ISchematic var3 = this.a(var4, var2);
            this.build(var2.toString(), var3, var4);
         } else {
            this.logDirect("No schematic currently open");
         }
      } else {
         this.logDirect("Schematica is not present");
      }
   }

   @Override
   public final void buildOpenLitematic(int var1) {
      if (LitematicaHelper.a()) {
         if (LitematicaHelper.a(var1)) {
            class_3545 var4;
            class_2382 var2 = (class_2382)(var4 = LitematicaHelper.a(var1)).method_15441();
            ISchematic var3 = this.a(var2, (IStaticSchematic)var4.method_15442());
            this.build(((IStaticSchematic)var4.method_15442()).toString(), var3, var2);
         } else {
            this.logDirect(String.format("List of placements has no entry %s", var1 + 1));
         }
      } else {
         this.logDirect("Litematica is not present");
      }
   }

   @Override
   public final void clearArea(class_2338 var1, class_2338 var2) {
      class_2338 var3 = new class_2338(
         Math.min(var1.method_10263(), var2.method_10263()),
         Math.min(var1.method_10264(), var2.method_10264()),
         Math.min(var1.method_10260(), var2.method_10260())
      );
      int var4 = Math.abs(var1.method_10263() - var2.method_10263()) + 1;
      int var5 = Math.abs(var1.method_10264() - var2.method_10264()) + 1;
      int var6 = Math.abs(var1.method_10260() - var2.method_10260()) + 1;
      this.build("clear area", new FillSchematic(var4, var5, var6, class_2246.field_10124.method_9564()), var3);
   }

   @Override
   public final List<class_2680> getApproxPlaceable() {
      return new ArrayList<>(this.a);
   }

   @Override
   public final boolean isActive() {
      return this.a != null;
   }

   public final boolean a(class_2338 var1, class_2680 var2) {
      class_265 var3;
      return (var3 = var2.method_26220(super.a.world(), var1)).method_1110()
         || super.a.world().method_8611(null, var3.method_1096(var1.method_10263(), var1.method_10264(), var1.method_10260()));
   }

   @Override
   public final PathingCommand onTick(boolean var1, boolean var2) {
      int var3 = 0;
      var2 = var2;
      BuilderProcess var44 = this;

      while (var3 <= 100) {
         var44.a = var44.a(36);
         if (var44.a.a.isInputForcedDown(Input.CLICK_LEFT)) {
            var44.a = 5;
         } else {
            var44.a--;
         }

         var44.a.a.clearAllKeys();
         if (var44.a) {
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
         }

         if (Baritone.a().buildInLayers.value) {
            if (var44.b == null) {
               var44.b = var44.a;
            }

            ISchematic var4 = var44.b;
            int var5;
            int var6;
            if (Baritone.a().layerOrder.value) {
               var6 = var4.heightY() - 1;
               var5 = var4.heightY() - var44.b * Baritone.a().layerHeight.value;
            } else {
               var6 = var44.b * Baritone.a().layerHeight.value - 1;
               var5 = 0;
            }

            var44.a = new BuilderProcess$3(var44, var4, var5, var6);
         }

         BuilderProcess.BuilderCalculationContext var46;
         boolean var10000;
         label398: {
            label410: {
               var46 = var44.new BuilderCalculationContext();
               BuilderProcess.BuilderCalculationContext var7 = var46;
               BuilderProcess var47 = var44;
               if (var44.a == null) {
                  var47.a = new HashSet<>();
                  var47.a(var7);
                  if (var47.a.isEmpty()) {
                     break label410;
                  }
               }

               BuilderProcess.BuilderCalculationContext var9 = var7;
               BuilderProcess var8 = var47;
               BetterBlockPos var10 = var47.a.playerFeet();

               int var11;
               for (int var12 = -(var11 = Baritone.a().builderTickScanRadius.value); var12 <= var11; var12++) {
                  for (int var13 = -var11; var13 <= var11; var13++) {
                     for (int var14 = -var11; var14 <= var11; var14++) {
                        int var15 = var10.x + var12;
                        int var16 = var10.y + var13;
                        int var17 = var10.z + var14;
                        class_2680 var18;
                        if ((var18 = var9.a(var15, var16, var17, var9.a.a(var15, var16, var17))) != null) {
                           BetterBlockPos var19 = new BetterBlockPos(var15, var16, var17);
                           if (a(var9.a.a(var15, var16, var17), var18, false)) {
                              var8.a.remove(var19);
                              var8.a.add(BetterBlockPos.longHash(var19));
                           } else {
                              var8.a.add(var19);
                              var8.a.remove(BetterBlockPos.longHash(var19));
                           }
                        }
                     }
                  }
               }

               if (var47.a.isEmpty()) {
                  var47.a(var7);
               }

               if (!var47.a.isEmpty()) {
                  var10000 = true;
                  break label398;
               }
            }

            var10000 = false;
         }

         if (!var10000) {
            if (Baritone.a().buildInLayers.value && var44.b * Baritone.a().layerHeight.value < var44.d) {
               var44.logDirect("Starting layer " + var44.b);
               var44.b++;
               var3++;
               var2 = var2;
               var44 = var44;
            } else {
               class_2382 var55 = Baritone.a().buildRepeat.value;
               int var60 = Baritone.a().buildRepeatCount.value;
               var44.c++;
               if (var55.equals(new class_2382(0, 0, 0)) || var60 != -1 && var44.c >= var60) {
                  var44.logDirect("Done building");
                  if (Baritone.a().notificationOnBuildFinished.value) {
                     var44.logNotification("Done building", false);
                  }

                  var44.onLostControl();
                  return null;
               }

               var44.b = 0;
               var44.a = new class_2338(var44.a).method_10081(var55);
               if (!Baritone.a().buildRepeatSneaky.value) {
                  var44.a.reset();
               }

               var44.logDirect("Repeating build in vector " + var55 + ", new origin is " + var44.a);
               var3++;
               var2 = var2;
               var44 = var44;
            }
         } else {
            if (Baritone.a().distanceTrim.value) {
               BuilderProcess var48 = var44;
               HashSet var61;
               (var61 = new HashSet<>(var48.a)).removeIf(var1x -> var1x.method_10262(super.a.player().method_24515()) > 200.0);
               if (!var61.isEmpty()) {
                  var48.a = var61;
               }
            }

            BuilderProcess.BuilderCalculationContext var62 = var46;
            BuilderProcess var49 = var44;
            BetterBlockPos var67 = var44.a.playerFeet();
            BetterBlockPos var71 = var49.a.a.a();
            int var75 = -5;

            label370:
            while (true) {
               if (var75 > 5) {
                  var111 = Optional.empty();
                  break;
               }

               for (int var77 = Baritone.a().breakFromAbove.value ? -1 : 0; var77 <= 5; var77++) {
                  for (int var79 = -5; var79 <= 5; var79++) {
                     int var81 = var67.x + var75;
                     int var86 = var67.y + var77;
                     int var90 = var67.z + var79;
                     class_2680 var93;
                     class_2680 var96;
                     if ((var77 != -1 || var81 != var71.x || var90 != var71.z)
                        && (var93 = var62.a(var81, var86, var90, var62.a.a(var81, var86, var90))) != null
                        && !((var96 = var62.a.a(var81, var86, var90)).method_26204() instanceof class_2189)
                        && var96.method_26204() != class_2246.field_10382
                        && var96.method_26204() != class_2246.field_10164
                        && !a(var96, var93, false)) {
                        BetterBlockPos var82 = new BetterBlockPos(var81, var86, var90);
                        Optional var87;
                        if ((var87 = RotationUtils.reachable(var49.a, var82, var49.a.playerController().getBlockReachDistance())).isPresent()) {
                           var111 = Optional.of(new class_3545(var82, (Rotation)var87.get()));
                           break label370;
                        }
                     }
                  }
               }

               var75++;
            }

            Optional var50 = var111;
            if (var111.isPresent() && var2 && var44.a.player().method_24828()) {
               Rotation var59 = (Rotation)((class_3545)var50.get()).method_15441();
               BetterBlockPos var54 = (BetterBlockPos)((class_3545)var50.get()).method_15442();
               var44.a.a.updateTarget(var59, true);
               MovementHelper.a(var44.a, var46.a(var54));
               if (var44.a.player().method_18276()) {
                  var44.a.a.setInputForceState(Input.SNEAK, true);
               }

               if (var44.a.isLookingAt(var54) || var44.a.playerRotations().isReallyCloseTo(var59)) {
                  var44.a.a.setInputForceState(Input.CLICK_LEFT, true);
               }

               return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            ArrayList var56 = new ArrayList();
            ArrayList var68 = var56;
            var62 = var46;
            var49 = var44;
            var71 = var44.a.playerFeet();
            var75 = -5;

            label341:
            while (true) {
               if (var75 > 5) {
                  var115 = Optional.empty();
                  break;
               }

               for (int var78 = -5; var78 <= 1; var78++) {
                  for (int var80 = -5; var80 <= 5; var80++) {
                     int var83 = var71.x + var75;
                     int var88 = var71.y + var78;
                     int var91 = var71.z + var80;
                     class_2680 var94;
                     if ((var94 = var62.a(var83, var88, var91, var62.a.a(var83, var88, var91))) != null) {
                        class_2680 var97 = var62.a.a(var83, var88, var91);
                        if (MovementHelper.a(var83, var91, var97, var62.a)
                           && !a(var97, var94, false)
                           && (var78 != 1 || !(var62.a.a(var83, var88 + 1, var91).method_26204() instanceof class_2189))) {
                           var68.add(var94);
                           BlockStateInterface var99 = var62.a;
                           int var98 = var91;
                           int var95 = var88;
                           var91 = var83;
                           class_2680 var89 = var94;
                           BuilderProcess var84 = var49;
                           class_2350[] var100;
                           int var20 = (var100 = class_2350.values()).length;
                           int var21 = 0;

                           label332:
                           while (true) {
                              if (var21 >= var20) {
                                 var114 = Optional.empty();
                                 break;
                              }

                              class_2350 var22 = var100[var21];
                              BetterBlockPos var23 = new BetterBlockPos(var91, var95, var98).relative(var22);
                              class_2680 var24 = var99.a(var23);
                              class_265 var101;
                              if (!MovementHelper.a(var23.x, var23.z, var24, var99)
                                 && var89.method_26184(var84.a.world(), new BetterBlockPos(var91, var95, var98))
                                 && var84.a(new BetterBlockPos(var91, var95, var98), var89)
                                 && !(var101 = var24.method_26218(var84.a.world(), var23)).method_1110()) {
                                 class_238 var102 = var101.method_1107();
                                 class_2350 var29 = var22;

                                 class_243[] var112 = switch (var29) {
                                    case field_11036 -> new class_243[]{
                                       new class_243(0.5, 1.0, 0.5),
                                       new class_243(0.1, 1.0, 0.5),
                                       new class_243(0.9, 1.0, 0.5),
                                       new class_243(0.5, 1.0, 0.1),
                                       new class_243(0.5, 1.0, 0.9)
                                    };
                                    case field_11033 -> new class_243[]{
                                       new class_243(0.5, 0.0, 0.5),
                                       new class_243(0.1, 0.0, 0.5),
                                       new class_243(0.9, 0.0, 0.5),
                                       new class_243(0.5, 0.0, 0.1),
                                       new class_243(0.5, 0.0, 0.9)
                                    };
                                    case field_11043, field_11035, field_11034, field_11039 -> {
                                       double var40 = var29.method_10148() == 0 ? 0.5 : (1 + var29.method_10148()) / 2.0;
                                       double var42 = var29.method_10165() == 0 ? 0.5 : (1 + var29.method_10165()) / 2.0;
                                       yield new class_243[]{new class_243(var40, 0.25, var42), new class_243(var40, 0.75, var42)};
                                    }
                                    default -> throw new IllegalStateException("Unexpected side " + var29);
                                 };
                                 class_243[] var25 = var112;
                                 int var26 = var112.length;

                                 for (int var27 = 0; var27 < var26; var27++) {
                                    class_243 var28 = var25[var27];
                                    double var34 = var23.x + var102.field_1323 * var28.field_1352 + var102.field_1320 * (1.0 - var28.field_1352);
                                    double var36 = var23.y + var102.field_1322 * var28.field_1351 + var102.field_1325 * (1.0 - var28.field_1351);
                                    double var38 = var23.z + var102.field_1321 * var28.field_1350 + var102.field_1324 * (1.0 - var28.field_1350);
                                    Rotation var103 = RotationUtils.calcRotationFromVec3d(
                                       RayTraceUtils.inferSneakingEyePosition(var84.a.player()), new class_243(var34, var36, var38), var84.a.playerRotations()
                                    );
                                    Rotation var104 = var84.a.a.getAimProcessor().peekRotation(var103);
                                    class_239 var30;
                                    if ((
                                             var30 = RayTraceUtils.rayTraceTowards(
                                                var84.a.player(), var104, var84.a.playerController().getBlockReachDistance(), true
                                             )
                                          )
                                          != null
                                       && var30.method_17783() == class_240.field_1332
                                       && ((class_3965)var30).method_17777().equals(var23)
                                       && ((class_3965)var30).method_17780() == var22.method_10153()) {
                                       Rotation var110 = var104;
                                       class_239 var41 = var30;
                                       class_2680 var109 = var89;
                                       BuilderProcess var105 = var84;
                                       int var43 = 0;

                                       while (true) {
                                          if (var43 >= 9) {
                                             var113 = OptionalInt.empty();
                                             break;
                                          }

                                          class_1799 var107;
                                          if (!(var107 = (class_1799)var105.a.player().method_31548().method_67533().get(var43)).method_7960()
                                             && var107.method_7909() instanceof class_1747) {
                                             float var31 = var105.a.player().method_36454();
                                             float var32 = var105.a.player().method_36455();
                                             var105.a.player().method_36456(var110.getYaw());
                                             var105.a.player().method_36457(var110.getPitch());
                                             class_1750 var33 = new class_1750(
                                                new BuilderProcess$2(var105.a.world(), var105.a.player(), class_1268.field_5808, var107, (class_3965)var41)
                                             );
                                             class_2680 var108 = ((class_1747)var107.method_7909()).method_7711().method_9605(var33);
                                             var105.a.player().method_36456(var31);
                                             var105.a.player().method_36457(var32);
                                             if (var108 != null && var33.method_7716() && a(var108, var109, true)) {
                                                var113 = OptionalInt.of(var43);
                                                break;
                                             }
                                          }

                                          var43++;
                                       }

                                       OptionalInt var106 = var113;
                                       if (var113.isPresent()) {
                                          var114 = Optional.of(new BuilderProcess.Placement(var106.getAsInt(), var23, var22.method_10153(), var103));
                                          break label332;
                                       }
                                    }
                                 }
                              }

                              var21++;
                           }

                           Optional var85 = var114;
                           if (var114.isPresent()) {
                              var115 = var85;
                              break label341;
                           }
                        }
                     }
                  }
               }

               var75++;
            }

            Optional var52 = var115;
            if (var115.isPresent() && var2 && var44.a.player().method_24828() && var44.a <= 0) {
               Rotation var66 = ((BuilderProcess.Placement)var52.get()).a;
               var44.a.a.updateTarget(var66, true);
               var44.a.player().method_31548().method_61496(((BuilderProcess.Placement)var52.get()).a);
               var44.a.a.setInputForceState(Input.SNEAK, true);
               if (var44.a.isLookingAt(((BuilderProcess.Placement)var52.get()).a)
                     && ((class_3965)var44.a.objectMouseOver()).method_17780().equals(((BuilderProcess.Placement)var52.get()).a)
                  || var44.a.playerRotations().isReallyCloseTo(var66)) {
                  var44.a.a.setInputForceState(Input.CLICK_RIGHT, true);
               }

               return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            if (Baritone.a().allowInventory.value) {
               ArrayList var64 = new ArrayList();
               ArrayList var53 = new ArrayList();

               label271:
               for (class_2680 var69 : var56) {
                  for (int var73 = 0; var73 < 9; var73++) {
                     if (a(var44.a.get(var73), var69, true)) {
                        var64.add(var73);
                        continue label271;
                     }
                  }

                  var53.add(var69);
               }

               label257:
               for (int var58 = 9; var58 < 36; var58++) {
                  for (class_2680 var74 : var53) {
                     if (a(var44.a.get(var58), var74, true)) {
                        if (!var44.a.a.a(var58, var64::contains)) {
                           return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                        }
                        break label257;
                     }
                  }
               }
            }

            Goal var65;
            if ((var65 = var44.a(var46, var44.a.subList(0, 9), false)) == null && (var65 = var44.a(var46, var44.a, true)) == null) {
               if (Baritone.a().skipFailedLayers.value && Baritone.a().buildInLayers.value && var44.b * Baritone.a().layerHeight.value < var44.b.heightY()) {
                  var44.logDirect("Skipping layer that I cannot construct! Layer #" + var44.b);
                  var44.b++;
                  var3++;
                  var2 = var2;
                  var44 = var44;
                  continue;
               }

               var44.logDirect("Unable to do it. Pausing. resume to resume, cancel to cancel");
               var44.a = true;
               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }

            return new PathingCommandContext(var65, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH, var46);
         }
      }

      return new PathingCommand(null, PathingCommandType.SET_GOAL_AND_PATH);
   }

   private void a(BuilderProcess.BuilderCalculationContext var1) {
      this.a = new HashSet<>();

      for (int var2 = 0; var2 < this.a.heightY(); var2++) {
         for (int var3 = 0; var3 < this.a.lengthZ(); var3++) {
            for (int var4 = 0; var4 < this.a.widthX(); var4++) {
               int var5 = var4 + this.a.method_10263();
               int var6 = var2 + this.a.method_10264();
               int var7 = var3 + this.a.method_10260();
               class_2680 var8 = var1.a.a(var5, var6, var7);
               if (this.a.inSchematic(var4, var2, var3, var8)) {
                  if (var1.a.a(var5, var7)) {
                     if (a(var1.a.a(var5, var6, var7), this.a.desiredState(var4, var2, var3, var8, this.a), false)) {
                        this.a.add(BetterBlockPos.longHash(var5, var6, var7));
                     } else {
                        this.a.add(new BetterBlockPos(var5, var6, var7));
                        this.a.remove(BetterBlockPos.longHash(var5, var6, var7));
                        if (this.a.size() > Baritone.a().incorrectSize.value) {
                           return;
                        }
                     }
                  } else if (!this.a.contains(BetterBlockPos.longHash(var5, var6, var7))) {
                     this.a.add(new BetterBlockPos(var5, var6, var7));
                     if (this.a.size() > Baritone.a().incorrectSize.value) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private Goal a(BuilderProcess.BuilderCalculationContext var1, List<class_2680> var2, boolean var3) {
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      HashMap var8 = new HashMap();
      ArrayList var9 = new ArrayList();
      this.a.forEach(var8x -> {
         class_2680 var9x;
         if (!((var9x = var1.a.a(var8x)).method_26204() instanceof class_2189)) {
            if (var9x.method_26204() instanceof class_2404) {
               if (!MovementHelper.g(var9x)) {
                  var6.add(var8x);
               } else {
                  var7.add(var8x);
               }
            } else {
               var5.add(var8x);
            }
         } else {
            class_2680 var10x;
            if ((var10x = var1.a(var8x.x, var8x.y, var8x.z, var9x)) == null) {
               var9.add(var8x);
            } else {
               class_2680 var12 = var10x;
               Iterator var11x = var2.iterator();

               boolean var13;
               while (true) {
                  if (var11x.hasNext()) {
                     if (!a((class_2680)var11x.next(), var12)) {
                        continue;
                     }

                     var13 = true;
                     break;
                  }

                  var13 = false;
                  break;
               }

               if (var13) {
                  var4.add(var8x);
               } else {
                  var8.put(var10x, 1 + var8.getOrDefault(var10x, 0));
               }
            }
         }
      });
      this.a.removeAll(var9);
      var2 = new ArrayList();
      var5.forEach(
         var3x -> {
            BuilderProcess.BuilderCalculationContext var5x = var1;
            BetterBlockPos var4x = var3x;
            var2.add(
               Baritone.a().goalBreakFromAbove.value
                     && var5x.a.a(var4x.method_10084()).method_26204() instanceof class_2189
                     && var5x.a.a(var4x.method_10086(2)).method_26204() instanceof class_2189
                  ? new BuilderProcess.JankyGoalComposite(new BuilderProcess.GoalBreak(var4x), new BuilderProcess$4(var4x.method_10084()))
                  : new BuilderProcess.GoalBreak(var4x)
            );
         }
      );
      var5 = new ArrayList();
      var4.forEach(
         var4x -> {
            if (!var4.contains(var4x.below()) && !var4.contains(var4x.below(2))) {
               BetterBlockPos var11x = var4x;
               BuilderProcess var10x = this;
               Goal var10001;
               if (!(super.a.world().method_8320(var11x).method_26204() instanceof class_2189)) {
                  var10001 = new BuilderProcess.GoalPlace(var11x);
               } else {
                  boolean var12 = !(var10x.a.world().method_8320(var11x.method_10084()).method_26204() instanceof class_2189);
                  class_2680 var5x = var10x.a.world().method_8320(var11x);
                  class_2350[] var6x = Movement.a;
                  int var7x = Movement.a.length;
                  int var8x = 0;

                  while (true) {
                     if (var8x >= var7x) {
                        var10001 = new BuilderProcess.GoalPlace(var11x);
                        break;
                     }

                     class_2350 var9x = var6x[var8x];
                     if (MovementHelper.c(var10x.a, var11x.method_10093(var9x))
                        && var10x.a(var11x, var1.a(var11x.method_10263(), var11x.method_10264(), var11x.method_10260(), var5x))) {
                        var10001 = new BuilderProcess.GoalAdjacent(var11x, var11x.method_10093(var9x), var12);
                        break;
                     }

                     var8x++;
                  }
               }

               var5.add(var10001);
            }
         }
      );
      var6.forEach(var1x -> var5.add(new GoalBlock(var1x.above())));
      if (!var5.isEmpty()) {
         return new BuilderProcess.JankyGoalComposite(new GoalComposite(var5.toArray(new Goal[0])), new GoalComposite(var2.toArray(new Goal[0])));
      }

      if (var2.isEmpty()) {
         if (var3 && !var8.isEmpty()) {
            this.logDirect("Missing materials for at least:");
            this.logDirect(var8.entrySet().stream().map(var0 -> String.format("%sx %s", var0.getValue(), var0.getKey())).collect(Collectors.joining("\n")));
         }

         if (var3 && !var7.isEmpty()) {
            this.logDirect("Unreplaceable liquids at at least:");
            this.logDirect(var7.stream().map(var0 -> String.format("%s %s %s", var0.x, var0.y, var0.z)).collect(Collectors.joining("\n")));
         }

         return null;
      } else {
         return new GoalComposite(var2.toArray(new Goal[0]));
      }
   }

   @Override
   public final void onLostControl() {
      this.a = null;
      this.a = null;
      this.a = null;
      this.b = null;
      this.b = Baritone.a().startAtLayer.value;
      this.c = 0;
      this.a = false;
      this.a = null;
   }

   @Override
   public final String displayName0() {
      return this.a ? "Builder Paused" : "Building " + this.a;
   }

   @Override
   public final Optional<Integer> getMinLayer() {
      return Baritone.a().buildInLayers.value ? Optional.of(this.b) : Optional.empty();
   }

   @Override
   public final Optional<Integer> getMaxLayer() {
      return Baritone.a().buildInLayers.value ? Optional.of(this.d) : Optional.empty();
   }

   final List<class_2680> a(int var1) {
      ArrayList var2 = new ArrayList();

      for (int var3 = 0; var3 < var1; var3++) {
         class_1799 var4;
         if (!(var4 = (class_1799)super.a.player().method_31548().method_67533().get(var3)).method_7960() && var4.method_7909() instanceof class_1747) {
            class_2680 var5;
            if ((
                  var5 = ((class_1747)var4.method_7909())
                     .method_7711()
                     .method_9605(
                        new class_1750(
                           new BuilderProcess$5(
                              super.a.world(),
                              super.a.player(),
                              class_1268.field_5808,
                              var4,
                              new class_3965(
                                 new class_243(
                                    super.a.player().method_73189().field_1352,
                                    super.a.player().method_73189().field_1351,
                                    super.a.player().method_73189().field_1350
                                 ),
                                 class_2350.field_11036,
                                 super.a.playerFeet(),
                                 false
                              )
                           )
                        )
                     )
               )
               != null) {
               var2.add(var5);
            } else {
               var2.add(class_2246.field_10124.method_9564());
            }
         } else {
            var2.add(class_2246.field_10124.method_9564());
         }
      }

      return var2;
   }

   private static boolean a(class_2680 var0, class_2680 var1) {
      if (var0.method_26204() != var1.method_26204()) {
         return false;
      }

      boolean var2 = Baritone.a().buildIgnoreDirection.value;
      List var3 = Baritone.a().buildIgnoreProperties.value;
      if (!var2 && var3.isEmpty()) {
         return var0.equals(var1);
      }

      Map var6 = var0.method_11656();
      Map var7 = var1.method_11656();

      for (class_2769 var5 : var6.keySet()) {
         if (var6.get(var5) != var7.get(var5) && (!var2 || !a.contains(var5)) && !var3.contains(var5.method_11899())) {
            return false;
         }
      }

      return true;
   }

   static boolean a(class_2680 var0, class_2680 var1, boolean var2) {
      if (var1 == null) {
         return true;
      } else if (var0.method_26204() instanceof class_2404 && Baritone.a().okIfWater.value) {
         return true;
      } else if (var0.method_26204() instanceof class_2189 && var1.method_26204() instanceof class_2189) {
         return true;
      } else if (var0.method_26204() instanceof class_2189 && Baritone.a().okIfAir.value.contains(var1.method_26204())) {
         return true;
      } else if (var1.method_26204() instanceof class_2189 && Baritone.a().buildIgnoreBlocks.value.contains(var0.method_26204())) {
         return true;
      } else if (!(var0.method_26204() instanceof class_2189) && Baritone.a().buildIgnoreExisting.value && !var2) {
         return true;
      } else if (Baritone.a().buildValidSubstitutes.value.getOrDefault(var1.method_26204(), Collections.emptyList()).contains(var0.method_26204()) && !var2) {
         return true;
      } else {
         return var0.equals(var1) ? true : a(var0, var1);
      }
   }

   public class BuilderCalculationContext extends CalculationContext {
      private final List<class_2680> b = BuilderProcess.this.a(9);
      private final ISchematic a;
      private final int e;
      private final int f;
      private final int g;

      public BuilderCalculationContext() {
         super(BuilderProcess.a(BuilderProcess.this), true);
         this.a = BuilderProcess.this.a;
         this.e = BuilderProcess.this.a.method_10263();
         this.f = BuilderProcess.this.a.method_10264();
         this.g = BuilderProcess.this.a.method_10260();
         super.e += 10.0;
         super.d = 1.0;
      }

      final class_2680 a(int var1, int var2, int var3, class_2680 var4) {
         return this.a.inSchematic(var1 - this.e, var2 - this.f, var3 - this.g, var4)
            ? this.a.desiredState(var1 - this.e, var2 - this.f, var3 - this.g, var4, BuilderProcess.this.a)
            : null;
      }

      @Override
      public final double a(int var1, int var2, int var3, class_2680 var4) {
         if (!super.a.b(var1, var3)) {
            return 1000000.0;
         }

         class_2680 var5;
         if ((var5 = this.a(var1, var2, var3, var4)) != null) {
            if (var5.method_26204() instanceof class_2189) {
               return super.a * Baritone.a().placeIncorrectBlockPenaltyMultiplier.value;
            } else if (this.b.contains(var5)) {
               return 0.0;
            } else {
               return !super.c ? 1000000.0 : super.a * 1.5 * Baritone.a().placeIncorrectBlockPenaltyMultiplier.value;
            }
         } else {
            return super.c ? super.a : 1000000.0;
         }
      }

      @Override
      public final double b(int var1, int var2, int var3, class_2680 var4) {
         if (!super.e && !super.a.contains(var4.method_26204())) {
            return 1000000.0;
         }

         if ((var4 = this.a(var1, var2, var3, var4)) != null) {
            if (var4.method_26204() instanceof class_2189) {
               return 1.0;
            } else {
               return BuilderProcess.a(super.a.a(var1, var2, var3), var4, false) ? Baritone.a().breakCorrectBlockPenaltyMultiplier.value : 1.0;
            }
         } else {
            return 1.0;
         }
      }
   }

   public static class GoalAdjacent extends GoalGetToBlock {
      private boolean a;
      private class_2338 a;

      public GoalAdjacent(BetterBlockPos var1, class_2338 var2, boolean var3) {
         super(var1);
         this.a = var2;
         this.a = var3;
      }

      @Override
      public boolean isInGoal(int var1, int var2, int var3) {
         if (var1 == super.x && var2 == super.y && var3 == super.z) {
            return false;
         } else if (var1 == this.a.method_10263() && var2 == this.a.method_10264() && var3 == this.a.method_10260()) {
            return false;
         } else if (!this.a && var2 == super.y - 1) {
            return false;
         } else {
            return var2 < super.y - 1 ? false : super.isInGoal(var1, var2, var3);
         }
      }

      @Override
      public double heuristic(int var1, int var2, int var3) {
         return super.y * 100 + super.heuristic(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (!super.equals(var1)) {
            return false;
         }

         var1 = var1;
         return this.a == var1.a && Objects.equals(this.a, var1.a);
      }

      @Override
      public int hashCode() {
         return (
                  (-2112107180 + super.hashCode()) * 1730799370
                     + (int)BetterBlockPos.longHash(this.a.method_10263(), this.a.method_10264(), this.a.method_10260())
               )
               * 260592149
            + (this.a ? -1314802005 : 1565710265);
      }

      @Override
      public String toString() {
         return String.format(
            "GoalAdjacent{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z)
         );
      }
   }

   public static class GoalBreak extends GoalGetToBlock {
      public GoalBreak(class_2338 var1) {
         super(var1);
      }

      @Override
      public boolean isInGoal(int var1, int var2, int var3) {
         return var2 > super.y ? false : super.isInGoal(var1, var2, var3);
      }

      @Override
      public String toString() {
         return String.format(
            "GoalBreak{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z)
         );
      }

      @Override
      public int hashCode() {
         return super.hashCode() * 1636324008;
      }
   }

   public static class GoalPlace extends GoalBlock {
      public GoalPlace(BetterBlockPos var1) {
         super(var1.method_10084());
      }

      @Override
      public double heuristic(int var1, int var2, int var3) {
         return super.y * 100 + super.heuristic(var1, var2, var3);
      }

      @Override
      public int hashCode() {
         return super.hashCode() * 1910811835;
      }

      @Override
      public String toString() {
         return String.format(
            "GoalPlace{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z)
         );
      }
   }

   public static class JankyGoalComposite implements Goal {
      private final Goal a;
      private final Goal b;

      public JankyGoalComposite(Goal var1, Goal var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public boolean isInGoal(int var1, int var2, int var3) {
         return this.a.isInGoal(var1, var2, var3) || this.b.isInGoal(var1, var2, var3);
      }

      @Override
      public double heuristic(int var1, int var2, int var3) {
         return this.a.heuristic(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            return Objects.equals(this.a, var1.a) && Objects.equals(this.b, var1.b);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return (1544707182 + this.a.hashCode()) * -80327868 + this.b.hashCode();
      }

      @Override
      public String toString() {
         return "JankyComposite Primary: " + this.a + " Fallback: " + this.b;
      }
   }

   public static class Placement {
      final int a;
      final BetterBlockPos a;
      final class_2350 a;
      final Rotation a;

      public Placement(int var1, BetterBlockPos var2, class_2350 var3, Rotation var4) {
         this.a = var1;
         this.a = var2;
         this.a = var3;
         this.a = var4;
      }
   }
}
