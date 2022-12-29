package me.alpha.bloxicleLobby.COMMANDS;

import me.alpha.bloxicleLobby.BloxicleLobby;
import me.alpha.bloxicleLobby.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("play")){
            Player player = (Player) sender;

            if(args.length<1){
                player.sendMessage(ChatColor.GREEN + "Right click with the compass in a lobby to select a game!");
                return true;
            }

            if(args[0].equalsIgnoreCase("pit")){
                String server = "pit-m2";

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80,2, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80,100, true));
                player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',"&a&lSERVER FOUND! &7Sending to " + server));
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
                return true;
            }


        }

        return true;
    }
}
