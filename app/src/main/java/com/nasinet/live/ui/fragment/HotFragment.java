package com.nasinet.live.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.model.entity.HomeAd;
import com.nasinet.live.ui.act.ToPayActivity;
import com.nasinet.live.dialog.HomeDialog;
import com.nasinet.live.dialog.LivePriceDialog;
import com.nasinet.live.ui.act.UserRankActivity;
import com.nasinet.live.ui.act.WebViewActivity;
import com.nasinet.live.ui.adapter.HomeLiveRecommendAdapter;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.model.entity.Banners;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.LiveCategory;
import com.nasinet.live.presenter.HomePresenter;

import com.nasinet.live.ui.act.SuperPlayerActivity;
import com.nasinet.live.ui.act.SuperPlayerVerticalActivity;
import com.nasinet.live.ui.adapter.HomeLiveAdapter;
import com.nasinet.live.ui.os.OnItemClickListener;
import com.nasinet.live.util.GridSpacingItemDecoration;
import com.nasinet.live.dialog.PassWordDialog;
import com.nasinet.live.util.StringUtil;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.dialog.UpdateDialog;
import com.nasinet.live.util.WordUtil;
import com.nasinet.live.widget.Dialogs;
import com.nasinet.nasinet.utils.AppManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static org.greenrobot.eventbus.EventBus.TAG;

@SuppressLint("ValidFragment")
public class HotFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnRefreshListener, OnLoadMoreListener {

