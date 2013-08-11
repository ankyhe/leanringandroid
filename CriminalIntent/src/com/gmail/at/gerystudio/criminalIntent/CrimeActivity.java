package com.gmail.at.gerystudio.criminalIntent;

import android.support.v4.app.Fragment;
import com.gmail.at.gerystudio.criminalIntent.model.Constants;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-11
 * Time: AM12:33
 * To change this template use File | Settings | File Templates.
 */
public class CrimeActivity extends SimpleFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(Constants.PARAM_UUID);
        return CrimeFragment.newInstance(uuid);
    }
}
