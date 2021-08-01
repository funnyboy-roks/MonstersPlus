package com.funnyboyroks.monstersplus.Utils;

import com.funnyboyroks.monstersplus.Data.structs.JobType;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils {

    public static boolean addJobXp(UUID uuid, String jobName, double xp) {
        Job job = Jobs.getJob(jobName);
        JobsPlayer jp = Jobs.getPlayerManager().getJobsPlayer(uuid);
        JobProgression progress = jp.getJobProgression(job);
        if(jp.isInJob(job)) {
            progress.addExperience(xp);
        }
        return jp.isInJob(job);
    }

    public static boolean addJobXp(UUID uuid, JobType jobType, double xp) {
        return addJobXp(uuid, jobType.name, xp);
    }

    public static JobsPlayer getJP(UUID uuid) {
        return Jobs.getPlayerManager().getJobsPlayer(uuid);
    }

    public static JobsPlayer getJP(Player player) {
        return Jobs.getPlayerManager().getJobsPlayer(player.getUniqueId());
    }

    public static boolean hasJob(JobsPlayer jp, JobType type) {
        Job job = Jobs.getJob(type.name);
        return jp.isInJob(job);
    }

    public static boolean hasJob(Player player, JobType type) {
        return hasJob(getJP(player), type);
    }

    public static int getJobLvl(Player player, JobType type) {
        return hasJob(player, type) ? getJP(player).getJobProgression(Jobs.getJob(type.name)).getLevel() : 0;
    }

}
