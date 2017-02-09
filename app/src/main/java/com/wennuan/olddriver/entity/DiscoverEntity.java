package com.wennuan.olddriver.entity;

import com.avos.avoscloud.AVStatus;

/**
 * Desc:
 * Author:
 * Date:
 * Time:15:27
 * E-mail:
 */

public class DiscoverEntity {

    private AVStatus mProxy;

    public DiscoverEntity(AVStatus mProxy) {
        this.mProxy = mProxy;
    }

    public AVStatus getmProxy() {
        return mProxy;
    }

    public void setmProxy(AVStatus mProxy) {
        this.mProxy = mProxy;
    }
}
