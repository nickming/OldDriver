package com.wennuan.olddriver.adapter;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.entity.MessageEntity;
import com.wennuan.olddriver.ui.widget.badge.MaterialBadgeTextView;
import com.wennuan.olddriver.util.TimeUtil;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:13:09
 * E-mail:
 */

public class MessageAdapter extends BaseItemDraggableAdapter<MessageEntity, BaseViewHolder> {


    public MessageAdapter(List<MessageEntity> data) {
        super(R.layout.item_message_index, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MessageEntity item) {
        AVIMConversation conversation = item.getLastConversation();
        helper.setImageResource(R.id.iv_message_head, item.getUserHead());
        helper.setText(R.id.tv_message_date,
                TimeUtil.getMixTimeFromTimestamp(conversation.getLastMessageAt().getTime(), 7 * 24 * 60 * 60, "MM-dd")
        );

        helper.setText(R.id.tv_message_name, item.getUserName());

        MaterialBadgeTextView materialBadgeTextView = helper.getView(R.id.tv_message_badge);
        materialBadgeTextView.setBadgeCount(item.getBadgets());

        conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (avimMessage instanceof AVIMTextMessage) {
                    helper.setText(R.id.tv_message_content, ((AVIMTextMessage) avimMessage).getText());
                }
            }
        });

    }
}
