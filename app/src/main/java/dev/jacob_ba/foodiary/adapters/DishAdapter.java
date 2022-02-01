package dev.jacob_ba.foodiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.models.Dish;

public class DishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Dish> dishes = new ArrayList<>();
    private Fragment fragment;

    public DishAdapter(Fragment fragment, ArrayList<Dish> dishes) {
        this.fragment = fragment;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DishViewHolder dishViewHolder = (DishViewHolder) holder;
        Dish dish = getItem(position);
        dishViewHolder.dish_name.setText(dish.getName());
        dishViewHolder.dish_rating.setRating(dish.getRank());
        Glide.with(fragment).load(dish.getImage_url()).into(dishViewHolder.dish_image);

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public Dish getItem(int position) {
        return dishes.get(position);
    }


    public class DishViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView dish_image;
        private MaterialTextView dish_name;
        private AppCompatRatingBar dish_rating;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_image = itemView.findViewById(R.id.dish_image);
            dish_name = itemView.findViewById(R.id.dish_name);
            dish_rating = itemView.findViewById(R.id.dish_rating);
        }
    }
}
