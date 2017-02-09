package com.wennuan.olddriver.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.entity.ChatDetailEntity;
import com.wennuan.olddriver.ui.main.contact.ContactManager;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:23:00
 * E-mail:
 */

public class ChatDetailAdapter extends BaseMultiItemQuickAdapter<ChatDetailEntity, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatDetailAdapter(List<ChatDetailEntity> data) {
        super(data);
        addItemType(ChatDetailEntity.MIME, R.layout.item_chat_detail_right);
        addItemType(ChatDetailEntity.OTHER, R.layout.item_chat_detail_left);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatDetailEntity item) {
        int head = ContactManager.getInstance().getUserHead(item.getProxy().getFrom());
        switch (item.getItemType()) {
            case ChatDetailEntity.MIME:
                helper.setImageResource(R.id.iv_chat_detail_right_head, head);
                helper.setText(R.id.tv_chat_detail_right_content, item.getProxy().getText());
                break;
            case ChatDetailEntity.OTHER:
                helper.setImageResource(R.id.iv_chat_detail_left_head, head);
                helper.setText(R.id.tv_chat_detail_left_content, item.getProxy().getText());
                break;
        }
    }
}
