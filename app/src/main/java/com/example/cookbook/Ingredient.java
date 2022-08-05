package com.example.cookbook;

import android.widget.EditText;

public class Ingredient {

    private EditText name;
    private EditText amount;

    public Ingredient(EditText name, EditText amount) {
        this.name = name;
        this.amount = amount;
    }

    EditText getName() { return name; }

    EditText getAmount() { return amount; }

}
