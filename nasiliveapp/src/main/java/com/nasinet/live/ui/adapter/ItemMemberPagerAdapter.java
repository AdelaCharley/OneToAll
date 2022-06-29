package com.nasinet.live.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ItemMemberPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    List<String> title;

    public ItemMemberPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        this.fragments = fragments;
        this.title = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
