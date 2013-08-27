package com.gmail.at.gerystudio.criminalIntent.model;

import android.content.Context;

import java.util.List;
import java.util.UUID;

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
        CrimeReposSerializer serializer = new CrimeReposSerializer(context, "crimeRepos.json");
        crimes = serializer.loadCrimes();
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

    public Crime newCrime() {
        Crime crime = new Crime();
        crimes.add(crime);
        return crime;
    }

    public List<Crime> getCrimeList() {
        return crimes;
    }

    public Crime getCrimeByUUID(UUID uuid) {
        for (Crime crime : crimes) {
            if (crime.getUuid().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }

    public int getIndexByUUID(UUID uuid) {
        for (int i = 0; i < crimes.size(); ++i) {
            if (crimes.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void removeCrime(int idx) {
        crimes.remove(idx);
    }

    public void initRepos() {
        for (int i = 0 ; i < 5; ++i) {
            Crime crime = new Crime(String.format("Crime %d", i+1));
            crimes.add(crime);
        }
    }

    public void saveCrimeRepos() {
        CrimeReposSerializer serializer = new CrimeReposSerializer(context, "crimeRepos.json");
        serializer.saveCrimes(crimes);
    }

}
