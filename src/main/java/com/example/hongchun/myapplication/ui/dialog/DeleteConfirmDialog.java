package com.example.hongchun.myapplication.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.example.hongchun.myapplication.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/12.
 */
@ContentView(R.layout.dialog_delete_confirm_layout)
public class DeleteConfirmDialog extends BaseDialogFragment {

    @ViewInject(R.id.button_cancel)
    Button buttonCancel;
    @ViewInject(R.id.button_delete)
    Button buttonDelete;

    View.OnClickListener onclickListener;

    private static DeleteConfirmDialog deleteConfirmDialog;
    public static DeleteConfirmDialog getInstance(){
        if(deleteConfirmDialog==null){
            deleteConfirmDialog=new DeleteConfirmDialog();
        }
        return deleteConfirmDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(value = {R.id.button_delete,R.id.button_cancel},type = View.OnClickListener.class)
    private void onEvenOnClick(View view){
        switch (view.getId()){
            case R.id.button_delete:
                dismiss();
                if(onclickListener!=null){
                    onclickListener.onClick(view);
                }
                break;
            case R.id.button_cancel:
                dismiss();
                break;
        }
    }

    public DeleteConfirmDialog setOnclickListener(View.OnClickListener onclickListener){
            this.onclickListener=onclickListener;
        return deleteConfirmDialog;
    }

    public void show(FragmentManager manager) {
        super.show(manager, "DeleteConfirmDialog");
    }
}
