package dev.jacob_ba.foodiary.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.databinding.FragmentAddDishBinding;
import dev.jacob_ba.foodiary.models.Dish;
import dev.jacob_ba.foodiary.myDB;

public class AddDishFragment extends Fragment {

    private FragmentAddDishBinding binding;
    private ChipGroup chipGroup_Add_Attributes;
    private Button button_Add_Attribute;
    private TextInputEditText editText_Attribute_Name;
    private ShapeableImageView add_dish_image;
    private TextInputEditText dish_name;
    private RatingBar ratingBar;
    private AppCompatSeekBar dish_rating_seekBar;
    private TextInputEditText dish_rating_input;
    private Uri imageUri;
    private String dish_image_url;
    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imageUri = data.getData();
                        add_dish_image.setImageURI(imageUri);
                    }
                }
            });


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
        bindViews();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFab();
        setDefaultImage();
        setOnClicks();
    }

    private void bindViews() {
        dish_name = binding.textFieldDishName;
        dish_rating_input = binding.addDishRatingInput;
        dish_rating_seekBar = binding.addDishSeekbar;
        ratingBar = binding.addDishRatingBar;
        add_dish_image = binding.addDishImage;
        ratingBar.setRating(2.5f);
        chipGroup_Add_Attributes = binding.chipGroupAttributes;
        button_Add_Attribute = binding.buttonAddAttribute;
        editText_Attribute_Name = binding.editTextAttributeName;
    }

    private void setFab() {
        ExtendedFloatingActionButton fab = FloatingActionButtonHandler.getInstance().getFab();
        fab.show();
        fab.extend();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "========== All is good and the dish can be added ==========");

                if (isDishToBeAdded()) {
                    String name = dish_name.getText().toString();
                    String str_rating = dish_rating_input.getText().toString();

                    setViewsNotEnabled();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference storage_dishes_image_ref = storageRef.child("Dishes_Pictures/" + UUID.randomUUID().toString());

                    storage_dishes_image_ref.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Handle unsuccessful upload
                            Toast.makeText(getContext(), "unable to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Handle successful upload
                            Toast.makeText(getContext(), "Uploaded image", Toast.LENGTH_SHORT).show();
                            storage_dishes_image_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dish_image_url = uri.toString();
                                    ArrayList<String> attributes = new ArrayList<>();
                                    attributes.add("Spicy");
                                    attributes.add("Vegan");
                                    Dish dish = new Dish(name, dish_image_url, Integer.parseInt(str_rating), attributes);
                                    setAttributes(dish);
                                    myDB.getInstance().addDishToDatabase(dish);
                                    NavController navController = Navigation.findNavController(requireActivity(), requireParentFragment().getId());
                                    navController.navigate(R.id.action_addDishFragment_to_navigation_dishes);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void setAttributes(Dish dish) {
        ArrayList<String> attributes = new ArrayList<>();
        int chipGroupLength = chipGroup_Add_Attributes.getChildCount();
        for (int i = 0; i < chipGroupLength; i++) {
            Chip chip = (Chip) chipGroup_Add_Attributes.getChildAt(i);
            attributes.add(chip.getText().toString());
            Log.i("info", "Chip = " + chip.getText());
        }
        dish.setAttributes(attributes);
    }

    private void setViewsNotEnabled() {
        dish_name.setEnabled(false);
        dish_rating_seekBar.setEnabled(false);
        dish_rating_input.setEnabled(false);
        add_dish_image.setEnabled(false);
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
        } else if (imageUri == null) {
            Toast.makeText(getContext(), "Please Pick an Image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                choosePicture();
            }
        });
        button_Add_Attribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = editText_Attribute_Name.getText().toString();
                if (str.isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter an Attribute", Toast.LENGTH_SHORT).show();
                    return;
                }
                editText_Attribute_Name.setText("");
                Chip chip = (Chip) getLayoutInflater().inflate(R.layout.custom_chip, chipGroup_Add_Attributes, false);
                chip.setText(str);
                chipGroup_Add_Attributes.addView(chip);
            }
        });
    }

    private void choosePicture() {
        Log.i("info", "Creating Intent for choosing a picture");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    private void setDefaultImage() {
        Glide.with(this)
                .load(R.drawable.image_not_available)
                .into(binding.addDishImage);
    }
}