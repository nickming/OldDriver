package com.wennuan.olddriver.entity;

import com.wennuan.olddriver.R;

/**
 * Desc:
 * Author:
 * Date:
 * Time:19:28
 * E-mail:
 */

public class HeadChoiceEntity {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;

    public static int getHeadReasource(int type) {
        switch (type) {
            case TYPE_1:
                return R.mipmap.head1;
            case TYPE_2:
                return R.mipmap.head2;
            case TYPE_3:
                return R.mipmap.head3;
            case TYPE_4:
                return R.mipmap.head4;
        }
        return R.mipmap.head1;
    }


    private int type = 1;

    private boolean isSelect = false;

    public HeadChoiceEntity(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}


