package com.wennuan.olddriver.ui.main.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.orhanobut.logger.Logger;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.adapter.ChatDetailAdapter;
import com.wennuan.olddriver.base.BaseActivity;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.ChatDetailEntity;
import com.wennuan.olddriver.ui.main.contact.ContactManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDetailActivity extends BaseActivity {

    private static final String TAG = "ChatDetailActivity";

    @BindView(R.id.tv_chat_title)
    TextView mChatTitleTv;
    @BindView(R.id.btn_chat_detail_back)
    Button mChatDetailBackBtn;
    @BindView(R.id.rv_chat_detail)
    RecyclerView mChatDetailRv;
    @BindView(R.id.et_chat_detail_content)
    EditText mChatDetailContentEt;
    @BindView(R.id.btn_chat_detail_send)
    Button mChatDetailSendBtn;
    @BindView(R.id.activity_chat_detail)
    LinearLayout mActivityChatDetail;

    private int screenHeight = 0;
    private int keyboardHeight = 0;

    private static int sTouchSlop=0;


    private ChatDetailAdapter mChatAdapter;
    private List<ChatDetailEntity> mCurrentMessageList = new ArrayList<>();

    private String mClientId = "";

    private AVIMMessageHandler mMessageHandler = new AVIMMessageHandler() {
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if (message instanceof AVIMTextMessage) {
                String content = ((AVIMTextMessage) message).getText();
                Logger.i(content);
                //更新对话,简单起见
                requestRecentHistoryMessage(mClientId);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        ButterKnife.bind(this);

        initKeyboardStateListener();

        mClientId = Constant.CLIENT_ID;
        Logger.i(mClientId);

        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, mMessageHandler);

        mChatTitleTv.setText(ContactManager.getInstance().getUsernameByObjId(mClientId));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mChatDetailRv.setLayoutManager(layoutManager);
        mChatAdapter = new ChatDetailAdapter(mCurrentMessageList);
        mChatAdapter.setEmptyView(R.layout.view_empty, mChatDetailRv);
        mChatDetailRv.setAdapter(mChatAdapter);
        mChatDetailRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //如果向上滑动超过一定距离
                if (-dy > 5) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mChatDetailContentEt.getWindowToken(), 0);
                }

            }
        });

        mChatDetailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mClientId);
            }
        });
        mChatDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDetailActivity.this.finish();
            }
        });
        requestRecentHistoryMessage(mClientId);
        receiveMessage();


    }

    private void initKeyboardStateListener() {
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyboardHeight = screenHeight / 3;
        mActivityChatDetail.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyboardHeight)) {
                    Log.i(TAG, "onLayoutChange: 软键盘弹起");
                    recyclerviewScrollToLast();
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyboardHeight)) {
                    Log.i(TAG, "onLayoutChange: 软键盘关闭");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        AVIMMessageManager.unregisterMessageHandler(AVIMTextMessage.class, mMessageHandler);
        super.onDestroy();
    }

    /**
     * 获取历史消息
     */
    private void requestRecentHistoryMessage(final String clientId) {
        final String userId = AVUser.getCurrentUser().getObjectId();
        final AVIMClient client = AVIMClient.getInstance(userId);
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    avimClient.createConversation(Arrays.asList(userId, clientId), "会话",
                            null, false, true, new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(AVIMConversation avimConversation, AVIMException e) {
                                    final int limit = 999;
                                    avimConversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
                                        @Override
                                        public void done(List<AVIMMessage> list, AVIMException e) {
                                            if (e == null) {
                                                mCurrentMessageList.clear();
                                                for (AVIMMessage message : list) {
                                                    if (message instanceof AVIMTextMessage) {
                                                        mCurrentMessageList.add(new ChatDetailEntity((AVIMTextMessage) message));
                                                    }
                                                }
                                                mChatAdapter.notifyDataSetChanged();
                                                recyclerviewScrollToLast();
                                            }
                                        }
                                    });
                                }
                            });
                }
            }
        });
    }

    private void recyclerviewScrollToLast() {
        if (mCurrentMessageList.size() > 1) {
            mChatDetailRv.scrollToPosition(mCurrentMessageList.size() - 1);
        }
    }

    /**
     * 发送信息
     *
     * @param clientId
     */
    private void sendMessage(final String clientId) {
        final String content = mChatDetailContentEt.getText().toString();
        if (TextUtils.isEmpty(content))
            return;
        final String userId = AVUser.getCurrentUser().getObjectId();
        AVIMClient user = AVIMClient.getInstance(userId);
        user.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    avimClient.createConversation(Arrays.asList(userId, clientId),
                            "会话", null, false, true
                            , new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(AVIMConversation avimConversation, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage textMessage = new AVIMTextMessage();
                                        textMessage.setText(content);
                                        avimConversation.sendMessage(textMessage, new AVIMConversationCallback() {
                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    Logger.i("发送成功:" + content);
                                                    mChatDetailContentEt.setText("");
                                                    //简单起见,直接请求历史消息
                                                    requestRecentHistoryMessage(mClientId);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }

    /**
     * 接受信息
     */
    private void receiveMessage() {
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

}
