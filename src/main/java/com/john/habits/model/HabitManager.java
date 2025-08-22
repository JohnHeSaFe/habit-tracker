package com.john.habits.model;

import java.util.Collection;
import java.util.HashMap;

public class HabitManager {
    private static final HashMap<Integer, Habit> habits = new HashMap<>();
    private static final HabitDAO dao = new HabitDAO();

    static {
        dao.getAllHabits().forEach(h -> habits.put(h.getId(), h));

        /* Test TODO */
        if (habits.isEmpty()) {
            // Daily habits
            Habit habit1 = new Habit("Drink water", Frequency.DAILY);
            Habit habit2 = new Habit("Read a book", Frequency.DAILY);
            Habit habit3 = new Habit("Go to gym", Frequency.DAILY);

            int id1 = dao.insertHabit(habit1);
            int id2 = dao.insertHabit(habit2);
            int id3 = dao.insertHabit(habit3);

            habits.put(id1, new Habit(id1, habit1.getName(), habit1.getFrequency()));
            habits.put(id2, new Habit(id2, habit2.getName(), habit2.getFrequency()));
            habits.put(id3, new Habit(id3, habit3.getName(), habit3.getFrequency()));

            // Weekly habits
            Habit habit4 = new Habit("Clean the house", Frequency.WEEKLY);
            Habit habit5 = new Habit("Meal prep for the week", Frequency.WEEKLY);

            int id4 = dao.insertHabit(habit4);
            int id5 = dao.insertHabit(habit5);

            habits.put(id4, new Habit(id4, habit4.getName(), habit4.getFrequency()));
            habits.put(id5, new Habit(id5, habit5.getName(), habit5.getFrequency()));

            // Monthly habits
            Habit habit6 = new Habit("Review monthly budget", Frequency.MONTHLY);
            Habit habit7 = new Habit("Deep clean fridge", Frequency.MONTHLY);
            Habit habit8 = new Habit("Pay bills", Frequency.MONTHLY);
            Habit habit9 = new Habit("Clean computer", Frequency.MONTHLY);

            int id6 = dao.insertHabit(habit6);
            int id7 = dao.insertHabit(habit7);
            int id8 = dao.insertHabit(habit8);
            int id9 = dao.insertHabit(habit9);

            habits.put(id6, new Habit(id6, habit6.getName(), habit6.getFrequency()));
            habits.put(id7, new Habit(id7, habit7.getName(), habit7.getFrequency()));
            habits.put(id8, new Habit(id8, habit8.getName(), habit8.getFrequency()));
            habits.put(id9, new Habit(id9, habit9.getName(), habit9.getFrequency()));
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

    /* Get a filtered list of habits depending on the frequency */
    public static Collection<Habit> getFilteredHabits(Frequency frequency) {
        HashMap<Integer, Habit> filteredHabits = new HashMap<>();

        habits.values().forEach(habit -> {
            if (habit.getFrequency() == frequency) {
                filteredHabits.put(habit.getId(), habit);
            }
        });
        /* practicing lambdas xd */

        return filteredHabits.values();
    }
}
