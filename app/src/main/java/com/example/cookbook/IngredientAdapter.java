package com.example.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Ingredient> mIngredientData;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredientData){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mIngredientData = ingredientData;
    }

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new IngredientAdapter.ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, int position) {
        //Ingredient mCurrent = mIngredientData.get(position);
        //holder.bindTo(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mIngredientData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText mName;
        private EditText mAmount;

        public ViewHolder(View itemView, IngredientAdapter adapter) {
            super(itemView);
            mName = itemView.findViewById(R.id.ingredient);
            mAmount = itemView.findViewById(R.id.amount);
        }

        //public void bindTo(Ingredient mCurrent) {
        //    mName = mCurrent.getName();
        //    mAmount = mCurrent.getAmount();
        //}
    }
}
