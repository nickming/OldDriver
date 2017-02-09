package com.wennuan.olddriver.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:16:02
 * E-mail:962570483@qq.com
 */

public class ActivityUtil {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int fragmentId)
    {
        if (fragmentManager==null)
            return;
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(fragmentId,fragment);
        transaction.commit();
    }
}
