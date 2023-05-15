package com.example.nomnomfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nomnomfood.Database.Database;
import com.example.nomnomfood.Model.Food;
import com.example.nomnomfood.Model.Order;
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

    private Food currentFood;

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
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(FoodDetails.this).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        ""+numberPicker.getValue(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));

                Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_LONG).show();
            }
        });

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
                currentFood = snapshot.getValue(Food.class);

                // Set image
                Picasso.get().load(currentFood.getImage())
                        .placeholder(R.drawable.default_food_item)
                        .error(R.drawable.default_food_item)
                        .into(imgFood);

                toolbarLayout.setTitle(currentFood.getName());
                txtFoodName.setText(currentFood.getName());
                txtFoodPrice.setText(currentFood.getPrice());
                txtFoodDescription.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}