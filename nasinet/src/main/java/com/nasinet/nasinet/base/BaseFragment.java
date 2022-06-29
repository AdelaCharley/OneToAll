package com.nasinet.nasinet.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unBinder;
    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view!=null){
            ViewGroup parent =(ViewGroup)view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
           return view;
        }
        view = inflater.inflate(this.getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        initView(view);

        Log.e("LIFE","onCreateView");
        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }

    }

    /**
     * 初始化视图
     *
     * @param view
     */
    protected abstract void initView(View view);

    protected abstract int getLayoutId();
}
