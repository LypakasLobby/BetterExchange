package com.lypaka.betterexchange.Listeners;

import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.ConfigGetters;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = BetterExchange.MOD_ID)
public class ServerShuttingDownListener {

    @SubscribeEvent
    public static void onShuttingDown (FMLServerStoppingEvent event) {

        BetterExchange.configManager.getConfigNode(1, "Points").setValue(ConfigGetters.pointMap);
        BetterExchange.configManager.save();

    }

}
