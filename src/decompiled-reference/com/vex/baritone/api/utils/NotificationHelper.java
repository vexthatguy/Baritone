package com.vex.baritone.api.utils;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;

public class NotificationHelper {
   private static TrayIcon trayIcon;

   public static void notify(String var0, boolean var1) {
      if (SystemUtils.IS_OS_WINDOWS) {
         windows(var0, var1);
      } else if (SystemUtils.IS_OS_MAC_OSX) {
         mac(var0);
      } else {
         if (SystemUtils.IS_OS_LINUX) {
            linux(var0);
         }
      }
   }

   private static void windows(String var0, boolean var1) {
      if (SystemTray.isSupported()) {
         try {
            if (trayIcon == null) {
               SystemTray var2 = SystemTray.getSystemTray();
               Image var3 = Toolkit.getDefaultToolkit().createImage("");
               (trayIcon = new TrayIcon(var3, "Baritone")).setImageAutoSize(true);
               trayIcon.setToolTip("Baritone");
               var2.add(trayIcon);
            }

            trayIcon.displayMessage("Baritone", var0, var1 ? MessageType.ERROR : MessageType.INFO);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      } else {
         System.out.println("SystemTray is not supported");
      }
   }

   private static void mac(String var0) {
      ProcessBuilder var1;
      (var1 = new ProcessBuilder()).command("osascript", "-e", "display notification \"" + var0 + "\" with title \"Baritone\"");

      try {
         var1.start();
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   private static void linux(String var0) {
      ProcessBuilder var1;
      (var1 = new ProcessBuilder()).command("notify-send", "-a", "Baritone", var0);

      try {
         var1.start();
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }
}
