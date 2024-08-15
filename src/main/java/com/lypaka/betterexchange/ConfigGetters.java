package com.lypaka.betterexchange;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static String commandPermission;
    public static boolean exchangeSystemEnabled;
    public static Map<String, Map<String, String>> exchangeBuyMap;
    public static Map<String, Map<String, String>> exchangeSellMap;
    public static int saveInterval;
    public static Map<String, Map<String, String>> customListingsMap;
    public static Map<String, Integer> pointMap;

    public static List<String> blacklist;

    public static int mainGUIRows;
    public static String mainGUITitle;
    public static String mainGUIBorderID;
    public static String mainGUIBorderSlots;
    public static String mainGUIExchangeButtonID;
    public static String mainGUIExchangeButtonName;
    public static int mainGUIExchangeButtonSlot;
    public static String mainGUIPartyButtonID;
    public static String mainGUIPartyButtonName;
    public static int mainGUIPartyButtonSlot;
    public static String mainGUIPointsDisplayButtonID;
    public static String mainGUIPointsDisplayButtonName;
    public static int mainGUIPointsDisplayButtonSlot;

    public static int partyGUIRows;
    public static String partyGUITitle;
    public static String partyGUIBorderID;
    public static String partyGUIBorderSlots;
    public static List<String> partyGUISpriteLore;
    public static String partyGUIMainMenuButtonID;
    public static String partyGUIMainMenuButtonName;
    public static int partyGUIMainMenuButtonSlot;
    public static String partyGUINoPokemonButtonID;
    public static String partyGUINoPokemonButtonName;
    public static Map<String, Map<String, String>> partyGUIPartySlotsMap;

    public static void load (boolean reload) throws ObjectMappingException {

        commandPermission = BetterExchange.configManager.getConfigNode(0, "Command-Permission").getString();
        exchangeSystemEnabled = BetterExchange.configManager.getConfigNode(0, "Enabled").getBoolean();
        exchangeBuyMap = new HashMap<>();
        exchangeBuyMap = BetterExchange.configManager.getConfigNode(0, "Exchange", "Buy").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        exchangeSellMap = new HashMap<>();
        exchangeSellMap = BetterExchange.configManager.getConfigNode(0, "Exchange", "Sell").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        saveInterval = 600;
        if (BetterExchange.configManager.getConfigNode(0, "Save-Interval").isVirtual()) {

            BetterExchange.configManager.getConfigNode(0, "Save-Interval").setValue(saveInterval);
            BetterExchange.configManager.getConfigNode(0, "Save-Interval").setComment("Sets how often, in seconds, BetterExchange saves player point values to the config. 0 to disable");
            BetterExchange.configManager.save();

        } else {

            saveInterval = BetterExchange.configManager.getConfigNode(0, "Save-Interval").getInt();

        }
        customListingsMap = new HashMap<>();
        customListingsMap = BetterExchange.configManager.getConfigNode(0, "Special-Listings").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        if (!reload) {

            pointMap = BetterExchange.configManager.getConfigNode(1, "Points").getValue(new TypeToken<Map<String, Integer>>() {});

        }

        blacklist = BetterExchange.configManager.getConfigNode(2, "Blacklist").getList(TypeToken.of(String.class));

        mainGUIRows = BetterExchange.configManager.getConfigNode(3, "Settings", "Rows").getInt();
        mainGUITitle = BetterExchange.configManager.getConfigNode(3, "Settings", "Title").getString();
        mainGUIBorderID = BetterExchange.configManager.getConfigNode(3, "Slots", "Border", "ID").getString();
        mainGUIBorderSlots = BetterExchange.configManager.getConfigNode(3, "Slots", "Border", "Slots").getString();
        mainGUIExchangeButtonID = BetterExchange.configManager.getConfigNode(3, "Slots", "Exchange-Button", "Display-ID").getString();
        mainGUIExchangeButtonName = BetterExchange.configManager.getConfigNode(3, "Slots", "Exchange-Button", "Display-Name").getString();
        mainGUIExchangeButtonSlot = BetterExchange.configManager.getConfigNode(3, "Slots", "Exchange-Button", "Slot").getInt();
        mainGUIPartyButtonID = BetterExchange.configManager.getConfigNode(3, "Slots", "Party-Button", "Display-ID").getString();
        mainGUIPartyButtonName = BetterExchange.configManager.getConfigNode(3, "Slots", "Party-Button", "Display-Name").getString();
        mainGUIPartyButtonSlot = BetterExchange.configManager.getConfigNode(3, "Slots", "Party-Button", "Slot").getInt();
        mainGUIPointsDisplayButtonID = BetterExchange.configManager.getConfigNode(3, "Slots", "Points-Display", "Display-ID").getString();
        mainGUIPointsDisplayButtonName = BetterExchange.configManager.getConfigNode(3, "Slots", "Points-Display", "Display-Name").getString();
        mainGUIPointsDisplayButtonSlot = BetterExchange.configManager.getConfigNode(3, "Slots", "Points-Display", "Slot").getInt();

        partyGUIRows = BetterExchange.configManager.getConfigNode(4, "Settings", "Rows").getInt();
        partyGUITitle = BetterExchange.configManager.getConfigNode(4, "Settings", "Title").getString();
        partyGUIBorderID = BetterExchange.configManager.getConfigNode(4, "Slots", "Border", "ID").getString();
        partyGUIBorderSlots = BetterExchange.configManager.getConfigNode(4, "Slots", "Border", "Slots").getString();
        partyGUISpriteLore = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "Lore").getList(TypeToken.of(String.class));
        partyGUIMainMenuButtonID = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "Main-Menu", "Display-ID").getString();
        partyGUIMainMenuButtonName = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "Main-Menu", "Display-Name").getString();
        partyGUIMainMenuButtonSlot = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "Main-Menu", "Slot").getInt();
        partyGUINoPokemonButtonID = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "No-Pokemon", "Display-ID").getString();
        partyGUINoPokemonButtonName = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "No-Pokemon", "Display-Name").getString();
        partyGUIPartySlotsMap = BetterExchange.configManager.getConfigNode(4, "Slots", "Party", "Pokemon").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

    }

}
