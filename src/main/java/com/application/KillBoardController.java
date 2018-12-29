package com.application;

import com.objects.*;
import com.services.CasualtyService;
import com.services.ItemService;
import com.services.PlayerService;
import com.services.VehicleService;
import com.utilities.Continents;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
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
    private Map<String, Vehicle> vehicles;
    private int headShots;
    private int numberOfKills;
    private String userName;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        ((TableColumn) tableView.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<CasualtyDisplay, String>("attackerCharacterName"));
        ((TableColumn) tableView.getColumns().get(4)).setCellFactory(getValue());

        ((TableColumn) tableView.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<CasualtyDisplay, String>("targetCharacterName"));
        ((TableColumn) tableView.getColumns().get(5)).setCellFactory(getValue());
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
                                    this.setTextFill(Color.PURPLE);
                                } else if (player.getFactionID().equals("2")) {
                                    this.setTextFill(Color.DODGERBLUE);
                                } else {
                                    this.setTextFill(Color.RED);
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
        VehicleService vehicleService = new VehicleService();
        progressBar.setVisible(true);
        Platform.runLater(() -> battleRank.setText(player.getPrestigeLevel() + "~" + player.getBattleRank().getBattleRankValue()));
        ArrayList<String> playerIDs = new ArrayList<>();
        playerIDs.add(player.getCharacterID());
        ArrayList<String> weaponIds = new ArrayList<>();
        ArrayList<String> vehicleIds = new ArrayList<>();
        Platform.runLater(() -> {
            progressBar.setProgress(0.1);
        });
        for (Casualty casualty : casualtyList) {
            if (!playerIDs.contains(casualty.getCharacterID())) {
                playerIDs.add(casualty.getCharacterID());
            }
            if (!playerIDs.contains(casualty.getAttackerCharacterID())) {
                playerIDs.add(casualty.getAttackerCharacterID());
            }
            if (!casualty.getAttackerWeaponID().equals("0") && !weaponIds.contains(casualty.getAttackerWeaponID())) {
                weaponIds.add(casualty.getAttackerWeaponID());
            }
            if(!casualty.getAttackerVehicleID().equals("0") && !vehicleIds.contains(casualty.getAttackerVehicleID())){
                vehicleIds.add(casualty.getAttackerVehicleID());
            }
        }
        Platform.runLater(() -> {
            progressBar.setProgress(0.2);
        });
        players = playerService.getPlayersByIds(playerIDs);
        Platform.runLater(() -> {
            progressBar.setProgress(0.4);
        });
        items = itemService.getItemsByIDs(weaponIds);
        Platform.runLater(() -> {
            progressBar.setProgress(0.6);
        });
        vehicles = vehicleService.getVehiclesByIds(vehicleIds);
        Platform.runLater(() -> {
            progressBar.setProgress(0.75);
        });
        for (Casualty casualty : casualtyList) {
            CasualtyDisplay casualtyDisplay = new CasualtyDisplay();
            casualtyDisplay.setRowNumber(String.valueOf(casualtyDisplayList.size() + 1));
            if (casualty.getTableType().equals("kills") && casualty.getAttackerCharacterID().equals(casualty.getCharacterID())) {
                continue;
            }
            casualtyDisplay.setAttackerCharacterName(casualty.getAttackerCharacterID());
            casualtyDisplay.setTargetCharacterName(casualty.getCharacterID());
            if (casualty.getAttackerWeaponID().equals("0") && casualty.getAttackerVehicleID().equals("0") && casualty.getAttackerCharacterID().equals(casualty.getCharacterID())) {
                casualtyDisplay.setAttackerWeaponName("Suicide");
            } else if (casualty.getAttackerWeaponID().equals("0") && casualty.getAttackerVehicleID().equals("0")) {
                casualtyDisplay.setAttackerWeaponName("Unknown");
            } else if (casualty.getAttackerWeaponID().equals("0")) {
                casualtyDisplay.setAttackerWeaponName(vehicles.get(casualty.getAttackerVehicleID()).getVehicleName().getEnglish());
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

            casualtyDisplay.setDateLocalTime(format.format(date));

            casualtyDisplayList.add(casualtyDisplay);
        }
        progressBar.setVisible(false);
        Platform.runLater(() -> {
            tableView.getItems().addAll(casualtyDisplayList);
            kills.setText(String.valueOf(numberOfKills));
            deaths.setText(String.valueOf(casualtyDisplayList.size() - numberOfKills));
            total.setText(String.valueOf(casualtyDisplayList.size()));
            headshotRate.setText(((double) Math.round((((double) headShots) / (double) numberOfKills) * 10000)) / 100 + "%");
            trueKD.setText(String.valueOf(Math.round((double) numberOfKills / ((double) (casualtyDisplayList.size() - numberOfKills)) * 100) / 100));
            tableView.refresh();
            progressBar.setProgress(1.0);
        });
    }
}
