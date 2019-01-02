package com.utilities;

public enum Continent {
    ALL(0),
    INDAR(2),
    HOSSIN(4),
    AMERISH(6),
    ESAMIR(8),
    VR_TRAINING_NC(96),
    VR_TRAINING_TR(97),
    VR_TRAINING_VS(98),
    CLEANROOM(200);

    private final int value;

    Continent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getContinentFromValue(int value){
        for(Continent e : Continent.values()){
            if(value == e.value) return e.name();
        }
        return null;
    }
}
