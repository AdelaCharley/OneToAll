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
package com.nasinet.live.model;


import com.nasinet.live.contract.LivePushContrat;
import com.nasinet.live.model.entity.BaseLiveInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.ContributeRank;
import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.QCloudData;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.net.RetrofitClient;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;


public class LivePushModel implements LivePushContrat.Model {


    public Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTempKeysForCos(body);
    }

    @Override
    public Flowable<BaseResponse<HotLive>> startLive(FormBody body) {
        return RetrofitClient.getInstance().getApi().startLive(body);
    }

    public Flowable<BaseResponse<ArrayList<GiftData>>> getGiftList() {
        return RetrofitClient.getInstance().getApi().getGiftList();
    }

    @Override
    public Flowable<BaseResponse> sendGift(FormBody body) {
        return RetrofitClient.getInstance().getApi().sendGift(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body) {
        return RetrofitClient.getInstance().getApi().getContributeRank(body);
    }

    @Override
    public Flowable<BaseResponse> endlive(FormBody body) {
        return RetrofitClient.getInstance().getApi().endlive(body);
    }

    @Override
    public Flowable<BaseResponse> publish(FormBody body) {
        return RetrofitClient.getInstance().getApi().publish(body);
    }

    @Override
    public Flowable<BaseResponse> requestMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().requestMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> stopMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().stopMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> refuseMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().refuseMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> acceptMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().acceptMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> mergeStream(FormBody body) {
        return RetrofitClient.getInstance().getApi().mergeStream(body);
    }

    @Override
    public Flowable<BaseResponse> enterPkMode(FormBody body) {
        return RetrofitClient.getInstance().getApi().enterPkMode(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserBasicInfo(body);
    }

    @Override
    public Flowable<BaseResponse> endPk(FormBody body) {
        return RetrofitClient.getInstance().getApi().endPk(body);
    }

    @Override
    public Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardInfo(body);
    }

    @Override
    public Flowable<BaseResponse> getGuardianCount(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardianCount(body);
    }

    @Override
    public Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveBasicInfo(body);
    }

    @Override
    public Flowable<BaseResponse> setLinkOnOff(FormBody body) {
        return RetrofitClient.getInstance().getApi().setLinkOnOff(body);
    }
}
