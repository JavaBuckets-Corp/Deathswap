package com.javabuckets.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CommandDeathswap implements CommandExecutor {

    protected static ArrayList<Player> contestants = new ArrayList<>();
    private static int taskId;
    private static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player playerSender = (Player) sender;

            if (contestants.size() != 0) {
                playerSender.sendMessage("A Deathswap is already in progress.");
                return false;
            }

            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);

                contestants.add(playerSender);
                contestants.add(player);

                playerSender.sendMessage("You are now in a deathswap with " + player.getDisplayName());
                player.sendMessage("You are now in a deathswap with " + playerSender.getDisplayName());

                Bukkit.broadcastMessage("Deathswap begins in 10 seconds!");

                // Start
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (args.length > 1) {
                            deathswap(Integer.valueOf(args[1]));
                        } else {
                            deathswap(5);
                        }
                    }
                },10000);

                return true;
            }
            return false;
        }
        return false;
    }

    private void deathswap(int minutes) {
        int interval = minutes * 60 * 20; // minutes * seconds_converter * tick_converter

        taskId = scheduler.scheduleSyncRepeatingTask(Deathswap.getPlugin(Deathswap.class), new Runnable() {
            public void run() {
                Location a = contestants.get(0).getLocation();
                Location b = contestants.get(1).getLocation();

                contestants.get(0).teleport(b);
                contestants.get(1).teleport(a);
            }
        }, interval, interval);
    }

    protected static void stopDeathswap() {
        Bukkit.broadcastMessage("Deathswap ended!");
        scheduler.cancelTask(taskId);
        contestants.clear();
    }
}
