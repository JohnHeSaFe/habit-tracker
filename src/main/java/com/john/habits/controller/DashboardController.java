package com.john.habits.controller;

import java.util.concurrent.atomic.AtomicInteger;

import com.john.habits.model.Habit;
import com.john.habits.model.HabitManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardController {
    @FXML private Button addHabitButton;
    @FXML private Label todayLabel;
    @FXML private Label dailyCompletedLabel;
    @FXML private VBox dailyCheckListVBox;

    @FXML
    public void initialize() { 
        AtomicInteger tasksCompletedCount = new AtomicInteger(0);
        int tasksCount = HabitManager.listHabits().size();
        
        dailyCompletedLabel.setText(tasksCompletedCount + " / " + tasksCount + " tasks completed");

        dailyCheckListVBox.getChildren().clear();
        for (Habit habit : HabitManager.listHabits()) {
            CheckBox cb = new CheckBox(habit.getName());

            cb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    tasksCompletedCount.incrementAndGet();
                } else {
                    tasksCompletedCount.decrementAndGet();
                }

                dailyCompletedLabel.setText(tasksCompletedCount + " / " + tasksCount + " tasks completed");
            });

            dailyCheckListVBox.getChildren().add(cb);
        }
    }
}
