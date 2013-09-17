package com.gmail.at.gerystudio.runnertracker.controller;

import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.runnertracker.R;
import com.gmail.at.gerystudio.runnertracker.utils.SimpleFragmentContainerActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-14
 * Time: PM6:06
 * To change this template use File | Settings | File Templates.
 */
public class RunnerTrackerActivity extends SimpleFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        return new RunnerTrackerFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_runnertracker;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
