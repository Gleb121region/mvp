package ru.spbstu.mvp.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    OWNER_READ("owner:read"),
    OWNER_UPDATE("owner:update"),
    OWNER_CREATE("owner:create"),
    OWNER_DELETE("owner:delete"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete");

    @Getter
    private final String permission;
}
