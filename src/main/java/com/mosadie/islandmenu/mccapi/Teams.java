package com.mosadie.islandmenu.mccapi;

public enum Teams {
    RED("Red Rabbits"),
    ORANGE("Orange Ocelots"),
    YELLOW("Yellow Yaks"),
    LIME("Lime Llamas"),
    GREEN("Green Geckos"),
    CYAN("Cyan Coyotes"),
    AQUA("Aqua Axolotls"),
    BLUE("Blue Bats"),
    PURPLE("Purple Pandas"),
    PINK("Pink Parrots"),
    SPECTATORS("Spectators"),
    NONE("None");

    private final String name;

    Teams(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
