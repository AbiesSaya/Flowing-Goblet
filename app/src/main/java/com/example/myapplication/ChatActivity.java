package com.example.myapplication;// 导入必要的包
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.myapplication.R;
import com.example.myapplication.ChatMessage;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.myapplication.ChatAdapter;


public class ChatActivity extends AppCompatActivity {

    private EditText mEtMessage;
    private Button mBtnSend;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化 Firebase 实时数据库
        mDatabase = FirebaseDatabase.getInstance().getReference("messages");

        // 初始化界面组件
        mEtMessage = findViewById(R.id.et_message);
        mBtnSend = findViewById(R.id.btn_send);
        mRecyclerView = findViewById(R.id.recycler_view);

        // 设置 RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // 设置发送按钮点击事件
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    // 发送消息方法
    private void sendMessage() {
        String messageText = mEtMessage.getText().toString().trim();
        String sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        if (!messageText.isEmpty()) {
            ChatMessage message = new ChatMessage(messageText, sender);
            mDatabase.push().setValue(message);
            mEtMessage.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 监听数据库，接收新消息
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 清空旧消息
                mAdapter.clearMessages();

                // 添加新消息
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    mAdapter.addMessage(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 处理数据库错误
            }
        });
    }
}
