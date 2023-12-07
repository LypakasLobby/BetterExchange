package com.lypaka.betterexchange.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.betterexchange.ConfigGetters;
import com.lypaka.betterexchange.ExchangeHandlers.UsePointsForPrize;
import com.lypaka.betterexchange.PointHandlers.Points;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.Map;

public class ExchangeGUI {

    public static Map<Integer, Map<Integer, Integer>> slotMap = new HashMap<>();

    public static void openExchangeMenu (ServerPlayerEntity player, int pageNum) throws ObjectMappingException {

        Map<String, Map<String, String>> buyMap = ConfigGetters.exchangeBuyMap;
        int slots = buyMap.size();
        if (slots > 243) {

            player.sendMessage(FancyText.getFormattedText("&cExceeding maximum slots...somehow!"), player.getUniqueID());
            return;

        }

        // Properly handle more than 45 slots available for buying
        int[] utilBar = new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};

        ChestTemplate template = ChestTemplate.builder(6).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedString("&eExchange Menu Page " + pageNum))
                .build();

        Map<Integer, Integer> miniMap = slotMap.get(pageNum);
        int max = 0;
        int min = 0;
        for (Map.Entry<Integer, Integer> entry : miniMap.entrySet()) {

            min = entry.getKey();
            max = entry.getValue();

        }
        int slotIndex = 0;
        int filledSlots = 0;
        for (int i = min; i < max; i++) {

            if (buyMap.containsKey("Slot-" + i)) {

                page.getTemplate().getSlot(slotIndex).setButton(getSlotButton(buyMap.get("Slot-" + i), player));
                filledSlots++;

            } else {

                page.getTemplate().getSlot(slotIndex).setButton(getAirButton());

            }

            slotIndex++;

        }

        for (int i : utilBar) {

            page.getTemplate().getSlot(i).setButton(getUtilButton(i, player));

        }

        // max of 243 slots (3 for each legendary)
        // max of like 5 and a half pages
        if (pageNum < 6) {

            if (slots > 45) {

                if (filledSlots == 45) {

                    int nextPage = pageNum + 1;
                    page.getTemplate().getSlot(53).setButton(getNextButton(nextPage));

                }

            }

        }

        if (pageNum > 1) {

            int prevPage = pageNum - 1;
            page.getTemplate().getSlot(45).setButton(getPrevButton(prevPage));

        }

        UIManager.openUIForcefully(player, page);

    }

    private static Button getPrevButton (int pageNum) {

        ItemStack prev = ItemStackBuilder.buildFromStringID("minecraft:arrow");
        prev.setDisplayName(FancyText.getFormattedText("&ePrevious Page"));
        return GooeyButton.builder()
                .display(prev)
                .onClick(action -> {

                    try {

                        openExchangeMenu(action.getPlayer(), pageNum);

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                })
                .build();

    }

    private static Button getNextButton (int pageNum) {

        ItemStack next = ItemStackBuilder.buildFromStringID("minecraft:arrow");
        next.setDisplayName(FancyText.getFormattedText("&eNext Page"));
        return GooeyButton.builder()
                .display(next)
                .onClick(action -> {

                    try {

                        openExchangeMenu(action.getPlayer(), pageNum);

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }

                })
                .build();

    }

    // int[] utilBar = new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static Button getUtilButton (int slot, ServerPlayerEntity player) {

        Button button;
        ItemStack itemStack;
        if (slot == 45 || slot == 46 || slot == 48 || slot == 50 || slot == 52 || slot == 53) {

            itemStack = ItemStackBuilder.buildFromStringID("minecraft:red_stained_glass_pane");
            itemStack.setDisplayName(FancyText.getFormattedText(""));
            button = GooeyButton.builder()
                    .display(itemStack)
                    .build();

        } else if (slot == 47) {

            itemStack = ItemStackBuilder.buildFromStringID("pixelmon:soft_sand");
            itemStack.setDisplayName(FancyText.getFormattedText("&eCurrent LP:&a " + Points.getPoints(player.getUniqueID())));
            button = GooeyButton.builder()
                    .display(itemStack)
                    .build();

        } else if (slot == 49) {

            itemStack = ItemStackBuilder.buildFromStringID("minecraft:diamond");
            itemStack.setDisplayName(FancyText.getFormattedText("&eMain Menu"));
            button = GooeyButton.builder()
                    .display(itemStack)
                    .onClick(action -> {

                        MainGUI.openMainGui(action.getPlayer());

                    })
                    .build();

        } else {

            itemStack = ItemStackBuilder.buildFromStringID("minecraft:barrier");
            itemStack.setDisplayName(FancyText.getFormattedText("&cClose"));
            button = GooeyButton.builder()
                    .display(itemStack)
                    .onClick(action -> {

                        UIManager.closeUI(action.getPlayer());

                    })
                    .build();

        }

        return button;

    }

    private static Button getSlotButton (Map<String, String> data, ServerPlayerEntity player) throws ObjectMappingException {

        String listing = data.get("Listing");
        ItemStack displayStack;
        int price = Integer.parseInt(data.get("Price"));
        Pokemon pokemon = null;
        if (PixelmonSpecies.has(listing)) {

            pokemon = PokemonBuilder.builder().species(listing).build();

            if (pokemon != null) {

                if (data.containsKey("Form")) {

                    pokemon.setForm(data.get("Form"));

                }
                if (data.containsKey("Texture")) {

                    pokemon.setPalette(data.get("Texture"));

                }
                if (data.containsKey("Level")) {

                    pokemon.setLevel(Integer.parseInt(data.get("Level")));

                }


                displayStack = SpriteItemHelper.getPhoto(pokemon);
                displayStack.setDisplayName(FancyText.getFormattedText("&a" + pokemon.getLocalizedName()));
                ListNBT lore = new ListNBT();
                lore.add(StringNBT.valueOf(""));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&ePrice: &a" + price))));
                displayStack.getOrCreateChildTag("display").put("Lore", lore);

            } else {

                displayStack = CustomListings.getCustomListingOrNull(listing, price);

            }

        } else {

            displayStack = CustomListings.getCustomListingOrNull(listing, price);

        }

        Button button;
        if (displayStack != null) {

            if (Points.getPoints(player.getUniqueID()) >= price) {

                Pokemon finalPokemon = pokemon;
                button = GooeyButton.builder()
                        .display(displayStack)
                        .onClick(action -> {

                            if (finalPokemon != null) {

                                UsePointsForPrize.redeemPokemon(action.getPlayer(), finalPokemon, price);

                            } else {

                                try {

                                    UsePointsForPrize.redeemListing(action.getPlayer(), listing, price);

                                } catch (ObjectMappingException e) {

                                    e.printStackTrace();

                                }

                            }

                        })
                        .build();

            } else {

                button = GooeyButton.builder()
                        .display(displayStack)
                        .build();

            }

        } else {

            button = GooeyButton.builder()
                    .display(ItemStackBuilder.buildFromStringID("minecraft:air"))
                    .build();

        }

        return button;

    }

    private static Button getAirButton() {

        return GooeyButton.builder()
                .display(ItemStackBuilder.buildFromStringID("minecraft:air"))
                .build();

    }

}
