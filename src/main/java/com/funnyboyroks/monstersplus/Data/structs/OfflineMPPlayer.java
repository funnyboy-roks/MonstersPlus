package com.funnyboyroks.monstersplus.Data.structs;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OfflineMPPlayer {

    private List<PlayerJob> jobs;

    public OfflineMPPlayer() {
        jobs = new ArrayList<>();
    }

    public List<PlayerJob> getJobs() {
        return jobs;
    }

    public PlayerJob getJob(JobType type) {
        return getJobs()
            .stream()
            .filter(j -> j.isType(type))
            .findFirst()
            .orElse(null)
            ;
    }

    public boolean hasJob(JobType type) {
        return getJob(type) != null;
    }

}
