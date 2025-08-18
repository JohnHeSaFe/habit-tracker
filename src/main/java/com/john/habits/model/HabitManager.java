package com.john.habits.model;

import java.util.Collection;
import java.util.HashMap;

public class HabitManager {
    private static final HashMap<Integer, Habit> habits = new HashMap<>();
    private static final HabitDAO dao = new HabitDAO();

    static {
        dao.getAllHabits().forEach(h -> habits.put(h.getId(), h));
    }

    public static void addHabit(Habit habit) {
        int id = dao.insertHabit(habit);
        habit.setId(id);
        habits.put(habit.getId(), habit);
    }

    public static void removeHabit(int id) {
        dao.deleteHabit(id);
        habits.remove(id);
    }

    public static Habit getHabit(int id) {
        return habits.get(id);
    }

    public static Collection<Habit> listHabits() {
        return habits.values();
    }
}
