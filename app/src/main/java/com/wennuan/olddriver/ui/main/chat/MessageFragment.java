package com.wennuan.olddriver.ui.main.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.MessageAdapter;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.MessageEntity;
import com.wennuan.olddriver.ui.main.contact.SearchUserActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc:
 * Author:nickming
 * Date:2017/2/6
 * Time:11:11
 * E-mail:962570483@qq.com
 */

public class MessageFragment extends BaseFragment {

    private static final String TAG = "MessageFragment";

    @BindView(R.id.rv_message)
    RecyclerView mMessageRv;


    private View mSearchHeader;
    private RelativeLayout mSearchContainer;

    private List<MessageEntity> mMessageEntities = new ArrayList<>();
    private MessageAdapter mMessageAdapter;

    private volatile AVIMMessageHandler mMessageHandler = new AVIMMessageHandler() {
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if (message instanceof AVIMTextMessage) {
//                Log.i(TAG, "onMessage: "+((AVIMTextMessage) message).getText());
//                EventBus.getDefault().post(new BadgesEvent(1));
                for (MessageEntity entity : mMessageEntities) {
                    if (entity.getLastConversation().getConversationId().equals(conversation.getConversationId())) {
                        entity.setBadgets((entity.getBadgets() + 1));
                        break;
                    }
                }
                mMessageAdapter.notifyDataSetChanged();
            }
        }
    };

    public static MessageFragment newInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public String getFragmentName() {
        return "信息";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, mMessageHandler);

        mSearchHeader = LayoutInflater.from(getActivity()).inflate(R.layout.view_search_bar, null, false);
        mSearchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchUserActivity.class));
            }
        });
        mSearchContainer = (RelativeLayout) mSearchHeader.findViewById(R.id.rl_search_bar_container);
        mMessageAdapter = new MessageAdapter(mMessageEntities);
        mMessageRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mMessageAdapter.addHeaderView(mSearchHeader);
        mMessageAdapter.setEmptyView(R.layout.view_empty, mMessageRv);
        mMessageRv.setAdapter(mMessageAdapter);
        mMessageRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), ChatDetailActivity.class));
            }
        });

        mMessageRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
                Constant.CLIENT_ID = mMessageEntities.get(position).getClientId();
                mMessageEntities.get(position).setBadgets(0);
                mMessageAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });

        loginSelf();
    }

    private void loginSelf() {
        AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getObjectId());
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    Logger.i("登陆成功!");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLatestConversation();
    }

    @Override
    public void onDestroy() {
        AVIMMessageManager.unregisterMessageHandler(AVIMTextMessage.class, mMessageHandler);
        super.onDestroy();
    }


    private void requestLatestConversation() {
        final String userId = AVUser.getCurrentUser().getObjectId();
        AVIMClient client = AVIMClient.getInstance(userId);
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    AVIMConversationQuery avimConversationQuery = avimClient.getQuery();
                    avimConversationQuery.setLimit(100);
                    avimConversationQuery.findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> list, AVIMException e) {
                            mMessageEntities.clear();
                            for (AVIMConversation conversation : list) {
                                MessageEntity entity = new MessageEntity(conversation);
                                mMessageEntities.add(entity);
                            }
                            mMessageAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
