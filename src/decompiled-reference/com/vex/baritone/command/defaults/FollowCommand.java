package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.KeepName;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.EntityClassById;
import com.vex.baritone.api.command.datatypes.IDatatypeFor;
import com.vex.baritone.api.command.datatypes.NearbyPlayer;
import com.vex.baritone.api.command.exception.CommandErrorMessageException;
import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import com.vex.baritone.api.process.IFollowProcess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class FollowCommand extends Command {
   public FollowCommand(Baritone var1) {
      super(var1, "follow");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMin(1);
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      FollowCommand.FollowGroup var7;
      IFollowProcess var10000;
      Predicate var10001;
      if (var2.hasExactlyOne()) {
         var10000 = super.com.vex.baritone.getFollowProcess();
         var10001 = (var7 = var2.getEnum(FollowCommand.FollowGroup.class)).a;
      } else {
         var2.requireMin(2);
         var7 = null;
         FollowCommand.FollowList var3 = var2.getEnum(FollowCommand.FollowList.class);

         while (var2.hasAny()) {
            Object var6;
            if ((var6 = var2.getDatatypeFor(var3.a)) instanceof class_1299) {
               var5.add((class_1299)var6);
            } else if (var6 != null) {
               var4.add((class_1297)var6);
            }
         }

         var10000 = super.com.vex.baritone.getFollowProcess();
         var10001 = var5.isEmpty() ? var4::contains : var1x -> var5.stream().anyMatch(var1xx -> var1x.method_5864().equals(var1xx));
      }

      var10000.follow(var10001);
      if (var7 != null) {
         this.logDirect(String.format("Following all %s", var7.name().toLowerCase(Locale.US)));
      } else if (var5.isEmpty()) {
         if (var4.isEmpty()) {
            throw new FollowCommand.NoEntitiesException();
         }

         this.logDirect("Following these entities:");
         var4.stream().<String>map(class_1297::toString).forEach(this::logDirect);
      } else {
         this.logDirect("Following these types of entities:");
         var5.stream().map(class_7923.field_41177::method_10221).map(Objects::requireNonNull).<String>map(class_2960::toString).forEach(this::logDirect);
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasExactlyOne()) {
         return new TabCompleteHelper().append(FollowCommand.FollowGroup.class).append(FollowCommand.FollowList.class).filterPrefix(var2.getString()).stream();
      }

      try {
         var4 = var2.getEnum(FollowCommand.FollowList.class).a;
      } catch (NullPointerException var3) {
         return Stream.empty();
      }

      while (var2.has(2)) {
         if (var2.peekDatatypeOrNull(var4) == null) {
            return Stream.empty();
         }

         var2.get();
      }

      return var2.tabCompleteDatatype(var4);
   }

   @Override
   public String getShortDesc() {
      return "Follow entity things";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList(
         "The follow command tells Baritone to follow certain kinds of entities.",
         "",
         "Usage:",
         "> follow entities - Follows all entities.",
         "> follow entity <entity1> <entity2> <...> - Follow certain entities (for example 'skeleton', 'horse' etc.)",
         "> follow players - Follow players",
         "> follow player <username1> <username2> <...> - Follow certain players"
      );
   }

   @KeepName
   enum FollowGroup {
      a(class_1309.class::isInstance),
      b(class_1657.class::isInstance);

      final Predicate<class_1297> a;

      FollowGroup(Predicate<class_1297> var3) {
         this.a = var3;
      }
   }

   @KeepName
   enum FollowList {
      a(EntityClassById.INSTANCE),
      b(NearbyPlayer.INSTANCE);

      final IDatatypeFor a;

      FollowList(IDatatypeFor var3) {
         this.a = var3;
      }
   }

   public static class NoEntitiesException extends CommandErrorMessageException {
      protected NoEntitiesException() {
         super("No valid entities in range!");
      }
   }
}
