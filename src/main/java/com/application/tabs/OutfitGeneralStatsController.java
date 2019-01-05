package com.application.tabs;

import com.application.TabPaneController;
import com.enums.Faction;
import com.objects.Outfit;
import com.objects.OutfitDisplay;
import com.objects.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static javax.jnlp.ServiceManager.lookup;

public class OutfitGeneralStatsController implements Initializable {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @FXML
    private TableView tableView;
    @FXML
    private ProgressBar progressBar;
    private ResourceBundle bundle;
    private Map<String, Integer> rank;
    private Map<String, Long> timePlayed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        rank = new HashMap<>();
        timePlayed = new HashMap<>();
        //Sorts the values based on there integer values instead of ASCII
        ((TableColumn) tableView.getColumns().get(0)).setComparator(new integerCompare());
        //Sorts the values based on there double values instead of ASCII
        ((TableColumn) tableView.getColumns().get(7)).setComparator(new doubleCompare());
        ((TableColumn) tableView.getColumns().get(8)).setComparator(new doubleCompare());
        ((TableColumn) tableView.getColumns().get(9)).setComparator(new doubleCompare());
        //Sorts the value based on the un formated time played
        ((TableColumn) tableView.getColumns().get(6)).setComparator(new formatedPlayTimeCompare());
        //Sorts the values based on rank ordinal instead of ASCII
        ((TableColumn) tableView.getColumns().get(2)).setComparator(new rankCompare());
        //Sorts the values based on the prestige level and battle rank level
        ((TableColumn) tableView.getColumns().get(3)).setComparator(new battleRankCompare());

