package com.lypaka.betterexchange.Commands;

import com.lypaka.betterexchange.BetterExchange;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BetterExchange.MOD_ID)
public class BetterExchangeCommand {

    public static final List<String> ALIASES = Arrays.asList("betterexchange", "bexchange", "exchange", "bex");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new MainCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
