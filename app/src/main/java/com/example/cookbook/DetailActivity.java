package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailActivity extends AppCompatActivity {

    private TextView recipeContent;
    private TextView recipeTitle;
    private TextView recipeDesc;
    private TextView recipeIngredient;
    private int ind;

    public static final int TEXT_REQUEST = 1;
    public static final int RESULT_CHANGED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recipeTitle = findViewById(R.id.detail_recipe_title);
        recipeDesc = findViewById(R.id.detail_recipe_desc);
        recipeContent = findViewById(R.id.detail_recipe_content);
        recipeIngredient = findViewById(R.id.detail_recipe_ingredients);


        recipeTitle.setText(getIntent().getStringExtra("title"));
        recipeDesc.setText(getIntent().getStringExtra("desc"));
        recipeContent.setText(getIntent().getStringExtra("recipe"));
        recipeIngredient.setText(getIntent().getStringExtra("ingredients"));
        ind = getIntent().getIntExtra("index", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(DetailActivity.this, EditRecipeActivity.class);
                //ADD EXTRAS
                intent.putExtra("EXTRA_OLD_TITLE", recipeTitle.getText().toString());
                intent.putExtra("EXTRA_OLD_DESC", recipeDesc.getText().toString());
                intent.putExtra("EXTRA_OLD_CONTENT", recipeContent.getText().toString());
                intent.putExtra("EXTRA_OLD_INGREDIENTS", recipeIngredient.getText().toString());
                intent.putExtra("EXTRA_INDEX", ind);
                startActivityForResult(intent, TEXT_REQUEST);
                return true;
            case android.R.id.home:
                finish();
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_CHANGED){
                int adapterIndex = data.getIntExtra("INDEX", 0);
                String newTitle = data.getStringExtra("EXTRA_TITLE_CHANGED");
                String newDesc = data.getStringExtra("EXTRA_DESC_CHANGED");
                String newContent = data.getStringExtra("EXTRA_CONTENT_CHANGED");
                String newIngredients = data.getStringExtra("EXTRA_INGREDIENTS_CHANGED");

                recipeTitle.setText(newTitle);
                recipeDesc.setText(newDesc);
                recipeContent.setText(newContent);
                recipeIngredient.setText(newIngredients);

                ApplyChanges(adapterIndex, newTitle, newDesc, newContent, newIngredients);

                //recipeTitles.set(adapterIndex, data.getStringExtra("EXTRA_TITLE_CHANGED"));
                //recipeDesc.set(adapterIndex, data.getStringExtra("EXTRA_DESC_CHANGED"));
                //recipeContent.set(adapterIndex, data.getStringExtra("EXTRA_CONTENT_CHANGED"));
                //saveData();
                //initializeData();
            }
        }
    }

    public void ApplyChanges(int index, String newTitle, String newDesc, String newContent, String newIngredients){
        File titles = new File(this.getFilesDir()+"/text/titles");
        File desc = new File(this.getFilesDir()+"/text/descriptions");
        File content = new File(this.getFilesDir()+"/text/content");
        File ingredients = new File(this.getFilesDir()+"/text/ingredients");

        ArrayList<String> titlesTemp = new ArrayList<>();
        ArrayList<String> descTemp = new ArrayList<>();
        ArrayList<String> contentTemp = new ArrayList<>();
        ArrayList<String> ingredientsTemp = new ArrayList<>();

        try{
            Scanner sc = new Scanner(titles);
            sc.useDelimiter("//NEW//");
            while(sc.hasNext()){
                titlesTemp.add(sc.next());
            }
        } catch (Exception e) {}

        try{
            Scanner sc = new Scanner(desc);
            sc.useDelimiter("//NEW//");
            while(sc.hasNext()){
                descTemp.add(sc.next());
            }
        } catch (Exception e) {}

        try{
            Scanner sc = new Scanner(content);
            sc.useDelimiter("//NEW//");
            while(sc.hasNext()){
                contentTemp.add(sc.next());
            }
        } catch (Exception e) {}

        try{
            Scanner sc = new Scanner(ingredients);
            sc.useDelimiter("//NEW//");
            while(sc.hasNext()){
                ingredientsTemp.add(sc.next());
            }
        } catch (Exception e) {}

        //SETTING UP OUR TEMPORARY ARRAYLISTS

        titlesTemp.set(index, newTitle);
        descTemp.set(index, newDesc);
        contentTemp.set(index, newContent);
        ingredientsTemp.set(index, newIngredients);

        //NOW WE SAVE THE DATA TO THE FILE

        //SAVING THE TITLES
        try{
            FileWriter writer = new FileWriter(titles, false);
            for(String s : titlesTemp){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();

        } catch(Exception e) { }

        //SAVING THE DESCRIPTIONS
        try{
            FileWriter writer = new FileWriter(desc, false);
            for(String s : descTemp){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();

        } catch(Exception e) {}

        //SAVING THE RECIPES
        try{
            FileWriter writer = new FileWriter(content, false);
            for(String s : contentTemp){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();
        } catch(Exception e) {}

        //SAVING THE INGREDIENTS
        try{
            FileWriter writer = new FileWriter(ingredients, false);
            for(String s : ingredientsTemp){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();
        } catch(Exception e) {}


        //ALL DONE!
        //although this is definitely not the most efficient solution to this problem that
        // I have come across, but it is okay at the current stage of the project.

    }


}