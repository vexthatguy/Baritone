package com.vex.baritone.behavior;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.behavior.ILookBehavior;
import com.vex.baritone.api.behavior.look.IAimProcessor;
import com.vex.baritone.api.behavior.look.ITickableAimProcessor;
import com.vex.baritone.api.event.events.PacketEvent;
import com.vex.baritone.api.event.events.PlayerUpdateEvent;
import com.vex.baritone.api.event.events.RotationMoveEvent;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.events.WorldEvent;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.api.utils.Rotation;
import com.vex.baritone.behavior.look.ForkableRandom;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.minecraft.class_2828;
import net.minecraft.class_2828.class_2830;
import net.minecraft.class_2828.class_2831;

public final class LookBehavior extends Behavior implements ILookBehavior {
   public LookBehavior.Target a;
   public Rotation a;
   private Rotation b;
   public final LookBehavior.AimProcessor a;
   private final Deque<Float> a;
   private final Deque<Float> b;

   public LookBehavior(Baritone var1) {
      super(var1);
      this.a = new LookBehavior.AimProcessor(var1.getPlayerContext());
      this.a = new ArrayDeque<>();
      this.b = new ArrayDeque<>();
   }

   @Override
   public final void updateTarget(Rotation var1, boolean var2) {
      this.a = new LookBehavior.Target(var1, LookBehavior.Target.Mode.a(super.a, var2));
   }

   @Override
   public final IAimProcessor getAimProcessor() {
      return this.a;
   }

   @Override
   public final void onTick(TickEvent var1) {
      if (var1.getType() == TickEvent.Type.IN) {
         this.a.tick();
      }
   }

   @Override
   public final void onPlayerUpdate(PlayerUpdateEvent var1) {
      if (this.a != null) {
         switch (var1.getState()) {
            case PRE:
               if (this.a.a == LookBehavior.Target.Mode.b) {
                  return;
               }

               this.b = new Rotation(super.a.player().method_36454(), super.a.player().method_36455());
               Rotation var2 = this.a.peekRotation(this.a.a);
               super.a.player().method_36456(var2.getYaw());
               super.a.player().method_36457(var2.getPitch());
               return;
            case POST:
               if (this.b != null) {
                  this.a.addLast(this.a.a.getYaw());

                  while (this.a.size() > Baritone.a().smoothLookTicks.value) {
                     this.a.removeFirst();
                  }

                  this.b.addLast(this.a.a.getPitch());

                  while (this.b.size() > Baritone.a().smoothLookTicks.value) {
                     this.b.removeFirst();
                  }

                  if (this.a.a == LookBehavior.Target.Mode.a) {
                     super.a.player().method_36456(this.b.getYaw());
                     super.a.player().method_36457(this.b.getPitch());
                  } else if (super.a.player().method_6128() ? Baritone.a().elytraSmoothLook.value : Baritone.a().smoothLook.value) {
                     super.a.player().method_36456((float)this.a.stream().mapToDouble(var0 -> var0.floatValue()).average().orElse(this.b.getYaw()));
                     if (super.a.player().method_6128()) {
                        super.a.player().method_36457((float)this.b.stream().mapToDouble(var0 -> var0.floatValue()).average().orElse(this.b.getPitch()));
                     }
                  }

                  this.b = null;
               }

               this.a = null;
         }
      }
   }

   @Override
   public final void onSendPacket(PacketEvent var1) {
      if (var1.getPacket() instanceof class_2828) {
         class_2828 var2;
         if ((var2 = (class_2828)var1.getPacket()) instanceof class_2831 || var2 instanceof class_2830) {
            this.a = new Rotation(var2.method_12271(0.0F), var2.method_12270(0.0F));
         }
      }
   }

   @Override
   public final void onWorldEvent(WorldEvent var1) {
      this.a = null;
      this.a = null;
   }

