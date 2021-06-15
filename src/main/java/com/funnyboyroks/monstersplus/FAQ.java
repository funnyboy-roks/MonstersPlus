package com.funnyboyroks.monstersplus;

import com.google.common.collect.ImmutableMap;
import net.md_5.bungee.api.ChatColor;

import java.util.Map;

public class FAQ {

    public static final Map<String, String> items = new ImmutableMap.Builder<String, String>()
        .put("jobs", ChatColor.GOLD + "Jobs: " + ChatColor.WHITE
            + "Choosing a job allows you to earn money by doing various activities. "
            + "Each job grants unique features only obtainable for each profession. "
            + "Each player may have up to 3 jobs. Certain jobs allow players to get custom enchantments and disguises. "
            + "To learn more about jobs type: " + ChatColor.RED
            + "/jobs, /jobs help, /jobs browse, /jobs join, /faq jobs explist"
            + "/faq survivalist, /faq warrior, /faq miner, /faq fisherman, /faq witchdoctor,"
            + "/faq farmer, /faq enchanter, /faq builder, /faq blacksmith, /faq customenchant, /faq disguise, /faq bombs")
        .put("", "")
        .build();

}
