package com.vex.baritone.api;

import com.vex.baritone.api.behavior.ILookBehavior;
import com.vex.baritone.api.behavior.IPathingBehavior;
import com.vex.baritone.api.cache.IWorldProvider;
import com.vex.baritone.api.command.manager.ICommandManager;
import com.vex.baritone.api.event.listener.IEventBus;
import com.vex.baritone.api.pathing.calc.IPathingControlManager;
import com.vex.baritone.api.process.IBuilderProcess;
import com.vex.baritone.api.process.ICustomGoalProcess;
import com.vex.baritone.api.process.IElytraProcess;
import com.vex.baritone.api.process.IExploreProcess;
import com.vex.baritone.api.process.IFarmProcess;
import com.vex.baritone.api.process.IFollowProcess;
import com.vex.baritone.api.process.IGetToBlockProcess;
import com.vex.baritone.api.process.IMineProcess;
import com.vex.baritone.api.selection.ISelectionManager;
import com.vex.baritone.api.utils.IInputOverrideHandler;
import com.vex.baritone.api.utils.IPlayerContext;

public interface IBaritone {
   IPathingBehavior getPathingBehavior();

   ILookBehavior getLookBehavior();

   IFollowProcess getFollowProcess();

   IMineProcess getMineProcess();

   IBuilderProcess getBuilderProcess();

   IExploreProcess getExploreProcess();

   IFarmProcess getFarmProcess();

   ICustomGoalProcess getCustomGoalProcess();

   IGetToBlockProcess getGetToBlockProcess();

   IElytraProcess getElytraProcess();

   IWorldProvider getWorldProvider();

   IPathingControlManager getPathingControlManager();

   IInputOverrideHandler getInputOverrideHandler();

   IPlayerContext getPlayerContext();

   IEventBus getGameEventHandler();

   ISelectionManager getSelectionManager();

   ICommandManager getCommandManager();

   void openClick();
}
