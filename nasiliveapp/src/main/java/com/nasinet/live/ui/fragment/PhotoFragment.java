package com.nasinet.live.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.presenter.HomePresenter;

import butterknife.BindView;

public class PhotoFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.photo_iv)
    ImageView photo_iv;

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (null != getArguments()){
            Bundle arguments = getArguments();
            Glide.with(getContext()).load(arguments.getString("url")).into(photo_iv);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
