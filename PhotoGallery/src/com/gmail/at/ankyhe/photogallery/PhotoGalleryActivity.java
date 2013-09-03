package com.gmail.at.ankyhe.photogallery;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.gmail.at.ankyhe.photogallery.model.Constants;
import com.gmail.at.ankyhe.photogallery.utils.SimpleFragmentContainerActivity;

public class PhotoGalleryActivity extends SimpleFragmentContainerActivity {

    private static final String LOG_TAG = PhotoGalleryActivity.class.getName();

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

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(LOG_TAG, "onNewIntent");
        PhotoGalleryFragment fragment = (PhotoGalleryFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragementContainer);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(LOG_TAG, "Received a new search query: " + query);
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putString(Constants.PREF_SEARCH_QUERY, query)
                    .commit();
        }
        fragment.updateItems();
    }

}
