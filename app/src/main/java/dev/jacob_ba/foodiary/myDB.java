package dev.jacob_ba.foodiary;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.models.Restaurant;

public class myDB {
    private static myDB myDatabase;
    private FirebaseDatabase database;
    private DatabaseReference reference_users;
    private FirebaseUser current_user;
    private ArrayList<Restaurant> arrayList_restaurants;
    private ArrayList<Dish> arrayList_dishes;
    private String lastDishKey;
    private String lastRestaurantKey;

    public static myDB getInstance() {
        if (myDatabase == null) {
            myDatabase = new myDB();
            myDatabase.database = FirebaseDatabase.getInstance();
            myDatabase.reference_users = myDatabase.database.getReference("Users");
            myDatabase.current_user = FirebaseAuth.getInstance().getCurrentUser();
        }
        return myDatabase;
    }

    public void loadRestaurants() {
        arrayList_restaurants = new ArrayList<>();

        reference_users.child(current_user.getUid()).child("Restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.i("info", "==============================Reading Restaurants from Database==============================");
                for (DataSnapshot restaurant_value : snapshot.getChildren()) {
                    arrayList_restaurants.add(restaurant_value.getValue(Restaurant.class));
                }
                Log.i("info", "==============================Finished Reading Restaurants from Database==============================");


                Log.i("info", "==============================Printing Restaurants==============================");
                for (Restaurant restaurant : arrayList_restaurants) {
                    Log.i("info", "" + restaurant.getName());
                }
                Log.i("info", "==============================Finished Printing Restaurants==============================");
                Log.i("info", "number of restaurants: " + Restaurant.id_of_next_restaurant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("info", "Failed to read value.", error.toException());
            }
        });
        if (getArrayList_restaurants().size() != 0) {
            String string_last_restaurant_key = getLastRestaurantKey();
            int int_last_restaurant_key = Integer.parseInt(string_last_restaurant_key) + 1;
            Restaurant.id_of_next_restaurant = int_last_restaurant_key;
        }
    }

    public void loadDishes() {
        arrayList_dishes = new ArrayList<>();
        reference_users.child(current_user.getUid()).child("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("info", "==============================Reading Dishes from Database==============================");
                for (DataSnapshot dish_value : snapshot.getChildren()) {
                    arrayList_dishes.add(dish_value.getValue(Dish.class));
                    //Log.i("info", "" + dish_value.getKey());
                }
                Log.i("info", "==============================Finished Reading Dishes from Database==============================");


                Log.i("info", "==============================Printing Dishes==============================");
                for (Dish dish : arrayList_dishes) {
                    Log.i("info", "" + dish.getName());
                }
                Log.i("info", "==============================Finished Printing Dishes==============================");
                Log.i("info", "Number of Dishes: " + Dish.id_of_next_dish);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("info", "Failed to read value.", error.toException());
            }
        });
        if (getArrayList_dishes().size() != 0) {
            String string_last_dish_key = getLastDishKey();
            int int_last_dish_key = Integer.parseInt(string_last_dish_key) + 1;
            Dish.id_of_next_dish = int_last_dish_key;
        }
    }

    public void addRestaurantToDatabase(Restaurant restaurant) {
        reference_users.child(current_user.getUid()).child("Restaurants").child(Integer.toString(restaurant.getId())).setValue(restaurant);
        arrayList_restaurants.add(restaurant);
    }

    public void addDishToDatabase(Dish dish) {
        reference_users.child(current_user.getUid()).child("Dishes").child(Integer.toString(dish.getId())).setValue(dish);
        getArrayList_dishes().add(dish);
    }

    public ArrayList<Restaurant> getArrayList_restaurants() {
        return arrayList_restaurants;
    }

    public ArrayList<Dish> getArrayList_dishes() {
        return arrayList_dishes;
    }

    public String getLastDishKey() {
        reference_users.child(current_user.getUid()).child("Dishes").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.i("info", "==============================Reading Last Dish From Database==============================");
                    Log.i("info", "==============================Last Dish Key: " + dataSnapshot.getKey() + "==============================");
                    lastDishKey = dataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("info", "Failed to read value.", error.toException());
            }
        });
        return lastDishKey;
    }

    public String getLastRestaurantKey() {
        reference_users.child(current_user.getUid()).child("Restaurants").orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.i("info", "==============================Reading Last Dish From Database==============================");
                    Log.i("info", "==============================Last Dish Key: " + dataSnapshot.getKey() + "==============================");
                    lastRestaurantKey = dataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("info", "Failed to read value.", error.toException());
            }
        });
        return lastRestaurantKey;
    }

    public Dish getDishById(int id) {
        for (Dish dish : arrayList_dishes) {
            if (dish.getId() == id)
                return dish;
        }
        return null;
    }
}
