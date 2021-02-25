package me.SheriFFu.oxplugin.configuration;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RewardInventory {
    private final Main plugin;
    public Inventory content;

    public RewardInventory(Main plugin) {
        this.plugin = plugin;
        content = Bukkit.createInventory(null, 9, "Premii");

        if(plugin.oxGame.reward != null && plugin.oxGame.reward.length > 0) {
            loadContents();
        }
    }

    void loadContents() {
        for(ItemStack stack : plugin.oxGame.reward) {
            content.addItem(stack);
        }
    }

    public ItemStack[] getContents() {
        return content.getContents();
    }
}
