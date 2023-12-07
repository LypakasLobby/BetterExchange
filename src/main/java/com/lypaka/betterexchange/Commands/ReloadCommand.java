package com.lypaka.betterexchange.Commands;

import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterExchangeCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "betterexchange.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 1;

                                                    }

                                                }

                                                try {

                                                    BetterExchange.configManager.getConfigNode(1, "Points").setValue(ConfigGetters.pointMap);
                                                    BetterExchange.configManager.save();
                                                    BetterExchange.configManager.load();
                                                    ConfigGetters.load();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterExchange!"), true);

                                                } catch (ObjectMappingException e) {

                                                    e.printStackTrace();

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}
