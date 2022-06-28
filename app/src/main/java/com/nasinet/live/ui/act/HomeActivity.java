package com.nasinet.live.ui.act;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.lxj.xpopup.XPopup;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpActivity;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.interfaces.OnGetUnRead;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.fragment.HomeFragment;
import com.nasinet.live.ui.fragment.HomeShortVideoFragment;
import com.nasinet.live.ui.fragment.HomeTrendsFragment;
import com.nasinet.live.ui.fragment.MyFragment;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.widget.CommentHomeBottomList;
import com.nasinet.live.widget.Dialogs;
import com.nasinet.live.wxapi.WxApiWrapper;
import com.nasinet.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.home_tab_iv)
    ImageView home_tab_iv;
    @BindView(R.id.home_tab_tv)
    TextView home_tab_tv;
    @BindView(R.id.short_tab_iv)
    ImageView short_tab_iv;
    @BindView(R.id.short_tab_tv)
    TextView short_tab_tv;


    @BindView(R.id.trend_tab_iv)
    ImageView trend_tab_iv;
    @BindView(R.id.trend_tab_tv)
    TextView trend_tab_tv;
    @BindView(R.id.my_tab_iv)
    ImageView my_tab_iv;
    @BindView(R.id.my_tab_tv)
    TextView my_tab_tv;
    @BindView(R.id.tv_red_hot)
    TextView tv_red_hot;
    @BindView(R.id.iv_star)
    ImageView iv_star;



    private HashSet<DialogFragment> mDialogFragmentSet;
    HomeFragment homeFragment;
    HomeShortVideoFragment shortVideoFragment;
    HomeTrendsFragment homeTrendsFragment;
    MyFragment myFragment;

    private int now_page = 0;
    OnGetUnRead onGetUnRead;
    private List<Fragment> mFragments = new ArrayList<>();
    private static boolean mBackKeyPressed = false;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_home_2;
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
    @Override
    protected void initData() {
        if(MyUserInstance.getInstance().hasToken()){
            updateUserInfo();
        }
    }

    @Override
    protected void initView() {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mDialogFragmentSet = new HashSet<>();
        hideTitle(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setAndroidNativeLightStatusBar(false);
                initIMSDK();
            }
        }, 1000);


        homeFragment = new HomeFragment();
        shortVideoFragment = new HomeShortVideoFragment();
        homeTrendsFragment = new HomeTrendsFragment();
        myFragment = new MyFragment();

        mFragments.add(homeFragment);
        mFragments.add(shortVideoFragment);
        mFragments.add(homeTrendsFragment);
        mFragments.add(myFragment);

        addFragment(homeFragment);
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new V2TIMSDKConfig());
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());
        TUIKit.init(this, MyUserInstance.getInstance().getUserConfig().getConfig().getIm_sdkappid(), configs);
        WxApiWrapper.getInstance().setAppID( MyUserInstance.getInstance().getUserConfig().getConfig().getWx_appid());

    }





    @Override
    public void onError(Throwable throwable) {

    }


    public void updateUserInfo() {
        mPresenter.getUserInfo();
    }

    @Override
    public void setUserInfo(UserRegist bean) {
        MyUserInstance.getInstance().setUserInfo(bean);
        String temp = JSON.toJSONString(bean);
        Hawk.put("USER_REGIST", temp);
    }

    @OnClick({R.id.home_tab, R.id.short_tab, R.id.trend_tab, R.id.my_tab,R.id.iv_star})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tab:
                now_page = 1;
                selectedTab(1);
                addFragment(mFragments.get(0));
                if (null != shortVideoFragment) {
                    shortVideoFragment.action = false;
                    shortVideoFragment.paseAll();
                }

                if (null != homeTrendsFragment) {
                    if (homeTrendsFragment.action) {
                        homeTrendsFragment.action = false;
                        homeTrendsFragment.paseVideo();
                    }
                }
                VideoViewManager.instance().release();
                setAndroidNativeLightStatusBar(false);
                break;
            case R.id.short_tab:
                now_page = 2;
                selectedTab(2);
                addFragment(mFragments.get(1));
                if (null != shortVideoFragment) {
                    shortVideoFragment.action = true;
                    shortVideoFragment.onResume();
                }
                if (null != homeTrendsFragment) {
                    if (homeTrendsFragment.action) {
                        homeTrendsFragment.action = false;
                        homeTrendsFragment.paseVideo();
                    }
                }
                VideoViewManager.instance().release();
                setAndroidNativeLightStatusBar(false);
                break;
            case R.id.trend_tab:
                now_page = 3;
                selectedTab(3);
                addFragment(mFragments.get(2));
                if (null != shortVideoFragment) {
                    shortVideoFragment.action = false;
                    shortVideoFragment.paseAll();
                }
                if (null != homeTrendsFragment) {
                    homeTrendsFragment.action = true;

                }
                VideoViewManager.instance().release();
                setAndroidNativeLightStatusBar(false);
                break;


            case R.id.my_tab:
                now_page = 4;
                selectedTab(4);
                addFragment(mFragments.get(3));

                if (null != shortVideoFragment) {
                    shortVideoFragment.action = false;
                    shortVideoFragment.paseAll();
                }

                if (null != homeTrendsFragment) {
                    if (homeTrendsFragment.action) {
                        homeTrendsFragment.action = false;
                        homeTrendsFragment.paseVideo();
                    }
                }
                VideoViewManager.instance().release();
                setAndroidNativeLightStatusBar(true);
                break;

            case R.id.iv_star:

                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(new CommentHomeBottomList(context))
                        .show();
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void selectedTab(int tab) {
        home_tab_iv.setImageResource(R.mipmap.zhibo);
        home_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));

        short_tab_iv.setImageResource(R.mipmap.video);
        short_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));

        trend_tab_iv.setImageResource(R.mipmap.dongtai);
        trend_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));


        my_tab_iv.setImageResource(R.mipmap.wode);
        my_tab_tv.setTextColor(getResources().getColor(R.color.color_939393));
        switch (tab) {
            case 1:
                home_tab_iv.setImageResource(R.mipmap.zhibo_pre);
                home_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
            case 2:
                short_tab_iv.setImageResource(R.mipmap.video_pre);
                short_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
            case 3:
                trend_tab_iv.setImageResource(R.mipmap.dongtai_pre);
                trend_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;


            case 4:
                my_tab_iv.setImageResource(R.mipmap.wode_pre);
                my_tab_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null != shortVideoFragment) {
            if (now_page == 2) {
                shortVideoFragment.action = true;
            } else {
                shortVideoFragment.action = false;
            }
        }
      //  initIMSDK();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void addDialogFragment(DialogFragment dialogFragment) {
        if (mDialogFragmentSet != null && dialogFragment != null) {
            mDialogFragmentSet.add(dialogFragment);
        }
    }

    public void removeDialogFragment(DialogFragment dialogFragment) {
        if (mDialogFragmentSet != null && dialogFragment != null) {
            mDialogFragmentSet.remove(dialogFragment);
        }
    }


    private Dialog dialog;

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public void setOnGetUnRead(OnGetUnRead onGetUnRead) {
        this.onGetUnRead = onGetUnRead;
    }
    private void initIMSDK() {
        TIMManager.getInstance().getUserConfig()  //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("onRefresh", "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        long num=0;
                        if(getAllUnReadMsgCount().equals("99+")){
                             num = 99;
                        }else{
                             num = Long.parseLong(getAllUnReadMsgCount());
                        }


                        if (HomeActivity.this.isFinishing()) {
                            return;
                        }

                        if (tv_red_hot == null) {
                            return;
                        }
                        if (num != 0) {
                            tv_red_hot.setVisibility(View.VISIBLE);
                            tv_red_hot.setText(num + "");

                        } else {
                            tv_red_hot.setVisibility(View.GONE);
                        }
                        if (onGetUnRead != null) {
                            onGetUnRead.getUnRead(num + "");
                        }
                    }
                });
        //开启消息已读回执

    }


    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.showT("再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {//退出程序
            TIMManager.getInstance().logout(null);
            AppManager.getAppManager().finishAllActivity();

        }
    }

    public void toPageFragement(int position) {
        if (mFragments.size() <= position) {
            return;
        }
        now_page = position;
        addFragment(mFragments.get(position));
    }

    public void addFragment(Fragment tabFragment) {
        if (tabFragment == null) {
            return;
        }
        if (tabFragment.isAdded()) {
            showFragment(tabFragment);
        } else {
            hideElseFragment(tabFragment);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, tabFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void showFragment(Fragment fragment) {
        hideElseFragment(fragment);
        getSupportFragmentManager().beginTransaction()

                .show(fragment)
                .commitAllowingStateLoss();
    }


    public void hideElseFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment tabFragmen = mFragments.get(i);
            if (fragment == tabFragmen) {
                continue;
            }
            if (fragment != null && tabFragmen.isAdded()) {
                transaction.hide(tabFragmen);
            }
        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
