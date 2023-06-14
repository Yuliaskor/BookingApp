package com.example.bookingapp.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category")
public enum Category {
    BEACH("Beach"),
    WINDMILLS("Windmills"),
    MODERN("Modern"),
    COUNTRYSIDE("Countryside"),
    POOLS("Pools"),
    ISLANDS("Islands"),
    LAKES("Lakes"),
    SKIING("Skiing"),
    CASTLES("Castles"),
    CAVES("Caves"),
    CAMPING("Camping"),
    ARCTIC("Arctic"),
    DESERTS("Deserts"),
    BARNS("Barns"),
    LUX("Lux");
    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
