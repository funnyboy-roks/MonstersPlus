package com.funnyboyroks.monstersplus.Jobs.WitchDoctor;

import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.*;
import java.util.stream.Collectors;

public class CustomCauldron {

    private Location loc;
    private UUID player;
    private int currentStep;
    private int stirs;
    private int refills;
    private final List<Material> mats = new ArrayList<>();
    private final Map<BlockFace, ItemFrame> frames;

    public CustomCauldron(Location loc, Player player) {
        this.loc = loc;
        this.player = player.getUniqueId();
        this.currentStep = 0;
        this.stirs = 0;
        this.refills = 0;
        this.frames = new HashMap<>();

    }

    // Getters/Setters
    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getStirs() {
        return stirs;
    }

    public void setStirs(int stirs) {
        this.stirs = stirs;
    }

    public int getRefills() {
        return refills;
    }

    public void setRefills(int refills) {
        this.refills = refills;
    }

    public List<Material> getMats() {
        return mats;
    }

    public ItemStack makePotion() {
        List<BrewIngredient> ingredients = mats
            .stream()
            .map(BrewIngredient::getIngredient)
            .collect(Collectors.toList());

        List<BrewIngredient> bucket = new ArrayList<>();
        List<PotionEffect> effects = new ArrayList<>();

        for (int i = 0; i < ingredients.size(); i++) {
            BrewIngredient ing = ingredients.get(i);
            bucket.add(ing);
            if (bucket.size() == 3 || i == ingredients.size() - 1) {
                if (isValidPotion(bucket)) {
                    PotionEffect effect = addCustomEffect(bucket);
                    if (effect == null) continue;
                    effects.add(effect);
                    bucket.clear();
                }
            }
        }
        if(effects.isEmpty()) return null;

        boolean splash = mats.contains(BrewIngredient.GUNPOWDER.mat);

        ItemStack stack = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();

        meta.clearCustomEffects();
        effects.forEach(e -> meta.addCustomEffect(e, true));

        stack.setItemMeta(meta);
        return stack;
    }

    public boolean hasValidPotion() {
        List<BrewIngredient> ingredients = mats
            .stream()
            .map(BrewIngredient::getIngredient)
            .collect(Collectors.toList());
        return isValidPotion(ingredients);
    }

    public static boolean isValidPotion(Collection<BrewIngredient> ings) {
        for (BrewIngredient ing : ings) {
            if (ing != null && ing.type != null) return true;
        }
        return false;
    }

    public static PotionEffect addCustomEffect(List<BrewIngredient> ings) {
        int amp = 0;
        int ext = 0;
        for (BrewIngredient ing : ings) {
            switch (ing) {
                case QUARTZ_BLOCK:
                    amp += 1;
                    break;
                case IRON_BLOCK:
                    amp += 2;
                    break;
                case REDSTONE_BLOCK:
                    ext += 1;
                    break;
                case LAPIS_BLOCK:
                    ext += 2;
                    break;
            }
        }
        List<BrewIngredient> validIngredients = BrewIngredient.getPotionTypes(ings);
        if (validIngredients.size() == 0) return null;
        BrewIngredient chosenIngredient = Utils.choseRandom(validIngredients);
        if(chosenIngredient == null) return null;

        return chosenIngredient.getPotionEffect(ext, amp);
    }

    public void dropItems() {
        mats.forEach(mat -> ItemUtils.dropItem(loc.clone().add(0, 1, 0), new ItemStack(mat)));
        mats.clear();
    }

    public void remove() {
        frames.values().forEach(ItemFrame::remove);
    }

    public Map<BlockFace, ItemFrame> getFrames() {
        return frames;
    }
}
