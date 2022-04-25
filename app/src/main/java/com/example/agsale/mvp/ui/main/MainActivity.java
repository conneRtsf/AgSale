package com.example.agsale.mvp.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;

public class MainActivity extends BaseActivity {
    private Fragment ShareFragment;

    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        if (ShareFragment == null) {
            ShareFragment = new ShareFragment();
            mTransaction.add(R.id.container, ShareFragment,
                    "clothes_fragment");
        } else {
            mTransaction.show(ShareFragment);
        }
        mTransaction.commit();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
}
