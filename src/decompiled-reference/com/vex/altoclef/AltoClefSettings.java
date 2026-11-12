package com.vex.altoclef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

/**
 * AltoClef-specific Baritone settings/hooks.
 *
 * In the original AltoClef fork of Baritone this class lives in
 * {@code baritone.altoclef} and is wired into Baritone's pathing internals.
 * This build keeps it as a self-contained holder so the AltoClef mod can read
 * and write the same state; the advanced pathing integrations are not applied.
 */
public class AltoClefSettings {
   private static final AltoClefSettings INSTANCE = new AltoClefSettings();

   private final Object breakMutex = new Object();
   private final Object placeMutex = new Object();
   private final Object propertiesMutex = new Object();
   private final Object globalHeuristicMutex = new Object();

   private final HashSet<class_2338> blocksToAvoidBreaking = new HashSet<>();
   private final List<Predicate<class_2338>> breakAvoiders = new ArrayList<>();
   private final List<Predicate<class_2338>> placeAvoiders = new ArrayList<>();
   private final HashSet<class_1792> protectedItems = new HashSet<>();
   private final List<Predicate<class_2338>> forceWalkOnPredicates = new ArrayList<>();
   private final List<Predicate<class_2338>> forceAvoidWalkThroughPredicates = new ArrayList<>();
   private final List<BiPredicate<class_2680, class_1799>> forceUseToolPredicates = new ArrayList<>();
   private final List<BiPredicate<class_2680, class_1799>> forceSaveToolPredicates = new ArrayList<>();
   private final List<BiFunction<Double, class_2338, Double>> globalHeuristics = new ArrayList<>();

   private boolean interactionPaused = false;
   private boolean canWalkOnEndPortal = false;
   private boolean flowingWaterPassAllowed = true;
   private boolean placeBucketButDontFall = false;

   private AltoClefSettings() {
   }

   public static AltoClefSettings getInstance() {
      return INSTANCE;
   }

   public Object getBreakMutex() {
      return breakMutex;
   }

   public Object getPlaceMutex() {
      return placeMutex;
   }

   public Object getPropertiesMutex() {
      return propertiesMutex;
   }

   public Object getGlobalHeuristicMutex() {
      return globalHeuristicMutex;
   }

   public void avoidBlockBreak(Predicate<class_2338> predicate) {
      synchronized (breakMutex) {
         breakAvoiders.add(predicate);
      }
   }

   public void avoidBlockPlace(Predicate<class_2338> predicate) {
      synchronized (placeMutex) {
         placeAvoiders.add(predicate);
      }
   }

   public boolean shouldAvoidBreaking(class_2338 pos) {
      synchronized (breakMutex) {
         if (blocksToAvoidBreaking.contains(pos)) {
            return true;
         }

         for (Predicate<class_2338> predicate : breakAvoiders) {
            if (predicate.test(pos)) {
               return true;
            }
         }
      }
      return false;
   }

   public boolean shouldAvoidPlacingAt(class_2338 pos) {
      synchronized (placeMutex) {
         for (Predicate<class_2338> predicate : placeAvoiders) {
            if (predicate.test(pos)) {
               return true;
            }
         }
      }
      return false;
   }

   public HashSet<class_2338> getBlocksToAvoidBreaking() {
      return blocksToAvoidBreaking;
   }

   public List<Predicate<class_2338>> getBreakAvoiders() {
      return breakAvoiders;
   }

   public List<Predicate<class_2338>> getPlaceAvoiders() {
      return placeAvoiders;
   }

   public HashSet<class_1792> getProtectedItems() {
      return protectedItems;
   }

   public List<Predicate<class_2338>> getForceWalkOnPredicates() {
      return forceWalkOnPredicates;
   }

   public List<Predicate<class_2338>> getForceAvoidWalkThroughPredicates() {
      return forceAvoidWalkThroughPredicates;
   }

   public List<BiPredicate<class_2680, class_1799>> getForceUseToolPredicates() {
      return forceUseToolPredicates;
   }

   public List<BiPredicate<class_2680, class_1799>> getForceSaveToolPredicates() {
      return forceSaveToolPredicates;
   }

   public List<BiFunction<Double, class_2338, Double>> getGlobalHeuristics() {
      return globalHeuristics;
   }

   public boolean isFlowingWaterPassAllowed() {
      return flowingWaterPassAllowed;
   }

   public void setFlowingWaterPassAllowed(boolean allowed) {
      this.flowingWaterPassAllowed = allowed;
   }

   public void setFlowingWaterPass(boolean allowed) {
      this.flowingWaterPassAllowed = allowed;
   }

   public void allowSwimThroughLava(boolean allowed) {
   }

   public boolean isInteractionPaused() {
      return interactionPaused;
   }

   public void setInteractionPaused(boolean paused) {
      this.interactionPaused = paused;
   }

   public boolean isCanWalkOnEndPortal() {
      return canWalkOnEndPortal;
   }

   public void canWalkOnEndPortal(boolean allowed) {
      this.canWalkOnEndPortal = allowed;
   }

   public boolean isConfigurePlaceBucketButDontFall() {
      return placeBucketButDontFall;
   }

   public void configurePlaceBucketButDontFall(boolean value) {
      this.placeBucketButDontFall = value;
   }
}
