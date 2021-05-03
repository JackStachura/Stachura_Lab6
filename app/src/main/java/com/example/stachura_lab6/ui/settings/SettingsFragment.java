package com.example.stachura_lab6.ui.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;

import com.example.stachura_lab6.R;
import com.example.stachura_lab6.dialogs.ConfirmationDialog;

public class SettingsFragment extends Fragment {

    private Observable<String> observable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        Button deleteAll = root.findViewById(R.id.deleteAllButton);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmationDialog().show(getActivity().getSupportFragmentManager(), "");


            }
        });
        return root;
    }
}

