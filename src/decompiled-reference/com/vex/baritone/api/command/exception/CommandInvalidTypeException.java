package com.vex.baritone.api.command.exception;

import com.vex.baritone.api.command.argument.ICommandArgument;

public class CommandInvalidTypeException extends CommandInvalidArgumentException {
   public CommandInvalidTypeException(ICommandArgument var1, String var2) {
      super(var1, String.format("Expected %s", var2));
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, Throwable var3) {
      super(var1, String.format("Expected %s", var2), var3);
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, String var3) {
      super(var1, String.format("Expected %s, but got %s instead", var2, var3));
   }

   public CommandInvalidTypeException(ICommandArgument var1, String var2, String var3, Throwable var4) {
      super(var1, String.format("Expected %s, but got %s instead", var2, var3), var4);
   }
}
