package com.gmail.at.gerystudio.criminalIntent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;
import com.gmail.at.gerystudio.criminalIntent.model.CrimeRepos;
import com.gmail.at.gerystudio.criminalIntent.view.CrimeArrayAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:10
 * To change this template use File | Settings | File Templates.
 */
public class CrimeListFragment extends ListFragment {

    private static final String LOG_TAG = CrimeListFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        getActivity().setTitle(R.string.crimes_title);
        ArrayAdapter<Crime> adapter = new CrimeArrayAdapter(CrimeRepos.getInstance(getActivity()).getCrimeList(),
                getActivity());
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        ((CrimeArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), CrimeActivity.class);
        List<Crime> list = CrimeRepos.getInstance(getActivity()).getCrimeList();
        intent.putExtra(Constants.PARAM_UUID, list.get(position).getUuid());
        startActivity(intent);
    }
}
