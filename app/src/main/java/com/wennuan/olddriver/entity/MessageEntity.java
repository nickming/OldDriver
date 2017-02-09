package com.wennuan.olddriver.entity;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.wennuan.olddriver.ui.main.contact.ContactManager;

import java.io.Serializable;

/**
 * Desc:
 * Author:
 * Date:
 * Time:13:09
 * E-mail:
 */
public class MessageEntity implements Serializable {

    private AVIMConversation lastConversation;

    private int badgets=0;

    public int getBadgets() {
        return badgets;
    }

    public void setBadgets(int badgets) {
        this.badgets = badgets;
    }

    public MessageEntity(AVIMConversation lastConversation) {
        this.lastConversation = lastConversation;
    }

    public AVIMConversation getLastConversation() {
        return lastConversation;
    }

    public void setLastConversation(AVIMConversation lastConversation) {
        this.lastConversation = lastConversation;
    }

    public String getUserName() {
        String username = ContactManager.getInstance().getUsernameByObjId(getClientId());
        return username;
    }

    public int getUserHead()
    {
        return ContactManager.getInstance().getUserHead(getClientId());
    }


    public String getClientId() {
        String clientId = "";
        if (lastConversation.getMembers().size() == 2) {
            if (lastConversation.getMembers().get(0).equals(AVUser.getCurrentUser().getObjectId())) {
                clientId = lastConversation.getMembers().get(1);
            } else {
                clientId = lastConversation.getMembers().get(0);
            }
        } else {
            clientId = lastConversation.getMembers().get(0);
        }
        return clientId;
    }
}
