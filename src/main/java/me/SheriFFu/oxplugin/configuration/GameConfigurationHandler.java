package me.SheriFFu.oxplugin.configuration;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;


public class GameConfigurationHandler {
    public GameConfigInventory config;
    public RewardInventory rewardInventory;
    public Player editor;
    //public boolean editingRewards;
    private final Main plugin;

    public final int limits[][];

    public GameConfigurationHandler(Main plugin, Player player) {
        this.plugin = plugin;
        this.editor = player;
        //editingRewards = false;

        config = new GameConfigInventory(plugin);
        editor.openInventory(config.configInventory);
        rewardInventory = new RewardInventory(plugin);

        limits = new int[][] {{1, 1, 10}, {20, 2, 60}};
    }

    public void addQuantityToSlot(int slot, int add) {
        int setting = slot % 9;
        int amount =  config.configInventory.getItem(setting + 9).getAmount();
        int newAmount = amount + add;

        if(add == -1 && newAmount < limits[0][setting])
        {
            return;
        }

        if(add == 1 && newAmount > limits[1][setting])
        {
            return;
        }

        config.configInventory.getItem(9 + setting).setAmount(newAmount);
    }

    public void saveSettings() {
        int rounds = config.configInventory.getItem(9).getAmount();
        boolean random = (config.configInventory.getItem(10).getAmount() == 1);
        int timer = config.configInventory.getItem(11).getAmount();

        plugin.oxGame.saveGameSettings(rounds, random, timer);
        //plugin.disableGameEditor();
    }

    public void openRewardsInventory() {
        //editingRewards = true;
        editor.openInventory(rewardInventory.content);
    }

    public void saveRewards() {
        //editingRewards = false;
        plugin.oxGame.saveRoundRewards(rewardInventory.content.getContents());
    }
}
