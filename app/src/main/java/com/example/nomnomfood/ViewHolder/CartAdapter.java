package com.example.nomnomfood.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nomnomfood.Model.Order;
import com.example.nomnomfood.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> orderList;
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Order order = orderList.get(position);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(order.getQuantity(), Color.RED);
        holder.imgCartItemCount.setImageDrawable(drawable);

        Locale locale = new Locale("en", "GB");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity());
        holder.txtCartItemPrice.setText(fmt.format(price));

        holder.txtCartItemName.setText(order.getProductName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}