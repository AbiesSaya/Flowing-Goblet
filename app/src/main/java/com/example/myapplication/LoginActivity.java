package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //声明
    private Button mBLogin;
    private EditText mEtUser;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //find
        mBLogin=findViewById(R.id.login);
        mEtUser=findViewById(R.id.username);
        mEtPassword=findViewById(R.id.password);

        //jump

        //匹配对应用户名和密码



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       mBLogin.setOnClickListener(this);
    }
        public void onClick(View v){
        //需要获取输入的用户名和密码
            String username = mEtUser.getText().toString();
            String password =mEtPassword.getText().toString();
            //弹出的内容
            String ok ="Welcome!We are waiting for U!";
            String fail="OH, maybe U just sent a WRONG Number";
            Intent intent =null;

            //true u:Abies P:0929  ;  u:morgan P:2478
            if((username.equals("Abies")&&password.equals("0929"))||(username.equals("morgan")&&password.equals("2478"))){
                //true
//toast
               // Toast.makeText(getApplicationContext(),ok,Toast.LENGTH_SHORT).show();
                //
                //这里试一下封装好的toast
                //
                ToastUtil.showMsg(LoginActivity.this,ok);
                intent=new Intent(LoginActivity.this,UsageActivity.class);
                startActivity(intent);
            }else{
//当不对的时候我我们需要做好一个弹出的toast
                Toast toastCenter = Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_SHORT);
                toastCenter.setGravity(Gravity.CENTER,0,0);//
                //
                //
                //这里的代码有些问题按道理来说这个东西应该是剧中显示得到但是实际上是和我们平时使用的普通的toast一样显示在底部的中间的的，有时间的时候可以看一下这里是什么样的问题，不过对于目前的阶段来说先忽略这个问题
                //
                //
                //

                toastCenter.show();
            }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}