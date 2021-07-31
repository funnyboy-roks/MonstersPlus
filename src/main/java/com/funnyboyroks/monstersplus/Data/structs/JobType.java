package com.funnyboyroks.monstersplus.Data.structs;

public enum JobType {

    SURVIVALIST("Survivalist"),
    WARRIOR("Warrior"),
    BUILDER("Builder"),
    FARMER("Farmer"),
    WITCH_DOCTOR("Witch Doctor"),
    MINER("Miner"),
    FISHERMAN("Fisherman"),
    BLACKSMITH("Blacksmith"),
    ENCHANTER("Enchanter"),
    ;

    private String humanName;

    JobType(String humanName) {
        this.humanName = humanName;
    }

    @Override
    public String toString() {
        return humanName;
    }
}
