package com.gmail.at.gerystudio.drawerview.controller;

import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.drawerview.R;
import com.gmail.at.gerystudio.drawerview.utils.SimpleFragmentContainerActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: AM11:33
 * To change this template use File | Settings | File Templates.
 */
public class DrawActivity extends SimpleFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        return new DrawFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_draw;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
