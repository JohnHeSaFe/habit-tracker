package com.john.habits.controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

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
    @FXML private Label todayDateLabel;
    @FXML private Label dailyProgressLabel;
    @FXML private VBox dailyCheckListVBox;
    @FXML private ProgressBar dailyProgressBar;

    private AtomicInteger dailyTasksCompletedCount;
    private int dailyTasksCount;

    @FXML
    public void initialize() {
        /* Load daily section */
        loadTodayDateLabel();
        loadTodayProgressTracking();
        loadTodayHabitsChecklist();
    }

    /** 
     * Daily section methods
     */
    
    /* Display today's date in the label with LocalDate */
    private void loadTodayDateLabel() {
        LocalDate today = LocalDate.now();

        todayDateLabel.setText(capitalize(today.getDayOfWeek().toString()) + ", " + today.getDayOfMonth() + " " + capitalize(today.getMonth().toString()));
    }

    /* Automatically create a checkbox for each habit for today TODO */
    private void loadTodayHabitsChecklist() {
        dailyCheckListVBox.getChildren().clear();

        for (Habit habit : HabitManager.listHabits()) {
            CheckBox cb = new CheckBox(habit.getName());

            cb.selectedProperty().addListener((obs, oldVal, newVal) -> {updateTodayProgressTracking(newVal);});

            VBox.setMargin(cb, new Insets(5, 0, 5, 0));

            dailyCheckListVBox.getChildren().add(cb);
        }
    }

    private void loadTodayProgressTracking() {
        dailyTasksCompletedCount = new AtomicInteger(0);
        dailyTasksCount = HabitManager.listHabits().size();

        dailyProgressLabel.setText("0 / " + dailyTasksCount + " tasks completed");
        dailyProgressBar.setProgress(0);
    }

    /* Update daily progress tracking when a task checkbox is switched */
    private void updateTodayProgressTracking(boolean newVal) {
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
