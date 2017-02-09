package com.wennuan.olddriver.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.entity.DiscoverEntity;
import com.wennuan.olddriver.util.TimeUtil;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:15:26
 * E-mail:
 */

public class DiscoverAdapter extends BaseQuickAdapter<DiscoverEntity,BaseViewHolder> {


    public DiscoverAdapter(List<DiscoverEntity> data) {
        super(R.layout.item_discover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverEntity item) {
        helper.setText(R.id.tv_discover_name,item.getmProxy().getSource().getUsername());
        helper.setText(R.id.tv_discover_content,item.getmProxy().getMessage());
        helper.setText(R.id.tv_discover_date,
                TimeUtil.getMixTimeFromTimestamp(item.getmProxy().getCreatedAt().getTime(), 7 * 24 * 60 * 60, "yyyy-MM-dd")
        );
    }
}
