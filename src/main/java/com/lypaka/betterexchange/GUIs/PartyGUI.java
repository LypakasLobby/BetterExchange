package com.lypaka.betterexchange.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.betterexchange.ConfigGetters;
import com.lypaka.betterexchange.ExchangeHandlers.SellPokemonForPoints;
import com.lypaka.betterexchange.PointHandlers.PointValidation;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class PartyGUI {

    private final ServerPlayerEntity player;

    public PartyGUI (ServerPlayerEntity player) {

        this.player = player;

    }

    public void openMenu() throws ObjectMappingException {

        ChestTemplate template = ChestTemplate.builder(ConfigGetters.partyGUIRows).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString(ConfigGetters.partyGUITitle))
                .build();

        int max = ConfigGetters.partyGUIRows * 9;
        String[] borderSlots = ConfigGetters.partyGUIBorderSlots.split(", ");
        for (String b : borderSlots) {

            page.getTemplate().getSlot(Integer.parseInt(b)).setButton(getBorder());

        }
        page.getTemplate().getSlot(ConfigGetters.partyGUIMainMenuButtonSlot).setButton(getMainMenu());
        PlayerPartyStorage storage = StorageProxy.getParty(this.player);
        for (int i = 0; i < 6; i++) {

            Pokemon pokemon = storage.get(i);
            int slot = Integer.parseInt(ConfigGetters.partyGUIPartySlotsMap.get("Party-Slot-" + i).get("Slot"));
            boolean passes = passes(pokemon);
            String displayName = passes ?
                    FancyText.getFormattedString(ConfigGetters.partyGUIPartySlotsMap.get("Party-Slot-" + i).get("Display-Name")
                            .replace("%pokemon%", pokemon.getLocalizedName())) :
                    FancyText.getFormattedString(ConfigGetters.partyGUINoPokemonButtonName);
            ItemStack item = passes ? SpriteItemHelper.getPhoto(pokemon) : ItemStackBuilder.buildFromStringID(ConfigGetters.partyGUINoPokemonButtonID);
            item.setDisplayName(FancyText.getFormattedText(displayName));
            Button pokeButton;
            if (passes) {

                int points = PointValidation.getPointWorth(pokemon);
                int finalI = i;
                List<String> displayLore = ConfigGetters.partyGUISpriteLore;
                ListNBT lore = new ListNBT();
                for (String l : displayLore) {

                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(l.replace("%points%", String.valueOf(points))))));

                }
                item.getOrCreateChildTag("display").put("Lore", lore);
                pokeButton = GooeyButton.builder()
                        .display(item)
                        .onClick(click -> {

                            try {

                                SellPokemonForPoints.tradeIn(this.player, pokemon, finalI, points);

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        })
                        .build();

            } else {

                pokeButton = GooeyButton.builder()
                        .display(item)
                        .build();

            }
            page.getTemplate().getSlot(slot).setButton(pokeButton);

        }

        UIManager.openUIForcefully(this.player, page);

    }

    private boolean passes (Pokemon pokemon) {

        if (pokemon == null) {

            return false;

        } else {

            boolean passes = false;
            for (String s : ConfigGetters.blacklist) {

                if (s.equalsIgnoreCase("!legendary")) {

                    if (PixelmonSpecies.getLegendaries(true).contains(pokemon.getSpecies().getDex())) {

                        passes = true;

                    }

                }
                if (!passes) {

                    if (s.equalsIgnoreCase("legendary")) {

                        if (!PixelmonSpecies.getLegendaries(true).contains(pokemon.getSpecies().getDex())) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.equalsIgnoreCase("!mythical")) {

                        if (PixelmonSpecies.getMythicals().contains(pokemon.getSpecies().getDex())) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.equalsIgnoreCase("mythical")) {

                        if (!PixelmonSpecies.getMythicals().contains(pokemon.getSpecies().getDex())) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.equalsIgnoreCase("!ultrabeast")) {

                        if (PixelmonSpecies.getUltraBeasts().contains(pokemon.getSpecies().getDex())) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.equalsIgnoreCase("ultrabeast")) {

                        if (!PixelmonSpecies.getUltraBeasts().contains(pokemon.getSpecies().getDex())) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.contains("!generation")) {

                        int generation = Integer.parseInt(s.replace("!generation", ""));
                        int pokemonGeneration = pokemon.getSpecies().getGeneration();
                        if (generation == pokemonGeneration) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.contains("generation")) {

                        int generation = Integer.parseInt(s.replace("generation", ""));
                        int pokemonGeneration = pokemon.getSpecies().getGeneration();
                        if (generation != pokemonGeneration) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.contains("!shiny")) {

                        if (pokemon.isShiny()) {

                            passes = true;

                        }

                    }

                }
                if (!passes) {

                    if (s.contains("shiny")) {

                        if (!pokemon.isShiny()) {

                            passes = true;

                        }

                    }

                }

                if (s.equalsIgnoreCase(pokemon.getLocalizedName())) {

                    return false;

                }

            }

            return passes;

        }

    }

    private Button getMainMenu() {

        ItemStack diamond = ItemStackBuilder.buildFromStringID(ConfigGetters.partyGUIMainMenuButtonID);
        diamond.setDisplayName(FancyText.getFormattedText(ConfigGetters.partyGUIMainMenuButtonName));
        return GooeyButton.builder()
                .display(diamond)
                .onClick(click -> {

                    MainGUI.openMainGui(click.getPlayer());

                })
                .build();

    }

    private Button getBorder() {

        ItemStack glass = ItemStackBuilder.buildFromStringID(ConfigGetters.partyGUIBorderID);
        glass.setDisplayName(FancyText.getFormattedText(""));
        return GooeyButton.builder()
                .display(glass)
                .build();

    }

}
