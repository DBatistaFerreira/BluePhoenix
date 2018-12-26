package com.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerLookupController implements Initializable {
    @FXML
    SceneController sceneController;
    @FXML
    private TextField playerName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setRootController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void search(){
        sceneController.createKillBoard(playerName.getText());
    }
}
