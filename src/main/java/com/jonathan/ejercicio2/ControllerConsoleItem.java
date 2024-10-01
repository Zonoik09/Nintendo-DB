package com.jonathan.ejercicio2;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.Objects;

public class ControllerConsoleItem {
    @FXML
    private ImageView img ;
    @FXML
    private Text name,data,procesador,vend;
    @FXML
    private Circle color;
    private JSONObject itemData;

    public void setData(JSONObject data) {
        this.itemData = data;

        // Extraer datos del JSONObject
        String consoleName = itemData.optString("nom", "Unknown Console");
        String year = itemData.optString("data", "No year available");
        String processor = itemData.optString("procesador", "Unknown Processor");
        String vendor = itemData.optString("venudes", "Unknown Vendor");
        String imagePath = itemData.optString("imatge", "default_console.png");
        String circleColor = itemData.optString("color", "#FFFFFF"); // Color por defecto blanco

        // Asignar los datos a los elementos gráficos
        setTitle(consoleName);
        setYear(year);
        setProcesador("Procesador: "+processor);
        setVend("Venudes:"+vendor);
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

    public void setVend(String vend) {
        this.vend.setText(vend);
    }

    public void setProcesador(String pro) {
        this.procesador.setText(pro);
    }

    public void setYear(String data) {
        this.data.setText(data);
    }

}
