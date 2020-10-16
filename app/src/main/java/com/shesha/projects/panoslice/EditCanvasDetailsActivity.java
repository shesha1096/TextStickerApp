package com.shesha.projects.panoslice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shesha.projects.panoslice.adapters.EditCanvasAdapter;

import java.util.HashMap;

public class EditCanvasDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditCanvasAdapter editCanvasAdapter;
    private HashMap<Integer,HashMap<String,String>> stickerInfoMap;
    private Button doneButton;
    private static HashMap<Integer,HashMap<String,String>> stickerInfoMapStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_canvas_details);
        recyclerView = findViewById(R.id.canvas_recycler_view);
        doneButton = findViewById(R.id.doneBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickerInfoMap = new HashMap<>();
        editCanvasAdapter = new EditCanvasAdapter((HashMap<Integer, HashMap<String, String>>) getIntent().getExtras().get("Sticker_Info"),getApplicationContext());
        stickerInfoMapStatic = (HashMap<Integer, HashMap<String, String>>) getIntent().getExtras().get("Sticker_Info");
        recyclerView.setAdapter(editCanvasAdapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCanvasDetailsActivity.this,MainActivity.class);
                if (stickerInfoMap.size() == 0)
                {
                    intent.putExtra("Sticker_Info",EditCanvasDetailsActivity.stickerInfoMapStatic);
                    Log.d("Sticker_Info", String.valueOf(stickerInfoMapStatic));
                }
                else
                {
                    intent.putExtra("Sticker_Info",stickerInfoMap);
                }


                startActivity(intent);
                finish();
            }
        });

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            stickerInfoMap = (HashMap<Integer, HashMap<String, String>>) intent.getSerializableExtra("Sticker_Map");
            Log.d("Details",String.valueOf(stickerInfoMap));

        }
    };

}