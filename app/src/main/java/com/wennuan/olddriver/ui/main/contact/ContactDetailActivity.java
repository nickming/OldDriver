package com.wennuan.olddriver.ui.main.contact;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.orhanobut.logger.Logger;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseActivity;
import com.wennuan.olddriver.base.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailActivity extends BaseActivity {

    @BindView(R.id.tv_contact_detail_title)
    TextView tvContactDetailTitle;
    @BindView(R.id.btn_contact_detail_back)
    Button btnContactDetailBack;
    @BindView(R.id.tv_contact_detail_username)
    TextView tvContactDetailUsername;
    @BindView(R.id.tv_contact_detail_phone)
    TextView tvContactDetailPhone;
    @BindView(R.id.btn_contact_detail_add)
    Button btnContactDetailAdd;
    @BindView(R.id.activity_contact_detail)
    LinearLayout activityContactDetail;

    private AVUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);


        if (getIntent() != null) {
            mCurrentUser = getIntent().getParcelableExtra(Constant.SEARCH_USER_INFO);
        }

        tvContactDetailTitle.setText("详情");
        tvContactDetailPhone.setText(mCurrentUser.getMobilePhoneNumber());
        tvContactDetailUsername.setText(mCurrentUser.getUsername());

        btnContactDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContactDetailAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i(mCurrentUser.getObjectId());
                if (mCurrentUser.getObjectId().equals(AVUser.getCurrentUser().getObjectId())) {
                    Snackbar.make(activityContactDetail, "不要太关注自己!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                AVUser.getCurrentUser().followInBackground(mCurrentUser.getObjectId(), new FollowCallback() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        Snackbar.make(activityContactDetail, "关注成功!", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void internalDone0(Object o, AVException e) {
                        Snackbar.make(activityContactDetail, "关注成功!", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
