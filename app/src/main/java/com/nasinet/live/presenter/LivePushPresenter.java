/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nasinet.live.presenter;

import com.nasinet.live.contract.LivePushContrat;
import com.nasinet.live.model.LivePushModel;
import com.nasinet.live.model.entity.BaseLiveInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.ContributeRank;

import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.Invite;
import com.nasinet.live.model.entity.MLVBLoginResponse;
import com.nasinet.live.model.entity.QCloudData;
import com.nasinet.live.model.entity.StartLive;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.net.RxScheduler;
import com.nasinet.live.util.MyUserInstance;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;


public class LivePushPresenter extends BasePresenter<LivePushContrat.View> implements LivePushContrat.Presenter {
    private LivePushContrat.Model model;

    public LivePushPresenter() {
        model = new LivePushModel();
    }

    public void getTempKeysForCos(String uid, String token) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.getTempKeysForCos(new FormBody.Builder()
                .add("uid", uid).add("token", token).build())
                .compose(RxScheduler.<BaseResponse<QCloudData>>Flo_io_main())
                .as(mView.<BaseResponse<QCloudData>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<QCloudData>>() {
                    @Override
                    public void accept(BaseResponse<QCloudData> bean) throws Exception {
                        mView.setTempKeysForCos(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void startLive(String mlvb_token, String cateid, String thumb, String title, String orientation, String room_type, String price, String pwd) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        if (room_type.equals("1") & pwd.equals("")) {

            return;
        }

        if (room_type.equals("2") & price.equals("") & Integer.parseInt(price) < 0) {

            return;
        }
        //具体实现
        model.startLive(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("cateid", cateid).add("thumb", thumb)
                .add("room_type", room_type).add("price", price)
                .add("pwd", pwd)
                .add("title", title)
                .add("mlvb_token", mlvb_token)

                .add("orientation", orientation).build())
                .compose(RxScheduler.<BaseResponse<HotLive>>Flo_io_main())
                .as(mView.<BaseResponse<HotLive>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<HotLive>>() {
                    @Override
                    public void accept(BaseResponse<HotLive> bean) throws Exception {
                        mView.startSuccess(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void getGiftList() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getGiftList()
                .compose(RxScheduler.<BaseResponse<ArrayList<GiftData>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<GiftData>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<GiftData>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<GiftData>> bean) throws Exception {
                        mView.setGiftList(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void sendGift(String uid, String token, String anchorid, String livetid, String giftid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.sendGift(new FormBody.Builder().add("uid", uid)
                .add("token", token)
                .add("anchorid", anchorid)
                .add("liveid", livetid)
                .add("giftid", giftid).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        mView.sendGiftSuccess();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    public void getContributeRank(String liveid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getContributeRank(new FormBody.Builder().add("liveid", liveid).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ContributeRank>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ContributeRank>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ContributeRank>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ContributeRank>> bean) throws Exception {
                        mView.setContributeRank(bean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    public void endlive(String liveid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.endlive(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).add("liveid", liveid).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        mView.endlive(bean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    @Override
    public void publish(String type, String title, String image_url, String blur_image_url, String video_url, String single_display_type, String unlock_price,String topic) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.publish(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("type", type)
                .add("title", title)
                .add("image_url", image_url)
                .add("blur_image_url", blur_image_url)
                .add("video_url", video_url)
                .add("topic", topic)
                .add("single_display_type", single_display_type)
                .add("unlock_price", unlock_price).build())

                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.publish();
                        } else {
                            mView.showMgs(bean.getMsg());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    @Override
    public void requestMlvbLink(String anchorid, String mlvb_token) {
        if (!isViewAttached()) {
            return;
        }
        model.requestMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .add("mlvb_token", mlvb_token)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.refuseMlvbLink(bean);
                        } else {
                            mView.showMgs(bean.getMsg());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void stopMlvbLink(String anchorid, String linkerid) {
        if (!isViewAttached()) {
            return;
        }
        model.stopMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .add("linkerid", linkerid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.stopMlvbLink(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void refuseMlvbLink(String userid) {
        if (!isViewAttached()) {
            return;
        }
        model.refuseMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("userid", userid)

                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.refuseMlvbLink(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void acceptMlvbLink(String userid) {
        if (!isViewAttached()) {
            return;
        }
        model.acceptMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("userid", userid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.acceptMlvbLink(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void setLinkOnOff(String type) {
        if (!isViewAttached()) {
            return;
        }
        model.setLinkOnOff(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("type", type)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setLinkOnOff(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void mergeStream(String linkerid) {
        if (!isViewAttached()) {
            return;
        }
        model.mergeStream(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("linkerid", linkerid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.mergeStream(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void enterPkMode() {
        if (!isViewAttached()) {
            return;
        }
        model.enterPkMode(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())

                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.enterPkMode(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getUserBasicInfo(String id) {
        if (!isViewAttached()) {
            return;
        }
        model.getUserBasicInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("id", id)
                .build()).compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getUserBasicInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void endPk() {
        if (!isViewAttached()) {
            return;
        }
        model.endPk(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.endPk(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getGuardInfo(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getGuardInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<GuardianInfo>>Flo_io_main())
                .as(mView.<BaseResponse<GuardianInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<GuardianInfo>>() {
                    @Override
                    public void accept(BaseResponse<GuardianInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getGuardianCount(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getGuardianCount(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardianCount(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getLiveBasicInfo(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.getLiveBasicInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("liveid", liveid)
                .build()).compose(RxScheduler.<BaseResponse<BaseLiveInfo>>Flo_io_main())
                .as(mView.<BaseResponse<BaseLiveInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<BaseLiveInfo>>() {
                    @Override
                    public void accept(BaseResponse<BaseLiveInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveBasicInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }
}
