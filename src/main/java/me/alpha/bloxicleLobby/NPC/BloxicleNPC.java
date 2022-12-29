package me.alpha.bloxicleLobby.NPC;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.alpha.bloxicleLobby.BloxicleLobby;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import net.citizensnpcs.trait.SkinTrait;

public abstract class BloxicleNPC{

    boolean useHologram;

    EntityType entityType;
    NPC npc;
    Hologram hologram;

    String name;
    String hologramName;

    public BloxicleNPC(EntityType entityType, String name, boolean useHologram){
        this.entityType = entityType;
        this.name = name;
        this.useHologram = useHologram;
    }

    public NPC createNPC(){
        if(this.npc!=null) return this.npc;

        this.npc = CitizensAPI.getNPCRegistry().createNPC(this.entityType,
                ChatColor.translateAlternateColorCodes('&', this.name));

        return this.npc;
    }

    public void skinNPC(String name){
        if(!(this.npc.getEntity() instanceof Player)) return;

        SkinTrait skinTrait = npc.getTrait(SkinTrait.class);
        skinTrait.setSkinName(name);

        LookClose lookClose = npc.getTrait(LookClose.class);
        lookClose.lookClose(true);
    }

    public void spawnNPC(Location location){
        if(!this.npc.isSpawned()) this.npc.spawn(location, SpawnReason.CREATE);

        this.npc.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public void removeNPC(){
        if(!npc.isSpawned()) return;

        npc.despawn();
        npc.destroy();

        removeHologram();
    }

    public void setHologramText(String text){
        this.hologramName = ChatColor.translateAlternateColorCodes('&', text);
    }

    public Hologram createHologram(){
        if(hologram!=null) return this.hologram;

        hologram = HologramsAPI.createHologram(BloxicleLobby.INSTANCE, npc.getEntity().getLocation().add(0, 2.75, 0));

        hologram.appendTextLine(this.hologramName);

        return hologram;
    }

    public void removeHologram(){
        if(hologram==null) return;

        if(!hologram.isDeleted()) hologram.delete();
    }

    public abstract void doNPC(World world);
    public abstract void npcClickEvent(NPCRightClickEvent event);

}
