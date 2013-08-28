package com.gmail.at.gerystudio.mytvlauncher;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/28/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyTVLauncherFragment extends ListFragment {

    private static final String LOG_TAG = MyTVLauncherFragment.class.getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        prepareData();


    }

    private void prepareData() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Log.i(LOG_TAG, "I've found " + activities.size() + " activities.");

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(pm).toString(),
                        b.loadLabel(pm).toString());
            }
        });

        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(
                getActivity(), R.layout.list_launcher_item, activities) {
            public View getView(int pos, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_launcher_item, null);
                }
                TextView titleTextView = (TextView)convertView.findViewById(R.id.launcher_title);
                ResolveInfo ri = getItem(pos);
                titleTextView.setText(ri.loadLabel(pm));

                ImageView iconImageView = (ImageView)convertView.findViewById(R.id.launcher_icon);
                iconImageView.setImageDrawable(ri.loadIcon(pm));

                return convertView;
            }
        };
        setListAdapter(adapter);


    }

}
