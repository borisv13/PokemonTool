package com.boris.pokemontool;

public class Player {
    private int id;
    private String name;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

    public Player () {

    }

    public Player (String name) {
        this.name = name;
    }

    public Player (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}