package dev.jacob_ba.foodiary.fragments;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.FragmentAddRestaurantBinding;
import dev.jacob_ba.foodiary.handlers.FloatingActionButtonHandler;
import dev.jacob_ba.foodiary.models.Restaurant;
import dev.jacob_ba.foodiary.myDB;

public class AddRestaurantFragment extends Fragment {

    private FragmentAddRestaurantBinding binding;
    private RatingBar ratingBar;
    private TextInputEditText restaurant_name;
    private TextInputEditText restaurant_category;
    private TextInputEditText restaurant_rating_input;
    private AppCompatSeekBar restaurant_rating_seekBar;
    private ShapeableImageView add_restaurant_image;
    private Uri imageUri;
    private String restaurant_image_url;
    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imageUri = data.getData();
                        add_restaurant_image.setImageURI(imageUri);
                    }
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddRestaurantBinding.inflate(inflater, container, false);
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
                .load(R.drawable.image_not_available)
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
                choosePicture();
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

                    setViewsNotEnabled();

                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    // Create a storage reference from our app
                    StorageReference storageRef = storage.getReference();

                    StorageReference storage_restaurants_image_ref = storageRef.child("Restaurant_Pictures/" + UUID.randomUUID().toString());

                    storage_restaurants_image_ref.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Handle unsuccessful uploads
                            Toast.makeText(getContext(), "unable to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Handle successful upload
                            Toast.makeText(getContext(), "Uploaded image", Toast.LENGTH_SHORT).show();
                            storage_restaurants_image_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    restaurant_image_url = uri.toString();
                                    Restaurant restaurant = new Restaurant(name, category, restaurant_image_url, Integer.parseInt(str_rating));
                                    myDB.getInstance().addRestaurantToDatabase(restaurant);

                                    NavController navController = Navigation.findNavController(requireActivity(), requireParentFragment().getId());
                                    navController.navigate(R.id.action_addRestaurantFragment_to_navigation_restaurants);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void setViewsNotEnabled() {
        add_restaurant_image.setEnabled(false);
        restaurant_name.setEnabled(false);
        restaurant_category.setEnabled(false);
        restaurant_rating_seekBar.setEnabled(false);
        restaurant_rating_input.setEnabled(false);
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
        } else if (imageUri == null) {
            Toast.makeText(getContext(), "Please Pick an Image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}