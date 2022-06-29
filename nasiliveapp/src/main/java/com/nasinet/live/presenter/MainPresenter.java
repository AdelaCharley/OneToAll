package com.nasinet.live.presenter;



import com.nasinet.live.contract.MainContract;
import com.nasinet.live.model.MainModel;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void getUserConfig() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.getUserConfig()
                .compose(RxScheduler.<BaseResponse<UserConfig>>Flo_io_main())
                .as(mView.<BaseResponse<UserConfig>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserConfig>>() {
                    @Override
                    public void accept(BaseResponse<UserConfig> bean) throws Exception {
                        mView.onSuccess(bean);
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
