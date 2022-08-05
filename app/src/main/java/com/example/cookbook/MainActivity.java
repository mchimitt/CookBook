package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Entry> mEntryData;
    private MyAdapter myAdapter;
    public ArrayList<String> recipeTitles = new ArrayList<>();
    public ArrayList<String> recipeDesc = new ArrayList<>();
    public ArrayList<String> recipeContent = new ArrayList<>();
    public ArrayList<String> recipeIngredients = new ArrayList<>();

    private int adapterIndex = 0;

    static final String RECIPE_TITLES = "Recipe Titles";
    static final String RECIPE_DESC = "Recipe Descriptions";
    static final String RECIPE_CONTENT = "Recipe Content";
    static final String RECIPE_INGREDIENTS = "Recipe Ingredients";


    public static final int TEXT_REQUEST = 1;
    //public static final int RESULT_CHANGED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LOAD SAVED FILE
        loadData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Initialize the RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);

        //Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayList that will contain the data
        mEntryData = new ArrayList<>();

        //Initialize the adapter and set it to the RecyclerView
        myAdapter = new MyAdapter(this, mEntryData);
        mRecyclerView.setAdapter(myAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewRecipeActivity.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mEntryData, from, to);
                Collections.swap(recipeTitles, from, to);
                Collections.swap(recipeDesc, from, to);
                Collections.swap(recipeContent, from, to);
                Collections.swap(recipeIngredients, from, to);
                myAdapter.notifyItemMoved(from, to);
                saveData();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapterIndex = viewHolder.getAdapterPosition();
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Removal");
                myBuilder.setMessage("Are you sure you want to delete this recipe?");

                //Positive Button
                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mEntryData.remove(adapterIndex);
                        recipeTitles.remove(adapterIndex);
                        recipeContent.remove(adapterIndex);
                        recipeDesc.remove(adapterIndex);
                        recipeIngredients.remove(adapterIndex);
                        myAdapter.notifyItemRemoved(adapterIndex);
                        Toast.makeText(getApplicationContext(), "Recipe Removed", Toast.LENGTH_SHORT).show();
                        saveData();
                    }
                });

                myBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initializeData();
                    }
                });
                myBuilder.show();
            }
        });
        helper.attachToRecyclerView(mRecyclerView);


        if(savedInstanceState != null){
            loadData();
        }

        initializeData();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                recipeTitles.add(data.getStringExtra("EXTRA_TITLE"));
                recipeDesc.add(data.getStringExtra("EXTRA_DESC"));
                recipeContent.add(data.getStringExtra("EXTRA_CONTENT"));
                recipeIngredients.add(data.getStringExtra("EXTRA_INGREDIENTS"));
                saveData();
                initializeData();
            }
        }
    }


    private void initializeData() {
        //Get the resources from the XML file
        //String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        //String[] sportsInfo = getResources().getStringArray(R.array.sports_info);

        //Clear the existing data to avoid duplication
        mEntryData.clear();

        //Create the ArrayList of Entry objects with titles and information about each sport.
        for(int i=0; i<recipeTitles.size(); i++){
            mEntryData.add(new Entry(recipeTitles.get(i), recipeDesc.get(i), recipeContent.get(i), recipeIngredients.get(i)));
        }

        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {

        loadData();
        initializeData();
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        //outState.putStringArrayList(RECIPE_TITLES, recipeTitles);
        //outState.putStringArrayList(RECIPE_DESC, recipeDesc);
        //outState.putStringArrayList(RECIPE_CONTENT, recipeContent);
        saveData();

        super.onSaveInstanceState(outState);
    }

    //SAVES DATA TO THE FILE
    void saveData(){
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if(!file.exists()){
            file.mkdir();
        }

        //SAVING THE TITLES
        try{
            File titles = new File(file, "titles");
            FileWriter writer = new FileWriter(titles, false);
            for(String s : recipeTitles){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();

        } catch(Exception e) { }

        //SAVING THE DESCRIPTIONS
        try{
            File desc = new File(file, "descriptions");
            FileWriter writer = new FileWriter(desc, false);
            for(String s : recipeDesc){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();

        } catch(Exception e) {}

        //SAVING THE RECIPES
        try{
            File content = new File(file, "content");
            FileWriter writer = new FileWriter(content, false);
            for(String s : recipeContent){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();
        } catch(Exception e) {}

        try{
            File ingredients = new File(file, "ingredients");
            FileWriter writer = new FileWriter(ingredients, false);
            for(String s : recipeIngredients){
                writer.append(s);
                writer.append("//NEW//");
            }
            writer.flush();
            writer.close();
        } catch(Exception e) {}


    }

    void loadData(){

        File titles = new File(MainActivity.this.getFilesDir()+"/text/titles");
        File desc = new File(MainActivity.this.getFilesDir()+"/text/descriptions");
        File content = new File(MainActivity.this.getFilesDir()+"/text/content");
        File ingredients = new File(MainActivity.this.getFilesDir()+"/text/ingredients");


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

        recipeTitles = titlesTemp;
        recipeContent = contentTemp;
        recipeDesc = descTemp;
        recipeIngredients = ingredientsTemp;

    }

}