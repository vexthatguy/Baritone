package com.vex.baritone.api.command.exception;

import com.vex.baritone.api.command.ICommand;
import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.utils.Helper;
import java.util.List;

public class CommandNotFoundException extends CommandException {
   public final String command;

   public CommandNotFoundException(String var1) {
      super(String.format("Command not found: %s", var1));
      this.command = var1;
   }

   @Override
   public void handle(ICommand var1, List<ICommandArgument> var2) {
      Helper.HELPER.logDirect(this.getMessage());
   }
}
