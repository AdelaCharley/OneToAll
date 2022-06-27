package com.nasinet.live.base;


import android.os.Environment;

public class Constants {
    public static final String CHAT_INFO = "chatInfo";
    public static final String UMENG_APP_KEY = "";
    //外部sd卡
    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    //内部存储 /data/data/<application package>/files目录
    public static final String INNER_PATH = MyApp.getInstance().getFilesDir().getAbsolutePath();
    //文件夹名字
    public static final String DOWNLOAD_GIF = "downloadGif";
    public static final String GIF_PATH = INNER_PATH + "/gif/";
    private static final String DIR_NAME = "nasi";
    public static final String VIDEO_PATH_2 = DCMI_PATH + "/" + DIR_NAME + "/video/";
    public static final String PAYLOAD = "payload";
    public static final String PAY_WX_NOT_ENABLE = "微信支付未接入";
    public static final String GIF_GIFT_PREFIX = "gif_gift_";
    public static final String VIDEO_FACE_OPEN = "videoOpenFace";
    public static final String VIDEO_COMMENT = "videoComment";
    public static final String VIDEO_CHOSE_COMMENT = "videoChoseComment";
    public static final String VIDEO_PATH = "videoPath";
    public static final String REPLAY_OTHER_FLAG = "replayFlag";

}
