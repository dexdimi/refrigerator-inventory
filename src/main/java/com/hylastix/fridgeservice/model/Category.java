package com.hylastix.fridgeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Category {

    FRUITS_AND_VEGETABLES("Fruits & Vegetables"),
    GRAINS("Grains"),
    PROTEINS("Proteins"),
    DAIRY("Dairy"),
    FATS_AND_OILS("Fats & Oils"),
    SWEETS_AND_TREATS("Sweets/Treats");

    private String label;

    private Category(final String message){
        this.label = message;
    }
}
