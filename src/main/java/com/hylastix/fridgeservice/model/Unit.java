package com.hylastix.fridgeservice.model;

import lombok.Getter;

@Getter
public enum Unit {

    GRAMS("Grams (g)"),
    KILOGRAMS("Kilograms (kg)"),
    LITRES("Litres (L)"),
    MILLILITRES("Millilitres (mL)"),
    PIECES("Pieces (pcs)");

    private String label;

    private Unit(final String label){
        this.label = label;
    }
}
