package com.example.hongchun.myapplication.util.download;

import com.example.hongchun.myapplication.data.pojo.DownLoadInfo;
import com.example.hongchun.myapplication.util.FileUtil;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Created by TianHongChun on 2016/5/8.
 */
public class DownLoadManage {
    private final Executor executor = new PriorityExecutor(1, true);
    private final List<DownLoadInfo> downloadInfoList = new ArrayList<DownLoadInfo>();
    private final ConcurrentHashMap<DownLoadInfo, DownloadCallback> callbackMap = new ConcurrentHashMap<DownLoadInfo, DownloadCallback>(5);

    private static DownLoadManage downLoadManage;
    private DownLoadManage(){
        for (int i=0;i<2;i++){
            DownLoadInfo downLoadInfo=new DownLoadInfo();
            switch (i){
                case 0:
                    downLoadInfo.setUrl("http://wenshitech.com/uploads/soft/160420/WenshiDianWuTong_n_1.1.17.apk");
                    downLoadInfo.setFileName("WenshiDianWuTong_n_1.1.17.apk");
                    break;
                case 1:
                    downLoadInfo.setUrl("http://wenshitech.com/uploads/soft/160420/WenshiDianWuTong_p_1.1.17.apk");
                    downLoadInfo.setFileName("WenshiDianWuTong_p_1.1.17.apk");
                    break;
                case 2:
                    downLoadInfo.setUrl("http://pic64.nipic.com/file/20150420/9885883_141342490000_2.jpg");
                    downLoadInfo.setFileName("9885883_141342490000_2.jpg");

                    break;
                case 3:
                    downLoadInfo.setUrl("http://m2.quanjing.com/2m/top016/top-682869.jpg");
                    downLoadInfo.setFileName("top-682869.jpg");
                    break;
                case 4:
                    downLoadInfo.setUrl("http://image.bitauto.com/dealer/news/100007009/9de46b69-ac3c-4feb-b83b-e44e25182069.jpg");
                    downLoadInfo.setFileName("9de46b69-ac3c-4feb-b83b-e44e25182069.jpg");
                    break;
            }
            downloadInfoList.add(downLoadInfo);
        }
    }
    public static DownLoadManage getInstance(){
        if(downLoadManage==null){
            synchronized (DownLoadManage.class) {
                if(downLoadManage==null){
                    downLoadManage=new DownLoadManage();
                }
            }

        }
        return downLoadManage;
    }
    public int getDownloadListCount() {
        return downloadInfoList.size();
    }

    public DownLoadInfo getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }

    public synchronized void startDownload(DownLoadInfo downLoadInfo,DownLoadViewHodler viewHodler)  {
        String fileSavePath = new File(downLoadInfo.getFileSavePath()).getAbsolutePath();
        RequestParams params = new RequestParams(downLoadInfo.getUrl());
        params.setAutoResume(true);
        params.setAutoRename(false);
        params.setSaveFilePath(fileSavePath);
        params.setExecutor(executor);
        params.setCancelFast(true);

        DownloadCallback callback = new DownloadCallback(viewHodler);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        callback.setCancelable(cancelable);
        callbackMap.put(downLoadInfo, callback);
    }

    public void stopDownload(DownLoadInfo downloadInfo) {
        Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
        if (cancelable != null) {
            cancelable.cancel();
        }
    }
}
