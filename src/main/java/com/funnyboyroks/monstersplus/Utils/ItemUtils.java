package com.funnyboyroks.monstersplus.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack renameItemStack(ItemStack stack, Component name) {
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack renameItemStack(ItemStack stack, Object o) {
        if(o instanceof String) {
            return renameItemStack(stack, o.toString(), false);
        }

        if(o instanceof Component) {
            return renameItemStack(stack, (Component) o);
        }
        return null;
    }

    public static ItemStack renameItemStack(ItemStack stack, String name, boolean translate) {
        return renameItemStack(stack, translate ? ChatColor.translateAlternateColorCodes('&', "&r" + name) : Component.text(name));
    }

    public static ItemStack renameItemStack(ItemStack stack, String name) {
        return renameItemStack(stack, name, false);
    }

    public static ItemStack setItemStackLore(ItemStack stack, Component... name) {
        ItemMeta meta = stack.getItemMeta();
        meta.lore(List.of(name));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack setItemStackLore(ItemStack stack, List<Component> lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack setItemStackLore(ItemStack stack, String... lore) {
        return setItemStackLore(
            stack,
            Arrays
                .stream(lore)
                .map(s -> ChatColor.translateAlternateColorCodes('&', "&r" + s))
                .map(Component::text)
                .collect(Collectors.toList())
        );
    }

    public static ItemStack addAllFlags(ItemStack stack) {
        stack.addItemFlags(ItemFlag.values());
        return stack;
    }

    /**
     * Drop an item at a given location
     *
     * @param loc        Location
     * @param stack      The ItemStack to drop
     * @param randOffset Should there be a random offset
     */
    public static void dropItem(Location loc, ItemStack stack, boolean randOffset) {
        if (randOffset) {
            loc.getWorld().dropItemNaturally(loc, stack);
        } else {
            loc.getWorld().dropItem(loc, stack);
        }
    }

    public static void dropItem(Location loc, ItemStack stack) {
        dropItem(loc, stack, true);
    }

    public static void forceEnchant(ItemStack stack, Enchantment ench, int level) {
        stack.addUnsafeEnchantment(ench, level);
    }

    public static ItemStack item(Material mat, String name, int count) {
        ItemStack stack = new ItemStack(mat, count);
        renameItemStack(stack, name, true);
        return stack;
    }

    public static ItemStack item(Material mat, String name) {
        return item(mat, name, 1);
    }

    public static ItemStack spawner(EntityType type) {
        ItemStack stack = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) stack.getItemMeta();
        CreatureSpawner cs = (CreatureSpawner) meta.getBlockState();

        cs.setSpawnedType(type);
        meta.setBlockState(cs);
        stack.setItemMeta(meta);
        return stack;

    }

    public static ItemStack spawner(EntityType type, String name) {
        return renameItemStack(spawner(type), name, true);
    }

    public static String getName(ItemStack stack) {
        return
            stack.getItemMeta().displayName() == null ?
                "" :
                ChatColor.stripColor(LegacyComponentSerializer.legacySection().serialize(stack.getItemMeta().displayName()));

    }

    public static List<String> getLore(ItemStack stack) {
        if(stack.getItemMeta() == null || stack.getItemMeta().getLore() == null) return Collections.emptyList();
        return stack.getItemMeta()
            .lore()
            .stream()
            .map(l -> ((TextComponent) l))
            .map(LegacyComponentSerializer.legacySection()::serialize)
            .map(ChatColor::stripColor)
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }

    public static boolean matchesPowerstone(ItemStack stack) {
        return getLore(stack).contains("Powerstone");
    }

    public static void changeAmount(ItemStack stack, int delta) {
        stack.setAmount(stack.getAmount() + delta);
    }

    public static boolean enchantConflicts(Enchantment enchant, ItemStack stack) {
        for(Enchantment e : stack.getEnchantments().keySet()) {
            if(e.conflictsWith(enchant)) return false;
        }
        return true;
    }
}
