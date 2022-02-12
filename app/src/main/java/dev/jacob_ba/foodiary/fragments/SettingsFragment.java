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
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.jacob_ba.foodiary.R;
import dev.jacob_ba.foodiary.databinding.FragmentSettingsBinding;
import dev.jacob_ba.foodiary.myDB;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private TextInputEditText textField_DisplayName;
    private TextInputEditText textField_Password;
    private Button button_Reset_Password;
    private Button button_Choose_Picture;
    private Button button_Save_User_Changes;
    private FirebaseUser user;
    private CircleImageView profile_Image;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imageUri = data.getData();
                        profile_Image.setImageURI(imageUri);
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bindViews();
        user = myDB.getInstance().getCurrent_user();
        setOnClickListeners();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayImage();
        textField_DisplayName.setText(user.getDisplayName());
    }

    private void bindViews() {
        textField_DisplayName = binding.textFieldDisplayName;
        button_Reset_Password = binding.buttonResetPassword;
        textField_Password = binding.textFieldPassword;
        profile_Image = binding.circleImageViewProfileImage;
        button_Choose_Picture = binding.buttonChoosePicture;
        button_Save_User_Changes = binding.buttonSaveUserChanges;
    }

    private void setOnClickListeners() {
        button_Reset_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "Button pressed! " + textField_Password.getText().toString());
                String newPassword = textField_Password.getText().toString();
                if (isPasswordValid(newPassword)) {
                    textField_Password.setEnabled(false);
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("info", "User password updated.");
                                        Toast.makeText(getContext(), "User password updated", Toast.LENGTH_SHORT).show();
                                        textField_Password.setText("");
                                        textField_Password.setEnabled(true);
                                    }
                                }
                            });
                }
            }
        });
        button_Choose_Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        button_Save_User_Changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_Choose_Picture.setEnabled(false);
                textField_DisplayName.setEnabled(false);
                button_Save_User_Changes.setEnabled(false);
                if (isDisplayNameValid()) {
                    String displayName = textField_DisplayName.getText().toString();
                    UserProfileChangeRequest profileUpdates;
                    if (imageUri != null) {
                        profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .setPhotoUri(imageUri)
                                .build();
                    } else {
                        profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build();
                    }

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("info", "User profile updated.");
                                        Toast.makeText(getContext(), "User profile updated", Toast.LENGTH_SHORT).show();
                                        button_Choose_Picture.setEnabled(true);
                                        textField_DisplayName.setEnabled(true);
                                        button_Save_User_Changes.setEnabled(true);
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isDisplayNameValid() {
        String displayName = textField_DisplayName.getText().toString();
        if (displayName.length() > 1)
            return true;
        else {
            return false;
        }
    }

    private void choosePicture() {
        Log.i("info", "Creating Intent for choosing a picture");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    private boolean isPasswordValid(String newPassword) {
        int length = newPassword.length();
        if (length > 5)
            return true;
        else {
            Toast.makeText(getContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void displayImage() {
        String url;
        if (user.getPhotoUrl() != null) {
            url = user.getPhotoUrl().toString();
            Glide.with(this)
                    .load(url)
                    .into(profile_Image);
        } else {
            Glide.with(this)
                    .load(R.drawable.image_not_available)
                    .into(profile_Image);
        }

    }
}