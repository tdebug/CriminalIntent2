package com.tdebug.criminalintent;


import android.support.v4.app.Fragment;

/**
 * Created by tdebug on 04/12/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
