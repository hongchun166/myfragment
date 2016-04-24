package com.example.hongchun.myapplication.util;

import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class CollectionsUtil {
    private CollectionsUtil(){
    }


    public static   void sortContactPerson(List<ContactPersonPojo> list){
        if(list.size()>1){
            Collections.sort(list, new Comparator<ContactPersonPojo>() {
                @Override
                public int compare(ContactPersonPojo lhs, ContactPersonPojo rhs) {
                    return lhs.getPinyin().compareToIgnoreCase(rhs.getPinyin());
                }
            });
        }
    }
}

