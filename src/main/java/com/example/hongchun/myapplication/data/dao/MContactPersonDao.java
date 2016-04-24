package com.example.hongchun.myapplication.data.dao;

import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by TianHongChun on 2016/4/24.
 */
public class MContactPersonDao extends BaseDao {

    private static MContactPersonDao dao;
    private MContactPersonDao(){

    }
    public static synchronized MContactPersonDao getInstant(){
        if(dao==null){
            dao=new MContactPersonDao();
        }
        return dao;
    }

    public void saveDatas(List<ContactPersonPojo> personPojoList){

        DbManager dbManager=getDBManage();
        try {
            dbManager.saveBindingId(personPojoList);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
    public void saveData(ContactPersonPojo contactPersonPojo){
        DbManager dbManager=getDBManage();
        try {
            dbManager.saveBindingId(contactPersonPojo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<ContactPersonPojo> selectAll(){
            DbManager dbManager=getDBManage();
        List<ContactPersonPojo> pojo=null;
        try {
            pojo= dbManager.selector(ContactPersonPojo.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    public ContactPersonPojo selectPersonById(int id){
        DbManager dbManager=getDBManage();
        ContactPersonPojo pojo=null;
        try {
            pojo=dbManager.selector(ContactPersonPojo.class).where("id", "in", new int[]{id}).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    public void deletePersonById(int id){
        DbManager dbManager=getDBManage();
        try {
            dbManager.deleteById(ContactPersonPojo.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updatePersonById(ContactPersonPojo pojo){
            DbManager dbManager=getDBManage();
        try {
            dbManager.update(pojo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
