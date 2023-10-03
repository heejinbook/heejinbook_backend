package com.book.heejinbook.enums;

public enum Category {
    로맨스(1),
    추리(2),
    에세이(3),
    고전(4),
    수필(5),
    SF(6),
    무협(7),
    시(8),
    판타지(9),
    공포(10);

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