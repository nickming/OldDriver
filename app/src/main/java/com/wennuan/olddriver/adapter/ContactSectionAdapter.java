package com.wennuan.olddriver.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.ContactEntity;
import com.wennuan.olddriver.entity.ContactSectionEntity;
import com.wennuan.olddriver.entity.HeadChoiceEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:15:00
 * E-mail:
 */

public class ContactSectionAdapter extends BaseSectionQuickAdapter<ContactSectionEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ContactSectionAdapter(List<ContactSectionEntity> data) {
        super(R.layout.item_contact, R.layout.item_contact_letter, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ContactSectionEntity item) {
        helper.setText(R.id.tv_contact_section_letter, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactSectionEntity item) {
        ContactEntity contactEntity = item.t;
        int type = (int) contactEntity.getmProxy().get(Constant.HEAD);
        helper.setImageResource(R.id.iv_contact_head, HeadChoiceEntity.getHeadReasource(type));
        helper.setText(R.id.tv_contact_name, contactEntity.getmProxy().getUsername());
        helper.setText(R.id.tv_contact_phone, contactEntity.getmProxy().getMobilePhoneNumber());
    }
}
