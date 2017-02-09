package com.wennuan.olddriver.ui.main.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.ContactSectionAdapter;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.ContactSectionEntity;
import com.wennuan.olddriver.ui.main.chat.ChatDetailActivity;
import com.wennuan.olddriver.util.ContactUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc:
 * Author:
 * Date:
 * Time:14:12
 * E-mail:
 */

public class ContactsFragment extends BaseFragment {

    @BindView(R.id.rv_contact)
    RecyclerView mContactRv;

    private View mSearchHeader;
    private RelativeLayout mSearchContainer;

    private ContactSectionAdapter mAdapter;
    private List<ContactSectionEntity> mContactEntities = new ArrayList<>();

    public static ContactsFragment newInstance() {

        Bundle args = new Bundle();

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSearchHeader = LayoutInflater.from(getActivity()).inflate(R.layout.view_search_bar, null, false);
        mSearchContainer = (RelativeLayout) mSearchHeader.findViewById(R.id.rl_search_bar_container);
        mSearchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchUserActivity.class));
            }
        });

        mContactRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new ContactSectionAdapter(mContactEntities);
        mAdapter.addHeaderView(mSearchHeader);
        mContactRv.setAdapter(mAdapter);
        mContactRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Constant.CLIENT_ID = mContactEntities.get(position).t.getmProxy().getObjectId();
                startActivity(new Intent(getActivity(), ChatDetailActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFolloweeContactData();
    }

    /**
     * 获取关注的联系人
     */
    private void requestFolloweeContactData() {
        AVQuery<AVUser> followeeQuery = AVUser.followeeQuery(AVUser.getCurrentUser().getObjectId(), AVUser.class);
        followeeQuery.include("followee");
        followeeQuery.setLimit(300);
        followeeQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                mContactEntities.clear();
                mContactEntities.addAll(ContactUtil.convertAVUserListToSectionList(list));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public String getFragmentName() {
        return "通讯录";
    }
}
