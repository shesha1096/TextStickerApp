package com.shesha.projects.panoslice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.FlipVerticallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import top.defaults.drawabletoolbox.DrawableBuilder;

public class MainActivity extends AppCompatActivity implements InputDialog.InputDialogListener {
    private static final String TAG = "PANOSLICE_APP";
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
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipVerticallyEvent());

        BitmapStickerIcon scaleIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_pause_24),
                        BitmapStickerIcon.LEFT_BOTTOM);
        scaleIcon.setIconEvent(new ScaleIconEvent());


         /*
        BitmapStickerIcon rotateIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_rotate_left_24),
                        BitmapStickerIcon.LEFT_BOTTOM);
        rotateIcon.setIconEvent(new ScaleIconEvent());

          */

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, scaleIcon ));
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
            }
        });
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
            textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(), com.xiaopo.flying.sticker.R.drawable.sticker_transparent_background));
            textStickers.add(textSticker);
            stickerView.addSticker(textSticker);
        }


    }
}