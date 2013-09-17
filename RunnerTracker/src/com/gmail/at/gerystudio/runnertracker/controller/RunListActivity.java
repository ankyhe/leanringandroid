package com.gmail.at.gerystudio.runnertracker.controller;

import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.runnertracker.R;
import com.gmail.at.gerystudio.runnertracker.utils.SimpleFragmentContainerActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-17
 * Time: PM11:25
 * To change this template use File | Settings | File Templates.
 */
public class RunListActivity extends SimpleFragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_runlist;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
