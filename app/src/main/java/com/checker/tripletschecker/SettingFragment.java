package com.checker.tripletschecker;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends DialogFragment {
    List<String> max_movement;

    public interface Max_Movement_SetListener {
        void onMax_Movement_Set(int max_movement);
    }

    private Max_Movement_SetListener max_Movement_SetListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        max_movement = new ArrayList<>();
        max_movement.add("5");
        max_movement.add("10");
        max_movement.add("15");
        max_movement.add("20");
        max_movement.add("25");
        max_movement.add("30");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.max_movement_value);
        builder.setItems(max_movement.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (max_movement.get(i)) {
                    case "5":
                        max_Movement_SetListener.onMax_Movement_Set(5);
                        break;
                    case "10":
                        max_Movement_SetListener.onMax_Movement_Set(10);
                        break;
                    case "15":
                        max_Movement_SetListener.onMax_Movement_Set(15);
                        break;
                    case "20":
                        max_Movement_SetListener.onMax_Movement_Set(20);
                        break;
                    case "25":
                        max_Movement_SetListener.onMax_Movement_Set(25);
                        break;
                    case "30":
                        max_Movement_SetListener.onMax_Movement_Set(30);
                        break;
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Max_Movement_SetListener)
                this.max_Movement_SetListener = (Max_Movement_SetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Max_Movement_setListener");
        }
        Log.d("Fragment", "Max_Movement_setListener(onAttach)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.max_Movement_SetListener = null;
        Log.d("Fragment", "Max_Movement_setListener(onDetach)");
    }
}
