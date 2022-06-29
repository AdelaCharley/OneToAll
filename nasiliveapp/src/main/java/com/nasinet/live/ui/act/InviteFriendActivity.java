package com.nasinet.live.ui.act;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nasinet.live.R;
import com.nasinet.live.base.OthrBase2Activity;
import com.nasinet.live.util.HttpUtils;
import com.nasinet.live.dialog.ShareDialog;
import com.nasinet.live.util.MyUserInstance;
//邀请好友
public class InviteFriendActivity extends OthrBase2Activity implements View.OnClickListener {
    TextView tv_invite, tv_withdrawal, tv_coin,tv_4;
    ImageView iv_invite, iv_income;
    String url = "";

    @Override
    protected int getLayoutId() {
        return R.layout.invite_activity;
    }

    @Override
    protected void initData() {
        initView();
        HttpUtils.getInstance().getAgentInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject result = HttpUtils.getInstance().check(response);
                if( HttpUtils.getInstance().swtichStatus(result)){
                    url = result.getJSONObject("data").getString("invite_url");
                    tv_coin.setText(result.getJSONObject("data").getString("profit"));
                }
            }
        });
    }

    private void initView() {
        setTitle("邀请好友");
        tv_invite = findViewById(R.id.tv_invite);
        tv_coin = findViewById(R.id.tv_coin);
        tv_withdrawal = findViewById(R.id.tv_withdrawal);
        iv_invite = findViewById(R.id.iv_invite);
        iv_income = findViewById(R.id.iv_income);
        tv_4=findViewById(R.id.tv_4);


        tv_4.setText("3.被邀请的用户充值，可获得分成比例为"+ MyUserInstance.getInstance().getUserConfig().getConfig().getAgent_ratio() +"%");
        tv_withdrawal.setOnClickListener(this);
        iv_income.setOnClickListener(this);
        iv_invite.setOnClickListener(this);
        tv_invite.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getInstance().getAgentInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject result = HttpUtils.getInstance().check(response);
                if( HttpUtils.getInstance().swtichStatus(result)){
                    url = result.getJSONObject("data").getString("invite_url");
                    tv_coin.setText(result.getJSONObject("data").getString("profit"));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_withdrawal:
                Intent i_withdrawal = new Intent(InviteFriendActivity.this, InviteHistoryActivity.class);
                i_withdrawal.putExtra("coin",tv_coin.getText().toString());
                startActivity(i_withdrawal);
                break;
            case R.id.iv_income:
                startActivity(new Intent(InviteFriendActivity.this,IncomeAgentActivity.class));
                break;
            case R.id.iv_invite:
                startActivity(new Intent(InviteFriendActivity.this,InviteHistoryAgentActivity.class));
                break;
            case R.id.tv_invite:
                ShareDialog.Builder builder = new ShareDialog.Builder(InviteFriendActivity.this);
                builder.setShare_url(url);
                builder.create().show();
                break;
        }
    }
}
