package com.vex.baritone.selection;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.selection.ISelection;
import com.vex.baritone.api.selection.ISelectionManager;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.LinkedList;
import java.util.ListIterator;
import net.minecraft.class_2350;

public class SelectionManager implements ISelectionManager {
   private final LinkedList<ISelection> a = new LinkedList<>();
   private ISelection[] a = new ISelection[0];

   public SelectionManager(Baritone var1) {
      new SelectionRenderer(var1, this);
   }

   private void a() {
      this.a = this.a.toArray(new ISelection[0]);
   }

   @Override
   public synchronized ISelection addSelection(ISelection var1) {
      this.a.add(var1);
      this.a();
      return var1;
   }

   @Override
   public ISelection addSelection(BetterBlockPos var1, BetterBlockPos var2) {
      return this.addSelection(new Selection(var1, var2));
   }

   @Override
   public synchronized ISelection removeSelection(ISelection var1) {
      this.a.remove(var1);
      this.a();
      return var1;
   }

   @Override
   public synchronized ISelection[] removeAllSelections() {
      ISelection[] var1 = this.getSelections();
      this.a.clear();
      this.a();
      return var1;
   }

   @Override
   public ISelection[] getSelections() {
      return this.a;
   }

   @Override
   public synchronized ISelection getOnlySelection() {
      return this.a.size() == 1 ? this.a.peekFirst() : null;
   }

   @Override
   public ISelection getLastSelection() {
      return this.a.peekLast();
   }

   @Override
   public synchronized ISelection expand(ISelection var1, class_2350 var2, int var3) {
      ListIterator var4 = this.a.listIterator();

      while (var4.hasNext()) {
         ISelection var5;
         if ((var5 = (ISelection)var4.next()) == var1) {
            var4.remove();
            var4.add(var5.expand(var2, var3));
            this.a();
            return (ISelection)var4.previous();
         }
      }

      return null;
   }

   @Override
   public synchronized ISelection contract(ISelection var1, class_2350 var2, int var3) {
      ListIterator var4 = this.a.listIterator();

      while (var4.hasNext()) {
         ISelection var5;
         if ((var5 = (ISelection)var4.next()) == var1) {
            var4.remove();
            var4.add(var5.contract(var2, var3));
            this.a();
            return (ISelection)var4.previous();
         }
      }

      return null;
   }

   @Override
   public synchronized ISelection shift(ISelection var1, class_2350 var2, int var3) {
      ListIterator var4 = this.a.listIterator();

      while (var4.hasNext()) {
         ISelection var5;
         if ((var5 = (ISelection)var4.next()) == var1) {
            var4.remove();
            var4.add(var5.shift(var2, var3));
            this.a();
            return (ISelection)var4.previous();
         }
      }

      return null;
   }
}
