package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setListeners();
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

    }

    private void setListeners(){
        binding.registerButton.setOnClickListener(v -> {
            if(isValidSignUp()){
                signUp();
            }
        });
    }
    //弹错误提示消息
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    //判断输入的信息是否完善
    private Boolean isValidSignUp(){
        if(binding.etNickname.getText().toString().trim().isEmpty()){
            showToast("请输入您的昵称");
            return false;
        }else if(binding.etEmail.getText().toString().trim().isEmpty()){
            showToast("请输入您的邮箱");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()){
            showToast("请输入正确的邮箱地址");
            return false;
        }else if(binding.etPassword.getText().toString().trim().isEmpty()){
            showToast("请输入您的密码");
            return false;
        }else if(binding.confPassword.getText().toString().trim().isEmpty()){
            showToast("请输入您的确认密码");
            return false;
        }else if(!binding.confPassword.getText().toString().equals(binding.etPassword.getText().toString())){
            showToast("两次输入的密码不一致，请重新输入");
            return false;
        }else {
            return true;
        }
    }

    private void signUp(){
        showToast("开始注册");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String,Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME,binding.etNickname.getText().toString());
        user.put(Constants.KEY_EMAIL,binding.etEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD,binding.etPassword.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, binding.etNickname.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), UsageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                    showToast(exception.getMessage());
                });
    }
}