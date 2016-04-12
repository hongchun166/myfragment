package com.example.hongchun.myapplication.data.pojo;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class ContactPersonPojo {
    private String id;
    private String personName;
    private String personPhone;
    private String personHead;

    public ContactPersonPojo(){

    }
    public ContactPersonPojo(String id,String personName,String personPhone,String personHead){
        this.id=id;
        this.personName=personName;
        this.personPhone=personPhone;
        this.personHead=personHead;
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
}
