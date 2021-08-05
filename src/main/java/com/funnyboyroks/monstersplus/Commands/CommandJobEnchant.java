package com.funnyboyroks.monstersplus.Commands;

import com.funnyboyroks.monstersplus.Data.structs.CustomItem;
import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.funnyboyroks.monstersplus.Jobs.Enchanter.EnchantList;
import com.funnyboyroks.monstersplus.Utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandJobEnchant implements TabCompleter, CommandExecutor {

    public static Paginate ENCHANTS = null;

    public CommandJobEnchant() {
        ENCHANTS = new Paginate(
            Utils.enumValues(EnchantList.values()),
            "Enchant Types",
            10,
            "/jobenchant display "
        );
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            TextUtils.sendFormatted(sender, "&(red)You must be in-game to use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) return false;
        if ("display".equalsIgnoreCase(args[0])) return displayEnchants(sender, args[1]);
        if (args.length == 1) return false;
        String enchantName = Utils.toEnumName(args[0]);
        int level = Utils.safeParseInt(args[1]);
        EnchantList enchant;
        try {
            enchant = EnchantList.valueOf(enchantName);
        } catch (IllegalArgumentException ex) {
            TextUtils.sendFormatted(sender, "&(red)Invalid enchant name, use {&(dark_red)/jobenchant display} to see them all.");
            return true;
        }

        EnchantList.EnchantRecipe recipe = enchant.getRecipe(level);
        if (recipe == null) {
            TextUtils.sendFormatted(sender, "&(red)Invalid enchant level.");
            return true;
        }
        int jobLevel = PlayerUtils.getJobLvl(player, JobType.ENCHANTER);
        if (recipe.jobLevel > jobLevel) {
            TextUtils.sendFormatted(
                sender,
                "&(red)This enchantment is only avaliable for a &(aqua)level %0 %1",
                recipe.jobLevel,
                JobType.ENCHANTER
            );
            return true;
        }
        if(recipe.xpCost > player.getLevel()) {
            TextUtils.sendFormatted(
                sender,
                "&(red)You do not have enough EXP for this enchantment, Level: {&(gold)%0} Required: {&(gold)%1}",
                player.getLevel(),
                recipe.xpCost
            );
            return true;
        }

        PlayerInventory inv = player.getInventory();
        ItemStack main = inv.getItemInMainHand();

        if(main.getType().isEmpty()) {
            TextUtils.sendFormatted(sender, "&(red)You must be holding something to enchant.");
            return true;
        }

        if (ItemUtils.enchantConflicts(enchant.getType(), main)) {
            TextUtils.sendFormatted(
                sender,
                "&(red)This enchantment conflicts with your current enchantments."
            );
            return true;
        }
        if (!enchant.getType().canEnchantItem(main)) {
            TextUtils.sendFormatted(
                sender,
                "&(red)This enchantment can't be added to this item."
            );
            return true;
        }

        main.addEnchantment(enchant.getType(), recipe.level);
        ExperienceManager em = new ExperienceManager(player);
        em.changeExp(-recipe.xpCost);
        PlayerUtils.addJobXp(player.getUniqueId(), JobType.ENCHANTER, recipe.xpCost);
        TextUtils.sendFormatted(sender, "&(green)Enchant successful!");





        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.stream(CustomItem.values()).map(Enum::name).map(String::toLowerCase).map(n -> n.replaceAll("_", "-")).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private boolean displayEnchants(CommandSender sender, String pageStr) {
        int page = Utils.safeParseInt(pageStr);
        TextUtils.sendFormatted(
            sender,
            ENCHANTS.getPage(page)
        );
        return true;
    }
}
