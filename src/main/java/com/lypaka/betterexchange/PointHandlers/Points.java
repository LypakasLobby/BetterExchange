package com.lypaka.betterexchange.PointHandlers;

import com.lypaka.betterexchange.ConfigGetters;

import java.util.UUID;

public class Points {

    public static void loadPlayer (UUID uuid) {

        if (!ConfigGetters.pointMap.containsKey(uuid.toString())) {

            ConfigGetters.pointMap.put(uuid.toString(), 0);

        }

    }

    public static int getPoints (UUID uuid) {

        int value = 0;
        if (ConfigGetters.pointMap.containsKey(uuid.toString())) {

            value = ConfigGetters.pointMap.get(uuid.toString());

        }
        return value;

    }

    public static void setPoints (UUID uuid, int value) {

        ConfigGetters.pointMap.put(uuid.toString(), value);

    }

}
