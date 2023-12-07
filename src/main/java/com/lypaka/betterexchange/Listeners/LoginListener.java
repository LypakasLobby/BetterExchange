package com.lypaka.betterexchange.Listeners;

import com.lypaka.betterexchange.PointHandlers.Points;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LoginListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) {

        Points.loadPlayer(event.getPlayer().getUniqueID());

    }

}
