package com.utilities;

public enum World {
    CONNERY(1),
    MILLER(10),
    COBALT(13),
    EMERALD(17),
    JAEGER(19),
    BRIGGS(25),
    SOLTECH(40);

    private final int value;

    World(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getWorldFromValue(int value) {
        for (World e : World.values()) {
            if (value == e.value) return e.name();
        }
        return null;
    }
}
