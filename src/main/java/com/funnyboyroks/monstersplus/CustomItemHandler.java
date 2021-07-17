package com.funnyboyroks.monstersplus;

import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CustomItemHandler {

    public CustomItemHandler() {

    }

    public CustomItem getCustomItem(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        List<Component> lore = meta.lore();

        if(
            meta.lore().isEmpty() ||
            !((TextComponent) (lore.get(lore.size() - 1))).content().equalsIgnoreCase("MonstersPlus")
        ){
            return null;
        }

        // TODO: WIP: return Arrays.stream(CustomItem.values()).filter((e) -> e.matches(stack)).findFirst().orElse(null);
        return null;
    }

    public boolean isCustomItem(ItemStack stack) {
        return getCustomItem(stack) != null;
    }

}
