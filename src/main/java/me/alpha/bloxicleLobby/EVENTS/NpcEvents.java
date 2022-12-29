package me.alpha.bloxicleLobby.EVENTS;

import me.alpha.bloxicleLobby.BloxicleLobby;
import me.alpha.bloxicleLobby.NPC.BloxicleNPC;
import me.alpha.bloxicleLobby.Sounds;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NpcEvents implements Listener {
    @EventHandler
    public void NpcClickEvent(NPCRightClickEvent event){

        BloxicleLobby.pitNPC.npcClickEvent(event);
    }

    @EventHandler
    public void CancelDamage(EntityDamageByEntityEvent event){
        event.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void entityDamageEvent(EntityDamageEvent event) {

        if(event.getCause()== EntityDamageEvent.DamageCause.VOID){
            event.getEntity().teleport(event.getEntity().getWorld().getSpawnLocation());
            if(event.getEntity().getType().equals(EntityType.PLAYER) &&
                    !CitizensAPI.getNPCRegistry().isNPC((Player) event.getEntity())){
                Player player = (Player)event.getEntity();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lOOPS! &7you fell out of the void!"));
                Sounds.ERROR.play(player);
            }
            event.setCancelled(true);
        }

        event.setCancelled(true);

    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onFoodLevelChange (FoodLevelChangeEvent event) {
        if (event.getEntityType () != EntityType.PLAYER) return;
        event.setFoodLevel(20);
        event.setCancelled(true);
    }
}
