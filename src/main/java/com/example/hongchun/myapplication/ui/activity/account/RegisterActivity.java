package com.example.hongchun.myapplication.ui.activity.account;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;
import com.example.hongchun.myapplication.ui.dialog.LoadDiaog;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by TianHongChun on 2016/4/26.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseNormActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.textview_titlename)
    TextView textViewTitleName;

    @ViewInject(R.id.edittext_username)
    EditText editTextUsername;
    @ViewInject(R.id.edittext_password)
    EditText editTextPassword;
    @ViewInject(R.id.edittext_password2)
    EditText editTextPassword2;
    @ViewInject(R.id.button_register)
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBarAndBackButton(toolbar);
        setToolBarTitle(textViewTitleName,"注册用户");
    }

    @Event(value = {R.id.button_register},type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        if(view.getId()==R.id.button_register){
                if(check()){
                   final String username=editTextUsername.getText().toString();
                    final  String password=editTextPassword.getText().toString();
                    LoadDiaog.getInstance().setTitleName("正在注册...").show(getSupportFragmentManager());
                    x.task().run(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(username, password);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });

                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (!RegisterActivity.this.isFinishing())
                                            LoadDiaog.getInstance().dismiss();
                                        int errorCode = e.getErrorCode();
                                        if (errorCode == EMError.NETWORK_ERROR) {
                                            Toast.makeText(getApplicationContext(),"网络错误", Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                            Toast.makeText(getApplicationContext(),"用户已存在", Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                            Toast.makeText(getApplicationContext(),"身份验证失败", Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                            Toast.makeText(getApplicationContext(),"用户名有吴", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
        }
    }


    private boolean check(){
        final String username = editTextUsername.getText().toString().trim();
        final String pwd = editTextPassword.getText().toString().trim();
        String confirm_pwd = editTextPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"用户名不能为空", Toast.LENGTH_SHORT).show();
            editTextUsername.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this,"密码不能为空", Toast.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            editTextPassword2.requestFocus();
            return false;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this,"2次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
