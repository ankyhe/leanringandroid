package com.gmail.at.gerystudio.mytvlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.utils.SimpleFragmentContainerActivity;

public class MyTVLauncherActivity extends SimpleFragmentContainerActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytvlauncher);
    }

    @Override
    protected Fragment createFragment() {
        return new MyTVLauncherFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_mytvlauncher;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
