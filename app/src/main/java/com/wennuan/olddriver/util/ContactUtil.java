package com.wennuan.olddriver.util;

import com.avos.avoscloud.AVUser;
import com.wennuan.olddriver.entity.ContactEntity;
import com.wennuan.olddriver.entity.ContactSectionEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Desc:
 * Author:
 * Date:
 * Time:17:51
 * E-mail:
 */

public class ContactUtil {


    /**
     * 根据首字母分类
     *
     * @param usersList
     * @return
     */
    public static List<ContactSectionEntity> convertAVUserListToSectionList(List<AVUser> usersList) {
        List<ContactSectionEntity> result = new ArrayList<>();
        TreeMap<String, List<AVUser>> sortMap = new TreeMap<>();
        //遍历分类
        for (AVUser user : usersList) {
            String username = user.getUsername();
            String firstLetter = String.valueOf(username.charAt(0)).toUpperCase();
            if (sortMap.containsKey(firstLetter)) {
                sortMap.get(firstLetter).add(user);
            } else {
                sortMap.put(firstLetter, new ArrayList<AVUser>());
                sortMap.get(firstLetter).add(user);
            }
        }
        sortMapByKey(sortMap);

        for (Map.Entry<String, List<AVUser>> entry : sortMap.entrySet()) {
            result.add(new ContactSectionEntity(true, entry.getKey()));
            for (AVUser temp : entry.getValue()) {
                result.add(new ContactSectionEntity(new ContactEntity(temp)));
            }
        }
        return result;
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, List<AVUser>> sortMapByKey(Map<String, List<AVUser>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, List<AVUser>> sortMap = new TreeMap<String, List<AVUser>>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });

        sortMap.putAll(map);
        return sortMap;
    }
}
