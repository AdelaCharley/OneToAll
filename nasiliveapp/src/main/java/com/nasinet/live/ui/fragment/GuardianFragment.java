package com.nasinet.live.ui.fragment;

import android.view.View;

import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.SuperPlayerContrat;
import com.nasinet.live.presenter.SuperPlayerPresenter;

public class GuardianFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
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
