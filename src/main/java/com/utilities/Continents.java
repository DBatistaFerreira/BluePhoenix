package com.utilities;

public enum Continents {
    ALL(0),
    INDAR(2),
    HOSSIN(4),
    AMERISH(6),
    ESAMIR(8);

    private final int value;

    Continents(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getContinentFromValue(int value){
        for(Continents e : Continents.values()){
            if(value == e.value) return e.name();
        }
        return null;
    }
}
