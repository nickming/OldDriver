package com.wennuan.olddriver.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.orhanobut.logger.Logger;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.entity.ReplaceFragmentEvent;
import com.wennuan.olddriver.ui.main.MainActivity;
import com.wennuan.olddriver.ui.main.contact.ContactManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc:
 * Author:
 * Date:
 * Time:23:10
 * E-mail:
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.et_username)
    EditText mUsernameEt;
    @BindView(R.id.et_password)
    EditText mPasswordEt;
    @BindView(R.id.btn_login)
    Button mLoginBtn;
    @BindView(R.id.tv_sign_up_free)
    TextView mSignUpFreeTv;

    private FrameLayout mRootContainer;
    private MaterialDialog mProgressDialog;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRootContainer = (FrameLayout) getActivity().findViewById(R.id.fl_login_container);

        mProgressDialog = new MaterialDialog.Builder(getActivity())
                .title("登录")
                .content("验证中...")
                .progress(true, 0)
                .build();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(mUsernameEt.getText().toString(), mPasswordEt.getText().toString());
            }
        });
        mSignUpFreeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ReplaceFragmentEvent(false));
            }
        });
    }

    private void login(final String username, final String password) {
        if (TextUtils.isEmpty(username)) {
            Snackbar.make(mRootContainer, "账户为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(mRootContainer, "密码为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog.show();
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    AVQuery<AVUser> userAVQuery = AVUser.getQuery();
                    userAVQuery.setLimit(100);
                    userAVQuery.findInBackground(new FindCallback<AVUser>() {
                        @Override
                        public void done(List<AVUser> list, AVException e) {
                            Logger.i("获取用户数量:"+list.size());
                            ContactManager.getInstance().setUserList(list);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                            mProgressDialog.dismiss();
                        }
                    });

                } else {
                    mProgressDialog.dismiss();
                    Snackbar.make(mRootContainer, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }
}
