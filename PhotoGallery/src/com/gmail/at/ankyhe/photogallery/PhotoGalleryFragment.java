package com.gmail.at.ankyhe.photogallery;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.gmail.at.ankyhe.photogallery.controller.FlickrFetcher;
import com.gmail.at.ankyhe.photogallery.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/30/13
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String LOG_TAG = PhotoGalleryFragment.class.getName();

    private GridView gridView;
    private List<GalleryItem> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photogallery, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView);
        setupAdaptor();
        return v;
    }


    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            return new FlickrFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> theItems) {
            items = theItems;
            setupAdaptor();
        }
    }

    private void setupAdaptor() {
        if (getActivity() == null || gridView == null) return;

        if (items != null) {
            gridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
                    android.R.layout.simple_gallery_item, items));
        } else {
            gridView.setAdapter(null);
        }
    }
}
