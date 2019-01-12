package com.application.tabs;

import com.application.TabPaneController;
import com.enums.Faction;
import com.enums.World;
import com.objects.Outfit;
import com.objects.OutfitDisplay;
import com.objects.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OutfitGeneralStatsController implements Initializable {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static long YEAR_IN_SECONDS = 31556952;
    private final static long MONTH_IN_SECONDS = 2629746;
    private final static long WEEK_IN_SECONDS = 604800;
    private final static long DAY_IN_SECONDS = 86400;
    private final static long HOUR_IN_SECONDS = 3600;
    private final static long MIN_IN_SECONDS = 60;

    @FXML
    private TableView tableView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label outfitName,
            tag,
            server,
            faction,
            leaderName,
            memberCount,
            membersCurrentlyOnline,
            totalScore,
            totalBr,
            averageBr,
            averageSpm,
            averageKpm,
            averageKd,
            totalKills,
            totalDeaths,
            totalTimePlayed,
            averageTimePlayed,
            activeTotalScore,
            activeTotalBr,
            activeAverageBr,
            activeAverageSpm,
            activeAverageKpm,
            activeAverageKd,
            activeTotalKills,
            activeTotalDeaths,
            activeTotalTimePlayed,
            activeAverageTimePlayed,
            lastDay,
            lastWeek,
            lastMonth,
            inactive;

    private ResourceBundle bundle;
    private Map<String, Integer> rank;
    private Map<String, Long> timePlayed;

    private double averageSpmValue = 0,
            averageKpmValue = 0,
            averageKdValue = 0,
            activeAverageSpmValue = 0,
            activeAverageKpmValue = 0,
            activeAverageKdValue = 0,
            totalLastDay = 0,
            totalLastWeek = 0,
            totalInactive = 0;

    private int numberOfOnlinePlayers = 0,
            totalBrValue = 0,
            totalKillsValue = 0,
            totalDeathsValue = 0,
            activeTotalBrValue = 0,
            activeTotalKillsValue = 0,
            activeTotalDeathsValue = 0,
            amountOfActiveMembers = 0;


    private long totalTimePlayedValue = 0,
            averageTimePlayedValue = 0,
            activeTotalScoreValue = 0,
            totalScoreValue = 0,
            activeTotalTimePlayedValue = 0,
            activeAverageTimePlayedValue = 0;

    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static DecimalFormat decimalFormat = new DecimalFormat("0.##");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        rank = new HashMap<>();
        timePlayed = new HashMap<>();
        numberFormat.setGroupingUsed(true);
        decimalFormat.setGroupingUsed(true);

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
        Platform.runLater(() -> {
            outfitName.setText(outfit.getName());
            tag.setText(outfit.getAlias());
            server.setText(bundle.getString(World.getWorldFromValue(Integer.parseInt(outfit.getLeader().getWorldID()))));
            faction.setTextFill(Faction.getFactionFromValue(Integer.parseInt(outfit.getLeader().getFactionId())).getColor());
            faction.setText(bundle.getString(Faction.getFactionFromValue(Integer.parseInt(outfit.getLeader().getFactionId())).name()));
            leaderName.setText(outfit.getLeader().getName().getName());
            memberCount.setText(String.valueOf(outfit.getMembers().size()));
        });
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
            int indexOfPlayTime = 0,
                    indexOfKills = 0,
                    indexOfDeaths = 0,
                    indexOfScore = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                switch (list.get(i).getStatName()) {
                    case "time":
                        indexOfPlayTime = i;
                        break;
                    case "kills":
                        indexOfKills = i;
                        break;
                    case "deaths":
                        indexOfDeaths = i;
                        break;
                    case "score":
                        indexOfScore = i;
                        break;
                }
            }
            long playTime = Long.parseLong(list.get(indexOfPlayTime).getValue());
            //Stats Needed for outfit Info
            totalScoreValue += Long.parseLong(list.get(indexOfScore).getValue());
            totalBrValue += Integer.parseInt(member.getPlayer().getBattleRank().getBattleRankValue());
            averageSpmValue += Double.parseDouble(list.get(indexOfScore).getValue()) / (((double) playTime) / MIN_IN_SECONDS);
            averageKdValue += Double.parseDouble(list.get(indexOfKills).getValue()) / (Double.parseDouble(list.get(indexOfDeaths).getValue()) == 0 ? 1 : Double.parseDouble(list.get(indexOfDeaths).getValue()));
            averageKpmValue += (Double.parseDouble(list.get(indexOfKills).getValue())) / (((double) playTime) / MIN_IN_SECONDS);
            totalKillsValue += Integer.parseInt(list.get(indexOfKills).getValue());
            totalDeathsValue += Integer.parseInt(list.get(indexOfDeaths).getValue());
            totalTimePlayedValue += playTime;

            OutfitDisplay outfitDisplay = new OutfitDisplay();
            //Column 1 - Online Status
            outfitDisplay.setOnlineStatus(member.getPlayer().getOnlineStatus());
            if (!member.getPlayer().getOnlineStatus().equals("0")) {
                numberOfOnlinePlayers++;
            }
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
            outfitDisplay.setLastLogin(toStringTimeElapsed((Instant.now().toEpochMilli() / 1000 - Long.parseLong(member.getPlayer().getPlayerTimes().getLastSaveDate()))));
            //Column 7 - PLAY TIME
            String playtimeFormated = toStringTimeElapsed(playTime);
            outfitDisplay.setPlayTime(playtimeFormated);
            timePlayed.put(playtimeFormated, playTime);
            //Column 8 - K/D
            outfitDisplay.setKillDeathRatio(decimalFormat.format((Double.parseDouble(list.get(indexOfKills).getValue())) / (Double.parseDouble(list.get(indexOfDeaths).getValue()) == 0 ? 1 : Double.parseDouble(list.get(indexOfDeaths).getValue()))));
            //Column 9 - SCORE PER MINUTE
            outfitDisplay.setScorePerMinute(decimalFormat.format(((Double.parseDouble(list.get(indexOfScore).getValue())) / (((double) playTime) / MIN_IN_SECONDS))));
            //Column 10 - KILLS PER MINUTE
            outfitDisplay.setKillsPerMinute(decimalFormat.format(((Double.parseDouble(list.get(indexOfKills).getValue())) / (((double) playTime) / MIN_IN_SECONDS))));
            outfitDisplayList.add(outfitDisplay);
            //Member Activity
            long diffInMillies = new Date().getTime() - lastLogin.getTime();
            long diffInSeconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diffInSeconds < DAY_IN_SECONDS) {
                totalLastDay++;
            }
            if (diffInSeconds < WEEK_IN_SECONDS) {
                totalLastWeek++;
            }
            if (diffInSeconds < MONTH_IN_SECONDS) {
                activeTotalBrValue += Integer.parseInt(member.getPlayer().getBattleRank().getBattleRankValue());
                activeTotalScoreValue += Long.parseLong(list.get(indexOfScore).getValue());
                activeAverageKdValue += Double.parseDouble(list.get(indexOfKills).getValue()) / (Double.parseDouble(list.get(indexOfDeaths).getValue()) == 0 ? 1 : Double.parseDouble(list.get(indexOfDeaths).getValue()));
                activeAverageSpmValue += Double.parseDouble(list.get(indexOfScore).getValue()) / (((double) playTime) / MIN_IN_SECONDS);
                activeTotalKillsValue += Integer.parseInt(list.get(indexOfKills).getValue());
                activeTotalDeathsValue += Integer.parseInt(list.get(indexOfDeaths).getValue());
                activeAverageKpmValue += (Double.parseDouble(list.get(indexOfKills).getValue())) / (((double) playTime) / MIN_IN_SECONDS);
                activeTotalTimePlayedValue += playTime;
                amountOfActiveMembers++;
            }
        }
        Platform.runLater(() -> {
            totalScore.setText(numberFormat.format(totalScoreValue));
            totalBr.setText(numberFormat.format(totalBrValue));
            averageBr.setText(numberFormat.format(totalBrValue / outfitDisplayList.size()));
            averageSpm.setText(decimalFormat.format(averageSpmValue / outfitDisplayList.size()));
            averageKpm.setText(decimalFormat.format(averageKpmValue / outfitDisplayList.size()));
            averageKd.setText(decimalFormat.format(averageKdValue / outfitDisplayList.size()));
            totalKills.setText(numberFormat.format(totalKillsValue));
            totalDeaths.setText(numberFormat.format(totalDeathsValue));
            totalTimePlayed.setText(toStringTimeElapsed(totalTimePlayedValue));
            averageTimePlayedValue = totalTimePlayedValue / outfitDisplayList.size();
            averageTimePlayed.setText(toStringTimeElapsed(averageTimePlayedValue));

            activeTotalScore.setText(numberFormat.format(activeTotalScoreValue));
            activeTotalBr.setText(numberFormat.format(activeTotalBrValue));
            activeAverageBr.setText(numberFormat.format(activeTotalBrValue / amountOfActiveMembers));
            activeAverageSpm.setText(decimalFormat.format(activeAverageSpmValue / amountOfActiveMembers));
            activeAverageKpm.setText(decimalFormat.format(activeAverageKpmValue / amountOfActiveMembers));
            activeAverageKd.setText(decimalFormat.format(activeAverageKdValue / amountOfActiveMembers));
            activeTotalKills.setText(numberFormat.format(activeTotalKillsValue));
            activeTotalTimePlayed.setText(toStringTimeElapsed(activeTotalTimePlayedValue));
            activeTotalDeaths.setText(numberFormat.format(activeTotalDeathsValue));
            activeAverageTimePlayedValue = activeTotalTimePlayedValue / amountOfActiveMembers;
            activeAverageTimePlayed.setText(toStringTimeElapsed(activeAverageTimePlayedValue));

            lastDay.setText(formatMemberActivityLabel(totalLastDay, outfitDisplayList.size()));
            lastWeek.setText(formatMemberActivityLabel(totalLastWeek, outfitDisplayList.size()));
            lastMonth.setText(formatMemberActivityLabel(amountOfActiveMembers, outfitDisplayList.size()));
            inactive.setText(formatMemberActivityLabel(totalInactive, outfitDisplayList.size()));

            membersCurrentlyOnline.setText(numberFormat.format(numberOfOnlinePlayers));
            tableView.getItems().addAll(outfitDisplayList);
            progressBar.setVisible(false);
            progressBar.setManaged(false);
            TabPaneController.autoResizeColumns(tableView);
        });
    }

    private static String formatMemberActivityLabel(double totalActive, double total) {
        return decimalFormat.format((totalActive / total) * 100) + " % (" + (int) totalActive + ")";
    }

    private static String toStringTimeElapsed(long timeElapsedInSeconds) {
        StringBuilder stringBuilder = new StringBuilder();

        int totalAdded = 0,
                years = (int) (timeElapsedInSeconds / YEAR_IN_SECONDS),
                months = (int) ((timeElapsedInSeconds - years * YEAR_IN_SECONDS) / MONTH_IN_SECONDS),
                weeks = (int) ((timeElapsedInSeconds - years * YEAR_IN_SECONDS - months * MONTH_IN_SECONDS) / WEEK_IN_SECONDS),
                days = (int) ((timeElapsedInSeconds - years * YEAR_IN_SECONDS - months * MONTH_IN_SECONDS - weeks * WEEK_IN_SECONDS) / DAY_IN_SECONDS),
                hours = (int) ((timeElapsedInSeconds - years * YEAR_IN_SECONDS - months * MONTH_IN_SECONDS - weeks * WEEK_IN_SECONDS - days * DAY_IN_SECONDS) / HOUR_IN_SECONDS),
                minutes = (int) (((timeElapsedInSeconds - years * YEAR_IN_SECONDS - months * MONTH_IN_SECONDS - weeks * WEEK_IN_SECONDS - days * DAY_IN_SECONDS - hours * HOUR_IN_SECONDS) / MIN_IN_SECONDS)),
                seconds = (int) (timeElapsedInSeconds - years * YEAR_IN_SECONDS - months * MONTH_IN_SECONDS - weeks * WEEK_IN_SECONDS - days * DAY_IN_SECONDS - hours * HOUR_IN_SECONDS - minutes * MIN_IN_SECONDS);
        if (years > 0) {
            stringBuilder.append(years > 1 ? " " + years + " Years" : " 1 Year");
            totalAdded++;
        }
        if (months > 0) {
            stringBuilder.append(months > 1 ? " " + months + " Months" : " 1 Month");
            totalAdded++;
        }
        if (weeks > 0) {
            stringBuilder.append(weeks > 1 ? " " + weeks + " Weeks" : " 1 Week");
            totalAdded++;
        }
        if (days > 0 && totalAdded < 3) {
            stringBuilder.append(days > 1 ? " " + days + " Days" : " 1 Day");
            totalAdded++;
        }
        if (hours > 0 && totalAdded < 3) {
            stringBuilder.append(hours > 1 ? " " + hours + " Hours" : " 1 Hour");
            totalAdded++;
        }
        if (minutes > 0 && totalAdded < 3) {
            stringBuilder.append(minutes > 1 ? " " + minutes + " Minutes" : " 1 Minute");
            totalAdded++;
        }
        if (seconds > 0 && totalAdded < 3) {
            stringBuilder.append(seconds > 1 ? " " + seconds + " Seconds" : " 1 Second");
        }
        return stringBuilder.toString();
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
            return new TableCell<S, T>() {
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
        }
    }

    private class getColorValue<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        private final Color color;

        public getColorValue(Color color) {
            this.color = color;
        }

        @Override
        public TableCell<S, T> call(TableColumn<S, T> p) {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem((T) item, empty);
                    if (!isEmpty()) {
                        this.setTextFill(color);
                        setText((String) item);
                    }
                }
            };
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
            if (o1.contains("~")) {
                index1 = o1.indexOf("~");
                prestige1 = Integer.parseInt(o1.substring(0, index1));
            }
            if (o2.contains("~")) {
                index2 = o2.indexOf("~");
                prestige2 = Integer.parseInt(o2.substring(0, index2));
            }
            battleRank1 = Integer.parseInt(o1.substring(index1 == 0 ? index1 : index1 + 1));
            battleRank2 = Integer.parseInt(o2.substring(index2 == 0 ? index2 : index2 + 1));
            if (prestige1 > prestige2) {
                return -1;
            } else if (prestige1 < prestige2) {
                return 1;
            } else if (battleRank1 > battleRank2) {
                return -1;
            } else {
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
