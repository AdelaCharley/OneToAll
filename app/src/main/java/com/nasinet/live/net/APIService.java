package com.nasinet.live.net;


import com.nasinet.live.model.entity.AnchorHistory;
import com.nasinet.live.model.entity.AnchorInfo;
import com.nasinet.live.model.entity.AttentUser;
import com.nasinet.live.model.entity.Banners;
import com.nasinet.live.model.entity.BaseLiveInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.BuyGuard;
import com.nasinet.live.model.entity.CashOutHistory;
import com.nasinet.live.model.entity.CodeMsg;
import com.nasinet.live.model.entity.Comment;
import com.nasinet.live.model.entity.ContributeRank;
import com.nasinet.live.model.entity.Guardian;
import com.nasinet.live.model.entity.GuardianInfo;
import com.nasinet.live.model.entity.HomeAd;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.Invite;

import com.nasinet.live.model.entity.LiveInfo;
import com.nasinet.live.model.entity.MLVBLoginResponse;
import com.nasinet.live.model.entity.MatchList;
import com.nasinet.live.model.entity.MomentDetail;
import com.nasinet.live.model.entity.PersonalAnchorInfo;
import com.nasinet.live.model.entity.ProfitLog;
import com.nasinet.live.model.entity.QCloudData;
import com.nasinet.live.model.entity.RoomManager;
import com.nasinet.live.model.entity.ShopItem;
import com.nasinet.live.model.entity.ShortVideo;
import com.nasinet.live.model.entity.StartLive;
import com.nasinet.live.model.entity.Topic;
import com.nasinet.live.model.entity.Trend;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.model.entity.UserInfo;
import com.nasinet.live.model.entity.UserRegist;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIService {

    /**
     * 登陆
     *
     * @return
     */
    String GetCommentReplys = "api/shortvideo/getCommentReplys";
    String GetCommentMomentReplys = "api/Moment/getCommentReplys";
    String GetCommentShort = "api/shortvideo/getComments";
    String SendCode = "api/user/sendCode";
    String BindPhone = "api/User/bindPhone";
    String GetHomePopAd = "api/config/getHomePopAd";
    String Collect = "api/Shortvideo/collect";
    String GetAnchorInfo = "api/Anchor/getAnchorInfo";
    String getAnchorLiveInfo = "api/Live/getAnchorLiveInfo";
    String ExplainingGoods = "api/live/explainingGoods";
    String BanUser = "api/live/banUser";
    String SetRoomMgr = "api/live/setRoomMgr";
    String LikeVideo = "api/shortvideo/likeVideo";
    String LikeMoment = "api/Moment/likeMoment";
    String buyVip = "api/vip/getEplutusOrder";
    String buyDiamond = "api/recharge/getEplutusOrder";
    String GetListByUser = "api/Moment/getListByUser";
    String ReoprtAnchor = "api/Anchor/reoprt";
    String ReoprtShortvideo = "api/shortvideo/reoprt";
    String AddShareCount = "api/Shortvideo/addShareCount";
    String CheckIsMgr = "api/live/checkIsMgr";

    String ReoprtMoment = "api/Moment/reoprt";
    String EditUserInfo = "api/User/editUserInfo";
    String SearchAnchor = "api/Anchor/searchAnchor";
    String SetComment = "api/shortvideo/setComment";
    String SetMomentComment = "api/Moment/publishComment";
    String LikeComment = "api/shortvideo/likeComment";
    String LikeMomentComment = "api/Moment/likeComment";
    String AttentAnchor = "api/Anchor/attentAnchor";
    String APP_GET_USER_INFO = "api/user/getUserInfo";
    String GetUserBasicInfo = "api/user/getUserBasicInfo";
    String Wxlogin = "api/user/wxlogin";
    String Qqlogin = "api/user/qqlogin";
    String WithDraw = "api/agent/withDraw";
    String UserAuthInfo = "api/User/userAuthInfo";
    String Withdraw = "api/withdraw/withdraw";
    String sendVerifyCode = "api/user/sendVerifyCode";
    String EditCashAccount = "api/withdraw/editCashAccount";
    String Withdrawlog = "api/withdraw/withdrawlog";
    String Withdrawlog_agent = "api/agent/withdrawlog";
    String GetProfitLog = "api/agent/getProfitLog";
    String GetInviteList = "api/agent/getInviteList";
    String GET_Profit = "api/User/giftProfit";
    String GET_dongtaiProfit = "api/User/momentProfit";
    String GET_TempKeysForCos = "api/Config/getTempKeysForCos";
    String SET_IdentityAuth = "api/auth/identityAuth";
    String SET_EditUserInfo = "api/User/editUserInfo";
    String GET_GetLiveLog = "api/User/getLiveLog";
    String GetAttentAnchors = "api/Anchor/getAttentAnchors";
    String CheckAttent = "api/Anchor/checkAttent";
    String GetLiveInfo = "api/Live/getLiveInfo";
    String GET_PriceList = "api/recharge/getPriceList";
    String GetGiftList = "api/Gift/getGiftList";
    String GetUserRankList = "api/Rank/getUserRankList";
    String GetAnchorRankList = "api/Rank/getAnchorRankList";
    String GET_Fans = "api/User/getFans";
    String GET_Account = "api/withdraw/getAccount";
    String GetSystemMsg = "api/Message/getSystemMsg";
    String GetWxPayOrder = "api/recharge/getWxPayOrder";
    String GetAliPayOrder = "api/recharge/getAliPayOrder";
    String GetVipWxPayOrder = "api/vip/getWxPayOrder";
    String GetVipAliPayOrder = "api/vip/getAliPayOrder";
    String PayDeposit = "api/shop/payDeposit";

    String GetAliShopPayOrder = "api/shop/getAliPayOrder";
    String Getuserlevelinfo = "api/user/getuserlevelinfo";
    String GetWxShopPayOrder = "api/shop/getWxPayOrder";
    String GetGoodsList = "api/live/getGoodsList";
    String GetWeekIntimacyRank = "api/Intimacy/getWeekIntimacyRank";
    String GetTotalIntimacyRank = "api/Intimacy/getTotalIntimacyRank";
    String GetVipPriceList = "api/vip/getVipPriceList";
    String GetAgentInfo = "api/agent/getAgentInfo";

    String baseUrl = "https:///";//服务地址

    String TXbaseUrl = "https://liveroom.qcloud.com/weapp/live_room/";

    String Guild = "h5/guild?";
    String Stores = "h5/stores/";
    String Goods = "h5/goods/";
    String Personal = "h5/personal?";
    String Business = "h5/cooperation";
    String About = "h5/about";
    String Privacy_2 = "h5/privacy/2";
    String Privacy_1 = "h5/privacy/1";
    String GetTempUserKey = "api/user/getTempUserKey";


    @POST("api/config/getCommonConfig")
    Flowable<BaseResponse<UserConfig>> getUserConfig();

    @POST("api/Ads/getHomeScrollAd")
    Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd();

    @POST("api/live/getHotLives")
    Flowable<BaseResponse<ArrayList<HotLive>>> getHotLives(@Body RequestBody body);

    @POST("api/live/getLivesByCategory")
    Flowable<BaseResponse<ArrayList<HotLive>>> getLivesByCategory(@Body RequestBody body);

    @POST("api/shortvideo/getRandomList")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getRandomList(@Body RequestBody body);

    @POST("api/shortvideo/getComments")
    Flowable<BaseResponse<ArrayList<Comment>>> getComments(@Body RequestBody body);

    @POST("api/config/getadd")
    Flowable<BaseResponse<Banners>> getUserPage();

    //登录
    @POST("/api/user/login")
    Flowable<BaseResponse<UserRegist>> userLogin(@Body RequestBody body);

    //登录
    @POST("/api/Moment/likeComment")
    Flowable<BaseResponse> likeComment(@Body RequestBody body);

    //登录
    @POST("/api/Moment/collectMoment")
    Flowable<BaseResponse> collectMoment(@Body RequestBody body);

    //登录
    @POST("/api/Moment/likeMoment")
    Flowable<BaseResponse> likeMoment(@Body RequestBody body);

    //发布短视频
    @POST("/api/shortvideo/publish")
    Flowable<BaseResponse> publish(@Body RequestBody body);

    //获取验证码
    @POST("/api/user/sendCode")
    Flowable<BaseResponse<CodeMsg>> getCode(@Body RequestBody phone);

    //用户注册
    @POST("/api/user/regist")
    Flowable<BaseResponse<UserRegist>> userRegist(@Body RequestBody body);

    //修改密码
    @POST("/api/user/changePwd")
    Flowable<BaseResponse<UserRegist>> changePwd(@Body RequestBody body);

    //用户配置
    @POST("/api/config/getCommonConfig")
    Flowable<BaseResponse<UserConfig>> getCommonConfig();

    @POST("/api/Gift/getGiftList")
    Flowable<BaseResponse<ArrayList<GiftData>>> getGiftList();

    @POST("/api/Gift/sendGift")
    Flowable<BaseResponse> sendGift(@Body RequestBody build);

    @POST("/api/Anchor/getAnchorBasicInfo")
    Flowable<BaseResponse<AnchorInfo>> getAnchorInfo(@Body RequestBody build);

    @POST("/api/live/getContributeRank")
    Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(@Body RequestBody build);

    @POST("/api/Live/endLive")
    Flowable<BaseResponse> endlive(@Body FormBody build);


    @POST("/api/Football/getMatchInfo")
    Flowable<BaseResponse<MatchList>> getMatchInfo(@Body FormBody build);

    @POST("/api/Config/getTempKeysForCos")
    Flowable<BaseResponse<QCloudData>> getTempKeysForCos(@Body FormBody body);

    @POST("/api/Live/mlvbStartLive")
    Flowable<BaseResponse<HotLive>> startLive(@Body FormBody body);

    @POST("/api/Gift/sendGift")
    Flowable<BaseResponse> sendGift(@Body FormBody build);

    @POST("/api/Moment/getAttentList")
    Flowable<BaseResponse<ArrayList<Trend>>> getMomentAttent(@Body FormBody build);

    @POST("/api/Moment/getHotList")
    Flowable<BaseResponse<ArrayList<Trend>>> getMomentHot(@Body FormBody build);

    @POST("/api/Moment/getComments")
    Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentDetail(@Body FormBody build);


    @POST("/api/Moment/getCommentReplys")
    Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentCommentReplys(@Body FormBody build);

    @POST("/api/shortvideo/getUserInfo")
    Flowable<BaseResponse<UserInfo>> getShortUserInfo(@Body FormBody build);

    @POST("/api/User/getUserInfo")
    Flowable<BaseResponse<UserRegist>> getUserInfo(@Body FormBody build);

    @POST("/api/shortvideo/getListByUser")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getAnchorWorks(@Body FormBody build);

    @POST("/api/User/myVideo")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getMyVideo(@Body FormBody build);


    @POST("/api/shortvideo/getListUserLike")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getUserLike(@Body FormBody build);

    @POST("/api/Moment/getListByUser")
    Flowable<BaseResponse<ArrayList<Trend>>> getListByUser(@Body FormBody build);

    @POST("/api/User/myMoment")
    Flowable<BaseResponse<ArrayList<Trend>>> getMyTrendList(@Body FormBody build);

    @POST("/api/Anchor/getAnchorInfo")
    Flowable<BaseResponse<PersonalAnchorInfo>> getPersonalAnchorInfo(@Body FormBody build);

    @POST("/api/Moment/unlockMoment")
    Flowable<BaseResponse> unlockMoment(@Body FormBody build);

    @POST("/api/Moment/publish")
    Flowable<BaseResponse> publish(@Body FormBody build);

    @POST("/api/Moment/getCollection")
    Flowable<BaseResponse<ArrayList<Trend>>> getCollection(@Body FormBody build);

    @POST("/api/shortvideo/getCollection")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getCollectionShort(@Body FormBody build);

    @POST("/api/live/timeBilling")
    Flowable<BaseResponse> getTimeBilling(@Body FormBody build);

    @POST("/api/config/getHomePopAd")
    Flowable<BaseResponse<HomeAd>> getHomePopAd();

    @POST("/api/Anchor/getAttentAnchors")
    Flowable<BaseResponse<ArrayList<AttentUser>>> getAttentAnchors(@Body FormBody build);

    @POST("/api/User/getFans")
    Flowable<BaseResponse<ArrayList<AttentUser>>> getFans(@Body FormBody build);

    @POST("/api/Moment/search")
    Flowable<BaseResponse<ArrayList<Trend>>> searchMoment(@Body FormBody body);

    @POST("/api/Anchor/search")
    Flowable<BaseResponse<ArrayList<AttentUser>>> searchAnchor(@Body FormBody body);

    @POST("/api/live/search")
    Flowable<BaseResponse<ArrayList<HotLive>>> searchLive(@Body FormBody body);

    @POST("/api/shortvideo/search")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> searchShort(@Body FormBody body);

    @POST("/api/withdraw/withdrawlog")
    Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog(@Body FormBody body);

    @POST("/api/agent/withdrawlog")
    Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog_agent(@Body FormBody body);

    @POST("/api/User/getLiveLog")
    Flowable<BaseResponse<ArrayList<AnchorHistory>>> livelog(@Body FormBody body);

    @POST("/api/User/bindPhone")
    Flowable<BaseResponse> bindPhone(@Body FormBody body);

    @POST("/api/agent/getProfitLog")
    Flowable<BaseResponse<ArrayList<ProfitLog>>> getProfitLog(@Body FormBody body);

    @POST("/api/agent/getInviteList")
    Flowable<BaseResponse<Invite>> getInviteList(@Body FormBody body);

    @POST("/api/Anchor/checkAttent")
    Flowable<BaseResponse> checkAttent(@Body FormBody body);

    @POST("/api/Anchor/attentAnchor")
    Flowable<BaseResponse> attentAnchor(@Body FormBody body);

    @POST("/api/user/qqlogin")
    Flowable<BaseResponse<UserRegist>> qqlogin(@Body FormBody body);

    @POST("/api/live/getBannedUserList")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(@Body FormBody body);

    @POST("/api/User/getManagedRooms")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getManagedRooms(@Body FormBody body);

    @POST("/api/live/getMgrList")
    Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(@Body FormBody body);


    @POST("/api/live/requestMlvbLink")
    Flowable<BaseResponse> requestMlvbLink(@Body FormBody body);

    @POST("/api/live/stopMlvbLink")
    Flowable<BaseResponse> stopMlvbLink(@Body FormBody body);

    @POST("/api/live/refuseMlvbLink")
    Flowable<BaseResponse> refuseMlvbLink(@Body FormBody body);

    @POST("/api/live/acceptMlvbLink")
    Flowable<BaseResponse> acceptMlvbLink(@Body FormBody body);


    @POST("/api/live/setLinkOnOff")
    Flowable<BaseResponse> setLinkOnOff(@Body FormBody body);

    @POST("/api/live/mergeStream")
    Flowable<BaseResponse> mergeStream(@Body FormBody body);

    @POST("/api/topic/getTopicList")
    Flowable<BaseResponse<ArrayList<Topic>>> getTopicList(@Body FormBody body);

    @POST("/api/shortvideo/listInTopic")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> videoListInTopic(@Body FormBody body);

    @POST("/api/moment/listInTopic")
    Flowable<BaseResponse<ArrayList<Trend>>> momentListInTopic(@Body FormBody body);

    @POST("/api/topic/getTopicInfo")
    Flowable<BaseResponse<Topic>> getTopicInfo(@Body FormBody body);

    @POST("/api/live/checkIsMgr")
    Flowable<BaseResponse> checkIsMgr(@Body FormBody body);

    @POST("/api/live/getGoodsList")
    Flowable<BaseResponse<ArrayList<ShopItem>>> getGoodsList(@Body FormBody body);

    @POST("/api/user/getUserBasicInfo")
    Flowable<BaseResponse<UserRegist>> getUserBasicInfo(@Body FormBody body);

    @POST("/api/Anchor/getGuardInfo")
    Flowable<BaseResponse<GuardianInfo>> getGuardInfo(@Body FormBody body);

    @POST("/api/Anchor/getGuardianCount")
    Flowable<BaseResponse> getGuardianCount(@Body FormBody body);

    @POST("/api/Anchor/getGuardianList")
    Flowable<BaseResponse<ArrayList<Guardian>>> getGuardianList(@Body FormBody body);

    @POST("/api/Anchor/guard")
    Flowable<BaseResponse<BuyGuard>> buyGuard(@Body FormBody body);

    @POST("/api/Live/getLiveInfo")
    Flowable<BaseResponse<LiveInfo>> getLiveInfo(@Body FormBody body);

    @POST("/api/Live/getAnchorLiveInfo")
    Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(@Body FormBody body);

    @POST("/api/Live/enterPkMode")
    Flowable<BaseResponse> enterPkMode(@Body FormBody body);

    @POST("/api/Live/endPk")
    Flowable<BaseResponse> endPk(@Body FormBody body);

    @POST("/api/Live/getLiveBasicInfo")
    Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(@Body FormBody body);
}
