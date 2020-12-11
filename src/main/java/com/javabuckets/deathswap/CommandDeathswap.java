package com.javabuckets.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeathswap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player playerSender = (Player) sender;

            if (args.length == 1 && args[0].equals("stop")) {
                Deathswap.stopDeathswap();
                return true;
            }

            if (Deathswap.isRunning) {
                playerSender.sendMessage("A Deathswap is already in progress.");
                return true;
            }

            if (args.length == 0) {
                playerSender.sendMessage("Please specify at least one player to play with");
                return true;
            }

            Deathswap.contestants.add(playerSender);

            for (String arg : args) {
                Player player = Bukkit.getPlayer(arg);

                Deathswap.contestants.add(player);
            }

            Deathswap.initialize();

            return true;
        }
        return false;
    }


}
