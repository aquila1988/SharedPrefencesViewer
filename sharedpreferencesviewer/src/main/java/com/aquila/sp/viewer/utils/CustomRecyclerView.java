package com.aquila.sp.viewer.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.aquila.sp.viewer.R;


public class CustomRecyclerView extends RecyclerView {

    private int layoutStyle;
    private int spanCount;
    private int divideLine;
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView);

        layoutStyle = typedArray.getInt(R.styleable.CustomRecyclerView_layout_style,0);
        divideLine = typedArray.getInt(R.styleable.CustomRecyclerView_divide_line_style,0);
        spanCount = typedArray.getInteger(R.styleable.CustomRecyclerView_spanCount, 2);
        typedArray.recycle();

        setLayoutStyle(layoutStyle);
        setDivideLineStyle(divideLine);
    }

    private void setDivideLineStyle(int divideLine) {
        if (divideLine != 0){
            int orientation ;
            if (divideLine == 1){
                orientation = LinearLayoutManager.VERTICAL;
            }else {
                orientation = LinearLayoutManager.HORIZONTAL;
            }
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), orientation);
            addItemDecoration(dividerItemDecoration);
        }

    }


    public void setLayoutStyle(int layoutStyle){
        LayoutManager layoutManager;
        switch (layoutStyle){
            case 0:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case 2:
                layoutManager = new GridLayoutManager(getContext(),spanCount, GridLayoutManager.VERTICAL, false);
                break;
            case 3:
                layoutManager = new GridLayoutManager(getContext(),spanCount, GridLayoutManager.HORIZONTAL, false);
                break;
            case 1:
            default:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                break;
        }
        setLayoutManager(layoutManager);
    }


}
