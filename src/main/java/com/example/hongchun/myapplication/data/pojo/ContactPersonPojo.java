package com.example.hongchun.myapplication.data.pojo;

import android.text.TextUtils;

import com.example.hongchun.myapplication.util.MStringUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by TianHongChun on 2016/4/24.
 */
@Table(name = "contact_person",onCreated = "CREATE UNIQUE INDEX index_name ON contact_person(name,phone)")
public class ContactPersonPojo {
    @Column(name = "id",isId = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "personHead")
    private String personHead;

    @Column(name = "description")
    private String description;

    private String sortLetter = "#";
    private String pinyin;

    public ContactPersonPojo(){

    }
    public ContactPersonPojo(int id, String personName, String personPhone, String personHead){
        this.id=id;
        this.name=personName;
        this.phone=personPhone;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.pinyin = MStringUtils.getPingYin(name);
        if (!TextUtils.isEmpty(pinyin)) {
            String sortString = this.pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                this.sortLetter = sortString.toUpperCase();
            } else {
                this.sortLetter = "#";
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonHead() {
        return personHead;
    }

    public void setPersonHead(String personHead) {
        this.personHead = personHead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
