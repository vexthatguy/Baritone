package com.vex.baritone.pathing.movement;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.pathing.movement.IMovement;
import com.vex.baritone.api.pathing.movement.MovementStatus;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.api.utils.RotationUtils;
import com.vex.baritone.api.utils.VecUtils;
import com.vex.baritone.api.utils.input.Input;
import com.vex.baritone.behavior.PathingBehavior;
import com.vex.baritone.utils.BlockStateInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_1540;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;

public abstract class Movement implements IMovement, MovementHelper {
   public static final class_2350[] a = new class_2350[]{
      class_2350.field_11043, class_2350.field_11035, class_2350.field_11034, class_2350.field_11039, class_2350.field_11033
   };
   public final IBaritone a;
   public final IPlayerContext a;
   private MovementState a;
   public final BetterBlockPos a;
   public final BetterBlockPos b;
   public final BetterBlockPos[] a;
   public final BetterBlockPos c;
   public Double a;
   public List<class_2338> a;
   private List<class_2338> c;
   public List<class_2338> b;
   private Set<BetterBlockPos> a;
   public Boolean a;

   public Movement(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3, BetterBlockPos[] var4, BetterBlockPos var5) {
      MovementState var10001 = new MovementState();
      MovementStatus var7 = MovementStatus.PREPPING;
      MovementState var6 = var10001;
      var10001.a = var7;
      this.a = var6;
      this.a = null;
      this.c = null;
      this.b = null;
      this.a = null;
      this.a = var1;
      this.a = var1.getPlayerContext();
      this.a = var2;
      this.b = var3;
      this.a = var4;
      this.c = var5;
   }

   public Movement(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3, BetterBlockPos[] var4) {
      this(var1, var2, var3, var4, null);
   }

   @Override
   public double getCost() {
      return this.a;
   }

   public abstract double a(CalculationContext var1);

   protected abstract Set<BetterBlockPos> a();

   public final Set<BetterBlockPos> b() {
      if (this.a == null) {
         this.a = this.a();
         Objects.requireNonNull(this.a);
      }

      return this.a;
   }

   protected final boolean a() {
      return this.b().contains(this.a.playerFeet()) || this.b().contains(((PathingBehavior)this.a.getPathingBehavior()).a());
   }

   @Override
   public MovementStatus update() {
      this.a.player().method_31549().field_7479 = false;
      this.a = this.a(this.a);
      if (MovementHelper.e(this.a, this.a.playerFeet())) {
         double var10001 = this.b.y;
         if (this.a.player().method_73189().field_1351 < var10001 + 0.6) {
            this.a.a(Input.JUMP, true);
         }
      }

      if (this.a.player().method_5757()) {
         this.a.getSelectedBlock().ifPresent(var1 -> MovementHelper.a(this.a, BlockStateInterface.a(this.a, var1)));
         this.a.a(Input.CLICK_LEFT, true);
      }

      Optional.ofNullable(this.a.a.a).ifPresent(var1 -> this.a.getLookBehavior().updateTarget(var1, this.a.a.a));
      this.a.getInputOverrideHandler().clearAllKeys();
      this.a.a.forEach((var1, var2) -> this.a.getInputOverrideHandler().setInputForceState(var1, var2));
      this.a.a.clear();
      if (this.a.a.isComplete()) {
         this.a.getInputOverrideHandler().clearAllKeys();
      }

      return this.a.a;
   }

   public boolean a(MovementState var1) {
      if (var1.a == MovementStatus.WAITING) {
         return true;
      }

      BetterBlockPos[] var2 = this.a;
      int var3 = this.a.length;

      for (int var4 = 0; var4 < var3; var4++) {
         BetterBlockPos var5 = var2[var4];
         if (!this.a.world().method_18467(class_1540.class, new class_238(0.0, 0.0, 0.0, 1.0, 1.1, 1.0).method_996(var5)).isEmpty()
            && Baritone.a().pauseMiningForFallingBlocks.value) {
            return false;
         }

         if (!MovementHelper.a(this.a, var5)) {
            MovementHelper.a(this.a, BlockStateInterface.a(this.a, (class_2338)var5));
            Optional var6;
            if (!(var6 = RotationUtils.reachable(this.a, var5, this.a.playerController().getBlockReachDistance())).isPresent()) {
               var1.a(
                  new MovementState.MovementTarget(
                     RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(var5), this.a.playerRotations()), true
                  )
               );
               var1.a(Input.CLICK_LEFT, true);
               return false;
            }

            Rotation var7 = (Rotation)var6.get();
            var1.a(new MovementState.MovementTarget(var7, true));
            if (this.a.isLookingAt(var5) || this.a.playerRotations().isReallyCloseTo(var7)) {
               var1.a(Input.CLICK_LEFT, true);
            }

            return false;
         }
      }

      return true;
   }

   @Override
   public boolean safeToCancel() {
      return this.b(this.a);
   }

   protected boolean b(MovementState var1) {
      return true;
   }

   @Override
   public BetterBlockPos getSrc() {
      return this.a;
   }

   @Override
   public BetterBlockPos getDest() {
      return this.b;
   }

   @Override
   public void reset() {
      MovementState var10001 = new MovementState();
      MovementStatus var2 = MovementStatus.PREPPING;
      MovementState var1 = var10001;
      var10001.a = var2;
      this.a = var1;
   }

   public MovementState a(MovementState var1) {
      if (!this.a(var1)) {
         MovementStatus var2 = MovementStatus.PREPPING;
         MovementState var3 = var1;
         var1.a = var2;
         return var3;
      }

      if (var1.a == MovementStatus.PREPPING) {
         var1.a = MovementStatus.WAITING;
      }

      if (var1.a == MovementStatus.WAITING) {
         var1.a = MovementStatus.RUNNING;
      }

      return var1;
   }

   @Override
   public class_2338 getDirection() {
      return this.getDest().method_10059(this.getSrc());
   }

   @Override
   public boolean calculatedWhileLoaded() {
      return this.a;
   }

   @Override
   public void resetBlockCache() {
      this.a = null;
      this.c = null;
      this.b = null;
   }

   public List<class_2338> a(BlockStateInterface var1) {
      if (this.a != null) {
         return this.a;
      }

      ArrayList var2 = new ArrayList();
      BetterBlockPos[] var3 = this.a;
      int var4 = this.a.length;

      for (int var5 = 0; var5 < var4; var5++) {
         BetterBlockPos var6 = var3[var5];
         if (!MovementHelper.a(var1, var6.x, var6.y, var6.z)) {
            var2.add(var6);
         }
      }

      this.a = var2;
      return var2;
   }

   public final List<class_2338> b(BlockStateInterface var1) {
      if (this.c != null) {
         return this.c;
      }

      ArrayList var2 = new ArrayList();
      if (this.c != null && !MovementHelper.b(var1, this.c.x, this.c.y, this.c.z)) {
         var2.add(this.c);
      }

      this.c = var2;
      return var2;
   }

   public List<class_2338> c(BlockStateInterface var1) {
      if (this.b == null) {
         this.b = new ArrayList<>();
      }

      return this.b;
   }
}
