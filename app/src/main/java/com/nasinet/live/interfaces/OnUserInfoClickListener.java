package com.nasinet.live.interfaces;

import com.nasinet.live.model.entity.RankAnchorItem;
import com.nasinet.live.model.entity.RankItem;

public interface OnUserInfoClickListener {

    void onAnchorAvatarClick(RankAnchorItem live);

    void onAvatarClick(RankItem live);
}
