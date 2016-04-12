package com.example.hongchun.myapplication.data.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianHongChun on 2016/4/12.
 */
public class ContactsPersonDao  {

    /**得到手机通讯录联系人信息**/
    public static List getContactsPersonList(Context context){
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        List<ContactPersonPojo> personPojoList=new ArrayList<>();
        while (cursor.moveToNext()){
            String contactId=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String contactName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String contactPhone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactPersonPojo contactPersonPojo=new ContactPersonPojo();
            contactPersonPojo.setId(contactId);
            contactPersonPojo.setPersonName(contactName);
            contactPersonPojo.setPersonPhone(contactPhone);
            personPojoList.add(contactPersonPojo);
        }
        cursor.close();
        return personPojoList;
    }
    public static int deleteContactsPerson(Context context,String contactId){
      int rowCount=context.getContentResolver().delete(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?"
                    ,new String[]{contactId});
        return rowCount;
    }
    public static int updataContactsPerson(Context context,String contactId,ContentValues contentValues){
        int rowCount= context.getContentResolver().update(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                ,contentValues
                ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?"
                ,new String[]{contactId});
            return rowCount;
    }


    /**得到手机SIM卡联系人人信息**/
    private void getSIMContacts(Context context) {
        ContentResolver resolver = context.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, null, null, null, null);
    }
}
