package com.shesha.projects.panoslice;

import android.util.Log;
import android.view.MotionEvent;

import com.xiaopo.flying.sticker.StickerIconEvent;
import com.xiaopo.flying.sticker.StickerView;

public class ScaleIconEvent implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {
        float xPosition = event.getX();
        float yPosition = event.getY();

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        float xPosition = event.getX();
        float yPosition = event.getY();
        stickerView.getCurrentSticker().getDrawable().setBounds((int) xPosition,(int) yPosition,(int)xPosition+200,(int)yPosition+200);
        Log.d("POS",String.valueOf(xPosition));


    }
}
