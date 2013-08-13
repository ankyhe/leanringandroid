package com.gmail.at.gerystudio.criminalIntent;

import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;
import com.gmail.at.gerystudio.criminalIntent.model.CrimeRepos;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:33
 * To change this template use File | Settings | File Templates.
 */
public class CrimePageActivity extends FragmentActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        final List<Crime> crimes = CrimeRepos.getInstance(this).getCrimeList();
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                Crime crime = crimes.get(i);
                return CrimeFragment.newInstance(crime.getUuid());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        UUID uuid = (UUID) getIntent().getSerializableExtra(Constants.PARAM_UUID);
        int idx = CrimeRepos.getInstance(this).getIndexByUUID(uuid);
        if (idx != -1) {
            viewPager.setCurrentItem(idx);
        }
    }
}
