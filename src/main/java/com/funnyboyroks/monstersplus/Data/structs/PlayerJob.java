package com.funnyboyroks.monstersplus.Data.structs;

public class PlayerJob {

    private final JobType type;
    private int level;

    public PlayerJob(JobType type) {
        this.type = type;
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void changeLevel(int diff) {
        this.level += diff;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public JobType getType() {
        return type;
    }

    public boolean isType(JobType type) {
        return this.type == type;
    }
}
