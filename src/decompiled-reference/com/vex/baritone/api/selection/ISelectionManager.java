package com.vex.baritone.api.selection;

import com.vex.baritone.api.utils.BetterBlockPos;
import net.minecraft.class_2350;

public interface ISelectionManager {
   ISelection addSelection(ISelection var1);

   ISelection addSelection(BetterBlockPos var1, BetterBlockPos var2);

   ISelection removeSelection(ISelection var1);

   ISelection[] removeAllSelections();

   ISelection[] getSelections();

   ISelection getOnlySelection();

   ISelection getLastSelection();

   ISelection expand(ISelection var1, class_2350 var2, int var3);

   ISelection contract(ISelection var1, class_2350 var2, int var3);

   ISelection shift(ISelection var1, class_2350 var2, int var3);
}
