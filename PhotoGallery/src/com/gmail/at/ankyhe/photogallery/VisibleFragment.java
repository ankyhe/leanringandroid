package com.gmail.at.ankyhe.photogallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.gmail.at.ankyhe.photogallery.model.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-9-7
 * Time: PM12:40
 * To change this template use File | Settings | File Templates.
 */
public class VisibleFragment extends Fragment {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("BroadcastReceiver", "cancel notification!");
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        IntentFilter filter = new IntentFilter(Constants.SHOW_NEWPICTURE_NOTIFICATION);
        getActivity().registerReceiver(receiver, filter, Constants.PERM_PRIVATE, null);
    }

    @Override
    public void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        getActivity().unregisterReceiver(receiver);
    }
}
