package com.nasinet.live.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.nasinet.live.ui.act.LoginActivity;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.util.TxPicturePushUtils;
import com.nasinet.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.bugly.crashreport.CrashReport;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.ugckit.UGCKit;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.module.upload.TCUserMgr;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLog;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.ugc.TXUGCBase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;


public class MyApp extends Application {
    private static Context mContext;
    //腾讯短视频KEY
    String ugcLicenceUrl = ""; //您从控制台申请的 licence url
    String ugcKey = "";
    private boolean login_mode = false;

    public boolean isLogin_mode() {
        return login_mode;
    }

    public void setLogin_mode(boolean login_mode) {
        this.login_mode = login_mode;
    }

    public boolean license = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {

                Log.e("@@", "加载内核是否成功:" + b);
            }
        });
        AppManager.getAppManager().setmApplication(this);
        Hawk.init(this).build();
        mContext = getApplicationContext();
        Fresco.initialize(this);
        TCConfigManager.init(this);

        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);
        UGCKit.init(this);
        TxPicturePushUtils.getInstance().setmContext(mContext);
        //移动分享SDK
        MobSDK.submitPolicyGrantResult(false, null);
        MobSDK.init(this);

        //   initSDK();

        // Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    public static Context getInstance() {
        return mContext;
    }

    public Application getApplication() {
        return this;
    }


    /**
     * 初始化SDK，包括Bugly，LiteAVSDK等
     */
    public void initSDK() {
        //启动bugly组件，bugly组件为腾讯提供的用于crash上报和分析的开放组件，如果您不需要该组件，可以自行移除
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TXLiveBase.getSDKVersionStr());
        CrashReport.initCrashReport(getApplicationContext(), UGCKitConstants.BUGLY_APPID, true, strategy);

        TCUserMgr.getInstance().initContext(getApplicationContext());
        TXLog.w("TAG", "app init sdk");
    }

    public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            //如果异常时在AsyncTask里面的后台线程抛出的
            //那么实际的异常仍然可以通过getCause获得
            Throwable cause = ex;
            while (null != cause) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            //stacktraceAsString就是获取的carsh堆栈信息
            final String stacktraceAsString = result.toString();
            printWriter.close();
            AppManager.getAppManager().AppExit(getApplicationContext());
        }
    }


    public void initIMSDK() {
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            int sdkAppId = MyUserInstance.getInstance().getUserConfig().getConfig().getIm_sdkappid();
            TIMSdkConfig config = new TIMSdkConfig(sdkAppId)
                    .enableLogPrint(false)
                    .setLogLevel(TIMLogLevel.OFF)
                    .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");


            //初始化 SDK
            TIMManager.getInstance().init(getApplicationContext(), config);

            //基本用户配置
            TIMUserConfig userConfig = new TIMUserConfig()
                    //设置用户状态变更事件监听器
                    .setUserStatusListener(new TIMUserStatusListener() {
                        @Override
                        public void onForceOffline() {
                            //被其他终端踢下线
                            Hawk.remove("USER_REGIST");
                            MyUserInstance.getInstance().setUserInfo(null);
                            TIMManager.getInstance().logout(null);
                            AppManager.getAppManager().startActivity(LoginActivity.class);
                            AppManager.getAppManager().finishAllActivity();
                            ToastUtils.showT("您的账号已在其他地方登录");
                            Log.e("TXXXXXXAA", "您的账号已在其他地方登录");
                        }

                        @Override
                        public void onUserSigExpired() {
                            //用户签名过期了，需要刷新 userSig 重新登录 IM SDK
                            Hawk.remove("USER_REGIST");
                            MyUserInstance.getInstance().setUserInfo(null);
                            TIMManager.getInstance().logout(null);
                            AppManager.getAppManager().startActivity(LoginActivity.class);
                            AppManager.getAppManager().finishAllActivity();
                            ToastUtils.showT("您的账号已登录已过期");
                            Log.e("TXXXXXXBB", "您的账号已登录已过期");
                        }


                    })

                    //设置群组事件监听器
                    .setGroupEventListener(new TIMGroupEventListener() {
                        @Override
                        public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                            Log.i("TXXXXXonGroupTipsEvent", "onGroupTipsEvent, type: " + elem.getTipsType());
                        }
                    });
            //禁用本地所有存储
            userConfig.disableStorage();
            //开启消息已读回执
            userConfig.enableReadReceipt(false);
            userConfig.disableAutoReport(true);

            //将用户配置与通讯管理器进行绑定
            TIMManager.getInstance().setUserConfig(userConfig);
            // identifier 为用户名，userSig 为用户登录凭证

        }
    }


    public void imlogin() {

        TIMManager.getInstance().login(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getTxim_sign(), new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d("TAGGonError", "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.d("TAGGonSuccess", "login succ");
            }
        });


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, MyUserInstance.getInstance().getUserinfo().getAvatar());
        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
    }
}


