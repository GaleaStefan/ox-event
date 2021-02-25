package me.SheriFFu.oxplugin.questions;



public class Question {
    public String text;
    public String correct;

    public Question() {
        this.text = "";
        this.correct = null;
    }

    public  Question(String txt, String a) {
        this.text = txt;
        this.correct = a;
    }
}
