package com.nasinet.live.interfaces;

import com.nasinet.live.model.entity.RankAnchorItem;
import com.nasinet.live.model.entity.RankItem;

public interface OnFollowClickListener {
    void onFollowAnchorClick(RankAnchorItem live);
    void onAnchorAvatarClick(RankItem live);
    void onFollowClick(RankItem live);
    void onAvatarClick(RankItem live);
}
