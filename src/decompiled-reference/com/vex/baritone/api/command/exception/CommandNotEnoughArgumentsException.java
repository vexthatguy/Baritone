package com.vex.baritone.api.command.exception;

public class CommandNotEnoughArgumentsException extends CommandErrorMessageException {
   public CommandNotEnoughArgumentsException(int var1) {
      super(String.format("Not enough arguments (expected at least %d)", var1));
   }
}
