package dev.jacob_ba.foodiary.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.FragmentRestaurantBinding;
import dev.jacob_ba.foodiary.models.Restaurant;

public class RestaurantFragment extends Fragment {

    private FragmentRestaurantBinding binding;
    private TextView restaurant_name;
    private TextView restaurant_category;
    private RatingBar restaurant_rating_bar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        restaurant_name = binding.textViewRestaurantName;
        restaurant_category = binding.textViewRestaurantCategory;
        restaurant_rating_bar = binding.restaurantRatingBar;

        if (getArguments() != null) {
            RestaurantFragmentArgs args = RestaurantFragmentArgs.fromBundle(getArguments());

            Restaurant restaurant = args.getRestaurant();
            restaurant_name.setText(restaurant.getName());
            restaurant_category.setText(restaurant.getCategory());
            restaurant_rating_bar.setRating(restaurant.getRank());
            Glide.with(this)
                    .load(restaurant.getImage_url())
                    .placeholder(R.drawable.image_not_available)
                    .into(binding.fragmentRestaurantImage);

        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}