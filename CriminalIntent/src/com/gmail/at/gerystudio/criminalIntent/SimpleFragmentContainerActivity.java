package com.gmail.at.gerystudio.criminalIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: PM8:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleFragmentContainerActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment crimeFragment = fm.findFragmentById(R.id.fragmentContainer);
        if (crimeFragment == null) {
            crimeFragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, crimeFragment).commit();
        }
    }

    protected abstract Fragment createFragment();
}
