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
		
	}
}
