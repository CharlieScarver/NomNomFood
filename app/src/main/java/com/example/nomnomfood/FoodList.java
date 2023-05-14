package com.example.nomnomfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nomnomfood.Model.Category;
import com.example.nomnomfood.Model.Food;
import com.example.nomnomfood.ViewHolder.FoodViewHolder;
import com.example.nomnomfood.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference foodList;

    private String categoryId;

    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Get intent here
        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getStringExtra("CategoryId");
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            loadFoodList(categoryId);
        }
    }

    private void loadFoodList(String categoryId) {
        // select * from Food where MenuId = {categoryId}
        Query query = foodList.orderByChild("MenuId").equalTo(categoryId);
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.txtFoodName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.imgFoodImage);

                final Food food = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this, food.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
                return new FoodViewHolder(view);
            }
        };

        Log.d("FoodList::loadFoodList", ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}