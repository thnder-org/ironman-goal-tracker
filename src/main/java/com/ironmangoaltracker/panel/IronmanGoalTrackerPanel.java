package com.ironmangoaltracker.panel;

import com.ironmangoaltracker.IronmanGoalTrackerConfig;
import com.ironmangoaltracker.IronmanGoalTrackerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.Text;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class IronmanGoalTrackerPanel extends PluginPanel {

    private final GoalOverviewPanel goalOverviewPanel;
    private final FixedWidthPanel goalOverviewWrapper = new FixedWidthPanel();
    private JPanel searchGoalsPanel;
    private final IconTextField searchBar = new IconTextField();
    private final FixedWidthPanel goalListPanel = new FixedWidthPanel();
    private final FixedWidthPanel goalListWrapper = new FixedWidthPanel();
    private final JScrollPane scrollableContainer;
    private final ArrayList<GoalSelectPanel> goalSelectPanels = new ArrayList<>();


   IronmanGoalTrackerPlugin ironmanGoalTrackerPlugin;

    public IronmanGoalTrackerPanel(IronmanGoalTrackerPlugin ironmanGoalTrackerPlugin) {
        super(false);
        this.ironmanGoalTrackerPlugin = ironmanGoalTrackerPlugin;

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        /* Setup overview panel */
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());

        JLabel title = new JLabel();
        title.setText("Ironman Goal Tracker");
        title.setForeground(Color.WHITE);
        titlePanel.add(title, BorderLayout.WEST);

        // Options
        final JPanel viewControls = new JPanel(new GridLayout(1, 3, 10, 0));
        viewControls.setBackground(ColorScheme.DARK_GRAY_COLOR);

        /* Search bar */
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onSearchBarChanged();
            }
        });

        searchGoalsPanel = new JPanel();
        searchGoalsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchGoalsPanel.setLayout(new BorderLayout(0, BORDER_OFFSET));
        searchGoalsPanel.add(searchBar, BorderLayout.CENTER);
//        searchGoalsPanel.add(allGoalsCompletedPanel, BorderLayout.SOUTH);

        goalListPanel.setBorder(new EmptyBorder(8, 10, 0, 10));
        goalListPanel.setLayout(new DynamicGridLayout(0, 1, 0, 5));
        goalListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        showMatchingGoals("");
//
//        // Filters
//        filterDropdown = makeNewDropdown(IronmanGoalTrackerConfig.QuestFilter.displayFilters(), "filterListBy");
//        JPanel filtersPanel = makeDropdownPanel(filterDropdown, "Filters");
//        filtersPanel.setPreferredSize(new Dimension(PANEL_WIDTH, DROPDOWN_HEIGHT));
//
//        difficultyDropdown = makeNewDropdown(QuestDetails.Difficulty.values(), "questDifficulty");
//        JPanel difficultyPanel = makeDropdownPanel(difficultyDropdown, "Difficulty");
//        difficultyPanel.setPreferredSize(new Dimension(PANEL_WIDTH, DROPDOWN_HEIGHT));
//
//        orderDropdown = makeNewDropdown(QuestHelperConfig.QuestOrdering.values(), "orderListBy");
//        JPanel orderPanel = makeDropdownPanel(orderDropdown, "Ordering");
//        orderPanel.setPreferredSize(new Dimension(PANEL_WIDTH, DROPDOWN_HEIGHT));
//
//        allDropdownSections.setBorder(new EmptyBorder(0, 0, 10, 0));
//        allDropdownSections.setLayout(new BorderLayout(0, BORDER_OFFSET));
//        allDropdownSections.add(filtersPanel, BorderLayout.NORTH);
//        allDropdownSections.add(difficultyPanel, BorderLayout.CENTER);
//        allDropdownSections.add(orderPanel, BorderLayout.SOUTH);
//
//        searchQuestsPanel.add(allDropdownSections, BorderLayout.NORTH);

        // Wrapper
        goalListWrapper.setLayout(new BorderLayout());
        goalListWrapper.add(goalListPanel, BorderLayout.NORTH);

        scrollableContainer = new JScrollPane(goalListWrapper);
        scrollableContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel introDetailsPanel = new JPanel();
        introDetailsPanel.setLayout(new BorderLayout());
        introDetailsPanel.add(titlePanel, BorderLayout.NORTH);
        introDetailsPanel.add(searchGoalsPanel, BorderLayout.CENTER);

        add(introDetailsPanel, BorderLayout.NORTH);
        add(scrollableContainer, BorderLayout.CENTER);

        /* Layout */
        goalOverviewPanel = new GoalOverviewPanel(ironmanGoalTrackerPlugin);

        goalOverviewWrapper.setLayout(new BorderLayout());
        goalOverviewWrapper.add(goalOverviewPanel, BorderLayout.NORTH);
    }

    private void onSearchBarChanged()
    {
        final String text = searchBar.getText();

        if ((goalOverviewPanel.currentGoal == null || !text.isEmpty()))
        {
            scrollableContainer.setViewportView(goalListWrapper);
            goalSelectPanels.forEach(goalListPanel::remove);
            showMatchingGoals(text);
        }
        else
        {
            scrollableContainer.setViewportView(goalOverviewWrapper);
        }
        revalidate();
    }

    private void showMatchingGoals(String text)
    {
        if (text.isEmpty())
        {
            goalSelectPanels.forEach(goalListPanel::add);
            return;
        }

        final String[] searchTerms = text.toLowerCase().split(" ");

        questSelectPanels.forEach(listItem ->
        {
            if (Text.matchesSearchTerms(Arrays.asList(searchTerms), listItem.getKeywords()))
            {
                goalListPanel.add(listItem);
            }
        });
    }
}


