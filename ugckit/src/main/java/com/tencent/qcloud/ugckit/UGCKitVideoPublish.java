package com.tencent.qcloud.ugckit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.utils.HttpUtils;
import com.tencent.qcloud.ugckit.module.upload.TCUserMgr;
import com.tencent.qcloud.ugckit.module.upload.TCVideoPublishKit;
import com.tencent.qcloud.ugckit.module.upload.TXUGCPublish;
import com.tencent.qcloud.ugckit.module.upload.TXUGCPublishTypeDef;
import com.tencent.qcloud.ugckit.utils.BackgroundTasks;
import com.tencent.qcloud.ugckit.utils.LogReport;
import com.tencent.qcloud.ugckit.utils.NetworkUtil;
import com.tencent.qcloud.ugckit.utils.ToastUtil;
import com.tencent.qcloud.ugckit.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UGCKitVideoPublish extends RelativeLayout implements TCVideoPublishKit, View.OnClickListener, TXUGCPublishTypeDef.ITXVideoPublishListener {
    @NonNull
    private String TAG = "UGCKitVideoPublish";

    private Context mContext;
    // 返回
    private ImageView mBtnBack;
    private ImageView mImageViewBg;
    // 发布视频进度条
    private ProgressBar mProgressBar;
    // 发布视频进度文字
    private TextView mTvProgress;

    @Nullable
    private TXUGCPublish mVideoPublish = null;
    private boolean mIsFetchCosSig = false;
    @Nullable
    private String mCosSignature = null;
    @NonNull
    private Handler mHandler = new Handler();

    private boolean mAllDone = false;
    private boolean mDisableCache;
    private String mLocalVideoPath;
    private String uid;
    private String token;

    private TCVideoPublishKit.OnPublishListener mOnPublishListener;
    /**
     * 视频路径
     */
    @Nullable
    private String mVideoPath = null;
    /**
     * 视频封面路径
     */
    @Nullable
    private String mCoverPath = null;

    public UGCKitVideoPublish(Context context) {
        super(context);
        mContext=context;
    }

    public UGCKitVideoPublish(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public UGCKitVideoPublish(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    /**
     * 功能：</p>
     * 1、UGCKit控件初始化</p>
     * 2、加载视频封面</p>
     * 3、发布视频</p>
     *
     *
     */
    private void init() {


        inflate(getContext(), R.layout.publish_video_layout, this);

        mBtnBack = (ImageView) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
        mImageViewBg = (ImageView) findViewById(R.id.bg_iv);

        publishVideo();
    }

    /**
     * 检测有网络时获取签名
     */
    private void publishVideo() {
        if (mAllDone) {
//            Intent intent = new Intent(TCVideoPublisherActivity.this, TCMainActivity.class);
//            startActivity(intent);
        } else {
            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                ToastUtil.toastShortMessage(getResources().getString(R.string.tc_video_publisher_activity_no_network_connection));
                return;
            }
            fetchSignature();
        }
    }

    /**
     * Step1:获取签名
     */
    private void fetchSignature() {
        if (mIsFetchCosSig) {
            return;
        }
        mIsFetchCosSig = true;

        TCUserMgr.getInstance().getVodSig2(uid, token, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                com.alibaba.fastjson.JSONObject data  = JSON.parseObject(response.body());


                if (data.get("status").toString().equals("0")) {
                    mCosSignature=data.getJSONObject("data").getString("signature");
                    startPublish();
                    LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, TCUserMgr.SUCCESS_CODE, "获取签名成功");
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, -1, "获取签名失败");
            }
        });

 /*       TCUserMgr.getInstance().getVodSig2(uid,token,new TCUserMgr.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject data) {
                try {
                    mCosSignature = data.getString("signature");
                    startPublish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                *//**
                 * ELK数据上报：获取签名成功
                 *//*
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, TCUserMgr.SUCCESS_CODE, "获取签名成功");
            }

            @Override
            public void onFailure(int code, final String msg) {
                *//**
                 * ELK数据上报：获取签名失败
                 *//*
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, code, "获取签名失败");
            }
        });*/
    }

    /**
     * Step2:开始发布视频（子线程）
     */
    private void startPublish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mVideoPublish == null) {
                    mVideoPublish = new TXUGCPublish(UGCKitImpl.getAppContext(), TCUserMgr.getInstance().getUserId());
                }
                /**
                 * 设置视频发布监听器
                 */
                mVideoPublish.setListener(UGCKitVideoPublish.this);

                TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();
                param.signature = mCosSignature;
                param.videoPath = mVideoPath;
                param.coverPath = mCoverPath;
                int publishCode = mVideoPublish.publishVideo(param);
