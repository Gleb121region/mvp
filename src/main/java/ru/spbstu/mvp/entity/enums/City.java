package ru.spbstu.mvp.entity.enums;

public enum City {
    MOSCOW("Москва"),
    SAINT_PETERSBURG("Санкт-Петербург"),
    NOVOSIBIRSK("Новосибирск"),
    YEKATERINBURG("Екатеринбург"),
    KAZAN("Казань"),
    NIZHNY_NOVGOROD("Нижний Новгород"),
    CHELYABINSK("Челябинск"),
    SAMARA("Самара"),
    UFA("Уфа"),
    ROSTOV_ON_DON("Ростов-на-Дону"),
    OMSK("Омск"),
    KRASNOYARSK("Красноярск"),
    VORONEZH("Воронеж"),
    PERM("Пермь"),
    VOLGOGRAD("Волгоград");

    private String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }
}
