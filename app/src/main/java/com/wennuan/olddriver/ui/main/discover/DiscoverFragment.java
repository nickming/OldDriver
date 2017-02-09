package com.wennuan.olddriver.ui.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVStatusQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.InboxStatusFindCallback;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.DiscoverAdapter;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.entity.DiscoverEntity;

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

public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.rv_discover)
    RecyclerView mDiscoverRv;

    private List<DiscoverEntity> mDiscoverEntities=  new ArrayList<>();
    private DiscoverAdapter mAdapter;

    public static DiscoverFragment newInstance() {

        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDiscoverRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter=new DiscoverAdapter(mDiscoverEntities);
        mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.view_discover_header,null,false));
        mDiscoverRv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestStatusData();
    }

    private void requestStatusData()
    {
        AVStatusQuery inboxQuery = AVStatus.inboxQuery(AVUser.getCurrentUser(),AVStatus.INBOX_TYPE.TIMELINE.toString());
        inboxQuery.setLimit(100);  //设置最多返回 50 条状态
        inboxQuery.setSinceId(0);  //查询返回的 status 的 messageId 必须大于 sinceId，默认为 0
        inboxQuery.findInBackground(new InboxStatusFindCallback(){
            @Override
            public void done(final List<AVStatus> avObjects, final AVException avException) {
                mDiscoverEntities.clear();
                for (AVStatus status : avObjects)
                {
                    mDiscoverEntities.add(new DiscoverEntity(status));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public String getFragmentName() {
        return "发现";
    }
}
