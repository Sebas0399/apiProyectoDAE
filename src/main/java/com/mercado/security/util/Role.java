package com.mercado.security.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    COMERCIANTE(Arrays.asList(Permission.SAVE_ONE_GUIA)),
    ADMINISTRADOR(Arrays.asList(Permission.SAVE_ONE_PRODUCT,Permission.READ_ALL_PRODUCTS,Permission.READ_ALL_USERS));

    Role(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
