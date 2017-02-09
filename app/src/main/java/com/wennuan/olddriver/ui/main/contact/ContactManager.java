package com.wennuan.olddriver.ui.main.contact;

import com.avos.avoscloud.AVUser;
import com.wennuan.olddriver.base.Constant;
import com.wennuan.olddriver.entity.HeadChoiceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:15:11
 * E-mail:
 */

public class ContactManager {

    private volatile static ContactManager sInstance;

    private List<AVUser> mUserList = new ArrayList<>();

    public static ContactManager getInstance() {
        if (sInstance == null) {
            synchronized (ContactManager.class) {
                if (sInstance == null) {
                    sInstance = new ContactManager();
                }
            }
        }
        return sInstance;
    }

    public void setUserList(List<AVUser> userList) {
        mUserList.clear();
        mUserList.addAll(userList);
    }

    public String getUsernameByObjId(String objectId) {
        String username = "";
        for (AVUser user : mUserList) {
            if (objectId.equals(user.getObjectId())) {
                username = user.getUsername();
                break;
            }
        }
        return username;
    }

    public int getUserHead(String objectId) {
        int type = 1;
        for (AVUser user : mUserList) {
            if (objectId.equals(user.getObjectId())) {
                type = (int) user.get(Constant.HEAD);
                break;
            }
        }
        return HeadChoiceEntity.getHeadReasource(type);
    }
}
