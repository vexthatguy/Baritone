package com.vex.baritone.api.command.argparser;

import com.vex.baritone.api.command.argument.ICommandArgument;
import com.vex.baritone.api.command.registry.Registry;

public interface IArgParserManager {
   <T> IArgParser.Stateless<T> getParserStateless(Class<T> var1);

   <T, S> IArgParser.Stated<T, S> getParserStated(Class<T> var1, Class<S> var2);

   <T> T parseStateless(Class<T> var1, ICommandArgument var2);

   <T, S> T parseStated(Class<T> var1, Class<S> var2, ICommandArgument var3, S var4);

   Registry<IArgParser> getRegistry();
}
