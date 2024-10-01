package com.jonathan.ejercicio2;

import com.almasb.fxgl.core.collection.Array;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ControllerSmall {

    @FXML
    public ScrollPane scrollsmall;
    public Label nintendoDB;
    public Button back;
    @FXML
    private VBox AnchPane;

    private JSONArray consolesInfo;
    private JSONArray jocsInfo;
    private JSONArray personatgesInfo;
    private static ControllerSmall instance;
    private final List<String> categories = new ArrayList<>(Arrays.asList("Consoles", "Jocs", "Personatges"));



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

    public static ControllerSmall getInstance() {
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
            initialLoad();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initialLoad() throws IOException {
        for (int i = 0; i < categories.size(); i++) {
            String nom = categories.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/choiceLayout.fxml"));
            Parent itemTemplate = loader.load();
            ControllerChoiceItem itemController = loader.getController();
            itemController.setTitle(nom);
            AnchPane.getChildren().add(itemTemplate);
        }
    }

    public void loadCategory(String categoryName) {
        AnchPane.getChildren().clear();

        switch (categoryName) {
            case "Consoles":
                VBox(consolesInfo);
                break;
            case "Jocs":
                VBox(jocsInfo);
                break;
            case "Personatges":
                VBox(personatgesInfo);
                break;
            default:
                System.err.println("Categoría desconocida: " + categoryName);
                break;
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            AnchPane.getChildren().clear();
            initialLoad();
        } catch (IOException e) {
            e.printStackTrace();
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

                AnchPane.getChildren().add(itemTemplate);
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
            String layoutPath = determineLayout(data);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(layoutPath));
            Parent detailNode = loader.load();
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

            AnchPane.getChildren().clear();
            AnchPane.getChildren().add(detailNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}