package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

public class EditRecipeActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mDescEditText;
    private EditText mContentEditText;
    private EditText mIngredientsEditText;
    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_recipe);
        mTitleEditText = findViewById(R.id.change_edit_name);
        mDescEditText = findViewById(R.id.change_edit_desc);
        mContentEditText = findViewById(R.id.change_edit_content);
        mIngredientsEditText = findViewById(R.id.change_edit_ingredients);

        Intent received = getIntent();
        mTitleEditText.setText(received.getStringExtra("EXTRA_OLD_TITLE"));
        mDescEditText.setText(received.getStringExtra("EXTRA_OLD_DESC"));
        mContentEditText.setText(received.getStringExtra("EXTRA_OLD_CONTENT"));
        mIngredientsEditText.setText(received.getStringExtra("EXTRA_OLD_INGREDIENTS"));
        index = received.getIntExtra("EXTRA_INDEX", 0);

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
        inflater.inflate(R.menu.app_bar_no_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onBackPressed(){
        finish();
    }

    public void SubmitChanges(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("EXTRA_TITLE_CHANGED", mTitleEditText.getText().toString());
        intent.putExtra("EXTRA_DESC_CHANGED", mDescEditText.getText().toString());
        intent.putExtra("EXTRA_CONTENT_CHANGED", mContentEditText.getText().toString());
        intent.putExtra("EXTRA_INGREDIENTS_CHANGED", mIngredientsEditText.getText().toString());
        intent.putExtra("INDEX", index);
        setResult(DetailActivity.RESULT_CHANGED, intent);
        finish();
    }
}