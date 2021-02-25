package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class CloseCommand extends SubCommand {
    public CloseCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String arguments[]) {
        if(plugin.oxGame == null) {
            sender.sendMessage((String) plugin.getConfig().get("messages.errors.nogame"));
            return;
        }

        if(!plugin.oxGame.getHost().equals(sender)) {
            if(!sender.hasPermission("ox.command.close.others")) {
                sender.sendMessage((String) plugin.getConfig().get("messages.errors.otherhost"));
                return;
            }
        }

        plugin.closeOXEvent();
    }
}
