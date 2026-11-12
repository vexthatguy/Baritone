package com.vex.baritone.launch.mixins;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.event.events.TabCompleteEvent;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_342;
import net.minecraft.class_4717;
import net.minecraft.class_4717.class_464;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_4717.class)
public class MixinCommandSuggestionHelper {
   @Shadow
   @Final
   class_342 field_21599;
   @Shadow
   @Final
   private List<String> field_21607;
   @Shadow
   private ParseResults field_21610;
   @Shadow
   private CompletableFuture<Suggestions> field_21611;
   @Shadow
   private class_464 field_21612;
   @Shadow
   boolean field_21614;

   @Inject(method = "updateCommandInfo", at = @At("HEAD"), cancellable = true)
   private void preUpdateSuggestion(CallbackInfo var1) {
      String var2 = this.field_21599.method_1882().substring(0, Math.min(this.field_21599.method_1882().length(), this.field_21599.method_1881()));
      TabCompleteEvent var3 = new TabCompleteEvent(var2);
      BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onPreTabComplete(var3);
      if (var3.isCancelled()) {
         var1.cancel();
      } else {
         if (var3.completions != null) {
            var1.cancel();
            this.field_21610 = null;
            if (this.field_21614) {
               return;
            }

            this.field_21599.method_1887(null);
            this.field_21612 = null;
            this.field_21607.clear();
            if (var3.completions.length == 0) {
               this.field_21611 = Suggestions.empty();
            } else {
               StringRange var4 = StringRange.between(var2.lastIndexOf(" ") + 1, var2.length());
               List var6 = Stream.of(var3.completions).map(var1x -> new Suggestion(var4, var1x)).collect(Collectors.toList());
               Suggestions var5 = new Suggestions(var4, var6);
               this.field_21611 = new CompletableFuture<>();
               this.field_21611.complete(var5);
            }

            ((class_4717)this).method_23920(true);
         }
      }
   }
}
