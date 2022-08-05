package com.example.cookbook;

public class Entry {

    private String title;
    private String info;
    private String recipe;
    private String ingredients;
    //private final int imageResource


    Entry(String title, String info, String recipe, String ingredients){
        this.title = title;
        this.info = info;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    String getTitle() { return title; }

    String getInfo() { return info; }

    String getRecipe() { return recipe; }

    String getIngredients() { return ingredients; }

}
