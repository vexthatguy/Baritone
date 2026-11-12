package com.vex.baritone.command.defaults;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.ICommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class DefaultCommands {
   private DefaultCommands() {
   }

   public static List<ICommand> a(IBaritone var0) {
      Objects.requireNonNull(var0);
      ArrayList var1 = new ArrayList<>(
         Arrays.asList(
            new HelpCommand(var0),
            new SetCommand(var0),
            new CommandAlias(var0, Arrays.asList("modified", "mod", "baritone", "modifiedsettings"), "List modified settings", "set modified"),
            new CommandAlias(var0, "reset", "Reset all settings or just one", "set reset"),
            new GoalCommand(var0),
            new GotoCommand(var0),
            new PathCommand(var0),
            new ProcCommand(var0),
            new ETACommand(var0),
            new VersionCommand(var0),
            new RepackCommand(var0),
            new BuildCommand(var0),
            new LitematicaCommand(var0),
            new ComeCommand(var0),
            new AxisCommand(var0),
            new ForceCancelCommand(var0),
            new GcCommand(var0),
            new InvertCommand(var0),
            new TunnelCommand(var0),
            new RenderCommand(var0),
            new FarmCommand(var0),
            new FollowCommand(var0),
            new PickupCommand(var0),
            new ExploreFilterCommand(var0),
            new ReloadAllCommand(var0),
            new SaveAllCommand(var0),
            new ExploreCommand(var0),
            new BlacklistCommand(var0),
            new FindCommand(var0),
            new MineCommand(var0),
            new ClickCommand(var0),
            new SurfaceCommand(var0),
            new ThisWayCommand(var0),
            new WaypointsCommand(var0),
            new CommandAlias(var0, "sethome", "Sets your home waypoint", "waypoints save home"),
            new CommandAlias(var0, "home", "Path to your home waypoint", "waypoints goto home"),
            new SelCommand(var0),
            new ElytraCommand(var0)
         )
      );
      ExecutionControlCommands var2 = new ExecutionControlCommands(var0);
      var1.add(var2.a);
      var1.add(var2.a);
      var1.add(var2.a);
      var1.add(var2.a);
      return Collections.unmodifiableList(var1);
   }
}
