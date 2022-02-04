package dev.jacob_ba.foodiary.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
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
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.databinding.FragmentAddDishBinding;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.myDB;

public class AddDishFragment extends Fragment {

    private FragmentAddDishBinding binding;
    private ShapeableImageView add_dish_image;
    private TextInputEditText dish_name;
    private RatingBar ratingBar;
    private AppCompatSeekBar dish_rating_seekBar;
    private TextInputEditText dish_rating_input;

    public AddDishFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddDishBinding.inflate(inflater, container, false);
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

    private void setFab() {
        ExtendedFloatingActionButton fab = FloatingActionButtonHandler.getInstance().getFab();
        fab.show();
        fab.extend();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "im here");

                if (isDishToBeAdded()) {
                    String name = dish_name.getText().toString();
                    String str_rating = dish_rating_input.getText().toString();

                    Dish dish = new Dish(name, "https://img.traveltriangle.com/blog/wp-content/tr:w-700,h-400/uploads/2018/09/swiss-alps.jpg", Integer.parseInt(str_rating));
                    myDB.getInstance().addDishToDatabase(dish);
                    NavController navController = Navigation.findNavController(requireActivity(), requireParentFragment().getId());
                    navController.navigate(R.id.action_addDishFragment_to_navigation_dishes);
                }
            }
        });
    }

    private boolean isDishToBeAdded() {
        String name = dish_name.getText().toString();
        String str_rating = dish_rating_input.getText().toString();

        if (str_rating.isEmpty()) {
            Toast.makeText(getContext(), "Please Rate The Dish!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please Name The Dish!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setBinding() {
        dish_name = binding.textFieldDishName;
        dish_rating_input = binding.addDishRatingInput;
        dish_rating_seekBar = binding.addDishSeekbar;
        ratingBar = binding.addDishRatingBar;
        add_dish_image = binding.addDishImage;
        ratingBar.setRating(2.5f);
    }

    private void setOnClicks() {
        dish_rating_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float progress_for_ratingBar = (float) progress / 2;
                Log.i("info", "" + progress);
                dish_rating_input.setText("" + progress);
                ratingBar.setRating(progress_for_ratingBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dish_rating_input.addTextChangedListener(new TextWatcher() {
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
                    dish_rating_seekBar.setProgress(x);
                    ratingBar.setRating((float) x / 2);
                } catch (Exception e) {
                    Log.i("error", e.toString());
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        add_dish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Working!!!");
            }
        });
    }

    private void setDefaultImage() {
        Glide.with(this)
                .load("https://img.traveltriangle.com/blog/wp-content/tr:w-700,h-400/uploads/2018/09/swiss-alps.jpg")
                .placeholder(R.drawable.image_not_available)
                .into(binding.addDishImage);
    }
}