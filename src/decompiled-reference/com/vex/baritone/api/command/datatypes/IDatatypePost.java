package com.vex.baritone.api.command.datatypes;

public interface IDatatypePost<T, O> extends IDatatype {
   T apply(IDatatypeContext var1, O var2);
}
