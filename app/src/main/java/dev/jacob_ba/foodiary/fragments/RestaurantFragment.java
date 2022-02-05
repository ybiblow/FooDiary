package dev.jacob_ba.foodiary.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.adapters.DishAdapter;
import dev.jacob_ba.foodiary.adapters.RestaurantAdapter;
import dev.jacob_ba.foodiary.databinding.FragmentRestaurantBinding;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.models.Restaurant;
import dev.jacob_ba.foodiary.myDB;

public class RestaurantFragment extends Fragment {

    private FragmentRestaurantBinding binding;
    private TextView restaurant_name;
    private TextView restaurant_category;
    private RatingBar restaurant_rating_bar;
    private Restaurant restaurant;
    private RecyclerView restaurant_recyclerView;
    private ArrayList<Dish> arrayList_dishes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bindViews();

        if (getArguments() != null) {
            getRestaurant();
            setFields();
            getDishes();
        }
        showRecycler();
        return root;
    }

    private void bindViews() {
        restaurant_name = binding.textViewRestaurantName;
        restaurant_category = binding.textViewRestaurantCategory;
        restaurant_rating_bar = binding.restaurantRatingBar;
        restaurant_recyclerView = binding.fragmentRestaurantRecyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getDishes() {
        arrayList_dishes = new ArrayList<Dish>();
        for (int id : restaurant.getArrayList_dishes_id()) {
            Dish dish = myDB.getInstance().getDishById(id);
            if (dish != null) {
                arrayList_dishes.add(dish);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();

        for (Dish dish : arrayList_dishes) {
            stringBuffer.append(dish.getName() + " ");
        }
        Log.i("info", "Dishes in Restaurant:" + stringBuffer.toString());
    }

    private void setFields() {
        restaurant_name.setText(restaurant.getName());
        restaurant_category.setText(restaurant.getCategory());
        restaurant_rating_bar.setRating(restaurant.getRank());
        Glide.with(this)
                .load(restaurant.getImage_url())
                .placeholder(R.drawable.image_not_available)
                .into(binding.fragmentRestaurantImage);
        binding.fragmentRestaurantImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    private void getRestaurant() {
        RestaurantFragmentArgs args = RestaurantFragmentArgs.fromBundle(getArguments());
        restaurant = args.getRestaurant();
    }

    private void showRecycler() {
        DishAdapter dishAdapter = new DishAdapter(this, arrayList_dishes);
        restaurant_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        restaurant_recyclerView.setItemAnimator(new DefaultItemAnimator());
        restaurant_recyclerView.setAdapter(dishAdapter);
    }
}