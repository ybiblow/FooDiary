package dev.jacob_ba.foodiary.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.ActivityMainBinding;
import dev.jacob_ba.foodiary.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        initStuff();

        return root;
    }

    private void initStuff() {
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

        Dish egg_roll = new Dish("Egg Roll", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FEgg_Roll.jpg?alt=media&token=5ebd4d91-1443-41c0-9b89-f9c5d9d6725c", 4.8f);
        Dish tori_ramen = new Dish("Tori Ramen", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FTori_Ramen.jpg?alt=media&token=2649bce2-c03b-4861-8052-70d0444ea75e", 5.0f);
        Dish miso_soup = new Dish("Miso Soup", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FMiso_Soup.jpg?alt=media&token=b66f096a-da58-4015-87e8-4c5ab088086a", 5.0f);
        Dish gyoza = new Dish("Gyoza", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FGyoza.jpg?alt=media&token=31d9f9a8-6aec-4213-8b53-d8156b02588d", 5.0f);
        Dish okashi_corn = new Dish("Okashi Corn", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FOkashi%20Corn.jpg?alt=media&token=2038ce6e-da23-4e24-b512-b07c34e15b67", 5.0f);
        Dish edamame = new Dish("Edamame", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FEdamame.jpg?alt=media&token=90f50ee6-b90f-44c3-8783-84c46687d095", 5.0f);
        Dish chicken_cucumber_salad = new Dish("Chicken Cucumber Salad", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FChicken%20Cucumber%20Salad.jpg?alt=media&token=00d61b21-7be2-400f-862a-5bb9034f9cf8", 5.0f);
        Dish Gyoza_Sweet_Potato = new Dish("Gyoza Sweet Potato", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FGyoza%20Sweet%20Potato.jpg?alt=media&token=d3873fdc-aa11-4447-911a-4c7e05c9eeb2", 5.0f);
        Dish chicken_wings = new Dish("Checken Wings", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FChicken%20Wings.jpg?alt=media&token=d51a0323-09de-4dca-8e95-7c6fc20bb418", 5.0f);
        Dish Thai_Corn_Soup = new Dish("Thai Corn Soup", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FThai%20Corn%20Soup.jpg?alt=media&token=fde2afbe-7240-4734-971f-e0509c704bcf", 5.0f);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef_restaurants = database.getReference("Restaurants");
        DatabaseReference myRef_Dishes = database.getReference("Dish");

        myRef_restaurants.child(tori.getName()).setValue(tori);
        myRef_restaurants.child(menTenTen.getName()).setValue(menTenTen);
        myRef_Dishes.child(egg_roll.getName()).setValue(egg_roll);
        myRef_Dishes.child(tori_ramen.getName()).setValue(tori_ramen);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}