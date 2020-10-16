package com.shesha.projects.panoslice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements InputDialog.InputDialogListener {
    private StickerView stickerView;
    private List<TextSticker> textStickers;
    private Button addStickerButton;
    private String enteredText;
    private int textStickerCounter;
    private HashMap<Integer,HashMap<String,String>> textStickerInfo;
    private Button editCanvasButton;
    private List<Boolean> expandedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stickerView = (StickerView) findViewById(R.id.stickerView);
        addStickerButton = (Button) findViewById(R.id.text_sticker_btn);
        editCanvasButton = (Button) findViewById(R.id.editBtn);
        if (getIntent().getExtras() != null)
        {
            if ((HashMap<Integer, HashMap<String, String>>) getIntent().getExtras().get("Sticker_Info") != null)
            {
                textStickers = new ArrayList<>();
                expandedList = new ArrayList<>();
                textStickerInfo = (HashMap<Integer, HashMap<String, String>>) getIntent().getExtras().get("Sticker_Info");
                Log.d("Details",String.valueOf(textStickerInfo));
                updateTextStickers();
            }
        }

        textStickers = new ArrayList<>();
        expandedList = new ArrayList<>();
        textStickerInfo = new HashMap<>();
        enteredText = "";
        textStickerCounter = 0;

        addStickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog inputDialog = new InputDialog();
                inputDialog.show(getSupportFragmentManager(),"input dialog");
            }
        });
        editCanvasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Details_Intent",String.valueOf(textStickerInfo));
                Intent intent = new Intent(MainActivity.this,EditCanvasDetailsActivity.class);
                intent.putExtra("Sticker_Info",textStickerInfo);
                startActivity(intent);
                finish();
            }
        });

    }

    private void updateTextStickers() {
        Iterator it = textStickerInfo.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            HashMap<String,String> stickerMap  = (HashMap<String, String>) pair.getValue();
            String textColour = stickerMap.get("Colour");
            Log.d("Colour:",textColour);
            textColour = textColour.toUpperCase();
            TextSticker textSticker = new TextSticker(MainActivity.this);
            textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.sticker_transparent_background));
            textSticker.setTextColor(Color.parseColor(textColour));
            textSticker.setText(stickerMap.get("Text"));
            textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            textSticker.resizeText();
            textSticker.setFlippedHorizontally(true);
            textStickers.add(textSticker);

            //it.remove();
        }
        for (TextSticker textSticker : textStickers)
        {
            stickerView.addSticker(textSticker);
        }
        Log.d("Setter",String.valueOf(textStickerInfo));
        setTextStickerInfo(textStickerInfo);
    }

    private void setTextStickerInfo(HashMap<Integer, HashMap<String, String>> textStickerInfo) {
        this.textStickerInfo = textStickerInfo;
        Log.d("Setter",String.valueOf(textStickerInfo));
    }

    @Override
    public void applyTexts(String textCanvas, String textColour) {
        if (textCanvas.equals("") || textColour.equals(""))
        {
            Toast.makeText(MainActivity.this,"Please enter both the values.",Toast.LENGTH_LONG).show();
        }
        else
        {
            HashMap<String,String> stickerMap = new HashMap<>();
            stickerMap.put("Text",textCanvas);
            stickerMap.put("Colour",textColour);
            String stickerString = String.valueOf(textStickerCounter);
            textStickerInfo.put(textStickerCounter++,stickerMap);
            expandedList.add(false);
            Log.d("Details",String.valueOf(textStickerInfo.size()));
            //updateTextStickers();
            TextSticker textSticker = new TextSticker(MainActivity.this);
            textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.sticker_transparent_background));
            textSticker.setTextColor(Color.parseColor(textColour));
            textSticker.setText(textCanvas);
            textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            textSticker.resizeText();
            textSticker.setFlippedHorizontally(true);
            textStickers.add(textSticker);
            stickerView.addSticker(textSticker);
        }


    }
}