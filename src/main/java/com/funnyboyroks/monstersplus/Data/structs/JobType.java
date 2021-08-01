package com.funnyboyroks.monstersplus.Data.structs;

public enum JobType {

    SURVIVALIST("Survivalist", "Survivalist"),
    WARRIOR("Warrior", "Warrior"),
    BUILDER("Builder", "Builder"),
    FARMER("Farmer", "Farmer"),
    WITCH_DOCTOR("Witch Doctor", "Witch Doctor"),
    MINER("Miner", "Miner"),
    FISHERMAN("Fisherman", "Fisherman"),
    BLACKSMITH("Blacksmith", "Blacksmith"),
    ENCHANTER("Enchanter", "Enchanter"),
    ;

    private final String humanName;
    public final String name;

    JobType(String humanName, String jrName) {
        this.humanName = humanName;
        this.name = jrName;
    }

    @Override
    public String toString() {
        return humanName;
    }
}
