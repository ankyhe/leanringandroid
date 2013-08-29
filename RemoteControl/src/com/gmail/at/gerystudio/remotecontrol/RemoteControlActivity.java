package com.gmail.at.gerystudio.remotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.utils.SimpleFragmentContainerActivity;

public class RemoteControlActivity extends SimpleFragmentContainerActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remotecontrol);
    }


    @Override
    protected Fragment createFragment() {
        return new RemoteControlFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_remotecontrol;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
