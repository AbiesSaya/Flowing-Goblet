package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.util.ToastUtil;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBLogin;
    private EditText mEtUser;
    private EditText mEtPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        mBLogin = findViewById(R.id.login);
        mEtUser = findViewById(R.id.username);
        mEtPassword = findViewById(R.id.password);

        mBLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String username = mEtUser.getText().toString();
        final String password = mEtPassword.getText().toString();

        // 使用 Firebase Authentication 进行邮箱和密码验证
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // 登录成功
                        ToastUtil.showMsg(SignInActivity.this, "Welcome! We are waiting for you!");
                        Intent intent = new Intent(SignInActivity.this, UsageActivity.class);
                        startActivity(intent);
                    } else {
                        // 登录失败
                        ToastUtil.showMsg(SignInActivity.this, "Incorrect email or password.");
                    }
                });
    }
}