   @Override
   public final void onPlayerRotationMove(RotationMoveEvent var1) {
      if (this.a != null) {
         Rotation var2 = this.a.peekRotation(this.a.a);
         var1.setYaw(var2.getYaw());
         var1.setPitch(var2.getPitch());
      }
   }

   public abstract static class AbstractAimProcessor implements ITickableAimProcessor {
      protected final IPlayerContext a;
      private final ForkableRandom a;
      private double a;
      private double b;

      public AbstractAimProcessor(IPlayerContext var1) {
         this.a = var1;
         this.a = new ForkableRandom();
      }

      AbstractAimProcessor(LookBehavior.AbstractAimProcessor var1) {
         this.a = var1.a;
         ForkableRandom var2 = var1.a;
         this.a = new ForkableRandom(Arrays.copyOf(var2.a, 4));
         this.a = var1.a;
         this.b = var1.b;
      }

      @Override
      public final Rotation peekRotation(Rotation var1) {
         Rotation var2 = this.a();
         float var3 = var1.getYaw();
         float var4;
         if ((var4 = var1.getPitch()) == var2.getPitch()) {
            var4 = var4 < -20.0F ? var4 + 1.0F : (var4 > 10.0F ? var4 - 1.0F : var4);
         }

         var3 = (float)(var3 + this.a);
         float var5 = (float)(var4 + this.b);
         return new Rotation(this.a(var2.getYaw(), var3), this.a(var2.getPitch(), var5)).clamp();
      }

      @Override
      public final void tick() {
         this.a = (this.a.a() - 0.5) * Baritone.a().randomLooking.value;
         this.b = (this.a.a() - 0.5) * Baritone.a().randomLooking.value;
         double var1;
         if (Math.abs(var1 = this.a.a() - 0.5) < 0.1) {
            var1 *= 4.0;
         }

         this.a = this.a + var1 * Baritone.a().randomLooking113.value;
      }

      @Override
      public final void advance(int var1) {
         for (int var2 = 0; var2 < var1; var2++) {
            this.tick();
         }
      }

      @Override
      public Rotation nextRotation(Rotation var1) {
         var1 = this.peekRotation(var1);
         this.tick();
         return var1;
      }

      @Override
      public final ITickableAimProcessor fork() {
         return new LookBehavior$AbstractAimProcessor$1(this, this);
      }

      protected abstract Rotation a();

      private float a(float var1, float var2) {
         var2 -= var1;
         double var3 = this.a(var2);
         return var1 + this.a(var3);
      }

      private double a(float var1) {
         float var2 = this.a(1.0);
         return Math.round(var1 / var2);
      }

      private float a(double var1) {
         double var3 = (Double)this.a.minecraft().field_1690.method_42495().method_41753() * 0.6F + 0.2F;
         return (float)(var1 * var3 * var3 * var3 * 8.0) * 0.15F;
      }
   }

   public static final class AimProcessor extends LookBehavior.AbstractAimProcessor {
      public AimProcessor(IPlayerContext var1) {
         super(var1);
      }

      @Override
      protected final Rotation a() {
         return super.a.playerRotations();
      }
   }

   public static class Target {
      public final Rotation a;
      public final LookBehavior.Target.Mode a;

      public Target(Rotation var1, LookBehavior.Target.Mode var2) {
         this.a = var1;
         this.a = var2;
      }

      enum Mode {
         c,
         a,
         b;

         static LookBehavior.Target.Mode a(IPlayerContext var0, boolean var1) {
            Settings var2;
            boolean var3 = (var2 = Baritone.a()).antiCheatCompatibility.value;
            boolean var4 = var2.blockFreeLook.value;
            if (var0.player().method_6128()) {
               return var2.elytraFreeLook.value ? a : c;
            }

            if (var2.freeLook.value) {
               if (var1) {
                  return var4 ? a : c;
               } else {
                  return var3 ? a : b;
               }
            } else {
               return c;
            }
         }
      }
   }
}
