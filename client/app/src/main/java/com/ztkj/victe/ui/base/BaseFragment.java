package com.ztkj.victe.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * Created by 13526 on 2017/10/28.
 */

public class BaseFragment extends Fragment {

    protected LayoutInflater inflater;
    private View contentView;
    private Context context;
    private ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (contentView != null){
            contentView = null;
        }
        if (container != null){
            container = null;
        }
        if (inflater != null){
            inflater = null;
        }
    }
    public Context getApplicationContext() {
        return context;
    }
    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }
    public void setContentView(View view) {
        contentView = view;
    }
    public View getContentView() {
        return contentView;
    }
    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }


    protected int getStatuesHeight() {
        int statusBarHeight1 = -1;
        int resourceId = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = getActivity().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    protected void setViewTrnslate(ViewGroup viewGroup){
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.height = layoutParams.height + getStatuesHeight();
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.setPadding(0,getStatuesHeight(),0,0);
    }
    protected void setViewTrnslate(View viewGroup){
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.height = layoutParams.height + getStatuesHeight();
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.setPadding(0,getStatuesHeight(),0,0);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    //toast消息
    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
