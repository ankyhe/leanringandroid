package com.gmail.at.gerystudio.criminalIntent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:01
 * To change this template use File | Settings | File Templates.
 */
public class CrimeRepos {

    /* Singleton */
    private CrimeRepos(Context aContext) {
        context = aContext;
        crimes = new ArrayList<Crime>();
        initRepos();
    }
    private static CrimeRepos instance = null;
    public static CrimeRepos getInstance(Context context) {
        if (instance == null) {
            instance = new CrimeRepos(context);
        }
        return instance;
    }
    /* END Singleton */

    private Context context;
    private List<Crime> crimes;

    private void initRepos() {
        // this is just for test
        for (int i = 0 ; i < 100; ++i) {
            Crime crime = new Crime(String.format("Crime %d", i));
            crime.setSolved((i % 2 == 0));
            crimes.add(crime);
        }
    }

    public List<Crime> getCrimeList() {
        return crimes;
    }

}
