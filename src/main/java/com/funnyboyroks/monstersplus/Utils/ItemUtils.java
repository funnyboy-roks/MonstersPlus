package com.funnyboyroks.monstersplus.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack renameItemStack(ItemStack stack, Component name) {
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack renameItemStack(ItemStack stack, String name) {
        return renameItemStack(stack, LegacyComponentSerializer.legacyAmpersand().deserialize(name));
    }

    public static ItemStack setItemStackLore(ItemStack stack, Component... name) {
        ItemMeta meta = stack.getItemMeta();
        meta.lore(List.of(name));
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack setItemStackLore(ItemStack stack, List<Component> name) {
        ItemMeta meta = stack.getItemMeta();
        meta.lore(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack setItemStackLore(ItemStack stack, String... name) {
        return setItemStackLore(
            stack,
            Arrays
                .stream(name)
                .map(s -> LegacyComponentSerializer.legacyAmpersand().deserialize(s))
                .collect(Collectors.toList())
        );
    }

    public static ItemStack addAllFlags(ItemStack stack) {
        stack.addItemFlags(ItemFlag.values());
        return stack;
    }

}
