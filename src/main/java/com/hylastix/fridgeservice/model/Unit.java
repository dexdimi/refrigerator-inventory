package com.hylastix.fridgeservice.model;

public enum Unit {

    GRAMS("Grams (g)"),
    KILOGRAMS("Kilograms (kg)"),
    LITRES("Litres (L)"),
    MILLILITRES("Millilitres (mL)"),
    PIECES("Pieces (pcs)");

    private String message;

    public String getMessage(){
        return message;
    }

    private Unit(final String message){
        this.message = message;
    }
}
