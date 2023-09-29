package com.book.heejinbook.enums;

public enum Category {
    소설(1),
    에세이(2),
    자서전(3),
    역사(4),
    과학(5),
    자연(6),
    로맨스(7),
    추리(8),
    판타지(9),
    공상과학(10);

    private final long categoryId;

    Category(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public static String getCategoryById(long id) {
        for (Category category : Category.values()) {
            if (category.getCategoryId() == id) {
                return category.name();
            }
        }
        throw new IllegalArgumentException("Invalid Category ID: " + id);
    }
}