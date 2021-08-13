package com.funnyboyroks.monstersplus.Jobs.WitchDoctor;

import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.funnyboyroks.monstersplus.Utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrewHandler {

    public static final Map<Location, CustomCauldron> CAULDRON_MAP = new HashMap<>();
    public static final int BREW_LEVEL = 2;
    public static final int[] STIR_LEVELS = { 7, 15 };
    private static final int MAX_REFILLS = 3;

    public static void rightClickCauldron(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = event.getItem();
        Block block = event.getClickedBlock();
        ExperienceManager em = new ExperienceManager(player);

        BlockData data = Material.CAULDRON.createBlockData();

        if (!block.getBlockData().getMaterial().name().endsWith("CAULDRON")) {
            return;
        }

        CustomCauldron cauldron = CAULDRON_MAP.getOrDefault(block.getLocation(), null);

        if (cauldron != null && cauldron.getPlayer() != player.getUniqueId()) {
            TextUtils.sendFormatted(player, "&(red)This is not your custom cauldron.");
            return;
        }

        if (cauldron != null && Utils.hasEmptyFaces(block)) {
            TextUtils.sendFormatted(player, "&(red)The blocks next to the cauldron must be empty to continue process.");
            return;
        }

        if (cauldron == null && hand.getType() != Material.NETHER_WART) return;

        switch (hand.getType()) {
            case NETHER_WART: { // Create custom cauldron
                if (cauldron != null) return;
                cauldron = tryCreateCauldron(player, hand, block);
            }
            break;
            case BONE: { // Stirring
                int stirs = cauldron.getStirs();
                int jobLevel = PlayerUtils.getJobLvl(player, JobType.WITCH_DOCTOR);

                if (stirs == 2) {
                    TextUtils.sendFormatted(
                        player,
                        "&(red)You cannot stir anymore."
                    );
                    return;
                }

                if ((stirs != 0 && stirs != 1) || jobLevel < STIR_LEVELS[stirs]) {
                    TextUtils.sendFormatted(
                        player,
                        "&(red)You must be a %0 level {&(gold)%1} to stir once and level {&(gold)%2} to stir twice.",
                        JobType.WITCH_DOCTOR,
                        STIR_LEVELS[0],
                        STIR_LEVELS[1]
                    );
                    return;
                }

                TextUtils.sendFormatted(player, "&(gold)You begin stirring the potion.");
                cleanUpFrames(cauldron);

                cauldron.setCurrentStep(stirs * 4 + 5);
                cauldron.setStirs(++stirs);

                for (int i = cauldron.getMats().size(); i < 3 * stirs; ++i) {
                    cauldron.getMats().add(Material.AIR);
                }

            }
            break;
            case GLASS_BOTTLE: { // Craft potion

                event.setCancelled(true);
                int step = cauldron.getCurrentStep();
                if (step == 0) return;
                if (!cauldron.hasValidPotion()) {
                    TextUtils.sendFormatted(
                        player,
                        "&(red)Invalid potion type."
                    );
                    cauldron.dropItems();
                    cauldron.remove();
                    CAULDRON_MAP.remove(cauldron.getLoc());
                    return;
                }

                int refills = cauldron.getRefills();
                if(refills >= MAX_REFILLS) {
                    removeCustomCauldron(cauldron);
                    return;
                }

                TextUtils.sendFormatted(
                    player,
                    "&(aqua)Potion brewing successfully."
                );
                ItemStack potion = cauldron.makePotion();

                if(potion == null) return;
                player.getInventory().addItem(potion);

                if (refills == MAX_REFILLS - 1) {
                    // Clean up the cauldron
                    removeCustomCauldron(cauldron);
                    TextUtils.sendFormatted(
                        player,
                        "&(red)The potion has been diluted."
                    );
                    return;
                }
                cauldron.setRefills(++refills);
            }
            break;
            default: { // Add held item as ingredient
                // TODO: Add held item as ingredient
            }
            break;
        }

    }

    private static void cleanUpFrames(CustomCauldron cauldron) {
        for (BlockFace face : List.of(BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)) {
            cauldron.getFrames().get(face).setItem(new ItemStack(Material.AIR));
        }

    }

    private static CustomCauldron tryCreateCauldron(Player player, ItemStack hand, Block block) {
        int jobLevel = PlayerUtils.getJobLvl(player, JobType.WITCH_DOCTOR);
        if (jobLevel < BREW_LEVEL) {
            TextUtils.sendFormatted(
                player,
                "&(red)Custom brewing is only available for level {&(gold)%0} %1s",
                BREW_LEVEL,
                JobType.WITCH_DOCTOR
            );
            return null;
        }

        if (block.getType() != Material.WATER_CAULDRON) {
            return null;
        }

        Levelled blockData = (Levelled) block.getBlockData();
        if (blockData.getMaximumLevel() != blockData.getLevel()) {
            TextUtils.sendFormatted(
                player,
                "&(red)The cauldron must be filled with water to start the custom brewing process."
            );
        }

        if (Utils.hasEmptyFaces(block)) {
            TextUtils.sendFormatted(
                player,
                "&(red)The blocks next to the cauldron must be empty to start the custom brewing process."
            );
            return null;
        }

        if (hasCustomCauldron(player)) {
            TextUtils.sendFormatted(
                player,
                "&(red)You already have a cauldron."
            );
            return null;
        }

        CustomCauldron cauldron = new CustomCauldron(block.getLocation(), player);
        CAULDRON_MAP.put(block.getLocation(), cauldron);
        TextUtils.sendFormatted(
            player,
            "&(gold)Custom brewing has begun."
        );

        cauldron.getFrames().put(
            BlockFace.NORTH,
            EntityUtils.itemFrame(block, BlockFace.NORTH, Material.POTION)
        );

        for (BlockFace face : new BlockFace[]{ BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST }) {
            cauldron.getFrames().put(
                face,
                EntityUtils.itemFrame(block, face, Material.AIR)
            );
        }

        cauldron.setCurrentStep(1);
        block.setBlockData(blockData);
        return cauldron;
    }

    public static boolean hasCustomCauldron(Player player) {
        for (CustomCauldron c : CAULDRON_MAP.values()) {
            if (c.getPlayer() == player.getUniqueId()) return true;
        }
        return false;
    }

    public static void removeCustomCauldron(CustomCauldron c) {
        Location loc = c.getLoc();
        if (CAULDRON_MAP.containsKey(loc)) {
            c.remove();
            CAULDRON_MAP.remove(loc);
        }
    }
}
