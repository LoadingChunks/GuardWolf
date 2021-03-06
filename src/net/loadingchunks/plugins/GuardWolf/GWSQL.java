package net.loadingchunks.plugins.GuardWolf;

import java.sql.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Handler for the /gw sample command.
 * @author Cue
 */
public class GWSQL {
	public Connection con;
	public Statement stmt;
	public final GuardWolf plugin;
	
	public GWSQL(GuardWolf plugin)
	{
		this.plugin = plugin;
	}
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(this.plugin.gwConfig.get("db_address"), this.plugin.gwConfig.get("db_user"), this.plugin.gwConfig.get("db_pass"));
		} catch ( SQLException e )
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	public void Ban(String name, String banner, long time, String reason, int permanent)
	{
		Integer strike = 1;
		try {
			PreparedStatement stat = con.prepareStatement("INSERT INTO `" + this.plugin.gwConfig.get("db_table") + "`" +
					"(`user`,`country`,`banned_at`,`expires_at`,`reason`,`banned_by`,`strike`,`strike_expires`,`unbanned`,`permanent`)" +
					" VALUES (?,?,NOW(),FROM_UNIXTIME(?),?,?,?,NOW(),0,?)"
					);
			stat.setString(1, name);
			stat.setString(2, "?");
			stat.setLong(3, time);
			stat.setString(4, reason);
			stat.setString(5, banner);
			stat.setInt(6, strike);
			stat.setInt(7, permanent);
			stat.execute();
		} catch ( SQLException e ) { e.printStackTrace(); }
	}
	
	public void UnBan(String name, String unbanner)
	{
		try {
			PreparedStatement stat = con.prepareStatement("UPDATE `" + this.plugin.gwConfig.get("db_table") + "` SET `unbanned` = 1 WHERE `user` = ?");
			stat.setString(1, name);
			stat.execute();
		} catch ( SQLException e ) { e.printStackTrace(); }
 	}
	
	public void Stats()
	{
		try {
			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery("SELECT COUNT(*) as counter FROM `" + this.plugin.gwConfig.get("db_table") + "`");
			result.next();
			System.out.println("Ban Records: " + result.getInt("counter"));
		} catch ( SQLException e ) { e.printStackTrace(); }
	}
	
	public String CheckBan(String user)
	{
		System.out.println("[GW] Checking ban status...");
		try {
			PreparedStatement stat = con.prepareStatement("SELECT * FROM `" + this.plugin.gwConfig.get("db_table") + "` WHERE (expires_at > NOW() OR `permanent` = 1) AND `user` = ? AND `unbanned` = 0 ORDER BY id DESC");
			stat.setString(1, user);
			ResultSet result = stat.executeQuery();
			if(result.last())
			{
				if(result.getInt("permanent") == 1)
					return result.getString("reason") + " (Permanent Ban)";
				else
					return result.getString("reason") + " (Expires " + result.getString("expires_at") + ")";
			} else return null;
		} catch ( SQLException e ) { e.printStackTrace(); }
		return null;
	}
	
	public void ListBan(int page, String user, CommandSender sender)
	{
		String tempString = "";
		if(user.isEmpty())
		{
			try {
				PreparedStatement statc = con.prepareStatement("SELECT COUNT(DISTINCT `user`) as c FROM `" + this.plugin.gwConfig.get("db_table") + "`");
				ResultSet resultc = statc.executeQuery();
				
				resultc.first();
				
				int pageCount = (int)Math.ceil((Double.parseDouble(resultc.getString("c")) / Double.parseDouble(this.plugin.gwConfig.get("per_page").toString())));
				
				sender.sendMessage(ChatColor.DARK_AQUA + "------------ Page " + page + "/" + pageCount + " ------------");
				
				PreparedStatement stat = con.prepareStatement("SELECT *,COUNT(*) as c FROM `" + this.plugin.gwConfig.get("db_table") + "` GROUP BY `user` ORDER BY `permanent`,`expires_at` DESC LIMIT " + ((page - 1)*(Integer.parseInt(this.plugin.gwConfig.get("per_page")))) + "," + (Integer.parseInt(this.plugin.gwConfig.get("per_page"))));
				ResultSet result = stat.executeQuery();
				
				if(!result.last())
					sender.sendMessage(ChatColor.RED + "No bans found.");
				else {
					result.first();
					do
					{
						sender.sendMessage("- " + ChatColor.WHITE + result.getString("user") + " (" + result.getInt("c") + " bans found)");
					} while(result.next());
				}
			return;
			} catch ( SQLException e ) { e.printStackTrace(); }
		} else {
			try {
				PreparedStatement stat = con.prepareStatement("SELECT * FROM `" + this.plugin.gwConfig.get("db_table") + "` WHERE `user` = ? ORDER BY `permanent`,`expires_at` DESC LIMIT " + ((page - 1)*(Integer.parseInt(this.plugin.gwConfig.get("per_page")))) + "," + (Integer.parseInt(this.plugin.gwConfig.get("per_page"))));
				stat.setString(1, user);
				ResultSet result = stat.executeQuery();
				
				if(!result.last())
					sender.sendMessage(ChatColor.RED + "No bans found for this user.");
				else {
					result.first();
					do
					{
						tempString = "- " + ChatColor.WHITE + result.getString("reason");
						if(result.getInt("permanent") == 1)
							tempString = tempString + " (Permanent)";
						else
							tempString = tempString + " (Expires: " + result.getString("expires_at") + ")";
						
						if(result.getInt("unbanned") == 1)
							tempString = tempString + ChatColor.GREEN + " (UNBANNED)";
						sender.sendMessage(tempString);
					} while (result.next());
				}
			return;
			} catch ( SQLException e ) { e.printStackTrace(); }
		}
		sender.sendMessage(ChatColor.RED + "Error getting Ban List!");
	}
}
