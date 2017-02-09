package com.wennuan.olddriver.entity;

import com.avos.avoscloud.AVUser;

import java.io.Serializable;

/**
 * Desc:
 * Author:
 * Date:
 * Time:14:59
 * E-mail:
 */

public class ContactEntity implements Serializable {

    private AVUser mProxy;
    private int head;

    public ContactEntity(AVUser mProxy) {
        this.mProxy = mProxy;
    }

    public AVUser getmProxy() {
        return mProxy;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
