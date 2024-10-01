package com.jonathan.ejercicio2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.Objects;

public class ControllerGameItem {
    @FXML
    private ImageView img;
    @FXML
    private Text name;
    @FXML
    private Text description;
    private JSONObject itemData;
    FXMLLoader loader;

    public void initialize() {
        // Comprobar si los elementos fueron inicializados
        if (name == null) {
            System.err.println("Error: name Text is null in initialize.");
        }
        if (description == null) {
            System.err.println("Error: description Text is null in initialize.");
        }
    }

    public void setData(JSONObject data) {
        String gameName = data.optString("nom", "Unknown");
        String gameDescription = data.optString("descripcio", "No description available.");
        String imagePath = data.optString("imatge", "default.png");  // Imagen predeterminada si no hay
        name.setText(gameName);
        description.setText(gameDescription);
        img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/" + imagePath))));
    }

    public void setTitle(String nom) {
        if (nom != null) {
            name = new Text("nom");
            name.setText(nom);
        } else {
            System.err.println("Error: name Text is null.");
        }
    }

    public void setDescription(String desc) {
        if (desc != null) {
            description = new Text(desc);
            description.setText(desc);
        } else {
            System.err.println("Error: description Text is null.");
        }
    }

    public void setImage(Image imagen) {
        if (imagen != null) {
            img = new ImageView(imagen);
            img.setImage(imagen);
        } else {
            System.err.println("Error: img ImageView is null.");
        }
    }
}
