package com.wennuan.olddriver.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

/**
 * Desc:
 * Author:
 * Date:
 * Time:14:59
 * E-mail:
 */

public class ContactSectionEntity extends SectionEntity<ContactEntity> implements Serializable{

    public ContactSectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ContactSectionEntity(ContactEntity contactEntity) {
        super(contactEntity);
    }
}
