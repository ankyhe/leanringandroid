package com.gmail.at.gerystudio.criminalIntent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.AbsListView;
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


    public interface Callbacks {
        void onCrimeSelected(Crime crime);

    }

    private Callbacks callbacks;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {


            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.crime_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_delete_crime:
                        CrimeArrayAdapter adapter = (CrimeArrayAdapter)getListAdapter();
                        CrimeRepos crimeRepos = CrimeRepos.getInstance(getActivity());
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                crimeRepos.removeCrime(i);
                            }
                        }
                        actionMode.finish();
                        adapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return v;
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
        List<Crime> list = CrimeRepos.getInstance(getActivity()).getCrimeList();
        Crime crime = list.get(position);
        if (callbacks != null) {
            callbacks.onCrimeSelected(crime);
        }
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        callbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.
        callbacks = null;
    }

    public void updateUI() {
        ((CrimeArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }
}
