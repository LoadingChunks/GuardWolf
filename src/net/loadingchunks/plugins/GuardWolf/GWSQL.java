package net.loadingchunks.plugins.GuardWolf;

import java.sql.*;

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
			this.con = DriverManager.getConnection(this.plugin.gwConfig.get("db_name"), this.plugin.gwConfig.get("db_user"), this.plugin.gwConfig.get("db_pass"));
		} catch ( SQLException e )
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
	}
}
