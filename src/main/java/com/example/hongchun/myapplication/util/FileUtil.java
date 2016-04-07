package com.example.hongchun.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TianHongChun on 2016/4/5.
 */
public class FileUtil {


    public static Uri getImageUri(Context context,String fileName) {
        File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+fileName);
        if (!tempFile.exists()){
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(tempFile);
    }

    public static File getImageFile(Context context,String fileName) {
        File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+fileName);
        if (!tempFile.exists()){
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }
    public static File getFileByFilePath(String filepath){
        File tempFile=new File(filepath);
        if( ! tempFile.exists()){
            return null;
        }
        return tempFile;
    }

    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap=null;
        try{
            bitmap=BitmapFactory.decodeFile(imgPath, newOpts);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            bitmap=null;
        }
        return bitmap;
    }

    public static void compByPath(String imgPath,Context context,String fileName){
        try{
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            newOpts.inSampleSize = 1;
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap=BitmapFactory.decodeFile(imgPath, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float hh = 1280f;
            float ww = 720f;
            int be = 1;
            if((w > h) && ((float)w > ww)) {
                be = (int)((float)newOpts.outWidth / ww);
            } else if((w < h) && ((float)h > hh)) {
                be = (int)((float)newOpts.outHeight / hh);
            }
            if(be <= 0) {
                be = 1;
            }
            newOpts.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            int options = 90;
            while((baos.toByteArray().length / 1024) >150) {
                baos.reset();
                options = options - 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }

            File tempfile = getImageFile(context, fileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(tempfile);
                out.write(baos.toByteArray(), 0, baos.toByteArray().length);
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out!=null)
                    try{
                        out.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
            }
            bitmap.recycle();
            bitmap=null;
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        System.gc();
    }
    public static Bitmap comp(Bitmap image,Context context,String fileName) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if((baos.toByteArray().length / 1024) > 1024) {
                baos.reset();
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();

            newOpts.inJustDecodeBounds = true;
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap  bitmap =BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            float hh = 1280f;
            float ww = 720f;
            int be = 1;
            if((w > h) && ((float)w > ww)) {
                be = (int)((float)newOpts.outWidth / ww);
            } else if((w < h) && ((float)h > hh)) {
                be = (int)((float)newOpts.outHeight / hh);
            }
            if(be <= 0) {
                be = 1;
            }
            newOpts.inSampleSize = be;

            isBm = new ByteArrayInputStream(baos.toByteArray());

            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            Bitmap compBitmap = compressImage(bitmap);
            File tempfile = getImageFile(context, fileName);

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(tempfile);
                compBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                out.flush();
                out.close();
                baos.reset();
                baos.close();
                isBm.reset();
                isBm.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.recycle();
            image.recycle();
            bitmap=null;
            image=null;
            System.gc();
            return compBitmap;
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            image.recycle();
            image=null;
            return null;
        }
    }

    //质量压缩
    private static Bitmap compressImage(Bitmap image) throws OutOfMemoryError{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        int options = 100;
        while((baos.toByteArray().length / 1024) >140) {
            baos.reset();
            options = options - 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try{
            baos.close();
            isBm.close();
        }catch (IOException e){
        }
        if(bitmap!=null){
            return bitmap;
        }else {
            return image;
        }
    }

    //生成图片文件名称，按userid_20150921092011的.jpg的格式
    public static String getFileName(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String ranStr=MyUtils.getRandomString(6);
        String filename = ranStr+"_"+sdf.format(new Date())+".jpg";
        return filename;
    }

    public static String getFileNameNoEndwith(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String ranStr=MyUtils.getRandomString(6);
        return (ranStr+"_"+sdf.format(new Date()));
    }

    ////// 获取下载的APK地址
    public static String getNativeFileUriStr(Context context){
        String sdPath=getSavePath(context);
        if(!sdPath.equals("")){
            String pkg=context.getPackageName();
            pkg=pkg.substring(pkg.lastIndexOf(".")+1);
            String path=sdPath+File.separator+"wenshifile"+File.separator+pkg+File.separator;
            File file = new File(path);
            if (!file.exists())
                file.mkdir();
            return path;
        }else {
            return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();
        }
    }
    private static String getSavePath(Context context){
        String path="";
        if(getExternaltStorageAvailableSpace()){
            path = Environment.getExternalStorageDirectory().getPath();
        }else if(getSdcard2StorageAvailableSpace()){
            File file = new File("/mnt/sdcard2");
            path=file.getPath();
        }else if(getEmmcStorageAvailableSpace()){
            File file = new File("/mnt/emmc");
            path=file.getPath();
        }
        return  path;
    }
    private static  boolean getExternaltStorageAvailableSpace(){
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return sdCardExist;
    }
    private static boolean getSdcard2StorageAvailableSpace(){
        File file = new File("/mnt/sdcard2");
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }
    private static boolean getEmmcStorageAvailableSpace(){
        File file = new File("/mnt/emmc");
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    public static String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名*/
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
    public static boolean isMIMEType(String fileName){
        boolean isMIMEType=false;
        int dotIndex = fileName.lastIndexOf(".");
        if(dotIndex < 0){
            return false;
        }
        String end=fileName.substring(dotIndex, fileName.length()).toLowerCase();
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                isMIMEType=true;
        }
        return isMIMEType;
    }
    public static final String[][] MIME_MapTable={
            //{后缀名，MIME类型}
//            {".3gp",    "video/3gpp"},
//            {".apk",    "application/vnd.android.package-archive"},
//            {".asf",    "video/x-ms-asf"},
//            {".avi",    "video/x-msvideo"},
//            {".bin",    "application/octet-stream"},
//            {".bmp",    "image/bmp"},
//            {".c",  "text/plain"},
//            {".class",  "application/octet-stream"},
//            {".conf",   "text/plain"},
//            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
//            {".exe",    "application/octet-stream"},
//            {".gif",    "image/gif"},
//            {".gtar",   "application/x-gtar"},
//            {".gz", "application/x-gzip"},
//            {".h",  "text/plain"},
//            {".htm",    "text/html"},
//            {".html",   "text/html"},
//            {".jar",    "application/java-archive"},
//            {".java",   "text/plain"},
//            {".jpeg",   "image/jpeg"},
//            {".jpg",    "image/jpeg"},
//            {".js", "application/x-javascript"},
//            {".log",    "text/plain"},
//            {".m3u",    "audio/x-mpegurl"},
//            {".m4a",    "audio/mp4a-latm"},
//            {".m4b",    "audio/mp4a-latm"},
//            {".m4p",    "audio/mp4a-latm"},
//            {".m4u",    "video/vnd.mpegurl"},
//            {".m4v",    "video/x-m4v"},
//            {".mov",    "video/quicktime"},
//            {".mp2",    "audio/x-mpeg"},
//            {".mp3",    "audio/x-mpeg"},
//            {".mp4",    "video/mp4"},
//            {".mpc",    "application/vnd.mpohun.certificate"},
//            {".mpe",    "video/mpeg"},
//            {".mpeg",   "video/mpeg"},
//            {".mpg",    "video/mpeg"},
//            {".mpg4",   "video/mp4"},
//            {".mpga",   "audio/mpeg"},
//            {".msg",    "application/vnd.ms-outlook"},
//            {".ogg",    "audio/ogg"},
//            {".pdf",    "application/pdf"},
//            {".png",    "image/png"},
//            {".pps",    "application/vnd.ms-powerpoint"},
//            {".ppt",    "application/vnd.ms-powerpoint"},
//            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
//            {".prop",   "text/plain"},
//            {".rc", "text/plain"},
//            {".rmvb",   "audio/x-pn-realaudio"},
//            {".rtf",    "application/rtf"},
//            {".sh", "text/plain"},
//            {".tar",    "application/x-tar"},
//            {".tgz",    "application/x-compressed"},
//            {".txt",    "text/plain"},
//            {".wav",    "audio/x-wav"},
//            {".wma",    "audio/x-ms-wma"},
//            {".wmv",    "audio/x-ms-wmv"},
//            {".wps",    "application/vnd.ms-works"},
//            {".xml",    "text/plain"},
//            {".z",  "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {".rar",    "application/x-zip-compressed"},
//            {"",        "*/*"}
    };

}
