package model;

public class SyllableTask {
    private String firstLetter;
    private String secondLetter;
    private String command;

    public SyllableTask(String firstLetter, String secondLetter, String command) {
        this.firstLetter = firstLetter;
        this.secondLetter = secondLetter;
        this.command = command;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getSecondLetter() {
        return secondLetter;
    }

    public String getCommand() {
        return command;
    }
}
