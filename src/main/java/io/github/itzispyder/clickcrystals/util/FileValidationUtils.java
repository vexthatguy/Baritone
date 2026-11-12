package io.github.itzispyder.clickcrystals.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class FileValidationUtils {
   public static boolean validate(File file) {
      try {
         return !file.getParentFile().exists() && !file.getParentFile().mkdirs() ? false : file.exists() || file.createNewFile();
      } catch (Exception ex) {
         return false;
      }
   }

   public static boolean quickWrite(File file, String string) {
      if (file != null && validate(file)) {
         try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(string);
            bw.flush();
            bw.close();
            fw.close();
            return true;
         } catch (Exception ex) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static String quickRead(File file) {
      try {
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);
         String read = String.join(" ", br.lines().toArray(String[]::new));
         br.close();
         fr.close();
         return read;
      } catch (Exception ex) {
         return "";
      }
   }
}
