package com.application.tabs;

import com.application.TabPaneController;
import com.enums.Faction;
import com.objects.*;
import com.services.CasualtyService;
import com.services.ItemService;
import com.services.PlayerService;
import com.services.VehicleService;
import com.enums.Continent;
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
    private int headShots;
    private int numberOfKills;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        //Sorts the values based on there integer values instead of ASCII
        ((TableColumn) tableView.getColumns().get(0)).setComparator(new integerCompare());

        //Changes the cell factories to color text based on what the cell contains
        ((TableColumn) tableView.getColumns().get(4)).setCellFactory(new getColorValue());

        ((TableColumn) tableView.getColumns().get(5)).setCellFactory(new getColorValue());
    }

    public void buildTableView(Player player) {
        CasualtyService service = new CasualtyService();
        List<Casualty> casualtyList = service.getCasualities(player.getCharacterID());

        PlayerService playerService = new PlayerService();
        ItemService itemService = new ItemService();
        VehicleService vehicleService = new VehicleService();

        progressBar.setVisible(true);
        Platform.runLater(() -> battleRank.setText(player.getPrestigeLevel() + "~" + player.getBattleRank().getBattleRankValue()));

        ArrayList<String> playerIDs = new ArrayList<>();
        ArrayList<String> weaponIds = new ArrayList<>();
        ArrayList<String> vehicleIds = new ArrayList<>();

        Platform.runLater(() -> progressBar.setProgress(0.1));

        playerIDs.add(player.getCharacterID());
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
            if (!casualty.getAttackerVehicleID().equals("0") && !vehicleIds.contains(casualty.getAttackerVehicleID())) {
                vehicleIds.add(casualty.getAttackerVehicleID());
            }
        }
        Platform.runLater(() -> progressBar.setProgress(0.2));

        players = playerService.getPlayersByIds(playerIDs);
        Platform.runLater(() -> progressBar.setProgress(0.4));

        Map<String, Item> items = itemService.getItemsByIDs(weaponIds);
        Platform.runLater(() -> progressBar.setProgress(0.6));

        Map<String, Vehicle> vehicles = vehicleService.getVehiclesByIds(vehicleIds);
        Platform.runLater(() -> progressBar.setProgress(0.9));

        LinkedList<CasualtyDisplay> casualtyDisplayList = new LinkedList<>();
        for (Casualty casualty : casualtyList) {
            CasualtyDisplay casualtyDisplay = new CasualtyDisplay();
            //check to make sure the person didn't kill themselves i.e a suicide which is already handled as a death
            if (casualty.getTableType().equals("kills") && casualty.getAttackerCharacterID().equals(casualty.getCharacterID())) {
                continue;
            }
            //Column 1 - ROW NUMBER
            casualtyDisplay.setRowNumber(String.valueOf(casualtyDisplayList.size() + 1));
            //Column 2 - TYPE OF EVENT (KILL:DEATH)
            casualtyDisplay.setTableType(bundle.getString(casualty.getTableType()));
            //Column 3 - TIME OF THE EVENT IN LOCAL TIME
            Date date = new Date(Long.parseLong(casualty.getTimestamp()) * 1000);
            casualtyDisplay.setDateLocalTime(format.format(date));
            //Column 4 - CONTINENT THAT THE EVENT OCCURRED ON
            casualtyDisplay.setContinent(bundle.getString(Continent.getContinentFromValue(Integer.parseInt(casualty.getZoneID()))));
            //Column 5 - ATTACKER
            casualtyDisplay.setAttackerCharacterName(casualty.getAttackerCharacterID());
            //Column 6 - TARGET
            casualtyDisplay.setTargetCharacterName(casualty.getCharacterID());
            //Column 7 - METHOD
            if (casualty.getAttackerWeaponID().equals("0") && casualty.getAttackerVehicleID().equals("0") && casualty.getAttackerCharacterID().equals(casualty.getCharacterID())) {
                casualtyDisplay.setAttackerWeaponName(bundle.getString("suicide"));
            } else if (casualty.getAttackerWeaponID().equals("0") && casualty.getAttackerVehicleID().equals("0")) {
                casualtyDisplay.setAttackerWeaponName(bundle.getString("unknown"));
            } else if (casualty.getAttackerWeaponID().equals("0")) {
                casualtyDisplay.setAttackerWeaponName(vehicles.get(casualty.getAttackerVehicleID()).getVehicleName().getEnglish());
            } else {
                casualtyDisplay.setAttackerWeaponName(items.get(casualty.getAttackerWeaponID()).getItemName().getEnglish());
            }
            //TODO if it is a headshot add a headshot picture next to target name
            if (casualty.getTableType().equals("kills")) {
                numberOfKills++;
                if (casualty.isHeadshot().equals("true")) {
                    headShots++;
                }
            }

            casualtyDisplayList.add(casualtyDisplay);
            Platform.runLater(() -> {
                //Add item to table view in a GUI thread
                tableView.getItems().add(casualtyDisplay);
                progressBar.setProgress(0.9 + (0.24 * casualtyDisplayList.size()/((double)casualtyList.size())));
                tableView.refresh();
            });
        }

        Platform.runLater(()->{
            kills.setText(String.valueOf(numberOfKills));
            deaths.setText(String.valueOf(casualtyDisplayList.size() - numberOfKills));
            total.setText(String.valueOf(casualtyDisplayList.size()));
            headshotRate.setText(((double) Math.round((((double) headShots) / (double) numberOfKills) * 10000)) / 100 + "%");
            trueKD.setText(String.valueOf((double)Math.round(((double) numberOfKills) / ((double) (casualtyDisplayList.size() - numberOfKills)) * 100) / 100));
            progressBar.setProgress(1.0);
            tableView.refresh();
            progressBar.setVisible(false);
            //TabPaneController.autoResizeColumns(tableView);
        });
    }

    /////////////////////
    /// INNER CLASSES ///
    /////////////////////

    private class getColorValue<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        public getColorValue() {
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> p) {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem((T)item, empty);
                    if (!isEmpty()) {
                        Player player = players.get(item);
                        if (!(player == null)) {
                            this.setTextFill(Faction.getFactionFromValue(Integer.parseInt(player.getFactionID())).getColor());
                            String prefix = "";
                            if(!(player.getOutfitMember() == null)){
                                if(!player.getOutfitMember().getAlias().isEmpty()){
                                    prefix = "[" + player.getOutfitMember().getAlias() + "] ";
                                }
                            }
                            setText(prefix + player.getCharacterName().getName());
                        } else {
                            this.setTextFill(Color.GRAY);
                            setText(bundle.getString("enemy"));
                        }
                    }
                }
            };
        }
    }
    private class integerCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (Integer.parseInt(o1) > Integer.parseInt(o2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
