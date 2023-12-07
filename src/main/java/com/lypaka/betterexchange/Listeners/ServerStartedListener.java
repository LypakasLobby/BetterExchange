package com.lypaka.betterexchange.Listeners;

import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.GUIs.ExchangeGUI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = BetterExchange.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        MinecraftForge.EVENT_BUS.register(new LoginListener());

        Map<Integer, Integer> miniMap = new HashMap<>();
        miniMap.put(0, 45);
        ExchangeGUI.slotMap.put(1, miniMap);
        miniMap = new HashMap<>();
        miniMap.put(46, 91);
        ExchangeGUI.slotMap.put(2, miniMap);
        miniMap = new HashMap<>();
        miniMap.put(92, 137);
        ExchangeGUI.slotMap.put(3, miniMap);
        miniMap = new HashMap<>();
        miniMap.put(138, 183);
        ExchangeGUI.slotMap.put(4, miniMap);
        miniMap = new HashMap<>();
        miniMap.put(184, 229);
        ExchangeGUI.slotMap.put(5, miniMap);
        miniMap = new HashMap<>();
        miniMap.put(230, 243);
        ExchangeGUI.slotMap.put(6, miniMap);

    }

}
