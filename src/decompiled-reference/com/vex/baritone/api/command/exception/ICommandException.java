package com.vex.baritone.api.command.exception;

import com.vex.baritone.api.command.ICommand;
import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.utils.Helper;
import java.util.List;
import net.minecraft.class_124;

public interface ICommandException {
   String getMessage();

   default void handle(ICommand var1, List<ICommandArgument> var2) {
      Helper.HELPER.logDirect(this.getMessage(), class_124.field_1061);
   }
}
