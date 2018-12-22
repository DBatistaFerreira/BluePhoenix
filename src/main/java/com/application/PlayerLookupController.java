package com.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerLookupController implements Initializable {
    @FXML
    SceneController sceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setRootController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void search(){
        sceneController.createKillBoard();
    }
}
