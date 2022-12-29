package me.alpha.bloxicleLobby.NPC;

import me.alpha.bloxicleLobby.BloxicleLobby;
import me.alpha.bloxicleLobby.Sounds;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.bukkit.Bukkit.getServer;

public class PitNPC extends BloxicleNPC {
    public PitNPC(EntityType entityType) {
        super(entityType, "&7back to lobby", true);
    }

    @Override
    public void doNPC(World world) {
        createNPC();

        spawnNPC(new Location(world, -8.5, 58, -26.5));

        this.skinNPC("EnderHunter242");

        this.setHologramText("&2&lTHE KEEPER");

        this.createHologram();
    }

    @Override
    public void npcClickEvent(NPCRightClickEvent event) {
        if(event.getNPC().equals(this.npc)){
            // Do action

            Player player = event.getClicker();
            String server = "pit-m1";

            if (player.getServer().getName().equalsIgnoreCase(server)) {
                player.sendMessage(ChatColor.RED+"You are already connected to the Pit!");
                return;
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80,2, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80,100, true));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&lSERVER FOUND! &7Sending to " + server));
            Sounds.MEGA_RNGESUS.play(player);


            Bukkit.getScheduler().scheduleSyncDelayedTask(BloxicleLobby.INSTANCE, new Runnable() {
                @Override
                public void run() {

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);

                    try {
                        out.writeUTF("Connect");
                        out.writeUTF(server); // Target Server
                    } catch (IOException e) {
                        // Can never happen
                    }

                    getServer().getMessenger().registerOutgoingPluginChannel(BloxicleLobby.INSTANCE, "BungeeCord");
                    player.sendPluginMessage(BloxicleLobby.INSTANCE, "BungeeCord", b.toByteArray());
                }
            }, 60);

        }
    }
}
