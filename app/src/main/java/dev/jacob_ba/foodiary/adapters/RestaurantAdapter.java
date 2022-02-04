package dev.jacob_ba.foodiary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragment;
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragmentDirections;
import dev.jacob_ba.foodiary.models.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Fragment fragment;

    public RestaurantAdapter(Fragment fragment, ArrayList<Restaurant> restaurants) {
        this.fragment = fragment;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RestaurantViewHolder restaurantViewHolder = (RestaurantViewHolder) holder;
        Restaurant restaurant = getItem(position);
        restaurantViewHolder.setRestaurant(restaurant);
        restaurantViewHolder.restaurant_name.setText(restaurant.getName());
        restaurantViewHolder.restaurant_category.setText(restaurant.getCategory());
        restaurantViewHolder.restaurant_rating.setRating(restaurant.getRank());
        Glide.with(fragment).load(restaurant.getImage_url()).into(restaurantViewHolder.restaurant_image);


    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public Restaurant getItem(int position) {
        return restaurants.get(position);
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView restaurant_image;
        private MaterialTextView restaurant_name;
        private MaterialTextView restaurant_category;
        private AppCompatRatingBar restaurant_rating;
        Restaurant restaurant;

        public RestaurantViewHolder(@NonNull View itemView) {

            super(itemView);
            restaurant_image = itemView.findViewById(R.id.restaurant_image);
            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            restaurant_category = itemView.findViewById(R.id.restaurant_category);
            restaurant_rating = itemView.findViewById(R.id.restaurant_rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(fragment.requireActivity(), fragment.requireParentFragment().getId());
                    RestaurantsListFragmentDirections.ActionNavigationRestaurantsToRestaurantFragment action = RestaurantsListFragmentDirections.actionNavigationRestaurantsToRestaurantFragment();
                    action.setMessage(restaurant.getName());
                    action.setNumber(restaurant.getId());
                    navController.navigate(action);
                }
            });
        }

        public void setRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
        }
    }

}
