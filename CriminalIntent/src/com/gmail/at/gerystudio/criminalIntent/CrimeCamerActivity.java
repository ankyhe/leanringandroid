package com.gmail.at.gerystudio.criminalIntent;

import android.support.v4.app.Fragment;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/27/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CrimeCamerActivity extends SimpleFragmentContainerActivity {

    protected Fragment createFragment() {
        return new CrimeCamerFragment();
    }
}
