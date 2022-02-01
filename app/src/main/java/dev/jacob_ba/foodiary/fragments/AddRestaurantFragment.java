package dev.jacob_ba.foodiary.fragments;

import androidx.appcompat.widget.AppCompatSeekBar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.AddRestaurantFragmentBinding;
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.models.Restaurant;
import dev.jacob_ba.foodiary.myDB;

public class AddRestaurantFragment extends Fragment {

    private AddRestaurantFragmentBinding binding;
    private RatingBar ratingBar;
    private TextInputEditText restaurant_name;
    private TextInputEditText restaurant_category;
    private TextInputEditText restaurant_rating_input;
    private AppCompatSeekBar restaurant_rating_seekBar;
    private ShapeableImageView add_restaurant_image;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddRestaurantFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setBinding();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFab();
        setDefaultImage();
        setOnClicks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setBinding() {
        restaurant_name = binding.textFieldRestaurantName;
        restaurant_category = binding.textFieldCategory;
        restaurant_rating_input = binding.addRestaurantRatingInput;
        restaurant_rating_seekBar = binding.addRestaurantSeekbar;
        ratingBar = binding.addRestaurantRatingBar;
        add_restaurant_image = binding.addRestaurantImage;
        ratingBar.setRating(2.5f);
    }

    private void setDefaultImage() {
        Glide.with(this)
                .load("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h-400/uploads/2018/09/swiss-alps.jpg")
                .placeholder(R.drawable.image_not_available)
                .into(binding.addRestaurantImage);
    }

    private void setOnClicks() {
        restaurant_rating_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float progress_for_ratingBar = (float) progress / 2;
                Log.i("info", "" + progress);
                restaurant_rating_input.setText("" + progress);
                ratingBar.setRating(progress_for_ratingBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        restaurant_rating_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                try {
                    int x = Integer.parseInt(tmp);
                    if (x > 10)
                        x = 10;
                    if (x < 0)
                        x = 0;
                    restaurant_rating_seekBar.setProgress(x);
                    ratingBar.setRating((float) x / 2);
                } catch (Exception e) {
                    Log.i("error", e.toString());
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        add_restaurant_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Working!!!");
            }
        });
    }

    private void setFab() {
        ExtendedFloatingActionButton fab = FloatingActionButtonHandler.getInstance().getFab();
        fab.show();
        fab.extend();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRestaurantToBeAdded()) {
                    Log.i("info", "========== All is good and the restaurant can be added ==========");
                    Log.i("info", "" + myDB.getInstance().getLastRestaurantKey());

                    String name = restaurant_name.getText().toString();
                    String category = restaurant_category.getText().toString();
                    String str_rating = restaurant_rating_input.getText().toString();

                    Restaurant restaurant = new Restaurant(name, category, "https://img.traveltriangle.com/blog/wp-content/tr:w-700,h-400/uploads/2018/09/swiss-alps.jpg", Integer.parseInt(str_rating));
                    myDB.getInstance().addRestaurantToDatabase(restaurant);
                    NavController navController = Navigation.findNavController(requireActivity(), requireParentFragment().getId());
                    navController.navigate(R.id.action_addRestaurantFragment_to_navigation_restaurants);
                }
            }
        });

    }


    private boolean isRestaurantToBeAdded() {
        String name = restaurant_name.getText().toString();
        String category = restaurant_category.getText().toString();
        String str_rating = restaurant_rating_input.getText().toString();

        if (str_rating.isEmpty()) {
            Toast.makeText(getContext(), "Please Rate The Restaurant!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please Name The Restaurant!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (category.isEmpty()) {
            Toast.makeText(getContext(), "Please Provide A Category!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}