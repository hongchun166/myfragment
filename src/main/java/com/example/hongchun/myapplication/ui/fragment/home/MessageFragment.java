package com.example.hongchun.myapplication.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.DownLoadInfo;
import com.example.hongchun.myapplication.data.pojo.DownloadState;
import com.example.hongchun.myapplication.ui.fragment.BaseFragment;
import com.example.hongchun.myapplication.util.FileUtil;
import com.example.hongchun.myapplication.util.download.DownLoadManage;
import com.example.hongchun.myapplication.util.download.DownLoadViewHodler;

import org.w3c.dom.ProcessingInstruction;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by TianHongChun on 2016/4/5.
 */
@ContentView(R.layout.fragment_message_layout)
public class MessageFragment extends BaseFragment {


    Context context;

    @ViewInject(R.id.listView)
    ListView  listView;

    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DownLoadAdapter adapter=new DownLoadAdapter();
        listView.setAdapter(adapter);
    }


    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initEven(View view, @Nullable Bundle savedInstanceState) {
//        String[]   strs= new String[30];
//        for (int i = 0; i < 30; i++) {
//            strs[i] = "data-----" + i;
//        }
//        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strs));

    }

    @Event(value = R.id.swipeRefreshLayout,type= SwipeRefreshLayout.OnRefreshListener.class)
    private void onRefreshDataEvent() {
        hideSwipeRefreshIcon();
    }

    /**
     * 显示刷新按钮
     */
    public void showSwipeRefreshIcon(){
        //显示加载图标
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 隐藏刷新按钮
     */
    public void hideSwipeRefreshIcon(){
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭加载图标
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     *
     */
    public class DownLoadAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return DownLoadManage.getInstance().getDownloadListCount();
        }

        @Override
        public Object getItem(int position) {
            return DownLoadManage.getInstance().getDownloadInfo(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler=null;
            DownLoadInfo downLoadInfo=DownLoadManage.getInstance().getDownloadInfo(position);
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.item_download,null);
                hodler=new ViewHodler(convertView,downLoadInfo);
                x.view().inject(hodler,convertView);
                convertView.setTag(hodler);
            }else {
                hodler=(ViewHodler)convertView.getTag();
                hodler.setDownLoadInfo(downLoadInfo);
            }

            hodler.setDownLoadInfo(downLoadInfo);
            String uri=downLoadInfo.getUrl();
            hodler.getProgressTextView().setText(uri.substring(uri.lastIndexOf("/")+1,uri.length()));
            return convertView;
        }
    }

    /**
     *
     */
    public class ViewHodler implements DownLoadViewHodler{
       private TextView progressTextView;
        private Button downloadButton;
        private DownLoadInfo downLoadInfo;

        public ViewHodler(View view,DownLoadInfo downLoadInfo){
            this.downLoadInfo=downLoadInfo;

            progressTextView=(TextView)view.findViewById(R.id.progress);
            downloadButton=(Button)view.findViewById(R.id.button_download);
        }
        @Event(value = {R.id.button_download},type = View.OnClickListener.class)
        private void onEvenOnClick(View view){
                if(view.getId()==R.id.button_download){
                    //判断当前下载状态,
                    DownloadState state=getDownLoadInfo().getState();
                    switch (state){
                        case WAITING:
                        case STARTED:
                            DownLoadManage.getInstance().stopDownload(downLoadInfo);
                            break;
                        case ERROR:
                        case STOPPED:
                            String filePath=FileUtil.getNativeFileUriStr(context);
                            downLoadInfo.setFileSavePath(filePath+"/"+downLoadInfo.getFileName());
                            DownLoadManage.getInstance().startDownload(downLoadInfo,this);
                            break;
                        case FINISHED:
                            Toast.makeText(x.app(), "已经下载完成", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                }
        }

        @Override
        public void onWaiting() {
            refreshDownLoad();
        }

        @Override
        public void onStarted() {
            refreshDownLoad();
        }

        @Override
        public void onLoading(long total, long current) {
            refreshDownLoad();
        }

        @Override
        public void onSuccess(File result) {
            refreshDownLoad();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            refreshDownLoad();
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            refreshDownLoad();
        }

        private void refreshDownLoad(){
            getProgressTextView().setText(""+downLoadInfo.getFileName()+":"+downLoadInfo.getProgress()+"%");
            DownloadState state=downLoadInfo.getState();
            switch (state){
                case WAITING:
                case STARTED:
                    getDownloadButton().setText("停止");
                    break;
                case ERROR:
                case STOPPED:
                    getDownloadButton().setText("开始");
                    break;
                case FINISHED:
                    getDownloadButton().setText("下载已经完成");
                    break;
            }
        }

        public TextView getProgressTextView() {
            return progressTextView;
        }

        public void setProgressTextView(TextView progressTextView) {
            this.progressTextView = progressTextView;
        }

        public Button getDownloadButton() {
            return downloadButton;
        }

        public void setDownloadButton(Button downloadButton) {
            this.downloadButton = downloadButton;
        }

        public DownLoadInfo getDownLoadInfo() {
            return downLoadInfo;
        }

        public void setDownLoadInfo(DownLoadInfo downLoadInfo) {
            this.downLoadInfo = downLoadInfo;
        }
    }

}
