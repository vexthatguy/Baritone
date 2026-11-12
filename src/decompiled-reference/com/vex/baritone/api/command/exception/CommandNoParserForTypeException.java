package com.vex.baritone.api.command.exception;

public class CommandNoParserForTypeException extends CommandUnhandledException {
   public CommandNoParserForTypeException(Class<?> var1) {
      super(String.format("Could not find a handler for type %s", var1.getSimpleName()));
   }
}
