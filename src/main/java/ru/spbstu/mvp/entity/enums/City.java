package ru.spbstu.mvp.entity.enums;

public enum City {
//    todo: добавить еще парочку городов
    MOSCOW("Москва"),
    SPB("Санкт-Петербург");

    private String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }
}
