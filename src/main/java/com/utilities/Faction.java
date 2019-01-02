package com.utilities;

public enum Faction {
    NANITE_SYSTEMS(0),
    VANU_SOVEREIGNTY(1),
    NEW_CONGLOMERATE(2),
    TERRAN_REPUBLIC(3);

    private final int value;

    Faction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getFactionFromValue(int value) {
        for (Faction e : Faction.values()) {
            if (value == e.value) return e.name();
        }
        return null;
    }
}

