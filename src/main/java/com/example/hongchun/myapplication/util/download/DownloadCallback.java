package com.example.hongchun.myapplication.util.download;

import android.util.Log;
import android.widget.Toast;

import com.example.hongchun.myapplication.data.pojo.DownLoadInfo;
import com.example.hongchun.myapplication.data.pojo.DownloadState;
import com.example.hongchun.myapplication.ui.fragment.home.MessageFragment;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by TianHongChun on 2016/5/8.
 */
public class DownloadCallback implements
        Callback.CommonCallback<File>,Callback.ProgressCallback<File>,Callback.Cancelable {

    private Cancelable cancelable;
    private boolean cancelled = false;
    DownLoadViewHodler viewHodler;
    DownLoadInfo downLoadInfo;

    public DownloadCallback(DownLoadViewHodler viewHodler){
        this.viewHodler=viewHodler;
        this.downLoadInfo=((MessageFragment.ViewHodler)viewHodler).getDownLoadInfo();
    }

    private void log(String content){
        Log.i(DownloadCallback.class.getName(), content);
    }


    public void setCancelable(Cancelable cancelable){
        this.cancelable=cancelable;
    }

//////////////////////
// ///////// CommonCallback/////////////
// //////////////////////
    @Override
    public void onSuccess(File result) {
        downLoadInfo.setState(DownloadState.FINISHED);
        viewHodler.onSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        downLoadInfo.setState(DownloadState.ERROR);
        viewHodler.onError(ex,isOnCallback);
    }

    @Override
    public void onCancelled(CancelledException cex) {
        downLoadInfo.setState(DownloadState.STOPPED);
        viewHodler.onCancelled(cex);
    }

    @Override
    public void onFinished() {

    }


///////////////////////////
///////////ProgressCallback//////////////
    /////////////////////////

    @Override
    public void onWaiting() {
        downLoadInfo.setState(DownloadState.WAITING);
            viewHodler.onWaiting();
    }

    @Override
    public void onStarted() {
        downLoadInfo.setState(DownloadState.STARTED);
        viewHodler.onStarted();
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        downLoadInfo.setState(DownloadState.STARTED);
        downLoadInfo.setFileLength(total);
        downLoadInfo.setProgress((int) (current * 100 / total));
        viewHodler.onLoading(total,current);
    }

//Cancelable
    @Override
    public void cancel() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
