package com.nasinet.live.presenter;

import com.nasinet.live.contract.WalletContract;
import com.nasinet.live.model.WalletModel;

public class WalletPresenter extends BasePresenter<WalletContract.View> implements WalletContract.Presenter {
    private WalletContract.Model model;
    public WalletPresenter() {
        model = new WalletModel();
    }
}
