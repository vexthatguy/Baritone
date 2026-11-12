package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.event.events.BlockChangeEvent;
import com.vex.baritone.api.event.events.ChunkEvent;
import com.vex.baritone.api.event.events.PacketEvent;
import com.vex.baritone.api.event.events.RenderEvent;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.events.WorldEvent;
import com.vex.baritone.api.event.events.type.EventState;
import com.vex.baritone.api.event.listener.AbstractGameEventListener;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.pathing.goals.GoalYLevel;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.process.IElytraProcess;
import com.vex.baritone.api.process.PathingCommand;
import com.vex.baritone.api.process.PathingCommandType;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.Pair;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.pathing.movement.CalculationContext;
import com.vex.baritone.pathing.movement.movements.MovementFall;
import com.vex.baritone.pathing.path.PathExecutor;
import com.vex.baritone.process.elytra.ElytraBehavior;
import com.vex.baritone.process.elytra.NetherPath;
import com.vex.baritone.process.elytra.NetherPathfinderContext;
import com.vex.baritone.process.elytra.NullElytraProcess;
import com.vex.baritone.utils.BaritoneProcessHelper;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.IRenderer;
import com.vex.baritone.utils.PathRenderer;
import com.vex.baritone.utils.PathingCommandContext;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.awt.Color;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import net.minecraft.class_1304;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_287;
import net.minecraft.class_638;
import net.minecraft.class_2338.class_2339;

public class ElytraProcess extends BaritoneProcessHelper implements AbstractGameEventListener, IBaritoneProcess, IElytraProcess {
   public ElytraProcess.State a;
   private boolean a;
   private BetterBlockPos a;
   private boolean b;
   private GoalYLevel a;
   private ElytraBehavior a;
   private boolean c;
   private Set<BetterBlockPos> a = new HashSet<>();

   @Override
   public void onLostControl() {
      this.a = ElytraProcess.State.d;
      this.a = false;
      this.a = null;
      this.b = false;
      this.a = null;
      this.a();
   }

   private ElytraProcess(Baritone var1) {
      super(var1);
      var1.getGameEventHandler().registerEventListener(this);
   }

   public static IElytraProcess a(Baritone var0) {
      return NetherPathfinderContext.a() ? new ElytraProcess(var0) : new NullElytraProcess(var0);
   }

   @Override
   public boolean isActive() {
      return this.a != null;
   }

   @Override
   public void resetState() {
      class_2338 var1 = this.currentDestination();
      this.onLostControl();
      if (var1 != null) {
         this.pathTo(var1);
         this.repackChunks();
      }
   }

