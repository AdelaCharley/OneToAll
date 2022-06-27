package com.nasinet.live.ui.act;

import android.content.Intent;
import android.view.View;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpActivity;
import com.nasinet.live.base.MyApp;
import com.nasinet.live.contract.LoginContract;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.net.APIService;
import com.nasinet.live.presenter.LoginPresenter;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.imsdk.TIMManager;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @OnClick({R.id.phone_login, R.id.tv_rule,R.id.qq_login, R.id.wecha_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wecha_login:
                MyUserInstance.getInstance().getWxPayBuilder(this).wxLogin();
                break;
            case R.id.qq_login:
                Platform plat = ShareSDK.getPlatform(QQ.NAME);
                plat.removeAccount(true);
                plat.SSOSetting(false);
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        PlatformDb db = platform.getDb();
                        String gender = "";
                        switch (hashMap.get("gender").toString()) {
                            case "男":
                                gender = "1";
                                break;
                            case "女":
                                gender = "0";
                                break;
                            default:
                                gender = "0";
                                break;
                        }
                        mPresenter.qqlogin(db.get("unionid"), hashMap.get("nickname").toString(), gender, hashMap.get("figureurl_qq").toString());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                });
                plat.showUser(null);
                break;
            case R.id.phone_login:
                startActivity(new Intent(this, PhoneLoginActivity.class));
                break;
            case R.id.tv_rule:
                Intent i_2 = new Intent(LoginActivity.this, WebShopActivity.class);
                i_2.putExtra("jump_url", APIService.Privacy_1);
                startActivity(i_2);
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        JSONObject userRegist_string = (JSONObject) JSON.parse(Hawk.get("USER_REGIST"));
        UserRegist userRegist = JSON.toJavaObject(userRegist_string, UserRegist.class);
        JSONObject userConfig_string = (JSONObject) JSON.parse(Hawk.get("USER_CONFIG"));
        UserConfig userConfig = JSON.toJavaObject(userConfig_string, UserConfig.class);
        if (null != userRegist & null != userConfig) {
            MyUserInstance.getInstance().setUserInfo(userRegist);
            MyUserInstance.getInstance().setUserConfig(userConfig);
            TIMManager.getInstance().logout(null);
            ((MyApp)MyApp.getInstance()).imlogin();
            AppManager.getAppManager().startActivity(HomeActivity.class);
            AppManager.getAppManager().finishActivity(LoginActivity.class);
        }else {
            if(MyUserInstance.getInstance().loginMode()){
                MyUserInstance.getInstance().setUserConfig(userConfig);
                TIMManager.getInstance().logout(null);
                ((MyApp)MyApp.getInstance()).imlogin();
                AppManager.getAppManager().startActivity(HomeActivity.class);
                AppManager.getAppManager().finishActivity(LoginActivity.class);
            }
        }
    }

    @Override
    public void userLogin(BaseResponse<UserRegist> bean) {
        if (bean.isSuccess()) {
            MyUserInstance.getInstance().setUserInfo(bean.getData());
            String temp = JSON.toJSONString(bean.getData());
            Hawk.put("USER_REGIST", temp);
            ((MyApp)MyApp.getInstance()).imlogin();
            AppManager.getAppManager().startActivity(HomeActivity.class);
            AppManager.getAppManager().finishActivity();
        } else {
            ToastUtils.showT(bean.getMsg());
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
