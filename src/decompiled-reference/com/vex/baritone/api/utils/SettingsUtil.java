package com.vex.baritone.api.utils;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;
import java.awt.Color;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.class_1792;
import net.minecraft.class_2248;
import net.minecraft.class_2382;
import net.minecraft.class_2415;
import net.minecraft.class_2470;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_7923;
import net.minecraft.class_6880.class_6883;

public class SettingsUtil {
   public static final String SETTINGS_DEFAULT_NAME = "settings.txt";
   private static final Pattern SETTING_PATTERN = Pattern.compile("^(?<setting>[^ ]+) +(?<value>.+)");

   private static boolean isComment(String var0) {
      return var0.startsWith("#") || var0.startsWith("//");
   }

   private static void forEachLine(Path var0, Consumer<String> var1) {
      String var2;
      try (BufferedReader var5 = Files.newBufferedReader(var0)) {
         while ((var2 = var5.readLine()) != null) {
            if (!var2.isEmpty() && !isComment(var2)) {
               var1.accept(var2);
            }
         }
      }
   }

   public static void readAndApply(Settings var0, String var1) {
      try {
         forEachLine(settingsByName(var1), var1x -> {
            Matcher var2x;
            if (!(var2x = SETTING_PATTERN.matcher(var1x)).matches()) {
               Helper.HELPER.logDirect("Invalid syntax in setting file: " + var1x);
            } else {
               String var3x = var2x.group("setting").toLowerCase();
               String var5 = var2x.group("value");
               if ("allowjumpat256".equals(var3x)) {
                  var3x = "allowjumpatbuildlimit";
               }

               try {
                  parseAndApply(var0, var3x, var5);
               } catch (Exception var4) {
                  Helper.HELPER.logDirect("Unable to parse line " + var1x);
                  var4.printStackTrace();
               }
            }
         });
      } catch (NoSuchFileException var2) {
         Helper.HELPER.logDirect("Baritone settings file not found, resetting.");
      } catch (Exception var3) {
         Helper.HELPER.logDirect("Exception while reading Baritone settings, some settings may be reset to default values!");
         var3.printStackTrace();
      }
   }

