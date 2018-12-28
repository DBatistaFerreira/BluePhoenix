package com.application;

import com.objects.Casualty;
import com.objects.CasualtyDisplay;
import com.objects.Item;
import com.objects.Player;
import com.services.CasualtyService;
import com.services.ItemService;
import com.services.PlayerService;
import com.utilities.Continents;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class KillBoardController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label battleRank;
    @FXML
    private Label kills;
    @FXML
    private Label deaths;
    @FXML
    private Label headshotRate;
    @FXML
    private Label total;
    @FXML
    private Label trueKD;

    private ResourceBundle bundle;

    private Map<String, Player> players;
    private Map<String, Item> items;
    private int headShots;
    private int numberOfKills;
    private String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        ((TableColumn) tableView.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<CasualtyDisplay, String>("attackerCharacterName"));
        ((TableColumn) tableView.getColumns().get(3)).setCellFactory(getValue());

        ((TableColumn) tableView.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<CasualtyDisplay, String>("targetCharacterName"));
        ((TableColumn) tableView.getColumns().get(4)).setCellFactory(getValue());

        players = new HashMap<String, Player>();
        items = new HashMap<String, Item>();

    }

    private Callback<TableColumn, TableCell> getValue() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<CasualtyDisplay, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Player player = players.get(item);
                            if (!(player == null)) {
                                if (player.getFactionID().equals("1")) {
                                    this.setTextFill(Color.RED);
                                } else if (player.getFactionID().equals("2")) {
                                    this.setTextFill(Color.DODGERBLUE);
                                } else {
                                    this.setTextFill(Color.PURPLE);
                                }
                                setText(player.getCharacterName().getName());
                            } else {
                                setText(bundle.getString("enemy"));
                            }
                        }
                    }
                };
            }
        };
    }

    public void buildTableView(Player player) {
        userName = player.getCharacterName().getName();
        CasualtyService service = new CasualtyService();
        List<Casualty> casualtyList = service.getCasualities(player.getCharacterID());
        LinkedList<CasualtyDisplay> casualtyDisplayList = new LinkedList<>();
        PlayerService playerService = new PlayerService();
        ItemService itemService = new ItemService();
        progressBar.setVisible(true);
        Platform.runLater(() -> battleRank.setText(player.getPrestigeLevel() + "~" + player.getBattleRank().getBattleRankValue()));
        ArrayList<String> playerIDs = new ArrayList<>();
        playerIDs.add(player.getCharacterID());
        for (Casualty casualty : casualtyList) {
            if (!playerIDs.contains(casualty.getCharacterID())) {
                playerIDs.add(casualty.getCharacterID());
            }
            if (!playerIDs.contains(casualty.getAttackerCharacterID())) {
                playerIDs.add(casualty.getAttackerCharacterID());
            }
        }
        players = playerService.getPlayersByIds(playerIDs);
        for (Casualty casualty : casualtyList) {
            try {
                if (casualty.getAttackerVehicleID().equals("0") && items.get(casualty.getAttackerWeaponID()) == null) {
                    //items.put(casualty.getAttackerWeaponID(), itemService.getItemById(casualty.getAttackerWeaponID()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            CasualtyDisplay casualtyDisplay = new CasualtyDisplay();

            casualtyDisplay.setAttackerCharacterName(casualty.getAttackerCharacterID());
            casualtyDisplay.setTargetCharacterName(casualty.getCharacterID());
            if (items.get(casualty.getAttackerWeaponID()) == null) {
                casualtyDisplay.setAttackerWeaponName(casualty.getAttackerVehicleID());
            } else {
                casualtyDisplay.setAttackerWeaponName(items.get(casualty.getAttackerWeaponID()).getItemName().getEnglish());
            }

            casualtyDisplay.setContinent(bundle.getString(Continents.getContinentFromValue(Integer.parseInt(casualty.getZoneID()))));
            casualtyDisplay.setIsHeadshot(bundle.getString(casualty.isHeadshot()));
            if (casualty.getTableType().equals("kills")) {
                numberOfKills++;
                if (casualty.isHeadshot().equals("true")) {
                    headShots++;
                }
            }
            casualtyDisplay.setTableType(bundle.getString(casualty.getTableType()));
            Date date = new Date(Long.parseLong(casualty.getTimestamp()) * 1000);
            casualtyDisplay.setDateLocalTime(date.toString());

            casualtyDisplayList.add(casualtyDisplay);
            Platform.runLater(() -> {
                progressBar.setProgress(((double) casualtyDisplayList.size()) / casualtyList.size());
                tableView.getItems().add(casualtyDisplay);
                kills.setText(String.valueOf(numberOfKills));
                deaths.setText(String.valueOf(casualtyDisplayList.size() - numberOfKills));
                total.setText(String.valueOf(casualtyDisplayList.size()));
                headshotRate.setText(((double) Math.round((((double) headShots) / (double) numberOfKills) * 10000)) / 100 + "%");
                trueKD.setText(String.valueOf(Math.round((double) numberOfKills / ((double) (casualtyDisplayList.size() - numberOfKills)) * 100) / 100));
            });
        }
        progressBar.setVisible(false);
    }
}
