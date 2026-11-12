package com.vex.baritone.api.command.datatypes;

import java.util.stream.Stream;

public interface IDatatype {
   Stream<String> tabComplete(IDatatypeContext var1);
}
