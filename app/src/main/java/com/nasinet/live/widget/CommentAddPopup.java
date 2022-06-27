package com.nasinet.live.widget;

import android.content.Context;

import android.support.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.nasinet.live.R;
import com.nasinet.live.ui.adapter.CommentAdapter;

import java.util.ArrayList;


public class CommentAddPopup extends BottomPopupView {
    VerticalRecyclerView recyclerView;
    private ArrayList<String> data;
    private CommentAdapter commonAdapter;

    public CommentAddPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commonAdapter);

    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .85f);
    }
}