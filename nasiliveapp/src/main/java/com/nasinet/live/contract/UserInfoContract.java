package com.nasinet.live.contract;

import com.nasinet.live.base.BaseView;
import com.nasinet.live.model.entity.Banners;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.UserConfig;

import io.reactivex.Flowable;

public interface UserInfoContract {
    interface Model {
        Flowable<BaseResponse<Banners>> getUserPage();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(BaseResponse<UserConfig> bean);
    }

    interface Presenter {
        /**
         * 获取用户配置信息
         *
         */
        void getUserPage();
    }
}
