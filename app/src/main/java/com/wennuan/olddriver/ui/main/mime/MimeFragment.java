package com.wennuan.olddriver.ui.main.mime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.entity.ActivityCloseEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Desc:
 * Author:
 * Date:
 * Time:14:12
 * E-mail:
 */

public class MimeFragment extends BaseFragment {

    @BindView(R.id.iv_mime_username)
    ImageView ivMimeUsername;
    @BindView(R.id.tv_mime_username)
    TextView tvMimeUsername;
    @BindView(R.id.tv_mime_phone)
    TextView tvMimePhone;
    @BindView(R.id.rl_mime_salary_count)
    RelativeLayout rlMimeSalaryCount;
    @BindView(R.id.rl_mime_collect)
    RelativeLayout rlMimeCollect;
    @BindView(R.id.rl_mime_setting)
    RelativeLayout rlMimeSetting;
    @BindView(R.id.rl_mime_exit)
    RelativeLayout rlMimeExit;

    public static MimeFragment newInstance() {

        Bundle args = new Bundle();

        MimeFragment fragment = new MimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mime, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivMimeUsername.setImageResource(R.mipmap.head1);
        tvMimeUsername.setText(AVUser.getCurrentUser().getUsername());
        tvMimePhone.setText(AVUser.getCurrentUser().getMobilePhoneNumber());
    }

    @Override
    public String getFragmentName() {
        return "我";
    }

    @OnClick({R.id.rl_mime_salary_count, R.id.rl_mime_collect, R.id.rl_mime_setting, R.id.rl_mime_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mime_salary_count:
                startActivity(new Intent(getActivity(), SalaryCountActivty.class));
                break;
            case R.id.rl_mime_collect:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.rl_mime_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.rl_mime_exit:
                EventBus.getDefault().post(new ActivityCloseEvent(true));
                Process.killProcess(Process.myPid());
                break;
        }
    }
}
