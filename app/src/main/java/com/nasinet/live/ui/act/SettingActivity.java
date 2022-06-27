package com.nasinet.live.ui.act;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nasinet.live.R;
import com.nasinet.live.base.MyApp;
import com.nasinet.live.base.OthrBase2Activity;
import com.nasinet.live.dialog.LanguageDialog;
import com.nasinet.live.dialog.MessageDialog;
import com.nasinet.live.dialog.PriceDialog;
import com.nasinet.live.net.APIService;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.nasinet.utils.AppManager;
import com.nasinet.nasinet.utils.CleanDataUtils;
import com.orhanobut.hawk.Hawk;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

public class SettingActivity extends OthrBase2Activity implements View.OnClickListener {

    TextView tv_exit, tv_version, tv_cache,tv_language;
    RelativeLayout rl_clean, rl_forus, rl_rules, rl_language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        tv_exit = findViewById(R.id.tv_exit);
        rl_forus = findViewById(R.id.rl_forus);
        rl_rules = findViewById(R.id.rl_rules);
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText(getVersionCode());
        tv_cache = findViewById(R.id.tv_cache);
        rl_clean = findViewById(R.id.rl_clean);
        rl_language = findViewById(R.id.rl_language);
        tv_language=findViewById(R.id.tv_language);

        tv_cache.setText(CleanDataUtils.getTotalCacheSize(this));
        rl_forus.setOnClickListener(this);
        tv_cache.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        rl_rules.setOnClickListener(this);
        rl_language.setOnClickListener(this);

        String lang= Hawk.get("Language","0");
        switch (lang){
            case "1":
                tv_language.setText("简体中文");
                break;
            case "2":
                tv_language.setText("English");
                break;

        }

        if(!MyUserInstance.getInstance().hasToken()){
            tv_exit.setText("立刻登录");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MyUserInstance.getInstance().hasToken()){
            tv_exit.setText("立刻登录");
        }else{
            tv_exit.setText("退出登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initData() {

    }


    private String getVersionCode() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:

                initDialog();
                break;

            case R.id.tv_cache:
                CleanDataUtils.clearAllCache(SettingActivity.this);
                tv_cache.setText(CleanDataUtils.getTotalCacheSize(SettingActivity.this));
                break;

            case R.id.rl_forus:
                Intent i_1 = new Intent(SettingActivity.this, WebShopActivity.class);
                i_1.putExtra("jump_url", APIService.About);
                startActivity(i_1);
                break;
            case R.id.rl_rules:
                Intent i_2 = new Intent(SettingActivity.this, WebShopActivity.class);
                i_2.putExtra("jump_url", APIService.Privacy_2);
                startActivity(i_2);
                break;
            case R.id.rl_language:
                LanguageDialog.Builder builder = new LanguageDialog.Builder(this);
                builder.setOnFinishListener(new LanguageDialog.OnFinishListener() {
                    @Override
                    public void onFinish(String language) {
                        if (language.equals("1")) {
                                Hawk.put("Language","1");
                            tv_language.setText("简体中文");
                        } else if (language.equals("2")) {
                            Hawk.put("Language","2");
                            tv_language.setText("English");
                        }

                        AppManager.getAppManager().finishAllActivity();
                        AppManager.getAppManager().startActivity(HomeActivity.class);
                    }
                });
                builder.create().show();
                break;
        }
    }


    private void initDialog(){

        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setOnFinishListener(new MessageDialog.OnFinishListener() {
            @Override
            public void onFinish() {
                Hawk.remove("USER_REGIST");
                if(!MyUserInstance.getInstance().hasToken()){
                    AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
                }else{
                    MyUserInstance.getInstance().setUserInfo(null);

                    TIMManager.getInstance().logout(new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            ToastUtils.showT("退出成功");
                            ((MyApp)MyApp.getInstance()).imlogin();
                            if(MyUserInstance.getInstance().loginMode()){
                                tv_exit.setText("立刻登录");
                            }else{
                                AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
                            }

                        }

                        @Override
                        public void onSuccess() {
                            ToastUtils.showT("退出成功");
                            ((MyApp)MyApp.getInstance()).imlogin();
                            if(MyUserInstance.getInstance().loginMode()){
                                tv_exit.setText("立刻登录");
                            }else{

                                AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
                            }
                        }
                    });
                }
            }
        });
        builder.create().show();
    }



}
