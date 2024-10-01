package com.jonathan.ejercicio2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public class ControllerListItem {

    @FXML
    public Label title;
    @FXML
    public ImageView imagen;

    private JSONObject itemData;

    public void setData(JSONObject data) {
        this.itemData = data;
    }

    public void setTitle(String nom) {
        this.title.setText(nom);
    }

    public void setImage(Image img) {
        imagen.setImage(img);
    }

    public void information(MouseEvent event) {
        Controller controllerInstance = Controller.getInstance();
        ControllerSmall ControllerInstanceSmall = ControllerSmall.getInstance();
        if (controllerInstance != null) {
            controllerInstance.loadInfo(itemData);
        }
        if (ControllerInstanceSmall != null) {
            ControllerInstanceSmall.loadInfo(itemData);
        }
    }
}
