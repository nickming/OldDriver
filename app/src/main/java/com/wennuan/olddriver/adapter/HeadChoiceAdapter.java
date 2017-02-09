package com.wennuan.olddriver.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.entity.HeadChoiceEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:19:25
 * E-mail:
 */

public class HeadChoiceAdapter extends BaseQuickAdapter<HeadChoiceEntity, BaseViewHolder> {

    private int mCurrentSelect = 0;

    private SelectChangeListener mListener;

    public interface SelectChangeListener {
        void select(int index);
    }

    public void setListener(SelectChangeListener mListener) {
        this.mListener = mListener;
    }

    public HeadChoiceAdapter(List<HeadChoiceEntity> data) {
        super(R.layout.item_head_choice, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HeadChoiceEntity item) {
        switch (item.getType()) {
            case HeadChoiceEntity.TYPE_1:
                helper.setImageResource(R.id.iv_head_choice, R.mipmap.head1);
                helper.setText(R.id.tv_head_choice, "美女");
                break;
            case HeadChoiceEntity.TYPE_2:
                helper.setImageResource(R.id.iv_head_choice, R.mipmap.head2);
                helper.setText(R.id.tv_head_choice, "帅哥");
                break;
            case HeadChoiceEntity.TYPE_3:
                helper.setImageResource(R.id.iv_head_choice, R.mipmap.head3);
                helper.setText(R.id.tv_head_choice, "你猜");
                break;
            case HeadChoiceEntity.TYPE_4:
                helper.setImageResource(R.id.iv_head_choice, R.mipmap.head4);
                helper.setText(R.id.tv_head_choice, "二次元");
                break;
        }
        CheckBox checkBox = helper.getView(R.id.cb_head_choice);
        if (item.isSelect())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = helper.getLayoutPosition();
                if (index != mCurrentSelect) {
                    mData.get(mCurrentSelect).setSelect(false);
                    notifyItemChanged(mCurrentSelect);
                    mData.get(index).setSelect(true);
                    notifyItemChanged(index);
                    mCurrentSelect = index;
                    mData.get(index).setSelect(true);
                    if (mListener != null)
                        mListener.select(helper.getLayoutPosition());
                }
            }
        });
    }


}
