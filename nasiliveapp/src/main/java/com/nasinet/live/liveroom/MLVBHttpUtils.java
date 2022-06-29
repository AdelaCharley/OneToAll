package com.nasinet.live.liveroom;

import com.nasinet.live.liveroom.roomutil.http.HttpRequests;
import com.nasinet.live.liveroom.roomutil.http.HttpResponse;
import com.nasinet.live.util.MyUserInstance;

import org.json.JSONArray;
import org.json.JSONObject;


public class MLVBHttpUtils {

    protected static MLVBHttpUtils mInstance;
    HttpRequests mHttpRequest;
    protected static final String mServerDomain = "https://liveroom.qcloud.com/weapp/live_room"; //RoomService后台域名
    private int mMainStreamWidth = 540;
    private int mMainStreamHeight = 960;

    public static MLVBHttpUtils sharedInstance() {
        synchronized (MLVBLiveRoomImpl.class) {
            if (mInstance == null) {
                mInstance = new MLVBHttpUtils();

            }
            return mInstance;
        }
    }

    public void init() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpRequests(mServerDomain);
        }
    }


    public void login(HttpRequests.OnResponseCallback<HttpResponse.LoginResponse> response) {

        mHttpRequest.login(MyUserInstance.getInstance().getUserConfig().getConfig().getIm_sdkappid(), MyUserInstance.getInstance().getUserinfo().getProfile().getUid(), MyUserInstance.getInstance().getUserinfo().getTxim_sign(), "Android", response);
    }


    public void mergeStream(String mCurrRoomID, String userID, JSONObject mergeParams, HttpRequests.OnResponseCallback<HttpResponse.MergeStream> response) {

        mHttpRequest.mergeStream(mCurrRoomID, userID, mergeParams, response);
    }

    public String getInputStreamId(String inputId,String stream){
        String mInputStreamId="";
        int index=stream.indexOf("_");
        if(index!=-1){
           // subString = subString.substring(0, index);
            mInputStreamId=stream.substring(0,index);
            mInputStreamId=mInputStreamId+"_"+inputId;
            return mInputStreamId;
        }
        return "";
    }


    public JSONObject createRequestParam(String mMainStreamId, String mInputStreamId) {

        JSONObject requestParam = null;

        try {
            // input_stream_list
            JSONArray inputStreamList = new JSONArray();

            // 大主播
            {
                JSONObject layoutParam = new JSONObject();
                layoutParam.put("image_layer", 1);

                JSONObject mainStream = new JSONObject();
                mainStream.put("input_stream_id", mMainStreamId);
                mainStream.put("layout_params", layoutParam);

                inputStreamList.put(mainStream);
            }

            int subWidth = 160;
            int subHeight = 240;
            int offsetHeight = 90;
            if (mMainStreamWidth < 540 || mMainStreamHeight < 960) {
                subWidth = 120;
                subHeight = 180;
                offsetHeight = 60;
            }
            int subLocationX = mMainStreamWidth - subWidth;
            int subLocationY = mMainStreamHeight - subHeight - offsetHeight;

            // 小主播
            int layerIndex = 0;

            if(mInputStreamId!=null){
                JSONObject layoutParam = new JSONObject();
                layoutParam.put("image_layer", layerIndex + 2);
                layoutParam.put("image_width", subWidth);
                layoutParam.put("image_height", subHeight);
                layoutParam.put("location_x", subLocationX);
                layoutParam.put("location_y", subLocationY - layerIndex * subHeight);

                JSONObject subStream = new JSONObject();
                subStream.put("input_stream_id", mInputStreamId);
                subStream.put("layout_params", layoutParam);

                inputStreamList.put(subStream);
            }



            // para
            JSONObject para = new JSONObject();
            para.put("app_id", "");
            para.put("interface", "mix_streamv2.start_mix_stream_advanced");
            para.put("mix_stream_session_id", mMainStreamId);
            para.put("output_stream_id", mMainStreamId);
            para.put("input_stream_list", inputStreamList);

            // interface
            JSONObject interfaceObj = new JSONObject();
            interfaceObj.put("interfaceName", "Mix_StreamV2");
            interfaceObj.put("para", para);

            // requestParam
            requestParam = new JSONObject();
            requestParam.put("timestamp", System.currentTimeMillis() / 1000);
            requestParam.put("eventId", System.currentTimeMillis() / 1000);
            requestParam.put("interface", interfaceObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestParam;
    }

    public String getStreamIDByStreamUrl(String strStreamUrl) {
        if (strStreamUrl == null || strStreamUrl.length() == 0) {
            return null;
        }

        //推流地址格式：rtmp://8888.livepush.myqcloud.com/path/8888_test_12345_test?txSecret=aaaa&txTime=bbbb
        //拉流地址格式：rtmp://8888.liveplay.myqcloud.com/path/8888_test_12345_test
        //            http://8888.liveplay.myqcloud.com/path/8888_test_12345_test.flv
        //            http://8888.liveplay.myqcloud.com/path/8888_test_12345_test.m3u8


        String subString = strStreamUrl;

        {
            //1 截取第一个 ？之前的子串
            int index = subString.indexOf("?");
            if (index != -1) {
                subString = subString.substring(0, index);
            }
            if (subString == null || subString.length() == 0) {
                return null;
            }
        }

        {
            //2 截取最后一个 / 之后的子串
            int index = subString.lastIndexOf("/");
            if (index != -1) {
                subString = subString.substring(index + 1);
            }

            if (subString == null || subString.length() == 0) {
                return null;
            }
        }

        {
            //3 截取第一个 . 之前的子串
            int index = subString.indexOf(".");
            if (index != -1) {
                subString = subString.substring(0, index);
            }
            if (subString == null || subString.length() == 0) {
                return null;
            }
        }

        return subString;
    }

}
