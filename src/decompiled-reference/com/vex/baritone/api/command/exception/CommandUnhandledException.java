package com.vex.baritone.api.command.exception;

import com.vex.baritone.api.command.ICommand;
import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.utils.Helper;
import java.util.List;

public class CommandUnhandledException extends RuntimeException implements ICommandException {
   public CommandUnhandledException(String var1) {
      super(var1);
   }

   public CommandUnhandledException(Throwable var1) {
      super(var1);
   }

   @Override
   public void handle(ICommand var1, List<ICommandArgument> var2) {
      Helper.HELPER.logUnhandledException(this);
   }
}
