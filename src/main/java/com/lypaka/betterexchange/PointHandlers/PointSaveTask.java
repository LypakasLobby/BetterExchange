package com.lypaka.betterexchange.PointHandlers;

import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.ConfigGetters;

import java.util.Timer;
import java.util.TimerTask;

public class PointSaveTask {

    private static Timer timer;

    public static void startTimer() {

        if (timer != null) {

            timer.cancel();

        }
        if (ConfigGetters.saveInterval <= 0) return;
        timer = new Timer();
        long interval = ConfigGetters.saveInterval * 1000L;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                BetterExchange.configManager.getConfigNode(1, "Points").setValue(ConfigGetters.pointMap);
                BetterExchange.configManager.save();
                BetterExchange.logger.info("Saved player point values!");

            }

        }, 0, interval);

    }

}
