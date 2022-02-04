package dev.jacob_ba.foodiary.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.jacob_ba.foodiary.databinding.FragmentRestaurantBinding;

public class RestaurantFragment extends Fragment {

    private FragmentRestaurantBinding binding;
    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textViewText;

        if (getArguments() != null) {
            RestaurantFragmentArgs args = RestaurantFragmentArgs.fromBundle(getArguments());
            String message = args.getMessage();
            int number = args.getNumber();
            textView.setText(message + " " + number);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}