package com.gmail.at.gerystudio.drawerview.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: PM8:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleFragmentContainerActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(getActivityLayoutId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentContainerId());
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(getFragmentContainerId(), fragment).commit();
        }
    }

    protected abstract Fragment createFragment();

    protected abstract int getActivityLayoutId();

    protected abstract int getFragmentContainerId();
}
