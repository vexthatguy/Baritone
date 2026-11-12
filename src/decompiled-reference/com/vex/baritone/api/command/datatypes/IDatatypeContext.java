package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.argument.IArgConsumer;

public interface IDatatypeContext {
   IBaritone getBaritone();

   IArgConsumer getConsumer();
}
