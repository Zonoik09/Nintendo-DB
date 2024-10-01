package com.jonathan.ejercicio2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.layout.*;
import org.json.JSONArray;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class Controller {
    @FXML
    private ComboBox<String> choice;
    @FXML
    private VBox yPane;
    @FXML
    private AnchorPane AnchPane;


    private JSONArray consolesInfo;
    private JSONArray jocsInfo;
    private JSONArray personatgesInfo;
    private static Controller instance;

    // Cargar los datos desde el JSON
    private void loadJsonData() {
        try {
            consolesInfo = loadJsonFromFile("/assets/data/consoles.json");
            jocsInfo = loadJsonFromFile("/assets/data/jocs.json");
            personatgesInfo = loadJsonFromFile("/assets/data/personatges.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Controller getInstance() {
        return instance;
    }

    private JSONArray loadJsonFromFile(String path) throws Exception {
        URL jsonFileURL = getClass().getResource(path);
        if (jsonFileURL == null) {
            System.err.println("File not found: " + path);
            throw new FileNotFoundException("JSON file not found: " + path);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFileURL.openStream(), StandardCharsets.UTF_8));
        StringBuilder jsonText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonText.append(line);
        }
        reader.close();
        return new JSONArray(jsonText.toString());
    }

    // Menu de elección del big
    @FXML
    public void initialize() {
        try {
            instance = this;
            loadJsonData();
            choiceMenu();
            if (AnchPane == null) {
                System.err.println("AnchPane is null");
            } else {
                System.out.println("AnchPane initialized correctly");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void choiceMenu() {
        if (choice != null) {
            choice.getItems().addAll("Consoles","Jocs","Personatges");
            choice.setOnAction(this::onComboBoxSelection);
        }
    }

    private void onComboBoxSelection(ActionEvent event) {
        String selected = choice.getValue();
        yPane.getChildren().clear();
        AnchPane.getChildren().clear();
        switch (selected) {
            case "Consoles":
                VBox(consolesInfo);
                loadLayout("/consoleLayout.fxml");
                break;
            case "Jocs":
                VBox(jocsInfo);
                loadLayout("/gameLayout.fxml");
                break;
            case "Personatges":
                VBox(personatgesInfo);
                loadLayout("/characterLayout.fxml");
                break;
            default:
                break;
        }
    }

    private void VBox(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String nom = item.getString("nom");
                String imatge = item.getString("imatge");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/itemLayout.fxml"));
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();

                itemController.setTitle(nom);
                itemController.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/" + imatge))));
                itemController.setData(item);

                yPane.getChildren().add(itemTemplate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLayout(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent layout = loader.load();
            AnchPane.getChildren().clear();
            AnchPane.getChildren().add(layout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String determineLayout(JSONObject data) {
        if (data.has("procesador")) {
            return "/consoleLayout.fxml";
        } else if (data.has("nom_del_videojoc")) {
            return "/characterLayout.fxml";
        } else {
            return "/gameLayout.fxml";
        }
    }
    

    public void loadInfo(JSONObject data) {
        try {
            // Determinar qué layout cargar
            String layoutPath = determineLayout(data);

            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(layoutPath));
            Parent detailNode = loader.load();

            // Cargar datos específicos del ítem
            switch (layoutPath) {
                case "/characterLayout.fxml" -> {
                    ControllerCharacterItem controller = loader.getController();
                    controller.setData(data);
                }
                case "/consoleLayout.fxml" -> {
                    ControllerConsoleItem controller = loader.getController();
                    controller.setData(data);
                }
                case "/gameLayout.fxml" -> {
                    ControllerGameItem controller = loader.getController();
                    controller.setData(data);
                }
            }

            // Reemplazar el contenido actual en el AnchorPane
            AnchPane.getChildren().clear();
            AnchPane.getChildren().add(detailNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // Parte chica




}