   @Override
   public PathingCommand onTick(boolean var1, boolean var2) {
      if (Baritone.a().elytraNetherSeed.value != this.a.a.b) {
         this.logDirect("Nether seed changed, recalculating path");
         this.resetState();
      }

      if (this.c != Baritone.a().elytraPredictTerrain.value) {
         this.logDirect("elytraPredictTerrain setting changed, recalculating path");
         this.c = Baritone.a().elytraPredictTerrain.value;
         this.resetState();
      }

      ElytraBehavior var4 = this.a;
      synchronized (this.a.a.a) {
         ElytraBehavior var3 = var4;
         var4.a = null;
         if (var3.a != null) {
            try {
               var3.a = var3.a.get();
            } catch (Exception var18) {
            } finally {
               var3.a = null;
            }
         }

         ElytraBehavior var6 = var3;
         Runnable var7;
         if (var3.d <= 0 && (var7 = var6.a.poll()) != null) {
            var7.run();
            var6.d = Baritone.a().ticksBetweenInventoryMoves.value;
         }

         if (var6.d > 0) {
            var6.d--;
         }

         if (var3.a > 0) {
            var3.a--;
         }

         if (var3.b > 0) {
            var3.b--;
         }

         if (!var3.a().isPresent()) {
            var3.c = 0;
         }

         var3.a.clear();
         var3.b.clear();
         var3.d = null;
         var3.c = null;
         var3.a = null;
         NetherPath var5 = var3.a.a;
         if (!var3.a.a.isEmpty()) {
            if (var3.b == null) {
               var3.a.a();
            } else {
               var3.a = new BlockStateInterface(var3.a);
               ElytraBehavior.PathManager var39 = var3.a;
               var3.a.c();
               int var43 = var39.a;
               var39.a = Math.max(var39.a, var39.c);
               if (var39.a == var43 && var39.a.a.player().method_6128()) {
                  var39.b++;
               } else {
                  var39.b = 0;
               }

               var39.b();
               if (!var39.b) {
                  int var44 = var39.a.size() - 1;
                  if (!var39.a && var39.a.a.world().method_8477(var39.a.a(var44))) {
                     var39.a(var44);
                  }
               }

               int var40 = var3.a.c;
               var3.d = var5.subList(Math.max(var40 - 30, 0), Math.min(var40 + 100, var5.size()));
            }
         }
      }

      long var57;
      if (((var57 = System.currentTimeMillis()) - var4.a) / 1000L > Baritone.a().elytraTimeBetweenCacheCullSecs.value) {
         var4.a.a(var4.a.player().method_31476().field_9181, var4.a.player().method_31476().field_9180, Baritone.a().elytraCacheCullDistance.value, var4.a);
         var4.a = var57;
      }

      if (var1) {
         this.onLostControl();
         this.logDirect(
            "Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding."
         );
         return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
      }

      var1 = false;
      if (super.a.player().method_6128() && this.a()) {
         if (Baritone.a().elytraAllowEmergencyLand.value) {
            this.logDirect("Emergency landing - almost out of elytra durability or fireworks");
            var1 = true;
         } else {
            this.logDirect("almost out of elytra durability or fireworks, but I'm going to continue since elytraAllowEmergencyLand is false");
         }
      }

      if (super.a.player().method_6128() && this.a != ElytraProcess.State.f && (this.a.a.a || var1)) {
         BetterBlockPos var28;
         if ((var28 = this.a.a.a.a()) != null
            && (super.a.player().method_73189().method_1025(var28.method_46558()) < 2304.0 || var1)
            && (!this.a || var1 && this.a == null)) {
            this.logDirect("Path complete, picking a nearby safe landing spot...");
            BetterBlockPos var58 = super.a.playerFeet();
            ElytraProcess var32 = this;
            PriorityQueue var12 = new PriorityQueue<>(
               Comparator.<BetterBlockPos>comparingInt(var1x -> (var1x.x - var58.x) * (var1x.x - var58.x) + (var1x.z - var58.z) * (var1x.z - var58.z))
                  .thenComparingInt(var0 -> -var0.y)
            );
            HashSet var22 = new HashSet();
            LongOpenHashSet var37 = new LongOpenHashSet();
            var12.add(var58);

            BetterBlockPos var71;
            while (true) {
               if (var12.isEmpty()) {
                  var71 = null;
                  break;
               }

               BetterBlockPos var41 = (BetterBlockPos)var12.poll();
               if (var32.a.world().method_8477(var41)) {
                  BetterBlockPos var8 = var41;
                  if (var41.method_10264() >= 0 && var8.method_10264() < 128 && var32.a.world().method_8320(var41).method_26204() == class_2246.field_10124) {
                     LongOpenHashSet var10 = var37;
                     BetterBlockPos var9 = var41;
                     ElytraProcess var47 = var32;
                     class_2339 var59 = new class_2339(var9.method_10263(), var9.method_10264(), var9.method_10260());

                     while (true) {
                        if (var59.method_10264() >= 0 && !var10.contains(var59.method_10063())) {
                           var10.add(var59.method_10063());
                           class_2248 var13;
                           if (a(var13 = var47.a.world().method_8320(var59).method_26204())) {
                              class_2339 var14 = var59;
                              ElytraProcess var53 = var47;
                              var71 = var47.a(var14.method_10095())
                                    && var53.a(var14.method_10072())
                                    && var53.a(var14.method_10078())
                                    && var53.a(var14.method_10067())
                                    && var53.a(var14.method_10095().method_10067())
                                    && var53.a(var14.method_10095().method_10078())
                                    && var53.a(var14.method_10072().method_10067())
                                    && var53.a(var14.method_10072().method_10078())
                                 ? new BetterBlockPos(var59)
                                 : null;
                              break;
                           }

                           if (var13 == class_2246.field_10124) {
                              var59.method_10103(var59.method_10263(), var59.method_10264() - 1, var59.method_10260());
                              continue;
                           }
                        }

                        var71 = null;
                        break;
                     }

                     BetterBlockPos var45 = var71;
                     if (var71 != null) {
                        var9 = var45;
                        ElytraProcess var48 = var32;
                        class_2339 var60;
                        int var65 = (var60 = new class_2339(var9.method_10263(), var9.method_10264(), var9.method_10260())).method_10264() + 15;
                        int var54 = var60.method_10264() + 1;

                        while (true) {
                           if (var54 > var65) {
                              var69 = true;
                              break;
                           }

                           var60.method_10103(var60.method_10263(), var54, var60.method_10260());
                           if (!(var48.a.world().method_8320(var60).method_26204() instanceof class_2189)) {
                              var69 = false;
                              break;
                           }

                           var54++;
                        }

                        if (var69) {
                           var9 = var45.above(15);
                           ElytraProcess var49 = var32;
                           class_2339 var61 = new class_2339();
                           var65 = -4;

                           label711:
                           while (true) {
                              if (var65 > 4) {
                                 var70 = true;
                                 break;
                              }

                              for (int var55 = -4; var55 <= 4; var55++) {
                                 for (int var68 = -4; var68 <= 4; var68++) {
                                    var61.method_10103(var9.method_10263() + var65, var9.method_10264() + var55, var9.method_10260() + var68);
                                    if (!(var49.a.world().method_8320(var61).method_26204() instanceof class_2189)) {
                                       var70 = false;
                                       break label711;
                                    }
                                 }
                              }

                              var65++;
                           }

                           if (var70 && !var32.a.contains(var45.above(15))) {
                              var71 = var45.above(15);
                              break;
                           }
                        }
                     }

                     if (var22.add(var41.north())) {
                        var12.add(var41.north());
                     }

                     if (var22.add(var41.east())) {
                        var12.add(var41.east());
                     }

                     if (var22.add(var41.south())) {
                        var12.add(var41.south());
                     }

                     if (var22.add(var41.west())) {
                        var12.add(var41.west());
                     }

                     if (var22.add(var41.above())) {
                        var12.add(var41.above());
                     }

                     if (var22.add(var41.below())) {
                        var12.add(var41.below());
                     }
                  }
               }
            }

            BetterBlockPos var23 = var71;
            if (var71 != null) {
               this.a(var23, true);
               this.a = var23;
            }

            this.a = true;
         }

         if (var28 != null && super.a.player().method_73189().method_1025(var28.method_46558()) < 1.0) {
            if (Baritone.a().notificationOnPathComplete.value && !this.b) {
               this.logNotification("Pathing complete", false);
            }

            if (Baritone.a().disconnectOnArrival.value && !this.b) {
               this.onLostControl();
               class_1937 var36;
               if ((var36 = super.a.world()) instanceof class_638) {
                  ((class_638)var36).method_8525(class_2561.method_43470("[Baritone] Arrived at goal!"));
               }

               return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            this.b = true;
            if (this.a) {
               this.a = ElytraProcess.State.f;
               this.logDirect("Above the landing spot, landing...");
            }
         }
      }

      if (this.a == ElytraProcess.State.f) {
         BetterBlockPos var29 = this.a != null ? this.a : this.a.a.a.a();
         if (super.a.player().method_6128() && var29 != null) {
            class_243 var24 = super.a.player().method_73189();
            class_243 var33 = new class_243(var29.x + 0.5, var24.field_1351, var29.z + 0.5);
            Rotation var25 = RotationUtils.calcRotationFromVec3d(var24, var33, super.a.playerRotations());
            super.a.a.updateTarget(new Rotation(var25.getYaw(), 0.0F), false);
            if (super.a.player().method_73189().field_1351 < var29.y - 15) {
               this.logDirect("bad landing spot, trying again...");
               this.a(var29);
            }
         }
      }

      if (super.a.player().method_6128()) {
         this.a.a = this.a == ElytraProcess.State.f;
         this.a = null;
         super.a.a.clearAllKeys();
         var4 = this.a;
         if (!this.a.a.a.isEmpty()) {
            ElytraBehavior var38 = var4;
            class_1799 var42;
            if (Baritone.a().elytraAutoSwap.value
               && var38.a.isEmpty()
               && (var42 = var38.a.player().method_6118(class_1304.field_6174)).method_7909() == class_1802.field_8833
               && var42.method_7936() - var42.method_7919() <= Baritone.a().elytraMinimumDurability.value) {
               class_2371 var62 = var38.a.player().method_31548().method_67533();
               int var67 = 0;

               int var72;
               while (true) {
                  if (var67 >= var62.size()) {
                     var72 = -1;
                     break;
                  }

                  class_1799 var56;
                  if ((var56 = (class_1799)var62.get(var67)).method_7909() == class_1802.field_8833
                     && var56.method_7936() - var56.method_7919() > Baritone.a().elytraMinimumDurability.value) {
                     var72 = var67;
                     break;
                  }

                  var67++;
               }

               int var46 = var72;
               if (var72 != -1) {
                  int var52 = var46 < 9 ? var46 + 36 : var46;
                  var38.a(var38.a.player().field_7498.field_7763, var52, class_1713.field_7790);
                  var38.a(var38.a.player().field_7498.field_7763, 6, class_1713.field_7790);
                  var38.a(var38.a.player().field_7498.field_7763, var52, class_1713.field_7790);
               }
            }

            if (var4.a.player().field_5976) {
               var4.a("hbonk");
            }

            if (var4.a.player().field_5992) {
               var4.a("vbonk");
            }

            ElytraBehavior.SolverContext var63 = var4.new SolverContext(false);
            var4.d = true;
            ElytraBehavior.Solution var64;
            if (var4.a != null && var4.a.a.equals(var63)) {
               var64 = var4.a;
            } else {
               var64 = var4.a(var63);
            }

            if (var4.b) {
               var4.a[var63.a.a() ? 1 : 0]++;
               var4.b = false;
            }

            if (var1 = var4.a.player().method_5771()) {
               var4.a.a.setInputForceState(Input.JUMP, true);
            }

            if (var64 == null) {
               var4.a("no solution");
            } else {
               var4.a.a.updateTarget(var64.a, false);
               if (!var64.a) {
                  var4.a("no pitch solution, probably gonna crash in a few ticks LOL!!!");
               } else {
                  var4.a = new BetterBlockPos(var64.a.field_1352, var64.a.field_1351, var64.a.field_1350);
                  var4.a(var64.a.a, var64.a, var64.a.a.a(), var64.b || var1);
               }
            }
         }

         return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
      } else if (this.a == ElytraProcess.State.f) {
         if (super.a.playerMotion().method_18805(1.0, 0.0, 1.0).method_1033() > 0.001) {
            this.logDirect("Landed, but still moving, waiting for velocity to die down... ");
            super.a.a.setInputForceState(Input.SNEAK, true);
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         } else {
            this.logDirect("Done :)");
            super.a.a.clearAllKeys();
            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
         }
      } else {
         if (this.a == ElytraProcess.State.e || this.a == ElytraProcess.State.d) {
            this.a = super.a.player().method_24828() && Baritone.a().elytraAutoJump.value ? ElytraProcess.State.a : ElytraProcess.State.d;
         }

         if (this.a == ElytraProcess.State.a) {
            if (this.a()) {
               this.logDirect("Not taking off, because elytra durability or fireworks are so low that I would immediately emergency land anyway.");
               this.onLostControl();
               return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            if (this.a == null) {
               this.a = new GoalYLevel(31);
            }

            PathExecutor var31 = super.a.a.a;
            if (super.a.a.a != null && var31.getPath().getGoal() == this.a) {
               IMovement var26;
               if ((var26 = var31.getPath().movements().stream().filter(var0 -> var0 instanceof MovementFall).findFirst().orElse(null)) == null) {
                  this.onLostControl();
                  this.logDirect(
                     "Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding."
                  );
                  return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }

               BetterBlockPos var34 = new BetterBlockPos(
                  (var26.getSrc().x + var26.getDest().x) / 2, (var26.getSrc().y + var26.getDest().y) / 2, (var26.getSrc().z + var26.getDest().z) / 2
               );
               this.a.a.a(var34).whenComplete((var1x, var2x) -> {
                  if (var2x == null) {
                     this.a = ElytraProcess.State.c;
                  } else {
                     this.onLostControl();
                  }
               });
               this.a = ElytraProcess.State.b;
            }

            return new PathingCommandContext(this.a, PathingCommandType.SET_GOAL_AND_PAUSE, new ElytraProcess.WalkOffCalculationContext(super.a));
         } else {
            if (this.a == ElytraProcess.State.b) {
               return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }

            if (this.a == ElytraProcess.State.c) {
               PathExecutor var30 = super.a.a.a;
               if (!(super.a.player().method_18798().field_1351 < -0.377)
                  || var2
                  || var30 == null
                  || !(var30.getPath().movements().get(var30.getPosition()) instanceof MovementFall)) {
                  return new PathingCommand(null, PathingCommandType.SET_GOAL_AND_PATH);
               }

               this.a = ElytraProcess.State.d;
            }

            if (this.a == ElytraProcess.State.d) {
               if (!var2) {
                  super.a.a.b();
               }

               super.a.a.clearAllKeys();
               if (super.a.player().method_18798().field_1351 < -0.377) {
                  super.a.a.setInputForceState(Input.JUMP, true);
               }
            }

            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
         }
      }
   }

   public final void a(BetterBlockPos var1) {
      this.a.add(var1);
      this.a = false;
      this.a = null;
      this.a = ElytraProcess.State.e;
   }

   private void a() {
      ElytraBehavior var1 = this.a;
      if (this.a != null) {
         this.a = null;
         Baritone.a().execute(var1::b);
      }
   }

   @Override
   public double priority() {
      return 0.0;
   }

   @Override
   public String displayName0() {
      return "Elytra - " + this.a.a;
   }

   @Override
   public void repackChunks() {
      if (this.a != null) {
         this.a.c();
      }
   }

   @Override
   public class_2338 currentDestination() {
      return this.a != null ? this.a.b : null;
   }

   @Override
   public void pathTo(class_2338 var1) {
      this.a(var1, false);
   }

   private void a(class_2338 var1, boolean var2) {
      if (super.a.player() != null && super.a.player().method_73183().method_27983() == class_1937.field_25180) {
         this.onLostControl();
         this.c = Baritone.a().elytraPredictTerrain.value;
         this.a = new ElytraBehavior(super.a, this, var1, var2);
         if (super.a.world() != null) {
            this.a.c();
         }

         this.a.a();
      }
   }

   @Override
   public void pathTo(Goal var1) {
      int var2;
      int var4;
      int var5;
      if (var1 instanceof GoalXZ) {
         GoalXZ var3;
         var4 = (var3 = (GoalXZ)var1).getX();
         var2 = 64;
         var5 = var3.getZ();
      } else {
         if (!(var1 instanceof GoalBlock)) {
            throw new IllegalArgumentException("The goal must be a GoalXZ or GoalBlock");
         }

         GoalBlock var6;
         var4 = (var6 = (GoalBlock)var1).x;
         var2 = var6.y;
         var5 = var6.z;
      }

      if (var2 > 0 && var2 < 128) {
         this.pathTo(new class_2338(var4, var2, var5));
      } else {
         throw new IllegalArgumentException("The y of the goal is not between 0 and 128");
      }
   }

   private boolean a() {
      class_1799 var1;
      if ((var1 = super.a.player().method_6118(class_1304.field_6174)).method_7909() == class_1802.field_8833
         && var1.method_7936() - var1.method_7919() >= Baritone.a().elytraMinimumDurability.value) {
         class_2371 var4 = super.a.player().method_31548().method_67533();
         int var2 = 0;

         for (int var3 = 0; var3 < 36; var3++) {
            if (ElytraBehavior.a((class_1799)var4.get(var3))) {
               var2 += ((class_1799)var4.get(var3)).method_7947();
            }
         }

         return var2 <= Baritone.a().elytraMinFireworksBeforeLanding.value;
      } else {
         return true;
      }
   }

   @Override
   public boolean isLoaded() {
      return true;
   }

   @Override
   public boolean isSafeToCancel() {
      return !this.isActive() || this.a != ElytraProcess.State.e && this.a != ElytraProcess.State.d;
   }

   @Override
   public void onRenderPass(RenderEvent var1) {
      if (this.a != null) {
         RenderEvent var2 = var1;
         ElytraBehavior var9 = this.a;
         Settings var3 = Baritone.a();
         if (var9.d != null) {
            PathRenderer.a(var2.getModelViewStack(), var9.d, 0, Color.RED, false, 0, 0, 0.0);
         }

         if (var9.a != null) {
            PathRenderer.a(var2.getModelViewStack(), var9.a, new GoalBlock(var9.a), var2.getPartialTicks(), Color.GREEN);
         }

         if (!var9.a.isEmpty() && var3.elytraRenderRaytraces.value) {
            class_287 var4 = IRenderer.a(Color.GREEN);

            for (Pair var6 : var9.a) {
               IRenderer.a(var4, var2.getModelViewStack(), (class_243)var6.first(), (class_243)var6.second(), var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var4, var3.renderPathIgnoreDepth.value);
         }

         if (!var9.b.isEmpty() && Baritone.a().elytraRenderRaytraces.value) {
            class_287 var10 = IRenderer.a(Color.BLUE);

            for (Pair var14 : var9.b) {
               IRenderer.a(var10, var2.getModelViewStack(), (class_243)var14.first(), (class_243)var14.second(), var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var10, var3.renderPathIgnoreDepth.value);
         }

         if (var9.c != null && Baritone.a().elytraRenderSimulation.value) {
            class_287 var11 = IRenderer.a(new Color(3591388));
            class_243 var13 = var9.a.player().method_30950(var2.getPartialTicks());

            for (int var15 = 0; var15 < var9.c.size() - 1; var15++) {
               class_243 var7 = var9.c.get(var15).method_1019(var13);
               class_243 var8 = var9.c.get(var15 + 1).method_1019(var13);
               IRenderer.a(var11, var2.getModelViewStack(), var7, var8, var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var11, var3.renderPathIgnoreDepth.value);
         }
      }
   }

   @Override
   public void onWorldEvent(WorldEvent var1) {
      if (var1.getWorld() != null && var1.getState() == EventState.POST) {
         this.a();
      }
   }

   @Override
   public void onChunkEvent(ChunkEvent var1) {
      if (this.a != null) {
         ChunkEvent var2 = var1;
         ElytraBehavior var3 = this.a;
         if (var2.isPostPopulate() && var3.a != null) {
            class_2818 var4 = var3.a.world().method_8497(var2.getX(), var2.getZ());
            var3.a.a(var4);
         }
      }
   }

   @Override
   public void onBlockChange(BlockChangeEvent var1) {
      if (this.a != null) {
         this.a.a.a(var1);
      }
   }

   @Override
   public void onReceivePacket(PacketEvent var1) {
      if (this.a != null) {
         this.a.a(var1);
      }
   }

   @Override
   public void onPostTick(TickEvent var1) {
      IBaritoneProcess var2 = super.a.a.mostRecentInControl().orElse(null);
      if (this.a != null && var2 == this) {
         this.a.a(var1);
      }
   }

   private static boolean a(class_2248 var0) {
      return var0 == class_2246.field_10515
         || var0 == class_2246.field_10255
         || var0 == class_2246.field_10266 && Baritone.a().elytraAllowLandOnNetherFortress.value;
   }

   private boolean a(class_2338 var1) {
      return a(super.a.world().method_8320(var1).method_26204());
   }

   public enum State {
      a("Finding spot to jump off"),
      b("Waiting for elytra path"),
      c("Walking to takeoff"),
      d("Begin flying"),
      e("Flying"),
      f("Landing");

      public final String a;

      State(String var3) {
         this.a = var3;
      }
   }

   public static final class WalkOffCalculationContext extends CalculationContext {
      public WalkOffCalculationContext(Baritone var1) {
         super(var1, true);
         super.k = true;
         super.b = 8;
         super.c = 10000;
      }

      @Override
      public final double a(int var1, int var2, int var3, class_2680 var4) {
         return 1000000.0;
      }

      @Override
      public final double b(int var1, int var2, int var3, class_2680 var4) {
         return 1000000.0;
      }

      @Override
      public final double a() {
         return 1000000.0;
      }
   }
}
