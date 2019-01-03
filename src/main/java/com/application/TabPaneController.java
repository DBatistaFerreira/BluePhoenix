package com.application;

import com.application.tabs.KillBoardController;
import com.application.tabs.OutfitGeneralStatsController;
import com.objects.Player;
import com.services.PlayerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/KillBoardTab.fxml"), bundle);
            PlayerService playerService = new PlayerService();
            Player player = playerService.getPlayerByName(name);
            Parent tabContent = fxmlLoader.load();
            Tab tab = new Tab(bundle.getString("killboard") + " - " + player.getCharacterName().getName(), tabContent);
            this.tab.getTabs().add(0,tab);
            KillBoardController killBoard = (KillBoardController) fxmlLoader.getController();

            Runnable task = () -> killBoard.buildTableView(player);
            // Run the task in a background thread
            Thread backgroundThread = new Thread(task);
            // Terminate the running thread if the application exists
            backgroundThread.setDaemon(true);
            // Start the thread
            backgroundThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOutfitGeneralStats(String tag) {
        //TODO This method needs to be completed once we have a Outfit Object
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/OutfitGeneralStatsTab.fxml"), bundle);
            Parent tabContent = fxmlLoader.load();
            Tab tab = new Tab(bundle.getString("outfitGeneralStats") + " - " , tabContent);
            this.tab.getTabs().add(0,tab);
            OutfitGeneralStatsController outfit = (OutfitGeneralStatsController) fxmlLoader.getController();

            Runnable task = () -> outfit.buildTableView();
            // Run the task in a background thread
            Thread backgroundThread = new Thread(task);
            // Terminate the running thread if the application exists
            backgroundThread.setDaemon(true);
            // Start the thread
            backgroundThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
