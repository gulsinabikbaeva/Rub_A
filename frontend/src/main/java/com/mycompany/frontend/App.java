package com.mycompany.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.StageStyle;
import java.util.Locale;

public class App extends Application {

    public static Stage stage = null;
    private static Scene scene;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.ITALIAN);
        //Parent fxmlLoader = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXML.fxml"));
        //Scene scene = new Scene(fxmlLoader, 800, 900);
        Scene scene = new Scene(fxmlLoader.load(), 820, 900);
        //Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Rubrica Telefonica");
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}