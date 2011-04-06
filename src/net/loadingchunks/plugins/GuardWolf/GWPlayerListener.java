package net.loadingchunks.plugins.GuardWolf;

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
		String result = this.plugin.sql.CheckBan(p.getPlayer().getName());
		if(result != null)
		{
			p.disallow(PlayerLoginEvent.Result.KICK_OTHER, result);
		}
	}
}
