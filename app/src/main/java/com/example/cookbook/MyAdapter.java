package com.example.cookbook;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Entry> mEntryData;
    private Context mContext;

    MyAdapter(Context context, ArrayList<Entry> entryData){
        this.mEntryData = entryData;
        this.mContext = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Entry currentEntry = mEntryData.get(position);
        holder.bindTo(currentEntry);
    }

    @Override
    public int getItemCount() { return mEntryData.size(); }




    //ViewHolder Class

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleText;
        private TextView mSubtitleText;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.title);
            mSubtitleText = itemView.findViewById(R.id.subtitle);

            itemView.setOnClickListener(this);
        }

        void bindTo(Entry currentEntry){
            //Populate textviews with data
            mTitleText.setText(currentEntry.getTitle());
            mSubtitleText.setText(currentEntry.getInfo());
        }

        @Override
        public void onClick(View view) {
            Entry currentEntry = mEntryData.get(getAdapterPosition());
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("title", currentEntry.getTitle());
            intent.putExtra("desc", currentEntry.getInfo());
            intent.putExtra("recipe", currentEntry.getRecipe());
            intent.putExtra("ingredients", currentEntry.getIngredients());
            intent.putExtra("index", getAdapterPosition());
            mContext.startActivity(intent);
        }

    }
}
