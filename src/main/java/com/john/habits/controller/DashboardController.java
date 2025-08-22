package com.john.habits.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

    /* Weekly elements */
    @FXML private Label weeklyDateLabel;
    @FXML private VBox weeklyCheckListVBox;
    @FXML private Label weeklyProgressLabel;
    @FXML private ProgressBar weeklyProgressBar;

    /* Monthly elements */
    @FXML private Label monthlyDateLabel;
    @FXML private VBox monthlyCheckListVBox;
    @FXML private Label monthlyProgressLabel;
    @FXML private ProgressBar monthlyProgressBar;

    private AtomicInteger dailyTasksCompletedCount;
    private int dailyTasksCount;

    @FXML
    public void initialize() {
        loadAllHabitsCheckListsTabs();
    }

    private void loadAllHabitsCheckListsTabs() {

        /* Load daily section tab */
        loadDateLabel(Frequency.DAILY, dailyDateLabel);
        loadHabitsChecklist(Frequency.DAILY, dailyCheckListVBox, dailyProgressLabel, dailyProgressBar);

        /* Load weekly section tab */
        loadDateLabel(Frequency.WEEKLY, weeklyDateLabel);
        loadHabitsChecklist(Frequency.WEEKLY, weeklyCheckListVBox, weeklyProgressLabel, weeklyProgressBar);

        /* Load monthly section tab */
        loadDateLabel(Frequency.MONTHLY, monthlyDateLabel);
        loadHabitsChecklist(Frequency.MONTHLY, monthlyCheckListVBox, monthlyProgressLabel, monthlyProgressBar);


        /*
        /* Load daily section tab 
        loadDailyDateLabel();
        loadDailyHabitsChecklist();
        loadDailyProgressTracking();

        /* Load weekly section tab 
        loadWeeklyDateLabel();
        loadWeeklyHabitsChecklist();
        loadWeeklyProgressTracking();
        
        /* Load monthly section tab 
        loadMonthlyDateLabel();
        loadMonthlyHabitsChecklist();
        loadMonthlyProgressTracking();  
        */
    }

    /** 
     * 
     *  Methods to load all the elements of the tabs of habits 
     * 
     */
    
    /* Display a date, with an style depending on the frequency, and to a specific label */
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
                label.setText(capitalize(today.getMonth().toString()) + today.getYear());
                break;
        
            default:
                break;
        }
    }

    /* Automatically create a checklist to a specific VBox, filtering the habits depending on the frequency */
    private void loadHabitsChecklist(Frequency frequency, VBox vbox, Label progressLabel, ProgressBar progressBar) {
        vbox.getChildren().clear();

        for (Habit habit : HabitManager.getFilteredHabits(frequency)) {
            CheckBox cb = new CheckBox(habit.getName());

            cb.selectedProperty().addListener((obs, oldVal, newVal) -> {updateProgressTracking(newVal, progressLabel, progressBar);});

            VBox.setMargin(cb, new Insets(5, 0, 5, 0));

            vbox.getChildren().add(cb);
        }
    }

    /* Resets progress tracking labels and variables */
    private void loadDailyProgressTracking() {
        dailyTasksCompletedCount = new AtomicInteger(0);
        dailyTasksCount = HabitManager.listHabits().size();

        dailyProgressLabel.setText("0 / " + dailyTasksCount + " tasks completed");
        dailyProgressBar.setProgress(0);
    }

    /* Update daily progress tracking when a task checkbox is switched */
    private void updateProgressTracking(boolean newVal, Label progressLabel, ProgressBar progressBar) {
        if (newVal) {
            dailyTasksCompletedCount.incrementAndGet();
        } else {
            dailyTasksCompletedCount.decrementAndGet();
        }

        dailyProgressLabel.setText(dailyTasksCompletedCount + " / " + dailyTasksCount + " tasks completed");
        dailyProgressBar.setProgress((double) dailyTasksCompletedCount.get() / dailyTasksCount);
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
