package com.nasinet.live.presenter;

import com.nasinet.live.contract.UserInfoContract;
import com.nasinet.live.model.UserInfoModel;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    private UserInfoContract.Model model;

    public UserInfoPresenter() {
        model = new UserInfoModel();
    }


    @Override
    public void getUserPage() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
/*        model.getUserConfig()
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
                });*/
    }
}
