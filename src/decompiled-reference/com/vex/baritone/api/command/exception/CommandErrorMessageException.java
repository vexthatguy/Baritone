package com.vex.baritone.api.command.exception;

public abstract class CommandErrorMessageException extends CommandException {
   public CommandErrorMessageException(String var1) {
      super(var1);
   }

   protected CommandErrorMessageException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
