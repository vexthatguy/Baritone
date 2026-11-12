package com.vex.baritone.api.command;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.utils.IPlayerContext;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command implements ICommand {
   public IBaritone baritone;
   public IPlayerContext ctx;
   protected final List<String> names;

   public Command(IBaritone var1, String... var2) {
      this.names = Collections.unmodifiableList(Stream.of(var2).map(var0 -> var0.toLowerCase(Locale.US)).collect(Collectors.toList()));
      this.baritone = var1;
      this.ctx = var1.getPlayerContext();
   }

   @Override
   public final List<String> getNames() {
      return this.names;
   }
}
