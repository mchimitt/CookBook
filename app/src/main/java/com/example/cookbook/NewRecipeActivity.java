package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.example.cookbook.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class NewRecipeActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mDescEditText;
    private EditText mContentEditText;
    private EditText mIngredientEditText;
    private RecyclerView mRecyclerView;
    private IngredientAdapter iAdapter;
    private ArrayList<EditText> mIngredientName;
    private ArrayList<EditText> mIngredientAmount;
    private ArrayList<Ingredient> mIngredientData;

    //ArrayList<String> mRecipeTitles = MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        mTitleEditText = findViewById(R.id.recipe_edit_name);
        mDescEditText = findViewById(R.id.recipe_edit_desc);
        mContentEditText = findViewById(R.id.recipe_edit_content);
        mIngredientEditText = findViewById(R.id.recipe_edit_ingredients);

        mIngredientData = new ArrayList<>();
        iAdapter = new IngredientAdapter(this, mIngredientData);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mIngredientAmount = new ArrayList<>();
        mIngredientName = new ArrayList<>();

        initializeData();
    }

    private void initializeData() {
        mIngredientData.clear();
        for(int i=0; i<mIngredientName.size(); i++){
            mIngredientData.add(new Ingredient(mIngredientName.get(i), mIngredientAmount.get(i)));

        }
        iAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_no_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onBackPressed(){
        finish();
    }

    public void SubmitRecipe(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_TITLE", mTitleEditText.getText().toString());
        intent.putExtra("EXTRA_DESC", mDescEditText.getText().toString());
        intent.putExtra("EXTRA_CONTENT", mContentEditText.getText().toString());
        intent.putExtra("EXTRA_INGREDIENTS", mIngredientEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
        //startActivity(intent);

        //activity.recipeTitles.add(mTitleEditText.getText().toString());
        //activity.recipeDesc.add(mDescEditText.getText().toString());
        //finish();


    }

    public void NewIngredient(View view) {
        EditText sample = new EditText(this);
        EditText sample2 = new EditText(this);
        mIngredientName.add(sample);
        mIngredientAmount.add(sample2);
        //mIngredientData.add(new Ingredient(sample, sample2));
        //iAdapter.notifyDataSetChanged();
        initializeData();
    }
}