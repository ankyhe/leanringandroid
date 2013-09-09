package com.gmail.at.ankyhe.photogallery;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.gmail.at.ankyhe.photogallery.controller.DownloadHanderThread;
import com.gmail.at.ankyhe.photogallery.controller.FlickrFetcher;
import com.gmail.at.ankyhe.photogallery.model.Constants;
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
public class PhotoGalleryFragment extends VisibleFragment {

    private static final String LOG_TAG = PhotoGalleryFragment.class.getName();

    private LruCache<String, Bitmap> imageCache;

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

            GalleryItem item = getItem(position);

            Bitmap bitmap = imageCache.get(item.getUrl());
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.placeholder);
                downloadThread.queue(imageView, item.getUrl());
            }
            return convertView;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setHasOptionsMenu(true);
        setRetainInstance(true);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        updateItems();
        PollService.setServiceAlarm(getActivity(), true);
        downloadThread = new DownloadHanderThread<ImageView>(new Handler() {});
        downloadThread.setDownloadListener(new DownloadHanderThread.DownloadListener<ImageView>() {
            @Override
            public void afterDownload(ImageView imageView, String url, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
                imageCache.put(url, thumbnail);
            }
        });
        downloadThread.start();
        downloadThread.getLooper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photogallery, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                GalleryItem item = items.get(pos);
                Uri photoPageUri = Uri.parse(item.getPhotoPageUrl());
                Intent i = new Intent(getActivity(), PhotoViewActivity.class);
                i.setData(photoPageUri);
                startActivity(i);
            }
        });
        setupAdaptor();
        return v;
    }

    @Override
    @TargetApi(11)
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Pull out the SearchView
            MenuItem searchItem = menu.findItem(R.id.menu_item_search);
            SearchView searchView = (SearchView)searchItem.getActionView();

            // Get the data from our searchable.xml as a SearchableInfo
            SearchManager searchManager = (SearchManager)getActivity()
                    .getSystemService(Context.SEARCH_SERVICE);
            ComponentName name = getActivity().getComponentName();
            SearchableInfo searchInfo = searchManager.getSearchableInfo(name);
            searchView.setSearchableInfo(searchInfo);

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(
                            Constants.PREF_SEARCH_QUERY, ""
                    ).commit();
                    updateItems();
                    return false;
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                Log.d(LOG_TAG, "clear");
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(
                        Constants.PREF_SEARCH_QUERY, ""
                ).commit();
                updateItems();
                return true;
            case R.id.menu_item_toggle_polling:
                boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
                PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getActivity().invalidateOptionsMenu();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
        if (PollService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.stop_polling);
        } else {
            toggleItem.setTitle(R.string.start_polling);
        }
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
            Activity activity = getActivity();
            if (activity == null)
                return new ArrayList<GalleryItem>();

            String query = PreferenceManager.getDefaultSharedPreferences(activity)
                    .getString(Constants.PREF_SEARCH_QUERY, null);
            if (query != null && !query.equals("")) {
                Log.i(LOG_TAG, "search");
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(
                        Constants.PREF_SEARCH_QUERY, ""
                ).commit();
                return new FlickrFetcher().search(query);
            } else {
                return new FlickrFetcher().fetchItems();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> theItems) {
            items = theItems;
            setupAdaptor();
        }
    }



    public void updateItems() {
        new FetchItemsTask().execute();
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
