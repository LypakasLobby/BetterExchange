package com.lypaka.betterexchange.Commands;

import com.lypaka.betterexchange.ConfigGetters;
import com.lypaka.betterexchange.GUIs.MainGUI;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MainCommand {

    public MainCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterExchangeCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .executes(c -> {

                                if (ConfigGetters.exchangeSystemEnabled) {

                                    if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                        ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                        if (!ConfigGetters.commandPermission.equalsIgnoreCase("")) {

                                            if (!PermissionHandler.hasPermission(player, ConfigGetters.commandPermission)) {

                                                player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                return 1;

                                            }

                                        }

                                        MainGUI.openMainGui(player);

                                    }

                                } else {

                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cThe exchange menu is currently disabled!"));
                                    return 1;

                                }

                                return 0;

                            })
            );

        }

    }

}
