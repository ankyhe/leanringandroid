package com.gmail.at.ankyhe.photogallery;

import android.support.v4.app.Fragment;
import com.gmail.at.ankyhe.photogallery.utils.SimpleFragmentContainerActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-9
 * Time: PM10:04
 * To change this template use File | Settings | File Templates.
 */
public class PhotoViewActivity extends SimpleFragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        return new PhotoViewFragment();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_photoview;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }
}
