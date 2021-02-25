package me.SheriFFu.oxplugin.listeners;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryInteractionListener implements Listener {
    private final Main plugin;

    public InventoryInteractionListener(Main pl) {
        this.plugin = pl;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {
        if(plugin.configHandler.config.configInventory != null && event.getInventory().equals(plugin.configHandler.config.configInventory)) {
            plugin.configHandler.saveSettings();
        }
        else if(plugin.configHandler.rewardInventory.content != null && event.getInventory().equals(plugin.configHandler.rewardInventory.content)) {
            plugin.configHandler.saveRewards();
            //plugin.disableGameEditor();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().equals(plugin.configHandler.config.configInventory)) {
            if(event.getClick().isLeftClick()) {
                if(event.getSlot() >= 0 && event.getSlot() <= 2) {
                    plugin.configHandler.addQuantityToSlot(event.getSlot(), 1);
                }
                else if(event.getSlot() >= 18 && event.getSlot() <= 20) {
                    plugin.configHandler.addQuantityToSlot(event.getSlot(), -1);
                }
                else if(event.getSlot() == 12) {
                    plugin.configHandler.openRewardsInventory();
                }
            }
            event.setCancelled(true);
        }
    }
}
