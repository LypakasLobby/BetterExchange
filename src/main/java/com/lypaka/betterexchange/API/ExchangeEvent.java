package com.lypaka.betterexchange.API;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public abstract class ExchangeEvent extends Event {

    private final ServerPlayerEntity player;

    protected ExchangeEvent (ServerPlayerEntity player) {

        this.player = player;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    @Cancelable
    public static class SellPokemon extends ExchangeEvent {

        private final Pokemon pokemon;
        private int pointsEarned;

        public SellPokemon (ServerPlayerEntity player, Pokemon pokemon, int pointsEarned) {

            super(player);
            this.pokemon = pokemon;
            this.pointsEarned = pointsEarned;

        }

        public Pokemon getPokemon() {

            return this.pokemon;

        }

        public int getPointsEarned() {

            return this.pointsEarned;

        }

        public void setPointsEarned (int pointsEarned) {

            this.pointsEarned = pointsEarned;

        }

    }

    @Cancelable
    public static class BuyPokemonListing extends ExchangeEvent {

        private Pokemon pokemon;
        private int pointsSpent;

        public BuyPokemonListing (ServerPlayerEntity player, Pokemon pokemon, int pointsSpent) {

            super(player);
            this.pokemon = pokemon;
            this.pointsSpent = pointsSpent;

        }

        public Pokemon getPokemon() {

            return this.pokemon;

        }

        public void setPokemon (Pokemon pokemon) {

            this.pokemon = pokemon;

        }

        public int getPointsSpent() {

            return this.pointsSpent;

        }

        public void setPointsSpent (int pointsSpent) {

            this.pointsSpent = pointsSpent;

        }

    }

    @Cancelable
    public static class BuyCustomListing extends ExchangeEvent {

        private final String listing;
        private int pointsSpent;

        public BuyCustomListing (ServerPlayerEntity player, String listing, int pointsSpent) {

            super(player);
            this.listing = listing;
            this.pointsSpent = pointsSpent;

        }

        public String getListing() {

            return this.listing;

        }

        public int getPointsSpent() {

            return this.pointsSpent;

        }

        public void setPointsSpent (int pointsSpent) {

            this.pointsSpent = pointsSpent;

        }

    }

}
