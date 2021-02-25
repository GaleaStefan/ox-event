package me.SheriFFu.oxplugin.questions;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class QuestionsFileReader {
    public static List<Question> readQuestions(FileConfiguration config) {
        List<Question> questions = new ArrayList<Question>();

        for(String id : config.getConfigurationSection("questions").getKeys(false)) {
            String text = (String) config.get("questions." + id + ".text");
            String a;

            if(config.get("questions." + id + ".answer") == "x") {
                a = "x";
            }
            else {
                a = "o";
            }

            questions.add(new Question(text, a));
        }

        return questions;
    }
}
