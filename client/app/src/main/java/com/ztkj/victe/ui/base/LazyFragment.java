package com.ztkj.victe.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ztkj.victe.R;


/**
 * Created by 13526 on 2017/10/31.
 */

public abstract class LazyFragment extends BaseFragment {
    private boolean isInit = false;//真正要显示的View是否已经被初始化（正常加载）
    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    public static final String INTENT_STRING_TYPE = "intent_String_type";
    private boolean isLazyLoad = true;
    private FrameLayout layout;
    private boolean isStart = false;//是否处于可见状态，in the screen
    private ImageView imageView;
    @Override
    protected  void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }
        //判断是否懒加载
        if (isLazyLoad) {
            //一旦isVisibleToUser==true即可对真正需要的显示内容进行加载
            if (getUserVisibleHint() && !isInit) {
                this.savedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);
                isInit = true;
            } else {
                //进行懒加载
                layout = new FrameLayout(getApplicationContext());
                layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_lazy_loading, null);
                imageView = (ImageView) view.findViewById(R.id.fragment_lazy_loading_iv);
//                Glide.with(getActivity()).load(R.mipmap.loding5).into(imageView);
                layout.addView(view);
                super.setContentView(layout);
            }
        } else {
            //不需要懒加载，开门江山，调用onCreateViewLazy正常加载显示内容即可
            onCreateViewLazy(savedInstanceState);
            isInit = true;
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //一旦isVisibleToUser==true即可进行对真正需要的显示内容的加载

        //可见，但还没被初始化
        if (isVisibleToUser && !isInit && getContentView() != null) {
            onCreateViewLazy(savedInstanceState);
            isInit = true;
            onResumeLazy();
            return;
        }
        //已经被初始化（正常加载）过了
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }

    }
    @Override
    public void setContentView(int layoutResID) {
        //判断若isLazyLoad==true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);

            layout.addView(view);
        }
        //否则，开门见山，直接加载
        else {
            super.setContentView(layoutResID);

        }


    }

    @Override
    public void setContentView(View view) {
        //判断若isLazyLoad==true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        }
        //否则，开门见山，直接加载
        else {
            super.setContentView(view);
        }
    }

    @Deprecated
    @Override
    public  void onStart() {
//        Log.d("TAG", "onStart() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }

    @Deprecated
    @Override
    public  void onStop() {
        super.onStop();
//        Log.d("TAG", "onStop() called: " + "getUserVisibleHint():" + getUserVisibleHint());
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    /**
     * 当Fragment被滑到可见的位置时，调用
     */
    protected abstract void onFragmentStartLazy();
    //当Fragment被滑到可见的位置时，调用
//    protected void onFragmentStartLazy() {
//        Log.d("TAG", "onFragmentStartLazy() called with: " + "");
//    }

    /**
     * Fragment被滑到不可见的位置，offScreen时，调用
     */
    protected abstract void onFragmentStopLazy();
    //当Fragment被滑到不可见的位置，offScreen时，调用
//    protected void onFragmentStopLazy() {
//        Log.d("TAG", "onFragmentStopLazy() called with: " + "");
//    }
    protected abstract void onCreateViewLazy(Bundle savedInstanceState);
//    protected void onCreateViewLazy(Bundle savedInstanceState) {
//        Log.d("TAG", "onCreateViewLazy() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
//    }

    /**
     * 第一次初始化可见时
     */
    protected abstract void onResumeLazy();
//    protected void onResumeLazy() {
//        Log.d("TAG", "onResumeLazy() called with: " + "");
//    }

    protected void onPauseLazy() {
//        Log.d("TAG", "onPauseLazy() called with: " + "");
    }

    /**
     * destroyView
     */
    protected abstract void onDestroyViewLazy();
//    protected void onDestroyViewLazy() {
//
//    }

    @Override
    @Deprecated
    public  void onResume() {
//        Log.d("TAG", "onResume() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onResume();

        if (isInit && getContentView() != null) {
            if (getUserVisibleHint()) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }


//        if (isInit) {
//            onResumeLazy();
//        }
    }

    @Override
    @Deprecated
    public  void onPause() {
//        Log.d("TAG", "onPause() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public  void onDestroyView() {
//        Log.d("TAG", "onDestroyView() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }
}
