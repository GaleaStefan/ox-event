package me.SheriFFu.oxplugin.listeners;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ArenaConfigListener implements Listener {
    private final Main plugin;

    public ArenaConfigListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(player.equals(plugin.arenaEditor.editorPlayer)) {
            if(event.getHand().equals(EquipmentSlot.HAND)) {
                if(event.getItem().getType().equals(Material.GOLDEN_AXE)) {
                    if(event.getClickedBlock() != null && !event.getClickedBlock().getType().equals(Material.AIR)) {
                        Action action = event.getAction();

                        if(action.equals(Action.LEFT_CLICK_BLOCK)) {
                            player.sendMessage((String) plugin.getConfig().get("messages.notifications.corner1"));
                            plugin.arenaEditor.region.setCorner1(event.getClickedBlock().getLocation());

                            event.setCancelled(true);
                        }
                        else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                            player.sendMessage((String) plugin.getConfig().get("messages.notifications.corner2"));
                            plugin.arenaEditor.region.setCorner2(event.getClickedBlock().getLocation());
                            plugin.arenaEditor.saveRegionToConfig();
                            plugin.disableArenaEditing();

                            player.sendMessage((String) plugin.getConfig().get("messages.notifications.areacreated"));
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