    private List<Banners> banners = new ArrayList<>();
    private Dialog dialog;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_hot)
    RecyclerView rc_hot;
    @BindView(R.id.rl_null)
    FrameLayout rl_null;
    private int page = 1;
    LiveCategory liveCategory;
    int type = 0;
    HomeLiveAdapter homeAdapter;
    HomeLiveRecommendAdapter homeLiveRecommendAdapter;
    ArrayList<HotLive> hotLives;
    private String keyword = "";
    private boolean first = true;
    private boolean have_banner = false;
    private boolean is_first = true;

    public RefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public HotFragment() {
    }


    @Override
    protected void initView(View view) {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        if (type != 2&type != 1) {
            loadData();
        }

        if (type == 0) {
            if (!first) {
                return;
            }

            if (bigOrSmall(getVersion(), MyUserInstance.getInstance().getUserConfig().getConfig().getVersion_android())) {
                first = false;
                UpdateDialog updateDialog = new UpdateDialog(getContext(), MyUserInstance.getInstance().getUserConfig().getConfig().getDl_web_url());
                updateDialog.show();
                updateDialog.setClose(new UpdateDialog.Close() {
                    @Override
                    public void close() {
                        mPresenter.getHomePopAd();
                    }
                });

            } else {
                first = false;
                mPresenter.getHomePopAd();
            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {


            if (getUserVisibleHint()) {//界面可见时
                if (type != 0 ) {
                    if (is_first) {
                        showLoading();
                        loadData();
                        is_first = false;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean bigOrSmall(String local, String server) {
        if(local==null||local.equals("")){
            return false;
        }
        if(server==null||server.equals("")){
            return false;
        }
        String[] locals = local.split("\\.");
        String[] servers = server.split("\\.");

        if(locals.length==0){
            return false;
        }
        if(servers.length==0){
            return false;
        }
        StringBuilder str_local=new StringBuilder();
        StringBuilder str_server=new StringBuilder();
        for(int i=0;i<locals.length;i++){
            str_local.append(locals[i]);
        }
        for(int i=0;i<servers.length;i++){
            str_server.append(locals[i]);
        }

      if(Integer.parseInt(str_local.toString())>=Integer.parseInt(str_server.toString())){
          return false;
      }else{
          return true;
      }


    }


    public HotFragment(int type) {
        this.type = type;
    }

    public HotFragment(int type, LiveCategory liveCategory) {
        this.type = type;
        this.liveCategory = liveCategory;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_2;
    }


    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (page > 1) {
            page--;
        }
        refreshLayout.finishLoadMore(false);
        refreshLayout.finishRefresh(false);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (hotLives != null) {
            hotLives.clear();
        }
        refreshLayout.setEnableLoadMore(true);
        loadData();

    }

    public void search(String word) {
        keyword = word;
        page = 1;
        if (hotLives != null) {
            hotLives.clear();
        }
        refreshLayout.setEnableLoadMore(true);
        loadData();
    }

    GridLayoutManager layoutManager;

    @Override
    public void getHotLives(BaseResponse<ArrayList<HotLive>> bean) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);


        if (bean.getData().size() == 0 & page == 1) {
            rl_null.setVisibility(View.VISIBLE);
            return;
        }

        if (bean.getData().size() == 0 & page > 1) {
            page--;
            refreshLayout.setEnableLoadMore(false);
            return;
        }

        rl_null.setVisibility(View.GONE);
        if (hotLives == null) {
            hotLives = bean.getData();
        } else {
            hotLives.addAll(bean.getData());
        }


        if (type == 0) {
            //为banner插入一个位置,固定插入到第数组的第三个位置
            if (hotLives.size() > 2) {
                if (hotLives.get(2).getAnchorid() != null) {
                    hotLives.add(2, new HotLive());
                }

            }

            //判断banner的数量,如果没有banner,跟有banner

            if (homeLiveRecommendAdapter == null) {
                homeLiveRecommendAdapter = new HomeLiveRecommendAdapter(getContext(), have_banner, hotLives);
                layoutManager = new GridLayoutManager(getContext(), 2);
                if (have_banner) {
                    //如果有banner,要让出两个位置,0给轮播图,3给rank
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int i) {
                            if (i == 0) {
                                return 2;
                            } else if (i == 3) {
                                return 2;
                            } else {
                                return 1;
                            }
                        }
                    });
                } else {
                    //如果没有banner,要让出一个位置,2给rank
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int i) {
                            if (i == 2) {
                                return 2;
                            } else {
                                return 1;
                            }
                        }
                    });
                }
                rc_hot.setLayoutManager(layoutManager);
                if (banners.size() != 0) {
                    homeLiveRecommendAdapter.addHeaderView(initBanner(banners));

                }

                if (rc_hot.getItemDecorationCount() == 0) {
                    rc_hot.addItemDecoration(new GridSpacingItemDecoration());
                }
                rc_hot.setNestedScrollingEnabled(true);
                rc_hot.setAdapter(homeLiveRecommendAdapter);
            } else {
                homeLiveRecommendAdapter.notifyDataSetChanged();
            }
            homeLiveRecommendAdapter.setOnItemClickListener(new HomeLiveRecommendAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(HotLive live, int postion) {
                    if (!isFastClick()) {


                        if (live.getAnchorid() == null) {
                            AppManager.getAppManager().startActivity(UserRankActivity.class);
                        } else {
                            liveClick(live);
                        }

                    }
                }
            });


        } else {
            if (homeAdapter == null) {
                homeAdapter = new HomeLiveAdapter(getContext(), R.layout.home_item, hotLives);
                layoutManager = new GridLayoutManager(getContext(), 2);
                rc_hot.setLayoutManager(layoutManager);
                if (rc_hot.getItemDecorationCount() == 0) {
                    rc_hot.addItemDecoration(new GridSpacingItemDecoration());
                }
                rc_hot.setNestedScrollingEnabled(true);

                rc_hot.setAdapter(homeAdapter);
            } else {
                homeAdapter.notifyDataSetChanged();
            }
            homeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(HotLive live) {
                    if (!isFastClick()) {
                        liveClick(live);
                    }
                }
            });
        }


    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取到版本号";
        }
    }

    @Override
    public void getHomePopAd(BaseResponse<HomeAd> bean) {
        HomeAd homeAd = bean.getData();
        if (homeAd == null) {
            return;
        }

        if (!getActivity().isFinishing()) {
            HomeDialog homeDialog = new HomeDialog(getContext(), homeAd);
            homeDialog.show();
            showLoading();
        }


    }

    private void initPayDialog(String live_gold, HotLive live, String my_gold) {
        LivePriceDialog.Builder builder = new LivePriceDialog.Builder(getContext());
        builder.create();

        builder.setContent(WordUtil.getString(R.string.Paid) + " " + live_gold + WordUtil.getString(R.string.Coin_Time));
        builder.setTitle(WordUtil.getString(R.string.Paid));
        builder.setSubmitText(WordUtil.getString(R.string.Submit));
        builder.setOnSubmit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFastClick()) {
                    builder.livePriceDialog.dismiss();
                    if (Integer.parseInt(my_gold) > Integer.parseInt(live_gold)) {
                        if (live.getOrientation().equals("1")) {
                            startActivity(new Intent(getContext(), SuperPlayerActivity.class).putExtra("studio_info", live));
                        } else {
                            startActivity(new Intent(getContext(), SuperPlayerVerticalActivity.class).putExtra("studio_info", live.getAnchor()));
                        }
                    } else {
                        showNoMoneyDialog(WordUtil.getString(R.string.Gold_Not_Enough), WordUtil.getString(R.string.Warn_Not_Enough_Coin), WordUtil.getString(R.string.Recharge));

                    }
                }
            }
        });
        builder.setCanCancel(true);
        builder.livePriceDialog.show();
    }


    @Override
    public void getHomeScrollAd(BaseResponse<ArrayList<Banners>> bean) {
        if (bean.getData() == null) {
            have_banner = false;
            return;
        }
        if (bean.getData().size() == 0) {
            have_banner = false;
            return;
        }
        have_banner = true;
        banners.addAll(bean.getData());

        mPresenter.getHotLives(page + "");

    }

    private View initBanner(List<Banners> banners) {
        View v = getLayoutInflater().inflate(R.layout.home_banner_layout, rc_hot, false);
        List<String> imgesUrl = new ArrayList<>();

        for (Banners banner : banners) {
            imgesUrl.add(banner.getImage_url());
        }

        XBanner xb_ad = v.findViewById(R.id.xb_ad);

        xb_ad.setData(R.layout.image_fresco, imgesUrl, null);

        xb_ad.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                SimpleDraweeView mSimpleView = view.findViewById(R.id.my_image_view);
                mSimpleView.setImageURI(imgesUrl.get(position));

            }
        });
        xb_ad.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                if (banners.size() != 0) {

                    if (banners.get(position).getJump_url() == null) {
                        return;
                    }
                    if (banners.get(position).getJump_url().equals("")) {
                        return;
                    }
                    switch (banners.get(position).getJump_type()) {
                        case "1":
                            Intent i = new Intent(getContext(), WebViewActivity.class);
                            i.putExtra("jump_url", banners.get(position).getJump_url());
                            i.putExtra("title", (banners.get(position).getTitle()));
                            getContext().startActivity(i);
                            break;
                        case "2":
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(banners.get(position).getJump_url());
                            intent.setData(content_url);
                            getContext().startActivity(intent);
                            break;
                    }
                }
            }
        });

        return v;
    }

    private void showNoMoneyDialog(String title, String content, String submit) {
        LivePriceDialog.Builder builder = new LivePriceDialog.Builder(getContext());
        builder.create();
        builder.setOnSubmit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToPayActivity.class);
                startActivity(intent);
            }
        });
        builder.setCanCancel(true);
        builder.setTitle(title);
        builder.setContent(content);
        builder.setSubmitText(submit);
        builder.livePriceDialog.show();
    }


    private void loadData() {
        switch (type) {
            case 0:
                mPresenter.getHomeScrollAd();

                break;
            case 1:
                if (liveCategory != null) {
                    mPresenter.getLivesByCategory(page + "", liveCategory.getId());
                }
                break;
            case 2:
                rl_null.setVisibility(View.VISIBLE);
                mPresenter.searchLive(page, keyword);
                break;
        }
    }


    private void liveClick(HotLive live) {
        switch (live.getRoom_type()) {
            case "0":
                if (live.getOrientation().equals("1")) {
                    startActivity(new Intent(getContext(), SuperPlayerActivity.class).putExtra("studio_info", live));
                } else {

                    Gson gson = new Gson();
                    StringUtil.log(gson.toJson(live.getAnchor()));

                    startActivity(new Intent(getContext(), SuperPlayerVerticalActivity.class).putExtra("studio_info", live.getAnchor()));
                }
                break;
            case "1":
                if (live.getPassword() == null) {
                    ToastUtils.showT("当前房间密码有误,请联系客服");
                    break;
                }
                PassWordDialog.Builder builder = new PassWordDialog.Builder(getContext());
                builder.setOnFinishListener(new PassWordDialog.OnFinishListener() {
                    @Override
                    public void onFinish(String price, PassWordDialog passWordDialog) {

                        if (StringUtil.md5(price).equals(live.getPassword().toUpperCase())) {
                            if (live.getOrientation().equals("1")) {
                                startActivity(new Intent(getContext(), SuperPlayerActivity.class).putExtra("studio_info", live));
                            } else {
                                startActivity(new Intent(getContext(), SuperPlayerVerticalActivity.class).putExtra("studio_info", live.getAnchor()));
                            }
                            passWordDialog.dismiss();
                        } else {

                            ToastUtils.showT("密码输入错误,请输入正确的密码");
                            passWordDialog.dismiss();
                        }


                    }
                });

                builder.create().show();
                builder.setHint("请输入房间密码");
                builder.setTitle("私密房间");
                break;
            case "2":

                initPayDialog(live.getPrice(), live, MyUserInstance.getInstance().getUserinfo().getGold());
                break;
        }

    }


}
