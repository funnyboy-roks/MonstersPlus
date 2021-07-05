package com.funnyboyroks.monstersplus.Data.structs;

import com.funnyboyroks.monstersplus.Tasks.SpawnTask;
import com.funnyboyroks.monstersplus.Utils.EntityUtils;
import com.funnyboyroks.monstersplus.Utils.ItemUtils;
import com.funnyboyroks.monstersplus.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MonsterType {
    ZOMBIE_CHARGER("Zombie Charger", 30, EntityType.ZOMBIE, DiffLevel.EASY),
    BURNING_WALKER("Burning Walker", 31, EntityType.ZOMBIE, DiffLevel.EASY, BiomeSets.HOT),
    LUMBER_ZOMBIE("Lumber Zombie", 32, EntityType.ZOMBIE, DiffLevel.EASY),
    ZOMBIE_REAPER("Zombie Reaper", 33, EntityType.ZOMBIE, DiffLevel.EASY),
    ZOMBIE_FISHERMAN("Zombie Fisherman", 34, EntityType.ZOMBIE, DiffLevel.EASY, BiomeSets.WATERFRONT),
    ROTTEN_FIGHTER("Rotten Fighter", 35, EntityType.ZOMBIE, DiffLevel.EASY),
    LAME_BRAIN("Lame Brain", 36, EntityType.ZOMBIE, DiffLevel.EASY, BiomeSets.SWAMP),
    ZOMBIE_CLIMBER("Zombie Climber", 37, EntityType.ZOMBIE, DiffLevel.EASY, BiomeSets.EXTREME_HILLS),
    PETTY_THIEF("Petty Thief", 38, EntityType.ZOMBIE, DiffLevel.EASY),
    THE_RESTLESS("The Restless", 51, EntityType.ZOMBIE, DiffLevel.MEDIUM),
    STINK_SWARM("Stink Swarm", 52, EntityType.ZOMBIE, DiffLevel.MEDIUM),
    LUNATIC("Lunatic", 53, EntityType.ZOMBIE, DiffLevel.MEDIUM),
    UNDEAD_MONK("Undead Monk", 54, EntityType.ZOMBIE, DiffLevel.MEDIUM),
    FAMISHED_LURKER("Famished Lurker", 55, EntityType.ZOMBIE, DiffLevel.MEDIUM, BiomeSets.HOT),
    COLD_CORPSE("Cold Corpse", 56, EntityType.ZOMBIE, DiffLevel.MEDIUM, BiomeSets.COLD),
    ROTTING_JACK("Rotting Jack", 57, EntityType.ZOMBIE, DiffLevel.MEDIUM),
    FROSTED_BITER("Frosted Biter", 58, EntityType.ZOMBIE, DiffLevel.MEDIUM, BiomeSets.COLD),
    CACTUAR("Cactuar", 60, EntityType.ZOMBIE, DiffLevel.MEDIUM, BiomeSets.HOT),
    CACTII("Cactii", 61, EntityType.ZOMBIE, DiffLevel.MEDIUM, BiomeSets.HOT),
    BURNT_GHOUL("Burnt Ghoul", 100, EntityType.ZOMBIE, DiffLevel.HARD, BiomeSets.HOT),
    UNDYING("Undying", 101, EntityType.ZOMBIE, DiffLevel.HARD),
    ARCTIC_ZED("Arctic Zed", 102, EntityType.ZOMBIE, DiffLevel.HARD, BiomeSets.COLD),
    HARDENED_THIEF("Hardened Thief", 59, EntityType.ZOMBIE, DiffLevel.HARD),
    ROYAL_THIEF("Royal Thief", 103, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    QACTUAR("Qactuar", 104, EntityType.ZOMBIE, DiffLevel.HARD, BiomeSets.HOT),

    SKELETON_KNIGHT("Skeleton Knight", 30, EntityType.SKELETON, DiffLevel.EASY),
    SKELETON_FIGHTER("Skeleton Fighter", 31, EntityType.SKELETON, DiffLevel.EASY),
    BONE_SOLDIER("Bone Soldier", 32, EntityType.SKELETON, DiffLevel.EASY),
    CRANIAL_BASHER("Cranial Basher", 33, EntityType.SKELETON, DiffLevel.EASY),
    SKELETON_PIRATE("Skeleton Pirate", 34, EntityType.SKELETON, DiffLevel.EASY, BiomeSets.WATERFRONT),
    SKELETON_FISHERMAN("Skeleton Fisherman", 35, EntityType.SKELETON, DiffLevel.EASY, BiomeSets.WATERFRONT),
    HOT_BONES("Hot Bones", 36, EntityType.SKELETON, DiffLevel.EASY, BiomeSets.HOT),
    DEAD_GARDENER("Dead Gardener", 37, EntityType.SKELETON, DiffLevel.EASY),
    FLAYED("Flayed", 50, EntityType.SKELETON, DiffLevel.MEDIUM),
    SKELETON_CHAMPION("Skeleton Champion", 51, EntityType.SKELETON, DiffLevel.MEDIUM),
    HOLY_SKELETON("Holy Skeleton", 52, EntityType.SKELETON, DiffLevel.MEDIUM),
    ICED_SHARPSHOOTER("Iced Sharpshooter", 53, EntityType.SKELETON, DiffLevel.MEDIUM, BiomeSets.COLD),
    DAZZLER("Dazzler", 54, EntityType.SKELETON, DiffLevel.MEDIUM, BiomeSets.SWAMP),
    FAMISHED_WALKER("Famished Walker", 55, EntityType.SKELETON, DiffLevel.MEDIUM, BiomeSets.HOT),
    CINDERTON("Cinderton", 56, EntityType.SKELETON, DiffLevel.MEDIUM, BiomeSets.HOT),
    SUMO_SKELETON("Sumo Skeleton", 57, EntityType.SKELETON, DiffLevel.MEDIUM),
    DEATH_KNIGHT("Death Knight", 97, EntityType.SKELETON, DiffLevel.HARD),
    ARSONIC_ARCHER("Arsonic Archer", 93, EntityType.SKELETON, DiffLevel.HARD),
    SKELETON_SNIPER("Skeleton Sniper", 100, EntityType.SKELETON, DiffLevel.HARD),
    MINER_OF_DEATH("Miner of Death", 101, EntityType.SKELETON, DiffLevel.HARD, BiomeSets.EXTREME_HILLS),
    SPINECHILLER("Spinechiller", 102, EntityType.SKELETON, DiffLevel.HARD, BiomeSets.COLD),
    PYRE("Pyre", 103, EntityType.SKELETON, DiffLevel.HARD, BiomeSets.HOT),
    BAD_BLACKSMITH("Bad Blacksmith", 104, EntityType.SKELETON, DiffLevel.HARD),
    CRAZED_SKELETON("Crazed Skeleton", 105, EntityType.SKELETON, DiffLevel.HARD, BiomeSets.SWAMP),
    EXECUTIONER("Executioner", 106, EntityType.SKELETON, DiffLevel.HARD),
    ARCHFIEND("Archfiend", 107, EntityType.SKELETON, DiffLevel.HARD),

    BLACK_WIDOW("Black Widow", 30, EntityType.SPIDER, DiffLevel.EASY),
    WITCH_APPRENTICE("Witch Apprentice", 31, EntityType.SPIDER, DiffLevel.EASY),
    MOUNTAIN_PINCER("Mountain Pincer", 32, EntityType.SPIDER, DiffLevel.EASY, BiomeSets.EXTREME_HILLS),
    SCORPION("Scorpion", 33, EntityType.SPIDER, DiffLevel.EASY),
    STINGER("Stinger", 50, EntityType.SPIDER, DiffLevel.MEDIUM),
    TARANTULA("Tarantula", 51, EntityType.SPIDER, DiffLevel.MEDIUM),
    ROCK_SCORPION("Rock Scorpion", 53, EntityType.SPIDER, DiffLevel.MEDIUM),
    WITCH_OF_THE_EAST("Witch of the East", 54, EntityType.SPIDER, DiffLevel.MEDIUM),
    DESERT_SCORPION("Desert Scorpion", 55, EntityType.SPIDER, DiffLevel.MEDIUM, BiomeSets.HOT),
    ICE_SPIDER("Ice Spider", 56, EntityType.SPIDER, DiffLevel.MEDIUM, BiomeSets.COLD),
    WARLOCK("Warlock", 57, EntityType.SPIDER, DiffLevel.MEDIUM),
    BROWN_RECLUSE("Brown Recluse", 100, EntityType.SPIDER, DiffLevel.HARD),
    WITCH_OF_THE_WEST("Witch of the West", 101, EntityType.SPIDER, DiffLevel.HARD),
    WATER_STINGER("Water Stinger", 102, EntityType.SPIDER, DiffLevel.HARD, BiomeSets.WATERFRONT),
    FILTH_PURCH("Filth Purch", 103, EntityType.SPIDER, DiffLevel.HARD, BiomeSets.SWAMP),
    SWAMP_WITCH("Swamp Witch", 104, EntityType.SPIDER, DiffLevel.HARD, BiomeSets.SWAMP),
    DARK_MAGE("Dark Mage", 105, EntityType.SPIDER, DiffLevel.HARD),

    SQUIRT("Squirt", 26, EntityType.CREEPER, DiffLevel.EASY),
    HEAVY_CREEPER("Heavy Creeper", 27, EntityType.CREEPER, DiffLevel.EASY),
    DROOPY("Droopy", 28, EntityType.CREEPER, DiffLevel.EASY),
    HOT_HEAD("Hot Head", 29, EntityType.CREEPER, DiffLevel.EASY),
    SWOLLEN_CREEPER("Swollen Creeper", 65, EntityType.CREEPER, DiffLevel.MEDIUM),
    HEAT_SEEKER("Heat Seeker", 23, EntityType.CREEPER, DiffLevel.MEDIUM),
    CREEPILLAR("Creepillar", 29, EntityType.CREEPER, DiffLevel.MEDIUM),
    STARVING_CREEPER("Starving Creeper", 41, EntityType.CREEPER, DiffLevel.MEDIUM, BiomeSets.HOT),
    REFORMING_CREEPER("Reforming Creeper", 38, EntityType.CREEPER, DiffLevel.MEDIUM),
    COLD_BOMB("Cold Bomb", 33, EntityType.CREEPER, DiffLevel.MEDIUM, BiomeSets.COLD),
    GAS_BAG("Gas Bag", 53, EntityType.CREEPER, DiffLevel.MEDIUM),
    VORTEX_CREEP("Vortex Creep", 35, EntityType.CREEPER, DiffLevel.HARD),
    LITTLE_BOY("Little Boy", 100, EntityType.CREEPER, DiffLevel.HARD),
    QUAKER("Quaker", 102, EntityType.CREEPER, DiffLevel.HARD),

    ATILLA("Atilla", 470, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    SLEEPY_HOLLOW("Sleepy Hollow", 510, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    HYDE("Hyde", 480, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    JUGGLER("Juggler", 201, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    HOCUS("Hocus", 560, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    POCUS("Pocus", 590, EntityType.ZOMBIE, DiffLevel.SPECIAL),
    ACHILLES("Achilles", 680, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    PIGLET("Piglet", 1354, EntityType.ZOMBIE, DiffLevel.LEGENDARY),
    WILBUR("Wilbur", 1350, EntityType.ZOMBIE, DiffLevel.LEGENDARY),

    LEGOLAS("Legolas", 550, EntityType.SKELETON, DiffLevel.LEGENDARY),
    HAWKEYE("Hawkeye", 570, EntityType.SKELETON, DiffLevel.LEGENDARY),
    TICKLES("Tickles", 505, EntityType.SKELETON, DiffLevel.LEGENDARY),
    CAPTAIN_AHAB("Captain Ahab", 615, EntityType.SKELETON, DiffLevel.LEGENDARY),

    INFEARNO("Infearno", 360, EntityType.SPIDER, DiffLevel.LEGENDARY),
    HADAMARD("Hadamard", 1770, EntityType.SPIDER, DiffLevel.LEGENDARY),
    LESATH("Lesath", 825, EntityType.SPIDER, DiffLevel.LEGENDARY),
    SHELOB("Shelob", 945, EntityType.SPIDER, DiffLevel.LEGENDARY),
    ARACHNE("Arachne", 1022, EntityType.SPIDER, DiffLevel.LEGENDARY),
    CHARLOTTE("Charlotte", 1021, EntityType.SPIDER, DiffLevel.LEGENDARY),
    RAIKOU("Raikou", 1650, EntityType.SPIDER, DiffLevel.LEGENDARY),
    ENTEI("Entei", 1660, EntityType.SPIDER, DiffLevel.LEGENDARY),
    SUICUNE("Suicune", 1670, EntityType.SPIDER, DiffLevel.LEGENDARY),
    BARKIRA("Barkira", 1760, EntityType.SPIDER, DiffLevel.LEGENDARY),

    FROST_ANT("Frost Ant", 15, EntityType.SILVERFISH, DiffLevel.SPECIAL),
    FIRE_ANT("Fire Ant", 13, EntityType.SILVERFISH, DiffLevel.SPECIAL),
    TOXIC_ANT("Toxic Ant", 14, EntityType.SILVERFISH, DiffLevel.SPECIAL),
    IRON_GUARDIAN("Iron Guardian", 100, EntityType.ZOMBIE, DiffLevel.SPECIAL),
    UNDEAD_HORSE("Undead Horse", 70, EntityType.HORSE, DiffLevel.SPECIAL),
    SKELETON_HORSE("Skeleton Horse", 70, EntityType.HORSE, DiffLevel.SPECIAL),
    BRONCO_OF_DEATH("Bronco of Death", 125, EntityType.HORSE, DiffLevel.SPECIAL),
    PLAGUE_STALLION("Plague Stallion", 125, EntityType.HORSE, DiffLevel.SPECIAL),
    ;

    public final String name;
    public final int health;
    public final EntityType baseEntity;
    public final DiffLevel difficulty;
    public final Biome[] biomes;


    private static final int MAX_SPAWNS = 8;

    MonsterType(String name, int health, EntityType baseEntity, DiffLevel difficulty, Biome[] biomes) {
        this.name = name;
        this.health = health;
        this.baseEntity = baseEntity;
        this.difficulty = difficulty;
        this.biomes = biomes;

//        addType(baseEntity);
    }

    MonsterType(String name, int health, EntityType baseEntity, DiffLevel difficulty) {
        this(name, health, baseEntity, difficulty, null);
    }


//    public void addType(EntityType baseEntity) {
//        if (TYPES_MAP.containsKey(baseEntity)) {
//            TYPES_MAP.get(baseEntity).add(this);
//        } else {
//            List<MonsterType> types = new ArrayList<>();
//            types.add(this);
//            TYPES_MAP.put(baseEntity, types);
//        }
//    }

    public LivingEntity spawn(Location loc, CreatureSpawnEvent.SpawnReason reason) {
        Entity e = loc.getWorld().spawnEntity(loc, baseEntity, reason);
        LivingEntity livingEnt = (LivingEntity) e;

        livingEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        livingEnt.setHealth(health);

        livingEnt.setCustomName(ChatColor.GOLD + name);

        return livingEnt;
    }

    public TrophyType getTrophy() {
        try {
            return TrophyType.valueOf(this.name());
        } catch (IllegalArgumentException ignored) { // If there's no trophy
            return null;
        }
    }

    public void dropTrophy(LivingEntity livEnt) {
        switch (difficulty) {
            case LEGENDARY:
            case SPECIAL:
                TrophyType trophy = getTrophy();
                if (trophy != null) {
                    ItemUtils.dropItem(livEnt.getLocation(), trophy.toItemStack());
                }

        }
    }

    public boolean isValidBiome(Biome biome) {
        if (biome == null || biomes == null) {
            return true;
        }

        return Arrays.stream(biomes).anyMatch(b -> b == biome);
    }

    public String getColouredMobName() {
        switch (difficulty) {
            case EASY:
                return ChatColor.YELLOW + name;
            case MEDIUM:
                return ChatColor.GOLD + name;
            case HARD:
                return ChatColor.RED + name;
            case LEGENDARY:
                return ChatColor.DARK_PURPLE + name;
            default:
                return name;
        }
    }

    public static MonsterType generateMonster(EntityType type, Biome biome) {
        DiffLevel chosenDiff;

        double legSpawn = 1.5;
        double hrdSpawn = 11 + legSpawn;
        double medSpawn = 19 + hrdSpawn;
        double easSpawn = 27 + medSpawn;

        double rand = Math.random() * 100;

        if (rand < legSpawn) {
            chosenDiff = DiffLevel.LEGENDARY;
        } else if (rand < hrdSpawn) {
            chosenDiff = DiffLevel.HARD;
        } else if (rand < medSpawn) {
            chosenDiff = DiffLevel.MEDIUM;
        } else if (rand < easSpawn) {
            chosenDiff = DiffLevel.EASY;
        } else {
            return null;
        }

        List<MonsterType> valid = Arrays.stream(values())
            .filter(
                m ->
                    m.difficulty == chosenDiff &&
                        m.baseEntity == type &&
                        m.isValidBiome(biome)
            )
            .collect(Collectors.toList());

        return valid.get((int) (Math.random() * valid.size())); // Get random one

    }

    public static void randomSpawn(MonsterType type, Location loc, int tries, double chance) {
        for (int i = 0; i < tries; ++i) {
            if (Utils.randomBool(chance)) {
                new SpawnTask(10 * i, type, loc);
            }
        }
    }

    public static boolean isMonster(LivingEntity livEnt) {
        return getMonsterType(livEnt) != null;
    }

    public static MonsterType getMonsterType(LivingEntity livEnt) {
        if (EntityUtils.hasCustomMetadata(livEnt, "MonstersPlusName")) {
            return valueOf(EntityUtils.getCustomMetadata(livEnt, "MonstersPlusName"));
        }
        return Arrays.stream(values()).filter(
            mt ->
                mt.baseEntity == livEnt.getType() &&
                    mt.health == livEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()
        ).findFirst().orElse(null);
    }

    public static void updateMonsterMetaName(LivingEntity livEnt) {
        MonsterType type = getMonsterType(livEnt);
        if (type == null || livEnt instanceof Player || livEnt.getCustomName() == null) {
            return;
        }

        String noColour = ChatColor.stripColor(livEnt.getCustomName());
        if (
            !EntityUtils.hasCustomMetadata(livEnt, "targetName") &&
                !noColour.contains(Utils.FULL_BLOCK) &&
                !noColour.contains(Utils.HALF_BLOCK)
        ) {
            EntityUtils.setCustomMetadata(livEnt, "targetName", noColour);
        }
    }

}
