package com.nasinet.live.contract;

import com.nasinet.live.base.BaseView;

public interface WalletContract {
    interface Model {
//        Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);
    }

    interface Presenter {

    }
}
