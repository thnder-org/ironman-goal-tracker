package com.ironmangoaltracker;

import com.ironmangoaltracker.IronmanGoalTrackerPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class IronmanGoalTrackerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(IronmanGoalTrackerPlugin.class);
		RuneLite.main(args);
	}
}