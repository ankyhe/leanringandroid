package com.gmail.at.ankyhe.photogallery;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.gmail.at.ankyhe.photogallery.controller.DownloadHanderThread;
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
    private DownloadHanderThread<ImageView> downloadThread;

    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        public GalleryItemAdapter(List<GalleryItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }
            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.gallery_item_imageview);
            imageView.setImageResource(R.drawable.placeholder);
            GalleryItem item = getItem(position);
            downloadThread.queue(imageView, item.getUrl());
            return convertView;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
        new FetchItemsTask().execute();
        downloadThread = new DownloadHanderThread<ImageView>(new Handler() {});
        downloadThread.setDownloadListener(new DownloadHanderThread.DownloadListener<ImageView>() {
            @Override
            public void afterDownload(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        downloadThread.start();
        downloadThread.getLooper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photogallery, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView);
        setupAdaptor();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadThread.quit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
        downloadThread.clearQueue();
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
            gridView.setAdapter(new GalleryItemAdapter(items));
        } else {
            gridView.setAdapter(null);
        }
    }

}
