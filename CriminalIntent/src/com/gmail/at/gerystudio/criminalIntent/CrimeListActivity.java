package com.gmail.at.gerystudio.criminalIntent;

import android.support.v4.app.Fragment;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:33
 * To change this template use File | Settings | File Templates.
 */
public class CrimeListActivity extends SimpleFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
