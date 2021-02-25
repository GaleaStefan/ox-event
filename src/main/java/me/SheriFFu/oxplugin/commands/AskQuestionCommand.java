package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import me.SheriFFu.oxplugin.questions.Question;
import org.bukkit.entity.Player;

public class AskQuestionCommand extends SubCommand {
    public AskQuestionCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String[] arguments) {
        if(plugin.oxGame.randomQuestions) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.randomquestions"));
            return;
        }

        if(!player.equals(plugin.oxGame.getHost())) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.onlyhost"));
            return;
        }

        if(arguments == null || arguments.length < 3) {
            player.sendMessage((String) plugin.getConfig().get("messages.usage.askquestion"));
            return;
        }

        if(!arguments[1].toLowerCase().equals("x") && !arguments[1].toLowerCase().equals("o")) {
            player.sendMessage((String) plugin.getConfig().get("messages.usage.askquestion"));
            return;
        }

        if(plugin.oxGame.currentQuestion != null) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.roundinprogress"));
            return;
        }

        String text = "";

        for(int i = 2; i < arguments.length; i++) {
            text += " " + arguments[i];
        }

        plugin.oxGame.askQuestion(new Question(text, arguments[1].toLowerCase()));
    }
}
