package com.javabuckets.deathswap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        for (Player p : CommandDeathswap.contestants) {
            if (p.getUniqueId() == player.getUniqueId()) {
                CommandDeathswap.stopDeathswap();
            }
        }
    }
}
