package com.vex.baritone.api.command.exception;

import com.vex.baritone.api.command.argument.ICommandArgument;

public abstract class CommandInvalidArgumentException extends CommandErrorMessageException {
   public final ICommandArgument arg;

   protected CommandInvalidArgumentException(ICommandArgument var1, String var2) {
      super(formatMessage(var1, var2));
      this.arg = var1;
   }

   protected CommandInvalidArgumentException(ICommandArgument var1, String var2, Throwable var3) {
      super(formatMessage(var1, var2), var3);
      this.arg = var1;
   }

   private static String formatMessage(ICommandArgument var0, String var1) {
      return String.format("Error at argument #%s: %s", var0.getIndex() == -1 ? "<unknown>" : Integer.toString(var0.getIndex() + 1), var1);
   }
}
