package me.alpha.bloxicleLobby;

import me.alpha.bloxicleLobby.COMMANDS.Commands;
import me.alpha.bloxicleLobby.EVENTS.NpcEvents;
import me.alpha.bloxicleLobby.NPC.PitNPC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class BloxicleLobby extends JavaPlugin {

    public static BloxicleLobby INSTANCE;

    // NPC INSTANCES
    public static PitNPC pitNPC = new PitNPC(EntityType.PLAYER);

    @Override
    public void onEnable() {
        INSTANCE = this;

        pitNPC.doNPC(Bukkit.getWorld("world"));

        getServer().getPluginManager().registerEvents(new NpcEvents(), INSTANCE);

        Commands commands = new Commands();

        INSTANCE.getCommand("play").setExecutor(commands);
    }

    @Override
    public void onDisable() {
        pitNPC.removeNPC();
    }

}
