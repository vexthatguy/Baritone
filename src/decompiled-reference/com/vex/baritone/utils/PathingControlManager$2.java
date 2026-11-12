package com.vex.baritone.utils;

import com.vex.baritone.api.process.PathingCommandType;

// $VF: synthetic class
class PathingControlManager$2 {
   static {
      try {
         a[PathingCommandType.SET_GOAL_AND_PAUSE.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         a[PathingCommandType.REQUEST_PAUSE.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         a[PathingCommandType.CANCEL_AND_SET_GOAL.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         a[PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         a[PathingCommandType.REVALIDATE_GOAL_AND_PATH.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }

      try {
         a[PathingCommandType.SET_GOAL_AND_PATH.ordinal()] = 6;
      } catch (NoSuchFieldError var0) {
      }
   }
}
