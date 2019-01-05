package com.enums;

import javafx.scene.paint.Color;

public enum Faction {
    NANITE_SYSTEMS(0, Color.GRAY),
    VANU_SOVEREIGNTY(1, Color.PURPLE),
    NEW_CONGLOMERATE(2, Color.DODGERBLUE),
    TERRAN_REPUBLIC(3, Color.RED);

    private final int value;
    private final Color color;

    Faction(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public Color getColor(){
        return color;
    }

    public static Faction getFactionFromValue(int value) {
        for (Faction faction : Faction.values()) {
            if (value == faction.value) return faction;
        }
        return null;
    }
}

