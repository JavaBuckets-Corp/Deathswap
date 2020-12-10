package com.javabuckets.deathswap;

import org.bukkit.plugin.java.JavaPlugin;

public final class Deathswap extends JavaPlugin {


    @Override
    public void onEnable() {
        this.getCommand("deathswap").setExecutor(new CommandDeathswap());

        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
