package com.book.heejinbook.enums;

public enum FilePath {
    USER_DIR("user/"),
    SEPARATE_POINT(".com/"),
    SHOP_DIR("shop/"),
    MENU_DIR("menu/"),
    REVIEW_DIR("review/");

    FilePath(String path) {
        this.path = path;
    }

    private final String path;

    public String getPath() {
        return path;
    }
}
