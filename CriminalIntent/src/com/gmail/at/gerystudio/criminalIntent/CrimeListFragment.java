package com.gmail.at.gerystudio.criminalIntent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        ArrayAdapter<Crime> adapter = new CrimeArrayAdapter(CrimeRepos.getInstance(getActivity()).getCrimeList(),
                getActivity());
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);    //To change body of overridden methods use File | Settings | File Templates.
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        ((CrimeArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), CrimePageActivity.class);
        List<Crime> list = CrimeRepos.getInstance(getActivity()).getCrimeList();
        intent.putExtra(Constants.PARAM_UUID, list.get(position).getUuid());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_crime) {
            Crime crime = CrimeRepos.getInstance(getActivity()).newCrime();
            Intent intent = new Intent(getActivity(), CrimePageActivity.class);
            intent.putExtra(Constants.PARAM_UUID, crime.getUuid());
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
