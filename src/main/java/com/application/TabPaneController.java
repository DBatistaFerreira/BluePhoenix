package com.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TabPaneController implements Initializable {
    @FXML
    SceneController sceneController;
    @FXML
    private TabPane tab;
    ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
    }

    public void setRootController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void createKillBoardTab() {
        Tab t = null;
        try {
            t = new Tab(bundle.getString("killboard"),FXMLLoader.load(getClass().getResource("/views/KillBoardTab.fxml"),bundle));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tab.getTabs().add(t);

    }
}
