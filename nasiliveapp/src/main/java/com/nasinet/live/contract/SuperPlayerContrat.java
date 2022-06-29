package com.nasinet.live.contract;

import androidx.annotation.NonNull;

import com.nasinet.live.base.BaseView;
import com.nasinet.live.model.entity.AnchorInfo;
import com.nasinet.live.model.entity.BaseLiveInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.ContributeRank;
import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.LiveInfo;
import com.nasinet.live.model.entity.ShopItem;
import com.nasinet.live.model.entity.UserRegist;
import com.tencent.liteav.demo.play.bean.GiftData;
import java.util.ArrayList;
import io.reactivex.Flowable;
import okhttp3.FormBody;

public interface SuperPlayerContrat {

    interface Model {
        Flowable<BaseResponse<ArrayList<GiftData>>> getGiftList();
        Flowable<BaseResponse> sendGift(FormBody body);
        Flowable<BaseResponse> timeBilling(FormBody body);
        Flowable<BaseResponse<AnchorInfo>> getAnchorInfo(FormBody body);
        Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body);

        Flowable<BaseResponse> requestMlvbLink(FormBody body);
        Flowable<BaseResponse> stopMlvbLink(FormBody body);
        Flowable<BaseResponse> refuseMlvbLink(FormBody body);
        Flowable<BaseResponse> acceptMlvbLink(FormBody body);

        Flowable<BaseResponse> checkIsMgr(FormBody body);
        Flowable<BaseResponse> checkAttent(FormBody body);

        Flowable<BaseResponse<ArrayList<ShopItem>>> getGoodsList(FormBody body);
        Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body);
        Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body);
        Flowable<BaseResponse> getGuardianCount(FormBody body);
        Flowable<BaseResponse<LiveInfo>> getLiveInfo(FormBody body);
        Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(FormBody body);
        Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void setGiftList(ArrayList<GiftData> giftList){

        }

        default void sendSuccess(String gold){

        }


        default void timeBilling(BaseResponse baseResponse){

        }
        default void setAnchorInfo(BaseResponse<AnchorInfo> anchorInfo){

        }

        default void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank){

        }

        default void showMessage(@NonNull String message) {

        }

        default void requestMlvbLink(BaseResponse baseResponse){

        }

        default void stopMlvbLink(BaseResponse baseResponse){

        }


        default void refuseMlvbLink(BaseResponse baseResponse){

        }

        default void acceptMlvbLink(BaseResponse baseResponse){

        }

        default void checkIsMgr(BaseResponse baseResponse){

        }
        default void checkAttent(BaseResponse baseResponse){

        }
        default void getGoodsList(BaseResponse<ArrayList<ShopItem>> baseResponse){

        }
        default void getUserBasicInfo(BaseResponse<UserRegist> baseResponse){

        }
        default void getGuardInfo(BaseResponse<GuardianInfo> baseResponse){

        }
        default void getGuardianCount(BaseResponse baseResponse){

        }
        default void getLiveInfo(BaseResponse<LiveInfo> baseResponse){

        }
        default void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse){

        }


    }

    interface Presenter {
        void requestMlvbLink(String anchorid,String mlvb_token);
        void stopMlvbLink(String anchorid,String linkerid);
        void refuseMlvbLink(String userid);
        void acceptMlvbLink(String userid);

        void checkIsMgr(String anchorid);
        void checkAttent(String anchorid);
        void getGoodsList(String anchorid);
        void getUserBasicInfo(String id);
        void getGuardInfo(String anchorid);
        void getGuardianCount(String anchorid);
        void getLiveInfo(String liveid);
        void getAnchorLiveInfo(String anchorid);
        void getLiveBasicInfo(String liveid);
    }


}
