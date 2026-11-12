package com.vex.baritone.api.command.exception;

public abstract class CommandException extends Exception implements ICommandException {
   protected CommandException(String var1) {
      super(var1);
   }

   protected CommandException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
