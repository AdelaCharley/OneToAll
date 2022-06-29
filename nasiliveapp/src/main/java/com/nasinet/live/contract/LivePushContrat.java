package com.nasinet.live.contract;

import com.nasinet.live.base.BaseView;
import com.nasinet.live.model.entity.BaseLiveInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.ContributeRank;

import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.MLVBLoginResponse;
import com.nasinet.live.model.entity.QCloudData;
import com.nasinet.live.model.entity.StartLive;
import com.nasinet.live.model.entity.UserRegist;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;

public interface LivePushContrat {
    interface Model {
        Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body);
        Flowable<BaseResponse<HotLive>> startLive(FormBody body);
        Flowable<BaseResponse<ArrayList<GiftData>>> getGiftList();
        Flowable<BaseResponse> sendGift(FormBody body);
        Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body);
        Flowable<BaseResponse> endlive(FormBody body);
        Flowable<BaseResponse> publish(FormBody body);
        Flowable<BaseResponse> setLinkOnOff(FormBody body);

        Flowable<BaseResponse> requestMlvbLink(FormBody body);
        Flowable<BaseResponse> stopMlvbLink(FormBody body);
        Flowable<BaseResponse> refuseMlvbLink(FormBody body);
        Flowable<BaseResponse> acceptMlvbLink(FormBody body);
        Flowable<BaseResponse> mergeStream(FormBody body);

        Flowable<BaseResponse> enterPkMode(FormBody body);
        Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body);
        Flowable<BaseResponse> endPk(FormBody body);
        Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body);
        Flowable<BaseResponse> getGuardianCount(FormBody body);
        Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body);
    }
    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void setTempKeysForCos(QCloudData data){

        }
        default void startSuccess(HotLive data){

        }
        default void setGiftList(ArrayList<GiftData> giftList){

        }

        default void sendGiftSuccess(){

        }
        default void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank){

        }

        default void endlive(BaseResponse baseResponse){

        }
        default void publish(){}
        default void showMgs(String msg){}


        default void requestMlvbLink(BaseResponse baseResponse){

        }

        default void stopMlvbLink(BaseResponse baseResponse){

        }


        default void refuseMlvbLink(BaseResponse baseResponse){

        }

        default void acceptMlvbLink(BaseResponse baseResponse){

        }
        default void setLinkOnOff(BaseResponse baseResponse){

        }
        default void mergeStream(BaseResponse baseResponse){

        }
        default void enterPkMode(BaseResponse baseResponse){

        }
        default void endPk(BaseResponse baseResponse){

        }
        default void getUserBasicInfo(BaseResponse<UserRegist> baseResponse){

        }
        default void getGuardInfo(BaseResponse<GuardianInfo> baseResponse){

        }
        default void getGuardianCount(BaseResponse baseResponse){

        }
        default void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse){

        }

    }

    interface Presenter {
        void getTempKeysForCos(String uid,String token);
        void startLive( String mlvb_token,String cateid, String thumb, String title, String orientation,String room_type,String price,String pwd);
        void getGiftList();
        void sendGift(String uid,String token,String anchorid,String livetid,String giftid);
        void getContributeRank(String liveid);
        void endlive(String liveid);
        void publish(String type,String title,String image_url,String blur_image_url,String video_url,String single_display_type,String unlock_price,String topic);
        void requestMlvbLink(String anchorid,String mlvb_token);
        void stopMlvbLink(String anchorid,String linkerid);
        void refuseMlvbLink(String userid);
        void acceptMlvbLink(String userid);
        void setLinkOnOff(String type);
        void mergeStream(String linkerid);
        void enterPkMode();
        void getUserBasicInfo(String id);
        void endPk();
        void getGuardInfo(String anchorid);
        void getGuardianCount(String anchorid);
        void getLiveBasicInfo(String liveid);
    }
}
