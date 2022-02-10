package dev.jacob_ba.foodiary.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.adapters.DishAdapter;
import dev.jacob_ba.foodiary.databinding.FragmentDishesBinding;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.models.Restaurant;
import dev.jacob_ba.foodiary.myDB;


public class DishesListFragment extends Fragment {
    private FragmentDishesBinding binding;
    private RecyclerView recyclerView_dishes;
    private String filter = "";
    private ArrayList<Dish> dishes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDishesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView_dishes = binding.getRoot().findViewById(R.id.dishes_recyclerView);

        dishes = myDB.getInstance().getArrayList_dishes();
        if (!getArguments().isEmpty()) {
            RestaurantsListFragmentArgs args = RestaurantsListFragmentArgs.fromBundle(getArguments());
            filter = args.getFilter();
            if (!filter.equals("default"))
                filterDishes();
            Log.i("info", "filter passed");
        }
        /*Dish egg_roll = new Dish("Egg Roll", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FEgg_Roll.jpg?alt=media&token=5ebd4d91-1443-41c0-9b89-f9c5d9d6725c", 4.8f);
        Dish tori_ramen = new Dish("Tori Ramen", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FTori_Ramen.jpg?alt=media&token=2649bce2-c03b-4861-8052-70d0444ea75e", 5.0f);
        Dish miso_soup = new Dish("Miso Soup", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FMiso_Soup.jpg?alt=media&token=b66f096a-da58-4015-87e8-4c5ab088086a", 5.0f);
        Dish gyoza = new Dish("Gyoza", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FGyoza.jpg?alt=media&token=31d9f9a8-6aec-4213-8b53-d8156b02588d", 5.0f);
        Dish okashi_corn = new Dish("Okashi Corn", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FOkashi%20Corn.jpg?alt=media&token=2038ce6e-da23-4e24-b512-b07c34e15b67", 5.0f);
        Dish edamame = new Dish("Edamame", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FEdamame.jpg?alt=media&token=90f50ee6-b90f-44c3-8783-84c46687d095", 5.0f);
        Dish chicken_cucumber_salad = new Dish("Chicken Cucumber Salad", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FChicken%20Cucumber%20Salad.jpg?alt=media&token=00d61b21-7be2-400f-862a-5bb9034f9cf8", 5.0f);
        Dish Gyoza_Sweet_Potato = new Dish("Gyoza Sweet Potato", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FGyoza%20Sweet%20Potato.jpg?alt=media&token=d3873fdc-aa11-4447-911a-4c7e05c9eeb2", 5.0f);
        Dish chicken_wings = new Dish("Checken Wings", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FChicken%20Wings.jpg?alt=media&token=d51a0323-09de-4dca-8e95-7c6fc20bb418", 5.0f);
        Dish Thai_Corn_Soup = new Dish("Thai Corn Soup", "https://firebasestorage.googleapis.com/v0/b/foodiary-9d602.appspot.com/o/Dishes_Pictures%2FThai%20Corn%20Soup.jpg?alt=media&token=fde2afbe-7240-4734-971f-e0509c704bcf", 5.0f);


        ArrayList<Dish> dishes = new ArrayList<>();
        dishes.add(egg_roll);
        dishes.add(tori_ramen);
        dishes.add(miso_soup);
        dishes.add(gyoza);
        dishes.add(okashi_corn);
        dishes.add(edamame);
        dishes.add(chicken_cucumber_salad);
        dishes.add(Gyoza_Sweet_Potato);
        dishes.add(chicken_wings);
        dishes.add(Thai_Corn_Soup);*/


        DishAdapter dishAdapter = new DishAdapter(this, dishes);
        recyclerView_dishes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_dishes.setItemAnimator(new DefaultItemAnimator());
        recyclerView_dishes.setAdapter(dishAdapter);


        return root;
    }

    private void filterDishes() {
        ArrayList<Dish> filtered_dishes = new ArrayList<>();
        for (Dish dish : dishes) {
            Log.i("info",""+dish.getAttributes());
            if (dish.getAttributes() != null) {
                for (String str : dish.getAttributes()) {
                    if (str.equals(filter)) {
                        filtered_dishes.add(dish);
                        break;
                    }
                }
            }
        }
        dishes = filtered_dishes;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}