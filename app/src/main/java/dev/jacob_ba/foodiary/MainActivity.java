package dev.jacob_ba.foodiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;
import java.util.List;

import dev.jacob_ba.foodiary.databinding.ActivityMainBinding;
import dev.jacob_ba.foodiary.fragments.DishesListFragmentDirections;
import dev.jacob_ba.foodiary.fragments.HomeFragmentDirections;
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragment;
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragmentDirections;
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.models.Restaurant;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_restaurants_list, R.id.navigation_dishes).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        ExtendedFloatingActionButton floatingActionButton = binding.fab;

        FloatingActionButtonHandler.getInstance().setFab(binding.fab);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                invalidateOptionsMenu();
                switch (destination.getId()) {
                    case R.id.navigation_home:
                        floatingActionButton.hide();
                        break;
                    case R.id.navigation_restaurants_list:
                        floatingActionButton.show();
                        floatingActionButton.shrink();
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navController.navigate(R.id.addRestaurantFragment);
                            }
                        });
                        break;
                    case R.id.navigation_dishes:
                        floatingActionButton.show();
                        floatingActionButton.shrink();
                        binding.fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {navController.navigate(R.id.addDishFragment);
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        String lbl = navController.getCurrentDestination().getLabel().toString();
        if (item.getTitle().toString().equals("filter")) {
            return false;
        }
        switch (lbl) {
            case "Restaurants List":
                Log.i("info", "Filtering Restaurants List");
                filterRestaurantsList(navController, item);
                break;
            case "Dishes List":
                Log.i("info", "Filtering Dishes List");
                filterDishesList(navController, item);
                break;
            case "Home":
                Log.i("info", "Home Settings");
                homeSettings(navController, item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void homeSettings(NavController navController, MenuItem item) {
        if (item.getTitle().toString().equals("Settings")) {
            navController.navigate(R.id.settingsFragment);
        }
    }

    private void filterDishesList(NavController navController, MenuItem item) {
        DishesListFragmentDirections.ActionNavigationDishesSelf action = DishesListFragmentDirections.actionNavigationDishesSelf();
        String item_in_menu = item.getTitle().toString();
        switch (item_in_menu) {
            case "Spicy":
                action.setFilter("Spicy");
                break;
            case "Vegan":
                action.setFilter("Vegan");
                break;
        }
        navController.navigate(action);
    }

    private void filterRestaurantsList(NavController navController, @NonNull MenuItem item) {
        RestaurantsListFragmentDirections.ActionNavigationRestaurantsListSelf action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
        String item_in_menu = item.getTitle().toString();
        switch (item_in_menu) {
            case "Asian":
                action.setFilter("Asian");
                break;
            case "Meat":
                action.setFilter("Meat");
                break;
            case "Fish":
                action.setFilter("Fish");
                break;
            case "Italian":
                action.setFilter("Italian");
                break;
        }
        navController.navigate(action);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        String lbl = navController.getCurrentDestination().getLabel().toString();
        getMenuInflater().inflate(R.menu.filter_options_menu, menu);

        if (lbl.equals("Restaurants List")) {
            menu.findItem(R.id.filter).setVisible(true);
            menu.findItem(R.id.settings).setVisible(false);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_restaurants_list, true);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_dishes_list, false);
        } else if (lbl.equals("Dishes List")) {
            menu.findItem(R.id.filter).setVisible(true);
            menu.findItem(R.id.settings).setVisible(false);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_restaurants_list, false);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_dishes_list, true);
        } else if (lbl.equals("Home")) {
            menu.findItem(R.id.filter).setVisible(false);
            menu.findItem(R.id.settings).setVisible(true);
        } else {
            menu.findItem(R.id.filter).setVisible(false);
            menu.findItem(R.id.settings).setVisible(false);
        }
        return true;
    }
}