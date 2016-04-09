package com.example.hongchun.myapplication.ui.activity.zxing.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;
import com.example.hongchun.myapplication.ui.activity.zxing.RGBLuminanceSource.RGBLuminanceSource;
import com.example.hongchun.myapplication.ui.activity.zxing.encoding.EncodingHandler;
import com.example.hongchun.myapplication.ui.activity.zxing.encoding.EncodingHandler2;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Hashtable;

/**
 * Created by HongChun on 2016/3/26.
 */
@ContentView(R.layout.activity_encoding)
public class EncodingActivity extends BaseNormActivity {

    @ViewInject(R.id.textview_titlename)
    TextView textView_titlename;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @ViewInject(R.id.button_logcode)
    Button btn_logcode;

    @ViewInject(R.id.button_code)
    Button btn_code;

    @ViewInject(R.id.button_decoding)
    Button btn_decoding;

    @ViewInject(R.id.textview_decoding)
    TextView textView_decoding;

    @ViewInject(R.id.imageView_myselfcode)
    ImageView imageView_myselfcode;




    String encodinStr="{datamap:[{name:thcc001,pas:88888888},{name:thcc002,pas:88888888}]}";
    String encodinStr2="{datamap:[{name:wsn001,pas:88888888},{name:wsn002,pas:88888888}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBarAndBackButton(toolbar);
        setToolBarTitle(textView_titlename, "二维码生成与解析");
        onEvenOnClick(btn_code);
    }

    @Event(value = {R.id.button_logcode,R.id.button_code,R.id.button_decoding},type = View.OnClickListener.class)
    private void onEvenOnClick(View view){
      switch (view.getId()){
          case R.id.button_code:
              {
                  Bitmap bitmap=  EncodingHandler2.createQRImageBitmap(encodinStr, 300, 300, null, "");
                  imageView_myselfcode.setImageBitmap(bitmap);
              }
              break;
          case R.id.button_logcode:
              Bitmap bitmap=  EncodingHandler2.createQRImageBitmap(encodinStr2, 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "");
              imageView_myselfcode.setImageBitmap(bitmap);
              break;
          case R.id.button_decoding:
              Bitmap scanBitmap=null;
              Drawable drawable= imageView_myselfcode.getDrawable();
              if (drawable instanceof BitmapDrawable) {
                  scanBitmap=((BitmapDrawable) drawable).getBitmap();
              } else if (drawable instanceof NinePatchDrawable) {
//                  scanBitmap= Bitmap
//                          .createBitmap(
//                                  drawable.getIntrinsicWidth(),
//                                  drawable.getIntrinsicHeight(),
//                                  drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                                          : Bitmap.Config.RGB_565);
//                  Canvas canvas = new Canvas(scanBitmap);
//                  drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                          drawable.getIntrinsicHeight());
//                  drawable.draw(canvas);
              }
              if(scanBitmap==null){
                    return;
              }
              RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
              BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
              QRCodeReader reader = new QRCodeReader();
              Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
              hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
              try {
                 Result result= reader.decode(bitmap1, hints);
                  String string=result.getText();
                  textView_decoding.setText(string);
              } catch (NotFoundException e) {
                  e.printStackTrace();
              } catch (ChecksumException e) {
                  e.printStackTrace();
              } catch (FormatException e) {
                  e.printStackTrace();
              }

              break;

      }
    }
}
