package net.loadingchunks.plugins.GuardWolf;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GWPermissions {
	  public static PermissionHandler permissionHandler;
	  private GuardWolf plugin;
	  
	  public GWPermissions(GuardWolf instance) {
		  this.plugin = instance;
	  }
	  
	  public void setupPermissions() {
	      Plugin permissionsPlugin = this.plugin.getServer().getPluginManager().getPlugin("Permissions");

	      if (this.permissionHandler == null) {
	          if (permissionsPlugin != null) {
	              this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	          } else {
	              System.out.println("[GUARDWOLF] Permission system not detected, defaulting to OP");
	          }
	      }
	  }
	  
	  public boolean MMAccess(Player p) // Can access server during maintenance
	  {
		  return this.permissionHandler.has(p, "guardwolf.gw.can_access_mm");
	  }
	  
	  public boolean ToggleMM(Player p) // Can enable/disable maintenance mode
	  {
		  return this.permissionHandler.has(p, "guardwolf.gw.maintenance");
	  }
	  
	  public boolean canBan(Player p) // Can ban
	  {
		  return this.permissionHandler.has(p, "guardwolf.ban.ban");
	  }
	  
	  public boolean canBanList(Player p) // Can list bans
	  {
		  return this.permissionHandler.has(p, "guardwolf.ban.list");
	  }
	  
	  public boolean canUnban(Player p) // Can unban
	  {
		  return this.permissionHandler.has(p, "guardwolf.ban.unban");
	  }
	  
	  public boolean canGimp(Player p) // Can gimp
	  {
		  return this.permissionHandler.has(p, "guardwolf.gimp.gimp");
	  }
	  
	  public boolean canUnGimp(Player p) // Can ungimp
	  {
		  return this.permissionHandler.has(p, "guardwolf.gimp.ungimp");
	  }
	
}
