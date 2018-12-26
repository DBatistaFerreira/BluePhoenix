package com.application;

import com.objects.Casualty;
import com.objects.CasualtyDisplay;
import com.objects.Player;
import com.services.CasualtyService;
import com.services.PlayerService;
import com.utilities.Continents;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import javax.annotation.processing.Processor;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.ZoneId.systemDefault;

public class KillBoardController implements Initializable {
    @FXML
    private TableView tableView;
    private ResourceBundle bundle;
    private Map<String, Player> players;
    private int headShots;
    private int numberOfKills;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        players = new HashMap<String, Player>();

    }

    public void buildTableView(Player player) {
        CasualtyService service = new CasualtyService();
        List<Casualty> casualtyList = service.getCasualities(player.getCharacterID());
        LinkedList<CasualtyDisplay> casualtyDisplayList = new LinkedList<>();
        PlayerService playerService = new PlayerService();
        players.put(player.getCharacterID(), player);

        for (Casualty casualty : casualtyList) {
            try {
                if (players.get(casualty.getCharacterID()) == null) {
                    players.put(casualty.getCharacterID(), playerService.getPlayerById((casualty.getCharacterID())));
                }
                if (players.get(casualty.getAttackerCharacterID()) == null) {
                    players.put(casualty.getAttackerCharacterID(), playerService.getPlayerById((casualty.getAttackerCharacterID())));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            CasualtyDisplay casualtyDisplay = new CasualtyDisplay();

            casualtyDisplay.setAttackerCharacterName(players.get(casualty.getAttackerCharacterID()) == null ? "Enemy" : players.get(casualty.getAttackerCharacterID()).getCharacterName().getName());
            casualtyDisplay.setTargetCharacterName(players.get(casualty.getCharacterID()) == null ? "Enemy" : players.get(casualty.getCharacterID()).getCharacterName().getName());
            casualtyDisplay.setAttackerWeaponName(casualty.getAttackerWeaponID());
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
                tableView.getItems().removeAll();
                tableView.getItems().addAll(casualtyDisplayList);
            });
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableView.refresh();
            }
        });
        System.out.println("Number of HeadShots: " + headShots);
        System.out.printf("HeadShot Rate is: %1.2f%s\n", ((((double) headShots) / ((double) numberOfKills)) * 100), "%");
        System.out.println("Number of Kills: " + numberOfKills);
        System.out.println("Number of Deaths: " + (casualtyList.size() - numberOfKills));
        System.out.println("Number of Kills and Deaths: " + casualtyList.size());

    }
}
