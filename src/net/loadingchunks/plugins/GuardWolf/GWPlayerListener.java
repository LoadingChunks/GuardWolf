package net.loadingchunks.plugins.GuardWolf;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class GWPlayerListener extends PlayerListener {
	private final GuardWolf plugin;
	
	public GWPlayerListener(GuardWolf plugin)
	{
		this.plugin = plugin;
	}
	
	public void onPlayerLogin(PlayerLoginEvent p)
	{
		Player pl = p.getPlayer();
		String result = this.plugin.sql.CheckBan(p.getPlayer().getName());
		if(result != null)
		{
			p.disallow(PlayerLoginEvent.Result.KICK_OTHER, result);
		} else if (this.plugin.maintenanceMode && !this.plugin.gm.getWorldsHolder().getWorldPermissions(pl).has(pl, "guardwolf.gw.can_access_mm"))
			p.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.plugin.gwConfig.get("maintenance_message"));
	}
}
