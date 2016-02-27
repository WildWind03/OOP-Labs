package ru.nsu.fit.chirikhin;

public class Statistics implements Comparable {
    private String description;
    private int numOfLines;
    private int numOfFiles;

    public Statistics() {
        numOfFiles = 0;
        numOfLines = 0;
    }

    public int getNumOfFiles() {
        return numOfFiles;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public void setNumOfLines(int numOfLines) {
        this.numOfLines = numOfLines;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumOfFiles(int numOfFiles) {
        this.numOfFiles = numOfFiles;
    }

    public void addNumOfLines(int numOfLines) {
        this.numOfLines += numOfLines;
    }

    public void addNumOfFiles(int numOfFiles) {
        this.numOfFiles += numOfFiles;
    }

    @Override
    public int compareTo(Object o) throws ClassCastException, IllegalArgumentException {

        Statistics stat = (Statistics) o;

        if (stat.getNumOfLines() == this.getNumOfLines()) {
            return 0;
        }
        else {
            if (stat.getNumOfLines() > this.getNumOfLines()) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }
}
