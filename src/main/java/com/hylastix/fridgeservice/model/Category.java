package com.hylastix.fridgeservice.model;

public enum Category {

    FRUITS_AND_VEGETABLES("Fruits & Vegetables"),
    GRAINS("Grains"),
    PROTEINS("Proteins"),
    DAIRY("Dairy"),
    FATS_AND_OILS("Fats & Oils"),
    SWEETS_AND_TREATS("Sweets/Treats");

    private String message;

    public String getMessage(){
        return message;
    }

    private Category(final String message){
        this.message = message;
    }
}
