package com.nasinet.live.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.interfaces.OnGetUnRead;
import com.nasinet.live.ui.act.LiveManageActivity;
import com.nasinet.live.ui.act.MyLevelActivity;
import com.nasinet.live.ui.act.MyShortVideoActivity;
import com.nasinet.live.ui.act.MyTrendActivity;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.net.APIService;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.act.ApplyAnchorActivity;
import com.nasinet.live.ui.act.BuyMemberActivity;
import com.nasinet.live.ui.act.CollectActivity;
import com.nasinet.live.ui.act.FollowActivity;
import com.nasinet.live.ui.act.HomeActivity;
import com.nasinet.live.ui.act.IntimacyListActivity;
import com.nasinet.live.ui.act.InviteFriendActivity;
import com.nasinet.live.ui.act.MyIncomeActivity;
import com.nasinet.live.ui.act.MySettingActivity;
import com.nasinet.live.ui.act.MyWalletActivity;

import com.nasinet.live.ui.act.AllMessageActivity;
import com.nasinet.live.ui.act.PhoneLoginActivity;
import com.nasinet.live.ui.act.RoomManagerActivity;
import com.nasinet.live.ui.act.SettingActivity;
import com.nasinet.live.ui.act.ToPayActivity;
import com.nasinet.live.ui.act.WebShopActivity;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.WordUtil;
import com.nasinet.nasinet.utils.AppManager;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnGetUnRead {

    @BindView(R.id.frag_my_txt_nickname)
    TextView frag_my_txt_nickname;
    @BindView(R.id.frag_my_txt_id)
    TextView frag_my_txt_id;

    @BindView(R.id.tv_num_follow)
    TextView tv_num_follow;

    @BindView(R.id.tv_num_fans)
    TextView tv_num_fans;
    @BindView(R.id.tv_authentication)
    TextView tv_authentication;

    @BindView(R.id.tv_num_guest)
    TextView tv_num_guest;

    @BindView(R.id.frag_my_img_icon)
    CircleImageView frag_my_img_icon;

    @BindView(R.id.tv_red_hot)
    TextView tv_red_hot;


    @BindView(R.id.iv_user_level)
    ImageView iv_user_level;
    @BindView(R.id.iv_anchor_level)
    ImageView iv_anchor_level;
    @BindView(R.id.iv_vip)
    ImageView iv_vip;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.rl_no_login)
    RelativeLayout rl_no_login;
    @BindView(R.id.rl_top_itmes)
    RelativeLayout rl_top_itmes;

    @BindView(R.id.tv_no_login)
    TextView tv_no_login;
    @BindView(R.id.rl_room_manager)
    RelativeLayout rl_room_manager;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        frag_my_txt_nickname.setText(MyUserInstance.getInstance().getUserinfo().getNick_name());
        frag_my_txt_id.setText("ID：" + MyUserInstance.getInstance().getUserinfo().getId());
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(MyUserInstance.getInstance().getUserinfo().getAvatar()).into(frag_my_img_icon);
        if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("1")) {
            tv_authentication.setText(WordUtil.getString(R.string.LiveLog));

        }
        ((HomeActivity) getActivity()).setOnGetUnRead(this);
        if (((HomeActivity) getActivity()).getAllUnReadMsgCount().equals("0")) {
            tv_red_hot.setVisibility(View.GONE);

        } else {
            tv_red_hot.setText(((HomeActivity) getActivity()).getAllUnReadMsgCount());
            tv_red_hot.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rl_room_manager,R.id.rl_my_level, R.id.rl_my_trend, R.id.rl_my_short, R.id.rl_wallet, R.id.rl_income, R.id.rl_authentication, R.id.ll_follow, R.id.ll_fans, R.id.iv_pay, R.id.rl_setting, R.id.iv_message,
            R.id.rl_intimacy,R.id.tv_no_login, R.id.rl_business, R.id.rl_vip_center, R.id.rl_invite, R.id.rl_collection, R.id.frag_my_img_icon, R.id.iv_edit, R.id.rl_my_shop, R.id.rl_guild})
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.rl_my_level:
              if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MyLevelActivity.class);
                startActivity(i);
              }
                break;
            case R.id.rl_my_trend:
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    i = new Intent(getContext(), MyTrendActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.rl_my_short:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MyShortVideoActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_wallet:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MyWalletActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_income:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MyIncomeActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_authentication:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("0")) {
                    i = new Intent(getContext(), ApplyAnchorActivity.class).putExtra("TYPE", 1);
                } else if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("1")) {
                    i = new Intent(getContext(), LiveManageActivity.class);
                    tv_authentication.setText(WordUtil.getString(R.string.LiveLog));
                }
                startActivity(i);
                }
                break;
            case R.id.rl_room_manager:
                i = new Intent(getContext(), RoomManagerActivity.class);
                startActivity(i);
                break;


            case R.id.ll_follow:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), FollowActivity.class).putExtra("index", 0);
                startActivity(i);
                }
                break;
            case R.id.ll_fans:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), FollowActivity.class).putExtra("index", 1);
                startActivity(i);
                }
                break;
            case R.id.iv_pay:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), ToPayActivity.class);
                startActivity(i);
                }
                break;

            case R.id.iv_message:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), AllMessageActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_intimacy:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), IntimacyListActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_vip_center:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), BuyMemberActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_invite:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), InviteFriendActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_collection:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), CollectActivity.class);
                startActivity(i);
                }
                break;
            case R.id.frag_my_img_icon:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MySettingActivity.class);
                startActivity(i);
                }
                break;

            case R.id.iv_edit:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), MySettingActivity.class);
                startActivity(i);
                }
                break;
            case R.id.rl_guild:
                if(MyUserInstance.getInstance().visitorIsLogin()){
                i = new Intent(getContext(), WebShopActivity.class);
                i.putExtra("jump_url", APIService.Guild + "token=" + URLEncoder.encode(MyUserInstance.getInstance().getUserinfo().getToken()) + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());
                startActivity(i);
                }
                break;
            case R.id.rl_my_shop:
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    i = new Intent(getContext(), WebShopActivity.class);
                    i.putExtra("jump_url", APIService.Stores+MyUserInstance.getInstance().getUserinfo().getId() + "token=" + URLEncoder.encode(MyUserInstance.getInstance().getUserinfo().getToken()) + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());
                    startActivity(i);
                }
                break;
            case R.id.rl_business:

                i = new Intent(getContext(), WebShopActivity.class);
                i.putExtra("jump_url", APIService.Business);

                startActivity(i);
                break;
            case R.id.tv_no_login:
                AppManager.getAppManager().startActivity(PhoneLoginActivity.class);
                break;
            case R.id.rl_setting:
                i = new Intent(getContext(), SettingActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if(MyUserInstance.getInstance().hasToken()){
            mPresenter.getUserInfo();
            rl_no_login.setVisibility(View.GONE);
            rl_top_itmes.setVisibility(View.VISIBLE);
        }else{
            rl_no_login.setVisibility(View.VISIBLE);
            rl_top_itmes.setVisibility(View.GONE);
        }
    }


    @Override
    public void setUserInfo(UserRegist bean) {

        if (bean == null) {
            return;
        }
        frag_my_txt_nickname.setText(bean.getNick_name());
        tv_num_follow.setText(bean.getAttent_count());
        tv_num_fans.setText(bean.getFans_count());
        tv_num_guest.setText(bean.getVisitor_count());
        if (bean.getIs_anchor().equals("1")) {
            tv_authentication.setText(WordUtil.getString(R.string.LiveLog));

        }else{
            tv_authentication.setText(WordUtil.getString(R.string.AnchorAuthentication));

        }
        frag_my_txt_id.setText("ID：" + MyUserInstance.getInstance().getUserinfo().getId());
        Glide.with(this).load(bean.getAvatar()).into(frag_my_img_icon);

        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(MyUserInstance.getInstance().getLevel(bean.getUser_level())).into(iv_user_level);

        if (null != bean.getIs_anchor()) {
            if (bean.getIs_anchor().equals("0")) {
                iv_anchor_level.setVisibility(View.GONE);
            } else {
                iv_anchor_level.setVisibility(View.VISIBLE);
                Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(MyUserInstance.getInstance().getAnchorLevel(bean.getAnchor_level())).into(iv_anchor_level);
            }
        } else {
            iv_anchor_level.setVisibility(View.GONE);
        }
        if (bean.getVip_level() != null & bean.getVip_date() != null) {
            if (!bean.getVip_level().equals("0")) {

                if (MyUserInstance.getInstance().OverTime(bean.getVip_date())) {
                    Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(MyUserInstance.getInstance().getVipLevel(bean.getVip_level())).into(iv_vip);
                    iv_vip.setVisibility(View.VISIBLE);
                } else {
                    iv_vip.setVisibility(View.GONE);
                }
            } else {
                iv_vip.setVisibility(View.GONE);
            }
        } else {
            iv_vip.setVisibility(View.GONE);
        }
        MyUserInstance.getInstance().setUserInfo(bean);
    }

    @Override
    public void getUnRead(String count) {
        if (count.equals("0")) {
            tv_red_hot.setVisibility(View.GONE);

        } else {
            tv_red_hot.setText(count);
            tv_red_hot.setVisibility(View.VISIBLE);
        }

    }
}
