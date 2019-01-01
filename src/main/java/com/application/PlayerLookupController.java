package com.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerLookupController implements Initializable {
    @FXML
    SceneController sceneController;
    @FXML
    private TextField playerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerName.setOnKeyPressed(key -> {
            if(key.getCode().equals(KeyCode.ENTER)){
                search();
            }
        });
    }

    public void setRootController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void search() {
        if (!playerName.getText().isEmpty()) {
                sceneController.createKillBoard(playerName.getText());
        }
        playerName.setText(null);
    }
}
