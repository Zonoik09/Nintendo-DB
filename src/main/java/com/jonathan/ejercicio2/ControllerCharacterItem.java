package com.jonathan.ejercicio2;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.Objects;

public class ControllerCharacterItem {
    @FXML
    private ImageView img ;
    @FXML
    private Text name,game;
    @FXML
    private Circle color;
    private JSONObject itemData;

    public void setData(JSONObject data) {
        this.itemData = data;

        // Extraer datos del JSONObject
        String characterName = itemData.optString("nom", "Unknown Character");
        String gameTitle = itemData.optString("nom_del_videojoc", "No game available");
        String imagePath = itemData.optString("imatge", "default_character.png");
        String circleColor = itemData.optString("color", "#FFFFFF"); // Color por defecto blanco

        // Asignar los datos a los elementos gráficos
        setTitle(characterName);
        setGame(gameTitle);
        setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/" + imagePath))));
        setCircleColor(circleColor);
    }


    public void setTitle(String nom) {
        this.name.setText(nom);
    }

    public void setCircleColor(String color) {
        this.color.setStyle("-fx-fill: " + color);
    }

    public void setImage(Image imagen) {
        this.img.setImage(imagen);
    }

    public void setGame(String game) {
        this.game.setText(game);
    }

}
