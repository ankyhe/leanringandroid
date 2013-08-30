package com.gmail.at.ankyhe.photogallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.gmail.at.ankyhe.photogallery.utils.SimpleFragmentContainerActivity;

public class PhotoGalleryActivity extends SimpleFragmentContainerActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photogallery);
    }

    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_photogallery;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragementContainer;
    }


}
