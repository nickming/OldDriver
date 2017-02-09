package com.wennuan.olddriver.adapter;

import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:19:37
 * E-mail:
 */

public class SearchAdapter extends BaseQuickAdapter<AVUser,BaseViewHolder> {


    public SearchAdapter(List<AVUser> data) {
        super(R.layout.item_contact, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVUser item) {
        helper.setImageResource(R.id.iv_contact_head, R.mipmap.head3);
        helper.setText(R.id.tv_contact_name, item.getUsername());
    }
}
