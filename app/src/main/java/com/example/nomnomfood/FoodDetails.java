package com.example.nomnomfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.nomnomfood.Model.Food;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    private TextView txtFoodName, txtFoodPrice, txtFoodDescription;
    private ImageView imgFood;

    private CollapsingToolbarLayout toolbarLayout;
    private FloatingActionButton btnCart;
    private NumberPicker numberPicker;

    private String foodId;

    private FirebaseDatabase database;
    private DatabaseReference foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        // Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        // Init view
        numberPicker = findViewById(R.id.btnQuantity);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(20);

        btnCart = findViewById(R.id.btnCart);

        txtFoodName = findViewById(R.id.txtFoodName);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        txtFoodDescription = findViewById(R.id.txtFoodDescription);
        imgFood = findViewById(R.id.imgFood);

        toolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        // Get Food Id from intent
        Intent intent = getIntent();
        if (intent != null) {
            foodId = intent.getStringExtra("FoodId");
        }

        if (foodId != null && !foodId.isEmpty()) {
            getFoodDetails(foodId);
        }
    }

    private void getFoodDetails(String foodId) {
        foodList.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Food food = snapshot.getValue(Food.class);

                // Set image
                Picasso.get().load(food.getImage())
                        .into(imgFood);

                toolbarLayout.setTitle(food.getName());
                txtFoodName.setText(food.getName());
                txtFoodPrice.setText(food.getPrice());
                txtFoodDescription.setText(food.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}