//                if (publishCode != 0) {
//                    mTVPublish.setText("发布失败，错误码：" + publishCode);
//                }
                NetworkUtil.getInstance(UGCKitImpl.getAppContext()).setNetchangeListener(new NetworkUtil.NetchangeListener() {
                    @Override
                    public void onNetworkAvailable() {
                        mTvProgress.setText(getResources().getString(R.string.tc_video_publisher_activity_network_connection_is_disconnected_video_upload_failed));
                    }
                });
                NetworkUtil.getInstance(UGCKitImpl.getAppContext()).registerNetChangeReceiver();
            }
        });
    }

    /************************************************************************/
    /*****                     UGCKit外部接口调用                         *****/
    /************************************************************************/
    @Override
    public void setPublishPath(String videoPath, String coverPath) {
        mVideoPath = videoPath;
        mCoverPath = coverPath;

        loadCoverImage();
    }

    @Override
    public void setCacheEnable(boolean disableCache) {
        mDisableCache = disableCache;
    }

    @Override
    public void setOnPublishListener(TCVideoPublishKit.OnPublishListener onUIClickListener) {
        mOnPublishListener = onUIClickListener;
    }

    @Override
    public void onClick(@NonNull View view) {
        int i = view.getId();
        if (i == R.id.btn_back) {
            showCancelPublishDialog();
        }
    }


    public void setUserInfo(String uid,String token){
        this.uid=uid;
        this.token=token;
        init();
    }
    /**
     * 加载视频封面
     */
    private void loadCoverImage() {
        if (mCoverPath != null) {
            Glide.with(mContext).load(Uri.fromFile(new File(mCoverPath))).into(mImageViewBg);
        }
    }

    /**
     * 显示取消发布的Dialog
     */
    private void showCancelPublishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog alertDialog = builder.setTitle(mContext.getString(R.string.cancel_publish_title)).setCancelable(false).setMessage(R.string.cancel_publish_msg)
                .setPositiveButton(R.string.cancel_publish_title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        if (mVideoPublish != null) {
                            mVideoPublish.canclePublish();
                        }
                        dialog.dismiss();
                        if (mOnPublishListener != null) {
                            mOnPublishListener.onPublishCanceled();
                        }
                    }
                })
                .setNegativeButton(mContext.getString(R.string.wrong_click), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 视频发布进度
     *
     * @param uploadBytes
     * @param totalBytes
     */
    @Override
    public void onPublishProgress(long uploadBytes, long totalBytes) {
        int progress = (int) (uploadBytes * 100 / totalBytes);
        Log.d(TAG, "onPublishProgress:" + progress);
        mProgressBar.setProgress(progress);
        mTvProgress.setText(getResources().getString(R.string.tc_video_publisher_activity_is_uploading) + progress + "%");
    }

    /**
     * 视频发布结果回调<p/>
     * 当视频发布成功后，发布到点播系统，此时就可以在视频列表看到"已发布的视频"
     *
     * @param publishResult
     */
    @Override
    public void onPublishComplete(@NonNull TXUGCPublishTypeDef.TXPublishResult publishResult) {
        Log.d(TAG, "onPublishComplete:" + publishResult.retCode);

        /**
         * ELK数据上报：视频发布到点播系统
         */
        LogReport.getInstance().reportPublishVideo(publishResult);

        if (publishResult.retCode == TXUGCPublishTypeDef.PUBLISH_RESULT_OK) {
            mBtnBack.setVisibility(View.GONE);
            UploadUGCVideo(publishResult.videoId, publishResult.videoURL, publishResult.coverURL);
        } else {
            if (publishResult.descMsg.contains("java.net.UnknownHostException") || publishResult.descMsg.contains("java.net.ConnectException")) {
                mTvProgress.setText(mContext.getResources().getString(R.string.tc_video_publisher_activity_network_connection_is_disconnected_video_upload_failed));
            } else {
                mTvProgress.setText(publishResult.descMsg);
            }
            Log.e(TAG, publishResult.descMsg);
        }
    }

    /**
     * 发布视频后删除本地缓存的视频和封面
     */
    private void deleteCache() {
        if (mDisableCache) {
            File file = new File(mVideoPath);
            if (file.exists()) {
                file.delete();
            }
            if (!TextUtils.isEmpty(mCoverPath)) {
                file = new File(mCoverPath);
                if (file.exists()) {
                    file.delete();
                }
            }
            if (mLocalVideoPath != null) {
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(new File(mLocalVideoPath)));
                mContext.sendBroadcast(scanIntent);
            }
        }
    }

    /**
     * 发布到服务器
     *
     * @param videoId
     * @param videoURL
     * @param coverURL
     */
    private void UploadUGCVideo(final String videoId, final String videoURL, final String coverURL) {
        String title = null; //TODO:传入本地视频文件名称
        if (TextUtils.isEmpty(title)) {
            title = "小视频";
        }
        try {
            JSONObject body = new JSONObject().put("file_id", videoId)
                    .put("title", title)
                    .put("frontcover", coverURL)
                    .put("location", "未知")
                    .put("play_url", videoURL);
            TCUserMgr.getInstance().request("/upload_ugc", body, new TCUserMgr.HttpCallback("upload_ugc", new TCUserMgr.Callback() {
                @Override
                public void onSuccess(JSONObject data) {
                    /**
                     * ELK上报：发布视频到服务器
                     */
                    LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_UPLOAD_SERVER, TCUserMgr.SUCCESS_CODE, "UploadUGCVideo Sucess");

                    BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(UGCKitConstants.EVENT_MSG_PUBLISH_DONE);

                            if (mOnPublishListener != null) {
                                mOnPublishListener.onPublishCompleted();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(int code, final String msg) {
                    /**
                     * ELK上报：发布视频到服务器
                     */
                    LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_UPLOAD_SERVER, code, msg);
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        NetworkUtil.getInstance(UGCKitImpl.getAppContext()).unregisterNetChangeReceiver();

        deleteCache();
    }

}
