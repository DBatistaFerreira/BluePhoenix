package com.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class ApplicationMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/Scene.fxml"), ResourceBundle.getBundle("bundles.lang_en", Locale.ENGLISH));
        Scene scene = new Scene(root);
        stage.setTitle("PlanetSide 2 - Blue Phoenix");

        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}