package com.wennuan.olddriver.ui.main.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.SearchAdapter;
import com.wennuan.olddriver.base.BaseActivity;
import com.wennuan.olddriver.base.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText mSearchEt;
    @BindView(R.id.tv_search_cancel)
    TextView mSearchCancelTv;
    @BindView(R.id.rv_search)
    RecyclerView mSearchRv;

    private SearchAdapter mAdapter;
    private List<AVUser> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initListener();

        mSearchRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new SearchAdapter(mUserList);
        mAdapter.setEmptyView(R.layout.view_empty, mSearchRv);
        mSearchRv.setAdapter(mAdapter);

        mSearchRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchUserActivity.this, ContactDetailActivity.class);
                intent.putExtra(Constant.SEARCH_USER_INFO, mUserList.get(position));
                startActivity(intent);
            }
        });

        mSearchCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    AVQuery<AVUser> userAVQuery = AVUser.getUserQuery(AVUser.class);
                    userAVQuery.setLimit(100);
                    userAVQuery.whereContains("username", s.toString());
                    userAVQuery.findInBackground(new FindCallback<AVUser>() {
                        @Override
                        public void done(List<AVUser> list, AVException e) {
                            mUserList.clear();
                            mUserList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    mUserList.clear();
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
