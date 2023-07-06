package com.example.loginregister.ui.slideshow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.loginregister.Login;
import com.example.loginregister.MainActivity;
import com.example.loginregister.MainActivity2;
import com.example.loginregister.R;
import com.example.loginregister.databinding.FragmentGalleryBinding;
import com.example.loginregister.menu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class SlideshowFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}