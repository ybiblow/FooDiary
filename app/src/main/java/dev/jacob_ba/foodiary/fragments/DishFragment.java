package dev.jacob_ba.foodiary.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.FragmentDishBinding;
import dev.jacob_ba.foodiary.models.Dish;


public class DishFragment extends Fragment {
    private FragmentDishBinding binding;
    private ShapeableImageView image;
    private TextView textView_dish_name;
    private RatingBar dish_ratingBar;
    private Dish dish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDishBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bindViews();

        if (getArguments() != null) {
            getDish();
            setFields();
        }

        return root;
    }

    private void setFields() {

        textView_dish_name.setText(dish.getName());
        dish_ratingBar.setRating(dish.getRank());
        Glide.with(this)
                .load(dish.getImage_url())
                .placeholder(R.drawable.image_not_available)
                .into(binding.fragmentDishImage);

    }

    private void bindViews() {
        image = binding.fragmentDishImage;
        textView_dish_name = binding.fragmentDishTextViewName;
        dish_ratingBar = binding.fragmentDishRatingBar;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getDish() {
        DishFragmentArgs args = DishFragmentArgs.fromBundle(getArguments());
        dish = args.getDish();
        Log.i("info", "" + dish.getName());
    }
}