package com.shesha.projects.panoslice.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shesha.projects.panoslice.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EditCanvasAdapter extends RecyclerView.Adapter<EditCanvasAdapter.CanvasViewHolder> {
    private HashMap<Integer,HashMap<String,String>> stickerInfoMap;
    private ArrayList<Boolean> expandedList;
    private Context context;

    public EditCanvasAdapter(HashMap<Integer,HashMap<String,String>> stickerInfoMap, Context context)
    {
        this.stickerInfoMap = stickerInfoMap;
        expandedList = new ArrayList<>();
        for (int i  = 0; i < stickerInfoMap.size(); i++)
        {
            expandedList.add(false);
        }
        this.context = context;
    }


    @NonNull
    @Override
    public CanvasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.canvas_row, parent, false);
        CanvasViewHolder viewHolder = new CanvasViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CanvasViewHolder holder, final int position) {
        int key = (int) stickerInfoMap.keySet().toArray()[position];
        HashMap<String,String> stickerMap = stickerInfoMap.get(key);
        holder.canvasColourEditText.setText(stickerMap.get("Colour"));
        holder.canvasTextEditText.setText(stickerMap.get("Text"));
        boolean isExpanded = expandedList.get(position);
        holder.relativeLayout.setVisibility(!isExpanded ? View.VISIBLE : View.GONE);
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String canvasText = holder.canvasTextEditText.getText().toString();
                String canvasColour = holder.canvasColourEditText.getText().toString();
                HashMap<String,String> stickerMap = new HashMap<>();
                stickerMap.put("Colour",canvasColour);
                stickerMap.put("Text",canvasText);
                stickerInfoMap.put(position,stickerMap);
                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("Sticker_Map",stickerInfoMap);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerInfoMap.remove(position);
                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("Sticker_Map",stickerInfoMap);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                notifyDataSetChanged();

            }
        });



    }

    @Override
    public int getItemCount() {
        return stickerInfoMap.size();
    }

    public class CanvasViewHolder extends RecyclerView.ViewHolder
    {
        private EditText canvasTextEditText;
        private EditText canvasColourEditText;
        private Button saveButton;
        private TextView canvasTitle;
        private RelativeLayout relativeLayout;
        private Button deleteButton;
        public CanvasViewHolder(@NonNull View itemView) {
            super(itemView);
            canvasTextEditText = itemView.findViewById(R.id.canvas_text_edittext);
            canvasColourEditText = itemView.findViewById(R.id.canvas_colour_edittext);
            saveButton = itemView.findViewById(R.id.save_canvas_btn);
            canvasTitle = itemView.findViewById(R.id.canvas_title);
            relativeLayout = itemView.findViewById(R.id.expandableLayout);
            deleteButton = itemView.findViewById(R.id.delete_canvas_btn);
            canvasTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean value = expandedList.get(getAdapterPosition());
                    expandedList.set(getAdapterPosition(),!value);
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
    }
}
