package dev.jacob_ba.foodiary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import dev.jacob_ba.foodiary.R;

import dev.jacob_ba.foodiary.adapters.RestaurantAdapter;
import dev.jacob_ba.foodiary.databinding.FragmentRestaurantsListBinding;
import dev.jacob_ba.foodiary.myDB;

public class RestaurantsListFragment extends Fragment {

    private FragmentRestaurantsListBinding binding;
    private RecyclerView recyclerView_restaurants;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView_restaurants = binding.getRoot().findViewById(R.id.restaurants_recyclerView);

        /*Restaurant tori = new Restaurant("Tori", "Asian", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/torii.png?alt=media&token=65a98e67-ceb8-479c-b04e-b098693caa89", 4.2f);
        Restaurant menTenTen = new Restaurant("MenTenTen", "Asian", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/mententen.png?alt=media&token=c257e013-7a48-4059-886b-d21da3b39114", 4.7f);
        Restaurant zozobra = new Restaurant("Zozobra", "Asian", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Zozobra.jpg?alt=media&token=3d089058-6f45-4f6a-9a9a-6c2b151c4619", 3.9f);
        Restaurant etnika = new Restaurant("Etnika", "Meat", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Etnika.jpg?alt=media&token=b1ce11b6-4aff-44d4-8344-413d73be8435", 3.7f);
        Restaurant meatbar = new Restaurant("Meat Bar", "Meat", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/MeatBar.jpg?alt=media&token=daf3f8bc-1675-4e8c-950e-66c3a328dfa4", 3.7f);
        Restaurant mcdonalds = new Restaurant("McDonalds", "Meat", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/McDonalds.png?alt=media&token=d46caaa2-e213-46bc-9128-1f0b2d4e1125", 3.7f);
        Restaurant burger_king = new Restaurant("Burger King", "Meat", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/burgerking.jpg?alt=media&token=59c19689-85a1-4ff9-96ff-632e21145aa9", 3.7f);
        Restaurant kfc = new Restaurant("KFC", "Meat", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/kfc.jpg?alt=media&token=054ff629-6a39-4073-b08e-f8deab9b90ec", 3.7f);
        Restaurant beni_hadayag = new Restaurant("Beni Hadayag", "Fish", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Beni_Hadayag.png?alt=media&token=5ea7288f-87b5-42a9-82dc-6c5bc350fb92", 3.7f);
        Restaurant joya = new Restaurant("Joya", "Italian", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Joya.jpg?alt=media&token=fb622278-6673-4bad-8223-a9f2c79296bd", 3.7f);


        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(tori);
        restaurants.add(menTenTen);
        restaurants.add(zozobra);
        restaurants.add(etnika);
        restaurants.add(meatbar);
        restaurants.add(mcdonalds);
        restaurants.add(burger_king);
        restaurants.add(kfc);
        restaurants.add(beni_hadayag);
        restaurants.add(joya);*/
        showRecycler();
        return root;
    }

    private void showRecycler() {
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, myDB.getInstance().getArrayList_restaurants());
        recyclerView_restaurants.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_restaurants.setItemAnimator(new DefaultItemAnimator());
        recyclerView_restaurants.setAdapter(restaurantAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}