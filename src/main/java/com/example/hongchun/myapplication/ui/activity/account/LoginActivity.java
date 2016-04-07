package com.example.hongchun.myapplication.ui.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseExitActivity;
import com.example.hongchun.myapplication.ui.activity.HomeActivity;
import com.example.hongchun.myapplication.ui.activity.MainActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseExitActivity {

    @ViewInject(R.id.edittext_password)
    EditText et_password;

    @ViewInject(R.id.edittext_username)
    EditText et_username;

    @ViewInject(R.id.checkbox_password_show)
    CheckBox checkBox_showPsw;

    @ViewInject(R.id.imagebutton_username_clear)
    ImageButton imageButton_clear;

    @ViewInject(R.id.button_register)
    Button button_reg;

    @ViewInject(R.id.button_forgrtpassword)
    Button button_forgrtpassword;

    @ViewInject(R.id.button_login)
    Button button_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageButton_clear.setVisibility(View.GONE);
        checkBox_showPsw.setVisibility(View.GONE);
    }

    @Event(value = {R.id.edittext_password,R.id.edittext_username},type =View.OnFocusChangeListener.class )
    private void onFocusChange(View v, boolean hasFocus){
            if(hasFocus){
                if(v.getId()==R.id.edittext_username){
                    imageButton_clear.setVisibility(View.VISIBLE);
                    checkBox_showPsw.setVisibility(View.GONE);
                }else if(v.getId()==R.id.edittext_password){
                    imageButton_clear.setVisibility(View.GONE);
                    checkBox_showPsw.setVisibility(View.VISIBLE);
                }
            }
    }
    @Event(value = {R.id.button_login,R.id.imagebutton_username_clear},type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
            if(view.getId()==R.id.button_login){
                Intent intent=new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }else if(view.getId()==R.id.imagebutton_username_clear){
                    et_username.setText("");
            }
    }

    // 显示隐藏密码
    @Event(value = R.id.checkbox_password_show,type = CompoundButton.OnCheckedChangeListener.class)
    private void onEvenCheckedChange(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        // 变化后 光标移动到末尾
        CharSequence charSequence = et_password.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }
}
