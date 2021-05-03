package com.example.stachura_lab6.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stachura_lab6.MainActivity;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ConfirmationDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This will delete all records. Are your sure?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Observable<String> observable = io.reactivex.Observable.just("");
                io.reactivex.Observer<String> observer = ((MainActivity) getActivity()).getDeleteAllObserver("");
                observable.observeOn(Schedulers.io()).subscribe(observer);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
            //Do nothing, dialog box will close.
        });

        return builder.create();



    }
}
