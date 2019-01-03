package com.application.tabs;

import com.application.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class OutfitGeneralStatsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void buildTableView() {
    }

    public void setRootController(SceneController sceneController) {

    }
}
