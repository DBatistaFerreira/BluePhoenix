package com.application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private PlayerLookupController playerLookupController;
    @FXML
    private TabPaneController tabPaneController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setRootController(this);
        playerLookupController.setRootController(this);
        tabPaneController.setRootController(this);
    }

    public void createKillBoard(String name) {
        tabPaneController.createKillBoardTab(name);
    }
}
