package com.funnyboyroks.monstersplus.Data.structs;

import com.funnyboyroks.monstersplus.MonstersPlus;
import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import com.funnyboyroks.monstersplus.Utils.PlayerUtils;
import com.funnyboyroks.monstersplus.Utils.RecipeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public enum CustomItem {

    // For recipeStr char information, look at RECIPE_PARTS_MAP in RecipeUtils.

    // Survivalist Arrows
    REPULSE_ARROW("&aRepulse Arrow", " c | S | F ", Material.ARROW, 8, JobType.SURVIVALIST, 2, "&eAn arrow that launches nearby enemies away."), //, "2. Obsidian  5. Stick  8. Feather"),
    TORCH_ARROW("&6Torch Arrow", " b | S | F ", Material.ARROW, 1, JobType.SURVIVALIST, 3, "&eCreates a torch at the arrow's location."), //, "2. Torch  5. Stick  8. Feather"),
    LASSO_ARROW("&9Lasso Arrow", " s | S | F ", Material.ARROW, 2, JobType.SURVIVALIST, 4, "&eTeleports the enemy to your location, great against players running away."), //, "2. Vine  5. Stick  8. Feather"),
    VORTEX_ARROW("&aVortex Arrow", " d | S | F ", Material.ARROW, 24, JobType.SURVIVALIST, 5, "&ePulls nearby enemies to the arrow's location."), //, "2. SlimeBall  5. Stick  8. Feather"),
    NET_ARROW("&fNet Arrow", "rrr| S | F ", Material.ARROW, 3, JobType.SURVIVALIST, 6, "&eCreates a Web net at the arrow's location."), //, "1. String  2. String  3. String  5. Stick  8. Feather"),
    SHUFFLE_ARROW("&eShuffle Arrow", " e | S | F ", Material.ARROW, 6, JobType.SURVIVALIST, 8, "&eCauses the shooter to switch places with the target."), //, "2. EnderPearl  5. Stick  8. Feather"),
    GRAPPLE_ARROW("&5Grapple Arrow", " g | S | F ", Material.ARROW, 20, JobType.SURVIVALIST, 10, "&eTeleports the shooter to the arrow location, Hold Crouch to latch onto objects."), //, "2. Lead  5. Stick  8. Feather"),
    BLIZZARD_ARROW("&bBlizzard Arrow", "ttt| S | F ", Material.ARROW, 3, JobType.SURVIVALIST, 11, "&eCauses all nearby enemies to suffer from intense slow."), //, "1. Ice  2. Ice  3. Ice  5. Stick  8. Feather"),

    // Warrior Arrows
    BEAST_ARROW("&aBeast Arrow", "qqq| S | F ", Material.ARROW, 1, JobType.WARRIOR, 2, "&eDoes triple damage to monsters."), //, "1. Flint  2. Flint  3. Flint  5. Stick  8. Feather"),
    FIREBALL_ARROW("&eFireball Arrow", "KkK| S | F ", Material.ARROW, 6, JobType.WARRIOR, 3, "&eLaunches an explosive Fireball."), //, "1. Coal  2. Gunpowder  3. Coal  5. Stick  8. Feather"),
    RAZOR_ARROW("&bRazor Arrow", "iii| S | F ", Material.ARROW, 3, JobType.WARRIOR, 4, "&eDoes double damage to unarmored targets."), //, "1. Glass  2. Glass  3. Glass  5. Stick  8. Feather"),
    SKULL_ARROW("&8Skull Arrow", "ono| S | F ", Material.ARROW, 3, JobType.WARRIOR, 5, "&eLaunches a WitherSkull that applies Wither II."), //, "1. Bone  2. Rotten Flesh  3. Bone  5. Stick  8. Feather"),
    PIERCING_ARROW("&6Piercing Arrow", " j | S | F ", Material.ARROW, 4, JobType.WARRIOR, 6, "&eDoes double damage to fully armored targets."), //, "2. Iron Ingot  5. Stick  8. Feather"),
    EXPLOSIVE_ARROW("&4Explosive Arrow", " k | S | F ", Material.ARROW, 6, JobType.WARRIOR, 8, "&eCreates a large explosion at the impact zone."), //, "1. Gunpowder  2. Gunpowder  3. Gunpowder  5. Stick  8. Feather"),
    POISON_ARROW("&5Poison Arrow", " h | S | F ", Material.ARROW, 1, JobType.WARRIOR, 10, "&eCauses the target to suffer from level 2 poison."), //, "2. Spider Eye  5. Stick  8. Feather"),
    SHREADING_ARROW("&dShreading Arrow", "ppp| S | F ", Material.ARROW, 6, JobType.WARRIOR, 11, "&eDoes 3% of the targets current HP, great against Legendaries."), //, "1. Arrow  2. Arrow  3. Arrow  5. Stick  8. Feather"),
    COMET_ARROW("&4Comet Arrow", "kmk| S | F ", Material.ARROW, 16, JobType.WARRIOR, 12, "&eFireballs fall from the sky to the impact zone."), //, "1. Gunpowder  2. Blaze Rod  3. Gunpowder  5. Stick  8. Feather"),

    CAVE_SPIDER_SPAWNER("&eCave Spider &fSpawner", "rrr|rLr|rrr", EntityType.CAVE_SPIDER, JobType.BUILDER, 2, 6), // "1-4. String 5. Mobspawner  6-9. String"),
    SPIDER_SPAWNER("&eSpider &fSpawner", "hhh|hLh|hhh", EntityType.SPIDER, JobType.BUILDER, 3, 6), // "1-4. Spider Eye 5. MobSpawner  6-9. Spidereye", 6),
    BAT_SPAWNER("&eBat &fSpawner", "KKK|KLK|KKK", EntityType.BAT, JobType.BUILDER, 4, 6), // "1-4. Coal 5. MobSpawner  6-9. Coal", 6),
    ZOMBIE_SPAWNER("&eZombie &fSpawner", "nnn|nLn|nnn", EntityType.ZOMBIE, JobType.BUILDER, 5, 7), // "1-4. RottenFlesh 5. MobSpawner  6-9. RottenFlesh", 7),
    SKELETON_SPAWNER("&eSkeleton &fSpawner", "ooo|oLo|ooo", EntityType.SKELETON, JobType.BUILDER, 6, 10), // "1-4. Bone 5. MobSpawner  6-9. Bone", 10),
    CREEPER_SPAWNER("&eCreeper &fSpawner", "kkk|kLk|kkk", EntityType.CREEPER, JobType.BUILDER, 7, 15), // "1-4. Gunpowder 5. MobSpawner  6-9. Gunpowder", 15),
    ENDERMAN_SPAWNER("&eEnderman &fSpawner", "eee|eLe|eee", EntityType.ENDERMAN, JobType.BUILDER, 8, 7), // "1-4. Enderpearl 5. MobSpawner  6-9. Enderpearl", 7),
    BLAZE_SPAWNER("&eBlaze &fSpawner", "mmm|mLm|mmm", EntityType.BLAZE, JobType.BUILDER, 9, 11), // "1-4. Blazerod 5. MobSpawner  6-9. Blazerod", 11),
    SILVERFISH_SPAWNER("&eSilverfish &fSpawner", "TTT|TLT|TTT", EntityType.SILVERFISH, JobType.BUILDER, 11, 10), // "1-4. Rawfish 5. MobSpawner  6-9. Rawfish", 10),
    ZOMBIE_PIGMAN_SPAWNER("&eZombie Pigman &fSpawner", "MMM|MLM|MMM", EntityType.ZOMBIFIED_PIGLIN, JobType.BUILDER, 13, 15), // "1-4. Gold Ore 5. MobSpawner  6-9. Gold Ore", 15),
    SNOW_GOLEM_SPAWNER("&eSnow Golem &fSpawner", "BBB|BLB|BBB", EntityType.SNOWMAN, JobType.BUILDER, 15, 7), // "1-4. Snow Ball 5. MobSpawner  6-9. Snow Ball", 7),

    CRACKED_STONE_BRICK("&fCracked Stone Brick", "lq |   |   ", Material.CRACKED_STONE_BRICKS, 1, JobType.BUILDER, 2), // "1. Stone Brick 2. Flint"),
    CHISELED_STONE_BRICK("&fChiseled Stone Brick", " q |qlq| q ", Material.CHISELED_STONE_BRICKS, 1, JobType.BUILDER, 4), // "2. Flint 4. Flint 5. Stone Brick 6. Flint 8. Flint"),
    MOSSY_COBBLESTONE("&fMossy Cobblestone", " u |uvu| u ", Material.MOSSY_COBBLESTONE, 1, JobType.BUILDER, 5), // "2. Seeds 4. Seeds 5. Cobblestone 6. Seeds 8. Seeds"),
    MOSSY_STONE_BRICK("&fMossy Stone Brick", " u |ulu| u ", Material.MOSSY_STONE_BRICKS, 1, JobType.BUILDER, 6), // "2. Seeds 4. Seeds 5. Stonebrick 6. Seeds 8. Seeds"),
    END_PORTAL("&fEnd Portal", "www|xxx|xxx", Material.END_PORTAL_FRAME, 1, JobType.BUILDER, 10), // "1. Emerald 2. Emerald 3. Emerald 4-9. Enderstone"),

    CHICKEN("&fChicken", "uuu|uBu|uuu", Material.CHICKEN_SPAWN_EGG, 1, JobType.FARMER, 2), // , "1-4. Seeds 5. Egg  6-9. Seeds"),
    COW("&fCow", "CCC|CBC|CCC", Material.COW_SPAWN_EGG, 1, JobType.FARMER, 3), // , "1-4. Wheat 5. Egg  6-9. Wheat"),
    PIG("&fPig", "DDD|DBD|DDD", Material.PIG_SPAWN_EGG, 1, JobType.FARMER, 4), // , "1-4. Carrot 5. Egg  6-9. Carrot"),
    SHEEP("&fSheep", "EEE|EBE|EEE", Material.SHEEP_SPAWN_EGG, 1, JobType.FARMER, 5), // , "1-4. Wool 5. Egg  6-9. Wool"),
    SQUID("&fSquid", "fff|fBf|fff", Material.SQUID_SPAWN_EGG, 1, JobType.FARMER, 5), // , "1-4. Inksac 5. Egg  6-9. Inksac"),
    WOLF("&fWolf", "ooo|oBG|GGG", Material.WOLF_SPAWN_EGG, 1, JobType.FARMER, 6), // , "1-4. Bone 5. Egg  6-9. Melon Seeds"),
    OCELOT("&fOcelot", "HHH|HBH|HHH", Material.OCELOT_SPAWN_EGG, 1, JobType.FARMER, 8), // , "1-4. Milk 5. Egg  6-9. Milk"),
    SPIDER("&fSpider", "Jhh|hBh|hhh", Material.SPIDER_SPAWN_EGG, 1, JobType.FARMER, 9), // , "1. Lapis Block 2-4. Spider Eye 5. Egg  6-9. Spider Eye"),
    SKELETON("&fSkeleton", "Joo|oBo|ooo", Material.SKELETON_SPAWN_EGG, 1, JobType.FARMER, 10), // , "1. Lapis Block 2-4. Bone 5. Egg  6-9. Bone"),
    BLAZE("&fBlaze", "Jmm|mBm|mmm", Material.BLAZE_SPAWN_EGG, 1, JobType.FARMER, 11), // , "1. Lapis Block 2-4. Blaze Rod 5. Egg  6-9. Blaze Rod"),
    ENDERMAN("&fEnderman", "Jee|eBe|eee", Material.ENDERMAN_SPAWN_EGG, 1, JobType.FARMER, 11), // , "1. Lapis Block 2-4. Enderpearl 5. Egg  6-9. Enderpearl"),
    ZOMBIE("&fZombie", "Jnn|nBn|nnn", Material.ZOMBIE_SPAWN_EGG, 1, JobType.FARMER, 12), // , "1. Lapis Block 2-4. Rotten Flesh 5. Egg  6-9. Rotten Flesh"),
    MOOSHROOM("&fMooshroom", "III|IBI|III", Material.MOOSHROOM_SPAWN_EGG, 1, JobType.FARMER, 12), // , "1-4. Mushroom Soup 5. Egg  6-9. Mushroom Soup"),
    CREEPER("&fCreeper", "Jkk|kBk|kkk", Material.CREEPER_SPAWN_EGG, 1, JobType.FARMER, 13), // , "1. Lapis Block 2-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    SLIME("&fSlime", "Jdd|dBd|ddd", Material.SLIME_SPAWN_EGG, 1, JobType.FARMER, 13), // , "1. Lapis Block 2-4. Slimeball 5. Egg  6-9. Slimeball"),
    HORSE("&fHorse", "ggg|gBg|ggg", Material.HORSE_SPAWN_EGG, 1, JobType.FARMER, 15), // , "1-4. Lead 5. Egg  6-9. Lead"),
    VILLAGER("&fVillager", "www|wBw|www", Material.VILLAGER_SPAWN_EGG, 1, JobType.FARMER, 18), // , "1-4. Emerald 5. Egg 6-9. Emerald"),

    SADDLE("&aSaddle", "yyy|yry|   ", Material.SADDLE, 1, JobType.FARMER, 3), // , "1-4. Leather 5. String 6. Leather"),
    IRON_HORSE_ARMOR("&aIron Horse Armor", "  j|jj | jj", Material.IRON_HORSE_ARMOR, 1, JobType.FARMER, 6), // , "3-5. Iron Ingot 7-8. Iron Ingot"),
    GOLDEN_HORSE_ARMOR("&aGolden Horse Armor", "  z|zz | zz", Material.GOLDEN_HORSE_ARMOR, 1, JobType.FARMER, 10), // , "3-5. Gold Ingot 7-8. Gold Ingot"),
    DIAMOND_HORSE_ARMOR("&aDiamond Horse Armor", "  A|AA | AA", Material.DIAMOND_HORSE_ARMOR, 1, JobType.FARMER, 14), // , "3-5. Diamond 7-8. Diamond"),


    SURVIVALIST_BOMB("Survivalist Bomb", "kak|kBk|kkk", Material.EGG, 1, JobType.SURVIVALIST, 10, "A stun bomb that immobilizes targets."), // , "1. Gunpowder  2. Iron Axe  3-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    WARRIOR_BOMB("Warrior Bomb", "kNk|kBk|kkk", Material.EGG, 1, JobType.WARRIOR, 10, "A fire bomb that ignites nearby targets and spawns a temporary powerful Zombie Pigman that will attack the nearest entity."), // , "1. Gunpowder  2. Iron Sword  3-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    WITCH_DOCTOR_BOMB("Witch Doctor Bomb", "kUk|kBk|kkk", Material.EGG, 1, JobType.WITCH_DOCTOR, 10, "A Wither bomb that withers nearby foes and spawns temporary wither skeletons."), // , "1. Gunpowder  2. Glass Bottle  3-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    MINER_BOMB("Miner Bomb", "kVk|kBk|kkk", Material.EGG, 1, JobType.MINER, 10, "A cluster bomb that explodes repeatedly."), // , "1. Gunpowder  2. Iron Pickaxe  3-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    FISHERMAN_BOMB("Fisherman Bomb", "kWk|kBk|kkk", Material.EGG, 1, JobType.FISHERMAN, 10, "A lightning bomb that electricutes nearby enemies."), // , "1. Gunpowder  2. Fishing Rod  3-4. Gunpowder 5. Egg  6-9. Gunpowder"),
    BLACKSMITH_BOMB("Blacksmith Bomb", "kXk|kBk|kkk", Material.EGG, 1, JobType.BLACKSMITH, 10, "A bomb that summons anvils from the sky to crush foes."), // , "1. Gunpowder  2. Furnace   3-4. Gunpowder 5. Egg  6-9. Gunpowder"),

    POWERSTONE("&5Powerstone", Material.REDSTONE, true),
    XP_BOTTLE("Bottle o' EXP", Material.GLASS_BOTTLE, true),
    XP_BUCKET("Bucket o' EXP", Material.BUCKET, true),
    ;

    private final String name;
    private final String recipeStr;
    private final ItemStack item;
    private final JobType job;
    private final int level;
    private final String description;
    private final int powerstones;
//    private String recipeDesc;

    CustomItem(ItemStack item, String recipeStr, JobType job, int level, String description, int powerstones/*, String recipeDesc*/) {
        this.name = ItemUtils.getName(item);
        this.recipeStr = recipeStr;
        this.item = item;
        this.job = job;
        this.level = level;
        this.description = description;
        this.powerstones = powerstones;
//        this.recipeDesc = recipeDesc;

        List<String> lore = new ArrayList<>();
        if (description != null) {
            lore.add(description);
        }
        lore.add("&9MonstersPlus");
        ItemUtils.setItemStackLore(item, lore.toArray(new String[0]));

    }

    CustomItem(String name, String recipeStr, Material mat, int count, JobType job, int level, String description) {
        this(ItemUtils.item(mat, name, count), recipeStr, job, level, description, 0);
    }

    CustomItem(String name, String recipeStr, EntityType type, JobType job, int level, int powerstones) {
        this(ItemUtils.spawner(type, name), recipeStr, job, level, null, powerstones);
    }

    CustomItem(String name, String recipeStr, Material mat, int count, JobType job, int level) {
        this(name, recipeStr, mat, count, job, level, null);
    }

    CustomItem(String name, Material mat, boolean glint) {
        ItemStack stack = ItemUtils.item(mat, name);
        if (glint) {
            ItemUtils.forceEnchant(stack, Enchantment.MENDING, 1);
            ItemUtils.addAllFlags(stack);
        }
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.item = stack;
        this.recipeStr = null;
        this.job = null;
        this.level = -1;
        this.description = null;
        this.powerstones = -1;
    }

    public static void registerRecipes() {
        List<CustomItem> fails = new ArrayList<>();
        Arrays.stream(values()).filter(ci -> ci.recipeStr != null).forEach(ci -> {
            if (!ci.registerRecipe()) fails.add(ci);
        });
        long success = Arrays.stream(values()).filter(ci -> ci.recipeStr != null).count() - fails.size();
        Bukkit.getLogger().log(
            Level.INFO,
            "Successfully loaded " + success + " recipes, " +
                "with " + fails.size() + " fails. " +
                "(" + (int) (success / ((double) values().length) * 100) + "%)"
        );

        if (!fails.isEmpty()) {
            Bukkit.getLogger().log(
                Level.WARNING,
                "Failed to load " + fails.size() + " recipes: " +
                    fails
                        .stream()
                        .map(Enum::name)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
            );
        }
    }

    public static List<CustomItem> getEndingWith(String s) {
        List<CustomItem> out = new ArrayList<>();
        for (CustomItem value : values()) {
            if (value.name().endsWith(s)) {
                out.add(value);
            }
        }
        return out;
    }

    public boolean registerRecipe() {
        if (recipeStr == null) return true;

        try {
            NamespacedKey key = new NamespacedKey(MonstersPlus.instance, name().toLowerCase());
            ShapedRecipe recipe = new ShapedRecipe(key, item);

            recipe.shape(recipeStr.split("\\|"));
            for (char c : recipeStr.toCharArray()) { // Set the ingredients based on what is used in the recipe string
                if (c == ' ' || c == '|') continue;
                recipe.setIngredient(c, RecipeUtils.RECIPE_PARTS_MAP.get(c));
            }

            Bukkit.addRecipe(recipe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public JobType getJob() {
        return job;
    }

    public int getLevel() {
        return level;
    }

    public int getPowerstones() {
        return powerstones;
    }

    public boolean canUse(Player player) {
        return PlayerUtils.getJobLvl(player, job) >= level;
    }

    /**
     * Check if a given itemstack matches the required values to count as this item.
     *
     * @param stack ItemStack to check against
     * @return Whether it could be a valid value
     */
    public boolean matches(ItemStack stack) {
        ItemMeta thisMeta = item.getItemMeta();
        ItemMeta meta = stack.getItemMeta();

        if (stack.getType() != item.getType() || !meta.hasLore()) {
            return false;
        }

        boolean matchLore = (
            description == null ||
                ItemUtils.getLore(stack).contains(description.replaceAll("&[a-f0-9klmnor]", "").toLowerCase())
        ) && ItemUtils.getLore(stack).contains("monstersplus");

        boolean matchName = ItemUtils
            .getName(stack)
            .toLowerCase()
            .contains(
                ItemUtils
                    .getName(item)
                    .toLowerCase()
            );

        return matchName && matchLore;


    }

    public ItemStack getItem() {
        return item.clone();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }
}

