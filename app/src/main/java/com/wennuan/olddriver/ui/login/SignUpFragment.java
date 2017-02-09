package com.wennuan.olddriver.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.orhanobut.logger.Logger;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.HeadChoiceAdapter;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.HeadChoiceEntity;
import com.wennuan.olddriver.entity.ReplaceFragmentEvent;
import com.wennuan.olddriver.ui.main.MainActivity;
import com.wennuan.olddriver.ui.main.contact.ContactManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

public class SignUpFragment extends BaseFragment {

    @BindView(R.id.et_sign_username)
    EditText mSignUsername;
    @BindView(R.id.et_sign_phone)
    EditText mSignPhone;
    @BindView(R.id.et_sign_password)
    EditText mSignPassword;
    @BindView(R.id.et_sign_password_again)
    EditText mSignPasswordAgain;
    @BindView(R.id.btn_sin_up_complete)
    Button mSinUpCompleteBtn;
    @BindView(R.id.btn_sign_up_back)
    Button mSignUpBackBtn;
    @BindView(R.id.btn_sin_up_choice_head)
    Button mSinUpChoiceHeadBtn;

    private int mCurrentHeadType = HeadChoiceEntity.TYPE_1;

    private View mRootView;
    private MaterialDialog mProgressDialog;
    private HeadChoiceAdapter choiceAdapter;
    List<HeadChoiceEntity> headChoiceEntities = new ArrayList<>();

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 1; i < 5; i++) {
            headChoiceEntities.add(new HeadChoiceEntity(i));
        }
        choiceAdapter = new HeadChoiceAdapter(headChoiceEntities);
        choiceAdapter.setListener(new HeadChoiceAdapter.SelectChangeListener() {
            @Override
            public void select(int index) {
                for (HeadChoiceEntity entity : headChoiceEntities) {
                    entity.setSelect(false);
                }
                headChoiceEntities.get(index).setSelect(true);
                mCurrentHeadType = headChoiceEntities.get(index).getType();
                Logger.i("选中头像为:" + mCurrentHeadType);
            }
        });

        headChoiceEntities.get(0).setSelect(true);

        mProgressDialog = new MaterialDialog.Builder(getActivity())
                .title("注册")
                .content("验证中...")
                .progress(true, 0)
                .build();

        mSinUpCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        mSignUpBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ReplaceFragmentEvent(true));
            }
        });

        mSinUpChoiceHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceHead();
            }
        });

    }

    private void choiceHead() {

        new MaterialDialog.Builder(getActivity())
                .title("选择头像")
                .adapter(choiceAdapter, new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false))
                .positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void signUp() {
        final String username = mSignUsername.getText().toString();
        String phone = mSignPhone.getText().toString();
        final String password = mSignPassword.getText().toString();
        String passwordConfirm = mSignPasswordAgain.getText().toString();
        if (judgeStringIsNull(username) && judgeStringIsNull(password) && judgeStringIsNull(phone)
                && judgeStringIsNull(passwordConfirm)) {

            if (!password.equals(passwordConfirm)) {
                showSnackBar("两次密码不同");
                return;
            }

            mProgressDialog.show();
            AVUser user = new AVUser();
            user.setUsername(username);
            user.setMobilePhoneNumber(phone);
            user.setPassword(password);
            user.put(Constant.HEAD, mCurrentHeadType);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        showSnackBar("注册成功!");
                        //登陆成功后还要获取全部用户的信息
                        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {

                                AVQuery<AVUser> userAVQuery = AVUser.getQuery();
                                userAVQuery.setLimit(100);
                                userAVQuery.findInBackground(new FindCallback<AVUser>() {
                                    @Override
                                    public void done(List<AVUser> list, AVException e) {
                                        Logger.i("获取用户数量:" + list.size());
                                        ContactManager.getInstance().setUserList(list);
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                        mProgressDialog.dismiss();
                                    }
                                });
                            }
                        });

                    } else {
                        showSnackBar(e.getMessage());
                        mProgressDialog.dismiss();
                    }

                }
            });
        } else {
            showSnackBar("存在空值");
            return;
        }

    }

    private boolean judgeStringIsNull(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        } else {
            return true;
        }
    }

    private void showSnackBar(String content) {
        Snackbar.make(mRootView, content, Snackbar.LENGTH_SHORT).show();
    }
}
