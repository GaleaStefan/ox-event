package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends SubCommand {
    public HelpCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player p, String arguments[]) {
        List<String> text = (List<String>) plugin.getConfig().getList("messages.help");

        for(String line : text) {
            p.sendMessage(line);
        }
    }
}
