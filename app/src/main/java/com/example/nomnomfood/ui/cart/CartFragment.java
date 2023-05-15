package com.example.nomnomfood.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomnomfood.Common.Common;
import com.example.nomnomfood.Database.Database;
import com.example.nomnomfood.Home;
import com.example.nomnomfood.Model.Order;
import com.example.nomnomfood.Model.OrderRequest;
import com.example.nomnomfood.R;
import com.example.nomnomfood.ViewHolder.CartAdapter;
import com.example.nomnomfood.databinding.FragmentCartBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference orderRequest;

    private FragmentCartBinding binding;
    private TextView txtTotalPrice;
    private Button btnPlace;

    private List<Order> cart = new ArrayList<Order>();
    private CartAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        cartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Firebase
        database = FirebaseDatabase.getInstance();
        orderRequest = database.getReference("OrderRequest");

        // Init
        recyclerView = root.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = root.findViewById(R.id.txtTotalPrice);
        btnPlace = root.findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        loadFoodList();

        return root;
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("One More step!");
        alertDialog.setMessage("Enter your address: ");

        final EditText edtAddress = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(layoutParams);
        alertDialog.setView(edtAddress); // Add edit text to alert dialog
        alertDialog.setIcon(R.drawable.shopping_cart_24);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Create a new request
                OrderRequest request = new OrderRequest(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                // Submit to Firebase
                // Using current milliseconds as key
                orderRequest.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                // Delete cart
                //new Database(getActivity().getBaseContext()).cleanCart();
                Toast.makeText(getContext(), "Your order is placed. Thank you!", Toast.LENGTH_LONG).show();

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_home);
                navController.navigateUp();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadFoodList() {
        cart = new Database(getContext()).getCart();
        adapter = new CartAdapter(cart, getContext());
        recyclerView.setAdapter(adapter);

        // Calculate total price
        int total = 0;
        for (Order order : cart) {
            total += Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity());
        }

        Locale locale = new Locale("en", "GB");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}