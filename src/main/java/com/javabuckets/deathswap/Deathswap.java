package com.javabuckets.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class Deathswap extends JavaPlugin {

    public static boolean isRunning = false;

    public static ArrayList<Player> contestants = new ArrayList<>();

    private static int timer = 60 * 5;

    @Override
    public void onEnable() {
        this.getCommand("deathswap").setExecutor(new CommandDeathswap());

        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    // When timer runs out call the deathswap function
                    if (timer < 0) {
                        deathswap();
                    }

                    if (timer < 10) {
                        contestants.forEach(contestant -> contestant.sendMessage(ChatColor.RED + String.valueOf(timer) + "!"));
                    }

                    // Last thing to do is to decrease timer
                    timer--;
                }
            }
        }, 0, 20);
    }

    public static void initialize() {
        Bukkit.broadcastMessage("Deathswap begins in 10 seconds!");
        contestants.forEach(contestant -> contestant.setGameMode(GameMode.SURVIVAL));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isRunning = true;
                Bukkit.broadcastMessage("Swapping in 5 minutes!");
            }
        }, 10000);
    }

    private static void deathswap() {
        timer = 60 * 5;

        for (int i = 0; i < contestants.size(); i++) {
            int next = (i+1 > contestants.size()) ? 0 : i+1;

            Location a = contestants.get(i).getLocation();
            Location b = contestants.get(next).getLocation();

            contestants.get(i).teleport(b);
            contestants.get(next).teleport(a);
        }
    }

    public static void contestantDied(Player contestant) {
        contestants.remove(contestant);

        if (contestants.size() == 1) {
            Bukkit.broadcastMessage(contestants.get(0).getDisplayName() + " is the winner!");
            stopDeathswap();
        }

        contestant.setGameMode(GameMode.SPECTATOR);
    }

    public static void stopDeathswap() {
        Bukkit.broadcastMessage("Deathswap ended!");
        isRunning = false;
        timer = 60 * 5;
        contestants.clear();
    }

    @Override
    public void onDisable() {

    }
}
