package net.loadingchunks.plugins.GuardWolf;

import java.util.Collections;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
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
		} else if (this.plugin.maintenanceMode && !this.plugin.perm.MMAccess(pl))
			p.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.plugin.gwConfig.get("maintenance_message"));
	}
	
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean done = false;
		Random gen = new Random();
		int rand = 0;
		if(this.plugin.GWGimped.contains(player.getName().toLowerCase()))
		{
			do {
				rand = gen.nextInt(this.plugin.GimpLines.size());
				if(this.plugin.GimpLines.get(rand) != null)
					done = true;
			} while (done == false);
			event.setMessage(this.plugin.GimpLines.get(rand).toString());
		}
	}
}
