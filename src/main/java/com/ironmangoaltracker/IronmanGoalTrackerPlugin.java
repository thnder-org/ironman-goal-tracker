package com.ironmangoaltracker;

import com.google.inject.Provides;
import javax.inject.Inject;

import com.ironmangoaltracker.panel.IronmanGoalTrackerPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.ClientToolbar;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@PluginDescriptor(
		name = "Ironman Goal Tracker",
		description = "Helps find out everything you need for a goal item, quest, or diary",
		tags = { "goals", "tracker", "overlay" }
)
public class IronmanGoalTrackerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private IronmanGoalTrackerConfig config;

	private IronmanGoalTrackerPanel panel;

	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;


	@Override
	protected void startUp() throws IOException
	{
		final BufferedImage icon = Icon.TRACKER_ICON.getImage();

		IronmanGoalTrackerPanel panel = new IronmanGoalTrackerPanel(this);
		navButton = NavigationButton.builder()
				.tooltip("Ironman Goal Tracker")
				.icon(icon)
				.priority(7)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	IronmanGoalTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(IronmanGoalTrackerConfig.class);
	}
}
