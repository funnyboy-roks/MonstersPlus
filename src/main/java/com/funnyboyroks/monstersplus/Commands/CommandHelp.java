package com.funnyboyroks.monstersplus.Commands;

import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Utils.TextUtils;
import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class CommandHelp implements TabCompleter, CommandExecutor {

    private static final double ENCH_XP = 12.5;
    private static final double REPAIR_XP = 15;


    private static final Map<String, String> topics =
        new ImmutableMap.Builder<String, String>()
            .put("jobs", "&(gold)Jobs: &(white)"
                + "Choosing a job allows you to earn money by doing various activities. "
                + "Each job grants unique features only obtainable for each profession. "
                + "Each player may have up to 3 jobs. Certain jobs allow players to get custom enchantments and disguises. "
                + "To learn more about jobs type: &(red)"
                + "/jobs, /jobs help, /jobs browse, /jobs join, /faq jobs explist"
                + "/faq survivalist, /faq warrior, /faq miner, /faq fisherman, /faq witchdoctor,"
                + "/faq farmer, /faq enchanter, /faq builder, /faq blacksmith, /faq customenchant, /faq disguise, /faq bombs")
            .put("survivalist", "&(gold)Survivalist: &(white)"
                + "The Survivalist receives money by breaking trees, "
                + "building wooden structures, killing animals, and killing certain monsters. &(aqua)"
                + "Unique abilities: Custom Arrow Recipes. &(white)To learn more type: "
                + "&(red)/faq customarrows, /faq arrowrecipes, /jobs info survivalist")
            .put("miner", "&(gold)Miner: &(white)"
                + "The Miner receives money by collecting precious ore such as diamond. &(aqua)"
                + "Unique Abilities: Ore Radar. &(white)To learn more type: &(red)"
                + "/jobs info miner, /faq oreradar")
            .put("warrior", "&(gold)Warrior: &(white)"
                + "The Warrior receives money for all monsters and animals. &(aqua)"
                + "Unique Abilities: Monster Tracking, Custom Arrow Recipes, challenge. &(white)"
                + "To learn more type: &(red)"
                + "/jobs info warrior, /faq tracking, /faq customarrows, /faq challenge")
            .put("fisherman", "Fisherman: &(white)"
                + "The Fisherman receives money for fishing various items and cooking fish. As fishermen level "
                + "they are able to fish many different items ranging from food to emeralds and monster spawners. "
                + "Higher level fishermen will also be able to catch many of the same item at once. "
                + "&(aqua)Unique Abilities: Custom Fishing &(white)"
                + "To learn more type:&(red) /jobs info fisherman, /faq fishing")
            .put("witchdoctor", "&(gold)Witchdoctor: &(white)"
                + "The Witchdoctor receives money and experience for brewing potions and crafting fireworks. "
                + "Witchdoctors unlock a special brewing technique that allows them the ability to brew unique "
                + "potions such as blindness, wither, confusion, jump, and dig speed. High level Witchdoctors "
                + "unlock the ability to amplify, extend, and stir potions. Stiring the potion allows for multiple "
                + "potion effects to be added onto a single potion. &(aqua)"
                + "Unique Abilities: Custom Brewing &(white)To learn more type: &(red)"
                + "/jobs info witchdoctor, /faq brewing, /faq brewing process, /faq brewing list")
            .put("farmer", "&(gold)Farmer: &(white)"
                + "Farmers receive money by creating farms to harvest crops. "
                + "Farmers unlock the ability to track animals, create spawn eggs, and other custom recipes. &(aqua)"
                + "Unique Abilities: Animal Tracking, Custom Farmer Recipes, Spawn Egg Crafting"
                + "&(white)To learn more type: &(red)"
                + "/jobs info farmer, /faq tracking, /faq farmer recipes")
            .put("enchanter", "&(gold)Enchanter: &(white)"
                + "Enchanters gain money by enchanting items, they receive an exp bonus based "
                + "on the item that they enchant. Enchanters also gain "
                + ENCH_XP + " exp for each level of enchantment that they use. Similarly, Enchanters receive "
                + REPAIR_XP + " exp for each level used to rename an item. "
                + "Enchanters gain the ability to use the command /jobenchant, which allows them to "
                + "directly enchant an item. Enchanters can also create every custom enchantment available. "
                + "&(aqua)Unique Abilities: Direct Enchanting (/jobenchant), All Custom Enchantments (/customenchant)&(white)"
                + "To learn more type: &(red)"
                + "/jobs info enchanter, /faq jobenchant, /faq customenchant, /faq powerstones")
            .put("builder", "Builders earn money and experience by placing certain blocks on the ground. "
                + "As builders gain levels they use custom recipes to create new blocks, such as "
                + "Chiseled Bricks, Mossy Stone Bricks, and End Portals. They can also change "
                + "the types of Mob Spawners. Builders can spawn EnderDragons in The End. "
                + "Builders can use commands to place blocks from farther away (/builder).&(aqua)"
                + "Unique Abilities: Custom Block Recipes, Mob Spawner Changing, EnderDragon Spawning "
                + "&(white)To learn more type: &(redO"
                + "/jobs info builder, /faq builder recipes, /faq powerstones, /faq EnderDragonSpawning, /builder")
            .put("blacksmith", "Blacksmith: &(white)"
                + "Blacksmiths receive money for crafting various armors, and weapons. "
                + "Alternatively, Blacksmiths can gain money by smelting gold and iron. "
                + "As a bonus, Blacksmiths gain " + REPAIR_XP + " exp for each level "
                + "used to repair an item. &(aqua)Unique Abilities: &(white)"
                + "To learn more type: &(red)/jobs info blacksmith")
            .put("disguises", "&(gold)Disguise: &(white)"
                + "As you level up your jobs you get access to more types of disguises. "
                + "Your disguise will be disabled when engaged in PVP or if you type /disguise stop. "
                + "&(white)To learn more type: &(red)"
                + "/disguise, /disguise display, /disguise display available")
            .put("customarrows", getCustomArrowText())
            .build();

    private static String getCustomArrowText() {
        StringBuilder sb = new StringBuilder(
            "&(gold)Custom Arrows: &(white)"
                + "Custom arrows are recipes granted to Survivalists and Warriors. You can cycle through the arrows in "
                + "your inventory by left clicking with a bow."
        );

        for (CustomItem arrow : CustomItem.getEndingWith("ARROW")) {
            sb.append("&(aqua)")
                .append(arrow.getName())
                .append(" - &(white)")
                .append(arrow.getDescription())
                .append(" (")
                .append(arrow.getJob())
                .append(" level ")
                .append(arrow.getLevel())
                .append(")");
        }

        sb.append("&(white)To find the recipes type: &(red)/faq arrowrecipes");
        return sb.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        boolean xpList = args.length >= 2 && args[1].equalsIgnoreCase("explist");

        if (!topics.containsKey(args[1].toLowerCase()) && !xpList) {
            return false;
        }

        TextUtils.sendFormatted(sender, topics.get(args[1].toLowerCase()));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
