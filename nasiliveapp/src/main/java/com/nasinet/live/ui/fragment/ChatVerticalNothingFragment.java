package com.nasinet.live.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.SuperPlayerContrat;
import com.nasinet.live.presenter.SuperPlayerPresenter;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class ChatVerticalNothingFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {




    @BindView(R.id.close_iv)
    ImageView close_iv;


    public ChatVerticalNothingFragment() {
    }

    @Override
    protected void initView(View view) {
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);

    }








    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_verical_nothing;
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




    @OnClick({ R.id.close_iv})
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.close_iv:


                getActivity().finish();
                break;

        }
    }




}
