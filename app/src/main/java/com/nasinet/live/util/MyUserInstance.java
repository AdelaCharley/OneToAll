package com.nasinet.live.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nasinet.live.R;
import com.nasinet.live.base.MyApp;
import com.nasinet.live.bean.MessageData;
import com.nasinet.live.model.entity.Filters;
import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.Profile;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.model.entity.UserTag;
import com.nasinet.live.pay.ali.AliPayBuilder;
import com.nasinet.live.ui.act.PhoneLoginActivity;
import com.nasinet.live.wxapi.WxPayBuilder;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nasinet.nasinet.base.NasiSDK;
import com.nasinet.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.graphics.BitmapFactory.decodeResource;

public class MyUserInstance {
    private WxPayBuilder wxPayBuilder;
    private AliPayBuilder aliPayBuilder;
    public static MyUserInstance instance;
    public static UserConfig config;
    public static UserRegist userInfo;
    public static UserRegist userInfo_null = new UserRegist("0", "", "", "", "", "", "0", "0", "", new ArrayList<UserTag>(), "", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", new Profile());
    public AlertDialog.Builder builder;
    public int device_width = 0;
    public int device_height = 0;

    public int getDevice_width() {
        return device_width;
    }

    public void setDevice_width(int device_width) {
        this.device_width = device_width;
    }

    public int getDevice_height() {
        return device_height;
    }

    public void setDevice_height(int device_height) {
        this.device_height = device_height;
    }

    public static MyUserInstance getInstance() {
        synchronized (MyUserInstance.class) {
            if (null == instance) {
                instance = new MyUserInstance();
                return instance;
            } else {
                return instance;

            }
        }
    }


    public UserRegist getUserInfo_null() {
        return userInfo_null;
    }

    public void setUserInfo_null(UserRegist userInfo_null) {
        MyUserInstance.userInfo_null = userInfo_null;
    }

    public WxPayBuilder getWxPayBuilder(Context context) {
        if (wxPayBuilder == null) {
            wxPayBuilder = new WxPayBuilder(context, MyUserInstance.getInstance().getUserConfig().getConfig().getWx_appid());
        }
        return wxPayBuilder;
    }

    public AliPayBuilder getAliPayBuilder(Activity context) {
        if (aliPayBuilder == null) {
            aliPayBuilder = new AliPayBuilder(context);
        }
        return aliPayBuilder;
    }


    public void initDialog(Context context, final Class c) {
        builder = new AlertDialog.Builder(context).setTitle("当前账户尚未登录")
                .setMessage("请登录使用完整功能.").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        AppManager.getAppManager().startActivity(c);
                        AppManager.getAppManager().finishOthersActivity(c);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public int getVipLevel(String lv) {
        if (!NasiSDK.isIs_check()) {
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "0":
                temp = 0;
                break;
            case "1":
                temp = R.mipmap.youxia;

                break;
            case "2":
                temp = R.mipmap.qishi;

                break;
            case "3":
                temp = R.mipmap.gongjue;

                break;
            case "4":
                temp = R.mipmap.king;

                break;
        }


        return temp;
    }


    public int getLevel(String lv) {
        if (!NasiSDK.isIs_check()) {
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "1":
                temp = R.mipmap.lv1;

                break;
            case "2":
                temp = R.mipmap.lv2;

                break;
            case "3":
                temp = R.mipmap.lv3;

                break;
            case "4":
                temp = R.mipmap.lv4;

                break;
            case "5":
                temp = R.mipmap.lv5;

                break;
            case "6":
                temp = R.mipmap.lv6;

                break;
            case "7":
                temp = R.mipmap.lv7;

                break;
            case "8":
                temp = R.mipmap.lv8;

                break;
            case "9":
                temp = R.mipmap.lv9;

                break;
            case "10":
                temp = R.mipmap.lv10;

                break;
            case "11":
                temp = R.mipmap.lv11;

                break;
            case "12":
                temp = R.mipmap.lv12;

                break;
            case "13":
                temp = R.mipmap.lv13;

                break;
            case "14":
                temp = R.mipmap.lv14;

                break;
            case "15":
                temp = R.mipmap.lv15;

                break;
            case "16":
                temp = R.mipmap.lv16;

                break;
            case "17":
                temp = R.mipmap.lv17;

                break;
            case "18":
                temp = R.mipmap.lv18;

                break;
            case "19":
                temp = R.mipmap.lv19;

                break;
            case "20":
                temp = R.mipmap.lv20;

                break;
            case "21":
                temp = R.mipmap.lv21;

                break;
            case "22":
                temp = R.mipmap.lv22;

                break;
            case "23":
                temp = R.mipmap.lv23;

                break;
            case "24":
                temp = R.mipmap.lv24;

                break;
            case "25":
                temp = R.mipmap.lv25;

                break;
            case "26":
                temp = R.mipmap.lv26;

                break;
            case "27":
                temp = R.mipmap.lv27;

                break;
            case "28":
                temp = R.mipmap.lv28;

                break;
            case "29":
                temp = R.mipmap.lv29;

                break;
            case "30":
                temp = R.mipmap.lv30;

                break;


        }

        return temp;

    }

    public int getAnchorLevel(String lv) {
        if (!NasiSDK.isIs_check()) {
            NasiSDK.getInstance().finish();
        }
        int temp = 0;
        switch (lv) {
            case "1":
                temp = R.mipmap.anchor_1;

                break;
            case "2":
                temp = R.mipmap.anchor_2;

                break;
            case "3":
                temp = R.mipmap.anchor_3;

                break;

            case "4":
                temp = R.mipmap.anchor_4;

                break;
            case "5":
                temp = R.mipmap.anchor_5;

                break;
            case "6":
                temp = R.mipmap.anchor_6;

                break;
            case "7":
                temp = R.mipmap.anchor_7;

                break;
            case "8":
                temp = R.mipmap.anchor_8;

                break;
            case "9":
                temp = R.mipmap.anchor_9;

                break;
            case "10":
                temp = R.mipmap.anchor_10;

                break;
            case "11":
                temp = R.mipmap.anchor_11;

                break;
            case "12":
                temp = R.mipmap.anchor_12;

                break;
            case "13":
                temp = R.mipmap.anchor_13;

                break;
            case "14":
                temp = R.mipmap.anchor_14;

                break;
            case "15":
                temp = R.mipmap.anchor_15;

                break;
            case "16":
                temp = R.mipmap.anchor_16;

                break;
            case "17":
                temp = R.mipmap.anchor_17;

                break;
            case "18":
                temp = R.mipmap.anchor_18;

                break;
            case "19":
                temp = R.mipmap.anchor_19;

                break;
            case "20":
                temp = R.mipmap.anchor_20;
                break;
            case "21":
                temp = R.mipmap.anchor_21;
                break;
            case "22":
                temp = R.mipmap.anchor_22;
                break;
            case "23":
                temp = R.mipmap.anchor_23;
                break;
            case "24":
                temp = R.mipmap.anchor_24;
                break;
            case "25":
                temp = R.mipmap.anchor_25;
                break;
            case "26":
                temp = R.mipmap.anchor_26;
                break;
            case "27":
                temp = R.mipmap.anchor_27;
                break;
            case "28":
                temp = R.mipmap.anchor_28;
                break;
            case "29":
                temp = R.mipmap.anchor_29;
                break;
            case "30":
                temp = R.mipmap.anchor_30;
                break;


        }

        return temp;

    }


    public void setUserConfig(UserConfig config) {
        this.config = config;
    }

    public UserConfig getUserConfig() {
        if (!NasiSDK.isIs_check()) {
            NasiSDK.getInstance().finish();
        }
        return config;
    }

    public void setUserInfo(UserRegist info) {
        this.userInfo = info;
    }

    public UserRegist getUserinfo() {
        if (!NasiSDK.isIs_check()) {
            NasiSDK.getInstance().finish();
        }
        if (null != userInfo) {
            return userInfo;
        } else {
            return userInfo_null;
        }

    }


    public boolean isGuardianFirst(String anchor_id) {
        boolean is_guardian = false;
        if (null != userInfo) {
            if (userInfo.getGuard_anchors() != null) {
                if (userInfo.getGuard_anchors().size() != 0) {
                    for (int i = 0; i < userInfo.getGuard_anchors().size(); i++) {
                        if (userInfo.getGuard_anchors().get(i).equals(anchor_id)) {
                            is_guardian = true;
                            break;
                        }
                    }
                }
            }
        }
        return is_guardian;
    }

    public boolean isGuardian(GuardianInfo guardianInfo) {
        if (guardianInfo == null) {
            return false;
        }

        if (guardianInfo.getExpire_time() != null) {
            return OverTime(guardianInfo.getExpire_time());
        } else {
            return false;
        }


    }

    public boolean OverTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(time);
            long into_time = mDate.getTime();
            long now_time = System.currentTimeMillis();
            if (into_time > now_time) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public long computerTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(time);
            long into_time = mDate.getTime();
            long now_time = System.currentTimeMillis();
            long now_timenow_time=now_time - into_time;
            return now_time - into_time;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public void startChatActivity(Context context, com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo conversationInfo, Class c) {
        ChatInfo chatInfo = new ChatInfo();

        chatInfo.setType(1);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(context, c);
        intent.putExtra("chatInfo", chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startChatActivity(Context context, String id, String name, Class c) {
        ChatInfo chatInfo = new ChatInfo();

        chatInfo.setType(1);
        chatInfo.setId(id);
        chatInfo.setChatName(name);
        Intent intent = new Intent(context, c);
        intent.putExtra("chatInfo", chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public ArrayList<String> Starts() {
        ArrayList startlist = new ArrayList();
        startlist.add("白羊座");
        startlist.add("金牛座");
        startlist.add("双子座");
        startlist.add("巨蟹座");
        startlist.add("狮子座");
        startlist.add("处女座");
        startlist.add("天秤座");
        startlist.add("天蝎座");
        startlist.add("射手座");
        startlist.add("摩羯座");
        startlist.add("水瓶座");
        startlist.add("双鱼座");
        return startlist;
    }

    public ArrayList<String> Sex() {
        ArrayList sexlist = new ArrayList();
        sexlist.add("男");
        sexlist.add("女");
        return sexlist;
    }

    public boolean loginMode() {
        //如果真表示游客模式
        if (((MyApp) MyApp.getInstance()).isLogin_mode()) {
            return true;
        } else {
            //如果假表示正常模式,需要登录
            return false;
        }
    }

    public boolean visitorIsLogin() {
        if (getUserinfo().getToken() == null || getUserinfo().getToken().equals("")) {
            //没有TOKEN表示没有登录跳转登录页面
            AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
            return false;
        } else {
            return true;
        }
    }

    public boolean visitorIsLogin(Activity activity) {
        if (getUserinfo().getToken() == null || getUserinfo().getToken().equals("")) {
            //没有TOKEN表示没有登录跳转登录页面
            //  AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
            activity.startActivityForResult(new Intent(activity, PhoneLoginActivity.class), 1111);
            return false;
        } else {
            return true;
        }
    }

    public boolean hasToken() {

        if (getUserinfo().getToken() == null || getUserinfo().getToken().equals("")) {
            return false;
        } else {
            return true;
        }

    }


    public String paste() {
        ClipboardManager manager = (ClipboardManager) MyApp.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }

    public void initVistor() {
        UserRegist userInfo_null = Hawk.get("USER_TEMP");
        if (userInfo_null == null) {
            int min = 10;
            int max = 99;
            Random random = new Random();
            int num = random.nextInt(max) % (max - min + 1) + min;
            String id = getTime() + num;
            HttpUtils.getInstance().getTempUserKey(id, new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    JSONObject jsonObject = JSON.parseObject(response.body());
                    if (jsonObject.getString("status").equals("0")) {
                        jsonObject.getJSONObject("data").getString("txim_sign");
                        MyUserInstance.getInstance().getUserInfo_null().setTxim_sign(jsonObject.getJSONObject("data").getString("txim_sign"));
                        MyUserInstance.getInstance().getUserInfo_null().setId(id);
                        MyUserInstance.getInstance().getUserInfo_null().setNick_name("游客A" + id);
                        Hawk.put("USER_TEMP", MyUserInstance.getInstance().getUserInfo_null());
                    }
                }
            });
        } else {
            MyUserInstance.getInstance().setUserInfo_null(userInfo_null);
        }
    }

    private String getTime() {

        Date date = new Date();

        String time = date.toLocaleString();

        Log.i("md", "时间time为： " + time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");

        String sim = dateFormat.format(date);
        return sim;
    }



    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour+ ":" + minute + ":" + second;
    }

    public ArrayList<Filters> initFilterList(Context context) {
        ArrayList<Filters> filters = new ArrayList<>();

        Filters filter = new Filters();
        filter.setTag(1);
        filter.setFilterBitmap(null);
        filter.setFilterSrc(R.mipmap.yuantu);
        filter.setUnFilterSrc(R.mipmap.yuantu_pre);
        filter.setSpecialRatio(0f);
        filters.add(filter);

        Filters filter1 = new Filters();
        filter1.setTag(2);
        Bitmap bitmap1 = decodeResource(context.getResources(), R.mipmap.filter_bailan);
        filter1.setFilterBitmap(bitmap1);
        filter1.setFilterSrc(R.mipmap.bailan);
        filter1.setUnFilterSrc(R.mipmap.bailan_pre);
        filters.add(filter1);

        Filters filter2 = new Filters();
        filter2.setTag(2);
        Bitmap bitmap2 = decodeResource(context.getResources(), R.mipmap.filter_biaozhun);
        filter2.setFilterBitmap(bitmap2);
        filter2.setFilterSrc(R.mipmap.biaozhun);
        filter2.setUnFilterSrc(R.mipmap.biaozhun_pre);
        filters.add(filter2);

        Filters filter3 = new Filters();
        filter3.setTag(2);
        Bitmap bitmap3 = decodeResource(context.getResources(), R.mipmap.filter_chaotuo);
        filter3.setFilterBitmap(bitmap3);
        filter3.setFilterSrc(R.mipmap.chaotuo);
        filter3.setUnFilterSrc(R.mipmap.chaotuo_pre);
        filters.add(filter3);

        Filters filter4 = new Filters();
        filter4.setTag(2);
        Bitmap bitmap4 = decodeResource(context.getResources(), R.mipmap.filter_chunzhen);
        filter4.setFilterBitmap(bitmap4);
        filter4.setFilterSrc(R.mipmap.chunzhen);
        filter4.setUnFilterSrc(R.mipmap.chunzhen_pre);
        filters.add(filter4);

        Filters filter5 = new Filters();
        filter5.setTag(2);
        Bitmap bitmap5 = decodeResource(context.getResources(), R.mipmap.filter_fennen);
        filter5.setFilterBitmap(bitmap5);
        filter5.setFilterSrc(R.mipmap.fennen);
        filter5.setUnFilterSrc(R.mipmap.fennen_pre);
        filters.add(filter5);

        Filters filter6 = new Filters();
        filter6.setTag(2);
        Bitmap bitmap6 = decodeResource(context.getResources(), R.mipmap.filter_huaijiu);
        filter6.setFilterBitmap(bitmap6);
        filter6.setFilterSrc(R.mipmap.huaijiu);
        filter6.setUnFilterSrc(R.mipmap.huaijiu_pre);
        filters.add(filter6);

        Filters filter7 = new Filters();
        filter7.setTag(2);
        Bitmap bitmap7 = decodeResource(context.getResources(), R.mipmap.filter_landiao);
        filter7.setFilterBitmap(bitmap7);
        filter7.setFilterSrc(R.mipmap.landiao);
        filter7.setUnFilterSrc(R.mipmap.landiao_pre);
        filters.add(filter7);

        Filters filter8 = new Filters();
        filter8.setTag(2);
        Bitmap bitmap8 = decodeResource(context.getResources(), R.mipmap.filter_langman);
        filter8.setFilterBitmap(bitmap8);
        filter8.setFilterSrc(R.mipmap.langman);
        filter8.setUnFilterSrc(R.mipmap.langman_pre);
        filters.add(filter8);

        Filters filter9 = new Filters();
        filter9.setTag(2);
        Bitmap bitmap9 = decodeResource(context.getResources(), R.mipmap.filter_white);
        filter9.setFilterBitmap(bitmap9);
        filter9.setFilterSrc(R.mipmap.meibai);
        filter9.setUnFilterSrc(R.mipmap.meibai_pre);
        filters.add(filter9);

        Filters filter10 = new Filters();
        filter10.setTag(2);
        Bitmap bitmap10 = decodeResource(context.getResources(), R.mipmap.filter_qingliang);
        filter10.setFilterBitmap(bitmap10);
        filter10.setFilterSrc(R.mipmap.qingliang);
        filter10.setUnFilterSrc(R.mipmap.qingliang_pre);
        filters.add(filter10);

        Filters filter11 = new Filters();
        filter11.setTag(2);
        Bitmap bitmap11 = decodeResource(context.getResources(), R.mipmap.filter_qingxin);
        filter11.setFilterBitmap(bitmap11);
        filter11.setFilterSrc(R.mipmap.qingxin);
        filter11.setUnFilterSrc(R.mipmap.qingxin_pre);
        filters.add(filter11);

        Filters filter12 = new Filters();
        filter12.setTag(2);
        Bitmap bitmap12 = decodeResource(context.getResources(), R.mipmap.filter_rixi);
        filter12.setFilterBitmap(bitmap12);
        filter12.setFilterSrc(R.mipmap.rixi);
        filter12.setUnFilterSrc(R.mipmap.rixi_pre);
        filters.add(filter12);

        Filters filter13 = new Filters();
        filter13.setTag(2);
        Bitmap bitmap13 = decodeResource(context.getResources(), R.mipmap.filter_weimei);
        filter13.setFilterBitmap(bitmap13);
        filter13.setFilterSrc(R.mipmap.weimei);
        filter13.setUnFilterSrc(R.mipmap.weimei_pre);
        filters.add(filter13);

        Filters filter14 = new Filters();
        filter14.setTag(2);
        Bitmap bitmap14 = decodeResource(context.getResources(), R.mipmap.filter_xiangfen);
        filter14.setFilterBitmap(bitmap14);
        filter14.setFilterSrc(R.mipmap.xiangfen);
        filter14.setUnFilterSrc(R.mipmap.xiangfen_pre);
        filters.add(filter14);

        Filters filter15 = new Filters();
        filter15.setTag(2);
        Bitmap bitmap15 = decodeResource(context.getResources(), R.mipmap.filter_yinghong);
        filter15.setFilterBitmap(bitmap15);
        filter15.setFilterSrc(R.mipmap.yinghong);
        filter15.setUnFilterSrc(R.mipmap.yinghong_p);
        filters.add(filter15);

        Filters filter16 = new Filters();
        filter16.setTag(2);
        Bitmap bitmap16 = decodeResource(context.getResources(), R.mipmap.filter_yuanqi);
        filter16.setFilterBitmap(bitmap16);
        filter16.setFilterSrc(R.mipmap.yuanqi);
        filter16.setUnFilterSrc(R.mipmap.yuanqi_pre);
        filters.add(filter16);

        Filters filter17 = new Filters();
        filter17.setTag(1);
        Bitmap bitmap17 = decodeResource(context.getResources(), R.mipmap.filter_yunshang);
        filter17.setFilterBitmap(bitmap17);
        filter17.setFilterSrc(R.mipmap.yunshang);
        filter17.setUnFilterSrc(R.mipmap.yunshang_pre);
        filters.add(filter17);

        return filters;
    }


    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public boolean isVip() {
        if (!TextUtils.isEmpty(getUserinfo().getVip_date())) {
            if (System.currentTimeMillis() < DateUtil.date2TimeStamp(MyUserInstance.getInstance().getUserinfo().getVip_date())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isAnchor() {
        if (hasToken()) {
            if (null != userInfo) {
                if (userInfo.getIs_anchor() == null || userInfo.getIs_anchor().equals("0")) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

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

    public boolean isVip(UserRegist sender) {

        if (sender.getVip_date() == null | Integer.parseInt(sender.getVip_level()) <= 0) {
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
}
