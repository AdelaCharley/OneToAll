package com.nasinet.live.contract;

import com.nasinet.live.base.BaseView;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.CodeMsg;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.model.entity.UserRegist;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public interface LoginContract {
    interface Model {
        Flowable<BaseResponse<UserConfig>> getCommonConfig();
        Flowable<BaseResponse<UserRegist>> userLogin(RequestBody body);
        Flowable<BaseResponse<CodeMsg>> getCode(RequestBody body);
        Flowable<BaseResponse<UserRegist>> userRegist(RequestBody body);
        Flowable<BaseResponse<UserRegist>> changePwd(RequestBody body);
        Flowable<BaseResponse> bindPhone(FormBody body);
        Flowable<BaseResponse<UserRegist>> qqlogin(FormBody body);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

       default void getCommonConfig(BaseResponse<UserConfig> bean){}
        default  void userLogin(BaseResponse<UserRegist> bean){}
        default void getCode(BaseResponse<CodeMsg> bean){}
        default void userRegist(BaseResponse<UserRegist> bean){}
        default void bindPhone(BaseResponse response) {
        }

    }

    interface Presenter {
        /**
         * 获取用户配置信息
         *
         */
        void getCommonConfig();
        void userLogin(String phone,String pwd);
        void getCode(String phone);
        void userRegist(String phone,String pwd,String smscode,String invite_code);
        void changePwd(String phone,String pwd,String smscode);
        void bindPhone(String mobile,String pwd,String code);
        void qqlogin(String unionid,String nick_name,String gender,String icon);
    }
}
