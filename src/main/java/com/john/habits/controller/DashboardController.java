package com.john.habits.controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import com.john.habits.model.Frequency;
import com.john.habits.model.Habit;
import com.john.habits.model.HabitManager;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class DashboardController {
    @FXML private Button addHabitButton;

    /*
     * Tab pane FXML elements
     */

    /* Daily elements */
    @FXML private Label dailyDateLabel;
    @FXML private VBox dailyCheckListVBox;
    @FXML private Label dailyProgressLabel;
    @FXML private ProgressBar dailyProgressBar;
    private AtomicInteger dailyTasksCompletedCount;
    private int dailyTasksCount;

    /* Weekly elements */
    @FXML private Label weeklyDateLabel;
    @FXML private VBox weeklyCheckListVBox;
    @FXML private Label weeklyProgressLabel;
    @FXML private ProgressBar weeklyProgressBar;
    private AtomicInteger weeklyTasksCompletedCount;
    private int weeklyTasksCount;

    /* Monthly elements */
    @FXML private Label monthlyDateLabel;
    @FXML private VBox monthlyCheckListVBox;
    @FXML private Label monthlyProgressLabel;
    @FXML private ProgressBar monthlyProgressBar;
    private AtomicInteger monthlyTasksCompletedCount;
    private int monthlyTasksCount;
    

    @FXML
    public void initialize() {
        loadAllHabitsTabs();
    }

    private void loadAllHabitsTabs() {

        /* Load daily section tab */
        loadDateLabel(Frequency.DAILY, dailyDateLabel);
        loadProgressTracking(Frequency.DAILY, dailyTasksCompletedCount, dailyTasksCount, dailyProgressLabel, dailyProgressBar);
        loadHabitsChecklist(Frequency.DAILY, dailyCheckListVBox, dailyTasksCompletedCount, dailyTasksCount, dailyProgressLabel, dailyProgressBar);

        /* Load weekly section tab */
        loadDateLabel(Frequency.WEEKLY, weeklyDateLabel);
        loadProgressTracking(Frequency.WEEKLY, weeklyTasksCompletedCount, weeklyTasksCount, weeklyProgressLabel, weeklyProgressBar);
        loadHabitsChecklist(Frequency.WEEKLY, weeklyCheckListVBox, weeklyTasksCompletedCount, weeklyTasksCount, weeklyProgressLabel, weeklyProgressBar);

        /* Load monthly section tab */
        loadDateLabel(Frequency.MONTHLY, monthlyDateLabel);
        loadProgressTracking(Frequency.MONTHLY, monthlyTasksCompletedCount, monthlyTasksCount, monthlyProgressLabel, monthlyProgressBar);
        loadHabitsChecklist(Frequency.MONTHLY, monthlyCheckListVBox, monthlyTasksCompletedCount, monthlyTasksCount, monthlyProgressLabel, monthlyProgressBar);
    }

    /** 
     * 
     *  Methods to load all the elements of the tabs of habits 
     * 
     */
    
    /* Display a date with an style depending on the frequency to a specific label */
    private void loadDateLabel(Frequency frequency, Label label) {
        LocalDate today = LocalDate.now();

        switch (frequency) {
            case DAILY:
                label.setText(capitalize(today.getDayOfWeek().toString()) + ", " + today.getDayOfMonth() + " " + capitalize(today.getMonth().toString()));
                break;

            case WEEKLY:
                /* To get Monday and Sunday, add or substract the LocalDate depending on current's day of the week ej. Monday = 1, Tuesday = 2...*/
                int dayOfWeekIndex = today.getDayOfWeek().getValue();
                LocalDate monday = today.minusDays(dayOfWeekIndex - 1);
                LocalDate sunday = today.plusDays(7 - dayOfWeekIndex);

                label.setText("Monday, " + monday.getDayOfMonth() + " " + capitalize(monday.getMonth().toString()) + " - Sunday, " + sunday.getDayOfMonth() + " " + capitalize(sunday.getMonth().toString()));
                break;

            case MONTHLY:
                label.setText(capitalize(today.getMonth().toString()) + " " + today.getYear());
                break;
        
            default:
                break;
        }
    }

    /* Automatically create a checklist to a specific VBox, filtering the habits depending on the frequency */
    private void loadHabitsChecklist(Frequency frequency, VBox vbox, AtomicInteger tasksCompletedCount, int tasksCount, Label progressLabel, ProgressBar progressBar) {
        vbox.getChildren().clear();

        for (Habit habit : HabitManager.getFilteredHabits(frequency)) {
            CheckBox cb = new CheckBox(habit.getName());
            cb.selectedProperty().addListener((obs, oldVal, newVal) -> {updateProgressTracking(newVal, tasksCompletedCount, tasksCount, progressLabel, progressBar);});

            VBox.setMargin(cb, new Insets(5, 0, 5, 0));
            vbox.getChildren().add(cb);
        }
    }

    /* Loads progress tracking labels and variables */
    private void loadProgressTracking(Frequency frequency, AtomicInteger tasksCompletedCount, int tasksCount, Label progressLabel, ProgressBar progressBar) {
        tasksCompletedCount = new AtomicInteger(0);
        tasksCount = HabitManager.getFilteredHabits(frequency).size();

        progressLabel.setText("0 / " + tasksCount + " tasks completed");
        progressBar.setProgress(0);
    }

    /* Update daily progress tracking when a task checkbox is switched */
    private void updateProgressTracking(boolean newVal, AtomicInteger tasksCompletedCount, int tasksCount, Label progressLabel, ProgressBar progressBar) {
        if (newVal) {
            tasksCompletedCount.incrementAndGet();
        } else {
            tasksCompletedCount.decrementAndGet();
        }

        progressLabel.setText(tasksCompletedCount + " / " + tasksCount + " tasks completed");
        progressBar.setProgress((double) tasksCompletedCount.get() / tasksCount);
    }

    /**
     * General methods
     */

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
