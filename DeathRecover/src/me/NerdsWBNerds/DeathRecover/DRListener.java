package me.NerdsWBNerds.DeathRecover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DRListener implements Listener {
	public DeathRecover plugin;
	
	public DRListener(DeathRecover pt) {
		plugin = pt;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player player = e.getEntity();

		if(player.hasPermission("deathrecover.nodeathdrops")){
			e.getDrops().clear();
		}
		
		if(!player.hasPermission("deathrecover.recover"))
			return;
		
		DeathRecover.lostItems.put(player.getName().toLowerCase(), player.getInventory().getContents());
		DeathRecover.lostArmour.put(player.getName().toLowerCase(), player.getInventory().getArmorContents());
	}
}
