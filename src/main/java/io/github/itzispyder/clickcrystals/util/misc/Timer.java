package io.github.itzispyder.clickcrystals.util.misc;

public class Timer {
   public static final long MILLIS_IN_SECOND = 1000L;
   public static final long MILLIS_IN_MINUTE = 60000L;
   public static final long MILLIS_IN_HOUR = 3600000L;
   public static final long MILLIS_IN_DAY = 86400000L;
   private long start = System.currentTimeMillis();

   private Timer() {
   }

   public static Timer start() {
      return new Timer();
   }

   public static Timer.End zero() {
      return new Timer.End(0L);
   }

   public Timer.End end() {
      return new Timer.End(this.start);
   }

   public static class End {
      private final long start;
      private final long end = System.currentTimeMillis();

      private End(long start) {
         this.start = start;
      }

      public long timePassed() {
         return this.end - this.start;
      }

      public String getStamp(boolean day, boolean hr, boolean min, boolean sec, boolean ms) {
         long time = this.timePassed();
         String stamp = "";
         if (day) {
            long l = (long)Math.floor(time / 8.64E7);
            time -= l * 86400000L;
            if (l > 0L) {
               stamp = stamp + l + "d";
            }
         }

         if (hr) {
            long l = (long)Math.floor(time / 3600000.0);
            time -= l * 3600000L;
            if (l > 0L) {
               stamp = stamp + " " + l + "hr";
            }
         }

         if (min) {
            long l = (long)Math.floor(time / 60000.0);
            time -= l * 60000L;
            if (l > 0L) {
               stamp = stamp + " " + l + "min";
            }
         }

         if (sec) {
            long l = (long)Math.floor(time / 1000.0);
            time -= l * 1000L;
            if (l > 0L) {
               stamp = stamp + " " + l + "sec";
            }
         }

         if (ms && time > 0L) {
            stamp = stamp + " " + time + "ms";
         }

         return stamp.trim();
      }

      public String getStampStandard() {
         return this.getStamp(false, true, true, false, false);
      }

      public String getStampLogger() {
         return this.getStamp(false, true, true, true, false);
      }

      public String getStampPrecise() {
         return this.getStamp(false, false, true, true, true);
      }

      public String getStampFull() {
         return this.getStamp(true, true, true, true, true);
      }
   }
}
