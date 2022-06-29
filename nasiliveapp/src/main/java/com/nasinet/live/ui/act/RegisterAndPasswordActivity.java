package com.nasinet.live.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpActivity;
import com.nasinet.live.base.MyApp;
import com.nasinet.live.contract.LoginContract;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.util.WordUtil;
import com.nasinet.live.widget.Dialogs;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.CodeMsg;
import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.presenter.LoginPresenter;
import com.nasinet.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterAndPasswordActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.login_page)
    TextView login_page;
    @BindView(R.id.next_tv)
    TextView next_tv;
    @BindView(R.id.verification_code)
    TextView verification_code;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.verification_code_et)
    EditText verification_code_et;
    @BindView(R.id.pwd_et)
    EditText pwd_et;
    private CountDownTimer countDownTimer;
    private Dialog dialog;
    private int tag;


    @OnClick({R.id.verification_code, R.id.next_tv, R.id.login_page, R.id.back_icon, R.id.rl_back2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_page:
                startActivity(new Intent(this, PhoneLoginActivity.class));
                finish();
                break;
            case R.id.next_tv:
                if (TextUtils.isEmpty(phone_number.getText())
                        || TextUtils.isEmpty(verification_code_et.getText())
                        || TextUtils.isEmpty(pwd_et.getText())) {
                    ToastUtils.showT(WordUtil.getString(R.string.Complete_Information));
                } else {

                    switch (tag) {
                        case 1:
                            String invite_code= MyUserInstance.getInstance().paste();
                            if(invite_code==null){
                                invite_code="";
                            }
                            if(invite_code.contains("NI_")){
                                invite_code= invite_code.substring(3,invite_code.length());
                            }

                            if(invite_code.length()==6){
                                mPresenter.userRegist(phone_number.getText().toString(),
                                        pwd_et.getText().toString(),
                                        verification_code_et.getText().toString(),
                                        invite_code);
                            }else{
                                mPresenter.userRegist(phone_number.getText().toString(),
                                        pwd_et.getText().toString(),
                                        verification_code_et.getText().toString(),
                                        "");
                            }

                            break;
                        case 2:
                            title_tv.setText(WordUtil.getString(R.string.Change_password));
                            mPresenter.changePwd(phone_number.getText().toString(),
                                    pwd_et.getText().toString(),
                                    verification_code_et.getText().toString());
                            break;
                    }

                }

                break;
            case R.id.verification_code:
                if (TextUtils.isEmpty(phone_number.getText())) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));

                } else {
                    mPresenter.getCode(phone_number.getText().toString());
                }
                break;

            case R.id.back_icon:
                finish();
                break;
            case R.id.rl_back2:
                finish();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_and_password;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 1);
        switch (tag) {
            case 1:
                title_tv.setText("手机号注册");
                break;
            case 2:
                title_tv.setText(WordUtil.getString(R.string.Change_password));
                login_page.setVisibility(View.GONE);
                break;
        }


        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verification_code.setText(new SimpleDateFormat("ss", Locale.US).
                        format(new Date(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                verification_code.setText(WordUtil.getString(R.string.Send_code));
                verification_code.setEnabled(true);
            }
        };
    }

    @Override
    protected void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
    }

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
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

    }


    @Override
    public void getCode(BaseResponse<CodeMsg> codeResponse) {
        if (codeResponse.isSuccess()) {
            verification_code.setEnabled(false);
            countDownTimer.start();

        } else {
            ToastUtils.showT(codeResponse.getMsg());

        }
    }

    @Override
    public void userRegist(BaseResponse<UserRegist> regist) {
        if (regist.isSuccess()) {
            //   startActivity(new Intent(this, PhoneLoginActivity.class));
            switch (tag) {
                case 1:
                    MyUserInstance.getInstance().setUserInfo(regist.getData());
                    String temp = JSON.toJSONString(regist.getData());
                    Hawk.put("USER_REGIST", temp);
                    AppManager.getAppManager().startActivity(HomeActivity.class);
                    AppManager.getAppManager().finishAllActivity();
                    ((MyApp) MyApp.getInstance()).imlogin();
                    break;
                case 2:
                    ToastUtils.showT("修改成功,请登录");
                    finish();
                    break;
            }


        } else {
            ToastUtils.showT(regist.getMsg());
        }
    }


    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }
}
