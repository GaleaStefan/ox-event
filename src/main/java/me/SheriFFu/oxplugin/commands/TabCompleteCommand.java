package me.SheriFFu.oxplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TabCompleteCommand implements TabCompleter {

    private List<String> subcommands = Arrays.asList("help", "reload",
            "gameconfig", "arenaconfig",
            "create", "close", "start",
            "join", "quit", "kick",
            "askquestion");

    private HashMap<String, List<String>> subParameters;

    public TabCompleteCommand() {
        subParameters = new HashMap<>();
        subParameters.put("arenaconfig", Arrays.asList("x", "o", "neutral"));
        subParameters.put("state", Arrays.asList("locked", "unlocked"));
    }

    private List<String> getStringsContaining(String substring, List<String> from) {
        List<String> subStrings = new ArrayList<String>();

        for(String arg : from) {
            if(arg.contains(substring)) {
                subStrings.add(arg);
            }
        }

        return subStrings;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {
        if(!command.getName().equals("ox")) {
            return null;
        }

        if(arguments.length > 0) {
            if(arguments.length == 1) {
                return getStringsContaining(arguments[0].toLowerCase(), subcommands);
            }
            else
            {
                if(subParameters.containsKey(arguments[0].toLowerCase())) {
                    return getStringsContaining(arguments[1], subParameters.get(arguments[0].toLowerCase()));
                }
                else {
                    return null;
                }
            }
        }
        else {
            return null;
        }
    }
}
