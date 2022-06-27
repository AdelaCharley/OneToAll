package com.nasinet.live.base;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.nasinet.live.bean.Message;
import com.gyf.immersionbar.ImmersionBar;
import com.nasinet.live.R;
import com.nasinet.live.bean.MessageData;
import com.nasinet.live.model.entity.ChatReceiveGiftBean;
import com.nasinet.live.presenter.BasePresenter;
import com.nasinet.live.ui.act.LivePushActivity;
import com.nasinet.live.ui.act.SplashActivity;
import com.nasinet.live.util.DateUtil;
import com.nasinet.live.util.HttpUtils;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.widget.Dialogs;
import com.nasinet.nasinet.base.NasiBaseActivity;
import com.nasinet.nasinet.userconfig.AppStatus;
import com.nasinet.nasinet.userconfig.AppStatusManager;
import com.nasinet.nasinet.utils.AppManager;
import com.nasinet.nasinet.utils.ScreenUtil;
import com.orhanobut.hawk.Hawk;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.liteav.demo.play.bean.GiftData;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;


import java.util.List;
import java.util.Locale;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends NasiBaseActivity implements BaseView {

    public T mPresenter;
    public long unReadMessage = 0;

    public boolean is_show_vip = false;

    TIMMessageListener timMessageListener = new TIMMessageListener() {
        @Override
        public boolean onNewMessages(List<TIMMessage> list) {
            for (TIMMessage msg : list) {
                for (int i = 0; i < msg.getElementCount(); i++) {
                    if (msg.getElement(i).getType() == TIMElemType.Custom) {
                        TIMCustomElem elem = (TIMCustomElem) msg.getElement(i);
                        String json = new String(elem.getData());
                        JSONObject jsonObject = JSON.parseObject(json);
                        if (jsonObject == null) {
                            return false;
                        }
                        if (jsonObject.getString("Action") == null) {
                            return false;
                        }
                        if (jsonObject.getString("Action").equals("BroadcastStreamer")) {
                            getMoveViewGroup(jsonObject);
                        }
                    }
                }

            }
            return false;
        }
    };

    VipInFinishListener vipInFinishListener;

    public void setVipInFinishListener(VipInFinishListener vipInFinishListener) {
        this.vipInFinishListener = vipInFinishListener;
    }

    public interface VipInFinishListener {
        void vipinfinish();
    }

    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画
        removeButMemberInfo();
        overridePendingTransition(R.anim.activity_anim_in_right, R.anim.activity_anim_out_left);
        HttpUtils.getInstance().cleanAll();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断app状态
        if (!getRunningActivityName().contains("Splash")) {
            if (AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE) {
                //被回收，跳转到启动页面
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }


        getAdminListner();
        AppManager.getAppManager().addActivity(this);

        ImmersionBar.with(this).statusBarDarkFont(true).init();

        Log.e("thisthisthis", this.toString());

    }


    public void removeButMemberInfo() {
        TIMManager.getInstance().removeMessageListener(timMessageListener);
    }


    private void getAdminListner() {
        TIMManager.getInstance().addMessageListener(timMessageListener);


    }


    private Dialog dialog;

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }


    public String getAllUnReadMsgCount() {


        List<TIMConversation> list = TIMManagerExt.getInstance().getConversationList();
        int unReadCount = 0;
        if (list != null && list.size() > 0) {
            for (TIMConversation conversation : list) {
                if (conversation == null) {
                    continue;
                }


                if (conversation.getType() != TIMConversationType.C2C || conversation.getPeer().equals(MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_broadcast())) {

                    continue;
                }
                String peer = conversation.getPeer();
                if (TextUtils.isEmpty(peer)) {
                    continue;
                }
                unReadCount += conversation.getUnreadMessageNum();

            }
        }
        Log.e("TAG", "未读消息总数----->" + unReadCount);
        String res = "";
        if (unReadCount > 99) {
            res = "99+";
        } else {
            if (unReadCount < 0) {
                unReadCount = 0;
            }
            res = String.valueOf(unReadCount);
        }
        return res;

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        removeButMemberInfo();
        super.onDestroy();
        // AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang = Hawk.get("Language", "0");
        switch (lang) {
            case "1":
                newBase = updateResources(newBase, "zh");
                break;
            case "2":
                newBase = updateResources(newBase, "en");
                break;
        }


        super.attachBaseContext(newBase);
    }


    private Context updateResources(Context context, String language) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Locale locale = new Locale(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList defaultList = LocaleList.forLanguageTags(language);
            LocaleList.setDefault(defaultList);
            conf.setLocales(defaultList);
        } else {
            conf.locale = locale;
        }
        conf.locale = locale;
        conf.setLocale(locale);
        context = context.createConfigurationContext(conf);
        res.updateConfiguration(conf, dm);
        return context;
    }


    protected void getVipIn(Message res, int type) {

        if (this.isFinishing()) {
            return;
        }
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        String[] temp = component.getClassName().split("\\.");

        if (this.toString().contains(temp[temp.length - 1])) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ScreenUtil.dp2px(this, 250), ScreenUtil.dp2px(this, 33));
            switch (type) {
                case 1:
                    lp.setMargins(ScreenUtil.dp2px(this, 10), 600, 0, 0);
                    break;
                case 2:
                    lp.setMargins(ScreenUtil.dp2px(this, 10), 300, 0, 0);
                    break;
                case 3:

                    if(live_chat_h==0){
                        int d_h = MyUserInstance.getInstance().getDevice_height() / 2 + 150;
                        lp.setMargins(ScreenUtil.dp2px(this, 10), d_h, 0, 0);
                    }else{
                        lp.setMargins(ScreenUtil.dp2px(this, 10), live_chat_h-100, 0, 0);
                    }

                    break;
            }


            final View vip_in = LayoutInflater.from(this).inflate(R.layout.item_vip_in, null);

            MessageData.MessageChat.Sender sender = res.getData().getChat().getSender();
            MessageData.MessageChat messageChat = res.getData().getChat();
            if (isVip(sender) & messageChat.getIs_guardian() == 0) {
                //1,vip
                vip_in.findViewById(R.id.iv_guardian).setVisibility(View.GONE);
                switch (res.getData().getChat().getSender().getVip_level()) {
                    case 1:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_anchor));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setText("游侠");
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setTextColor(Color.parseColor("#8BFFB4"));
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 2:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_knight));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setText("骑士");
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setTextColor(Color.parseColor("#B6BCF7"));
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 3:

                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_duke));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setText("公爵");
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setTextColor(Color.parseColor("#5EF6FF"));
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 4:
                        lp.width=ScreenUtil.dp2px(this, 250);
                        lp.height=ScreenUtil.dp2px(this, 44);
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_king));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setText("国王");
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setTextColor(Color.parseColor("#FFEE30"));
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                }
            } else if (!isVip(sender) & messageChat.getIs_guardian() == 1) {
                //2.当前主播守护
                vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_protect));
                vip_in.findViewById(R.id.tv_vip_name).setVisibility(View.GONE);
                ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setTextColor(Color.parseColor("#8BFFB4"));
                ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                vip_in.findViewById(R.id.iv_vip_level).setVisibility(View.GONE);
                LinearLayout.LayoutParams lp_guardian = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp_guardian.gravity = Gravity.CENTER_VERTICAL;
                lp_guardian.setMargins(ScreenUtil.dp2px(this, 10), 0, 0, 0);
                vip_in.findViewById(R.id.iv_guardian).setLayoutParams(lp_guardian);
            } else if (isVip(sender) & messageChat.getIs_guardian() == 1) {
                //3,又是VIP又是守护
                switch (res.getData().getChat().getSender().getVip_level()) {
                    case 1:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_anchor));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setVisibility(View.GONE);

                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 2:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_knight));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setVisibility(View.GONE);

                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 3:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_duke));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setVisibility(View.GONE);
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                    case 4:
                        vip_in.findViewById(R.id.rl_root).setBackground(getResources().getDrawable(R.mipmap.bg_king));
                        ((TextView) vip_in.findViewById(R.id.tv_vip_name)).setVisibility(View.GONE);
                        ((TextView) vip_in.findViewById(R.id.tv_user_name)).setText(res.getData().getChat().getSender().getNick_name());
                        break;
                }
            }


            addContentView(vip_in, lp);

            Animation myAnim1 = AnimationUtils.loadAnimation(this, R.anim.vip_anim);


            myAnim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    is_show_vip = false;
                    if (!BaseMvpActivity.this.isFinishing()) {
                        vip_in.setVisibility(View.GONE);
                        if (vipInFinishListener != null) {
                            vipInFinishListener.vipinfinish();
                        }
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            vip_in.startAnimation(myAnim1);
            is_show_vip = true;
        }
    }


    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    @SuppressLint("LongLogTag")
    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        Log.e("isFastClick_currentClickTime", currentClickTime + "");
        Log.e("isFastClick_lastClickTime", lastClickTime + "");
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        Log.e("isFastClick_flag", flag + "");
        return flag;
    }

    public boolean isVip(MessageData.MessageChat.Sender sender) {

        if (sender.getVip_date() == null | sender.getVip_level() <= 0) {
            return false;
        }
        if (sender.getVip_date().equals("")) {
            return false;
        }

        if (System.currentTimeMillis() < DateUtil.date2TimeStamp(sender.getVip_date())) {
            return true;
        }
        return false;
    }


    public  void setAndroidNativeLightStatusBar( boolean dark) {
        //true黑false白
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    public void showXPop(BasePopupView basePopupView, boolean isAuto, boolean hasShadow,Context context) {
        new XPopup.Builder(context)
                .autoDismiss(isAuto)
                .hasShadowBg(hasShadow)

                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(basePopupView)
                .show();
    }
    public ChatReceiveGiftBean initGift(GiftData giftData) {
        ChatReceiveGiftBean chatReceiveGiftBean = new ChatReceiveGiftBean();
        chatReceiveGiftBean.setUid(giftData.getSender().getId());
        chatReceiveGiftBean.setAvatar(giftData.getSender().getAvatar());
        chatReceiveGiftBean.setUserNiceName(giftData.getSender().getNick_name());
        chatReceiveGiftBean.setLevel(Integer.parseInt(giftData.getSender().getUser_level()));
        chatReceiveGiftBean.setGiftId(giftData.getId() + "");
        chatReceiveGiftBean.setGiftCount(Integer.parseInt(giftData.getCount()));
        chatReceiveGiftBean.setGiftName(giftData.getTitle());
        chatReceiveGiftBean.setGiftIcon(giftData.getIcon());
        chatReceiveGiftBean.setUse_type(giftData.getUse_type());
        if (giftData.getAnimat_type().equals("0")) {
            chatReceiveGiftBean.setGif(0);
        } else {
            chatReceiveGiftBean.setGif(1);
            chatReceiveGiftBean.setGifUrl(giftData.getAnimation());
            if (giftData.getAnimat_type().equals("1")) {
                chatReceiveGiftBean.setGitType(0);
            }
            if (giftData.getAnimat_type().equals("2")) {
                chatReceiveGiftBean.setGitType(1);
            }
        }


        return chatReceiveGiftBean;
    }

    // -------------------------------------隐藏输入法-----------------------------------------------------
// 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
// TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
