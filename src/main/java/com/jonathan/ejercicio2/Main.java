package com.jonathan.ejercicio2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(@NotNull Stage stage) throws IOException {

        Parent largeroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout_big.fxml")));
        Scene scene = new Scene(largeroot);

        stage.setTitle("Nintendo DB");
        stage.setWidth(900);
        stage.setMinWidth(350);
        stage.setMinHeight(650);
        stage.setMaxHeight(650);
        stage.setHeight(650);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 400) {
                if (stage.getScene() != scene) {
                    stage.setScene(scene);
                }
            } else {
                Parent smallroot = null;
                try {
                    smallroot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout_small.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene1 = new Scene(smallroot);
                if (!Objects.equals(stage.getScene(), scene1)) {
                    stage.setScene(scene1);
                }
            }
        });

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}