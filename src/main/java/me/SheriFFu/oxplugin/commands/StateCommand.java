package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class StateCommand extends SubCommand {
    public StateCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player sender, String arguments[]) {
        String action = arguments[1].toLowerCase();

        if(action.equals("locked")) {
            plugin.oxGame.setLocked(true);
        }
        else if(action.equals("unlocked")) {
            plugin.oxGame.setLocked(false);
        }
        else {
            sender.sendMessage((String) plugin.getConfig().get("messages.usage.state"));
            return;
        }
    }
}
