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
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragment;
import dev.jacob_ba.foodiary.fragments.RestaurantsListFragmentDirections;
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.models.Restaurant;

public class MainActivity extends AppCompatActivity {

    private Activity a;
    private ActivityMainBinding binding;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).setLogo(R.drawable.logo)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginTheme)
                .build();
        signInLauncher.launch(signInIntent);
        // [END auth_fui_create_intent]
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.i("info", "User ID: " + user.getUid());
            Log.i("info", "User Display Name: " + user.getDisplayName().toString());

            Log.i("info", "Number of Restaurants: " + Restaurant.id_of_next_restaurant);
            Log.i("info", "Number of Dishes: " + Dish.id_of_next_dish);

            myDB.getInstance().loadRestaurants();
            myDB.getInstance().loadDishes();


        } else {

            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

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
                            public void onClick(View v) {
                                navController.navigate(R.id.action_navigation_dishes_to_addDishFragment);
                            }
                        });
                        break;
                }
            }
        });
        createSignInIntent();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterDishesList(NavController navController, MenuItem item) {
    }

    private void filterRestaurantsList(NavController navController, @NonNull MenuItem item) {
        RestaurantsListFragmentDirections.ActionNavigationRestaurantsListSelf action = null;
        String item_in_menu = item.getTitle().toString();
        switch (item_in_menu) {
            case "Asian":
                action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
                action.setFilter("Asian");
                break;
            case "Meat":
                action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
                action.setFilter("Meat");
                break;
            case "Fish":
                action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
                action.setFilter("Fish");
                break;
            case "Italian":
                action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
                action.setFilter("Italian");
                break;
            default:
                action = RestaurantsListFragmentDirections.actionNavigationRestaurantsListSelf();
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
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_restaurants_list, true);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_dishes_list, false);
        } else if (lbl.equals("Dishes List")) {
            menu.findItem(R.id.filter).setVisible(true);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_restaurants_list, false);
            menu.findItem(R.id.filter).getSubMenu().setGroupVisible(R.id.group_dishes_list, true);
        } else {
            menu.findItem(R.id.filter).setVisible(false);
        }
        return true;
    }
}