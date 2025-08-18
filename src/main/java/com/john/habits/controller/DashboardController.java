package com.john.habits.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML private Button addHabitButton;
    @FXML private Label labelExample;

    @FXML
    public void initialize() { 
        addHabitButton.setOnAction((event) -> {
            labelExample.setText("You pressed the button");
        });
    }
}