        //Changes the cell factories to contain a graphic for online status
        ((TableColumn) tableView.getColumns().get(0)).setCellFactory(new onlineStatus());

    }

    public void buildTableView(Outfit outfit) {
        //Changes the cell factories to color text based on what the cell contains
        ((TableColumn) tableView.getColumns().get(1)).setCellFactory(new getColorValue(Faction.getFactionFromValue(Integer.parseInt(outfit.getLeader().getFactionId())).getColor()));
        ArrayList<OutfitDisplay> outfitDisplayList = new ArrayList<>();
        Platform.runLater(() -> progressBar.setVisible(true));
        for (Outfit.OutfitMember member : outfit.getMembers()) {
            if (member.getPlayer().getCharacterName() == null) {
                continue;
            }
            rank.put(member.getRank(), Integer.parseInt(member.getRankOrdinal()));
            List<Player.Stats.Stat> list = member.getPlayer().getStatList().getStatHistory();
            int indexOfPlayTime = 0;
            int indexOfKills = 0;
            int indexOfDeaths = 0;
            int indexOfScore = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).getStatName().equals("time")) {
                    indexOfPlayTime = i;
                } else if (list.get(i).getStatName().equals("kills")) {
                    indexOfKills = i;
                } else if (list.get(i).getStatName().equals("deaths")) {
                    indexOfDeaths = i;
                } else if (list.get(i).getStatName().equals("score")) {
                    indexOfScore = i;
                }
            }
            OutfitDisplay outfitDisplay = new OutfitDisplay();
            //Column 1 - Online Status
            outfitDisplay.setOnlineStatus(member.getPlayer().getOnlineStatus());
            //Column 2 - NAME
            outfitDisplay.setName(member.getPlayer().getCharacterName().getName());
            //Column 3 - RANK
            outfitDisplay.setRank(member.getRank());
            //Column 4 - BATTLE RANK
            outfitDisplay.setBattleRank(Integer.parseInt(member.getPlayer().getPrestigeLevel()) > 0 ? member.getPlayer().getPrestigeLevel() + "~" + member.getPlayer().getBattleRank().getBattleRankValue() : member.getPlayer().getBattleRank().getBattleRankValue());
            //Column 5 - DATE JOINED
            Date dateJoined = new Date(Long.parseLong(member.getMemberSince()) * 1000);
            outfitDisplay.setDateJoined(format.format(dateJoined));
            //Column 6 - LAST LOGIN
            Date lastLogin = new Date(Long.parseLong(member.getPlayer().getPlayerTimes().getLastSaveDate()) * 1000);
            outfitDisplay.setLastLogin(format.format(lastLogin));
            //Column 7 - PLAY TIME
            long playTime = Long.parseLong(list.get(indexOfPlayTime).getValue());
            int day = (int) TimeUnit.SECONDS.toDays(playTime);
            long hours = TimeUnit.SECONDS.toHours(playTime) - (day * 24);
            long minute = TimeUnit.SECONDS.toMinutes(playTime) - (TimeUnit.SECONDS.toHours(playTime) * 60);
            long second = TimeUnit.SECONDS.toSeconds(playTime) - (TimeUnit.SECONDS.toMinutes(playTime) * 60);
            String playtimeFormated;
            if (day > 0) {
                playtimeFormated = day + " Days " + hours + " Hours " + minute + " Minutes";
                outfitDisplay.setPlayTime(playtimeFormated);
            } else {
                playtimeFormated = hours + " Hours " + minute + " Minutes " + second + " Seconds";
                outfitDisplay.setPlayTime(playtimeFormated);
            }
            timePlayed.put(playtimeFormated, playTime);
            //Column 8 - K/D
            outfitDisplay.setKillDeathRatio(String.valueOf((double) Math.round((Double.parseDouble(list.get(indexOfKills).getValue())) / (Double.parseDouble(list.get(indexOfDeaths).getValue())) * 100) / 100));
            //Column 9 - SCORE PER MINUTE
            outfitDisplay.setScorePerMinute(String.valueOf((double) Math.round(((Double.parseDouble(list.get(indexOfScore).getValue())) / (Double.parseDouble(list.get(indexOfPlayTime).getValue()) / 60)) * 10) / 10));
            //Column 10 - KILLS PER MINUTE
            outfitDisplay.setKillsPerMinute(String.valueOf((double) Math.round(((Double.parseDouble(list.get(indexOfKills).getValue())) / (Double.parseDouble(list.get(indexOfPlayTime).getValue()) / 60)) * 100) / 100));
            outfitDisplayList.add(outfitDisplay);
        }
        Platform.runLater(() -> {
            tableView.getItems().addAll(outfitDisplayList);
            progressBar.setVisible(false);
            TabPaneController.autoResizeColumns(tableView);
        });
    }

    /////////////////////
    /// INNER CLASSES ///
    /////////////////////

    // TABLE COLUMN CELLS

    private class onlineStatus<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        public onlineStatus() {
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> p) {
            TableCell<S, T> cell = new TableCell<S, T>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem((T) item, empty);
                    if (!isEmpty()) {
                        Rectangle rectangle = new Rectangle(15, 15);
                        RadialGradient redRadialGradientGrad = new RadialGradient(0, 0, 7.5, 7.5, 10, false, CycleMethod.NO_CYCLE, new Stop(0f, Color.rgb(255, 0, 0, 1)), new Stop(1f, Color.rgb(0, 0, 0, 0)));
                        RadialGradient greenRadialGradientGrad = new RadialGradient(0, 0, 7.5, 7.5, 10, false, CycleMethod.NO_CYCLE, new Stop(0f, Color.rgb(0, 255, 0, 1)), new Stop(1f, Color.rgb(0, 0, 0, 0)));
                        rectangle.setFill(greenRadialGradientGrad);
                        if (item.equals("0")) {
                            rectangle.setFill(redRadialGradientGrad);
                        }
                        setGraphic(rectangle);
                    }
                }
            };
            return cell;
        }
    }

    private class getColorValue<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        private final Color color;

        public getColorValue(Color color) {
            this.color = color;
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> p) {
            TableCell<S, T> cell = new TableCell<S, T>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem((T) item, empty);
                    if (!isEmpty()) {
                        this.setTextFill(color);
                        setText((String) item);
                    }
                }
            };
            return cell;
        }
    }

    //COLUMN COMPARATORS

    private class formatedPlayTimeCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (timePlayed.get(o1) > timePlayed.get(o2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private class rankCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (rank.get(o1) > rank.get(o2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private class battleRankCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            int prestige1 = 0;
            int prestige2 = 0;
            int battleRank1 = 0;
            int battleRank2 = 0;
            int index1 = 0;
            int index2 = 0;
            if(o1.contains("~")){
                index1 = o1.indexOf("~");
                prestige1 = Integer.parseInt(o1.substring(0, index1));
            }
            if(o2.contains("~")){
                index2 = o2.indexOf("~");
                prestige2 = Integer.parseInt(o2.substring(0, index2));
            }
            battleRank1 = Integer.parseInt(o1.substring(index1 == 0 ? index1 : index1 + 1));
            battleRank2 = Integer.parseInt(o2.substring(index2 == 0 ? index2 : index2 + 1));
            if (prestige1 > prestige2) {
                return -1;
            } else if(prestige1 < prestige2){
                return 1;
            }else if(battleRank1 > battleRank2){
                return -1;
            }else{
                return 1;
            }
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

    private class doubleCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (Double.parseDouble(o1) > Double.parseDouble(o2)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
