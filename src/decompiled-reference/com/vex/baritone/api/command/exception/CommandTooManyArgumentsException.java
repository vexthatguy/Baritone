package com.vex.baritone.api.command.exception;

public class CommandTooManyArgumentsException extends CommandErrorMessageException {
   public CommandTooManyArgumentsException(int var1) {
      super(String.format("Too many arguments (expected at most %d)", var1));
   }
}
