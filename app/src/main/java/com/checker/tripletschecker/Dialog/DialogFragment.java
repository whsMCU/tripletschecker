package com.checker.tripletschecker.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.checker.tripletschecker.R;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    boolean m_save;

    public interface Save_Listener {
        void onSave_Set(boolean m_save);
    }

    private Save_Listener save_listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.db_save);
        builder.setMessage(R.string.db_save_message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                m_save = true;
                save_listener.onSave_Set(m_save);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                m_save = false;
                save_listener.onSave_Set(m_save);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Save_Listener)
                this.save_listener = (DialogFragment.Save_Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Save_Listener");
        }
        Log.d("Fragment", "Save_Listener(onAttach)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.save_listener = null;
        Log.d("Fragment", "Save_Listener(onDetach)");
    }
}
