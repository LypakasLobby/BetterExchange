package com.lypaka.betterexchange.GUIs;

import com.google.common.reflect.TypeToken;
import com.lypaka.betterexchange.BetterExchange;
import com.lypaka.betterexchange.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class CustomListings {

    public static ItemStack getCustomListingOrNull (String name, int price) throws ObjectMappingException {

        ItemStack display = null;
        Map<String, Map<String, String>> customMap = ConfigGetters.customListingsMap;
        if (customMap.containsKey(name)) {

            String id = BetterExchange.configManager.getConfigNode(0, "Special-Listings", name, "Display", "ID").getString();
            display = ItemStackBuilder.buildFromStringID(id);
            if (!BetterExchange.configManager.getConfigNode(0, "Special-Listings", name, "Display", "Metadata").isVirtual()) {

                int meta = BetterExchange.configManager.getConfigNode(0, "Special-Listings", name, "Display", "Metadata").getInt();
                display.setDamage(meta);

            }
            ListNBT lore = new ListNBT();
            lore.add(StringNBT.valueOf(""));
            lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&ePrice: &a" + price))));
            lore.add(StringNBT.valueOf(""));
            List<String> stringLore = BetterExchange.configManager.getConfigNode(0, "Special-Listings", name, "Display", "Lore").getList(TypeToken.of(String.class));
            for (String s : stringLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }

            display.setDisplayName(FancyText.getFormattedText(BetterExchange.configManager.getConfigNode(0, "Special-Listings", name, "Display", "Name").getString()));
            display.getOrCreateChildTag("display").put("Lore", lore);

        }

        return display;

    }

}
