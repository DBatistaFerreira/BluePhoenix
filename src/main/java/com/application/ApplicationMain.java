package com.application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


public class ApplicationMain extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/views/Scene.fxml"), ResourceBundle.getBundle("bundles.lang_en", Locale.ENGLISH));
        Scene scene = new Scene(root,1365,900, Color.TRANSPARENT);
        stage.setTitle("PlanetSide 2 - Blue Phoenix");
        stage.setOnCloseRequest(confirmCloseEventHandler);

        Button closeButton = new Button("Close Application");
        closeButton.setOnAction(event -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(primaryStage);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}