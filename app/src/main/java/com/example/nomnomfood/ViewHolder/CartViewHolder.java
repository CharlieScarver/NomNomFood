package com.example.nomnomfood.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomnomfood.ItemClickListener;
import com.example.nomnomfood.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtCartItemName, txtCartItemPrice;
    public ImageView imgCartItemCount;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCartItemName = itemView.findViewById(R.id.txtCartItemName);
        txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);
        imgCartItemCount = itemView.findViewById(R.id.imgCartItemCount);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAbsoluteAdapterPosition(), false);
    }

    public void setTxtCartItemName(TextView txtCartItemName) {
        this.txtCartItemName = txtCartItemName;
    }
}
