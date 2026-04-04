package com.wedding.wedding_invitation.domain.product.entity;

public enum ProductCategory {
    ENVELOPE("편지봉투"),
    TICKET("식권"),
    FREAM("액자");

    private  String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }
}
