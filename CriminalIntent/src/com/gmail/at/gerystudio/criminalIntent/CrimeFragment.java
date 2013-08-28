package com.gmail.at.gerystudio.criminalIntent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;
import com.gmail.at.gerystudio.criminalIntent.model.Crime;
import com.gmail.at.gerystudio.criminalIntent.model.CrimeRepos;
import com.gmail.at.gerystudio.criminalIntent.model.Photo;
import com.gmail.at.gerystudio.criminalIntent.utils.PhotoUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: PM8:28
 * To change this template use File | Settings | File Templates.
 */
public class CrimeFragment extends Fragment {
    private Crime crime;

    private ImageView photoView;
    private Button suspectButton;

    private static final String LOG_TAG = CrimeFragment.class.getName();

    public interface Callbacks {
        void onCrimeUpdated(Crime crime);
    }

    private Callbacks callbacks;

    public static CrimeFragment newInstance(UUID uuid) {
        CrimeFragment crimeFragment = new CrimeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_UUID, uuid);
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        UUID uuid = (UUID) bundle.getSerializable(Constants.PARAM_UUID);
        Crime aCrime = CrimeRepos.getInstance(getActivity()).getCrimeByUUID(uuid);
        if (aCrime != null) {
            crime = aCrime;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        EditText titleEditText = (EditText)view.findViewById(R.id.crime_title);
        titleEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (crime != null) {
                    crime.setTitle(editable.toString());
                }
            }
        });

        if (crime != null) {
            titleEditText.setText(crime.getTitle());
            Button datetimeButton = (Button)view.findViewById(R.id.datetime_button);
            datetimeButton.setText(crime.getDatetimeStr());
            datetimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DatePickDialogFragment fragment;
                    fragment = DatePickDialogFragment.newInstance(new Date(crime.getDatetime()));
                    fragment.setTargetFragment(CrimeFragment.this, Constants.REQUESTCODE_DATE);
                    fragment.show(fm, "date");
                }
            });
            CheckBox solvedCheckBox = (CheckBox)view.findViewById(R.id.solved_checkbox);
            solvedCheckBox.setChecked(crime.isSolved());
            solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d(LOG_TAG, "ENTER checkbox changes");
                    crime.setSolved(b);
                    if (callbacks != null) {
                        callbacks.onCrimeUpdated(crime);
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "crime is null.");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        ImageButton takePhotoButton = (ImageButton)view.findViewById(R.id.take_photo_button);
        if (takePhotoButton != null) {
            takePhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CrimeCamerActivity.class);
                    startActivityForResult(intent, Constants.REQUESTCODE_PHOTO);
                }
            });
        }

        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            if (takePhotoButton != null) {
                takePhotoButton.setEnabled(false);
            }
        }

        photoView = (ImageView)view.findViewById(R.id.photo);


        Button sendButton = (Button)view.findViewById(R.id.sendmessage_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, crime.toReport());
                intent = Intent.createChooser(intent, getString(R.string.send_report));
                startActivity(intent);
            }
        });

        suspectButton = (Button)view.findViewById(R.id.suspect_button);
        suspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, Constants.REQUESTCODE_CONTACT);

            }
        });

        String title = crime.getSuspect() == null ? getString(R.string.suspect) : crime.getSuspect();
        suspectButton.setText(title);
        if (callbacks != null) {
            callbacks.onCrimeUpdated(crime);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUESTCODE_DATE) {
            if (resultCode == Activity.RESULT_OK) {
                Date date = (Date) data.getSerializableExtra(Constants.PARAM_DATE);
                crime.setDatetime(date);
                Button datetimeButton = (Button)getView().findViewById(R.id.datetime_button);
                datetimeButton.setText(crime.getDatetimeStr());
                if (callbacks != null) {
                    callbacks.onCrimeUpdated(crime);
                }
            }
        } else if (requestCode == Constants.REQUESTCODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                String filename  = (String)data.getSerializableExtra(Constants.PARAM_PHOTO);
                Photo photo = new Photo(filename);
                crime.setPhoto(photo);
            }
        } else if (requestCode == Constants.REQUESTCODE_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactUri = data.getData();

                // Specify which fields you want your query to return
                // values for.
                String[] queryFields = new String[] {
                        ContactsContract.Contacts.DISPLAY_NAME
                };
                // Perform your query - the contactUri is like a "where"
                // clause here
                Cursor c = getActivity().getContentResolver()
                        .query(contactUri, queryFields, null, null, null);

                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    c.close();
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your suspect's name.
                c.moveToFirst();
                String suspect = c.getString(0);
                crime.setSuspect(suspect);
                suspectButton.setText(suspect);
                if (callbacks != null) {
                    callbacks.onCrimeUpdated(crime);
                }
                c.close();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                NavUtils.navigateUpFromSameTask(getActivity());
                getActivity().overridePendingTransition(R.anim.right_slide_out, R.anim.right_slide_out);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        CrimeRepos.getInstance(getActivity()).saveCrimeRepos();
    }

    @Override
    public void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        PhotoUtils.cleanImageView(photoView);
    }

    @Override
    public void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
        showPhoto();
    }

    private void showPhoto() {
        // (Re)set the image button's image based on our photo
        Photo p = crime.getPhoto();

        if (p != null) {
            String path = getActivity()
                    .getFileStreamPath(p.getFileName()).getAbsolutePath();
            BitmapDrawable b = PhotoUtils.getScaledDrawable(getActivity(), path);
            photoView.setImageDrawable(b);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}
