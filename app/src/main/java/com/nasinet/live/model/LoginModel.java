package com.nasinet.live.model;

import com.nasinet.live.contract.LoginContract;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.CodeMsg;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.net.RetrofitClient;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginModel implements LoginContract.Model {


    @Override
    public Flowable<BaseResponse<UserConfig>> getCommonConfig() {
        return RetrofitClient.getInstance().getApi().getCommonConfig();
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> userLogin(RequestBody body) {
        return RetrofitClient.getInstance().getApi().userLogin(body);
    }


    @Override
    public Flowable<BaseResponse<CodeMsg>> getCode(RequestBody phone) {
        return RetrofitClient.getInstance().getApi().getCode(phone);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> userRegist(RequestBody body) {
        return RetrofitClient.getInstance().getApi().userRegist(body);
    }
    @Override
    public Flowable<BaseResponse<UserRegist>> changePwd(RequestBody body) {
        return RetrofitClient.getInstance().getApi().changePwd(body);
    }
    @Override
    public Flowable<BaseResponse> bindPhone(FormBody body) {
        return RetrofitClient.getInstance().getApi().bindPhone(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> qqlogin(FormBody body) {
        return RetrofitClient.getInstance().getApi().qqlogin(body);
    }

}
