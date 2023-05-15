package com.example.nomnomfood.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.nomnomfood.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "NomNomDb.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCart() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";

        builder.setTables(sqlTable);
        Cursor cursor = builder.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(new Order(
                        cursor.getString(cursor.getColumnIndexOrThrow("ProductId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ProductName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Quantity")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Discount"))
                ));
            } while (cursor.moveToNext());
        }

        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetails(ProductId, ProductName, Quantity, Price, Discount) VALUES ('%s', '%s', '%s', '%s', '%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }
}
