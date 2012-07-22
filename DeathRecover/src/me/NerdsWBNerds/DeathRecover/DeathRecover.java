package me.NerdsWBNerds.DeathRecover;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathRecover extends JavaPlugin{
	public static HashMap<String, ItemStack[]> lostItems = new HashMap<String, ItemStack[]>();
	public static HashMap<String, ItemStack[]> lostArmour = new HashMap<String, ItemStack[]>();
	
	public Logger log;
	
	public void onEnable(){
		log = getServer().getLogger();
		
		getServer().getPluginManager().registerEvents(new DRListener(this), this);
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("recover")){
				if(player.hasPermission("deathrecover.recover")){
					recoverInv(player);
				}else{
					player.sendMessage(ChatColor.RED + "You do not have permission to do this.");
					log.info(player.getName() + " tried to recover his inventory, but was denied permission. Permission required. deathrecover.recover");
				}
				
				return true;
			}
		}else{
			log.info("Sorry, this command can only be performed by people.");
			return true;
		}
		
		return false;
	}
	
	public void recoverInv(Player p){
		if(canRecover(p)){
			try{
				recoverItems(p);
				recoverArmour(p);	
		
				p.sendMessage(ChatColor.GREEN + "Your inventory has been recovered.");
				log.info(p.getName() + " has recovered his inventory.");

			}catch(Exception e){
				p.sendMessage(ChatColor.RED + "Error recovering inventory.");
			}
		}else{
			p.sendMessage(ChatColor.RED + "You have no items to recover.");
		}
	}
	
	public boolean canRecover(Player p){
		if(lostItems.containsKey(p.getName().toLowerCase()))
			return true;
		
		if(lostArmour.containsKey(p.getName().toLowerCase()))
			return true;
		
		return false;
	}
	
	public void recoverItems(Player p){
		p.getInventory().setContents(lostItems.get(p.getName().toLowerCase()));
		lostItems.remove(p.getName().toLowerCase());
	}
	
	public void recoverArmour(Player p){
		p.getInventory().setArmorContents(lostArmour.get(p.getName().toLowerCase()));
		lostArmour.remove(p.getName().toLowerCase());
	}
}
