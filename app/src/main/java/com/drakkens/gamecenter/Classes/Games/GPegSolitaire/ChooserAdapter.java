package com.drakkens.gamecenter.Classes.Games.GPegSolitaire;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.drakkens.gamecenter.R;

public class ChooserAdapter extends RecyclerView.Adapter<ChooserAdapter.ViewHolder> {
    private int[] images = new int[]{R.drawable.peg_german, R.drawable.peg_diamond, R.drawable.peg_french, R.drawable.peg_english};
    private Context context;
    private PegChooser pegChooser;

    public ChooserAdapter(Context context, PegChooser pegChooser) {
        this.context = context;
        this.pegChooser = pegChooser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chooser_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view1 -> {
            pegChooser.startPeg(position);
        });
        holder.imageView.setBackground(AppCompatResources.getDrawable(context, images[position]));
//                holder.itemView.findViewById(R.id.chooserItemImage).setBackground(AppCompatResources.getDrawable(context, images[position]));

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chooserItemImage);
        }
    }
}
