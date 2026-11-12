package com.vex.baritone;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.behavior.IBehavior;
import com.vex.baritone.api.event.listener.IEventBus;
import com.vex.baritone.api.process.IBaritoneProcess;
import com.vex.baritone.api.process.IElytraProcess;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.behavior.InventoryBehavior;
import com.vex.baritone.behavior.LookBehavior;
import com.vex.baritone.behavior.PathingBehavior;
import com.vex.baritone.behavior.WaypointBehavior;
import com.vex.baritone.cache.WorldProvider;
import com.vex.baritone.command.manager.CommandManager;
import com.vex.baritone.event.GameEventHandler;
import com.vex.baritone.process.BackfillProcess;
import com.vex.baritone.process.BuilderProcess;
import com.vex.baritone.process.CustomGoalProcess;
import com.vex.baritone.process.ElytraProcess;
import com.vex.baritone.process.ExploreProcess;
import com.vex.baritone.process.FarmProcess;
import com.vex.baritone.process.FollowProcess;
import com.vex.baritone.process.GetToBlockProcess;
import com.vex.baritone.process.InventoryPauserProcess;
import com.vex.baritone.process.MineProcess;
import com.vex.baritone.selection.SelectionManager;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.GuiClick;
import com.vex.baritone.utils.InputOverrideHandler;
import com.vex.baritone.utils.PathingControlManager;
import com.vex.baritone.utils.player.BaritonePlayerContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.class_310;

public class Baritone implements IBaritone {
   private static final ThreadPoolExecutor a = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
   private final class_310 a;
   public final Path a;
   private final GameEventHandler a;
   public final PathingBehavior a;
   public final LookBehavior a;
   public final InventoryBehavior a;
   public final InputOverrideHandler a;
   private final FollowProcess a;
   private final MineProcess a;
   private final GetToBlockProcess a;
   private final CustomGoalProcess a;
   public final BuilderProcess a;
   private final ExploreProcess a;
   private final FarmProcess a;
   public final InventoryPauserProcess a;
   private final IElytraProcess a;
   public final PathingControlManager a;
   public final SelectionManager a;
   public final CommandManager a;
   private final BaritonePlayerContext a;
   public final WorldProvider a;
   public BlockStateInterface a;

   Baritone(class_310 var1) {
      this.a = var1;
      this.a = new GameEventHandler(this);
      this.a = var1.field_1697.toPath().resolve("baritone");
      if (!Files.exists(this.a)) {
         try {
            Files.createDirectories(this.a);
         } catch (IOException var2) {
         }
      }

      this.a = new BaritonePlayerContext(this, var1);
      this.a = this.a(LookBehavior::new);
      this.a = this.a(PathingBehavior::new);
      this.a = this.a(InventoryBehavior::new);
      this.a = this.a(InputOverrideHandler::new);
      this.a(WaypointBehavior::new);
      this.a = new PathingControlManager(this);
      this.a = this.a(FollowProcess::new);
      this.a = this.a(MineProcess::new);
      this.a = this.a(CustomGoalProcess::new);
      this.a = this.a(GetToBlockProcess::new);
      this.a = this.a(BuilderProcess::new);
      this.a = this.a(ExploreProcess::new);
      this.a = this.a(FarmProcess::new);
      this.a = this.a(InventoryPauserProcess::new);
      this.a = this.a(ElytraProcess::a);
      this.a(BackfillProcess::new);
      this.a = new WorldProvider(this);
      this.a = new SelectionManager(this);
      this.a = new CommandManager(this);
   }

   private void a(IBehavior var1) {
      this.a.registerEventListener(var1);
   }

   public final <T extends IBehavior> T a(Function<Baritone, T> var1) {
      IBehavior var2 = (IBehavior)var1.apply(this);
      this.a(var2);
      return (T)var2;
   }

   private <T extends IBaritoneProcess> T a(Function<Baritone, T> var1) {
      IBaritoneProcess var2 = (IBaritoneProcess)var1.apply(this);
      this.a.registerProcess(var2);
      return (T)var2;
   }

   @Override
   public IPlayerContext getPlayerContext() {
      return this.a;
   }

   @Override
   public IEventBus getGameEventHandler() {
      return this.a;
   }

   @Override
   public IElytraProcess getElytraProcess() {
      return this.a;
   }

   @Override
   public void openClick() {
      new Thread(() -> {
         try {
            Thread.sleep(100L);
            this.a.execute(() -> this.a.method_1507(new GuiClick()));
         } catch (Exception var1) {
         }
      }).start();
   }

   public static Settings a() {
      return BaritoneAPI.getSettings();
   }

   public static Executor a() {
      return a;
   }
}
