package com.wennuan.olddriver.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:14:40
 * E-mail:962570483@qq.com
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getFragmentName() {
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
