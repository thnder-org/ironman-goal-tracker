package com.ironmangoaltracker.panel;

        import com.ironmangoaltracker.Icon;
        import com.ironmangoaltracker.IronmanGoalTrackerPlugin;
        import com.ironmangoaltracker.ironmangoaltrackers.IronmanGoalTracker;
        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import javax.swing.ImageIcon;
        import javax.swing.JButton;
        import javax.swing.JLabel;
        import javax.swing.JPanel;
        import lombok.Getter;
        import net.runelite.api.QuestState;
        import net.runelite.client.ui.ColorScheme;
        import net.runelite.client.ui.PluginPanel;

public class GoalSelectPanel extends JPanel
{
    @Getter
    private final List<String> keywords = new ArrayList<>();

    @Getter
    private final IronmanGoalTracker ironmanGoalTracker;

    private static final ImageIcon START_ICON = Icon.START.getIcon();

    public GoalSelectPanel(IronmanGoalTrackerPlugin ironmanGoalTrackerPlugin, IronmanGoalTrackerPanel ironmanGoalTrackerPanel, IronmanGoalTracker ironmanGoalTracker, GoalState goalState)
    {
        this.ironmanGoalTracker = ironmanGoalTracker;

        keywords.addAll(ironmanGoalTracker.getGoal().getKeywords());

        setLayout(new BorderLayout(3, 0));
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 20));

        JLabel nameLabel = new JLabel(ironmanGoalTracker.getGoal().getName());
        Color color = goalState == GoalState.FINISHED ? ironmanGoalTrackerPlugin.getConfig().passColour() : (goalState == GoalState.IN_PROGRESS ?
                new Color(240,207, 123) : Color.WHITE);
        nameLabel.setForeground(color);
        add(nameLabel, BorderLayout.CENTER);

        if (goalState != GoalState.FINISHED)
        {
            JButton startButton = new JButton();
            startButton.setIcon(START_ICON);
            startButton.addActionListener(e ->
            {
                ironmanGoalTracker.setSidebarSelectedGoal(ironmanGoalTracker);
                ironmanGoalTrackerPanel.emptyBar();
            });
            add(startButton, BorderLayout.LINE_END);
        }
    }

    public GoalSelectPanel(String text)
    {
        this.ironmanGoalTracker = null;

        setLayout(new BorderLayout(3, 3));
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel nameLabel = new JLabel(text);
        Color color = Color.WHITE;
        nameLabel.setForeground(color);
        add(nameLabel, BorderLayout.CENTER);
    }
}