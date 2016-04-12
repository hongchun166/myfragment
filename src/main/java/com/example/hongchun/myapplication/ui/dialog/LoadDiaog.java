package com.example.hongchun.myapplication.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by TianHongChun on 2016/4/12.
 */
@ContentView(R.layout.dialog_load_layout)
public class LoadDiaog extends BaseDialogFragment {

    @ViewInject(R.id.textView)
    TextView textView;

    @ViewInject(R.id.progressBar)
    ProgressBar progressBar;

    String titleName="处理中...";

    private static LoadDiaog loadDiaog;
    public static LoadDiaog getInstance(){
        if(loadDiaog==null){
            loadDiaog=new LoadDiaog();
        }
        return loadDiaog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText(titleName);
    }

    public void show(FragmentManager fragmentManager){
        super.show(fragmentManager,"LoadDiaog");
    }


    public String getTitleName() {
        return titleName;
    }

    public LoadDiaog setTitleName(String titleName) {
        this.titleName = titleName;
        return loadDiaog;
    }
}
