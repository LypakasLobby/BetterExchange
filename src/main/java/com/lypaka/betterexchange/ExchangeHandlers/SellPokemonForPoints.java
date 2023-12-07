package com.lypaka.betterexchange.ExchangeHandlers;

import com.lypaka.betterexchange.API.ExchangeEvent;
import com.lypaka.betterexchange.GUIs.MainGUI;
import com.lypaka.betterexchange.PointHandlers.Points;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class SellPokemonForPoints {

    public static void tradeIn (ServerPlayerEntity player, Pokemon pixelmon, int slot, int points) throws ObjectMappingException {

        PlayerPartyStorage party = StorageProxy.getParty(player);

        if (party.countPokemon() > 1) {

            ExchangeEvent.SellPokemon event = new ExchangeEvent.SellPokemon(player, pixelmon, points);
            MinecraftForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {

                party.set(slot, null);
                Points.setPoints(player.getUniqueID(), Points.getPoints(player.getUniqueID()) + event.getPointsEarned());
                player.sendMessage(FancyText.getFormattedText("&4You traded in your " + pixelmon.getLocalizedName() + " for " + points + " Legendary Points!"), player.getUniqueID());

            } else {

                player.sendMessage(FancyText.getFormattedText("&c" + pixelmon.getLocalizedName() + " refused to leave!"), player.getUniqueID());

            }

        } else {

            player.sendMessage(FancyText.getFormattedText("&cYou can't trade in your last Pokemon!"), player.getUniqueID());

        }

        MainGUI.openMainGui(player);

    }

}
