package com.application;

import com.application.tabs.KillBoardController;
import com.application.tabs.OutfitGeneralStatsController;
import com.objects.Outfit;
import com.objects.Player;
import com.services.OutfitService;
import com.services.PlayerService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
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

    private void setNewTab(Tab tab) {
        this.tab.getTabs().add(0, tab);
        this.tab.getSelectionModel().select(0);
    }

    public void createKillBoardTab(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/KillBoardTab.fxml"), bundle);
            PlayerService playerService = new PlayerService();
            Player player = playerService.getPlayerByName(name);
            if (Objects.isNull(player)) {
                System.out.println("NULL");
                return;
            }
            Parent tabContent = fxmlLoader.load();
            Tab tab = new Tab(bundle.getString("killboard") + " - " + player.getCharacterName().getName(), tabContent);
            setNewTab(tab);
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

    public boolean createOutfitGeneralStats(String tag) {
        //TODO This method needs to be completed once we have a Outfit Object
        boolean foundOutfit = false;
        try {
            OutfitService outfitService = new OutfitService();
            Outfit outfit = outfitService.getOutfit(tag);
            if (outfit != null) {
                foundOutfit = true;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/OutfitGeneralStatsTab.fxml"), bundle);
                Parent tabContent = fxmlLoader.load();
                Tab tab = new Tab(bundle.getString("outfitGeneralStats") + " - " + outfit.getAlias(), tabContent);
                Platform.runLater(() -> setNewTab(tab));
                OutfitGeneralStatsController outfitTabController = (OutfitGeneralStatsController) fxmlLoader.getController();
                Runnable task = () -> outfitTabController.buildTableView(outfit);
                // Run the task in a background thread
                Thread backgroundThread = new Thread(task);
                // Terminate the running thread if the application exists
                backgroundThread.setDaemon(true);
                // Start the thread
                backgroundThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundOutfit;
    }

    public static void autoResizeColumns(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        double width = 0;
        for (TableColumn column : table.getColumns()) {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            if(max == 0){
                width += column.getWidth();
                continue;
            }
            for (int i = 0; i < table.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-width with some extra space
            width += max + 10.0;
            column.setMinWidth(max + 10.0);
            column.setMaxWidth(max + 10.0);
        }
        table.setPrefWidth(width + 20.0);
    }
}
