package com.john.habits.model;

import java.util.Collection;
import java.util.HashMap;

public class HabitManager {
    private static final HashMap<Integer, Habit> habits = new HashMap<>();
    private static final HabitDAO dao = new HabitDAO();

    static {
        dao.getAllHabits().forEach(h -> habits.put(h.getId(), h));

        if (habits.isEmpty()) {
            Habit habit1 = new Habit("Drink water", Frequency.DAILY);
            Habit habit2 = new Habit("Read a book", Frequency.DAILY);
            Habit habit3 = new Habit("Go to gym", Frequency.DAILY);

            int id1 = dao.insertHabit(habit1);
            int id2 = dao.insertHabit(habit2);
            int id3 = dao.insertHabit(habit3);

            habits.put(id1, new Habit(id1, habit1.getName(), habit1.getFrequency()));
            habits.put(id2, new Habit(id2, habit2.getName(), habit2.getFrequency()));
            habits.put(id3, new Habit(id3, habit3.getName(), habit3.getFrequency()));
        }
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
