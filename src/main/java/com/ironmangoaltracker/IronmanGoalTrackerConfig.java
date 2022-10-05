package com.ironmangoaltracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.PluginDescriptor;


@PluginDescriptor(
		name = "Ironman Goal Tracker",
		description = "Helps find out everything you need for a goal item, quest, or diary",
		tags = { "goals", "tracker", "overlay" }
)

@ConfigGroup("ironmangoaltracker")
public interface IronmanGoalTrackerConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Testing the Plugin",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}
}
