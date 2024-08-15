package com.lypaka.betterexchange.ExchangeHandlers;

import com.google.common.reflect.TypeToken;
import com.lypaka.betterexchange.API.ExchangeEvent;
import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.GUIs.MainGUI;
import com.lypaka.betterexchange.PointHandlers.Points;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PixelmonHelpers;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class UsePointsForPrize {

    public static void redeemListing (ServerPlayerEntity player, String listingName, int points) throws ObjectMappingException {

        ExchangeEvent.BuyCustomListing event = new ExchangeEvent.BuyCustomListing(player, listingName, points);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {

            Points.setPoints(player.getUniqueID(), Points.getPoints(player.getUniqueID()) - event.getPointsSpent());
            List<String> commands = BetterExchange.configManager.getConfigNode(0, "Special-Listings", listingName, "Execute").getList(TypeToken.of(String.class));
            for (String c : commands) {

                player.world.getServer().getCommandManager().handleCommand(
                        player.world.getServer().getCommandSource(),
                        c.replace("%player%", player.getName().getString())
                );

            }
            String msg = BetterExchange.configManager.getConfigNode(0, "Special-Listings", listingName, "Message").getString();
            player.sendMessage(FancyText.getFormattedText(msg), player.getUniqueID());
            BetterExchange.logger.info("Player " + player.getName().getString() + " used " + points + " points to redeem listing " + listingName);
            MainGUI.openMainGui(player);

        }

    }

    public static void redeemPokemon (ServerPlayerEntity player, Pokemon pokemon, int points) {

        ExchangeEvent.BuyPokemonListing event = new ExchangeEvent.BuyPokemonListing(player, pokemon, points);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {

            Points.setPoints(player.getUniqueID(), Points.getPoints(player.getUniqueID()) - event.getPointsSpent());
            PlayerPartyStorage party = StorageProxy.getParty(player);
            Pokemon p = PixelmonHelpers.fixPokemonIVsAndGender(event.getPokemon());
            party.add(p);
            player.sendMessage(FancyText.getFormattedText("&eYou used " + points + " points to redeem a " + event.getPokemon().getLocalizedName() + "!"), player.getUniqueID());
            BetterExchange.logger.info("Player " + player.getName().getString() + " used " + points + " points to redeem a " + event.getPokemon().getLocalizedName());
            MainGUI.openMainGui(player);

        }

    }

}
