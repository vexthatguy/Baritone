package com.vex.baritone.api.command;

import com.vex.baritone.api.command.argparser.IArgParserManager;

public interface ICommandSystem {
   IArgParserManager getParserManager();
}
