package com.example.hongchun.myapplication.data.pojo;

import android.text.TextUtils;

import com.example.hongchun.myapplication.util.MStringUtils;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class ContactPersonPojo {
    private String id;
    private String personName;
    private String personPhone;
    private String personHead;
    private String sortLetter = "#";
    private String pinyin;


    public ContactPersonPojo(){

    }
    public ContactPersonPojo(String id, String personName, String personPhone, String personHead){
        this.id=id;
        this.personName=personName;
        this.personPhone=personPhone;
        this.personHead=personHead;

        this.pinyin = MStringUtils.getPingYin(personName);
        if (!TextUtils.isEmpty(pinyin)) {
            String sortString = this.pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                this.sortLetter = sortString.toUpperCase();
            } else {
                this.sortLetter = "#";
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonHead() {
        return personHead;
    }

    public void setPersonHead(String personHead) {
        this.personHead = personHead;
    }


    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
