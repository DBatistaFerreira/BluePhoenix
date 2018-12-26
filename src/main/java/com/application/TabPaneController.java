package com.application;

import com.objects.Player;
import com.services.PlayerService;
import javafx.application.Platform;
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

    public void createKillBoardTab(String name) {
        Tab tab = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/KillBoardTab.fxml"), bundle);
            PlayerService playerService = new PlayerService();
            Player player = playerService.getPlayerByName(name);
            Parent tabContent = fxmlLoader.load();
            tab = new Tab(bundle.getString("killboard") + " - " + player.getCharacterName().getName(), tabContent);
            this.tab.getTabs().add(tab);
            KillBoardController killBoard = (KillBoardController) fxmlLoader.getController();
            System.out.println("Beginning the Processing");
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    killBoard.buildTableView(player);
                }
            };

            // Run the task in a background thread
            Thread backgroundThread = new Thread(task);

            // Terminate the running thread if the application exists
            backgroundThread.setDaemon(true);
            System.out.println("Starting the Processing");
            // Start the thread
            backgroundThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
