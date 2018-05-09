package sample.model;

import java.time.LocalDate;

public class ToDoItem {
    private String shortDisc;
    private String longDisc;
    private LocalDate date;

    public ToDoItem(String shortDisc, String longDisc, LocalDate date) {
        this.shortDisc = shortDisc;
        this.longDisc = longDisc;
        this.date = date;
    }

    public String getShortDisc() {
        return shortDisc;
    }

    public String getLongDisc() {
        return longDisc;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return shortDisc;
    }
}
