package com.wennuan.olddriver.ui.main.mime;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SettingFragment settingFragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.fl_setting_container);
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_setting_container, settingFragment);
            fragmentTransaction.commit();
        }
    }

    public void back(View view)
    {
        finish();
    }
}
