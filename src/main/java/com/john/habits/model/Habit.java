package com.john.habits.model;

public class Habit {
    private int id;
    private String name;
    private Frequency frequency;

    public Habit(int id, String name, Frequency frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }

    public Habit(String name, Frequency frequency) {
        this.id = -1;
        this.name = name;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
}