package com.shesha.projects.panoslice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class InputDialog extends AppCompatDialogFragment {
    private EditText textCanvas;
    private EditText textColour;
    private InputDialogListener inputDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog,null);
        textCanvas = (EditText) view.findViewById(R.id.text_canvas);
        textColour = (EditText) view.findViewById(R.id.text_color);
        alertDialogBuilder.setView(view)
                .setTitle("Enter Text Details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textCanvasString = textCanvas.getText().toString();
                        String textColourString = textColour.getText().toString();
                        inputDialogListener.applyTexts(textCanvasString,textColourString);
                    }
                });
        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        inputDialogListener = (InputDialogListener) context;
    }

    public interface InputDialogListener
    {
        void applyTexts(String textCanvas, String textColour);
    }
}