   public static synchronized void save(Settings param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IllegalStateException: No successor exists for {Do}:31
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.Statement.getFirstSuccessor(Statement.java:837)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:334)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:181)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.flattenStatement(FlattenStatementsHelper.java:474)
      //   at org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.buildDirectGraph(FlattenStatementsHelper.java:43)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.splitVariables(SFormsConstructor.java:74)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:54)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:317)
      //
      // Bytecode:
      // 00: ldc "settings.txt"
      // 02: invokestatic baritone/api/utils/SettingsUtil.settingsByName (Ljava/lang/String;)Ljava/nio/file/Path;
      // 05: bipush 0
      // 06: anewarray 53
      // 09: invokestatic java/nio/file/Files.newBufferedWriter (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;
      // 0c: astore 1
      // 0d: aload 0
      // 0e: invokestatic baritone/api/utils/SettingsUtil.modifiedSettings (Lbaritone/api/Settings;)Ljava/util/List;
      // 11: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 16: astore 0
      // 17: aload 0
      // 18: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1d: ifeq 3a
      // 20: aload 0
      // 21: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 26: checkcast baritone/api/Settings$Setting
      // 29: astore 2
      // 2a: aload 1
      // 2b: aload 2
      // 2c: invokestatic baritone/api/utils/SettingsUtil.settingToString (Lbaritone/api/Settings$Setting;)Ljava/lang/String;
      // 2f: invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; bsm=java/lang/invoke/StringConcatFactory.makeConcatWithConstants (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "\u0001\n" ]
      // 34: invokevirtual java/io/BufferedWriter.write (Ljava/lang/String;)V
      // 37: goto 17
      // 3a: aload 1
      // 3b: ifnull 57
      // 3e: aload 1
      // 3f: invokevirtual java/io/BufferedWriter.close ()V
      // 42: return
      // 43: astore 0
      // 44: aload 1
      // 45: ifnull 55
      // 48: aload 1
      // 49: invokevirtual java/io/BufferedWriter.close ()V
      // 4c: goto 55
      // 4f: astore 2
      // 50: aload 0
      // 51: aload 2
      // 52: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 55: aload 0
      // 56: athrow
      // 57: return
      // 58: astore 1
      // 59: getstatic baritone/api/utils/Helper.HELPER Lbaritone/api/utils/Helper;
      // 5c: ldc "Exception thrown while saving Baritone settings!"
      // 5e: invokeinterface baritone/api/utils/Helper.logDirect (Ljava/lang/String;)V 2
      // 63: aload 1
      // 64: invokevirtual java/lang/Exception.printStackTrace ()V
      // 67: return
      // try (6 -> 23): 28 java/lang/Throwable
      // try (31 -> 33): 34 java/lang/Throwable
      // try (0 -> 40): 41 java/lang/Exception
   }

   private static Path settingsByName(String var0) {
      return class_310.method_1551().field_1697.toPath().resolve("baritone").resolve(var0);
   }

   public static List<Settings.Setting> modifiedSettings(Settings var0) {
      ArrayList var1 = new ArrayList();
      Iterator var3 = var0.allSettings.iterator();

      while (var3.hasNext()) {
         Settings.Setting var2;
         if ((var2 = (Settings.Setting)var3.next()).value == null) {
            System.out.println("NULL SETTING?" + var2.getName());
         } else if (!var2.isJavaOnly() && var2.value != var2.defaultValue) {
            var1.add(var2);
         }
      }

      return var1;
   }

   public static String settingTypeToString(Settings.Setting var0) {
      return var0.getType().getTypeName().replaceAll("(?:\\w+\\.)+(\\w+)", "$1");
   }

   public static <T> String settingValueToString(Settings.Setting<T> var0, T var1) {
      SettingsUtil.Parser var2;
      if ((var2 = SettingsUtil.Parser.getParser(var0.getType())) == null) {
         throw new IllegalStateException("Missing " + var0.getValueClass() + " " + var0.getName());
      } else {
         return var2.toString(var0.getType(), var1);
      }
   }

   public static String settingValueToString(Settings.Setting var0) {
      return settingValueToString(var0, var0.value);
   }

   public static String settingDefaultToString(Settings.Setting var0) {
      return settingValueToString(var0, var0.defaultValue);
   }

   public static String maybeCensor(int var0) {
      return BaritoneAPI.getSettings().censorCoordinates.value ? "<censored>" : Integer.toString(var0);
   }

   public static String settingToString(Settings.Setting var0) {
      return var0.isJavaOnly() ? var0.getName() : var0.getName() + " " + settingValueToString(var0);
   }

   @Deprecated
   public static boolean javaOnlySetting(Settings.Setting var0) {
      return var0.isJavaOnly();
   }

   public static void parseAndApply(Settings var0, String var1, String var2) {
      Settings.Setting var4;
      if ((var4 = var0.byLowerName.get(var1)) == null) {
         throw new IllegalStateException("No setting by that name");
      }

      Class var5 = var4.getValueClass();
      SettingsUtil.Parser var3;
      Object var6 = (var3 = SettingsUtil.Parser.getParser(var4.getType())).parse(var4.getType(), var2);
      if (!var5.isInstance(var6)) {
         throw new IllegalStateException(var3 + " parser returned incorrect type, expected " + var5 + " got " + var6 + " which is " + var6.getClass());
      }

      var4.value = (T)var6;
   }

   interface ISettingParser<T> {
      T parse(Type var1, String var2);

      String toString(Type var1, T var2);

      boolean accepts(Type var1);
   }

   enum Parser implements SettingsUtil.ISettingParser {
      DOUBLE(Double.class, Double::parseDouble),
      BOOLEAN(Boolean.class, Boolean::parseBoolean),
      INTEGER(Integer.class, Integer::parseInt),
      FLOAT(Float.class, Float::parseFloat),
      LONG(Long.class, Long::parseLong),
      STRING(String.class, String::new),
      MIRROR(class_2415.class, class_2415::valueOf, Enum::name),
      ROTATION(class_2470.class, class_2470::valueOf, Enum::name),
      COLOR(
         Color.class,
         var0 -> new Color(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])),
         var0 -> var0.getRed() + "," + var0.getGreen() + "," + var0.getBlue()
      ),
      VEC3I(
         class_2382.class,
         var0 -> new class_2382(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])),
         var0 -> var0.method_10263() + "," + var0.method_10264() + "," + var0.method_10260()
      ),
      BLOCK(class_2248.class, var0 -> BlockUtils.stringToBlockRequired(var0.trim()), BlockUtils::blockToString),
      ITEM(
         class_1792.class,
         var0 -> class_7923.field_41178.method_10223(class_2960.method_60654(var0.trim())).<class_1792>map(class_6883::comp_349).orElse(null),
         var0 -> class_7923.field_41178.method_10221(var0).toString()
      ),
      LIST,
      MAPPING;

      private final Class<?> cla$$;
      private final Function<String, Object> parser;
      private final Function<Object, String> toString;

      Parser() {
         this.cla$$ = null;
         this.parser = null;
         this.toString = null;
      }

      <T> Parser(Class<T> var3, Function<String, T> var4) {
         this(var3, var4, Object::toString);
      }

      <T> Parser(Class<T> var3, Function<String, T> var4, Function<T, String> var5) {
         this.cla$$ = var3;
         this.parser = var4::apply;
         this.toString = var1x -> (String)var5.apply(var1x);
      }

      @Override
      public Object parse(Type var1, String var2) {
         Object var3;
         Objects.requireNonNull(var3 = this.parser.apply(var2));
         return var3;
      }

      @Override
      public String toString(Type var1, Object var2) {
         return this.toString.apply(var2);
      }

      @Override
      public boolean accepts(Type var1) {
         return var1 instanceof Class && this.cla$$.isAssignableFrom((Class<?>)var1);
      }

      public static SettingsUtil.Parser getParser(Type var0) {
         return Stream.of(values()).filter(var1 -> var1.accepts(var0)).findFirst().orElse(null);
      }
   }
}
