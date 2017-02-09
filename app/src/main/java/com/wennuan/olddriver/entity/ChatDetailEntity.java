package com.wennuan.olddriver.entity;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Desc:
 * Author:
 * Date:
 * Time:23:08
 * E-mail:
 */
public class ChatDetailEntity implements MultiItemEntity, Serializable {

    public static final int MIME = 1;
    public static final int OTHER = 2;

    private AVIMTextMessage mProxy;

    public ChatDetailEntity(AVIMTextMessage mProxy) {
        this.mProxy = mProxy;
    }

    public AVIMTextMessage getProxy() {
        return mProxy;
    }

    @Override
    public int getItemType() {
        if (mProxy.getFrom().equals(AVUser.getCurrentUser().getObjectId()))
            return MIME;
        else
            return OTHER;
    }
}
