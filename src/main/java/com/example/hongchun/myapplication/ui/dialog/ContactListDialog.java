package com.example.hongchun.myapplication.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/12.
 */
@ContentView(R.layout.dialog_contacts_person_list_layout)
public class ContactListDialog extends BaseDialogFragment {

    @ViewInject(R.id.button_update)
    TextView buttonUpdate;
    @ViewInject(R.id.button_delete)
    TextView buttonDelete;
    @ViewInject(R.id.button_cancel)
    TextView buttonCancel;

    private ContactPersonPojo contactPersonPojo;

    private static ContactListDialog contactListDialog=null;
    public static ContactListDialog getInstance(){
            if(contactListDialog==null){
                contactListDialog=new ContactListDialog();
            }
        return contactListDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(value = {R.id.button_update,R.id.button_delete,R.id.button_cancel},type = View.OnClickListener.class)
    private void onEvenOnClick(View view){
        switch (view.getId()){
            case R.id.button_update:
                break;
            case R.id.button_delete:
                DeleteConfirmDialog.getInstance().setOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadDiaog.getInstance()
                                .setTitleName("删除:" + getContactPersonPojo().getPersonName())
                                .show(getFragmentManager());
                        dismiss();
                    }
                }).show(getFragmentManager());
                break;
            case R.id.button_cancel:
                dismiss();
                break;
        }
    }
    public void show(FragmentManager manager) {
        super.show(manager, "ContactListDialog");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        this.contactPersonPojo=null;
    }

    public ContactPersonPojo getContactPersonPojo() {
        return contactPersonPojo;
    }

    public ContactListDialog setContactPersonPojo(ContactPersonPojo contactPersonPojo) {
        this.contactPersonPojo = contactPersonPojo;
        return contactListDialog;
    }
}
