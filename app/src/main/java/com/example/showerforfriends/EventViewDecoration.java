package com.example.showerforfriends;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EventViewDecoration extends RecyclerView.ItemDecoration {
    private final int decoHeight;

    public EventViewDecoration(int decoHeight)
    {
        this.decoHeight = decoHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = decoHeight;
    }
}
