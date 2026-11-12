package com.vex.baritone.api.command.datatypes;

public interface IDatatypeFor<T> extends IDatatype {
   T get(IDatatypeContext var1);
}
