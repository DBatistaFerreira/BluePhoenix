package com.application;

import com.application.lookups.PlayerLookupController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private PlayerLookupController playerLookupController;
    @FXML
    private TabPaneController tabPaneController;
    @FXML
    private VBox playerLookup;
    @FXML
    private VBox outfitLookup;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarController.setRootController(this);
        playerLookupController.setRootController(this);
        tabPaneController.setRootController(this);
    }

    public void createKillBoard(String name) {
        tabPaneController.createKillBoardTab(name);
    }

    public void setKillboardVisible(boolean visible) {
        playerLookup.setVisible(visible);
    }

    public void reset() {
        setKillboardVisible(false);
        setOutfitGeneralStatsVisible(false);
    }

    public void setOutfitGeneralStatsVisible(boolean visible) {
        outfitLookup.setVisible(visible);
    }
}
