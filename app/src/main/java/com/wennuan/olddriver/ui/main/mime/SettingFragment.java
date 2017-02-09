package com.wennuan.olddriver.ui.main.mime;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wennuan.olddriver.R;

/**
 * Desc:
 * Author:
 * Date:
 * Time:17:16
 * E-mail:
 */

public class SettingFragment extends PreferenceFragment {

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
    }
}
