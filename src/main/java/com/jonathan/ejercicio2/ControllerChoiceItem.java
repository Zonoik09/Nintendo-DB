package com.jonathan.ejercicio2;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerChoiceItem {

    @FXML
    private Label title;

    private String categoryName;

    public void setTitle(String title) {
        this.categoryName = title;
        this.title.setText(title);
    }

    public void choice(MouseEvent mouseEvent) {
        ControllerSmall.getInstance().loadCategory(categoryName);
    }
}

