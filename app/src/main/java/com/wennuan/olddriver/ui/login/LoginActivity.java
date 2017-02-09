package com.wennuan.olddriver.ui.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseActivity;
import com.wennuan.olddriver.entity.ReplaceFragmentEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseActivity {

    private LoginFragment mLoginFragment;
    private SignUpFragment mSignUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        replaceFragment(new ReplaceFragmentEvent(true));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void replaceFragment(ReplaceFragmentEvent replaceFragmentEvent) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (replaceFragmentEvent.isLogin) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (mLoginFragment == null)
                mLoginFragment = LoginFragment.newInstance();
            transaction.replace(R.id.fl_login_container, mLoginFragment);
        } else {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            if (mSignUpFragment == null)
                mSignUpFragment = SignUpFragment.newInstance();
            transaction.replace(R.id.fl_login_container, mSignUpFragment);
        }
        transaction.commit();

    }

}
